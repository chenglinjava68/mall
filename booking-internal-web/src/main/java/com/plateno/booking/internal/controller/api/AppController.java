package com.plateno.booking.internal.controller.api;


import java.util.Date;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.plateno.booking.internal.base.vo.MOrderSearchVO;
import com.plateno.booking.internal.bean.response.custom.SelectOrderResponse;
import com.plateno.booking.internal.common.util.date.DateUtil;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.controller.base.BaseController;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.order.MOrderService;
import com.plateno.booking.internal.util.vo.PageInfo;

@RestController
public class AppController extends BaseController{

	private final static Logger log = Logger.getLogger(MBookingWebRPCService.class);
	
	@Autowired
	private MOrderService mOrderService;
	
	@ResponseBody
	@RequestMapping(value="/mApp/orderList", method = RequestMethod.POST)
	public ResultVo<PageInfo<SelectOrderResponse>> queryOrderByPage(@RequestBody @Valid MOrderSearchVO  search, BindingResult result) throws Exception{
		log.info("APP查询订单列表项,请求参数:"+ JsonUtils.toJsonString(search));
		bindingResultHandler(result);
		checkBaseSearchVO(search);
		
		if(search.getBookingStartDate() == null && search.getBookingEndDate() == null) {
			log.info("没有填开始和结束日志，默认查询最近的一个月");
			search.setBookingStartDate(DateUtil.getDate(new Date(), -30, 0, 0, 0).getTime());
		}
		
		if(search.getSize() > 1000) {
			search.setSize(1000);
		}
		
		return mOrderService.queryOrderList(search);
	}
	
}
