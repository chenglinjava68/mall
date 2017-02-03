package com.plateno.booking.internal.cashierdesk.vo;

public class RefundOrderResponse {
    private Integer resultCode;//如果网关收银台返回交易成功，值为100。网关返回其他，值为其他。100请求成功，300请求参数有误,500系统繁忙，请稍后再试
    private String resultMsg;
    private String result;
    private String payCode;// 网关收银台返回的响应码
    private String payMessage;//网关收银台返回的响应消息 
    private String platenopayRefundFlowNo;//网关收银台退款流水号 
    public Integer getResultCode() {
        return resultCode;
    }
    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }
    public String getResultMsg() {
        return resultMsg;
    }
    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getPayCode() {
        return payCode;
    }
    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
    public String getPayMessage() {
        return payMessage;
    }
    public void setPayMessage(String payMessage) {
        this.payMessage = payMessage;
    }
    public String getPlatenopayRefundFlowNo() {
        return platenopayRefundFlowNo;
    }
    public void setPlatenopayRefundFlowNo(String platenopayRefundFlowNo) {
        this.platenopayRefundFlowNo = platenopayRefundFlowNo;
    }
    
    
}
