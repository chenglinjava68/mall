package com.plateno.testservice;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.base.model.SelectOrderParam;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.common.LstOrder;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.bean.request.custom.ReceiptParam;
import com.plateno.booking.internal.bean.response.custom.OrderDetail;
import com.plateno.booking.internal.bean.response.custom.SelectOrderResponse;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.goods.MallGoodsService;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.order.MOrderService;
import com.plateno.booking.internal.wechat.model.ProductSkuBean;
import com.plateno.booking.internal.wechat.model.ProductSkuBean.SkuPropertyInfos;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class MOrderServiceTest {
	
	@Autowired
	private MOrderService service;
	
	@Autowired
	private MallGoodsService mallGoodsService;
	
	@Test
	public void testModifyReceiptInfo() throws OrderException, Exception{
		
		ReceiptParam receiptParam = new ReceiptParam();
		receiptParam.setOrderNo("O1474959953609872686");
		receiptParam.setReceiptName("张思");
		receiptParam.setReceiptMobile("13888888888");
		receiptParam.setReceiptAddress("银河系");
		receiptParam.setOperateUserid("123456");
		receiptParam.setOperateUsername("管理员");
		receiptParam.setPlateForm(1);
		
		ResultVo<Object> modifyReceiptInfo = service.modifyReceiptInfo(receiptParam );
		System.out.println("结果：" + modifyReceiptInfo);
	}
	
	@Test
	public void testGetOrderDetail() throws OrderException, Exception{
		
		
		MOrderParam orderParam = new MOrderParam();
		orderParam.setOrderNo("O1477969364866126107");
		ResultVo<OrderDetail> orderDetail = service.getOrderDetail(orderParam );
		System.out.println("结果：" + orderDetail);
	}
	
	@Test
	public void testGetProductAndskuStock() throws OrderException, Exception{
		
		
		ProductSkuBean productAndskuStock = mallGoodsService.getProductAndskuStock("6");
		System.out.println(productAndskuStock);
		
	}
	
	@Test
	public void testQueryOrderByPage() throws OrderException, Exception{
		
		
		SelectOrderParam param = new SelectOrderParam();
		param.setChannelId(2);
		param.setRequstPlatenoform(3);
		
		ResultVo<LstOrder<SelectOrderResponse>> queryOrderByPage = service.queryOrderByPage(param);
		System.out.println(queryOrderByPage);
		
	}

}