package com.plateno.testservice;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.coupon.constant.CouponEnum;
import com.plateno.booking.internal.coupon.constant.CouponPlatformType;
import com.plateno.booking.internal.coupon.service.CouponService;
import com.plateno.booking.internal.coupon.vo.BaseResponse;
import com.plateno.booking.internal.coupon.vo.CancelParam;
import com.plateno.booking.internal.coupon.vo.CancelResponse;
import com.plateno.booking.internal.coupon.vo.Conditions;
import com.plateno.booking.internal.coupon.vo.QueryParam;
import com.plateno.booking.internal.coupon.vo.QueryResponse;
import com.plateno.booking.internal.coupon.vo.UseParam;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CouponServiceTest {
	
	@Autowired
	private CouponService service;
	
	@Test
	public void testQueryCoupon() throws IOException {
		QueryParam param = new QueryParam();
		param.setPlatformId(CouponPlatformType.APP.getPlatformId());
		//param.setCouponId(19744);
		param.setMebId(181295316);
		param.setAddBusType(CouponEnum.MONEY_COUPON.getType());
		param.setAddSubBusType(CouponEnum.MONEY_COUPON.getSubType());
		Conditions conditions = new Conditions();
		param.setConditions(conditions);
		conditions.setOrderAmount(new BigDecimal("50"));
		conditions.setProductId(2);
		conditions.setCategoryId(102);
		
		ResultVo<QueryResponse> result = service.queryCoupon(param);
		
		System.out.println(result);
		
	}
	
	@Test
	public void testUseCoupon() throws IOException {
		UseParam useCouponParam = new UseParam();
		useCouponParam.setCouponId(19747);
		useCouponParam.setMebId(181295316);
		useCouponParam.setOrderCode("1234567896");
		
		Conditions conditions = new Conditions();
		useCouponParam.setConditions(conditions);
		conditions.setOrderAmount(new BigDecimal("50"));
		conditions.setProductId(7);
		conditions.setCategoryId(109);
		
		ResultVo<BaseResponse> useCouponResult = service.useCoupon(useCouponParam);

		System.out.println(useCouponResult);
	}
	
	@Test
	public void testCancelCoupon() throws IOException {
		
		CancelParam param = new CancelParam();
		param.setCouponId(19747);
		param.setMebId(181295316);

		ResultVo<CancelResponse> cancelCouponResult = service.cancelCoupon(param);
		
		System.out.println(cancelCouponResult);
	}
}