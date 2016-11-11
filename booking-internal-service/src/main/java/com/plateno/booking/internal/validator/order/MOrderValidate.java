package com.plateno.booking.internal.validator.order;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.base.model.SelectOrderParam;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.request.common.LstOrder;
import com.plateno.booking.internal.bean.request.custom.AddBookingParam;
import com.plateno.booking.internal.bean.request.custom.OrderParam;
import com.plateno.booking.internal.bean.response.custom.SelectOrderResponse;
import com.plateno.booking.internal.dao.pojo.BillDetails;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;

@Service
@ServiceErrorCode(BookingConstants.CODE_VERIFY_ERROR)
public class MOrderValidate {
	
	
	public ResultVo<LstOrder<SelectOrderResponse>> customQueryValidate(SelectOrderParam param,LstOrder<SelectOrderResponse> vos){
		ResultVo<LstOrder<SelectOrderResponse>> vo = new ResultVo<LstOrder<SelectOrderResponse>>();
		
		if(param.getPageNo()==null||param.getPageNo()==0){
			param.setPageNo(1);
		}
		
		if(param.getPageNumber()==null||param.getPageNumber()==0){
			param.setPageNumber(10);
		}
		
		int pageNo=1;
		pageNo=(param.getPageNo()-1)*param.getPageNumber();
		param.setPageNumber(param.getPageNumber());
		param.setPageNo(pageNo);
		return null;
	}
	
