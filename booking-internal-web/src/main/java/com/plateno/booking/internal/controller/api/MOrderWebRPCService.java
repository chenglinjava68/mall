package com.plateno.booking.internal.controller.api;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.plateno.booking.internal.base.model.SelectOrderParam;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.request.common.LstOrder;
import com.plateno.booking.internal.bean.request.custom.MOperateLogParam;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.bean.request.custom.ModifyOrderParams;
import com.plateno.booking.internal.bean.request.custom.ReceiptParam;
import com.plateno.booking.internal.bean.response.custom.MOperateLogResponse;
import com.plateno.booking.internal.bean.response.custom.OrderDetail;
import com.plateno.booking.internal.bean.response.custom.SelectOrderResponse;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.controller.base.BaseController;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.fromTicket.BOTAOMallBookingService;
import com.plateno.booking.internal.service.order.MOrderService;
import com.plateno.booking.internal.service.order.PayService;

@RestController
@RequestMapping("/mOrderService")
public class MOrderWebRPCService extends BaseController{

	private final static Logger log = Logger.getLogger(MBookingWebRPCService.class);
	
	@Autowired
	private MOrderService mOrderService;
	
	@Autowired
	private PayService payService;
	
	@Autowired
	private BOTAOMallBookingService botaoMallBookingService;
	

