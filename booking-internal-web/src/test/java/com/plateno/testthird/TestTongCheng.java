/*package com.plateno.testthird;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.bean.request.convert.ConvertBookingParam;
import com.plateno.booking.internal.bean.request.custom.AddBookingParam;
import com.plateno.booking.internal.bean.request.custom.AddBookingParam.BookInfo;
import com.plateno.booking.internal.bean.request.custom.OrderParam;
import com.plateno.booking.internal.bean.request.lvmama.order.TravellersInfo;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.common.util.number.StringUtil;
import com.plateno.booking.internal.dao.mapper.BillMapper;
import com.plateno.booking.internal.dao.pojo.BillDetails;
import com.plateno.booking.internal.dao.pojo.BillLogDetail;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.thirdService.impl.TongChengService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestTongCheng {

	private final static Logger logger = Logger.getLogger(TestTongCheng.class);
	@Autowired
	private TongChengService tongChengService;
	@Autowired
	private BillMapper billMapper;
	
	*//**
	 * 同程短信重发接口
	 * 
	 * @throws IOException
	 *//*
	@Test
	public void testSendMessage() throws IOException{
		ResultVo<Object> output = new ResultVo<Object>();
		OrderParam orderParam = new OrderParam();
		orderParam.setTradeNo("B1468658066984788");
		BillDetails billDetails = billMapper.getBillByTradeNo(orderParam);
		output = tongChengService.SendMessageService(billDetails,output);
		logger.info(JsonUtils.toJsonString(output));
	}
	
	
	*//**
	 * 同程查询订单信息接口
	 * 
	 * @throws IOException
	 *//*
	@Test
	public void testQueryOrderListByOrderNo() throws IOException{
		ResultVo<Object> output = new ResultVo<Object>();
		List<BillDetails> list = new ArrayList<BillDetails>();
		BillDetails billDetails = new BillDetails();
		billDetails.setPartnerOrderId("sz578c740521008b1865");
		list.add(billDetails);
		output = tongChengService.QueryOrderListByOrderNo(list,output);
		logger.info(JsonUtils.toJsonString(output));
	}
	
	
	*//**
	 * 同程查询订单信息接口(根据第三方流水号查询)
	 * 
	 * @throws IOException
	 *//*
	@Test
	public void testQueryOrderList() throws IOException{
		ResultVo<Object> output = new ResultVo<Object>();
		List<BillLogDetail> billLogDetails = new ArrayList<BillLogDetail>();
		BillLogDetail billLogDetail = new BillLogDetail();
		billLogDetail.setBillLogTradeNo("L1466495684937727");//billMapper.getBillLogByTradeNo("B1468633297998791", BookingResultCodeContants.PAYCODE_INTEGER);
		billLogDetails.add(billLogDetail);
		output = tongChengService.QueryOrderList(billLogDetails,output);
		logger.info(JsonUtils.toJsonString(output));
	}
	
	*//**
	 * 同程下单校验接口
	 * 
	 * @throws IOException
	 *//*
	@Test
	public void testValidate() throws Exception{
		ResultVo<Object> output = new ResultVo<Object>();
		AddBookingParam addBookingParam = new AddBookingParam();
		addBookingParam.setGoodsId(new Long(45708));
		addBookingParam.setVisitDate(new Long("1469808000000"));
		addBookingParam.setSellPrice(7800);
		addBookingParam.setChannelGoodsId(new Long(350785));
		addBookingParam.setChannelProductId(new Long(220399));
		addBookingParam.setMarketPrice(7800);
		addBookingParam.setTicketType(5);
		addBookingParam.setGoodsType(2);
		addBookingParam.setLowestPrice(0);
		addBookingParam.setRate(4);
		addBookingParam.setType(1);
		addBookingParam.setTime(new Long(0));
		addBookingParam.setQuantity(1);
		addBookingParam.setMemberId(135962183);
		addBookingParam.setTotalAmount(7800);
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
		output = tongChengService.Validate(convertBookingParam,output);
		logger.info(JsonUtils.toJsonString(output));
	}
	
	
	*//**
	 * 同程支付接口
	 * 
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 *//*
	@Test
	public void testPay() throws Exception{
		ResultVo<Object> output = new ResultVo<Object>();
		AddBookingParam addBookingParam = new AddBookingParam();
		addBookingParam.setGoodsId(new Long(15820));
		addBookingParam.setVisitDate(new Long("1469721600000"));
		addBookingParam.setSellPrice(1);
		addBookingParam.setChannelGoodsId(new Long(309626));
		addBookingParam.setChannelProductId(new Long(182965));
		addBookingParam.setMarketPrice(1);
		addBookingParam.setTicketType(5);
		addBookingParam.setGoodsType(2);
		addBookingParam.setLowestPrice(0);
		addBookingParam.setRate(4);
		addBookingParam.setType(1);
		addBookingParam.setTime(new Long(0));
		addBookingParam.setQuantity(1);
		addBookingParam.setMemberId(135962183);
		addBookingParam.setTotalAmount(1);
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
		output = tongChengService.Pay(convertBookingParam,output);
		logger.info(JsonUtils.toJsonString(output));
	}
	
	
	*//**
	 * 同程退款接口
	 * 
	 * @throws IOException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 *//*
	@Test
	public void testRefund() throws Exception{
		ResultVo<Object> output = new ResultVo<Object>();
		//OrderParam orderParam = new OrderParam();
		//orderParam.setTradeNo("B1468814074243774");
		BillDetails billDetails = new BillDetails();
		billDetails.setPartnerOrderId("sz5784a0102101599589");
		
		output = tongChengService.Refund(billDetails,output);
		logger.info(JsonUtils.toJsonString(output));
	}
	
}
*/