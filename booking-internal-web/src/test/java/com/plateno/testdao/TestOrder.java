/*package com.plateno.testdao;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.dao.mapper.OrderMapper;
import com.plateno.booking.internal.dao.pojo.OrderDetail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestOrder {

	private final static Logger loger = Logger.getLogger(TestOrder.class);
	
	@Autowired
	private OrderMapper orderMapper;
	
	*//**
	 * 
	 * 根据第三方订单ID,获取本地的订单信息
	 * @throws IOException
	 *//*
	@Test
	public void testOrderDetailByPartnerOrderId() throws IOException{
		String partnerOrderId = "31673690";
		String orderNo = "O1468546635435731";
		OrderDetail orderDetail = orderMapper.getOrderDetailByPartnerOrderId(partnerOrderId, orderNo,181292705,null);
		loger.info(JsonUtils.toJsonString(orderDetail));
	}
	
	*//**
	 * 
	 * 根据订单ID,获取本地的订单信息
	 * @throws IOException
	 *//*
	@Test
	public void testGetOrderDetailByOrderNo() throws IOException{
		String orderNo = "O1468546635435731";
		OrderDetail orderDetail = orderMapper.getOrderDetailByOrderNo(orderNo,null,null);
		loger.info(JsonUtils.toJsonString(orderDetail));
	}

}
*/