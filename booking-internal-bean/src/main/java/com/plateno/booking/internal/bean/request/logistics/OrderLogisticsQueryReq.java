package com.plateno.booking.internal.bean.request.logistics;

import com.plateno.booking.internal.base.model.BaseParam;

public class OrderLogisticsQueryReq extends BaseParam{
    /** 
    * @Fields serialVersionUID : TODO
    */ 
    private static final long serialVersionUID = 1L;
    
    /**
     * 父订单号
     */
    private String orderNo;

    /**
     * 子订单号
     */
    private String orderSubNo;
    
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderSubNo() {
        return orderSubNo;
    }

    public void setOrderSubNo(String orderSubNo) {
        this.orderSubNo = orderSubNo;
    }
    
    
    
    
}
