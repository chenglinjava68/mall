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

import com.plateno.booking.internal.base.mapper.OrderMapper;
import com.plateno.booking.internal.base.mapper.OrderPayLogMapper;
import com.plateno.booking.internal.base.model.NotifyReturn;
import com.plateno.booking.internal.base.model.bill.BillOrderDetail;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.base.pojo.OrderPayLog;
import com.plateno.booking.internal.base.pojo.OrderPayLogExample;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.contants.PayGateCode;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.common.util.number.StringUtil;
import com.plateno.booking.internal.common.util.redis.RedisUtils;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;

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
	
	@SuppressWarnings("unchecked")
	public ResultVo<Object> pullerPay(MOrderParam mOrderParam) throws OrderException, Exception{
		ResultVo<Object> output = new ResultVo<Object>();
		List<Order> listOrder=mallOrderMapper.getOrderByNoAndMemberId(mOrderParam.getOrderNo(), mOrderParam.getMemberId());
		if(CollectionUtils.isEmpty(listOrder)) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("订单查询失败,获取不到订单");
			return output;
		}
		
		//更新订单状态
		Order order = new Order();
		order.setPayStatus(BookingResultCodeContants.PAY_STATUS_11);
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

		//查询订单是否存在
		BillOrderDetail bill = this.getOrderNoByTradeNo(notifyReturn.getOrderNo());
		if (bill == null){
			logger.error("支付网关支付回调,获取不到对应的账单信息：" + notifyReturn.getOrderNo());
			throw new RuntimeException("找不到订单的信息");
		}
		
		//判断是否已经处理
		if(!BookingResultCodeContants.PAY_STATUS_11.equals(bill.getStatus())) {
			logger.info("订单已经处理, orderNo：" + notifyReturn.getOrderNo() + "，  status:" + bill.getStatus());
			return;
		}
		
		//支付成功
		if (PayGateCode.SUCCESS.equals(notifyReturn.getCode())) {
			
			logger.info("orderNo:{}, 支付成功", bill.getOrderNo());
			
			//更新订单状态
			Order order = new Order();
			order.setPayStatus(BookingResultCodeContants.PAY_STATUS_3);
			order.setUpTime(new Date());
			List<Integer> list = new ArrayList<>(1);
			list.add(BookingResultCodeContants.PAY_STATUS_11);
			int row = mOrderService.updateOrderStatusByNo(order, bill.getOrderNo(), list);
			//订单已经处理
			if(row < 1) {
				logger.info("订单已经处理, orderNo：" + notifyReturn.getOrderNo());
				return ;
			}
			
			//更新支付流水
			OrderPayLog record = new OrderPayLog();
			record.setStatus(BookingConstants.BILL_LOG_SUCCESS);
			record.setUpTime(new Date());
			record.setReferenceid(StringUtils.trimToEmpty(notifyReturn.getReferenceId()));
			record.setRemark("支付成功");
			OrderPayLogExample example = new OrderPayLogExample();
			example.createCriteria().andTrandNoEqualTo(notifyReturn.getOrderNo());
			orderPayLogMapper.updateByExampleSelective(record, example);
			
		} else if(PayGateCode.FAIL.equals(notifyReturn.getCode())) { //支付失败
			
			logger.info("orderNo:{}, 支付失败", bill.getOrderNo());
			
			//更新订单的状态,支付失败
			Order order = new Order();
			order.setPayStatus(BookingResultCodeContants.PAY_STATUS_1);
			order.setUpTime(new Date());
			List<Integer> list = new ArrayList<>(1);
			list.add(BookingResultCodeContants.PAY_STATUS_11);
			int row = mOrderService.updateOrderStatusByNo(order, bill.getOrderNo(), list);
			//订单已经处理
			if(row < 1) {
				logger.info("订单已经处理, orderNo：" + notifyReturn.getOrderNo());
				return ;
			}
			
			//更新支付流水
			OrderPayLog record = new OrderPayLog();
			record.setStatus(BookingConstants.BILL_LOG_FAIL);
			record.setUpTime(new Date());
			record.setReferenceid(StringUtils.trimToEmpty(notifyReturn.getReferenceId()));
			record.setRemark(String.format("支付失败:%s", notifyReturn.getMessage()));
			OrderPayLogExample example = new OrderPayLogExample();
			example.createCriteria().andTrandNoEqualTo(notifyReturn.getOrderNo());
			orderPayLogMapper.updateByExampleSelective(record, example);
		} else {
			logger.info(String.format("支付网关支付回调，非最终状态, orderNo:%s, code:%s", notifyReturn.getOrderNo(), notifyReturn.getCode()));
			throw new RuntimeException("支付网关支付回调，非最终状态");
		}
	}
	
	


}
