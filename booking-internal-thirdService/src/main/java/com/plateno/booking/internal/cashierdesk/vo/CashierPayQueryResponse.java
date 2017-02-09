package com.plateno.booking.internal.cashierdesk.vo;

public class CashierPayQueryResponse {
    private Integer msgCode;//交易成功返回100，其他情况返回其他值。
    private String message;
    private PayQueryVo result;
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
    public PayQueryVo getResult() {
        return result;
    }
    public void setResult(PayQueryVo result) {
        this.result = result;
    }
    
    
}
  
 
   