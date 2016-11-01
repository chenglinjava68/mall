/*package com.plateno.testdao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.base.mapper.MBillMapper;
import com.plateno.booking.internal.base.pojo.MBill;
import com.plateno.booking.internal.base.pojo.MBillExample;
import com.plateno.booking.internal.bean.request.custom.BillSelectParam;
import com.plateno.booking.internal.bean.request.custom.OrderParam;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.dao.mapper.BillMapper;
import com.plateno.booking.internal.dao.pojo.BillDetails;
import com.plateno.booking.internal.dao.pojo.BillRefundDetail;
import com.plateno.booking.internal.service.order.BillService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestBill {
	
	private final static Logger loger = Logger.getLogger(TestBill.class);
	@Autowired
	private BillMapper billMapper;
	
	@Autowired
	private MBillMapper mBillMapper;
	@Autowired
	private BillService billService;
	
	*//**
	 * 测试自定义dao
	 * 
	 * @throws IOException
	 *//*
	@Test
	public void testSelectBill() throws IOException{
		BillSelectParam param = new BillSelectParam();
		param.setMemberId(135962183);
		param.setState("[301,500]");
		List<BillDetails> billReturns = billMapper.selectBill(param);
		loger.info(JsonUtils.toJsonString(billReturns));
	}
	
	*//**
	 * 测试自定义dao
	 * 
	 * @throws IOException
	 *//*
	@Test
	public void testSelectBillByTradeNo() throws IOException{
		List<String> param = new ArrayList<String>();
		param.add("B1468593010682812");
		param.add("B1468591512747127");
		List<BillDetails> billReturns = billMapper.selectBillByTradeNo(param,null);
		loger.info(JsonUtils.toJsonString(billReturns));
	}
	
	*//**
	 * 获取总数
	 * 
	 * @throws IOException
	 *//*
	@Test
	public void testCountBill() throws IOException{
		BillSelectParam param = new BillSelectParam();
		param.setMemberId(135962183);
		Integer num = billMapper.countBill(param);
		loger.info(num);
	}
	
	*//**
	 * 根据账单ID,分店ID,会员ID 获取订单信息
	 * 
	 * @throws IOException
	 *//*
	@Test
	public void testGetBillByTradeNo() throws IOException{
		OrderParam param = new OrderParam();
		param.setTradeNo("B1468482336049724");
		BillDetails  billDetails= billMapper.getBillByTradeNo(param);
		loger.info(JsonUtils.toJsonString(billDetails));
	}
	
	
	*//**
	 * 根据账单ID,获取退款详情
	 * 
	 * @throws IOException
	 *//*
	@Test
	public void testBillRefundDetail() throws IOException{
		List<BillRefundDetail>  billRefundDetail= billMapper.billRefundDetail("B1468482336049724", null, 135962183,1);
		loger.info(JsonUtils.toJsonString(billRefundDetail));
	}
	
	
	*//**
	 * 根据账单ID,更新账单信息
	 * 
	 * @throws IOException
	 *//*
	@Test
	public void testUpdateBillByTradeNo() throws IOException{
		Integer result = billMapper.updateBillByTradeNo("B1468482336049724", 301);
		loger.info(result);
	}
	
	*//**
	 * 根据支付流水号,获取对应的订单信息
	 * 
	 * @throws IOException
	 *//*
	@Test
	public void testGetBillDetailByBillLog() throws IOException{
		BillDetails  billDetails = billMapper.getBillDetailByBillLog("L1468482332465878");
		loger.info(JsonUtils.toJsonString(billDetails));
	}
	
	*//**
	 * 根据账单ID,获取订单的支付状态
	 * 
	 * @throws IOException
	 *//*
	@Test
	public void testGetBillLogByTradeNo() throws IOException{
		BillDetails  billDetails = billMapper.getBillLogByTradeNo("B1466405608511314","",1);
		loger.info(JsonUtils.toJsonString(billDetails));
	}
	
	*//**
	 * 测试基础生成dao
	 * 
	 * @throws IOException
	 *//*
	@Test
	public void testBaseselectBillByMemberId() throws IOException{
		List<Date> list =  mBillMapper.selectBillByMemberId(135962183);
		loger.info(JsonUtils.toJsonString(list));
	}
	
	*//**
	 * 测试基础生成dao
	 * 
	 * @throws IOException
	 *//*
	@Test
	public void testBaseSelectBill() throws IOException{
		MBillExample example = new MBillExample();
		example.createCriteria().andMemberidEqualTo(135962183);
		List<MBill> list =  mBillMapper.selectByExample(example);
		loger.info(JsonUtils.toJsonString(list));
	}
	
}
*/