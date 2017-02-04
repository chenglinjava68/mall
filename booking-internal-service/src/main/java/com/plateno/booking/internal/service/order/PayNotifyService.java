package com.plateno.booking.internal.service.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.plateno.booking.internal.base.constant.PayStatusEnum;
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
import com.plateno.booking.internal.bean.contants.ViewStatusEnum;
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
    private OrderProductMapper orderProductMapper;

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

        OrderPayLog log = orderPayLogMapper.getByTrandNo(payNotifyVo.getTradeNo());
        // 查询订单是否存在
        if (log == null) {
            logger.warn("支付网关支付回调,获取不到对应的流水信息：" + payNotifyVo.getTradeNo());
            throw new RuntimeException("找不到支付流水信息");
        }
        // 判断是否已经处理
        if (log.getStatus() != 1) {
            logger.warn("流水已经处理, trand_no：{}，status：{}", payNotifyVo.getTradeNo(), log.getStatus());
            return;
        }

        if (payNotifyVo.getAmount() == null || !payNotifyVo.getAmount().equals(log.getAmount())) {
            logger.warn("trand_no:{}, 流水金额和支付金额不对应，支付金额被篡改, orderMoney:{}, payMoney:{}",
                    log.getTrandNo(), log.getAmount(), payNotifyVo.getAmount());
            throw new RuntimeException("实付金额与网关金额对应不上");
        }
        doSuccessOrderPayLog(payNotifyVo, log);
        Order order = orderMapper.selectByPrimaryKey(log.getOrderId());
        // 查询订单状态
        checkOrderStatus(order, log);
        // 更新订单状态
        doUpdateOrderStatus(order, payNotifyVo);
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
    private void doSuccessOrderPayLog(PayNotifyVo payNotifyVo, OrderPayLog log) {

        OrderPayLogExample example = new OrderPayLogExample();
        example.createCriteria().andIdEqualTo(log.getId()).andStatusEqualTo(1);
        OrderPayLog record = new OrderPayLog();
        record.setUpTime(new Date());
        // record.setReferenceid(StringUtils.trimToEmpty(payNotifyVo.getPlatenopayFlowNo()));


        logger.info("trand_no:{}, 支付成功", log.getTrandNo());
        record.setCurrencyDepositAmount(payNotifyVo.getCurrencyDepositAmount());
        record.setGatewayAmount(payNotifyVo.getGatewayAmount());
        // 更新支付流水
        record.setStatus(BookingConstants.BILL_LOG_SUCCESS);
        record.setRemark("支付成功");
        orderPayLogMapper.updateByExampleSelective(record, example);

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
            logger.warn("找不到对应的订单, orderId:{}", log.getOrderId());
            throw new RuntimeException("找不到对应的订单");
        }

        if (order.getPayStatus() != PayStatusEnum.PAY_STATUS_11.getPayStatus()) {
            logger.warn("订单状态非支付中, orderNo:{}， paystatus：{}", order.getOrderNo(),
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
    private void doUpdateOrderStatus(Order order, PayNotifyVo payNotifyVo) throws Exception {
        Order updateOrder = new Order();
        updateOrder.setUpTime(new Date());
        updateOrder.setPayStatus(BookingResultCodeContants.PAY_STATUS_3);
        updateOrder.setPayTime(new Date());
        updateOrder.setCurrencyDepositAmount(payNotifyVo.getCurrencyDepositAmount());
        updateOrder.setGatewayAmount(payNotifyVo.getGatewayAmount());


        // 更新订单状态
        List<Integer> list = new ArrayList<>(1);
        list.add(BookingResultCodeContants.PAY_STATUS_11);
        int row = mOrderService.updateOrderStatusByNo(updateOrder, order.getOrderNo(), list);

        if (row > 0) {
            orderLogService.saveGSOrderLog(order.getOrderNo(), updateOrder.getPayStatus(),
                    PayStatusEnum.from(updateOrder.getPayStatus()).getDesc(), "支付网关回调：支付成功", 0,
                    ViewStatusEnum.VIEW_STATUS_PAY_USE.getCode());
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
        OrderPayLog orderPayLog = orderPayLogMapper.getByTrandNo(refundNotifyVo.getRefundTradeNo());
        Order order = mallOrderMapper.getByOrderIdForUpdate(orderPayLog.getOrderId());
        // 检查订单状态是否为退款中
        if (null == order) {
            logger.warn("订单不存在，订单退款回调通知失败，订单主键为:{},tranNo为：{}", orderPayLog.getOrderId(),
                    orderPayLog.getTrandNo());
            return;
        }
        if (order.getPayStatus() != PayStatusEnum.PAY_STATUS_10.getPayStatus()) {
            logger.warn("订单状态不为退款中，订单退款回调通知失败， orderNo:{}, payStatus:{}", order.getOrderNo(),
                    order.getPayStatus());
            return;
        }

        parseRefundNotify(refundNotifyVo, orderPayLog, order);
        logger.info(String.format("orderNo:%s, 收银台退款回调通知成功，进行后续操作", order.getOrderNo()));
        orderRefundActorService.doSuccessOrderRefundActor(order);


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
    private void parseRefundNotify(RefundNotifyVo refundNotifyVo, OrderPayLog orderPayLog,
            Order order) {
        logger.info("orderNo:{}, 退款成功", order.getOrderNo());
        // 更新支付流水状态(success == 2)
        orderPayLog.setStatus(BookingConstants.BILL_LOG_SUCCESS);
        // record.setRemark("退款成功");
        orderPayLog.setUpTime(new Date());
        orderPayLogMapper.updateByPrimaryKeySelective(orderPayLog);
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
        List<OrderProduct> productOrderList =
                orderProductMapper.selectByExample(orderProductExample);
        if (CollectionUtils.isEmpty(productOrderList)) {
            return null;
        }

        return productOrderList.get(0);
    }



}
