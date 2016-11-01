/*package com.plateno.booking.internal.controller.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.plateno.booking.internal.bean.request.custom.OrderParam;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.api.ApiService;


@RestController
@RequestMapping("/test")
public class TestService {
	
	@Autowired
	private ApiService apiService;

	*//**
	 * 查询订单状态
	 * @param orderId 【合】订单号,即铂涛订单号
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping(value = "/getOrderStatus/{orderId}")
	public ResultVo getOrderStatus(@PathVariable("orderId") String orderId) throws Exception {
		ResultVo output = new ResultVo();
		OrderParam orderParam = new OrderParam();
		orderParam.setChannel(3);
		orderParam.setOrderNo(orderId);
		return apiService.QueryOrderListByOrderNo(orderParam, output);
	}
	
}
*/