	public void checkBooking(AddBookingParam addBookingParam,ResultVo<Object> output){
		if (addBookingParam == null) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("下单失败,下单对象不能为空");
			return;
		}
		if (addBookingParam.getGoodsId() == null || addBookingParam.getGoodsId() <= 0) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("下单失败,商品ID不能为空");
			return;
		}
		if (addBookingParam.getVisitDate() == null || addBookingParam.getVisitDate() <= 0) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("下单失败,游玩日期不能为空");
			return;
		}
		if (addBookingParam.getQuantity() <= 0) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("下单失败,票数必须大于0");
			return;
		}
		if (addBookingParam.getTotalAmount() <= 0) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("下单失败,订单总价必须大于0");
			return;
		}
		if (addBookingParam.getPlatformId() <= 0) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("下单失败,平台来源,不能为空");
			return;
		}
		if (addBookingParam.getChannel() <= 0) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("下单失败,订单所属渠道不能为空");
			return;
		}
	}
	
	
	/**
	 * 校验删除订单信息
	 * 
	 * @param billDetails
	 * @param output
	 */
	public void checkDeleteOrder(Order order,ResultVo<Object> output){
		
		if (order == null){
			output.setResultCode(getClass(), MsgCode.SYSTEM_ORDER_NULL.getMsgCode());
			output.setResultMsg(MsgCode.SYSTEM_ORDER_NULL.getMessage());
			return;
		}
		if (!BookingResultCodeContants.ORDERS_STATUS.contains(order.getPayStatus())) {
			output.setResultCode(getClass(), MsgCode.SYSTEM_ORDER_STATUS_DELETE_ERROR.getMsgCode());
			output.setResultMsg(MsgCode.SYSTEM_ORDER_STATUS_DELETE_ERROR.getMessage());
			return;
		}
		if (order.getPayStatus().equals(9)) {
			output.setResultCode(getClass(), MsgCode.SYSTEM_ORDER_DELETE.getMsgCode());
			output.setResultMsg(MsgCode.SYSTEM_ORDER_DELETE.getMessage());
			return;
		}
	}
	
	
	/**
	 * 校验更新订单信息
	 * 
	 * @param billDetails
	 * @param output
	 */
	public void checkModifyOrder(Order order,ResultVo<Object> output){
		
		if (order == null){
			output.setResultCode(getClass(), MsgCode.SYSTEM_ORDER_NULL.getMsgCode());
			output.setResultMsg(MsgCode.SYSTEM_ORDER_NULL.getMessage());
			return;
		}
		if (BookingResultCodeContants.ORDERS_MODIFY_STATUS.contains(order.getPayStatus())) {
			output.setResultCode(getClass(), MsgCode.SYSTEM_ORDER_STATUS_ERROR.getMsgCode());
			output.setResultMsg(MsgCode.SYSTEM_ORDER_STATUS_ERROR.getMessage());
			return;
		}
	}
	
	/**
	 * 校验发货
	 * 
	 * @param billDetails
	 * @param output
	 */
	public void checkDeliverOrder(Order order,ResultVo<Object> output){
		if (order == null){
			output.setResultCode(getClass(), MsgCode.SYSTEM_ORDER_NULL.getMsgCode());
			output.setResultMsg(MsgCode.SYSTEM_ORDER_NULL.getMessage());
			return;
		}

		if (!BookingResultCodeContants.DELIVER_GOODS_STATUS.contains(order.getPayStatus())) {
			output.setResultCode(getClass(), MsgCode.SYSTEM_ORDER_DLIVER.getMsgCode());
			output.setResultMsg(MsgCode.SYSTEM_ORDER_DLIVER.getMessage());
			return;
		}
	}
	
	/**
	 * 校验取消订单信息
	 * 
	 * @param order
	 * @param output
	 */
	public void checkCancelOrder(Order order,ResultVo<Object> output){
		
		if (order == null){
			output.setResultCode(getClass(), MsgCode.SYSTEM_ORDER_NULL.getMsgCode());
			output.setResultMsg(MsgCode.SYSTEM_ORDER_NULL.getMessage());
			return;
		}
		if (BookingResultCodeContants.ORDERS_CANCEL_STATUS.contains(order.getPayStatus())) {
			output.setResultCode(getClass(), MsgCode.SYSTEM_ORDER_STATUS_CANCEL_ERROR.getMsgCode());
			output.setResultMsg(MsgCode.SYSTEM_ORDER_STATUS_CANCEL_ERROR.getMessage());
			return;
		}
		if (order.getPayStatus().equals(2)) {
			output.setResultCode(getClass(), MsgCode.SYSTEM_ORDER_CANCEL.getMsgCode());
			output.setResultMsg(MsgCode.SYSTEM_ORDER_CANCEL.getMessage());
			return;
		}
	}
	
	
	public void checkEnterReceiptStatus(Order order,ResultVo<Object> output){
		if (order == null){
			output.setResultCode(getClass(), MsgCode.SYSTEM_ORDER_NULL.getMsgCode());
			output.setResultMsg(MsgCode.SYSTEM_ORDER_NULL.getMessage());
			return;
		}
		if (!order.getPayStatus().equals(4)) {
				output.setResultCode(getClass(), MsgCode.SYSTEM_ORDER_ENTER.getMsgCode());
				output.setResultMsg(MsgCode.SYSTEM_ORDER_ENTER.getMessage());
				return;
		}
	}
	
	public void checkUserRefund(Order order,ResultVo<Object> output){
		if (order == null){
			output.setResultCode(getClass(), MsgCode.SYSTEM_ORDER_NULL.getMsgCode());
			output.setResultMsg(MsgCode.SYSTEM_ORDER_NULL.getMessage());
			return;
		}
		if (!order.getPayStatus().equals(3)) {
				output.setResultCode(getClass(), MsgCode.SYSTEM_ORDER_APPLICATION.getMsgCode());
				output.setResultMsg(MsgCode.SYSTEM_ORDER_APPLICATION.getMessage());
				return;
		}
	}
	public void checkAdminRefund(Order order,ResultVo<Object> output){
		if (order == null){
			output.setResultCode(getClass(), MsgCode.SYSTEM_ORDER_NULL.getMsgCode());
			output.setResultMsg(MsgCode.SYSTEM_ORDER_NULL.getMessage());
			return;
		}
		if (!order.getPayStatus().equals(6)) {
				output.setResultCode(getClass(), MsgCode.SYSTEM_ORDER_AUDI.getMsgCode());
				output.setResultMsg(MsgCode.SYSTEM_ORDER_AUDI.getMessage());
				return;
		}
	}
	
	
	
	
	
	/**
	 * 校验更新订单信息
	 * 
	 * @param billDetails
	 * @param output
	 */
	public void checkUpdateOrder(Order order,ResultVo<Object> output){
		
		if (order == null){
			output.setResultCode(getClass(), MsgCode.SYSTEM_ORDER_NULL.getMsgCode());
			output.setResultMsg(MsgCode.SYSTEM_ORDER_NULL.getMessage());
			return;
		}
		if (order.getPayStatus().equals(9)) {
			output.setResultCode(getClass(), MsgCode.SYSTEM_ORDER_DELETE.getMsgCode());
			output.setResultMsg(MsgCode.SYSTEM_ORDER_DELETE.getMessage());
			return;
		}
		/*if (!BookingResultCodeContants.READY_PAY_STATUS.contains(order.getPayStatus())) {
			output.setResultCode(getClass(), MsgCode.SYSTEM_ORDER_STATUS_ERROR.getMsgCode());
			output.setResultMsg("更新状态失败,账单目前所处状态不允许被更新,请核实信息");
			return;
		}*/
	}
	
	/**
	 * 校验重发短信信息
	 * 
	 * @param billDetails
	 * @param output
	 */
	public void checkSendMessage(BillDetails billDetails,ResultVo<Object> output){
		
		if (billDetails == null){
			output.setResultCode(getClass(), MsgCode.SYSTEM_ORDER_NULL.getMsgCode());
			output.setResultMsg(MsgCode.SYSTEM_ORDER_NULL.getMessage());
			return;
		}
		if (billDetails.getIsDelete().equals(1)) {
			output.setResultCode(getClass(), MsgCode.SYSTEM_ORDER_DELETE.getMsgCode());
			output.setResultMsg(MsgCode.SYSTEM_ORDER_DELETE.getMessage());
			return;
		}
		if (!billDetails.getStatus().equals(BookingResultCodeContants.PAY_STATUS_301)) {
			output.setResultCode(getClass(), MsgCode.SYSTEM_SEND_MESSAGE_STATUS_ERROR.getMsgCode());
			output.setResultMsg(MsgCode.SYSTEM_SEND_MESSAGE_STATUS_ERROR.getMessage());
			return;
		}
		if (!billDetails.getTicketStatus().equals(BookingResultCodeContants.TICKET_STATE_NOUSE) || billDetails.getTicketStatus().equals(BookingResultCodeContants.TICKET_STATE_USE)) {
			output.setResultCode(getClass(), MsgCode.SYSTEM_SEND_MESSAGE_TICKET_ERROR.getMsgCode());
			output.setResultMsg(MsgCode.SYSTEM_SEND_MESSAGE_TICKET_ERROR.getMessage());
			return;
		}
	}
	
	/**
	 * 校验退款规则信息
	 * 
	 * @param billDetails
	 * @param output
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void checkRefund(Order order,ResultVo output){
		
		if (order == null){
			output.setResultMsg("退款失败,订单编号所对应的账单信息不存在,请核实好信息.");
			return;
		}
		if (!order.getPayStatus().equals(BookingResultCodeContants.PAY_STATUS_6)) {
			output.setResultCode(getClass(), BookingConstants.CODE_601006);
			output.setResultMsg("退款失败,订单所处状态,不能进行退款服务");
			return;
		}
	}
	
	public void checkQueryOrderList(OrderParam orderParam , ResultVo<Object> output,List<String> tradeNos,List<String> orderNos){
		if (StringUtils.isNotBlank(orderParam.getTradeNo()) && StringUtils.isNotBlank(orderParam.getOrderNo())) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("请求参数有误,tradeNo和orderNo只能出现一个");
			return;
		}
		if (StringUtils.isBlank(orderParam.getTradeNo()) && StringUtils.isBlank(orderParam.getOrderNo())) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("请求参数有误,tradeNo和orderNo不能同时为空");
			return;
		}
		if (orderNos == null && tradeNos == null)
			return;
		if (StringUtils.isNotBlank(orderParam.getTradeNo())) {
			orderNos = null;
			tradeNos.add(orderParam.getTradeNo());
		}
		
		if (StringUtils.isNotBlank(orderParam.getOrderNo())) {
			tradeNos = null;
			orderNos.add(orderParam.getOrderNo());
		}
	}

}
