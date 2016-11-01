package com.plateno.testservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.base.model.SelectOrderParam;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.common.LstOrder;
import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.bean.request.custom.ReceiptParam;
import com.plateno.booking.internal.bean.response.custom.MAddBookResponse;
import com.plateno.booking.internal.bean.response.custom.OrderDetail;
import com.plateno.booking.internal.bean.response.custom.SelectOrderResponse;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.fromTicket.BOTAOMallBookingService;
import com.plateno.booking.internal.wechat.model.ProductSkuBean;

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
		addBookingParam.setGoodsId(1L);
		addBookingParam.setTotalAmount(100);
		addBookingParam.setQuantity(2);
		addBookingParam.setConsigneeName("Zhangsan");
		addBookingParam.setConsigneeMobile("138888888888");
		addBookingParam.setConsigneeAddress("宇宙");
		addBookingParam.setShippingType(2);
		addBookingParam.setPlatformId(1);
		addBookingParam.setName("李四");
		addBookingParam.setMobile("13777777777");
		addBookingParam.setMemberId(12345678);
		addBookingParam.setResource(2);
		addBookingParam.setSellStrategy(1);
		addBookingParam.setPoint(0);
		
		ResultVo<MAddBookResponse> addBooking = service.addBooking(addBookingParam);
		System.out.println(addBooking);
	}

}