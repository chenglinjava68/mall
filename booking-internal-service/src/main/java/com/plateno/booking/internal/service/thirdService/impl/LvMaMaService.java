/*package com.plateno.booking.internal.service.thirdService.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.contants.UrlConstants;
import com.plateno.booking.internal.bean.request.convert.ConvertBookingParam;
import com.plateno.booking.internal.bean.request.convert.ConvertOrderCancelBean;
import com.plateno.booking.internal.bean.request.convert.ConvertPayParam;
import com.plateno.booking.internal.bean.request.convert.ConvertThirdPayParam;
import com.plateno.booking.internal.bean.request.lvmama.order.OrderInfo;
import com.plateno.booking.internal.bean.request.lvmama.order.OrderInfo.Booker;
import com.plateno.booking.internal.bean.request.lvmama.order.OrderStatusInfo;
import com.plateno.booking.internal.bean.request.lvmama.order.OrderStatusInfo.Order;
import com.plateno.booking.internal.bean.request.lvmama.order.OrderStatusInfo.Request;
import com.plateno.booking.internal.bean.request.lvmama.order.ProductInfo;
import com.plateno.booking.internal.bean.request.lvmama.order.RequestInfo;
import com.plateno.booking.internal.bean.request.lvmama.order.ResendCode;
import com.plateno.booking.internal.bean.request.lvmama.order.ResendCode.LvMaMaResendCode;
import com.plateno.booking.internal.bean.request.lvmama.order.ValidateOrderRequest;
import com.plateno.booking.internal.bean.request.lvmama.pay.PayInfo;
import com.plateno.booking.internal.bean.request.lvmama.pay.PayInfo.Orders;
import com.plateno.booking.internal.bean.request.lvmama.pay.PayInfo.Requests;
import com.plateno.booking.internal.bean.response.lvmama.base.ResponseState;
import com.plateno.booking.internal.bean.response.lvmama.order.LvMaMaOrderBean;
import com.plateno.booking.internal.bean.response.lvmama.order.OrderCannelResponse;
import com.plateno.booking.internal.bean.response.lvmama.order.OrderResponse;
import com.plateno.booking.internal.common.util.date.DateUtil;
import com.plateno.booking.internal.common.util.http.HttpUtils;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.dao.pojo.BillDetails;
import com.plateno.booking.internal.dao.pojo.BillLogDetail;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;
import com.plateno.booking.internal.service.thirdService.base.ThirdApiService;
import com.plateno.booking.internal.validator.order.OrderValidate;


*//**
 * 驴妈妈基础服务接口
 * 
 * @author user
 *
 *//*
@Service
@ServiceErrorCode(BookingConstants.CODE_LVMAMA_BOOK_ERROR)
public class LvMaMaService implements ThirdApiService{
	
	private final static Logger logger = Logger.getLogger(LvMaMaService.class);
	
	@Autowired
	private OrderValidate orderValidate;
	

	 (non-Javadoc)
	 * @see com.plateno.booking.internal.service.thirdService.base.ThirdApiService#SendMessageService()
	 * 
	 * 驴妈妈短信发送接口
	 
	@Override
	public ResultVo SendMessageService(BillDetails billDetails,ResultVo output) {
		orderValidate.checkSendMessage(billDetails, output);
		if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
			return output;
		}
		LvMaMaResendCode code = new LvMaMaResendCode();
		code.setOrderId(billDetails.getPartnerOrderId());
		code.setPartnerOrderNo(billDetails.getOrderNo());
		try {
			ResendCode resendCode = new ResendCode(code);
			logger.info("驴妈妈发起重发短信接口请求结构:" + resendCode.toString());
			//请求驴妈妈重发短信验证接口
			String result = HttpUtils.excutePost(UrlConstants.LVMAMA_RESEND_CODE_URL, resendCode.toString());
			logger.info("驴妈妈重发短信接口返回结构:"+result);
			if (result == null){
				output.setResultCode(getClass(), BookingConstants.CODE_712002);
				output.setResultMsg(MsgCode.THIRD_SEND_CODE_NULL.getMessage());
				return output;
			}
			ResponseState state = JsonUtils.jsonToObject(result, ResponseState.class);
			if (!state.getState().getCode().equals(BookingResultCodeContants.LVMAMA_SUCCESS_CODE)) {
				output.setResultCode(getClass(), BookingConstants.CODE_712003);
			}
			output.setResultMsg(state.getState().getMessage());
			return output;
		} catch (Exception e) {
			logger.error(String.format("SendMessageService exception[%s]", e));
			output.setResultCode(getClass(), BookingConstants.CODE_712004);
			output.setResultMsg(e.getMessage());
			return output;
		}
	}

	 (non-Javadoc)
	 * @see com.plateno.booking.internal.service.thirdService.base.ThirdApiService#QueryOrderListByOrderNo(java.util.List)
	 * 
	 * 驴妈妈根据铂涛订单号,查询订单信息
	 
	@Override
	public ResultVo QueryOrderListByOrderNo(List<BillDetails> billDetails,ResultVo output) {
		if (billDetails == null || billDetails.size() == 0) {
			return output;
		}
		OrderStatusInfo orderStatusInfo;
		orderStatusInfo = new OrderStatusInfo();
		Request request = new Request();
		Order orders = new Order();
		orders.setPartnerOrderNos(getOrderNos(billDetails));
		request.setOrder(orders);
		orderStatusInfo.setRequest(request);
		try {
			logger.info("驴妈妈发起订单查询接口请求结构:" + orderStatusInfo.toString());
			//请求驴妈妈订单查询接口
			String result = HttpUtils.excutePost(UrlConstants.LVMAMA_GET_ORDER_URL, orderStatusInfo.toString());
			logger.info("驴妈妈订单查询接口返回结构:"+result);
			if (result == null){
				String msg = "查询订单失败,第三方没有返回响应结果";
				output.setResultCode(getClass(), BookingConstants.CODE_702002);
				output.setResultMsg(msg);
				return output;
			}
			LvMaMaOrderBean bean = JsonUtils.jsonToObject(result, LvMaMaOrderBean.class);
			if (!bean.getState().getCode().equals(BookingResultCodeContants.LVMAMA_SUCCESS_CODE)) {
				output.setResultCode(getClass(), BookingConstants.CODE_702003);
				output.setResultMsg(bean.getState().getMessage());
			}else {
				output.setData(bean.getOrderList());
			}
			return output;
		} catch (Exception e) {
			logger.error(String.format("QueryOrderListByOrderNo exception[%s]", e));
			output.setResultCode(getClass(), BookingConstants.CODE_702004);
			output.setResultMsg(e.getMessage());
			return output;
		}
	}

	 (non-Javadoc)
	 * @see com.plateno.booking.internal.service.thirdService.base.ThirdApiService#QueryOrderList(java.util.List)
	 * 
	 * 驴妈妈没有根据订单号查询订单信息
	 
	@Override
	public ResultVo QueryOrderList(List<BillLogDetail> billLogDetails,ResultVo output) {
		// TODO Auto-generated method stub
		return null;
	}

	 (non-Javadoc)
	 * @see com.plateno.booking.internal.service.thirdService.base.ThirdApiService#Validate()
	 * 
	 * 驴妈妈订单校验接口
	 
	@Override
	public ResultVo  Validate(ConvertBookingParam convertBookingParam,ResultVo  output) {
		try {
			ValidateOrderRequest validateOrderRequest =  setOrderParams(convertBookingParam);
			logger.info("发送驴妈妈校验订单的请求参数:" + JsonUtils.toJsonString(validateOrderRequest));
			String result = HttpUtils.excutePost(UrlConstants.LVMAMA_VALADATE_ORDER_URL,validateOrderRequest.toString());
			logger.info("驴妈妈校验订单接口返回信息:" + result);
			if (result == null){
				output.setResultCode(getClass(), BookingConstants.CODE_702007);
				output.setResultMsg("校验订单失败,驴妈妈订单校验返回信息失败,返回结果是空");
				return output;
			}
			ResponseState state = JsonUtils.jsonToObject(result, ResponseState.class);
			if (!state.getState().getCode().equals(BookingResultCodeContants.LVMAMA_SUCCESS_CODE)) {
				output.setResultCode(getClass(), BookingConstants.CODE_702008);
			}
			output.setResultMsg(state.getState().getMessage());
			return output;
		} catch (Exception e) {
			logger.error(String.format("Validate exception[%s]", e));
			output.setResultCode(getClass(), BookingConstants.CODE_702009);
			output.setResultMsg(e.getMessage());
			return output;
		}
	}

	 (non-Javadoc)
	 * @see com.plateno.booking.internal.service.thirdService.base.ThirdApiService#Booking()
	 * 
	 * 驴妈妈下单接口
	 
	@Override
	public ResultVo Booking(ConvertBookingParam convertBookingParam,ResultVo output) {
		try {
			ValidateOrderRequest validateOrderRequest =  setOrderParams(convertBookingParam);
			logger.info("发送驴妈妈下单请求的请求参数:" + JsonUtils.toJsonString(validateOrderRequest));
			String result = HttpUtils.excutePost(UrlConstants.LVMAMA_CREATE_ORDER_URL,validateOrderRequest.toString());
			logger.info("驴妈妈下单请求接口返回信息:" + result);
			if (result == null){
				output.setResultCode(getClass(), MsgCode.THIRD_ORDER_NULL.getMsgCode());
				output.setResultMsg("下单失败,驴妈妈下单接口返回信息失败,返回结果是空");
				return output;
			}
			OrderResponse state = JsonUtils.jsonToObject(result, OrderResponse.class);
			if (!state.getState().getCode().equals(BookingResultCodeContants.LVMAMA_SUCCESS_CODE)) {
				output.setResultCode(getClass(), MsgCode.THIRD_ORDER_NULL.getMsgCode());
				output.setResultMsg(state.getState().getMessage());
			}else{
				
				output.setData(state.getOrder());
			}
			return output;
		} catch (Exception e) {
			logger.error(String.format("Booking exception[%s]", e));
			output.setResultCode(getClass(), MsgCode.THIRD_ORDER_NULL.getMsgCode());
			output.setResultMsg(e.getMessage());
			return output;
		}
	}

	 (non-Javadoc)
	 * @see com.plateno.booking.internal.service.thirdService.base.ThirdApiService#Pay(com.plateno.booking.internal.bean.request.convert.ConvertPayParam)
	 * 
	 * 驴妈妈订单支付接口
	 
	@Override
	public ResultVo Pay(ConvertThirdPayParam convertThirdPayParam,ResultVo output,Runnable runnable) {
		try {
			PayInfo payInfo = new PayInfo();
			Requests request = new Requests();
			Orders orders = new Orders();
			orders.setPartnerOrderNo(convertThirdPayParam.getOrderNo());
			orders.setSerialNum(convertThirdPayParam.getReferenceId());
			request.setOrder(orders);
			payInfo.setRequest(request);
			logger.info("发送驴妈妈订单支付接口的请求参数:" + JsonUtils.toJsonString(payInfo));
			String result = HttpUtils.excutePost(UrlConstants.LVMAMA_ORDER_PAY_URL,payInfo.toString());
			logger.info("驴妈妈订单支付接口返回信息:" + result);
			if (result == null){
				output.setResultCode(getClass(), BookingConstants.CODE_722002);
				output.setResultMsg("订单支付失败,驴妈妈订单支付接口返回信息失败,返回结果是空");
				return output;
			}
			OrderResponse state = JsonUtils.jsonToObject(result, OrderResponse.class);
			if (!state.getState().getCode().equals(BookingResultCodeContants.LVMAMA_SUCCESS_CODE)) {
				output.setResultCode(getClass(), BookingConstants.CODE_722003);
				output.setResultMsg(state.getState().getMessage());
			}else{
				output.setData(state.getOrder());
			}
			return output;
		} catch (Exception e) {
			logger.error(String.format("Pay exception[%s]", e));
			output.setResultCode(getClass(), BookingConstants.CODE_712004);
			output.setResultMsg(e.getMessage());
			return output;
		}
	}

	 (non-Javadoc)
	 * @see com.plateno.booking.internal.service.thirdService.base.ThirdApiService#Refund()
	 * 
	 * 驴妈妈申请退款接口
	 
	@Override
	public ResultVo Refund(BillDetails billDetails,ResultVo output) {
		//过滤不符合退款条件
		//orderValidate.checkRefund(billDetails, output);
		ConvertOrderCancelBean bean = new ConvertOrderCancelBean();
		bean.setOrderNo(billDetails.getPartnerOrderId());
		bean.setPartnerOrderNo(billDetails.getOrderNo());
		
		try {
			logger.info("发送驴妈妈申请退款接口的请求参数:" + JsonUtils.toJsonString(bean));
			String result = HttpUtils.excutePost(UrlConstants.LVMAMA_CANCEL_ORDER_URL,bean.toString());
			logger.info("申请退款接口返回信息:" + result);
			if (result == null){
				output.setResultCode(getClass(), BookingConstants.CODE_732002);
				output.setResultMsg("退款申请失败,申请退款接口返回信息失败,返回结果是空");
				return output;
			}
			OrderCannelResponse state = JsonUtils.jsonToObject(result, OrderCannelResponse.class);
			if (!state.getState().getCode().equals(BookingResultCodeContants.LVMAMA_SUCCESS_CODE)) {
				output.setResultCode(getClass(), BookingConstants.CODE_732003);
				output.setResultMsg(state.getState().getMessage());
			}else{
				//拒绝退款
				if(state.getOrder().getRequestStatus().equals(BookingConstants.REJECT)){
					output.setResultCode(getClass(), BookingConstants.CODE_732003);
					output.setResultMsg(state.getOrder().getRefundInfo());
				}
			}
			return output;
		} catch (Exception e) {
			logger.error(String.format("Refund exception[%s]", e));
			output.setResultCode(getClass(), BookingConstants.CODE_732004);
			output.setResultMsg(e.getMessage());
			return output;
		}
	}
	
	
	*//**
	 * 下单对象封装方法
	 * 
	 * @param convertBookingParam
	 * @return
	 * @throws Exception
	 *//*
	private ValidateOrderRequest setOrderParams(ConvertBookingParam convertBookingParam)throws Exception {
		ValidateOrderRequest validateOrderRequest = new ValidateOrderRequest();
		RequestInfo request = new RequestInfo();
		OrderInfo orderInfo = new OrderInfo();
		Double orderAmount = Double.valueOf(convertBookingParam.getTotalAmount())/100;
		orderInfo.setOrderAmount(orderAmount);		//设置订单总价
		orderInfo.setPartnerOrderNo(convertBookingParam.getOrderNo());		//校验订单,可以不传
				
		//设置产品类别
		ProductInfo product = new ProductInfo();
		product.setGoodsId(convertBookingParam.getChannelGoodsId());
		product.setProductId(convertBookingParam.getChannelProductId());
		product.setQuantity(convertBookingParam.getQuantity());
		Double money = Double.valueOf(convertBookingParam.getSellPrice())/100;
		product.setSellPrice(money);
		product.setVisitDate(DateUtil.dateToFormatStr(convertBookingParam.getVisitDate(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		orderInfo.setProduct(product);
		
		
		Booker b =new Booker();
		b.setName(convertBookingParam.getBookInfo().getName());
		b.setMobile(convertBookingParam.getBookInfo().getMobile());
		b.setEmail(convertBookingParam.getBookInfo().getEmail());
		orderInfo.setBooker(b);		//设置取票人
		
		orderInfo.setTravellers(convertBookingParam.getTravellers());	//设置游玩人
		//设置购买人
		request.setOrderInfo(orderInfo);
		validateOrderRequest.setRequest(request);
		
		return validateOrderRequest;
	}
	
	*//**
	 * 获取铂涛订单号列表
	 * 
	 * @param billDetails
	 * @return
	 *//*
	private String getOrderNos(List<BillDetails> billDetails){
		StringBuffer orderNo = new StringBuffer();
		for(BillDetails billDetail : billDetails){
			if (StringUtils.isNotEmpty(billDetail.getOrderNo()) && StringUtils.isNotBlank(billDetail.getOrderNo())) {
				orderNo.append(billDetail.getOrderNo()).append(",");
			}
		}
		return orderNo.toString();
	}
}
*/