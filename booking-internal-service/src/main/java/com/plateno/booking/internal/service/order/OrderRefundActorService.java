package com.plateno.booking.internal.service.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.base.constant.PayStatusEnum;
import com.plateno.booking.internal.base.mapper.MOrderCouponMapper;
import com.plateno.booking.internal.base.mapper.OrderMapper;
import com.plateno.booking.internal.base.pojo.MOrderCouponPO;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.base.pojo.OrderProduct;
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
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.member.PointService;
import com.plateno.booking.internal.service.log.OrderLogService;

@Service
public class OrderRefundActorService {

    private final static Logger logger = LoggerFactory.getLogger(OrderRefundActorService.class);


    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderLogService orderLogService;

    @Autowired
    private PointService pointService;

    @Autowired
    private PhoneMsgService phoneMsgService;

    @Autowired
    private MOrderCouponMapper mOrderCouponMapper;

    @Autowired
    private CouponService couponService;

    @Autowired
    private OrderStockService orderStockService;

    @Autowired
    private OrderSubService orderSubService;

    /**
     * @throws OrderException
     * 
     * @Title: doSuccessOrderRefundActor
     * @Description: 处理退款成功后的利益返还
     * @param @param order
     * @return void
     * @throws
     */
    public void doSuccessOrderRefundActor(Order order) throws OrderException {
        updateOrderStatusToRefund(order);
        orderSubService.updateToPayStatus(order.getOrderNo(),
                PayStatusEnum.PAY_STATUS_7.getPayStatus());
        orderLogService.saveGSOrderLog(order.getOrderNo(), BookingResultCodeContants.PAY_STATUS_7,
                "收银台回调通知", "收银台回调通知：退款成功", 0, ViewStatusEnum.VIEW_STATUS_REFUND.getCode(),
                "收银台回调通知");
        returnBenefit(order);
        // 返还库存
        List<OrderProduct> orderProductList = orderStockService.returnStock(order.getOrderNo());
        sendMsg(order, orderProductList);
    }

    private void updateOrderStatusToRefund(Order order) {
        order.setRefundSuccesstime(new Date());
        order.setPayStatus(PayStatusEnum.PAY_STATUS_7.getPayStatus());
        orderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * 
    * @Title: returnBenefit 
    * @Description: 返还积分、优惠券、库存
    * @param @param order
    * @param @throws OrderException    
    * @return void    
    * @throws
     */
    public void returnBenefit(Order order) throws OrderException {
        // 返还积分
        returnPoint(order);
        // 如果使用了优惠券，退还优惠券
        if (order.getCouponAmount() > 0) {
            returnCoupon(order.getOrderNo(), order.getMemberId());
        }
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
    private void sendMsg(Order order, List<OrderProduct> orderProductList) {
        logger.info("orderNo:{}发送退款短信", order.getOrderNo());
        String templateId;
        RefundSuccessContent content = new RefundSuccessContent();
        content.setObjectNo(order.getOrderNo());
        content.setOrderCode(order.getOrderNo());
        content.setMoney(new BigDecimal(order.getPayMoney()).divide(new BigDecimal("100"), 2,
                BigDecimal.ROUND_DOWN).toString());
        StringBuilder sb = new StringBuilder();
        for (OrderProduct orderProduct : orderProductList) {
            sb.append(orderProduct.getProductName()).append(";");
        }
        content.setName(sb.toString());
        // 积分变动不提醒用户，使用同一个模板
        templateId = Config.SMS_SERVICE_TEMPLATE_EIGHT;
        phoneMsgService.sendPhoneMessageAsync(order.getMobile(), templateId, content);
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
        if (dbOrder.getPoint() > 0) {
            // 退款归还下单积分
            logger.info("orderNo:{}， 退还积分，point:{}", dbOrder.getOrderNo(), dbOrder.getRefundPoint());
            ValueBean vb = new ValueBean();
            vb.setPointvalue(dbOrder.getPoint());
            vb.setMebId(dbOrder.getMemberId());
            vb.setTrandNo(dbOrder.getOrderNo());
            int mallAddPoint = pointService.mallAddPoint(vb);
            if (mallAddPoint > 0) {
                LogUtils.DISPERSED_ERROR_LOGGER.error("退还积分失败, orderNo:{}, memberId:{}, point:{}",
                        dbOrder.getOrderNo(), dbOrder.getMemberId(), dbOrder.getRefundPoint());
            }
        }
    }


    /**
     * 返回优惠券
     * 
     * @param orderNo
     */
    private ResultVo<String> returnCoupon(String orderNo, Integer memberId) {

        logger.info("退还优惠券, orderNo:{}", orderNo);

        MOrderCouponSearchVO svo = new MOrderCouponSearchVO();
        svo.setOrderNo(orderNo);
        // 查询优惠券信息
        List<MOrderCouponPO> couponList = mOrderCouponMapper.list(svo);

        if (couponList.size() <= 0) {
            logger.error("订单orderNo:{}, 找不到优惠券的使用信息", orderNo);
            return new ResultVo<String>(ResultCode.FAILURE, null, "查询优惠券信息失败");
        } else {

            MOrderCouponPO mOrderCouponPO = couponList.get(0);
            CancelParam param = new CancelParam();
            param.setCouponId(mOrderCouponPO.getCouponId());
            param.setMebId(memberId);

            ResultVo<CancelResponse> cancelCouponResult = couponService.cancelCoupon(param);

            if (!cancelCouponResult.success()) {
                logger.error("退还优惠券失败，orderNo:{}, memberId:{}, couponId:{}, result:{}", orderNo,
                        memberId, mOrderCouponPO.getCouponId(), cancelCouponResult);
                LogUtils.DISPERSED_ERROR_LOGGER.error(
                        "退还优惠券失败，orderNo:{}, memberId:{}, couponId:{}, result:{}", orderNo,
                        memberId, mOrderCouponPO.getCouponId(), cancelCouponResult);

                return new ResultVo<String>(ResultCode.FAILURE, null, "返回优惠券失败，"
                        + cancelCouponResult.getResultMsg());
            }

            return new ResultVo<String>(ResultCode.SUCCESS);
        }
    }

}
