package com.plateno.testthird;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.plateno.booking.internal.bean.request.custom.MOrderGoodsParam;
import com.plateno.booking.internal.goods.MallGoodsService;
import com.plateno.booking.internal.goods.vo.OrderCheckDetail;
import com.plateno.booking.internal.goods.vo.OrderCheckParamInfo;
import com.plateno.booking.internal.goods.vo.OrderCheckReq;

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
    
    @Test
    public void test_commitOrder(){
        
        OrderCheckReq req = new OrderCheckReq();
        List<OrderCheckParamInfo> commitOrderInfos = Lists.newArrayList();
        OrderCheckParamInfo commitOrderInfo = new OrderCheckParamInfo();
        commitOrderInfo.setGoodsId(16L);
        commitOrderInfo.setQuantity(1);
        commitOrderInfos.add(commitOrderInfo);
//        OrderCheckParamInfo commitOrderInfo2 = new OrderCheckParamInfo();
//        commitOrderInfo2.setGoodsId(14L);
//        commitOrderInfo2.setQuantity(1);
//        commitOrderInfos.add(commitOrderInfo2);
        req.setOrderCheckParamInfos(commitOrderInfos);
        req.setMemberPoints(2000);
        OrderCheckDetail detail = mallGoodsService.orderCheck(req);
        System.out.println(detail.getTotalPrice());
        
        
    }
    
}
