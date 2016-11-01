package com.plateno.booking.internal.controller.api;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.plateno.booking.internal.base.model.NotifyReturn;
import com.plateno.booking.internal.base.model.bill.BillOrderDetail;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.bean.response.custom.MAddBookResponse;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.common.util.redis.RedisUtils;
import com.plateno.booking.internal.controller.base.BaseController;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.fromTicket.BOTAOMallBookingService;
import com.plateno.booking.internal.service.order.MOrderService;
import com.plateno.booking.internal.service.order.PayService;
import com.plateno.booking.internal.wechat.WechatTemplementSerivce;

@RestController
@RequestMapping("/mbookingService")
public class MBookingWebRPCService extends BaseController{

	private final static Logger log = Logger.getLogger(MBookingWebRPCService.class);
	
	@Autowired
	private MOrderService mOrderService;
	
	@Autowired
	private PayService payService;
	
	@Autowired
	private BOTAOMallBookingService botaoMallBookingService;
	
	@Autowired
	private WechatTemplementSerivce wechatTemplementSerivce;
	
	@Autowired
	private RedisUtils redisUtils;
	
	
	@ResponseBody
	@RequestMapping(value = "/addBooking" ,method = RequestMethod.POST)
	public ResultVo<MAddBookResponse> booking(@RequestBody @Valid MAddBookingParam addBookingParam,BindingResult result) throws Exception{
		log.info("下单,请求参数:"+ JsonUtils.toJsonString(addBookingParam));
		bindingResultHandler(result);
		return botaoMallBookingService.addBooking(addBookingParam);
	}
	
	
	//支付网关回调
	@RequestMapping(value = "/payNotifyUrl",method = RequestMethod.POST)
	public String payNotifyUrl(HttpServletRequest request) throws Exception, OrderException{
//		ResultVo<Object> object=new ResultVo<Object>();
		long start = System.currentTimeMillis();
		log.info("支付网关回调开始时间：" + start);
//		BillOrderDetail bill=new BillOrderDetail();
		try {
			NotifyReturn notifyReturn = resolveParam(request);
			
			log.info("支付网关回调,请求入参:" + JsonUtils.toJsonString(notifyReturn));
			
			if(StringUtils.isBlank(notifyReturn.getOrderNo())) {
				log.error("支付网关支付回调,订单号为空");
				return "FAILURE";
			}
			
			//支付网关回调订单处理
			payService.payNotify(notifyReturn);
			
			return "SUCCESS";
			
//			if (notifyReturn.getCode().equals("0000")) {
//				log.info("支付网关回调,请求入参:" + JsonUtils.toJsonString(notifyReturn));
//				
//				 bill = payService.getOrderNoByTradeNo(notifyReturn.getOrderNo());
//				//更新状态为支付中
//					updateStatus(bill,BookingResultCodeContants.PAY_STATUS_11);
//				if (bill == null){
//					log.error("支付网关支付回调,获取不到对应的账单信息");
//					object.setData("FAILURE");
//					return object;
//				}
//				//更新状态为支付成功(待发货)
//				updateStatus(bill,BookingResultCodeContants.PAY_STATUS_3);
//				object.setData("SUCCESS");
//				return object;
//				/*String strBookingRequestParam = redisUtils.get(bill.getOrderNo());
//				if(strBookingRequestParam != null){
//					MAddBookingParam mAddBookingParam = JsonUtils.jsonToObject(strBookingRequestParam, MAddBookingParam.class);
//					long end = System.currentTimeMillis();
//					
//						//发送支付成功模版
//						//wechatTemplementSerivce.paying(bill.getTradeNo(),null,bill.getMemberId().intValue(),mAddBookingParam.getChanelId());
//						// 删除redis订单信息
//						redisUtils.del(bill.getOrderNo());
//						return "SUCCESS";
//				}else{
//					log.info("获取缓存订单信息为空");
//					return "FAILURE";
//				}*/
//			}else{
//				//更新订单的状态,支付失败
//				updateStatus(bill,BookingResultCodeContants.PAY_STATUS_12);		
//				object.setData("SUCCESS");
//				return object;
//			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			log.error("异常日志："+ e);
			//object.setData("FAILURE");
			return "FAILURE";
		}
		
	}


	private void updateStatus(BillOrderDetail bill,Integer status)
			throws Exception {
		Order order = new Order();
		order.setPayStatus(status);
		order.setPayType(bill.getPayId());
		mOrderService.updateOrderStatusByNo(order,bill.getOrderNo());
	}
	
	private NotifyReturn resolveParam(HttpServletRequest request){
		NotifyReturn notifyReturn = new NotifyReturn();
		notifyReturn.setCode(request.getParameter("code").toString());
		notifyReturn.setOrderNo(request.getParameter("orderNo").toString());
		notifyReturn.setSignData(request.getParameter("signData").toString());
		notifyReturn.setMessage(request.getParameter("message").toString());
		notifyReturn.setReferenceId(request.getParameter("referenceId").toString());
		notifyReturn.setOrderAmount(Integer.valueOf(request.getParameter("orderAmount").toString()));
		if(request.getParameter("ext1")!=null)
		notifyReturn.setExt1(request.getParameter("ext1").toString());
		if(request.getParameter("ext2")!=null)
		notifyReturn.setExt2(request.getParameter("ext2").toString());
		if(request.getParameter("ext3")!=null)
		notifyReturn.setExt3(request.getParameter("ext3").toString());
		
		return notifyReturn;
	}
	
	
	
}
