package com.plateno.booking.internal.service.order;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.plateno.booking.internal.base.model.NotifyReturn;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.base.pojo.OrderPayLog;
import com.plateno.booking.internal.base.pojo.OrderPayLogExample;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.contants.PayGateCode;
import com.plateno.booking.internal.bean.contants.ViewStatusEnum;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.bean.response.gateway.pay.PayQueryResponse;
import com.plateno.booking.internal.common.util.date.DateUtil;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.common.util.number.StringUtil;
import com.plateno.booking.internal.common.util.redis.RedisUtils;
import com.plateno.booking.internal.gateway.PaymentService;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
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
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public ResultVo<Object> pullerPay(MOrderParam mOrderParam) throws OrderException, Exception{
		ResultVo<Object> output = new ResultVo<Object>();
		List<Order> listOrder=mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(mOrderParam.getOrderNo(), mOrderParam.getMemberId(), mOrderParam.getChannelId());
		if(CollectionUtils.isEmpty(listOrder)) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("订单查询失败,获取不到订单");
			return output;
		}
		
		//更新订单状态
		Order order = new Order();
		order.setPayStatus(BookingResultCodeContants.PAY_STATUS_11);
		order.setPayType(mOrderParam.getPayType());
		//代发货和支付中才（多次拉起支付）允许更新订单
		List<Integer> list = new ArrayList<>();
		list.add(BookingResultCodeContants.PAY_STATUS_1);
		list.add(BookingResultCodeContants.PAY_STATUS_11);
		int row = mOrderService.updateOrderStatusByNo(order, mOrderParam.getOrderNo(), list);
		if(row < 1) {
			logger.info("orderNo:" + mOrderParam.getOrderNo() + ", 目前状态不允许拉起支付, status:" + listOrder.get(0).getPayStatus());
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("目前订单状态不允许支付");
			return output;
		}
		
		order=listOrder.get(0);
		OrderPayLog paylog=new OrderPayLog();
		paylog.setAmount(order.getPayMoney());
		paylog.setCreateTime(new Date());
		paylog.setOrderId(order.getId());
		paylog.setClientType(1);
		paylog.setTrandNo(StringUtil.getCurrentAndRamobe("L"));
		paylog.setReferenceid("");
		paylog.setStatus(1);
		paylog.setPoint(order.getPoint());
		paylog.setType(1);
		paylog.setUpTime(new Date());
		orderPayLogMapper.insert(paylog);
		
		orderLogService.saveGSOrderLog(order.getOrderNo(), PayStatusEnum.PAY_STATUS_11.getPayStatus(), PayStatusEnum.PAY_STATUS_11.getDesc(), "拉起支付", 0, ViewStatusEnum.VIEW_STATUS_PAYING.getCode());
		
		output.setData(paylog.getTrandNo());
		
		return output;
	}
	
	
	public com.plateno.booking.internal.base.model.bill.BillOrderDetail getOrderNoByTradeNo(String orderNo){
		return mallOrderMapper.getOrderNoByTradeNo(orderNo);
	}


	/**
	 * 支付网关回调，更新订单的状态
	 * @param notifyReturn
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
	public void payNotify(NotifyReturn notifyReturn) throws Exception {
		
		logger.info("支付网关回调:{}", JsonUtils.toJsonString(notifyReturn));
		
		OrderPayLog log = orderPayLogMapper.getByTrandNo(notifyReturn.getOrderNo());

		//查询订单是否存在
		if (log == null){
			logger.error("支付网关支付回调,获取不到对应的流水信息：" + notifyReturn.getOrderNo());
			throw new RuntimeException("找不到支付流水信息");
		}
		
		//判断是否已经处理
		if(log.getStatus() != 1) {
			logger.info("流水已经处理, trand_no：" + notifyReturn.getOrderNo() + "，  status:" + log.getStatus());
			return;
		}
		
		boolean success = false;
		

		OrderPayLogExample example = new OrderPayLogExample();
		example.createCriteria().andIdEqualTo(log.getId()).andStatusEqualTo(1);
		OrderPayLog record = new OrderPayLog();
		record.setUpTime(new Date());
		record.setReferenceid(StringUtils.trimToEmpty(notifyReturn.getReferenceId()));
		
		//支付成功
		if (PayGateCode.SUCCESS.equals(notifyReturn.getCode())) {
			
			if(notifyReturn.getOrderAmount() == null || !notifyReturn.getOrderAmount().equals(log.getAmount())) {
				logger.error("trand_no:{}, 流水金额和支付金额不对应，支付金额被篡改, orderMoney:{}, payMoney:{}", log.getTrandNo(), log.getAmount(), notifyReturn.getOrderAmount());
				return ;
			}
			
			logger.info("trand_no:{}, 支付成功", log.getTrandNo());
			
			success = true;
			
			//更新支付流水
			record.setStatus(BookingConstants.BILL_LOG_SUCCESS);
			record.setRemark("支付成功");
			int row = orderPayLogMapper.updateByExampleSelective(record, example);
			//订单已经处理
			if(row < 1) {
				logger.info("流水已经处理, trand_no：" + notifyReturn.getOrderNo());
				return ;
			}
			
		} else if(PayGateCode.FAIL.equals(notifyReturn.getCode())) { //支付失败
			
			logger.info("trand_no:{}, 支付失败", log.getTrandNo());
			
			//更新支付流水
			record.setStatus(BookingConstants.BILL_LOG_FAIL);
			record.setRemark(String.format("支付失败:%s", notifyReturn.getMessage()));
			int row = orderPayLogMapper.updateByExampleSelective(record, example);
			//订单已经处理
			if(row < 1) {
				logger.info("流水已经处理, trand_no：" + notifyReturn.getOrderNo());
				return ;
			}
			
		} else {
			logger.info(String.format("支付网关支付回调，非最终状态, orderNo:%s, code:%s", notifyReturn.getOrderNo(), notifyReturn.getCode()));
			throw new RuntimeException("支付网关支付回调，非最终状态");
		}
		
		Order order = (Order) orderMapper.selectByPrimaryKey(log.getOrderId());
		if(order == null) {
			logger.info("找不到对应的订单, orderId:{}", log.getOrderId());
			return;
		}
		
		if(order.getPayStatus() != PayStatusEnum.PAY_STATUS_11.getPayStatus()) {
			logger.info("订单状态非支付中, orderNo:{}， paystatus：{}", order.getOrderNo(), order.getPayStatus());
			return ;
		}
		
		Order updateOrder = new Order();
		updateOrder.setUpTime(new Date());

		if(success) {
			updateOrder.setPayStatus(BookingResultCodeContants.PAY_STATUS_3);
			updateOrder.setPayTime(new Date());
		} else {
			updateOrder.setPayStatus(BookingResultCodeContants.PAY_STATUS_1);
			updateOrder.setPayType(0); //支付方式设置成未支付
		}
		
		//更新订单状态
		List<Integer> list = new ArrayList<>(1);
		list.add(BookingResultCodeContants.PAY_STATUS_11);
		int row = mOrderService.updateOrderStatusByNo(updateOrder, order.getOrderNo(), list);
		
		if(row > 0) {
			orderLogService.saveGSOrderLog(order.getOrderNo(), updateOrder.getPayStatus(), PayStatusEnum.from(updateOrder.getPayStatus()).getDesc(), "支付网关回调：" + (success ? "支付成功" : "支付失败"), 0, success ? ViewStatusEnum.VIEW_STATUS_PAY_USE.getCode() : ViewStatusEnum.VIEW_STATUS_PAYING.getCode());
		}
		
		logger.info("更新订单状态, orderNo：{}, row:{}", order.getOrderNo(), row);
		
	}


	/**
	 * 处理支付中的流水
	 */
	public void handlePaying() {
		Date startTime = DateUtil.getDate(new Date(), -4, 0, 0, 0);
		Date endTime = DateUtil.getDate(new Date(), 0, 0, -5, 0);
		int num = 3000;
		List<OrderPayLog> list = orderPayLogMapper.queryPayingLog(startTime, endTime, num);
		for(OrderPayLog log : list) {
			try {
				handlePayingLog(log);
			} catch (Exception e) {
				logger.error("支付中流水处理失败:" + log.getTrandNo(), e);
			}
		}
	}
	
	/**
	 * 每一条支付中的流水处理
	 * @param log
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
	public void handlePayingLog(OrderPayLog log) throws Exception {
		logger.info("支付中订单处理，orderId:{}, trandNo:{}", log.getOrderId(), log.getTrandNo());
		
		//获取网关的订单状态
		PayQueryResponse response = paymentService.payOrderQuery(log.getTrandNo());
		
		logger.info("trandNo:{}, 查询支付网关支付状态:{}", log.getTrandNo(), JsonUtils.toJsonString(response));
		
		if (response == null || StringUtils.isBlank(response.getCode())) {
			logger.error("查询支付网关订单失败, trandNo:" + log.getTrandNo());
			return;
		}
		
		if(response.getCode().equals(PayGateCode.HADNLING) || response.getCode().equals(PayGateCode.PAY_HADNLING) || response.getCode().equals(PayGateCode.UNKNOWN_STATUS)) {
			logger.error(String.format("支付网关订单不是最终状态, trandNo:%s, code:%s", log.getTrandNo(), response.getCode()));
			return;
		}
		
		OrderPayLogExample example = new OrderPayLogExample();
		example.createCriteria().andIdEqualTo(log.getId()).andStatusEqualTo(1);
		OrderPayLog updateLog = new OrderPayLog();
		updateLog.setUpTime(new Date());
		updateLog.setReferenceid(StringUtils.trimToEmpty(response.getReferenceId()));
		
		boolean success = false;
		
		if(response.getCode().equals(BookingConstants.GATEWAY_PAY_SUCCESS_CODE)){
			
			//判断金额是否相等，防止支付时篡改
			if(response.getOrderAmount() == null || !response.getOrderAmount().equals(log.getAmount())) {
				logger.error("trandNo:{}, 订单金额和支付金额不对应，支付金额被篡改, orderMoney:{}, payMoney:{}", log.getTrandNo(), log.getAmount(), response.getOrderAmount());
				return ;
			}
			
			logger.info(String.format("trandNo:%s, 支付成功", log.getTrandNo()));
			
			//更新支付流水状态(success == 2)
			updateLog.setStatus(BookingConstants.BILL_LOG_SUCCESS);
			updateLog.setRemark("支付成功");
			int row = orderPayLogMapper.updateByExampleSelective(updateLog, example);
			
			success = true;
			
			if(row < 0) {
				logger.info("trandNo:{}, 流水已经更新", log.getTrandNo());
				return;
			}
			
		}else{
			logger.info(String.format("trandNo:%s, 支付失败", log.getTrandNo()));
			
			//更新支付流水状态(fail == 3)
			updateLog.setStatus(BookingConstants.BILL_LOG_FAIL);
			updateLog.setRemark(String.format("支付失败:%s", response.getMessage()));
			int row = orderPayLogMapper.updateByExampleSelective(updateLog, example);
			
			if(row < 0) {
				logger.info("trandNo:{}, 流水已经更新", log.getTrandNo());
				return;
			}
		}
		
		Order order = (Order) orderMapper.selectByPrimaryKey(log.getOrderId());
		if(order == null) {
			logger.info("找不到对应的订单, orderId:{}", log.getOrderId());
			return;
		}
		
		if(order.getPayStatus() != PayStatusEnum.PAY_STATUS_11.getPayStatus()) {
			logger.info("订单状态非支付中, orderId:{}， paystatus：{}", log.getOrderId(), order.getPayStatus());
			return ;
		}
		
		//更新订单
		Order record = new Order();
		record.setUpTime(new Date());
		if(success){
			record.setPayTime(new Date());
			record.setPayStatus(BookingResultCodeContants.PAY_STATUS_3);
			//orderLogService.saveGSOrderLog(order.getOrderNo(), BookingResultCodeContants.PAY_STATUS_3, "网关支付成功", "网关支付成功",order.getChanelid(),ViewStatusEnum.VIEW_STATUS_WATIDELIVER.getCode(),"扫单job维护");
		}else{
			record.setPayStatus(BookingResultCodeContants.PAY_STATUS_1);
			record.setPayType(0); //支付方式设置为未支付
			//orderLogService.saveGSOrderLog(order.getOrderNo(), BookingConstants.PAY_STATUS_1, "网关支付失败", "网关支付失败",order.getChanelid(),ViewStatusEnum.VIEW_STATUS_PAYFAIL.getCode(),"扫单job维护");
		}
		//更新账单状态
		List<Integer> list = Arrays.asList(PayStatusEnum.PAY_STATUS_11.getPayStatus());
		Integer row = mOrderService.updateOrderStatusByNo(record, order.getOrderNo(), list);
		
		if(row > 0) {
			orderLogService.saveGSOrderLog(order.getOrderNo(), record.getPayStatus(), PayStatusEnum.from(record.getPayStatus()).getDesc(), "支付网关支付主动同步：" + (success ? "支付成功" : "支付失败"), 0, success ? ViewStatusEnum.VIEW_STATUS_PAY_USE.getCode() : ViewStatusEnum.VIEW_STATUS_PAYING.getCode());
		}

		logger.info("订单更新结果, orderNo:{}, row:{}", order.getOrderNo(), row);
	}
}
