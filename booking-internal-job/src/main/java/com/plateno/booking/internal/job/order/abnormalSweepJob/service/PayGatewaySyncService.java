package com.plateno.booking.internal.job.order.abnormalSweepJob.service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plateno.booking.internal.base.mapper.OrderMapper;
import com.plateno.booking.internal.base.mapper.OrderPayLogMapper;
import com.plateno.booking.internal.base.mapper.OrderProductMapper;
import com.plateno.booking.internal.base.mapper.SmsLogMapper;
import com.plateno.booking.internal.base.model.SelectOrderParam;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.base.pojo.OrderPayLog;
import com.plateno.booking.internal.base.pojo.OrderPayLogExample;
import com.plateno.booking.internal.base.pojo.OrderProduct;
import com.plateno.booking.internal.base.pojo.OrderProductExample;
import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.bean.contants.PayGateCode;
import com.plateno.booking.internal.bean.contants.ViewStatusEnum;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.point.ValueBean;
import com.plateno.booking.internal.bean.response.gateway.pay.PayQueryResponse;
import com.plateno.booking.internal.bean.response.gateway.refund.RefundQueryResponse;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.email.model.RefundSuccessContent;
import com.plateno.booking.internal.email.service.PhoneMsgService;
import com.plateno.booking.internal.gateway.PaymentService;
import com.plateno.booking.internal.goods.MallGoodsService;
import com.plateno.booking.internal.member.PointService;
import com.plateno.booking.internal.service.log.OrderLogService;
import com.plateno.booking.internal.service.order.MOrderService;
import com.plateno.booking.internal.sms.SMSSendService;

/**
 * 支付网关同步
 * @author mogt
 * @date 2016年11月14日
 */
@Service
public class PayGatewaySyncService {
	protected final static Logger logger = LoggerFactory.getLogger(PayGatewaySyncService.class);
	

	@Autowired
	private OrderLogService orderLogService;

	@Autowired
	private PaymentService paymentService;
	
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired 
	private MOrderService  orderService;
	
	@Autowired 
	private PointService  pointService;
	
	@Autowired
	private OrderPayLogMapper orderPayLogMapper;
	
	@Autowired
	private MallGoodsService mallGoodsService;
	
	@Autowired
	private OrderProductMapper orderProductMapper;
	
	@Autowired
	private SMSSendService sendService;
	
	@Autowired
	private SmsLogMapper smsLogMapper;
	
	@Autowired
	private PhoneMsgService phoneMsgService;

	/**
	 * 同步支付中和退款中的订单状态
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void sync() throws Exception {
		
		logger.info("处理退款中订单开始...");
		
		SelectOrderParam selectOrderParam = new SelectOrderParam();
		selectOrderParam.setPageNo(0);
		selectOrderParam.setPageNumber(3000);
		selectOrderParam.setPayStatus(BookingResultCodeContants.PAY_STATUS_10);
		//退款中的订单
		List<Order> orderTList=orderMapper.getPageOrders(selectOrderParam );
		handleEach(orderTList);
		
		logger.info("处理退款中订单结束");
		
		logger.info("处理支付中订单开始...");
		
		//支付中的订单

		List<Order> orderPayingList=orderMapper.getPayingAndPayLogPre5Min(BookingResultCodeContants.PAY_STATUS_11);
		handleEach(orderPayingList);
		
		logger.info("处理支付中订单结束");
		
		logger.info("处理退款中订单开始...");
	}
	
	private void handleEach(List<Order> listOrder)throws Exception{
		for(Iterator<Order> iter=listOrder.iterator();iter.hasNext();){
			Order order = (Order)iter.next();
			Integer status=order.getPayStatus();
			switch(status){
			
			//处理账单退款中状态：10,验证网关退款查询接口 ==> 7/13
			case 10:
				handleGateWayefund(order);
				break;
			
			//处理支付中账单状态：11,验证网关支付查询接口==>3/12
			case 11:
					handlePaying(order);
				break;
			
			}
		}
	}

	/**
	 * 物理确认订单的最终状态
	 * 
	 * @param mbill
	 * @param status
	 * @return
	 */
	private boolean validate(Order order,Integer status){
		Order mbills= (Order) orderMapper.selectByPrimaryKey(order.getId());
		if(mbills==null) 
			return false;
		if(!mbills.getPayStatus().equals(status)) 
			return false;
		return true;
	}
	

