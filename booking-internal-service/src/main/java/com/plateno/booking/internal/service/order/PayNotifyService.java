package com.plateno.booking.internal.service.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.plateno.booking.internal.base.constant.PayStatusEnum;
import com.plateno.booking.internal.base.mapper.MOrderCouponMapper;
import com.plateno.booking.internal.base.mapper.OrderMapper;
import com.plateno.booking.internal.base.mapper.OrderPayLogMapper;
import com.plateno.booking.internal.base.mapper.OrderProductMapper;
import com.plateno.booking.internal.base.model.PayNotifyVo;
import com.plateno.booking.internal.base.model.RefundNotifyVo;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.base.pojo.OrderPayLog;
import com.plateno.booking.internal.base.pojo.OrderPayLogExample;
import com.plateno.booking.internal.base.pojo.OrderProduct;
import com.plateno.booking.internal.base.pojo.OrderProductExample;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.bean.contants.PayGateCode;
import com.plateno.booking.internal.bean.contants.ViewStatusEnum;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.coupon.service.CouponService;
import com.plateno.booking.internal.email.service.PhoneMsgService;
import com.plateno.booking.internal.goods.MallGoodsService;
import com.plateno.booking.internal.member.PointService;
import com.plateno.booking.internal.service.log.OrderLogService;

/**
 * 
 * @ClassName: PayNotifyService
 * @Description: 收银台通知处理
 * @author zhengchubin
 * @date 2017年1月23日 下午5:24:15
 *
 */
@Service
public class PayNotifyService {
    private final static Logger logger = LoggerFactory.getLogger(PayNotifyService.class);

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

    @Autowired
    private OrderRefundActorService orderRefundActorService;

    /**
     * 支付网关回调，更新订单的状态
     * 
     * @param notifyReturn
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void payNotify(PayNotifyVo payNotifyVo) throws Exception {

        OrderPayLog log = orderPayLogMapper.getByTrandNo(payNotifyVo.getMerchantOrderNo());
        // 查询订单是否存在
        if (log == null) {
            logger.error("支付网关支付回调,获取不到对应的流水信息：" + payNotifyVo.getMerchantOrderNo());
            throw new RuntimeException("找不到支付流水信息");
        }
        // 判断是否已经处理
        if (log.getStatus() != 1) {
            logger.info("流水已经处理, trand_no：{}，status：{}", payNotifyVo.getMerchantOrderNo(),
                    log.getStatus());
            return;
        }

        boolean success = false;
        // 支付成功
        if (PayGateCode.SUCCESS.equals(payNotifyVo.getCode())) {
            success = true;
            // 插入成功状态流水
            boolean flag = doSuccessOrderPayLog(payNotifyVo, log);
            // 流水处理失败，则直接返回
            if (!flag)
                return;
        } else if (PayGateCode.FAIL.equals(payNotifyVo.getCode())) { // 支付失败
            boolean flag = doFailOrderPayLog(payNotifyVo, log);
            // 流水处理失败，则直接返回
            if (!flag)
                return;
        } else {
            logger.info(String.format("支付网关支付回调，非最终状态, orderNo:%s, code:%s",
                    payNotifyVo.getMerchantOrderNo(), payNotifyVo.getCode()));
            throw new RuntimeException("支付网关支付回调，非最终状态");
        }

        Order order = orderMapper.selectByPrimaryKey(log.getOrderId());
        // 查询订单状态
        checkOrderStatus(order, log);
        // 更新订单状态
        doUpdateOrderStatus(success, order);
    }

    /**
     * 
     * @Title: doFailOrderPayLog
     * @Description: 更新支付流水失败
     * @param @param payNotifyVo
     * @param @param log
     * @param @return
     * @return boolean
     * @throws
     */
    private boolean doFailOrderPayLog(PayNotifyVo payNotifyVo, OrderPayLog log) {
        OrderPayLogExample example = new OrderPayLogExample();
        example.createCriteria().andIdEqualTo(log.getId()).andStatusEqualTo(1);
        OrderPayLog record = new OrderPayLog();
        record.setUpTime(new Date());
        record.setReferenceid(StringUtils.trimToEmpty(payNotifyVo.getPlatenopayFlowNo()));

        logger.info("trand_no:{}, 支付失败", log.getTrandNo());

        // 更新支付流水
        record.setStatus(BookingConstants.BILL_LOG_FAIL);
        record.setRemark(String.format("支付失败:%s", payNotifyVo.getMessage()));
        int row = orderPayLogMapper.updateByExampleSelective(record, example);
        // 订单已经处理
        if (row < 1) {
            logger.info("流水已经处理, trand_no：" + payNotifyVo.getMerchantOrderNo());
            return false;
        }

        return true;
    }

