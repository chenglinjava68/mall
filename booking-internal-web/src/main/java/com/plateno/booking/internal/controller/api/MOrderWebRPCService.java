package com.plateno.booking.internal.controller.api;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.plateno.booking.internal.base.constant.PlateFormEnum;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.request.custom.GetProductBuyNumParam;
import com.plateno.booking.internal.bean.request.custom.MOperateLogParam;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.bean.request.custom.ModifyOrderParams;
import com.plateno.booking.internal.bean.request.custom.OrderSkuQueryParam;
import com.plateno.booking.internal.bean.response.custom.MOperateLogResponse;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.conf.data.LogisticsTypeData;
import com.plateno.booking.internal.conf.vo.LogisticsTypeInfo;
import com.plateno.booking.internal.controller.base.BaseController;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.fromTicket.BOTAOMallBookingService;
import com.plateno.booking.internal.service.order.MOrderService;
import com.plateno.booking.internal.service.order.OrderCancelService;
import com.plateno.booking.internal.service.order.PayService;

@RestController
@RequestMapping("/mOrderService")
public class MOrderWebRPCService extends BaseController{

	private final static Logger log = LoggerFactory.getLogger(MOrderWebRPCService.class);
	
	@Autowired
	private MOrderService mOrderService;
	
	@Autowired
	private PayService payService;
	
	@Autowired
	private BOTAOMallBookingService botaoMallBookingService;
	
	@Autowired
	private OrderCancelService orderCancelService;

