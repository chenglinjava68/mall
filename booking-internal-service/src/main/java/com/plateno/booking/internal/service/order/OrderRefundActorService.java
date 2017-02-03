package com.plateno.booking.internal.service.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.plateno.booking.internal.base.mapper.MOrderCouponMapper;
import com.plateno.booking.internal.base.mapper.OrderMapper;
import com.plateno.booking.internal.base.mapper.OrderPayLogMapper;
import com.plateno.booking.internal.base.mapper.OrderProductMapper;
import com.plateno.booking.internal.base.pojo.MOrderCouponPO;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.base.pojo.OrderProduct;
import com.plateno.booking.internal.base.pojo.OrderProductExample;
import com.plateno.booking.internal.base.vo.MOrderCouponSearchVO;
import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.bean.contants.ViewStatusEnum;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.point.ValueBean;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.coupon.service.CouponService;
import com.plateno.booking.internal.coupon.vo.CancelParam;
import com.plateno.booking.internal.coupon.vo.CancelResponse;
import com.plateno.booking.internal.email.model.RefundSuccessContent;
import com.plateno.booking.internal.email.service.PhoneMsgService;
import com.plateno.booking.internal.goods.MallGoodsService;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.member.PointService;
import com.plateno.booking.internal.service.log.OrderLogService;

@Service
public class OrderRefundActorService {

    private final static Logger logger = LoggerFactory.getLogger(OrderRefundActorService.class);

    @Autowired
    private OrderPayLogMapper orderPayLogMapper;
    @Autowired
    private OrderMapper mallOrderMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private MOrderService mOrderService;

    @Autowired
    private OrderLogService orderLogService;

    @Autowired
    private PointService pointService;
    
    @Autowired
    private OrderProductMapper orderProductMapper;
    
    @Autowired
    private MallGoodsService mallGoodsService;
    
    @Autowired
    private PhoneMsgService phoneMsgService;
    
    @Autowired
    private MOrderCouponMapper mOrderCouponMapper; 
    
    @Autowired
    private CouponService couponService;
    
    /**
     * @throws OrderException 
     * 
    * @Title: doSuccessOrderRefundActor 
    * @Description: 处理退款成功后的利益返还
    * @param @param order    
    * @return void    
    * @throws
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void doSuccessOrderRefundActor(Order order) throws OrderException{
        order.setRefundSuccesstime(new Date());
        order.setPayStatus(BookingResultCodeContants.PAY_STATUS_7);
        orderLogService.saveGSOrderLog(order.getOrderNo(), BookingResultCodeContants.PAY_STATUS_7, "网关退款成功", "支付网关退款同步：退款成功", 0,ViewStatusEnum.VIEW_STATUS_REFUND.getCode(), "支付网关回调通知");
        //更新账单状态
        orderMapper.updateByPrimaryKeySelective(order);
        returnPoint(order);
        OrderProduct orderProduct = returnProduct(order);
        //如果使用了优惠券，退还优惠券
        if(order.getCouponAmount() > 0) {
            returnCoupon(order.getOrderNo(), order.getMemberId());
        }
        
        sendMsg(order, orderProduct);
    }
    
    /**
     * 
    * @Title: sendMsg 
    * @Description: 发送退款短信
    * @param @param order
    * @param @param orderProduct    
    * @return void    
    * @throws
     */
    private void sendMsg(Order order,OrderProduct orderProduct){
        logger.info(String.format("orderNo:%s， 发送退款短信，skuid:%s, count:%s", order.getOrderNo(), orderProduct.getSkuid(), orderProduct.getSkuCount()));
        String templateId;
        RefundSuccessContent content = new RefundSuccessContent();
        content.setObjectNo(order.getOrderNo());
        content.setOrderCode(order.getOrderNo());
        content.setMoney(new BigDecimal(order.getPayMoney()).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_DOWN).toString());
        content.setName(orderProduct.getProductName());