    /**
     * 
     * @Title: doSuccessOrderPayLog
     * @Description: 处理成功流水，成功返回true
     * @param @param record
     * @param @param example
     * @param @return
     * @return boolean
     * @throws
     */
    private boolean doSuccessOrderPayLog(PayNotifyVo payNotifyVo, OrderPayLog log) {

        OrderPayLogExample example = new OrderPayLogExample();
        example.createCriteria().andIdEqualTo(log.getId()).andStatusEqualTo(1);
        OrderPayLog record = new OrderPayLog();
        record.setUpTime(new Date());
        record.setReferenceid(StringUtils.trimToEmpty(payNotifyVo.getPlatenopayFlowNo()));

        if (payNotifyVo.getAmount() == null || !payNotifyVo.getAmount().equals(log.getAmount())) {
            logger.error("trand_no:{}, 流水金额和支付金额不对应，支付金额被篡改, orderMoney:{}, payMoney:{}",
                    log.getTrandNo(), log.getAmount(), payNotifyVo.getAmount());
            return false;
        }

        logger.info("trand_no:{}, 支付成功", log.getTrandNo());
        record.setCurrencyDepositAmount(payNotifyVo.getCurrencyDepositAmount());
        record.setGatewayAmount(payNotifyVo.getGatewayAmount());
        // 更新支付流水
        record.setStatus(BookingConstants.BILL_LOG_SUCCESS);
        record.setRemark("支付成功");
        int row = orderPayLogMapper.updateByExampleSelective(record, example);
        // 订单已经处理
        if (row < 1) {
            logger.info("流水已经处理, trand_no：" + payNotifyVo.getMerchantOrderNo());
            return false;
        }
        return true;
    }

    /**
     * 
     * @Title: checkOrderStatus
     * @Description: 检查订单状态
     * @param @param order
     * @param @param log
     * @return void
     * @throws
     */
    private void checkOrderStatus(Order order, OrderPayLog log) {
        if (order == null) {
            logger.error("找不到对应的订单, orderId:{}", log.getOrderId());
            throw new RuntimeException("找不到对应的订单");
        }

        if (order.getPayStatus() != PayStatusEnum.PAY_STATUS_11.getPayStatus()) {
            logger.error("订单状态非支付中, orderNo:{}， paystatus：{}", order.getOrderNo(),
                    order.getPayStatus());
            throw new RuntimeException("订单状态非支付中");
        }
    }

