package com.plateno.booking.internal.service.order;

import java.lang.reflect.InvocationTargetException;
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
import com.plateno.booking.internal.base.mapper.OrderMapper;
import com.plateno.booking.internal.base.mapper.OrderPayLogMapper;
import com.plateno.booking.internal.base.model.BookingPayQueryVo;
import com.plateno.booking.internal.base.model.NotifyReturn;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.base.pojo.OrderPayLog;
import com.plateno.booking.internal.base.pojo.OrderPayLogExample;
import com.plateno.booking.internal.base.pojo.OrderSub;
import com.plateno.booking.internal.base.pojo.OrderSubExample;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.contants.PayGateCode;
import com.plateno.booking.internal.bean.contants.ViewStatusEnum;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.cashierdesk.CashierDeskService;
import com.plateno.booking.internal.cashierdesk.vo.CashierDeskConstant;
import com.plateno.booking.internal.cashierdesk.vo.CashierPayQueryResponse;
import com.plateno.booking.internal.cashierdesk.vo.PayQueryReq;
import com.plateno.booking.internal.cashierdesk.vo.PayQueryVo;
import com.plateno.booking.internal.common.util.bean.BeanUtils;
import com.plateno.booking.internal.common.util.date.DateUtil;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.common.util.number.StringUtil;
import com.plateno.booking.internal.common.util.redis.RedisUtils;
import com.plateno.booking.internal.gateway.PaymentService;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.dict.DictService;
import com.plateno.booking.internal.service.log.OrderLogService;

@Service
public class PayService {

    private final static Logger logger = LoggerFactory.getLogger(PayService.class);

    @Autowired
    private OrderPayLogMapper orderPayLogMapper;
    @Autowired
    private OrderMapper mallOrderMapper;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private MOrderService mOrderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderLogService orderLogService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CashierDeskService cashierDeskService;

    @Autowired
    private OrderSubService orderSubService;



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResultVo<Object> pullerPay(MOrderParam mOrderParam) throws OrderException, Exception {
        ResultVo<Object> output = new ResultVo<Object>();
        List<Order> listOrder =
                mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(mOrderParam.getOrderNo(),
                        mOrderParam.getMemberId(), mOrderParam.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }

        // 代发货和支付中才（多次拉起支付）允许更新订单
        List<Integer> list = new ArrayList<>();
        list.add(BookingResultCodeContants.PAY_STATUS_1);
        list.add(BookingResultCodeContants.PAY_STATUS_11);

        if (!list.contains(listOrder.get(0).getPayStatus())) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("目前订单状态不允许支付");
            return output;
        }

        // 更新订单状态
        Order order = new Order();
        order.setPayStatus(BookingResultCodeContants.PAY_STATUS_11);
        order.setPayType(mOrderParam.getPayType());
        int row = mOrderService.updateOrderStatusByNo(order, mOrderParam.getOrderNo(), list);
        if (row < 1) {
            logger.info("orderNo:" + mOrderParam.getOrderNo() + ", 目前状态不允许拉起支付, status:"
                    + listOrder.get(0).getPayStatus());
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("目前订单状态不允许支付");
            return output;
        }

        order = listOrder.get(0);
        OrderPayLog paylog = new OrderPayLog();
        paylog.setAmount(order.getPayMoney());
        paylog.setCreateTime(new Date());
        paylog.setOrderId(order.getId());
        paylog.setClientType(1);
        paylog.setTrandNo(StringUtil.getCurrentAndRamobe("L"));
        paylog.setReferenceid("");
        paylog.setStatus(BookingConstants.BILL_LOG_NORMAL);
        paylog.setPoint(order.getPoint());
        paylog.setType(1);// 1收入 2支出
        paylog.setUpTime(new Date());
        paylog.setCurrencyDepositAmount(null != mOrderParam.getCurrencyDepositAmount() ? mOrderParam
                .getCurrencyDepositAmount() : 0);
        orderPayLogMapper.insert(paylog);

        orderLogService.saveGSOrderLog(order.getOrderNo(),
                PayStatusEnum.PAY_STATUS_11.getPayStatus(), PayStatusEnum.PAY_STATUS_11.getDesc(),
                "拉起支付", 0, ViewStatusEnum.VIEW_STATUS_PAYING.getCode());

        // 回传支付流水号
        output.setData(paylog.getTrandNo());

