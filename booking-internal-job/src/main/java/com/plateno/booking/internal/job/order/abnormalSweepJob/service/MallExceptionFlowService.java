package com.plateno.booking.internal.job.order.abnormalSweepJob.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.base.mapper.OrderMapper;
import com.plateno.booking.internal.base.mapper.OrderPayLogMapper;
import com.plateno.booking.internal.base.mapper.OrderProductMapper;
import com.plateno.booking.internal.base.mapper.SmsLogMapper;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.gateway.PaymentService;
import com.plateno.booking.internal.goods.MallGoodsService;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.member.PointService;
import com.plateno.booking.internal.service.log.OrderLogService;
import com.plateno.booking.internal.service.order.MOrderService;
import com.plateno.booking.internal.sms.SMSSendService;

@Service
public class MallExceptionFlowService {
	protected final static Logger logger = LoggerFactory.getLogger(MallExceptionFlowService.class);
	

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
	private TaskExecutor taskExecutor;
	
	@Autowired
	private SMSSendService sendService;
	
	@Autowired
	private SmsLogMapper smsLogMapper;
	
	@Autowired
	private MOrderService mOrderService;

	
	@SuppressWarnings("unchecked")
	public void handleException() throws Exception {
		
		logger.info("处理已发货订单开始...");
		
		//查询已发货的订单，如果大于15天则更新为已收货4==>5
		List<Order> orderEList=orderMapper.getOrderByStatusAndDeliverTime(BookingResultCodeContants.PAY_STATUS_4, 15);
		for(Order order:orderEList){
			mOrderService.handleReceiveGoods(order.getOrderNo());
		}
		
		logger.info("处理已发货订单结束");
		
		logger.info("处理未支付订单开始...");
		
		//超过30分钟未支付 ==> 1 --> 2
		List<Order> orderList=orderMapper.getPre30Min(BookingResultCodeContants.PAY_STATUS_1);
		for(Order order:orderList){
			
			logger.info(String.format("未支付 -->取消, orderNo:%s", order.getOrderNo()));
			
			/*order.setPayStatus(BookingResultCodeContants.PAY_STATUS_2);
			order.setUpTime(new Date());
			orderMapper.updateByPrimaryKeySelective(order);
			
			returnPoint(order);
			
			//退还库存
			calcelOrderReturnSku(order.getOrderNo());
			
			orderLogService.saveGSOrderLog(order.getOrderNo(), BookingConstants.PAY_STATUS_2, "已取消", "订单取消成功",0,ViewStatusEnum.VIEW_STATUS_CANNEL.getCode(),"扫单job维护");*/
			
			MOrderParam orderParam = new MOrderParam();
			orderParam.setOrderNo(order.getOrderNo());
			orderParam.setMemberId(order.getMemberId());
			orderParam.setType(1);//超时取消
			ResultVo<Object> result = orderService.cancelOrderLock(orderParam);

			logger.info("取消订单结果, orderNo:{}, result:{}", order.getOrderNo(), result);
		}
		
		logger.info("处理未支付订单结束");
	}
	
}
