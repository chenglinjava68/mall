/*package com.plateno.testservice;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.bean.request.custom.AddBookingParam;
import com.plateno.booking.internal.bean.request.custom.AddBookingParam.BookInfo;
import com.plateno.booking.internal.bean.request.custom.OrderParam;
import com.plateno.booking.internal.bean.request.lvmama.order.TravellersInfo;
import com.plateno.booking.internal.common.util.number.StringUtil;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.api.ApiService;
import com.plateno.cache.RedisHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestApiService {
	
	private final static Logger logger = Logger.getLogger(TestApiService.class);
	
	@Autowired
	private ApiService apiService;
	
	*//**
	 * 测试重发短信接口
	 * 
	 *//*
	@Test
	public void testSendMessage(){
		ResultVo<Object> output = new ResultVo<Object>();
		OrderParam orderParam = new OrderParam();
		orderParam.setTradeNo("B1468814074243774");
		orderParam.setChannel(1);
		apiService.SendMessageService(orderParam,output);
	}
	
	@Test
	public void testQueryOrderListByOrderNo(){
		ResultVo<Object> output = new ResultVo<Object>();
		OrderParam orderParam = new OrderParam();
		orderParam.setTradeNo("B1468814074243774");
		orderParam.setChannel(1);
		apiService.QueryOrderListByOrderNo(orderParam,output);
	}
	
	@Test
	public void testValidate(){
		ResultVo<Object> output = new ResultVo<Object>();
		AddBookingParam addBookingParam = new AddBookingParam();
		addBookingParam.setGoodsId(new Long(15820));
		addBookingParam.setVisitDate(new Long("1469808000000"));
		addBookingParam.setSellPrice(8000);
		addBookingParam.setChannelGoodsId(new Long(2347909));
		addBookingParam.setChannelProductId(new Long(182965));
		addBookingParam.setMarketPrice(8000);
		addBookingParam.setTicketType(5);
		addBookingParam.setGoodsType(2);
		addBookingParam.setLowestPrice(0);
		addBookingParam.setRate(4);
		addBookingParam.setType(1);
		addBookingParam.setTime(new Long(0));
		addBookingParam.setQuantity(1);
		addBookingParam.setMemberId(135962183);
		addBookingParam.setTotalAmount(8000);
		addBookingParam.setChannel(1);
		addBookingParam.setPlatformId(111);
		addBookingParam.setOpenId("oQqvKt8T-TiYeMzEIuPqXN5OB_b4");
		BookInfo bookInfo = new BookInfo();
		bookInfo.setName("刘练源");
		bookInfo.setMobile("13570963376");
		bookInfo.setEmail("2412903400@qq.com");
		addBookingParam.setBookInfo(bookInfo);
		List<TravellersInfo> travellers = new ArrayList<TravellersInfo>();
		TravellersInfo e = new TravellersInfo();
		e.setName("刘练源");
		e.setMobile("13570963376");
		e.setEmail("2412903400@qq.com");
		e.setCredentialsType("ID_CARD");
		e.setCredentials("441522199204160612");
		travellers.add(e);
		addBookingParam.setTravellers(travellers);
		
		logger.info(apiService.Validate(addBookingParam,output));
		
	}
	
	@Test
	public void testBooking(){
		ResultVo<Object> output = new ResultVo<Object>();
		AddBookingParam addBookingParam = new AddBookingParam();
		addBookingParam.setGoodsId(new Long(15820));
		addBookingParam.setVisitDate(new Long("1469808000000"));
		addBookingParam.setSellPrice(8000);
		addBookingParam.setChannelGoodsId(new Long(2347909));
		addBookingParam.setChannelProductId(new Long(182965));
		addBookingParam.setMarketPrice(8000);
		addBookingParam.setTicketType(5);
		addBookingParam.setGoodsType(2);
		addBookingParam.setLowestPrice(0);
		addBookingParam.setRate(4);
		addBookingParam.setType(1);
		addBookingParam.setTime(new Long(0));
		addBookingParam.setQuantity(1);
		addBookingParam.setMemberId(135962183);
		addBookingParam.setTotalAmount(8000);
		addBookingParam.setChannel(1);
		addBookingParam.setPlatformId(111);
		addBookingParam.setOpenId("oQqvKt8T-TiYeMzEIuPqXN5OB_b4");
		BookInfo bookInfo = new BookInfo();
		bookInfo.setName("刘练源");
		bookInfo.setMobile("13570963376");
		bookInfo.setEmail("2412903400@qq.com");
		addBookingParam.setBookInfo(bookInfo);
		List<TravellersInfo> travellers = new ArrayList<TravellersInfo>();
		TravellersInfo e = new TravellersInfo();
		e.setName("刘练源");
		e.setMobile("13570963376");
		e.setEmail("2412903400@qq.com");
		e.setCredentialsType("ID_CARD");
		e.setCredentials("441522199204160612");
		travellers.add(e);
		addBookingParam.setTravellers(travellers);
		String orderNo = StringUtil.getCurrentAndRamobe("O");
		logger.info(apiService.Booking(addBookingParam,orderNo,output));
		
	}
	
	@Test
	public void testRedis(){
		System.out.println(RedisHelper.setnx("hell", "123", 1000000));
	}

}
*/