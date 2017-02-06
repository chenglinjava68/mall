package com.plateno.booking.internal.job.order.abnormalSweepJob.service;


import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.base.constant.PayStatusEnum;
import com.plateno.booking.internal.base.mapper.OrderMapper;
import com.plateno.booking.internal.base.mapper.OrderPayLogMapper;
import com.plateno.booking.internal.base.mapper.OrderProductMapper;
import com.plateno.booking.internal.base.mapper.SmsLogMapper;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.base.pojo.OrderPayLog;
import com.plateno.booking.internal.base.pojo.OrderPayLogExample;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.gateway.PaymentService;
import com.plateno.booking.internal.goods.MallGoodsService;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.member.PointService;
import com.plateno.booking.internal.service.log.OrderLogService;
import com.plateno.booking.internal.service.order.MOrderService;
import com.plateno.booking.internal.service.order.OrderCancelService;
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

	@Autowired
	private OrderCancelService orderCancelService;
	
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
			ResultVo<Object> result = orderCancelService.cancelOrderLock(orderParam);

			logger.info("待付款取消订单结果, orderNo:{}, result:{}", order.getOrderNo(), result);
		}
		
		logger.info("处理代付款订单结束");
		
		logger.info("处理支付中到取消订单开始...");
		
		//超过30分钟，且5分钟内没有支付流水，都改成已经取消
		List<Order> payList = orderMapper.getPre30Min(PayStatusEnum.PAY_STATUS_11.getPayStatus());
		for(Order order : payList) {
			
			logger.info("支付中-->取消， orderNo:{}", order.getOrderNo());
			
			OrderPayLogExample example = new OrderPayLogExample();
			example.createCriteria().andOrderIdEqualTo(order.getId()).andTypeEqualTo(1).andStatusEqualTo(1);
			List<OrderPayLog> logList = orderPayLogMapper.selectByExample(example );
			boolean hasPaying = false;
			for(OrderPayLog log : logList) {
				if(new Date().getTime() - log.getCreateTime().getTime() < 1000*5*60) {
					hasPaying = true;
					logger.info("订单状态支付中，但是5分钟内有支付中的支付流水， orderNo:{}, trandNo:{}", order.getOrderNo(), log.getTrandNo());
					break;
				}
			}
			
			//取消订单
			if(!hasPaying) {
				MOrderParam orderParam = new MOrderParam();
				orderParam.setOrderNo(order.getOrderNo());
				orderParam.setMemberId(order.getMemberId());
				orderParam.setType(1);//超时取消
				ResultVo<Object> result = orderCancelService.cancelOrderLock(orderParam);
				logger.info("支付中取消订单结果, orderNo:{}, result:{}", order.getOrderNo(), result);
			}
		}
		
		logger.info("处理支付中到取消订单结束");
	}
	
}
