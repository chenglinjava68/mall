/*package com.plateno.testthird;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.request.convert.ConvertBookingParam;
import com.plateno.booking.internal.bean.request.convert.ConvertPayParam;
import com.plateno.booking.internal.bean.request.custom.AddBookingParam;
import com.plateno.booking.internal.bean.request.custom.AddBookingParam.BookInfo;
import com.plateno.booking.internal.bean.request.custom.OrderParam;
import com.plateno.booking.internal.bean.request.lvmama.order.TravellersInfo;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.common.util.number.MD5Util;
import com.plateno.booking.internal.common.util.number.StringUtil;
import com.plateno.booking.internal.dao.mapper.BillMapper;
import com.plateno.booking.internal.dao.pojo.BillDetails;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.thirdService.impl.LvMaMaService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestLvMaMa {
	
	private final static Logger logger = Logger.getLogger(TestLvMaMa.class);
	@Autowired
	private LvMaMaService lvMaMaService;
	@Autowired
	private BillMapper billMapper;
	
	*//**
	 * 驴妈妈短信重发接口
	 * 
	 * @throws IOException
	 *//*
	@Test
	public void testSendMessage() throws IOException{
		ResultVo<Object> output = new ResultVo<Object>();
		OrderParam orderParam = new OrderParam();
		orderParam.setTradeNo("B1468658066984788");
		BillDetails billDetails = billMapper.getBillByTradeNo(orderParam);
		output = lvMaMaService.SendMessageService(billDetails,output);
		logger.info(JsonUtils.toJsonString(output));
	}
	
	*//**
	 * 驴妈妈查询订单信息接口
	 * 
	 * @throws IOException
	 *//*
	@Test
	public void testQueryOrderListByOrderNo() throws IOException{
		OrderParam orderParam = new OrderParam();
		orderParam.setTradeNo("B1468658066984788");
		BillDetails billDetails = billMapper.getBillByTradeNo(orderParam);
		List<String> tradeNos = new ArrayList<String>();
		ResultVo<Object> output = new ResultVo<Object>();
		tradeNos.add("B1468658066984788");
		tradeNos.add("B1468591512747127");
		List<BillDetails> list = billMapper.selectBillByTradeNo(tradeNos, null);
		//list.add(billDetails);
		output = lvMaMaService.QueryOrderListByOrderNo(list,output);
		logger.info(JsonUtils.toJsonString(output));
	}
	
	*//**
	 * 驴妈妈下单校验接口
	 * 
	 * @throws IOException
	 *//*
	@Test
	public void testValidate() throws Exception{
		ResultVo<Object> output = new ResultVo<Object>();
		AddBookingParam addBookingParam = new AddBookingParam();
		addBookingParam.setGoodsId(new Long(15820));
		addBookingParam.setVisitDate(new Long("1468808626000"));
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
		ConvertBookingParam convertBookingParam = new ConvertBookingParam();
		BeanUtils.copyProperties(addBookingParam, convertBookingParam);
		output = lvMaMaService.Validate(convertBookingParam,output);
		logger.info(JsonUtils.toJsonString(output));
	}
	
	*//**
	 * 驴妈妈下单接口
	 * 
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 *//*
	@Test
	public void testBooking() throws Exception{
		ResultVo<Object> output = new ResultVo<Object>();
		AddBookingParam addBookingParam = new AddBookingParam();
		addBookingParam.setGoodsId(new Long(15820));
		addBookingParam.setVisitDate(new Long("1469721600000"));
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
		
		ConvertBookingParam convertBookingParam = new ConvertBookingParam();
		BeanUtils.copyProperties(addBookingParam, convertBookingParam);
		convertBookingParam.setOrderNo(StringUtil.getCurrentAndRamobe("O"));
		output = lvMaMaService.Booking(convertBookingParam,output);
		logger.info(JsonUtils.toJsonString(output));
	}
	
	
	*//**
	 * 驴妈妈支付接口
	 * 
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 *//*
	@Test
	public void testPay() throws Exception{
		ResultVo<Object> output = new ResultVo<Object>();
		OrderParam orderParam = new OrderParam();
		orderParam.setTradeNo("B1468814074243774");
		BillDetails billDetails = billMapper.getBillByTradeNo(orderParam);
		ConvertPayParam payParam = new ConvertPayParam();
		BeanUtils.copyProperties(billDetails, payParam);
		payParam.setReferenceId(UUID.randomUUID().toString());
		
		output = lvMaMaService.Pay(payParam,output);
		logger.info(JsonUtils.toJsonString(output));
	}
	
	
	*//**
	 * 驴妈妈退款接口
	 * 
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 *//*
	@Test
	public void testRefund() throws Exception{
		ResultVo<Object> output = new ResultVo<Object>();
		OrderParam orderParam = new OrderParam();
		orderParam.setTradeNo("B1468814074243774");
		BillDetails billDetails = billMapper.getBillByTradeNo(orderParam);
		
		output = lvMaMaService.Refund(billDetails,output);
		logger.info(JsonUtils.toJsonString(output));
	}
	
	public void test(){
		String uuid = UUID.randomUUID().toString();
		String signStr = uuid + BookingConstants.BILLLOG_KEY + "123";
		String tmpSign = MD5Util.encode(signStr);
		System.out.println(tmpSign);
	}

}
*/