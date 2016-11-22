package com.plateno.testservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.base.model.NotifyReturn;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.order.PayService;

/**
 * 
 * @author mogt
 * @date 2016年11月1日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PayServiceTest {
	
	@Autowired
	private PayService service;
	
	@Test
	public void testPullerPay() throws OrderException, Exception{
		
		MOrderParam param = new MOrderParam();
		param.setOrderNo("O1478076012273383901");
		
		ResultVo<Object> pullerPay = service.pullerPay(param);
		System.out.println(pullerPay);
	}
	
	@Test
	public void testPayNotify() throws OrderException, Exception{
		
		NotifyReturn notifyReturn = new NotifyReturn();
		notifyReturn.setCode("0000");
		notifyReturn.setOrderNo("L1479797201909635503");
		notifyReturn.setSignData("342434");
		notifyReturn.setMessage("");
		notifyReturn.setReferenceId("32323232323");
		notifyReturn.setOrderAmount(1);
		
		service.payNotify(notifyReturn );
	}
	
	@Test
	public void testHandlePaying() throws OrderException, Exception{
		
		service.handlePaying();
	}

}