    /**
     * 
     * @Title: doUpdateOrderStatus
     * @Description: 更新订单状态，后期需要更新子订单状态
     * @param @param success
     * @param @param order
     * @param @throws Exception
     * @return void
     * @throws
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void doUpdateOrderStatus(boolean success, Order order) throws Exception {
        Order updateOrder = new Order();
        updateOrder.setUpTime(new Date());

        if (success) {
            updateOrder.setPayStatus(BookingResultCodeContants.PAY_STATUS_3);
            updateOrder.setPayTime(new Date());
        } else {
            updateOrder.setPayStatus(BookingResultCodeContants.PAY_STATUS_1);
            updateOrder.setPayType(0); // 支付方式设置成未支付
        }

        // 更新订单状态
        List<Integer> list = new ArrayList<>(1);
        list.add(BookingResultCodeContants.PAY_STATUS_11);
        int row = mOrderService.updateOrderStatusByNo(updateOrder, order.getOrderNo(), list);

        if (row > 0) {
            orderLogService.saveGSOrderLog(order.getOrderNo(), updateOrder.getPayStatus(),
                    PayStatusEnum.from(updateOrder.getPayStatus()).getDesc(), "支付网关回调："
                            + (success ? "支付成功" : "支付失败"), 0,
                    success ? ViewStatusEnum.VIEW_STATUS_PAY_USE.getCode()
                            : ViewStatusEnum.VIEW_STATUS_PAYING.getCode());
        }

        logger.info("更新订单状态, orderNo：{}, row:{}", order.getOrderNo(), row);
    }


    /**
     * 
     * @Title: payNotifyRefund
     * @Description: 退款回调通知处理
     * @param @param refundNotifyVo
     * @param @throws Exception
     * @return void
     * @throws
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void payNotifyRefund(RefundNotifyVo refundNotifyVo) throws Exception {
        // 根据商户订单号，获取记录
        OrderPayLog orderPayLog =
                orderPayLogMapper.getByTrandNo(refundNotifyVo.getMerchantOrderNo());
        Order order = mallOrderMapper.getByOrderIdForUpdate(orderPayLog.getOrderId());
        // 检查订单状态是否为退款中
        if (null == order) {
            logger.error("订单不存在，订单退款回调通知失败，订单主键为:{},tranNo为：{}", orderPayLog.getOrderId(),
                    orderPayLog.getTrandNo());
            return;
        }
        if (order.getPayStatus() != PayStatusEnum.PAY_STATUS_10.getPayStatus()) {
            logger.error("订单状态不为退款中，订单退款回调通知失败， orderNo:{}, payStatus:{}", order.getOrderNo(),
                    order.getPayStatus());
            return;
        }

        boolean success = false;
        success = parseRefundNotify(refundNotifyVo, orderPayLog, order);
        if (success) {
            logger.info(String.format("orderNo:%s, 退款成功", order.getOrderNo()));
            orderRefundActorService.doSuccessOrderRefundActor(order);

        } else {
            logger.info(String.format("orderNo:%s, 退款失败", order.getOrderNo()));
            order.setRefundSuccesstime(new Date());
            order.setPayStatus(BookingResultCodeContants.PAY_STATUS_13);
            order.setRefundFailReason("网关退款失败");
            orderLogService.saveGSOrderLog(order.getOrderNo(), BookingConstants.PAY_STATUS_13,
                    "网关退款失败", "支付网关退款同步：退款失败", 0, ViewStatusEnum.VIEW_STATUS_REFUND_FAIL.getCode(),
                    "收银台回调通知");
            // 更新账单状态
            orderMapper.updateByPrimaryKeySelective(order);
        }

    }

    /**
     * 
    * @Title: parseRefundNotify 
    * @Description: 解析退款回调通知数据
    * @param @param refundNotifyVo
    * @param @param orderPayLog
    * @param @param order
    * @param @return    
    * @return boolean    
    * @throws
     */
    private boolean parseRefundNotify(RefundNotifyVo refundNotifyVo, OrderPayLog orderPayLog,
            Order order) {
        if (refundNotifyVo.getCode().equals(BookingConstants.GATEWAY_REFUND_SUCCESS_CODE)) { // 退款成功

            logger.info(String.format("orderNo:%s, 退款成功", order.getOrderNo()));

            // 更新支付流水状态(success == 2)
            orderPayLog.setStatus(BookingConstants.BILL_LOG_SUCCESS);
            // record.setRemark("退款成功");
            orderPayLog.setUpTime(new Date());
            orderPayLog.setReferenceid(StringUtils.trimToEmpty(refundNotifyVo
                    .getPlatenopayRefundFlowNo()));
            orderPayLogMapper.updateByPrimaryKeySelective(orderPayLog);
            return true;
        } else if ((refundNotifyVo.getCode().equals(PayGateCode.REFUND_FAIL) || refundNotifyVo
                .getCode().equals(PayGateCode.REQUEST_EXCEPTION))) { // 退款失败

            logger.info(String.format("orderNo:%s, 退款失败", order.getOrderNo()));

            // 更新支付流水状态(fail == 3)
            // record.setRemark(String.format("退款失败:%s", response.getMessage()));
            orderPayLog.setStatus(BookingConstants.BILL_LOG_FAIL);
            orderPayLog.setUpTime(new Date());
            orderPayLog.setReferenceid(StringUtils.trimToEmpty(refundNotifyVo
                    .getPlatenopayRefundFlowNo()));
            orderPayLogMapper.updateByPrimaryKeySelective(orderPayLog);
            return false;
        }
        return true;
    }


    /**
     * 获取订单的商品信息
     * 
     * @param orderNo
     * @return
     */
    public OrderProduct getProductByOrderNo(String orderNo) {
        OrderProductExample orderProductExample = new OrderProductExample();
        orderProductExample.createCriteria().andOrderNoEqualTo(orderNo);
        @SuppressWarnings("unchecked")
        List<OrderProduct> productOrderList =
                orderProductMapper.selectByExample(orderProductExample);
        if (CollectionUtils.isEmpty(productOrderList)) {
            return null;
        }

        return productOrderList.get(0);
    }



}
