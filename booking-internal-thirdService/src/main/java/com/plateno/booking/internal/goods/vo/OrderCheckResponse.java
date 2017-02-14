package com.plateno.booking.internal.goods.vo;

public class OrderCheckResponse {

    private String resultCode;
    
    private String resultMsg;
    
    private OrderCheckDetail data;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public OrderCheckDetail getData() {
        return data;
    }

    public void setData(OrderCheckDetail data) {
        this.data = data;
    }
    
    
    
}
