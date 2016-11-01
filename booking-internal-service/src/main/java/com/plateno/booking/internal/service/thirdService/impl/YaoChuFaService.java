/*package com.plateno.booking.internal.service.thirdService.impl;

import java.io.IOException;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.contants.UrlConstants;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.convert.ConvertBookingParam;
import com.plateno.booking.internal.bean.request.convert.ConvertThirdPayParam;
import com.plateno.booking.internal.bean.request.lvmama.order.TravellersInfo;
import com.plateno.booking.internal.bean.request.ycf.Request;
import com.plateno.booking.internal.bean.request.ycf.Vocher;
import com.plateno.booking.internal.bean.request.ycf.YcfCancelOrderRequest;
import com.plateno.booking.internal.bean.request.ycf.YcfCheckAvailRequest;
import com.plateno.booking.internal.bean.request.ycf.YcfOrderRequest;
import com.plateno.booking.internal.bean.request.ycf.YcfOrderRequest.YcfGuest;
import com.plateno.booking.internal.bean.request.ycf.YcfOrderRequest.YcfPriceItem;
import com.plateno.booking.internal.bean.request.ycf.YcfOrderRequest.YcfTicket;
import com.plateno.booking.internal.bean.request.ycf.YcfPayOrderRequest;
import com.plateno.booking.internal.bean.response.custom.Order;
import com.plateno.booking.internal.bean.response.ycf.Response;
import com.plateno.booking.internal.bean.response.ycf.YcfCancelOrderResponse;
import com.plateno.booking.internal.bean.response.ycf.YcfCheckAvailResponse;
import com.plateno.booking.internal.bean.response.ycf.YcfCheckAvailResponse.YcfSaleInfos;
import com.plateno.booking.internal.bean.response.ycf.YcfCheckAvailResponse.YcfStockItem;
import com.plateno.booking.internal.bean.response.ycf.YcfGoodsIdUtil;
import com.plateno.booking.internal.bean.response.ycf.YcfOrderResponse;
import com.plateno.booking.internal.bean.response.ycf.YcfOrderStatusResponse;
import com.plateno.booking.internal.bean.response.ycf.YcfPayOrderResponse;
import com.plateno.booking.internal.bean.response.ycf.YcfRequestUtil;
import com.plateno.booking.internal.bean.response.ycf.YcfStatusCodeEnum;
import com.plateno.booking.internal.bean.response.ycf.YcfVoucherResponse;
import com.plateno.booking.internal.bean.util.HttpUtils;
import com.plateno.booking.internal.common.util.date.DateUtil;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.common.util.number.AmountUtils;
import com.plateno.booking.internal.dao.mapper.BillMapper;
import com.plateno.booking.internal.dao.pojo.BillDetails;
import com.plateno.booking.internal.dao.pojo.BillLogDetail;
import com.plateno.booking.internal.dao.pojo.sms.SmsMessage;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;
import com.plateno.booking.internal.service.order.BillService;
import com.plateno.booking.internal.service.order.OrderService;
import com.plateno.booking.internal.service.thirdService.base.ThirdApiService;
import com.plateno.booking.internal.sms.SMSSendService;
import com.plateno.booking.internal.sms.model.SmsMessageReq;
import com.plateno.booking.internal.bean.contants.TicketTypeEnum;



@Service
@ServiceErrorCode(BookingConstants.CODE_TONGCHENG_BOOK_ERROR)
public class YaoChuFaService implements ThirdApiService{

	private final static Logger log = Logger.getLogger(YaoChuFaService.class);
	
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private BillService billService;
	@Autowired
	private SMSSendService sendService;
	
	@Autowired
	private BillMapper billMapper;
	
	@Override
	public ResultVo SendMessageService(BillDetails billDetails, ResultVo output) {
		String interfaceName = "要出发重发凭证接口";
		String url = UrlConstants.YAOCHUFA_RESEND_CODE_URL;
		try {
			String orderId = billDetails.getOrderNo();
			String json = "{\"data\": {\"partnerOrderId\": \"" + orderId + "\"}}";
			String result = YcfRequestUtil.httpPostRequest(url, json, interfaceName);
			log.info("要出发重发凭证接口返回:"+result);
			if (result == null){
				output.setResultCode(getClass(), BookingConstants.CODE_714001);
				output.setResultMsg(MsgCode.THIRD_SEND_CODE_NULL.getMessage());
				return output;
			}
			Response<YcfVoucherResponse> resData = JsonUtils.jsonToObject(result, Response.class, YcfVoucherResponse.class);
			if (resData.getStatusCode() != YcfStatusCodeEnum.SUCCESS.getStatus()) {
				output.setResultCode(getClass(), BookingConstants.CODE_714002);
				output.setResultMsg(resData.getMessage());
				return output;
			}
			if (!resData.isSuccess()) {
				output.setResultCode(getClass(), BookingConstants.CODE_714002);
				output.setResultMsg(resData.getMessage());
				return output;
			}
			if (!sendMessage(resData, orderId)) {
				output.setResultCode(getClass(), BookingConstants.CODE_714003);
			}else{
				
				output.setResultMsg("重发凭证成功.");
			}
			return output;
		}  catch (Exception e) {
			log.error(String.format("SendMessageService exception[%s]", e));
			output.setResultCode(getClass(), BookingConstants.CODE_714004);
			output.setResultMsg(e.getMessage());
			return output;
		}
	}

	@Override
	public ResultVo QueryOrderListByOrderNo(List<BillDetails> billDetails, ResultVo output) {
		
		if (billDetails == null || billDetails.size() == 0) {
			return output;
		}
		String url = UrlConstants.YAOCHUFA_GET_ORDER_URL;
		
		try {
			String json = "{\"data\": {\"partnerOrderId\": \"" + billDetails.get(0).getOrderNo() + "\"}}";
			log.info("要出发订单查询接口请求:"+json);
			String result = HttpUtils.httpPostRequest(url, json, YcfRequestUtil.getHeadersMap(json));
			log.info("要出发订单查询接口返回:"+result);
			if (result == null){
				String msg = "查询订单失败,第三方没有返回响应结果";
				output.setResultCode(getClass(), BookingConstants.CODE_704002);
				output.setResultMsg(msg);
				return output;
			}
			Response<YcfOrderStatusResponse> resData = JsonUtils.jsonToObject(result, Response.class, YcfOrderStatusResponse.class);
			if (resData.getStatusCode() != YcfStatusCodeEnum.SUCCESS.getStatus()) {
				output.setResultCode(getClass(), BookingConstants.CODE_704001);
				output.setResultMsg(resData.getMessage());
				return output;
			}
			if (!resData.isSuccess()) {
				output.setResultCode(getClass(), BookingConstants.CODE_704003);
				output.setResultMsg(resData.getMessage());
				return output;
			}
			output.setData(resData.getData());
			return output;
		} catch (Exception e) {
			log.error(String.format("QueryOrderListByOrderNo exception[%s]", e));
			output.setResultCode(getClass(), BookingConstants.CODE_704004);
			output.setResultMsg(e.getMessage());
			return output;
		}
	}

	@Override
	public ResultVo QueryOrderList(List<BillLogDetail> billLogDetails, ResultVo output) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultVo Validate(ConvertBookingParam convertBookingParam, ResultVo output) {
		try {
			List<YcfStockItem> stockItem = ycfValidate(convertBookingParam);
			//传给下单接口
			output.setData(stockItem);
		} catch (Exception e) {
			
			log.error(String.format("Validate exception[%s]", e));
			output.setResultCode(getClass(), MsgCode.THIRD_ORDER_NULL.getMsgCode());
			output.setResultMsg(e.getMessage());
		}
		return output;
	}

	@Override
	public ResultVo Booking(ConvertBookingParam convertBookingParam, ResultVo output) {
		try {
			//验证
			List<YcfStockItem> stockItem = (List<YcfStockItem>) output.getData();
			//下单
			createYcfOrder(setOrderParam(convertBookingParam, stockItem),output);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(String.format("Booking exception[%s]", e));
			output.setResultCode(getClass(), MsgCode.THIRD_ORDER_NULL.getMsgCode());
			output.setResultMsg(e.getMessage());
		}
		return output;
	}

	@Override
	public ResultVo Pay(ConvertThirdPayParam convertThirdPayParam, ResultVo output, Runnable runnable) {
	    try {
			Request<YcfPayOrderRequest> ycfPay = new Request<YcfPayOrderRequest>();
			YcfPayOrderRequest data = new YcfPayOrderRequest();
			data.setPartnerOrderId(convertThirdPayParam.getOrderNo());
			data.setPaySerialNumber(convertThirdPayParam.getReferenceId());
			BigDecimal b2bPrice = new BigDecimal(AmountUtils.changeF2Y(convertThirdPayParam.getB2bPrice().toString()));
			data.setPrice(b2bPrice.multiply(new BigDecimal(convertThirdPayParam.getQuantity())));
			ycfPay.setData(data);
			
			String json = JsonUtils.toJsonString(ycfPay);
			log.info("发送要出发订单支付接口的请求参数:" + json);
			String result = HttpUtils.httpPostRequest(UrlConstants.YAOCHUFA_PAY_URL, json, YcfRequestUtil.getHeadersMap(json));
			log.info("要出发订单支付接口返回信息:" + result);
			if (result == null){
				output.setResultCode(getClass(), BookingConstants.CODE_724002);
				output.setResultMsg("订单支付失败,要出发订单支付接口返回信息失败,返回结果是空");
				return output;
			}
			Response<YcfPayOrderResponse> resData = JsonUtils.jsonToObject(result, Response.class, YcfPayOrderResponse.class);
			if (resData.getStatusCode() != YcfStatusCodeEnum.SUCCESS.getStatus()) {
				output.setResultCode(getClass(), BookingConstants.CODE_724003);
				output.setResultMsg(resData.getMessage());
			}else{
				output.setData(resData.getData());
			}
			return output;
		} catch (Exception e) {
			log.error(String.format("Pay exception[%s]", e));
			output.setResultCode(getClass(), BookingConstants.CODE_724004);
			output.setResultMsg(e.getMessage());
			return output;
		}
	}

	@Override
	public ResultVo Refund(BillDetails billDetails, ResultVo output) {
		
		String url = UrlConstants.YAOCHUFA_REFUND_URL;
		try {
			YcfCancelOrderRequest coreq = new YcfCancelOrderRequest();
			coreq.setPartnerOrderId(billDetails.getOrderNo());
			coreq.setRemark("行程取消");
			Request<YcfCancelOrderRequest> param = new Request<YcfCancelOrderRequest>();
			String json = JsonUtils.toJsonString(param);
			log.info("发送要出发申请退款接口的请求参数:" + json);
			String result = HttpUtils.httpPostRequest(url, json, YcfRequestUtil.getHeadersMap(json));
			log.info("要出发申请退款接口返回信息:" + result);
			if (result == null){
				output.setResultCode(getClass(), BookingConstants.CODE_734002);
				output.setResultMsg("退款申请失败,申请退款接口返回信息失败,返回结果是空");
				return output;
			}
			Response<YcfCancelOrderResponse> resData = JsonUtils.jsonToObject(result, Response.class, YcfCancelOrderResponse.class);
			if (resData.getStatusCode() != YcfStatusCodeEnum.SUCCESS.getStatus()) {
				output.setResultCode(getClass(), BookingConstants.CODE_734001);
				output.setResultMsg(resData.getMessage());
				return output;
			}
			// 要出发取消订单失败
			if (!resData.isSuccess()) {
				output.setResultCode(getClass(), BookingConstants.CODE_734003);
				output.setResultMsg(resData.getMessage());
				return output;
			}
			return output;
		} catch (IOException e) {
			log.error(String.format("Refund exception[%s]", e));
			output.setResultCode(getClass(), BookingConstants.CODE_734004);
			output.setResultMsg(e.getMessage());
			return output;
		}
	}

	*//**
	 * 要出发创建订单
	 * @param param
	 * @return
	 * @throws Exception
	 *//*
	public void createYcfOrder(Request<YcfOrderRequest> param,ResultVo output)  {
		String interfaceName = "创建订单接口";
		String url = UrlConstants.YAOCHUFA_CREATE_ORDER_URL;
		try {
			String json = JsonUtils.toJsonString(param);
			log.info("发送要出发下单接口的请求参数:" + json);
			String result = YcfRequestUtil.httpPostRequest(url, json, interfaceName);
			log.info("发送要出发下单接口的请求返回:" + result);
			Response<YcfOrderResponse> resData = JsonUtils.jsonToObject(result, Response.class, YcfOrderResponse.class);
			if (resData.getStatusCode() != YcfStatusCodeEnum.SUCCESS.getStatus()) {
				throw new OrderException(interfaceName + "返回错误,参数:" + json + ",result:" + result);
			}
			if (!resData.isSuccess()) {
				throw new OrderException(interfaceName + ",处理失败:" + resData.getMessage());
			}
			String sdate = DateUtil.dateToFormatStr(DateUtil.addOrMinusYear(new Date(), 30), DateUtil.YYYY_MM_DD_HH_MM_SS);
			Order order = new Order(resData.getData().getOrderId(), "NORMAL", "UNPAY", sdate);
			//OrderResponse orderRes = new OrderResponse();
			//orderRes.setOrder(order);
			output.setData(order);
		} catch (Exception e) {
			
			log.error(String.format("createYcfOrder exception[%s]", e));
			output.setResultCode(getClass(), MsgCode.THIRD_ORDER_NULL.getMsgCode());
			output.setResultMsg(e.getMessage());
		}
	}

	*//**
	 * 要出发验证接口
	 *//*
	public List<YcfStockItem> ycfValidate(ConvertBookingParam param) throws Exception {
		
			Response<YcfCheckAvailResponse> res = validateYcfOrder(setValidateParam(param));
			List<YcfSaleInfos> infos = res.getData().getSaleInfos();
			if (infos == null || infos.size() == 0)
				return null;
			YcfSaleInfos info = infos.get(0);
			return info.getStockList();
		
	}
	private Request<YcfCheckAvailRequest> setValidateParam(ConvertBookingParam p) {
		Request<YcfCheckAvailRequest> request = new Request<YcfCheckAvailRequest>();
		YcfCheckAvailRequest careq = new YcfCheckAvailRequest();
		careq.setProductId(YcfGoodsIdUtil.dec(p.getChannelGoodsId().toString()));
		careq.setBeginDate(DateUtil.dateToFormatStr(new Date(p.getVisitDate()), DateUtil.YYYY_MM_DD_HH_MM_SS));
		careq.setEndDate(DateUtil.dateToFormatStr(new Date(p.getVisitDate()), DateUtil.YYYY_MM_DD_HH_MM_SS));
		request.setData(careq);
		return request;
	}
	*//**
	 * 要出发下单验证请求(预订检查)
	 * @param param
	 * @return
	 * @throws Exception
	 *//*
	public Response<YcfCheckAvailResponse> validateYcfOrder(Request<YcfCheckAvailRequest> param) throws Exception {
		String interfaceName = "下单验证接口";
		String url = UrlConstants.YAOCHUFA_VALADATE_ORDER_URL;
		try {
			String json = JsonUtils.toJsonString(param);
			log.info("发送要出发下单校验接口的请求参数:" + json);
			String result = YcfRequestUtil.httpPostRequest(url, json, interfaceName);
			log.info("发送要出发下单校验接口的请求返回:" + result);
			Response<YcfCheckAvailResponse> resData = JsonUtils.jsonToObject(result, Response.class, YcfCheckAvailResponse.class);
			if (resData.getStatusCode() != YcfStatusCodeEnum.SUCCESS.getStatus()) {
				throw new OrderException(interfaceName + "返回错误,参数:" + json + ",result:" + result);
			}
			return resData;
		} catch (OrderException e) {
			log.error(e.getMessage());
			throw new OrderException(e.getMessage());
		} catch (Exception e) {
			log.error(String.format("validateYcfOrder exception[%s]", e));
			throw new Exception("未知异常:" + e);
		}
	}
	private Request<YcfOrderRequest> setOrderParam(ConvertBookingParam p,  List<YcfStockItem> stockItem) throws Exception {
		Request<YcfOrderRequest> req = new Request<YcfOrderRequest>();
		YcfOrderRequest order = new YcfOrderRequest();
		//【合】订单号,必填
		order.setPartnerOrderId(p.getOrderNo());
		//【要】产品编号,必填
		order.setProductId(YcfGoodsIdUtil.dec(p.getChannelGoodsId().toString()));
		//【要】产品名称
		order.setProductName(p.getName());
		//产品数量,必填
		order.setQunatity(p.getQuantity());
		//总价,必填
		BigDecimal b2bPrice = new BigDecimal(AmountUtils.changeF2Y(p.getB2bPrice().toString()));
		order.setAmount(b2bPrice.multiply(new BigDecimal(p.getQuantity())));
		//销售总价
		order.setSellAmount(new BigDecimal(AmountUtils.changeF2Y(p.getTotalAmount().toString())));
		//联系人中文姓名,套餐预定规则要求，则必填
		order.setcName(p.getBookInfo().getName());
		//联系人英文姓名,套餐预定规则要求，则必填
		//order.seteName(null);
		//联系人手机,套餐预定规则要求，则必填
		order.setMobile(p.getBookInfo().getMobile());
		//联系人邮箱,套餐预定规则要求，则必填
		order.setEmail(p.getBookInfo().getEmail());
		//联系人证件号,套餐预定规则要求，则必填
		order.setCredential(p.getBookInfo().getCredentials());
		//联系人证件类型
		if (null != p.getBookInfo() && StringUtils.isNotBlank(p.getBookInfo().getCredentialsType())) {
			order.setCredentialType(setCredentialsType(p.getBookInfo().getCredentialsType()));
		}

		//出行客人集合,套餐预定规则要求，则必填
		List<TravellersInfo> travellers = p.getTravellers();
		if (CollectionUtils.isNotEmpty(travellers)) {
			List<YcfGuest> guests = new ArrayList<YcfGuest>();
			for (TravellersInfo o : travellers) {
				YcfGuest guest = new YcfGuest();
				guest.setcName(o.getName());
				guest.seteName(o.getEnName());
				guest.setMobile(o.getMobile());
				guest.setEmail(o.getEmail());
				guest.setCredential(o.getCredentials());
				if (StringUtils.isNotBlank(o.getCredentialsType())) {
					guest.setCredentialType(setCredentialsType(o.getCredentialsType()));
				}
				guests.add(guest);
			}
			order.setGuests(guests);
		}
		//门票资源组,必填
		List<YcfTicket> ticketDetail = new ArrayList<YcfTicket>();
		if (CollectionUtils.isNotEmpty(stockItem)) {
			for (YcfStockItem item : stockItem) {
				YcfTicket ticket = new YcfTicket();
				ticket.setTicketId(item.getItemId());
				ticket.setCheckInDate(DateUtil.dateToFormatStr(new Date(p.getVisitDate()), DateUtil.YYYY_MM_DD));
				ticketDetail.add(ticket);
			}
		}
		order.setTicketDetail(ticketDetail);
		//价格集合,必填
		YcfPriceItem pi = new YcfPriceItem();
		pi.setDate(DateUtil.dateToFormatStr(new Date(p.getVisitDate()), DateUtil.YYYY_MM_DD));
		//pi.setPrice(new BigDecimal(AmountUtils.changeF2Y(p.getSellPrice().toString())));
		pi.setPrice(new BigDecimal(AmountUtils.changeF2Y(p.getB2bPrice().toString())));
		List<YcfPriceItem> priceDetail = new ArrayList<YcfPriceItem>();
		priceDetail.add(pi);
		order.setPriceDetail(priceDetail);

		req.setData(order);
		return req;
	}
	private Integer setCredentialsType(String credentialsType) {
		Integer type = null;
		switch (credentialsType) {
		case "ID_CARD": //身份证
			type = 0;
			break;
		case "HUZHAO": //护照
			type = 1;
			break;
		case "GANGAO": //港澳通行证
			type = 2;
			break;
		case "TAIBAO": //台湾通行证
			type = 3;
			break;
		default:
			break;
		}
		return type;
	}
	
	*//**
	 * 要出发支付
	 * @param param
	 * @return
	 * @throws Exception
	 *//*
	public Response<YcfPayOrderResponse> toPay(Request<YcfPayOrderRequest> param) throws Exception {
		String interfaceName = "支付接口";
		String url = UrlConstants.YAOCHUFA_PAY_URL;
		try {
			String json = JsonUtils.toJsonString(param);
			String result = YcfRequestUtil.httpPostRequest(url, json, interfaceName);
			Response<YcfPayOrderResponse> resData = JsonUtils.jsonToObject(result, Response.class, YcfPayOrderResponse.class);
			if (resData.getStatusCode() != YcfStatusCodeEnum.SUCCESS.getStatus()) {
				throw new OrderException(interfaceName + "返回错误,参数:" + json + ",result:" + result);
			}
			return resData;
		} catch (OrderException e) {
			log.error(e.getMessage());
			throw new OrderException(e.getMessage());
		} catch (Exception e) {
			log.error(String.format("toPay exception[%s]", e));
			throw new Exception("未知异常:" + e);
		}
	}

	*//**
	 * 发送凭证短信
	 * @param resData
	 * @param orderId
	 * @return
	 * @throws IOException
	 *//*
	private Boolean sendMessage(Response<YcfVoucherResponse> resData, String orderId) throws IOException {
		List<Vocher> vochers = resData.getData().getVochers();
		SmsMessage message = billMapper.getSmsMessage(orderId);
		if (null != message) {
			SmsMessageReq req = new SmsMessageReq();
			req.setPhone(message.getPhone());// 手机号码
			Map<String, String> params = new HashMap<String, String>();

			if (CollectionUtils.isNotEmpty(vochers)) {
				StringBuffer sbNo = new StringBuffer();
				StringBuffer sbUrl = new StringBuffer();
				for (Vocher vocher : vochers) {
					if (StringUtils.isNotBlank(vocher.getVocherNo())) {
						sbNo.append(vocher.getVocherNo()).append(",");
					}
					if (StringUtils.isNotBlank(vocher.getVocherUrl())) {
						sbUrl.append(vocher.getVocherUrl()).append(",");
					}
				}
				if (sbNo.length() > 0) {
					sbNo.deleteCharAt(sbNo.length() - 1);
				}
				if (sbUrl.length() > 0) {
					sbUrl.deleteCharAt(sbUrl.length() - 1);
				}

				if (sbNo.length() == 0 && sbUrl.length() == 0) {
					req.setType(Integer.parseInt(Config.SMS_SERVICE_TEMPLATE_ONE));
				} else if (sbNo.length() > 0 && sbUrl.length() == 0) {
					req.setType(Integer.parseInt(Config.SMS_SERVICE_TEMPLATE_TWO));
					params.put("code", sbNo.toString());
				} else if (sbNo.length() > 0 && sbUrl.length() > 0) {
					req.setType(Integer.parseInt(Config.SMS_SERVICE_TEMPLATE_THREE));
					params.put("code", sbNo.toString());
					params.put("url", sbUrl.toString());
				}
			} else {
				req.setType(Integer.parseInt(Config.SMS_SERVICE_TEMPLATE_ONE));
			}
			params.put("order", message.getTradeNo());
			params.put("travelName", message.getTravelName());
			params.put("commodityName", message.getCommodityName());
			params.put("type", TicketTypeEnum.getName(Integer.valueOf(message.getType())));
			params.put("count", message.getCount() + "张");
			params.put("date", message.getDate());

			String enter = message.getEnter();
			if (StringUtils.isNotBlank(enter)) {
				String textValue = JsonUtils.parseJson(enter).findValue("ways").getTextValue();
				if (StringUtils.isNotBlank(textValue)) {
					params.put("enter", textValue);
				} else {
					params.put("enter", "凭此短信或手机号码入园");
				}
			} else {
				params.put("enter", "凭此短信或手机号码入园");
			}
			params.put("supplier", "要出发");
			req.setParams(params);
			return sendService.sendMessage(req);
		}
		return false;
	}

}
*/