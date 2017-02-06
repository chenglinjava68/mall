package com.plateno.booking.internal.service.order;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plateno.booking.internal.base.constant.PayStatusEnum;
import com.plateno.booking.internal.base.constant.PlateFormEnum;
import com.plateno.booking.internal.base.mapper.MOrderCouponMapper;
import com.plateno.booking.internal.base.mapper.OrderMapper;
import com.plateno.booking.internal.base.mapper.OrderProductMapper;
import com.plateno.booking.internal.base.pojo.MOrderCouponPO;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.base.pojo.OrderExample;
import com.plateno.booking.internal.base.pojo.OrderProduct;
import com.plateno.booking.internal.base.pojo.OrderProductExample;
import com.plateno.booking.internal.base.vo.MOrderCouponSearchVO;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.contants.OperateLogEnum;
import com.plateno.booking.internal.bean.contants.ViewStatusEnum;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.custom.MOperateLogParam;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.bean.request.point.ValueBean;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.common.util.redis.RedisLock;
import com.plateno.booking.internal.common.util.redis.RedisLock.Holder;
import com.plateno.booking.internal.coupon.service.CouponService;
import com.plateno.booking.internal.coupon.vo.CancelParam;
import com.plateno.booking.internal.coupon.vo.CancelResponse;
import com.plateno.booking.internal.goods.MallGoodsService;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.member.PointService;
import com.plateno.booking.internal.service.log.OperateLogService;
import com.plateno.booking.internal.service.log.OrderLogService;
import com.plateno.booking.internal.validator.order.MOrderValidate;
import com.plateno.booking.internal.wechat.model.ProductSkuBean;

@Service
public class OrderCancelService {

    protected final static Logger logger = LoggerFactory.getLogger(OrderCancelService.class);

    @Autowired
    private OrderMapper mallOrderMapper;

    @Autowired
    private MOrderValidate orderValidate;

    @Autowired
    private OrderLogService orderLogService;

    @Autowired
    private PointService pointService;

    @Autowired
    private MOrderCouponMapper mOrderCouponMapper;

    @Autowired
    private OperateLogService operateLogService;

    @Autowired
    private OrderProductMapper orderProductMapper;

    @Autowired
    private CouponService couponService;

    @Autowired
    private MallGoodsService mallGoodsService;

