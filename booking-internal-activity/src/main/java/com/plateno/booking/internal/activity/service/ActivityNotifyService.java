/*package com.plateno.booking.internal.activity.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.plateno.booking.internal.activity.common.convert.ConvertParam;
import com.plateno.booking.internal.bean.request.gateway.PayNotifyReturnParam;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.order.ActivityPayService;


*//**
 * 回调通知入口
 * 
 * @author user
 *
 *//*
@RestController
@RequestMapping("/activityNotifyService")
public class ActivityNotifyService {
	
	
	@Autowired
	private ActivityPayService activityPayService;
	
	*//**
	 * @Description: 活动支付网关的支付回调通知
	 * @param request
	 * @throws Exception 
	 * @throws
	 * @author liulianyuan
	 * @date 2016年5月31日
	 *//*
	@RequestMapping(value = "/activityPayNotifyUrl",method = RequestMethod.POST)
	public String payNotifyUrl(HttpServletRequest request) throws Exception{
		
		long start = System.currentTimeMillis();
		LogUtils.httpLoggerInfo("支付网关回调开始时间：" + start);
		try {
			PayNotifyReturnParam notifyReturn = ConvertParam.convertPayNotifyObject(request);
			ResultVo<Boolean> vo =  activityPayService.checkPayStatus(notifyReturn);
			long end = System.currentTimeMillis();
			LogUtils.httpLoggerInfo("支付网关回调结束时间：" + (end-start)/1000);
			if (vo.success()) {
				return "SUCCESS";
			}else{
				return "FAILURE";
			}
		} catch (Exception e) {
			e.getStackTrace();
			LogUtils.sysErrorLoggerError(ExceptionUtils.getStackTrace(e),e);
			return "FAILURE";
		}
	}
	
}
*/