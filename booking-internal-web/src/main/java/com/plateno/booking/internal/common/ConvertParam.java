package com.plateno.booking.internal.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.bean.request.convert.ConvertOrderParam;
import com.plateno.booking.internal.bean.request.convert.ConvertRefundDetailParam;
import com.plateno.booking.internal.bean.request.gateway.PayNotifyReturnParam;
import com.plateno.booking.internal.bean.request.lvmama.order.PushOrderInfo;
import com.plateno.booking.internal.bean.request.lvmama.refund.OrderRefundResponse;
import com.plateno.booking.internal.bean.request.tc.order.TCRefundBody;
import com.plateno.booking.internal.bean.request.ycf.YcfRefundNoticeRequest;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.common.util.number.AmountUtils;
import com.plateno.booking.internal.common.util.xml.JaxbUtils;

public class ConvertParam {
	
	/**
	 * 同程退款bean ==> 通用退款bean
	 * 
	 * @param body
	 * @return
	 */
	public static ConvertRefundDetailParam convertTCRefundObject(TCRefundBody body){
		ConvertRefundDetailParam convertRefundDetailParam = new ConvertRefundDetailParam();
		if (body == null) {
			convertRefundDetailParam = null;
			return convertRefundDetailParam;
		}
		if (body.getReturnCode().equals(BookingConstants.TC_SUCCESS_CODE.toString())) {
			convertRefundDetailParam.setCode(1);
		}else{
			convertRefundDetailParam.setCode(0);
		}
		convertRefundDetailParam.setRefundReason(body.getReturnMsg());
		convertRefundDetailParam.setPartnerOrderId(body.getSerialId());
		convertRefundDetailParam.setRefundCharge(Double.valueOf(body.getPoundageAmount() * 100).intValue());
		convertRefundDetailParam.setRefundPrice(Double.valueOf(body.getRefundAmount() * 100).intValue());
		
		return convertRefundDetailParam;
	}
	
	
	/**
	 * 驴妈妈退款bean ==> 通用退款bean
	 * 
	 * @param request
	 * @return
	 */
	public static ConvertRefundDetailParam convertLvMaMaRefundObject(HttpServletRequest request){
		ConvertRefundDetailParam convertRefundDetailParam = new ConvertRefundDetailParam();
		String orderParam = request.getParameter("order").toString();
		LogUtils.sysErrorLoggerInfo("驴妈妈退款回调参数：" + orderParam);
		if (StringUtils.isBlank(orderParam)) {
			return null;
		}
		OrderRefundResponse orderRefundResponse = JaxbUtils.convery2Bean(orderParam, OrderRefundResponse.class);
		if (orderRefundResponse == null) {
			convertRefundDetailParam = null;
			return convertRefundDetailParam;
		}
		if (orderRefundResponse.getBody().getOrder().getRequestStatus().equals(BookingConstants.PASS)) {
			convertRefundDetailParam.setCode(1);
		}else if(orderRefundResponse.getBody().getOrder().getRequestStatus().equals(BookingConstants.REJECT)){
			convertRefundDetailParam.setCode(0);
		}
		convertRefundDetailParam.setRefundReason(orderRefundResponse.getBody().getOrder().getRefundInfo());
		convertRefundDetailParam.setPartnerOrderId(orderRefundResponse.getBody().getOrder().getOrderId());
		convertRefundDetailParam.setRefundCharge(orderRefundResponse.getBody().getOrder().getFactorage());
		convertRefundDetailParam.setRefundPrice(orderRefundResponse.getBody().getOrder().getRefundAmount());
		return convertRefundDetailParam;
	}
	
