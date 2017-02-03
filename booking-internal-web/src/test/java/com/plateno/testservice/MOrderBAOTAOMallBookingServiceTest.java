package com.plateno.testservice;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.bean.request.custom.MOrderGoodsParam;
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
		MOrderGoodsParam orderGoodsParam = new MOrderGoodsParam();
		orderGoodsParam.setGoodsId(5L);
		orderGoodsParam.setQuantity(1);
		List<MOrderGoodsParam> goodsList = new ArrayList<MOrderGoodsParam>();
		goodsList.add(orderGoodsParam);
		addBookingParam.setGoodsList(goodsList);
//		addBookingParam.setGoodsId(5L);
		addBookingParam.setTotalAmount(13800+11);
//		addBookingParam.setQuantity(1);
		addBookingParam.setConsigneeName("Zhangsan");
		addBookingParam.setConsigneeMobile("13999999999");
		addBookingParam.setConsigneeAddress("宇宙");
		addBookingParam.setProvince("广东省");
		addBookingParam.setCity("广州市");
		addBookingParam.setArea("番禺区");
		addBookingParam.setProvince("广东省");
		addBookingParam.setShippingType(2);
		addBookingParam.setPlatformId(1);
		addBookingParam.setName("李四");
		addBookingParam.setMobile("13777777777");
		addBookingParam.setMemberId(181295316);
		addBookingParam.setResource(2);
		addBookingParam.setSellStrategy(1);
		addBookingParam.setSubResource(10086);
		addBookingParam.setPoint(0);
		//addBookingParam.setCouponId(19742);
		
		ResultVo<MAddBookResponse> addBooking = service.addBooking(addBookingParam);
		System.out.println(addBooking);
	}
	
}