	@ResponseBody
	@RequestMapping(value="/queryOrderByPage",method = RequestMethod.POST)
	public ResultVo<LstOrder<SelectOrderResponse>> queryOrderByPage(@RequestBody @Valid SelectOrderParam  param,BindingResult result) throws Exception{
		log.info("查询订单列表项,请求参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		return mOrderService.queryOrderByPage(param);
	}
	

	@ResponseBody
	@RequestMapping(value = "/getOrderDetail",method = RequestMethod.POST)
	public ResultVo<OrderDetail> getOrderDetail(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
		log.info("查询订单详情,请求参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		
		//必须输入会员ID
		if(param.getMemberId() == null) {
			ResultVo<OrderDetail> out = new ResultVo<OrderDetail>();
			out.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			out.setResultMsg("请输入会员ID");
			return out;
		} 
		
		return mOrderService.getOrderDetail(param);
	}
	

	@ResponseBody
	@RequestMapping(value = "/removeOrder" ,method = RequestMethod.POST)
	public ResultVo<Object> removeOrder(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
		log.info("删除订单,请求参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		
		//必须输入会员ID
		ResultVo<Object> out = needMemberId(param);
		if(!out.success()) {
			return out;
		}
		
		return mOrderService.deleteOrder(param);
	}
	
	private ResultVo<Object> needMemberId(MOrderParam param) {
		ResultVo<Object> result = new ResultVo<Object>();
		if(param.getMemberId() == null) {
			result.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			result.setResultMsg("请输入会员ID");
		} 
		return result;
	}
	

	@ResponseBody
	@RequestMapping(value = "/cancelOrder" ,method = RequestMethod.POST)
	public ResultVo<Object> cancelOrder(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
		log.info("取消订单,请求参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		
		//必须输入会员ID
		ResultVo<Object> out = needMemberId(param);
		if(!out.success()) {
			return out;
		}
		
		return mOrderService.cancelOrderLock(param);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/deliverGoods" ,method = RequestMethod.POST)
	public ResultVo<Object> deliverGoods(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
		log.info("订单发货通知接口,请求参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		return mOrderService.deliverOrder(param);
	}
	
	@ResponseBody
	@RequestMapping(value = "/modifydeliverInfo" ,method = RequestMethod.POST)
	public ResultVo<Object> modifydeliverInfo(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
		log.info("修改发货信息,请求参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		return mOrderService.modifydeliverOrder(param);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/enterReceipt" ,method = RequestMethod.POST)
	public ResultVo<Object> enterReceipt(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
		log.info("确定收货的操作,请求参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		
		//必须输入会员ID
		ResultVo<Object> out = needMemberId(param);
		if(!out.success()) {
			return out;
		}
		
		return mOrderService.enterReceipt(param);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/userRefund" ,method = RequestMethod.POST)
	public ResultVo<Object> userRefund(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
		log.info("用户申请退款的操作,请求参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		
		//必须输入会员ID
		ResultVo<Object> out = needMemberId(param);
		if(!out.success()) {
			return out;
		}
		
		return mOrderService.userRefund(param);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/adminRefuseRefund" ,method = RequestMethod.POST)
	public ResultVo<Object> adminRefuseRefund(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
		log.info("客服审核拒绝退款的操作,请求参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		return mOrderService.adminRefuseRefund(param);
	}
	
	@ResponseBody
	@RequestMapping(value = "/consentRefund" ,method = RequestMethod.POST)
	public ResultVo<Object> consentRefund(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
		log.info("客服审核同意退款操作,请求参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		return mOrderService.refundOrder(param);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/pullerPay" ,method = RequestMethod.POST)
	public ResultVo<Object> pullerPay(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
		log.info("拉起支付,请求参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		
		//必须输入会员ID
		ResultVo<Object> out = needMemberId(param);
		if(!out.success()) {
			return out;
		}
		
		return payService.pullerPay(param);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getPaySuccessDetail" ,method = RequestMethod.POST)
	public ResultVo<OrderDetail> getPaySuccessDetail(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
		log.info("购买成功页面参数请求:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		
		//必须输入会员ID
		if(param.getMemberId() == null) {
			ResultVo<OrderDetail> out = new ResultVo<OrderDetail>();
			out.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			out.setResultMsg("请输入会员ID");
			return out;
		} 
		
		return mOrderService.getPaySuccessDetail(param);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/modifyReceiptInfo" ,method = RequestMethod.POST)
	public ResultVo<Object> modifyReceiptInfo(@RequestBody @Valid ReceiptParam param,BindingResult result) throws Exception{
		log.info("修改收货人请求参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		return mOrderService.modifyReceiptInfo(param);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/recordOperateLog" ,method = RequestMethod.POST)
	public ResultVo<Object> saveOperateLog(@RequestBody @Valid MOperateLogParam param,BindingResult result) throws Exception{
		log.info("订单操作日志参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		return mOrderService.saveOperateLog(param);
	}

	@ResponseBody
	@RequestMapping(value = "/selectOperateLog" ,method = RequestMethod.POST)
	public ResultVo<List<MOperateLogResponse>> selectOperateLog(@RequestBody @Valid MOperateLogParam params) throws Exception{
		log.info("查询订单操作日志参数:"+ JsonUtils.toJsonString(params));
		return mOrderService.selectOperateLog(params);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/modifyOrder" ,method = RequestMethod.POST)
	public ResultVo<Object> modifyOrder(@RequestBody @Valid ModifyOrderParams param,BindingResult result) throws Exception{
		log.info("修改订单日志参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		return mOrderService.modifyOrder(param);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateOrderStatus" ,method = RequestMethod.POST)
	public ResultVo<Object> updateOrderStatus(@RequestBody @Valid ModifyOrderParams param,BindingResult result) throws Exception{
		log.info("更新订单状态参数:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		return mOrderService.updateOrderStatus(param);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getOrderInfo" ,method = RequestMethod.POST)
	public ResultVo<Object> getOrderInfo(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
		log.info("获取订单状态参数，用于前端轮询支付结果:"+ JsonUtils.toJsonString(param));
		bindingResultHandler(result);
		return mOrderService.getOrderInfo(param);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/getPruSellAmountByPreDay" ,method = RequestMethod.POST)
	public ResultVo<Object> getOrderListByPreDay(HttpServletRequest requst) throws Exception{
		log.info("获取商品的已销售数量参数:"+ JsonUtils.toJsonString(requst.getParameter("days")));
		return mOrderService.getPruSellAmountByPreDay(Integer.parseInt(requst.getParameter("days")));
	}
	
}
