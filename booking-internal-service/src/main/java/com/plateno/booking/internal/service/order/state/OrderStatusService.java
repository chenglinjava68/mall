package com.plateno.booking.internal.service.order.state;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;



/**
 * 状态模式 ==> 进化(策略模式)
 * 
 * @author user
 *
 */
@Service
public class OrderStatusService {
	private final int ORDER_STATUS_0 = 0; // 待支付：创建订单成功，合作方尚未付款。

	private final int ORDER_STATUS_10 = 10; // 待确认：支付订单成功，要出发确认流程中

	private final int ORDER_STATUS_11 = 11; // 待确认（申请取消）：合作方申请取消，要出发在审核状态

	private final int ORDER_STATUS_20 = 20; // 待出行：要出发已确认订单，客人可出行消费

	private final int ORDER_STATUS_30 = 30; // 已消费：客人已消费订单

	private final int ORDER_STATUS_40 = 40; // 已取消：订单已取消

	@Autowired
	public TaskExecutor taskExecutor;

	//private OrderParam orderParam = new OrderParam();
	
/*	public Boolean handle(YcfOrderStatusRequest orderStatus) {
		Boolean success = false;
		switch (orderStatus.getOrderStatus()) {
		case ORDER_STATUS_0:
			//无作为,不处理
			break;
		case ORDER_STATUS_10:
			//无作为,不处理
			break;

		case ORDER_STATUS_11:
			//无作为,不处理
			break;

		case ORDER_STATUS_20:
			// 1、申请取消【要出发】拒绝，推送20状态，不做处理。 2、出票中303状态才做处理
			BillDetails billDetails = orderService.getBillByOrderNo(orderStatus.getPartnerOrderId());
			if (billDetails != null  && billDetails.getStatus().equals(BookingResultCodeContants.PAY_STATUS_400))
				break;
			
			MBill bill = new MBill();
			bill.setStatus(BookingConstants.PAY_STATUS_301);
			MOrder order = new MOrder();
			order.setTicketStatus(2);
			billService.updateBillByTradeNo(billDetails.getTradeNo(), bill, billDetails.getOrderNo(), order);
			orderParam.setChannel(billDetails.getChannel());
			orderParam.setTradeNo(billDetails.getTradeNo());
			//发送短信
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					try {
						ResultVo output = new ResultVo();
						apiService.SendMessageService(orderParam, output);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			success = true;
			break;

		case ORDER_STATUS_30:
			updateOrder(orderStatus.getPartnerOrderId());
			success = true;
			break;

		case ORDER_STATUS_40:
			//无作为,不处理
			return false;

		default:
			//无作为,不处理
			return false;
		}
		return success;
	}
	
	private void updateOrder(String orderNo){
		MOrderExample example = new MOrderExample();
		example.createCriteria().andOrderNoEqualTo(orderNo);
		MOrder order = new MOrder();
		order.setTicketStatus(3);
		orderService.updateByExampleSelective(order, example);
	}*/
	

}
