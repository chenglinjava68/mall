package com.plateno.booking.internal.cashierdesk.vo;

public class RefundOrderPayVo {
    private String payCode;
    private String payMessage;
    private String platenopayRefundFlowNo;//网关收银台退款流水号
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