    /**
     * 更新订单状态(取消)
     * 
     * @param orderParam
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor=OrderException.class)
    public ResultVo<Object> cancelOrderLock(final MOrderParam orderParam) throws Exception{
        
        String lockName = "MALL_CANEL_ORDER_" + orderParam.getOrderNo();
        
        Holder holder = new RedisLock.Holder() {
            @Override
            public Object exec() throws Exception {
                //取消订单
                return cancelOrder(orderParam);
            }
        };
        
        return (ResultVo<Object>) RedisLock.lockExec(lockName, holder );
    }
    

    /**
     * 取消订单
     * 
     * @param orderParam
     * @return
     * @throws Exception
     */
    private ResultVo<Object> cancelOrder(final MOrderParam orderParam) throws Exception {
        ResultVo<Object> output = new ResultVo<Object>();

        // 校验订单是否可被处理
        List<Order> listOrder =
                mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(),
                        orderParam.getMemberId(), orderParam.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }
        orderValidate.checkCancelOrder(listOrder.get(0), output);
        if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
            return output;
        }

        String desc = "手动取消订单";
        // 判断取消类型
        if (orderParam.getType() != null && orderParam.getType() == 1) {

            desc = "超时取消订单";

            logger.info(String.format("orderNo:%s, 超时取消订单", orderParam.getOrderNo()));
            long now = new Date().getTime();
            long createTime = listOrder.get(0).getCreateTime().getTime();
            // 29分钟，避免时间存在误差
            if (now - createTime <= 29 * 60 * 1000) {
                logger.info(String.format("orderNo:%s, 超期取消时间错误:%s", orderParam.getOrderNo(),
                        listOrder.get(0).getCreateTime()));
                output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
                output.setResultMsg("超时取消时间未到达30分钟");
                return output;
            }
        }

        Order order = new Order();
        order.setPayStatus(BookingResultCodeContants.PAY_STATUS_2);
        order.setUpTime(new Date());
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andOrderNoEqualTo(orderParam.getOrderNo());
        mallOrderMapper.updateByExampleSelective(order, orderExample);

        orderLogService.saveGSOrderLog(orderParam.getOrderNo(),
                BookingResultCodeContants.PAY_STATUS_2, PayStatusEnum.PAY_STATUS_2.getDesc(), desc,
                0, ViewStatusEnum.VIEW_STATUS_CANNEL.getCode(), desc);

        // 退还积分
        if (listOrder.get(0).getPoint() > 0) {

            logger.info("取消订单，退还积分，orderNo:{}, point:{}", listOrder.get(0).getOrderNo(), listOrder
                    .get(0).getPoint());

            ValueBean vb = new ValueBean();
            vb.setPointvalue(listOrder.get(0).getPoint());
            vb.setMebId(listOrder.get(0).getMemberId());
            vb.setTrandNo(listOrder.get(0).getOrderNo());
            int mallAddPoint = pointService.mallAddPoint(vb);
            if (mallAddPoint > 0) {
                logger.error("取消订单，退还积分失败，orderNo:{}, memberId:{}, point:{}", listOrder.get(0)
                        .getOrderNo(), listOrder.get(0).getMemberId(), listOrder.get(0).getPoint());
                LogUtils.DISPERSED_ERROR_LOGGER.error(
                        "取消订单，退还积分失败，orderNo:{}, memberId:{}, point:{}", listOrder.get(0)
                                .getOrderNo(), listOrder.get(0).getMemberId(), listOrder.get(0)
                                .getPoint());
            }
        }

        // 退还库存
        try {
            logger.info("取消订单，退还库存，orderNo:{}", listOrder.get(0).getOrderNo());
            updateStock(orderParam, output);
        } catch (Exception e) {
            logger.error("退还库存生异常:" + orderParam.getOrderNo(), e);
        }

        // 如果使用了优惠券，退还优惠券
        if (listOrder.get(0).getCouponAmount() > 0) {
            returnCoupon(listOrder.get(0).getOrderNo(), listOrder.get(0).getMemberId());
        }

        // 如果是后台操作，取消记录操作日志
        if (orderParam.getPlateForm() != null
                && (orderParam.getPlateForm() == PlateFormEnum.ADMIN.getPlateForm() || orderParam
                        .getPlateForm() == PlateFormEnum.PROVIDER_ADMIN.getPlateForm())) {
            MOperateLogParam paramlog = new MOperateLogParam();
            paramlog.setOperateType(OperateLogEnum.CANCEL_ORDER.getOperateType());
            paramlog.setOperateUserid(orderParam.getOperateUserid());
            paramlog.setOperateUsername(orderParam.getOperateUsername());
            paramlog.setOrderCode(orderParam.getOrderNo());
            paramlog.setPlateForm(orderParam.getPlateForm());
            paramlog.setRemark(OperateLogEnum.CANCEL_ORDER.getOperateName());
            operateLogService.saveOperateLog(paramlog);
        }

        return output;
    }





    private ProductSkuBean updateStock(final MOrderParam orderParam, ResultVo<Object> output)
            throws OrderException {
        OrderProductExample orderProductExample = new OrderProductExample();
        orderProductExample.createCriteria().andOrderNoEqualTo(orderParam.getOrderNo());
        List<OrderProduct> productOrderList =
                orderProductMapper.selectByExample(orderProductExample);
        if (CollectionUtils.isEmpty(productOrderList)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单获取不到对应的产品信息");
        }
        final ProductSkuBean bean =
                mallGoodsService.getProductAndskuStock(productOrderList.get(0).getSkuid()
                        .toString());

        logger.info("取消订单，退还库存， orderNo:{}, skuId:{}, count:{}", orderParam.getOrderNo(),
                productOrderList.get(0).getSkuid(), productOrderList.get(0).getSkuCount());

        if (!mallGoodsService.modifyStock(productOrderList.get(0).getSkuid().toString(),
                productOrderList.get(0).getSkuCount())) {
            // LogUtils.sysLoggerInfo("更新库存失败");
            LogUtils.DISPERSED_ERROR_LOGGER.error("取消订单返回库存失败, skuId:{}, num:{}", productOrderList
                    .get(0).getSkuid(), productOrderList.get(0).getSkuCount());
            logger.error("取消订单返回库存失败, skuId:{}, num:{}", productOrderList.get(0).getSkuid(),
                    productOrderList.get(0).getSkuCount());
        }

        // 更新已经退还的库存
        orderProductMapper.updateReturnSkuCount(productOrderList.get(0).getSkuCount(),
                productOrderList.get(0).getId());

        return bean;
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
