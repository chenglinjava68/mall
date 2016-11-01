/*package com.plateno.testservice;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.bean.request.common.LstOrder;
import com.plateno.booking.internal.bean.request.custom.BillSelectParam;
import com.plateno.booking.internal.bean.request.custom.OrderParam;
import com.plateno.booking.internal.bean.response.custom.CustomBillDetail;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.order.OrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestOrderService {
	
	private final static Logger logger = Logger.getLogger(TestOrderService.class);
	
	@Autowired
	private OrderService orderService;

	@Test
	public void testQueryOrderByPage() throws Exception{
		BillSelectParam param = new BillSelectParam();
		param.setMemberId(135962183);
		ResultVo<LstOrder<CustomBillDetail>> vo =  orderService.queryOrderByPage(param);
		
		logger.info(JsonUtils.toJsonString(vo));
	}
	
	@Test
	public void testGetBillDetail() throws Exception{
		OrderParam param = new OrderParam();
		//param.setTradeNo("B1468593514347707");
		ResultVo<CustomBillDetail> vo =  orderService.getBillDetail(param);
		
		logger.info(JsonUtils.toJsonString(vo));
	}
	
	@Test
	public void testDeleteBill() throws Exception{
		OrderParam param = new OrderParam();
		param.setTradeNo("B1468593514347707");
		ResultVo<Object> vo =  orderService.deleteOrder(param);
		
		logger.info(JsonUtils.toJsonString(vo));
	}
}
*/