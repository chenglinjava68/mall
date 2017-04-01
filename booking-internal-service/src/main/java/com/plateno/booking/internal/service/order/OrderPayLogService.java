package com.plateno.booking.internal.service.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.base.mapper.OrderPayLogMapper;
import com.plateno.booking.internal.base.pojo.OrderPayLog;
import com.plateno.booking.internal.base.pojo.OrderPayLogExample;
import com.plateno.booking.internal.bean.contants.BookingConstants;

@Service
public class OrderPayLogService {

    @Autowired
    private OrderPayLogMapper orderPayLogMapper;
    
    /**
     * 
    * @Title: queryOrderPayLogInPayByOrderId 
    * @Description: 根据orderId查询支付流水
    * @param @param orderId
    * @param @return    
    * @return List<OrderPayLog>    
    * @throws
     */
    public List<OrderPayLog> queryOrderPayLogInPayByOrderId(int orderId){
        OrderPayLogExample example = new OrderPayLogExample();
        example.createCriteria().andOrderIdEqualTo(orderId).andTypeEqualTo(1).andStatusEqualTo(BookingConstants.BILL_LOG_NORMAL);
        List<OrderPayLog> orderPayLogs = orderPayLogMapper.selectByExample(example);
        return orderPayLogs;
    }
    
}
