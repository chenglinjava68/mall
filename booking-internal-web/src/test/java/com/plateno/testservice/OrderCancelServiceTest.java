package com.plateno.testservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.order.OrderCancelService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OrderCancelServiceTest {

    @Autowired
    private OrderCancelService orderCancelService;
    
    @Test
    public void testCancelOrder() throws OrderException, Exception{
        
        MOrderParam orderParam = new MOrderParam();
        orderParam.setOrderNo("O1483513542892102624");
        orderParam.setMemberId(181295316);
        orderParam.setType(2);
        ResultVo<Object> userRefund = orderCancelService.cancelOrderLock(orderParam);
        System.out.println(userRefund);
        
    }
}