	/**
	 * 退款处理
	 * @param order
	 * @throws Exception
	 */
	@Transactional(rollbackFor=OrderException.class)
	public void handleGateWayefund(Order order)throws Exception{
		if(!validate(order,BookingConstants.PAY_STATUS_10)) 
			return ;
		
		logger.info(String.format("退款中订单处理开始, orderNo:%s", order.getOrderNo()));
		
		OrderPayLogExample example=new OrderPayLogExample();
		example.createCriteria().andOrderIdEqualTo(order.getId()).andTypeEqualTo(2);
		List<OrderPayLog> listpayLog=orderPayLogMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(listpayLog))		return;
		
		boolean success = false;
		boolean fail = false;
		for(OrderPayLog orderPayLog:listpayLog){
			
			//获取网关的订单状态
			RefundQueryResponse response = paymentService.refundOrderQuery(orderPayLog.getTrandNo());
			
			logger.info(String.format("orderNo:%s, 查询退款状态，返回：%s", order.getOrderNo(), JsonUtils.toJsonString(response)));
			
			if (response == null || StringUtils.isBlank(response.getCode())) {
				logger.error("查询支付网关订单失败, trandNo:" + orderPayLog.getTrandNo());
				return;
			}
			
			if(response.getCode().equals(PayGateCode.HADNLING) || response.getCode().equals(PayGateCode.PAY_HADNLING)) {
				logger.error(String.format("退款支付网关订单支付中, trandNo:%s, code:%s", orderPayLog.getTrandNo(), response.getCode()));
				return;
			}
			
			example=new OrderPayLogExample();
			example.createCriteria().andIdEqualTo(orderPayLog.getId());
			
			if(response.getCode().equals(BookingConstants.GATEWAY_REFUND_SUCCESS_CODE)){ //退款成功
				
				logger.info(String.format("orderNo:%s, 退款成功", order.getOrderNo()));
				
				//更新支付流水状态(success == 2)
				OrderPayLog record=new OrderPayLog();
				record.setStatus(BookingConstants.BILL_LOG_SUCCESS);
				//record.setRemark("退款成功");
				record.setUpTime(new Date());
				record.setReferenceid(StringUtils.trimToEmpty(response.getReferenceId()));
				orderPayLogMapper.updateByExampleSelective(record, example);
				
				success=true;
			}else if((response.getCode().equals(PayGateCode.REFUND_FAIL) || response.getCode().equals(PayGateCode.REQUEST_EXCEPTION))){ //退款失败
				
				logger.info(String.format("orderNo:%s, 退款失败", order.getOrderNo()));
				
				//更新支付流水状态(fail == 3)
				OrderPayLog record=new OrderPayLog();
				//record.setRemark(String.format("退款失败:%s", response.getMessage()));
				record.setStatus(BookingConstants.BILL_LOG_FAIL);
				record.setUpTime(new Date());
				record.setReferenceid(StringUtils.trimToEmpty(response.getReferenceId()));
				orderPayLogMapper.updateByExampleSelective(record, example);
				
				fail = true;
			}
		}
		