        return output;
    }


    public com.plateno.booking.internal.base.model.bill.BillOrderDetail getOrderNoByTradeNo(
            String orderNo) {
        return mallOrderMapper.getOrderNoByTradeNo(orderNo);
    }


    /**
     * 支付网关回调，更新订单的状态
     * 
     * @param notifyReturn
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void payNotify(NotifyReturn notifyReturn) throws Exception {

        logger.info("支付网关回调:{}", JsonUtils.toJsonString(notifyReturn));

        OrderPayLog log = orderPayLogMapper.getByTrandNo(notifyReturn.getOrderNo());

        // 查询订单是否存在
        if (log == null) {
            logger.error("支付网关支付回调,获取不到对应的流水信息：" + notifyReturn.getOrderNo());
            throw new RuntimeException("找不到支付流水信息");
        }

        // 判断是否已经处理
        if (log.getStatus() != 1) {
            logger.info("流水已经处理, trand_no：" + notifyReturn.getOrderNo() + "，  status:"
                    + log.getStatus());
            return;
        }

        boolean success = false;

        OrderPayLogExample example = new OrderPayLogExample();
        example.createCriteria().andIdEqualTo(log.getId()).andStatusEqualTo(1);
        OrderPayLog record = new OrderPayLog();
        record.setUpTime(new Date());
        record.setReferenceid(StringUtils.trimToEmpty(notifyReturn.getReferenceId()));

        // 支付成功
        if (PayGateCode.SUCCESS.equals(notifyReturn.getCode())) {

            if (notifyReturn.getOrderAmount() == null
                    || !notifyReturn.getOrderAmount().equals(log.getAmount())) {
                logger.error("trand_no:{}, 流水金额和支付金额不对应，支付金额被篡改, orderMoney:{}, payMoney:{}",
                        log.getTrandNo(), log.getAmount(), notifyReturn.getOrderAmount());
                return;
            }

            logger.info("trand_no:{}, 支付成功", log.getTrandNo());

            success = true;

            // 更新支付流水
            record.setStatus(BookingConstants.BILL_LOG_SUCCESS);
            record.setRemark("支付成功");
            int row = orderPayLogMapper.updateByExampleSelective(record, example);
            // 订单已经处理
            if (row < 1) {
                logger.info("流水已经处理, trand_no：" + notifyReturn.getOrderNo());
                return;
            }

        } else if (PayGateCode.FAIL.equals(notifyReturn.getCode())) { // 支付失败

            logger.info("trand_no:{}, 支付失败", log.getTrandNo());

            // 更新支付流水
            record.setStatus(BookingConstants.BILL_LOG_FAIL);
            record.setRemark(String.format("支付失败:%s", notifyReturn.getMessage()));
            int row = orderPayLogMapper.updateByExampleSelective(record, example);
            // 订单已经处理
            if (row < 1) {
                logger.info("流水已经处理, trand_no：" + notifyReturn.getOrderNo());
                return;
            }

        } else {
            logger.info(String.format("支付网关支付回调，非最终状态, orderNo:%s, code:%s",
                    notifyReturn.getOrderNo(), notifyReturn.getCode()));
            throw new RuntimeException("支付网关支付回调，非最终状态");
        }

        Order order = (Order) orderMapper.selectByPrimaryKey(log.getOrderId());
        if (order == null) {
            logger.info("找不到对应的订单, orderId:{}", log.getOrderId());
            return;
        }

        if (order.getPayStatus() != PayStatusEnum.PAY_STATUS_11.getPayStatus()) {
            logger.info("订单状态非支付中, orderNo:{}， paystatus：{}", order.getOrderNo(),
                    order.getPayStatus());
            return;
        }

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
     * 处理支付中的流水
     * @throws Exception 
     */
    @Transactional(rollbackFor = Exception.class)
    public void handlePaying() throws Exception {
        Date startTime = DateUtil.getDate(new Date(), -4, 0, 0, 0);
        Date endTime = DateUtil.getDate(new Date(), 0, 0, -5, 0);
        int num = 3000;
        List<OrderPayLog> list = orderPayLogMapper.queryPayingLog(startTime, endTime, num);
        for (OrderPayLog log : list) {
            handlePayingLog(log);
        }
    }

    /**
     * 每一条支付中的流水处理
     * 
     * @param log
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws Exception
     */
    public void handlePayingLog(OrderPayLog orderPayLog) throws Exception{
        logger.info("支付中订单处理，orderId:{}, trandNo:{}", orderPayLog.getOrderId(),
                orderPayLog.getTrandNo());
        // 获取网关的订单状态
        CashierPayQueryResponse cashierPayQueryResponse = queryPayFromCashier(orderPayLog);
        if (null == cashierPayQueryResponse)
            return;
        PayQueryVo payQueryVo = cashierPayQueryResponse.getResult();
        BookingPayQueryVo bookingPayQueryVo = new BookingPayQueryVo();
        BeanUtils.copyProperties(bookingPayQueryVo, payQueryVo);
        orderPayLog.setRemark("支付成功：主动查询更新");
        doWithOrderPayLogAndOrder(orderPayLog, bookingPayQueryVo);

    }

    /**
     * 
     * @Title: queryPayFromCashier
     * @Description: 查询收银台支付状态
     * @param @param orderPayLog
     * @param @return
     * @return CashierPayQueryResponse
     * @throws
     */
    private CashierPayQueryResponse queryPayFromCashier(OrderPayLog orderPayLog) {
        // 获取网关的订单状态
        PayQueryReq req = new PayQueryReq();
        req.setTradeNo(orderPayLog.getTrandNo());
        req.setUpdatePayStatusFlag(1);
        CashierPayQueryResponse cashierPayQueryResponse = cashierDeskService.payQuery(req);
        logger.info("trandNo:{}, 查询支付网关支付状态:{}", orderPayLog.getTrandNo(),
                cashierPayQueryResponse.toString());
        // 失败状态则直接返回
        if (cashierPayQueryResponse == null
                || cashierPayQueryResponse.getMsgCode() != CashierDeskConstant.SUCCESS_MSG_CODE) {
            logger.error("查询支付网关订单失败, trandNo:" + orderPayLog.getTrandNo());
            // 明确查询支付中失败才记录，0100，其他情况返回继续轮询
            if (null != cashierPayQueryResponse.getResult()
                    && "0100".equals(cashierPayQueryResponse.getResult().getCode())) {
                updateOrderPayLogToFail(orderPayLog, cashierPayQueryResponse);
            }
            return null;
        }
        return cashierPayQueryResponse;
    }


    private void updateOrderPayLogToFail(OrderPayLog orderPayLog,
            CashierPayQueryResponse cashierPayQueryResponse) {
        orderPayLog.setStatus(BookingConstants.BILL_LOG_FAIL);
        orderPayLog.setUpTime(new Date());
        String remark = "";
        if (null != cashierPayQueryResponse.getResult())
            remark = cashierPayQueryResponse.getResult().toString();
        orderPayLog.setRemark("主动查询支付中的状态:" + cashierPayQueryResponse.getMessage() + "," + remark);
        orderPayLogMapper.updateByPrimaryKeySelective(orderPayLog);
    }

    /**
     * 
     * @Title: doWithOrderPayLogAndOrder
     * @Description: 处理order_pay_log以及order
     * @param @param orderPayLog
     * @param @param bookingPayQueryVo
     * @param @throws Exception
     * @return void
     * @throws
     */
    public void doWithOrderPayLogAndOrder(OrderPayLog orderPayLog,
            BookingPayQueryVo bookingPayQueryVo){
        checkOrderPayLog(bookingPayQueryVo, orderPayLog);
        doSuccessOrderPayLog(bookingPayQueryVo, orderPayLog);
        Order order = orderMapper.selectByPrimaryKey(orderPayLog.getOrderId());
        // 查询订单状态
        checkOrderStatus(order, orderPayLog);
        // 更新订单状态
        doUpdateOrderStatus(order, bookingPayQueryVo);
        // 更新子订单状态
        orderSubService.doUpdateOrderSubStatusToPay(order);
    }



    private void checkOrderPayLog(BookingPayQueryVo bookingPayQueryVo, OrderPayLog orderPayLog) {
        // 判断是否已经处理
        if (orderPayLog.getStatus() != BookingConstants.BILL_LOG_NORMAL) {
            logger.warn("流水已经处理, trand_no：{}，status：{}", bookingPayQueryVo.getTradeNo(),
                    orderPayLog.getStatus());
            throw new RuntimeException("流水已经处理" + bookingPayQueryVo.getTradeNo());
        }

        if (bookingPayQueryVo.getAmount() == null
                || !bookingPayQueryVo.getAmount().equals(orderPayLog.getAmount())) {
            logger.warn("trand_no:{}, 流水金额和支付金额不对应，支付金额被篡改, orderMoney:{}, payMoney:{}",
                    orderPayLog.getTrandNo(), orderPayLog.getAmount(),
                    bookingPayQueryVo.getAmount());
            throw new RuntimeException("实付金额与网关金额对应不上");
        }
    }

    private void doSuccessOrderPayLog(BookingPayQueryVo bookingPayQueryVo, OrderPayLog orderPayLog) {

        orderPayLog.setUpTime(new Date());
        orderPayLog.setCurrencyDepositAmount(bookingPayQueryVo.getCurrencyDepositAmount());
        orderPayLog.setGatewayAmount(bookingPayQueryVo.getGatewayAmount());
        // 更新支付流水
        orderPayLog.setStatus(BookingConstants.BILL_LOG_SUCCESS);
        orderPayLog.setRemark(StringUtils.isNotBlank(orderPayLog.getRemark()) ? orderPayLog
                .getRemark() : "收银台支付回调成功");
        int row = orderPayLogMapper.updateByPrimaryKeySelective(orderPayLog);
        if (row < 1) {
            logger.warn("流水已经处理, trand_no：" + bookingPayQueryVo.getTradeNo());
            throw new RuntimeException("流水已经处理" + bookingPayQueryVo.getTradeNo());
        }
    }

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

    private void doUpdateOrderStatus(Order order, BookingPayQueryVo bookingPayQueryVo){
        Order updateOrder = new Order();
        updateOrder.setUpTime(new Date());
        updateOrder.setPayStatus(BookingResultCodeContants.PAY_STATUS_3);
        // 线下交易，则订单状态为已发货
        if (null != order.getOffline() && order.getOffline() == 1) {
            updateOrder.setPayStatus(BookingResultCodeContants.PAY_STATUS_4);
        }
        updateOrder.setPayTime(new Date());
        updateOrder.setCurrencyDepositAmount(bookingPayQueryVo.getCurrencyDepositAmount());
        updateOrder.setGatewayAmount(bookingPayQueryVo.getGatewayAmount());


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

}
