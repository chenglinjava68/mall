package com.plateno.booking.internal.cashierdesk.vo;

public class RefundOrderResponse {
    private Integer msgCode;// 如果网关收银台返回交易成功，值为100。网关返回其他，值为其他。100请求成功，300请求参数有误,500系统繁忙，请稍后再试
    private String message;
    private RefundOrderPayVo result;

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

    public RefundOrderPayVo getResult() {
        return result;
    }

    public void setResult(RefundOrderPayVo result) {
        this.result = result;
    }



}