	/**
	 * 要出发退款bean ==> 通用退款bean
	 * 
	 * @param request
	 * @return
	 */
	public static ConvertRefundDetailParam convertYaoChuFaRefundObject(YcfRefundNoticeRequest refundNotice){
		ConvertRefundDetailParam convertRefundDetailParam = new ConvertRefundDetailParam();
		convertRefundDetailParam.setRefundReason(refundNotice.getRefundReason());
		convertRefundDetailParam.setRemark(refundNotice.getHandleRemark());
		convertRefundDetailParam.setRefundPrice(AmountUtils.changeY2FInt(refundNotice.getRefundPrice().doubleValue()));
		convertRefundDetailParam.setRefundCharge(AmountUtils.changeY2FInt(refundNotice.getRefundCharge().doubleValue()));
		convertRefundDetailParam.setRefundStatus(refundNotice.getRefundStatus());
		convertRefundDetailParam.setOrderNo(refundNotice.getPartnerOrderId());
		if (refundNotice.getRefundStatus().equals(BookingConstants.YCF_REFUND_SUCCESS_CODE)) {
			convertRefundDetailParam.setCode(1);
		}else {
			convertRefundDetailParam.setCode(0);
		}
		return convertRefundDetailParam;
	}
	
	
	/**
	 * 驴妈妈订单状态bean ==> 通用订单状态bean
	 * 
	 * @param request
	 * @return
	 */
	public static ConvertOrderParam convertLvMaMaOrderStatusObject(HttpServletRequest request){
		ConvertOrderParam convertOrderParam = new ConvertOrderParam();
		String orderParam = request.getParameter("order").toString();
		LogUtils.sysErrorLoggerInfo("驴妈妈退款回调参数：" + orderParam);
		if (StringUtils.isBlank(orderParam)) {
			return null;
		}
		PushOrderInfo pushOrderInfo = JaxbUtils.convery2Bean(orderParam, PushOrderInfo.class);
		if (pushOrderInfo == null) {
			convertOrderParam = null;
			return convertOrderParam;
		}
		if (pushOrderInfo.getBody().getOrder().getStatus().equals("NORMAL")) {
			convertOrderParam.setStatus(BookingResultCodeContants.NOMARL);
		}else if (pushOrderInfo.getBody().getOrder().getStatus().equals("FINISHED")) {
			convertOrderParam.setStatus(BookingResultCodeContants.FINISH);
		}else if (pushOrderInfo.getBody().getOrder().getStatus().equals("CANCEL")) {
			convertOrderParam.setStatus(BookingResultCodeContants.CANCEL);
		}else if (pushOrderInfo.getBody().getOrder().getStatus().equals("REFUND")) {
			convertOrderParam.setStatus(BookingResultCodeContants.REFUND);
		}
		convertOrderParam.setApproveStatus(pushOrderInfo.getBody().getOrder().getApproveStatus());
		convertOrderParam.setWaitPaymentTime(pushOrderInfo.getBody().getOrder().getWaitPaymentTime());
		convertOrderParam.setOrderId(pushOrderInfo.getBody().getOrder().getOrderId());
		convertOrderParam.setCredenctStatus(pushOrderInfo.getBody().getOrder().getCredenctStatus());
		convertOrderParam.setPaymentStatus(pushOrderInfo.getBody().getOrder().getPaymentStatus());
		if (pushOrderInfo.getBody().getOrder().getPerformStatus().equals("USED")) {
			convertOrderParam.setPerformStatus(BookingResultCodeContants.TICKET_STATE_USE);
		}
		return convertOrderParam;
	}
	
	
	/**
	 * 键值对转化网关支付回调bean
	 * 
	 * @param request
	 * @return
	 */
	public static PayNotifyReturnParam convertPayNotifyObject(HttpServletRequest request){
		PayNotifyReturnParam notifyReturn = new PayNotifyReturnParam();
		notifyReturn.setCode(request.getParameter("code").toString());
		notifyReturn.setOrderNo(request.getParameter("orderNo").toString());
		notifyReturn.setSignData(request.getParameter("signData").toString());
		notifyReturn.setMessage(request.getParameter("message").toString());
		notifyReturn.setReferenceId(request.getParameter("referenceId").toString());
		notifyReturn.setOrderAmount(Integer.valueOf(request.getParameter("orderAmount").toString()));
		notifyReturn.setExt1(request.getParameter("ext1").toString());
		notifyReturn.setExt2(request.getParameter("ext2").toString());
		notifyReturn.setExt3(request.getParameter("ext3").toString());
		
		return notifyReturn;
	}

}