        //积分变动不提醒用户，使用同一个模板
        templateId = Config.SMS_SERVICE_TEMPLATE_EIGHT;
        phoneMsgService.sendPhoneMessageAsync(order.getMobile(), templateId, content);
    }
    
    /**
     * 
    * @Title: returnProduct 
    * @Description: 返还库存
    * @param @param order
    * @param @return
    * @param @throws OrderException    
    * @return OrderProduct    
    * @throws
     */
    private OrderProduct returnProduct(Order order) throws OrderException{
        OrderProduct productByOrderNo = getProductByOrderNo(order.getOrderNo());
        if(productByOrderNo == null) {
            logger.error(String.format("orderNo:%s, 退款退库存失败, 找不到购买的商品信息", order.getOrderNo()));
        } else {
            
            //更新已经返还的库存
            int row = orderProductMapper.updateReturnSkuCount(productByOrderNo.getSkuCount(), productByOrderNo.getId());
            //同意退款也会退还库存，为了过渡， 两边都加退款逻辑
            if(row > 0) {
                logger.info(String.format("orderNo:%s， 退还库存，skuid:%s, count:%s", order.getOrderNo(), productByOrderNo.getSkuid(), productByOrderNo.getSkuCount()));
                boolean modifyStock = mallGoodsService.modifyStock(productByOrderNo.getSkuid().toString(), productByOrderNo.getSkuCount());
                if(!modifyStock){
                    logger.error(String.format("orderNo:%s, 调用商品服务失败", order.getOrderNo()));
                    //LogUtils.sysLoggerInfo(String.format("orderNo:%s, 调用商品服务失败", orderNo));
                    LogUtils.DISPERSED_ERROR_LOGGER.error("退款归还库存失败, orderNo:{}, skuId:{}, count:{}", order.getOrderNo(), productByOrderNo.getSkuid(), productByOrderNo.getSkuCount());
                }
            }
        }
        return productByOrderNo;
    }
    
    /**
     * 
    * @Title: returnPoint 
    * @Description: 返还积分
    * @param @param dbOrder    
    * @return void    
    * @throws
     */
    private void returnPoint(final Order dbOrder) {
        if(dbOrder.getPoint()>0){
          //退款归还下单积分
            logger.info("orderNo:{}， 退还积分，point:{}", dbOrder.getOrderNo(), dbOrder.getRefundPoint());
            ValueBean vb=new ValueBean();
            vb.setPointvalue(dbOrder.getRefundPoint());
            vb.setMebId(dbOrder.getMemberId());
            vb.setTrandNo(dbOrder.getOrderNo());
            int mallAddPoint = pointService.mallAddPoint(vb);
            if(mallAddPoint > 0) {
                LogUtils.DISPERSED_ERROR_LOGGER.error("退还积分失败, orderNo:{}, memberId:{}, point:{}", dbOrder.getOrderNo(), dbOrder.getMemberId(), dbOrder.getRefundPoint());
            }
        }
    }
    
    /**
     * 获取订单的商品信息
     * @param orderNo
     * @return
     */
    public OrderProduct getProductByOrderNo(String orderNo) {
        OrderProductExample orderProductExample=new OrderProductExample();
        orderProductExample.createCriteria().andOrderNoEqualTo(orderNo);
        @SuppressWarnings("unchecked")
        List<OrderProduct> productOrderList = orderProductMapper.selectByExample(orderProductExample);
        if(CollectionUtils.isEmpty(productOrderList)) {
            return null;
        }
        
        return productOrderList.get(0);
    }
    
    /**
     * 返回优惠券
     * @param orderNo
     */
    private ResultVo<String> returnCoupon(String orderNo, Integer memberId) {
        
        logger.info("退还优惠券, orderNo:{}", orderNo);
        
        MOrderCouponSearchVO svo = new MOrderCouponSearchVO();
        svo.setOrderNo(orderNo);
        //查询优惠券信息
        List<MOrderCouponPO> couponList = mOrderCouponMapper.list(svo);
        
        if(couponList.size() <= 0) {
            logger.error("订单orderNo:{}, 找不到优惠券的使用信息", orderNo);
            return new ResultVo<String>(ResultCode.FAILURE, null, "查询优惠券信息失败");
        } else {
            
            MOrderCouponPO mOrderCouponPO = couponList.get(0);
            
            CancelParam param = new CancelParam();
            param.setCouponId(mOrderCouponPO.getCouponId());
            param.setMebId(memberId);

            ResultVo<CancelResponse> cancelCouponResult = couponService.cancelCoupon(param);
            
            if(!cancelCouponResult.success()) {
                logger.error("退还优惠券失败，orderNo:{}, memberId:{}, couponId:{}, result:{}", orderNo, memberId, mOrderCouponPO.getCouponId(), cancelCouponResult);
                LogUtils.DISPERSED_ERROR_LOGGER.error("退还优惠券失败，orderNo:{}, memberId:{}, couponId:{}, result:{}", orderNo, memberId, mOrderCouponPO.getCouponId(), cancelCouponResult);
                
                return new ResultVo<String>(ResultCode.FAILURE, null, "返回优惠券失败，" + cancelCouponResult.getResultMsg());
            }
            
            return new ResultVo<String>(ResultCode.SUCCESS);
        }
    }
    
}
