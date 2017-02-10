package com.plateno.booking.internal.bean.request.logistics;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.plateno.booking.internal.base.model.BaseParam;

public class OrderLogisticsQueryReq extends BaseParam{
    /** 
    * @Fields serialVersionUID : TODO
    */ 
    private static final long serialVersionUID = 1L;
    
    @NotNull(message = "订单编号不能为空")
    @NotEmpty(message = "订单编号不能为空")
    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    
    
    
}
