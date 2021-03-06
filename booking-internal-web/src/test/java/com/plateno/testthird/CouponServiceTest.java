package com.plateno.testthird;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.coupon.constant.CouponPlatformType;
import com.plateno.booking.internal.coupon.service.CouponService;
import com.plateno.booking.internal.coupon.vo.Conditions;
import com.plateno.booking.internal.coupon.vo.QueryParam;
import com.plateno.booking.internal.coupon.vo.QueryResponse;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CouponServiceTest {
    
    @Autowired
    private CouponService couponService;
    
    @Test
    public void testQuery(){
        QueryParam param = new QueryParam();
        param.setPlatformId(CouponPlatformType.fromResource(2).getPlatformId());
        param.setCouponId(37695);
        param.setMebId(135944358);
        ResultVo<QueryResponse> result = couponService.queryCoupon(param);
        System.out.println(result.getData().toString());
    }
    
    @Test
    public void test(){
        Integer a = new Integer(3);
        int b = 3;
        Integer c = new Integer(3);
        System.out.println(a==b);
        System.out.println(a == c);
    }
    
}