	@ResponseBody
	@RequestMapping(value = "/removeOrder" ,method = RequestMethod.POST)
	public ResultVo<Object> removeOrder(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
		log.info("删除订单,请求参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		checkBaseParam(param);
		
		return mOrderService.deleteOrder(param);
	}
	
	@ResponseBody
	@RequestMapping(value = "/cancelOrder" ,method = RequestMethod.POST)
	public ResultVo<Object> cancelOrder(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
		log.info("取消订单,请求参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		checkBaseParam(param);
		
		if(param.getPlateForm() != null && (param.getPlateForm() == PlateFormEnum.ADMIN.getPlateForm() || param.getPlateForm() == PlateFormEnum.PROVIDER_ADMIN.getPlateForm())) {
			
			if(StringUtils.isBlank(param.getOperateUserid())) {
				ResultVo<Object> out = new ResultVo<Object>();
				out.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
				out.setResultMsg("请输入操作人ID");
				return out;
			}
			
			if(StringUtils.isBlank(param.getOperateUsername())) {
				ResultVo<Object> out = new ResultVo<Object>();
				out.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
				out.setResultMsg("请输入操作人姓名");
				return out;
			}
		}
		
		return orderCancelService.cancelOrderLock(param);
	}
	
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/userRefund" ,method = RequestMethod.POST)
	public ResultVo<Object> userRefund(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
		log.info("用户申请退款的操作,请求参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		checkBaseParam(param);
		
		if(StringUtils.isBlank(param.getRefundRemark())) {
			ResultVo<Object> out = new ResultVo<Object>();
			out.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			out.setResultMsg("请输入退款原因");
			return out;
		}
		
		if(param.getPlateForm() != null && (param.getPlateForm() == PlateFormEnum.ADMIN.getPlateForm() || param.getPlateForm() == PlateFormEnum.PROVIDER_ADMIN.getPlateForm())) {
			
			if(StringUtils.isBlank(param.getOperateUserid())) {
				ResultVo<Object> out = new ResultVo<Object>();
				out.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
				out.setResultMsg("请输入操作人ID");
				return out;
			}
			
			if(StringUtils.isBlank(param.getOperateUsername())) {
				ResultVo<Object> out = new ResultVo<Object>();
				out.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
				out.setResultMsg("请输入操作人姓名");
				return out;
			}
		}
		ResultVo<Object> resultVo = mOrderService.userConsentRefund(param);
		return resultVo;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/adminRefuseRefund" ,method = RequestMethod.POST)
	public ResultVo<Object> adminRefuseRefund(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
		log.info("客服审核拒绝退款的操作,请求参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		checkBaseParam(param);
		
		return mOrderService.adminRefuseRefund(param);
	}
	
	@ResponseBody
	@RequestMapping(value = "/consentRefund" ,method = RequestMethod.POST)
	public ResultVo<Object> consentRefund(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
		log.info("客服审核同意退款操作,请求参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		checkBaseParam(param);
		
		return mOrderService.refundOrder(param);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/pullerPay" ,method = RequestMethod.POST)
	public ResultVo<Object> pullerPay(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
		log.info("拉起支付,请求参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		
		if(param.getPayType() == null) {
			param.setPayType(1);
		}
		
		if(param.getPayType() != 1 && param.getPayType() != 2) {
			ResultVo<Object> out = new ResultVo<Object>();
			out.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			out.setResultMsg("请输入正确的支付方式:" + param.getPayType());
			return out;
		}
		
		return payService.pullerPay(param);
	}
	
	
	
	

	
	
	@ResponseBody
	@RequestMapping(value = "/recordOperateLog" ,method = RequestMethod.POST)
	public ResultVo<Object> saveOperateLog(@RequestBody @Valid MOperateLogParam param,BindingResult result) throws Exception{
		log.info("订单操作日志参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		checkBaseParam(param);
		
		if(StringUtils.isBlank(param.getOperateUserid())) {
			ResultVo<Object> response = new ResultVo<Object>();
			response.setResultCode(this.getClass(), BookingResultCodeContants.MsgCode.BAD_REQUEST.getMsgCode());
			response.setResultMsg("请输入操作人ID");
			return response;
		}
		
		if(StringUtils.isBlank(param.getOperateUsername())) {
			ResultVo<Object> response = new ResultVo<Object>();
			response.setResultCode(this.getClass(), BookingResultCodeContants.MsgCode.BAD_REQUEST.getMsgCode());
			response.setResultMsg("请输入操作用户");
			return response;
		}
		
		if(StringUtils.isBlank(param.getOrderCode())) {
			ResultVo<Object> response = new ResultVo<Object>();
			response.setResultCode(this.getClass(), BookingResultCodeContants.MsgCode.BAD_REQUEST.getMsgCode());
			response.setResultMsg("订单编码");
			return response;
		}
		
		if(param.getOperateType() == null) {
			ResultVo<Object> response = new ResultVo<Object>();
			response.setResultCode(this.getClass(), BookingResultCodeContants.MsgCode.BAD_REQUEST.getMsgCode());
			response.setResultMsg("操作类型");
			return response;
		}
		
		return mOrderService.saveOperateLog(param);
	}

	@ResponseBody
	@RequestMapping(value = "/selectOperateLog" ,method = RequestMethod.POST)
	public ResultVo<List<MOperateLogResponse>> selectOperateLog(@RequestBody @Valid MOperateLogParam params) throws Exception{
		log.info("查询订单操作日志参数:"+ JsonUtils.toJsonString(params));
		checkBaseParam(params);
		return mOrderService.selectOperateLog(params);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/modifyOrder" ,method = RequestMethod.POST)
	public ResultVo<Object> modifyOrder(@RequestBody @Valid ModifyOrderParams param,BindingResult result) throws Exception{
		log.info("修改订单日志参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		checkBaseParam(param);
		
		if(StringUtils.isBlank(param.getOperateUserid())) {
			ResultVo<Object> response = new ResultVo<Object>();
			response.setResultCode(this.getClass(), BookingResultCodeContants.MsgCode.BAD_REQUEST.getMsgCode());
			response.setResultMsg("请输入操作人ID");
			return response;
		}
		
		if(StringUtils.isBlank(param.getOperateUsername())) {
			ResultVo<Object> response = new ResultVo<Object>();
			response.setResultCode(this.getClass(), BookingResultCodeContants.MsgCode.BAD_REQUEST.getMsgCode());
			response.setResultMsg("请输入操作用户");
			return response;
		}
		
		if(param.getPlateForm() == null) {
			ResultVo<Object> response = new ResultVo<Object>();
			response.setResultCode(this.getClass(), BookingResultCodeContants.MsgCode.BAD_REQUEST.getMsgCode());
			response.setResultMsg("请输入操作平台");
			return response;
		}
		
		ResultVo<Object> out = mOrderService.modifyOrderLock(param);
		return out;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateOrderStatus" ,method = RequestMethod.POST)
	public ResultVo<Object> updateOrderStatus(@RequestBody @Valid ModifyOrderParams param,BindingResult result) throws Exception{
		log.info("更新订单状态参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		checkBaseParam(param);
		return mOrderService.updateOrderStatus(param);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getPruSellAmountByPreDay" ,method = RequestMethod.POST)
	public ResultVo<Object> getOrderListByPreDay(HttpServletRequest requst) throws Exception{
		log.info("获取商品的已销售数量参数:"+ JsonUtils.toJsonString(requst.getParameter("days")));
		return mOrderService.getPruSellAmountByPreDay(Integer.parseInt(requst.getParameter("days")));
	}
	
	@ResponseBody
	@RequestMapping(value = "/getProductBuyNum" ,method = RequestMethod.POST)
	public ResultVo<Integer> getProductBuyNum(@RequestBody @Valid GetProductBuyNumParam param,BindingResult result) throws Exception{
		log.info("获取商品的购买数量:{}", JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		checkBaseParam(param);
		
		ResultVo<Integer> output = new ResultVo<>();
		int num = mOrderService.queryUserProductSum(param.getMemberId(), param.getProductId());
		output.setData(num);
		
		return output;
	}
	
	@ResponseBody
	@RequestMapping(value = "/logisticsList" ,method = RequestMethod.POST)
	public ResultVo<List<LogisticsTypeInfo>> logisticsList() throws Exception{
		log.info("获取物流信息");
		ResultVo<List<LogisticsTypeInfo>> output = new ResultVo<>();
		List<LogisticsTypeInfo> dataList = LogisticsTypeData.getDataList();
		output.setData(dataList);
		
		return output;
	}
	
	@ResponseBody
	@RequestMapping(value = "/skuSoldNum" ,method = RequestMethod.POST)
	public ResultVo<Integer> skuSoldNum(@RequestBody @Valid OrderSkuQueryParam param,BindingResult result) throws Exception{
		log.info("查询已经售出的数量:{}", JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		ResultVo<Integer> r = mOrderService.querySkuSoldNum(param.getSkuId());
		return r;
	}
}