		Order record = new Order();
		record.setRefundSuccesstime(new Date());
		String orderNo = order.getOrderNo();
		if(success){
			
			logger.info(String.format("orderNo:%s, 退款成功", order.getOrderNo()));
			
			record.setPayStatus(BookingResultCodeContants.PAY_STATUS_7);
			orderLogService.saveGSOrderLog(orderNo, BookingResultCodeContants.PAY_STATUS_7, "网关退款成功", "网关退款成功",order.getChanelid(),ViewStatusEnum.VIEW_STATUS_REFUND.getCode(),"扫单job维护");
			//更新账单状态
			orderService.updateOrderStatusByNo(record, orderNo);
			
			//退款归还下单积分
			logger.info("orderNo:{}， 退还积分，point:{}", orderNo, order.getRefundPoint());
			returnPoint(order);
			
			OrderProduct productByOrderNo = getProductByOrderNo(orderNo);
			if(productByOrderNo == null) {
				logger.error(String.format("orderNo:%s, 退款退库存失败, 找不到购买的商品信息", orderNo));
			} else {
				//更新库存
				logger.info(String.format("orderNo:%s， 退还库存，skuid:%s, count:%s", orderNo, productByOrderNo.getSkuid(), productByOrderNo.getSkuCount()));
				boolean modifyStock = mallGoodsService.modifyStock(productByOrderNo.getSkuid().toString(), productByOrderNo.getSkuCount());
				if(!modifyStock){
					logger.error(String.format("orderNo:%s, 调用商品服务失败", orderNo));
					LogUtils.sysLoggerInfo(String.format("orderNo:%s, 调用商品服务失败", orderNo));
				}
				
				final Order dbOrder = order;
				final OrderProduct product = productByOrderNo;
				//发送退款短信
				/*taskExecutor.execute(new Runnable() {
					@Override
					public void run() {
						
						logger.info("发送退款成功短信:{}", dbOrder.getOrderNo());
						
						SmsMessageReq messageReq = new SmsMessageReq();
						Map<String, String> params = new HashMap<String, String>();
						if(dbOrder.getPoint() > 0){
							messageReq.setPhone(dbOrder.getMobile());
							params.put("orderCode", dbOrder.getOrderNo());
							params.put("name", product.getProductName());
							params.put("money", dbOrder.getPayMoney()+"");
							params.put("jf",dbOrder.getPoint()+"");
							messageReq.setType(Integer.parseInt(Config.SMS_SERVICE_TEMPLATE_NINE));
						}else{
							params.remove("jf");
							messageReq.setType(Integer.parseInt(Config.SMS_SERVICE_TEMPLATE_EIGHT));
						}
						messageReq.setParams(params);
						Boolean res=sendService.sendMessage(messageReq);
						
						logger.info("发送退款成功短信:{}, res:{}", dbOrder.getOrderNo(), res);
						
						//记录短信日志
						SmsLog smslog=new SmsLog();
						smslog.setCreateTime(new Date());
						smslog.setIsSuccess(res==true?1:0);
						smslog.setContent(product.getProductName());
						smslog.setObjectNo(dbOrder.getOrderNo());
						smslog.setPhone(dbOrder.getMobile());
						smslog.setUpdateTime(new Date());
						smsLogMapper.insertSelective(smslog);
					}
				});*/
				
				String templateId;
				RefundSuccessContent content = new RefundSuccessContent();
				content.setObjectNo(dbOrder.getOrderNo());
				content.setOrderCode(dbOrder.getOrderNo());
				content.setMoney(new BigDecimal(dbOrder.getPayMoney()).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_DOWN).toString());
				content.setName(product.getProductName());
				if(dbOrder.getRefundPoint() > 0){
					content.setJf(dbOrder.getRefundPoint() + "");
					templateId = Config.SMS_SERVICE_TEMPLATE_NINE;
				}else{
					templateId = Config.SMS_SERVICE_TEMPLATE_EIGHT;
				}
				phoneMsgService.sendPhoneMessageAsync(dbOrder.getMobile(), templateId, content);
			}
		}else if(fail){
			
			logger.info(String.format("orderNo:%s, 退款失败", order.getOrderNo()));
			
			record.setPayStatus(BookingResultCodeContants.PAY_STATUS_13);
			record.setRefundFailReason("网关退款失败");
			orderLogService.saveGSOrderLog(orderNo, BookingConstants.PAY_STATUS_13, "网关退款失败", "网关退款失败",order.getChanelid(),ViewStatusEnum.VIEW_STATUS_REFUND_FAIL.getCode(),"扫单job维护");
			//更新账单状态
			orderService.updateOrderStatusByNo(record, orderNo);
		}
	  }


	/**
	 * 返还积分
	 * @param order
	 */
	private void returnPoint(Order order) {
		if(order.getPoint()>0){
			ValueBean vb=new ValueBean();
			vb.setPointvalue(order.getRefundPoint());
			vb.setMebId(order.getMemberId());
			vb.setTrandNo(order.getOrderNo());
			vb.setChannelid(order.getChanelid());
			pointService.mallAddPoint(vb);
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
	 * 支付中处理
	 * @param order
	 * @throws Exception
	 */
	@Transactional(rollbackFor=OrderException.class)
	public void handlePaying(Order order)throws Exception{
		if(!validate(order,BookingConstants.PAY_STATUS_11)) 
			return ;
		
		logger.info(String.format("支付中订单处理开始, orderNo:%s", order.getOrderNo()));
		
		OrderPayLogExample example=new OrderPayLogExample();
		example.createCriteria().andOrderIdEqualTo(order.getId()).andTypeEqualTo(1);
		List<OrderPayLog> listpayLog=orderPayLogMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(listpayLog))	{
			logger.error("订单状态异常, 订单状态支付中，但是找不到支付流水, orderNo:" + order.getOrderNo());
			return;
		}
		
		boolean success = false;
		for(OrderPayLog orderPayLog:listpayLog){
			
			//获取网关的订单状态
			PayQueryResponse response = paymentService.payOrderQuery(orderPayLog.getTrandNo());
			
			logger.info("orderNo:{}, 查询支付网关支付状态:{}", order.getOrderNo(), JsonUtils.toJsonString(response));
			
			if (response == null || StringUtils.isBlank(response.getCode())) {
				logger.error("查询支付网关订单失败, trandNo:" + orderPayLog.getTrandNo());
				return;
			}
			
			if(response.getCode().equals(PayGateCode.HADNLING) || response.getCode().equals(PayGateCode.PAY_HADNLING) || response.getCode().equals(PayGateCode.UNKNOWN_STATUS)) {
				logger.error(String.format("支付网关订单不是最终状态, trandNo:%s, code:%s", orderPayLog.getTrandNo(), response.getCode()));
				return;
			}
			
			example = new OrderPayLogExample();
			example.createCriteria().andIdEqualTo(orderPayLog.getId());
				
			if(response.getCode().equals(BookingConstants.GATEWAY_PAY_SUCCESS_CODE)){
				
				//判断金额是否相等，防止支付时篡改
				if(response.getOrderAmount() == null || !response.getOrderAmount().equals(orderPayLog.getAmount())) {
					logger.error("orderNo:{}, 订单金额和支付金额不对应，支付金额被篡改, orderMoney:{}, payMoney:{}", orderPayLog.getTrandNo(), orderPayLog.getAmount(), response.getOrderAmount());
					return ;
				}
				
				logger.info(String.format("orderNo:%s, 支付成功", order.getOrderNo()));
				//更新支付流水状态(success == 2)
				OrderPayLog record=new OrderPayLog();
				record.setStatus(BookingConstants.BILL_LOG_SUCCESS);
				record.setRemark("支付成功");
				record.setUpTime(new Date());
				record.setReferenceid(StringUtils.trimToEmpty(response.getReferenceId()));
				orderPayLogMapper.updateByExampleSelective(record, example);
				
				success=true;
			}else{
				logger.info(String.format("orderNo:%s, 支付失败", order.getOrderNo()));
				//更新支付流水状态(fail == 3)
				OrderPayLog record=new OrderPayLog();
				record.setStatus(BookingConstants.BILL_LOG_FAIL);
				record.setUpTime(new Date());
				record.setReferenceid(StringUtils.trimToEmpty(response.getReferenceId()));
				record.setRemark(String.format("支付失败:%s", response.getMessage()));
				orderPayLogMapper.updateByExampleSelective(record, example);
			}
		}
		
		Order record = new Order();
		if(success){
			record.setPayStatus(BookingResultCodeContants.PAY_STATUS_3);
			orderLogService.saveGSOrderLog(order.getOrderNo(), BookingResultCodeContants.PAY_STATUS_3, "网关支付成功", "网关支付成功",order.getChanelid(),ViewStatusEnum.VIEW_STATUS_WATIDELIVER.getCode(),"扫单job维护");
		}else{
			record.setPayStatus(BookingResultCodeContants.PAY_STATUS_1);
			orderLogService.saveGSOrderLog(order.getOrderNo(), BookingConstants.PAY_STATUS_1, "网关支付失败", "网关支付失败",order.getChanelid(),ViewStatusEnum.VIEW_STATUS_PAYFAIL.getCode(),"扫单job维护");
		}
		//更新账单状态
		orderService.updateOrderStatusByNo(record, order.getOrderNo());
	  }
	

}
