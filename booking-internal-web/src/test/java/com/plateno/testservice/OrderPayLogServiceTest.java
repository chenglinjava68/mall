package com.plateno.testservice;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.bean.util.JsonUtils;
import com.plateno.booking.internal.service.order.OrderPayLogService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OrderPayLogServiceTest {

    @Autowired
    private OrderPayLogService orderPayLogService;
    
    @Test
    public void test_queryOrderPayLogInPayByOrderId() throws IOException{
        System.out.println(JsonUtils.toJsonString(orderPayLogService.queryOrderPayLogInPayByOrderId(995111)));
    }
    
}
