package com.plateno.testthird;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.bean.request.custom.MOrderGoodsParam;
import com.plateno.booking.internal.goods.MallGoodsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestMallGoodsService {

    @Autowired
    private MallGoodsService mallGoodsService;
    
    @Test
    public void testDeductBatchStock(){
        
        List<MOrderGoodsParam> goodsList = new ArrayList<>();
        MOrderGoodsParam param1 = new MOrderGoodsParam();
        MOrderGoodsParam param2 = new MOrderGoodsParam();
        param1.setGoodsId(1L);
        param1.setQuantity(1);
        
        param2.setGoodsId(2L);
        param2.setQuantity(2);
        
        goodsList.add(param1);
        goodsList.add(param2);
        
        mallGoodsService.deductBatchStock(goodsList);
    }
    
    @Test
    public void testReturnBatchStock(){
        
        List<MOrderGoodsParam> goodsList = new ArrayList<>();
        MOrderGoodsParam param1 = new MOrderGoodsParam();
        MOrderGoodsParam param2 = new MOrderGoodsParam();
        param1.setGoodsId(1L);
        param1.setQuantity(1);
        
        param2.setGoodsId(2L);
        param2.setQuantity(2);
        
        goodsList.add(param1);
        goodsList.add(param2);
        
        mallGoodsService.returnBatchStock(goodsList);
    }
    
}
