package com.plateno.booking.internal.base.model;

public class ProviderOrderDetailParam extends BaseParam{

    /** 
    * @Fields serialVersionUID : TODO
    */ 
    private static final long serialVersionUID = 1L;
    /**
     * 子订单号
     */
    private String orderSubNo;

    public String getOrderSubNo() {
        return orderSubNo;
    }

    public void setOrderSubNo(String orderSubNo) {
        this.orderSubNo = orderSubNo;
    }
    
    
    
    
}
