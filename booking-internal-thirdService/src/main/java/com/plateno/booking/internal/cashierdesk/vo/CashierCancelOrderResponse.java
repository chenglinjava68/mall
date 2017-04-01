package com.plateno.booking.internal.cashierdesk.vo;

public class CashierCancelOrderResponse {
    private Integer msgCode;//交易成功返回100，其他情况返回其他值。
    private String message;
    private boolean result;
    public Integer getMsgCode() {
        return msgCode;
    }
    public void setMsgCode(Integer msgCode) {
        this.msgCode = msgCode;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isResult() {
        return result;
    }
    public void setResult(boolean result) {
        this.result = result;
    }
    
    
    
}
