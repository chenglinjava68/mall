package com.plateno.booking.internal.cashierdesk.vo;

public class PayQueryVo {
    private String code;
    private String message;
    private String subject;//商品名称 
    private Integer amount;//收银台支付金额
    private Integer paymentType;//支付方式
    private Integer mebid;//会员ID
    private String merchantNo;//商户号
    private String tradeNo;//交易流水号
    private Integer gateId;//网关ID
    private Integer currencyDepositAmount;//储值总支付金额
    private Integer gatewayAmount;//网关总支付金额 
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public Integer getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }
    public Integer getMebid() {
        return mebid;
    }
    public void setMebid(Integer mebid) {
        this.mebid = mebid;
    }
    public String getMerchantNo() {
        return merchantNo;
    }
    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }
    public String getTradeNo() {
        return tradeNo;
    }
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
    public Integer getGateId() {
        return gateId;
    }
    public void setGateId(Integer gateId) {
        this.gateId = gateId;
    }
    public Integer getCurrencyDepositAmount() {
        return currencyDepositAmount;
    }
    public void setCurrencyDepositAmount(Integer currencyDepositAmount) {
        this.currencyDepositAmount = currencyDepositAmount;
    }
    public Integer getGatewayAmount() {
        return gatewayAmount;
    }
    public void setGatewayAmount(Integer gatewayAmount) {
        this.gatewayAmount = gatewayAmount;
    }
    
    
    
}
