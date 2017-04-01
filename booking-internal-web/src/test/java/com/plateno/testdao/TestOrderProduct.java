package com.plateno.testdao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.base.mapper.OrderProductMapper;
import com.plateno.booking.internal.base.pojo.OrderProduct;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestOrderProduct {

    @Autowired
    private OrderProductMapper orderProductMapper;
    
    @Test
    public void testInsert(){
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrderNo("0001");
        orderProduct.setOrderSubNo("0001001");
        orderProduct.setPrice(1);
        orderProduct.setSkuid(1);
        orderProduct.setSkuCount(1);
        orderProductMapper.insertSelective(orderProduct);
    }
    
    @Test
    public void test_queryProductByPackageId(){
        List<OrderProduct> list = orderProductMapper.queryProductByPackageId(1);
        System.out.println(list.get(0).getProductName());
    }
    
}
