package com.plateno.booking.internal.cashierdesk.vo;

public class CashierRefundQueryResponse {

    private Integer msgCode;
    private String message;
    private CashierRefundQueryVo result;
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
    public CashierRefundQueryVo getResult() {
        return result;
    }
    public void setResult(CashierRefundQueryVo result) {
        this.result = result;
    }
    
    
}
