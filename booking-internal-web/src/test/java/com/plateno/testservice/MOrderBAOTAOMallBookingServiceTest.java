package com.plateno.testservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.bean.response.custom.MAddBookResponse;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.fromTicket.BOTAOMallBookingService;

/**
 * 
 * @author mogt
 * @date 2016年11月1日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MOrderBAOTAOMallBookingServiceTest {
	
	@Autowired
	private BOTAOMallBookingService service;
	
	@Test
	public void testAddBooking() throws OrderException, Exception{
		
		MAddBookingParam addBookingParam = new MAddBookingParam();
		addBookingParam.setGoodsId(14L);
		addBookingParam.setTotalAmount(9000);
		addBookingParam.setQuantity(2);
		addBookingParam.setConsigneeName("Zhangsan");
		addBookingParam.setConsigneeMobile("13999999999");
		addBookingParam.setConsigneeAddress("宇宙");
		addBookingParam.setShippingType(1);
		addBookingParam.setPlatformId(1);
		addBookingParam.setName("李四");
		addBookingParam.setMobile("13777777777");
		addBookingParam.setMemberId(181295316);
		addBookingParam.setResource(2);
		addBookingParam.setSellStrategy(1);
		addBookingParam.setSubResource(10086);
		//addBookingParam.setPoint(8000);
		
		ResultVo<MAddBookResponse> addBooking = service.addBooking(addBookingParam);
		System.out.println(addBooking);
	}
	
}