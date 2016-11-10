package com.plateno.testservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.druid.support.json.JSONUtils;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.response.gateway.pay.PayQueryResponse;
import com.plateno.booking.internal.bean.response.gateway.refund.RefundQueryResponse;
import com.plateno.booking.internal.gateway.PaymentService;

/**
 * 
 * @author mogt
 * @date 2016年11月1日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PaymentServiceTest {
	
	@Autowired
	private PaymentService service;
	
	@Test
	public void testPayOrderQuery() throws OrderException, Exception{
		PayQueryResponse payOrderQuery = service.payOrderQuery("L1478764589349755057");
		System.out.println(JSONUtils.toJSONString(payOrderQuery));
	}
	
	@Test
	public void testRefundOrderQuery() throws OrderException, Exception{
		RefundQueryResponse refundOrderQuery = service.refundOrderQuery("L1478740203813312912");
		System.out.println(JSONUtils.toJSONString(refundOrderQuery));
	}
	

}