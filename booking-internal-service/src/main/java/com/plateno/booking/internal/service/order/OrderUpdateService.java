package com.plateno.booking.internal.service.order;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.base.constant.PayStatusEnum;
import com.plateno.booking.internal.base.mapper.OrderMapper;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;

@Service
public class OrderUpdateService {

    @Autowired
    private OrderMapper orderMapper;
    
    /**
     * 
    * @Title: updateToPayStatus_4 
    * @Description: 修改为待收货状态
    * @param @param order    
    * @return void    
    * @throws
     */
    public void updateToPayStatus_4(Order order){
        order.setPayStatus(PayStatusEnum.PAY_STATUS_4.getPayStatus());// 待发货==>待收货
        order.setDeliverTime(new Date());
        orderMapper.updateByPrimaryKeySelective(order);
    }
   
    
    public void updateToPayStatus_14(Order order){
        order.setPayStatus(PayStatusEnum.PAY_STATUS_14.getPayStatus());// 部分发货
        order.setDeliverTime(new Date());
        orderMapper.updateByPrimaryKeySelective(order);
    }
    
    /**
     * 
    * @Title: updateToPayStatus_5 
    * @Description: 修改订单状态为已完成
    * @param @param order    
    * @return void    
    * @throws
     */
    public void updateToPayStatus_5(Order order){
        order.setPayStatus(BookingResultCodeContants.PAY_STATUS_5);// 确定收货操作==>已完成
        order.setUpTime(new Date());
        orderMapper.updateByPrimaryKeySelective(order);
    }
    
}
