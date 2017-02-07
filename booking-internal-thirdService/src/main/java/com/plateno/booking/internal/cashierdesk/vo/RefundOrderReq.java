package com.plateno.booking.internal.cashierdesk.vo;

public class RefundOrderReq extends CashierBaseParam{

    private String tradeNo;//支付流水号
    private String refundTradeNo;//退款流水号
    private String refundOrderNo;//退款流水号
    private Integer memberId;//会员ID
    private Integer amount;//订单金额
    private String appId;//商城为mall
    public String getTradeNo() {
        return tradeNo;
    }
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
    public String getRefundTradeNo() {
        return refundTradeNo;
    }
    public void setRefundTradeNo(String refundTradeNo) {
        this.refundTradeNo = refundTradeNo;
    }
    public String getRefundOrderNo() {
        return refundOrderNo;
    }
    public void setRefundOrderNo(String refundOrderNo) {
        this.refundOrderNo = refundOrderNo;
    }
    public Integer getMemberId() {
        return memberId;
    }
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    

    
    
    
}
