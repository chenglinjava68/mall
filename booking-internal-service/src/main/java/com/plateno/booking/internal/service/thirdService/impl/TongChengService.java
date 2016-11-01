/*package com.plateno.booking.internal.service.thirdService.impl;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.contants.UrlConstants;
import com.plateno.booking.internal.bean.request.convert.ConvertBookingParam;
import com.plateno.booking.internal.bean.request.convert.ConvertThirdPayParam;
import com.plateno.booking.internal.bean.request.lvmama.order.TravellersInfo;
import com.plateno.booking.internal.bean.request.tc.Request;
import com.plateno.booking.internal.bean.request.tc.RequestHead;
import com.plateno.booking.internal.bean.request.tc.order.OrderBody;
import com.plateno.booking.internal.bean.request.tc.order.OrderCancelBody;
import com.plateno.booking.internal.bean.request.tc.order.OrderListBody;
import com.plateno.booking.internal.bean.request.tc.order.OrderListByPageBody;
import com.plateno.booking.internal.bean.request.tc.order.ResendCodeBody;
import com.plateno.booking.internal.bean.request.tc.order.ValidateBody;
import com.plateno.booking.internal.bean.request.tc.order.ValidateBody.RealBookInfo;
import com.plateno.booking.internal.bean.request.tc.order.ValidateBody.ScreeningInfo;
import com.plateno.booking.internal.bean.response.custom.Order;
import com.plateno.booking.internal.bean.response.tc.OrderResponse;
import com.plateno.booking.internal.bean.response.tc.Response;
import com.plateno.booking.internal.bean.response.tc.order.OrderListByPageResponse;
import com.plateno.booking.internal.bean.response.tc.order.OrderListByPageResponse.OrderList;
import com.plateno.booking.internal.bean.response.tc.order.OrderListResponse;
import com.plateno.booking.internal.common.util.date.DateUtil;
import com.plateno.booking.internal.common.util.http.HttpUtils;
import com.plateno.booking.internal.common.util.json.JsonNullUtils;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.common.util.number.AmountUtils;
import com.plateno.booking.internal.common.util.number.MD5Util;
import com.plateno.booking.internal.dao.pojo.BillDetails;
import com.plateno.booking.internal.dao.pojo.BillLogDetail;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;
import com.plateno.booking.internal.runnable.TCRunnable;
import com.plateno.booking.internal.service.order.BillService;
import com.plateno.booking.internal.service.thirdService.base.ThirdApiService;
import com.plateno.booking.internal.validator.order.OrderValidate;

*//**
 * 同程基础服务接口
 * 
 * @author user
 *
 *//*
@Service
@ServiceErrorCode(BookingConstants.CODE_TONGCHENG_BOOK_ERROR)
public class TongChengService implements ThirdApiService{
	
	private static final Logger logger = Logger.getLogger(TongChengService.class);
	
	@Autowired
	private OrderValidate orderValidate;
	
	@Autowired
	private TaskExecutor taskExecutor;

	 (non-Javadoc)
	 * @see com.plateno.booking.internal.service.thirdService.base.ThirdApiService#SendMessageService(com.plateno.booking.internal.dao.pojo.BillDetails)
	 * 
	 * 同程重发短信接口
	 
	@Override
	public ResultVo SendMessageService(BillDetails billDetails,ResultVo output) {
		orderValidate.checkSendMessage(billDetails, output);
		if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
			return output;
		}
		//构造发短信的请求信息
		ResendCodeBody codeBody = new ResendCodeBody();
		codeBody.setSendMobile(billDetails.getMobile());
		List<String> num = new ArrayList<String>();
		num.add(billDetails.getPartnerOrderId());
		codeBody.setSerialId(num);
		String digitalSign = MD5Util.encode(Config.TC_AGENTACCOUNT + billDetails.getPartnerOrderId() + billDetails.getMobile() + Config.TC_KEY);
		RequestHead requestHead = new RequestHead(digitalSign);
		Request<ResendCodeBody> request = new Request<ResendCodeBody>(requestHead, codeBody);
		try {
			String tongchengSendMessageBean = JsonUtils.toJsonString(request);
			logger.info("同程发起重发短信接口请求结构:" + tongchengSendMessageBean);
			//请求驴妈妈重发短信验证接口
			String result = HttpUtils.excutePost(UrlConstants.TONGCHENG_RESEND_CODE_URL, tongchengSendMessageBean);
			logger.info("同程重发短信接口返回结构:"+result);
			if (result == null){
				String msg = "重发凭证失败,第三方没有返回响应结果";
				output.setResultCode(getClass(), BookingConstants.CODE_713001);
				output.setResultMsg(msg);
				return output;
			}
			Response response = JsonUtils.jsonToObject(result, Response.class);
			if (!response.getRespCode().equals(BookingResultCodeContants.TC_SUCCESS_CODE)) {
				output.setResultCode(getClass(), BookingConstants.CODE_713002);
			}
			output.setResultMsg(response.getRespMsg());
			return output;
		} catch (Exception e) {
			logger.error(String.format("SendMessageService exception[%s]", e));
			output.setResultCode(getClass(), BookingConstants.CODE_713003);
			output.setResultMsg(e.getMessage());
			return output;
		}
	}

	//接口对换
	
	 (non-Javadoc)
	 * @see com.plateno.booking.internal.service.thirdService.base.ThirdApiService#QueryOrderListByOrderNo(java.util.List)
	 * 
	 * 同程根据同程订单号,查询订单信息,目前进行简化,仅支持基于订单号进行查询处理
	 
	@Override
	public ResultVo QueryOrderListByOrderNo(List<BillDetails> billDetails,ResultVo output) {
		if (billDetails == null || billDetails.size() == 0) {
			return output;
		}
		List<OrderList> list = new ArrayList<OrderList>();
		OrderListByPageBody body = new OrderListByPageBody();
		for(BillDetails billDetail : billDetails){
			body.setOrderSerialId(billDetail.getPartnerOrderId());
			String digitalSign = MD5Util.encode(Config.TC_AGENTACCOUNT + billDetail.getPartnerOrderId() + body.getPageIndex() + body.getPageSize() + Config.TC_KEY);
			RequestHead requestHead = new RequestHead(digitalSign);
			Request<OrderListByPageBody> request = new Request<OrderListByPageBody>(requestHead, body);
			try {
				String tongchengQueryOrderListByOrderNo = JsonUtils.toJsonString(request);
				logger.info("同程发起分销商订单查询接口请求结构:" + tongchengQueryOrderListByOrderNo);
				//请求驴妈妈订单查询接口
				String result = HttpUtils.excutePost(UrlConstants.TONGCHENG_ORDER_LIST_URL, tongchengQueryOrderListByOrderNo);
				logger.info("同程发起分销商订单查询接口返回结构:"+result);
				if (result == null){
					String msg = "查询订单失败,第三方没有返回响应结果";
					output.setResultCode(getClass(), BookingConstants.CODE_703002);
					output.setResultMsg(msg);
					return output;
				}
				OrderListByPageResponse bean = JsonUtils.jsonToObject(result, OrderListByPageResponse.class);
				if (bean.getRespCode().equals(BookingResultCodeContants.TC_SUCCESS_CODE)) {
					list.addAll(bean.getOrderList());
				}
			} catch (Exception e) {
				logger.error(String.format("QueryOrderListByOrderNo exception[%s]", e));
				output.setResultCode(getClass(), BookingConstants.CODE_703003);
				output.setResultMsg(e.getMessage());
				return output;
			}
		}
		output.setData(list);
		return output;
	}

	 (non-Javadoc)
	 * @see com.plateno.booking.internal.service.thirdService.base.ThirdApiService#QueryOrderList(java.util.List)
	 * 
	 * 同程分销商订单查询接口(根据第三方流水号查询)
	 
	@Override
	public ResultVo QueryOrderList(List<BillLogDetail> billLogDetails,ResultVo output) {
		if (billLogDetails == null || billLogDetails.size() == 0) {
			return output;
		}
		List<String> orders = new ArrayList<String>();
		StringBuffer orderNo = new StringBuffer();
		for(BillLogDetail billLogDetail : billLogDetails){
			orderNo.append(billLogDetail.getBillLogTradeNo() == null ? "" :billLogDetail.getBillLogTradeNo());
			orders.add(billLogDetail.getBillLogTradeNo() == null ? "" :billLogDetail.getBillLogTradeNo());
		}
		String digitalSign = MD5Util.encode(Config.TC_AGENTACCOUNT+orderNo+Config.TC_KEY);
		RequestHead head = new RequestHead(digitalSign);
		OrderListBody body = new OrderListBody(orders);
		Request<OrderListBody> request = new Request<OrderListBody>(head, body);
		try {
			String tongchengQueryOrderListByOrderNo = JsonUtils.toJsonString(request);
			logger.info("同程发起分销商订单查询接口(根据第三方流水号查询)请求结构:" + tongchengQueryOrderListByOrderNo);
			//请求驴妈妈订单查询接口
			String result = HttpUtils.excutePost(UrlConstants.TONGCHENG_ORDER_LIST_BYTHIRD_URL, tongchengQueryOrderListByOrderNo);
			logger.info("同程发起分销商订单查询接口(根据第三方流水号查询)返回结构:"+result);
			if (result == null){
				String msg = "查询订单失败,第三方没有返回响应结果";
				output.setResultCode(getClass(), BookingConstants.CODE_703004);
				output.setResultMsg(msg);
				return output;
			}
			OrderListResponse bean = JsonUtils.jsonToObject(result, OrderListResponse.class);
			if (!bean.getRespCode().equals(BookingResultCodeContants.TC_SUCCESS_CODE)) {
				output.setResultCode(getClass(), BookingConstants.CODE_703005);
				output.setResultMsg(bean.getRespMsg());
				return output;
			}else {
				output.setData(bean.getOrderList());
			}
			return output;
		} catch (Exception e) {
			logger.error(String.format("QueryOrderList exception[%s]", e));
			output.setResultCode(getClass(), BookingConstants.CODE_703006);
			output.setResultMsg(e.getMessage());
			return output;
		}
	}

	 (non-Javadoc)
	 * @see com.plateno.booking.internal.service.thirdService.base.ThirdApiService#Validate(com.plateno.booking.internal.bean.request.convert.ConvertBookingParam)
	 * 
	 * 同程订单校验接口
	 
	@Override
	public ResultVo Validate(ConvertBookingParam convertBookingParam,ResultVo output) {
		try {
			ValidateBody validateBody =  setOrderParams(convertBookingParam);
			Request<ValidateBody> request = getValidateRequest(validateBody);
			String tcvalidate = JsonNullUtils.toJsonString(request);
			logger.info("发送同程校验订单的请求参数:" + tcvalidate);
			String result = HttpUtils.excutePost(UrlConstants.TONGCHENG_VALADATE_ORDER_URL,tcvalidate);
			logger.info("同程校验订单接口返回信息:" + result);
			if (result == null){
				output.setResultCode(getClass(), MsgCode.THIRD_ORDER_NULL.getMsgCode());
				output.setResultMsg("校验订单失败,同程订单校验返回信息失败,返回结果是空");
				return output;
			}
			Response state = JsonUtils.jsonToObject(result, Response.class);
			if (!state.getRespCode().equals(BookingResultCodeContants.TC_SUCCESS_CODE)) {
				output.setResultCode(getClass(), BookingConstants.CODE_703001);
				output.setResultMsg(state.getRespMsg());
			}
			return output;
		} catch (Exception e) {
			logger.error(String.format("Validate exception[%s]", e));
			output.setResultCode(getClass(), MsgCode.THIRD_ORDER_NULL.getMsgCode());
			output.setResultMsg(e.getMessage());
			return output;
		}
	}

	 (non-Javadoc)
	 * @see com.plateno.booking.internal.service.thirdService.base.ThirdApiService#Booking(com.plateno.booking.internal.bean.request.convert.ConvertBookingParam)
	 * 
	 * 同程没有下单接口(支付接口,等价于下单接口)
	 
	@Override
	public ResultVo Booking(ConvertBookingParam convertBookingParam,ResultVo output) {
		
		try {
			String sdate = DateUtil.dateToFormatStr(DateUtil.addOrMinusYear(new Date(), 30), DateUtil.YYYY_MM_DD_HH_MM_SS);
			Order order = new Order(null, "NORMAL", "UNPAY", sdate);
			output.setData(order);
		} catch (Exception e) {
			logger.error(String.format("Booking exception[%s]", e));
		}
		return output;
	}

	 (non-Javadoc)
	 * @see com.plateno.booking.internal.service.thirdService.base.ThirdApiService#Pay(java.lang.Object)
	 * 
	 * 同程下单支付接口
	 
	@Override
	public ResultVo Pay(ConvertThirdPayParam convertThirdPayParam,ResultVo output,Runnable runnable) {
		try {
			Request<OrderBody> request = setPayRequest(convertThirdPayParam);
			String param = JsonUtils.toJsonString(request);
			logger.info("发送同程订单支付接口的请求参数:" + param);
			String result = HttpUtils.excutePost(UrlConstants.TONGCHENG_PAY_URL,param);
			logger.info("同程订单支付接口返回信息:" + result);
			if (result == null){
				output.setResultCode(getClass(), BookingConstants.CODE_723002);
				output.setResultMsg("订单支付失败,同程订单支付接口返回信息失败,返回结果是空");
				return output;
			}
			OrderResponse state = JsonUtils.jsonToObject(result, OrderResponse.class);
			if (!state.getRespCode().equals(BookingResultCodeContants.TC_SUCCESS_CODE)) {
				output.setResultCode(getClass(), BookingConstants.CODE_723003);
				output.setResultMsg(state.getRespMsg());
			}else{
				output.setData(state.getOrderResponse());
				//有待优化处理
				convertThirdPayParam.setPartnerOrderId(state.getOrderResponse().get(0).getSerialId());
				//更新OTA订单id到订单表
				//runnable = new TCRunnable(convertThirdPayParam);
				taskExecutor.execute(new TCRunnable(convertThirdPayParam));
			}
			return output;
		} catch (Exception e) {
			logger.error(String.format("Pay exception[%s]", e));
			output.setResultCode(getClass(), BookingConstants.CODE_723004);
			output.setResultMsg(e.getMessage());
			return output;
		}
	}

	 (non-Javadoc)
	 * @see com.plateno.booking.internal.service.thirdService.base.ThirdApiService#Refund(com.plateno.booking.internal.dao.pojo.BillDetails, com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo)
	 * 
	 * 退款接口
	 
	@Override
	public ResultVo Refund(BillDetails billDetails,ResultVo output) {
		OrderCancelBody body = new OrderCancelBody();
		body.setSerialId(billDetails.getPartnerOrderId());
		body.setRefundType(1);
		body.setRefundReason("行程取消");
		Request<OrderCancelBody> request = getCancelRequest(body);
		try {
			String refund_json = JsonNullUtils.toJsonString(request);
			logger.info("发送同程申请退款接口的请求参数:" + refund_json);
			String result = HttpUtils.excutePost(UrlConstants.TONGCHENG_REFUND_URL,refund_json);
			logger.info("发送同程申请退款接口返回信息:" + result);
			if (result == null){
				output.setResultCode(getClass(), BookingConstants.CODE_733002);
				output.setResultMsg("退款申请失败,申请退款接口返回信息失败,返回结果是空");
				return output;
			}
			Response state = JsonUtils.jsonToObject(result, Response.class);
			if (!state.getRespCode().equals(BookingResultCodeContants.TC_SUCCESS_CODE)) {
				output.setResultCode(getClass(), BookingConstants.CODE_733003);
			}
			output.setResultMsg(state.getRespMsg());
			return output;
		} catch (Exception e) {
			logger.error(String.format("Refund exception[%s]", e));
			output.setResultCode(getClass(), BookingConstants.CODE_733004);
			output.setResultMsg(e.getMessage());
			return output;
		}
	}
	
	*//**
	 * 同程拼凑校验下单对象
	 * 
	 * @param convertBookingParam
	 * @return
	 * @throws Exception
	 *//*
	private ValidateBody setOrderParams(ConvertBookingParam convertBookingParam) throws Exception {
		ValidateBody t = new ValidateBody();
		//产品Id
		t.setPriceId(convertBookingParam.getChannelGoodsId());
		//旅游日期
		t.setTravelDate(DateUtil.dateToFormatStr(convertBookingParam.getVisitDate(), DateUtil.YY_MM_DD));
		//取票数
		t.setTickets(convertBookingParam.getQuantity());
		//同程价
		String money = AmountUtils.changeF2Y(convertBookingParam.getTotalAmount().toString());
		t.setTCAmount(new BigDecimal(money));
		//分销结算总价
		t.setAgentAmount(new BigDecimal(0));
		//取票人身份证号码
		t.setTravelerIdCardNo(convertBookingParam.getBookInfo().getCredentials());
		//取票人手机号码
		t.setTravelerMobile(convertBookingParam.getBookInfo().getMobile());
		//预定人手机号码识别码
		t.setMobileIdentifier(convertBookingParam.getTravellers().get(0).getMobile());
		//预定人邮箱
		t.setBookEmail(convertBookingParam.getBookInfo().getEmail());

		//实名制列表
		RealBookInfo realBookInfo = new RealBookInfo();
		//取票人姓名
		byte[] encodeBase64 = Base64.encodeBase64(convertBookingParam.getBookInfo().getName().getBytes("UTF-8"));  
		realBookInfo.setName(new String(encodeBase64));
		//取票人手机号码
		realBookInfo.setMobile(convertBookingParam.getBookInfo().getMobile());
		//身份证/护照号码
		realBookInfo.setIdCard(convertBookingParam.getBookInfo().getCredentials());
		//取票人邮箱
		realBookInfo.setEmail(convertBookingParam.getBookInfo().getEmail());
		RealBookInfo[] realBookInfos = { realBookInfo };
		t.setRealBookInfo(realBookInfos);

		//场次信息
		if (StringUtils.isNotBlank(convertBookingParam.getTcScreeningId()) || convertBookingParam.getTcBeginTime() > 0
				|| convertBookingParam.getTcEndTime()>0) {
			ScreeningInfo screeningInfo = new ScreeningInfo();
			screeningInfo.setScreeningId(convertBookingParam.getTcScreeningId());
			screeningInfo.setBeginTime(DateUtil.dateToFormatStr(convertBookingParam.getTcBeginTime(),DateUtil.YYYY_MM_DD_HH_MM_SS));
			screeningInfo.setEndTime(DateUtil.dateToFormatStr(convertBookingParam.getTcEndTime(),DateUtil.YYYY_MM_DD_HH_MM_SS));
			t.setScreeningInfo(screeningInfo);
		} else {
			t.setScreeningInfo(null);
		}
		return t;
	}
	
	
	*//**
	 * 同程拼凑支付请求参数
	 * 
	 * @param convertBookingParam
	 * @return
	 *//*
	private Request<OrderBody> setPayRequest(ConvertThirdPayParam convertBookingParam){
			OrderBody body = new OrderBody();
			List<String> travelerName = new ArrayList<String>();
			List<String> travelerMobile = new ArrayList<String>();
			List<String> travelerIDCard = new ArrayList<String>();
			StringBuffer mobilesMD5= new StringBuffer();
			
			body.setTicketsNum(Arrays.asList(convertBookingParam.getQuantity()));//购买数量
			body.setBookMan(Arrays.asList(convertBookingParam.getBookInfo().getName()));//预定人
			body.setBookMobileType(1);//预定人手机类型
			body.setBookMobile(Arrays.asList(convertBookingParam.getBookInfo().getMobile()));//预定人手机号码
			
			List<TravellersInfo> travellers = convertBookingParam.getTravellers();
			body.setBookEmail(travellers.get(0).getEmail());//预定人邮箱
			
			if(CollectionUtils.isNotEmpty(travellers)){
				
				for(TravellersInfo tra:travellers){
					travelerName.add(tra.getName());
					travelerMobile.add(tra.getMobile());
					travelerIDCard.add(tra.getCredentials());
					mobilesMD5.append(tra.getMobile());
				}
				body.setTravelerName(travelerName);//取票人姓名
				body.setTravelerMobile(travelerMobile);//取票人手机号码
				if(travelerIDCard.size() > 0){
					body.setTravelerIdCardNo(travelerIDCard.get(0));//预订人身份证
					body.setTravelerIdCardNoList(travelerIDCard);//旅游人身份证
				
				}
			}
			body.setTravelerMobileType(1);//取票人手机类型(1：大陆 0：非大陆
			body.setPriceID(convertBookingParam.getChannelGoodsId().intValue());//产品id
			body.setTravelDate(new Date(convertBookingParam.getVisitDate()));//旅游日期
			body.setThirdSerialId(Arrays.asList(convertBookingParam.getOrderNo()));//第三方流水
			body.setScreeningId(convertBookingParam.getTcScreeningId());//场次ID
			body.setBeginTime(DateUtil.dateToFormatStr(convertBookingParam.getTcBeginTime(),DateUtil.YYYY_MM_DD_HH_MM_SS));//场次开始时间
			body.setEndTime(DateUtil.dateToFormatStr(convertBookingParam.getTcEndTime(),DateUtil.YYYY_MM_DD_HH_MM_SS));//场次结束时间
			String digitalSign = MD5Util.encode(Config.TC_AGENTACCOUNT+convertBookingParam.getChannelGoodsId()+mobilesMD5.toString()+convertBookingParam.getOrderNo()+Config.TC_KEY);
			RequestHead head = new RequestHead(digitalSign);
			Request<OrderBody> request = new Request<OrderBody>(head,body);
			return request;
	}
	
	private Request<ValidateBody> getValidateRequest(ValidateBody param) {
		String digitalSign = MD5Util.encode(Config.TC_AGENTACCOUNT + param.getPriceId() + param.getTickets() + param.getTravelerMobile() + Config.TC_KEY);
		RequestHead requestHead = new RequestHead(digitalSign);
		Request<ValidateBody> request = new Request<ValidateBody>(requestHead, param);
		return request;
	}
	
	
	private Request<OrderCancelBody> getCancelRequest(OrderCancelBody param) {
		String digitalSign = MD5Util.encode(Config.TC_AGENTACCOUNT + param.getSerialId() + Config.TC_KEY);
		RequestHead requestHead = new RequestHead(digitalSign);
		Request<OrderCancelBody> request = new Request<OrderCancelBody>(requestHead, param);
		return request;
	}

}
*/