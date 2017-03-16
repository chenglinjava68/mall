package com.plateno.booking.internal.service.order;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.plateno.booking.internal.base.constant.PayStatusEnum;
import com.plateno.booking.internal.base.mapper.OrderSubMapper;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.base.pojo.OrderSub;
import com.plateno.booking.internal.base.pojo.OrderSubExample;

@Service
public class OrderSubService {
    @Autowired
    private OrderSubMapper orderSubMapper;
    
    
    public void insertOrderSub(Map<String,OrderSub> orderSubMap){
        Iterator<Map.Entry<String,OrderSub>> iter = orderSubMap.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry<String,OrderSub> entry = (Map.Entry<String,OrderSub>) iter.next();
            OrderSub orderSub = entry.getValue();
            orderSubMapper.insertSelective(orderSub);
        }
    }
    
    public void updateToPayStatus_4(String orderSubNo){
        OrderSub orderSub = new OrderSub();
        orderSub.setSubFlag(PayStatusEnum.PAY_STATUS_4.getPayStatus());
        OrderSubExample example = new OrderSubExample();
        example.createCriteria().andOrderSubNoEqualTo(orderSubNo);
        orderSubMapper.updateByExampleSelective(orderSub, example);
    }
    
    
    public void updateToPayStatus(String orderNo,Integer payStauts){
        OrderSub orderSub = new OrderSub();
        orderSub.setSubFlag(payStauts);
        OrderSubExample example = new OrderSubExample();
        example.createCriteria().andOrderNoEqualTo(orderNo);
        orderSubMapper.updateByExampleSelective(orderSub, example);
    }
    
    
    /**
     * 
    * @Title: hasAllDeliver 
    * @Description: 查询子订单是否全部发货
    * @param @param orderNo
    * @param @return    
    * @return boolean    
    * @throws
     */
    public boolean hasAllDeliver(String orderNo){
        OrderSubExample example = new OrderSubExample();
        example.createCriteria().andOrderNoEqualTo(orderNo);
        boolean flag = true;
        List<OrderSub> orderSubs = orderSubMapper.selectByExample(example);
        for(OrderSub orderSub : orderSubs){
            if(orderSub.getSubFlag().compareTo(PayStatusEnum.PAY_STATUS_4.getPayStatus()) != 0)
                flag = false;
        }
        return flag;
    }
    
    public void doUpdateOrderSubStatusToPay(Order order){
        OrderSubExample example = new OrderSubExample();
        example.createCriteria().andOrderNoEqualTo(order.getOrderNo());
        OrderSub orderSub = new OrderSub();
        orderSub.setSubFlag(PayStatusEnum.PAY_STATUS_3.getPayStatus());
        // 线下交易，则订单状态为已发货
        if (null != order.getOffline() && order.getOffline() == 1) {
            orderSub.setSubFlag(PayStatusEnum.PAY_STATUS_4.getPayStatus());
        }
        orderSubMapper.updateByExampleSelective(orderSub, example);
    }
    
    /**
     * 
    * @Title: queryOrderSubByOrderNo 
    * @Description: 根据父订单号查询子订单
    * @param @param orderNo
    * @param @return    
    * @return List<OrderSub>    
    * @throws
     */
    public List<OrderSub> queryOrderSubByOrderNo(String orderNo){
        OrderSubExample example = new OrderSubExample();
        example.createCriteria().andOrderNoEqualTo(orderNo);
        List<OrderSub> list = orderSubMapper.selectByExample(example);
        return list;
    }
    
    public List<String> queryOrderSubStrByOrderNo(String orderNo){
        List<OrderSub> orderSubs = queryOrderSubByOrderNo(orderNo);
        List<String> orderSubStrs = Lists.newArrayList();
        for(OrderSub orderSub : orderSubs){
            orderSubStrs.add(orderSub.getOrderSubNo());
        }
        return orderSubStrs;
    }
    
}
