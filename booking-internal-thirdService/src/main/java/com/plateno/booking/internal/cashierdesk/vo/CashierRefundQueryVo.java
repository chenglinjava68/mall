package com.plateno.booking.internal.cashierdesk.vo;

public class CashierRefundQueryVo {
    
    private String code;
    private String message;
    private Integer refundAmount;//退款金额
    private String merchantNo;//商户号
    private String tradeNo;//支付流水号
    private Integer gateId;
    private Integer refundCurrencyDepositAmount;//储值总退款金额
    private Integer refundGatewayAmount;//网关总退款金额
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
    public Integer getRefundAmount() {
        return refundAmount;
    }
    public void setRefundAmount(Integer refundAmount) {
        this.refundAmount = refundAmount;
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
    public Integer getRefundCurrencyDepositAmount() {
        return refundCurrencyDepositAmount;
    }
    public void setRefundCurrencyDepositAmount(Integer refundCurrencyDepositAmount) {
        this.refundCurrencyDepositAmount = refundCurrencyDepositAmount;
    }
    public Integer getRefundGatewayAmount() {
        return refundGatewayAmount;
    }
    public void setRefundGatewayAmount(Integer refundGatewayAmount) {
        this.refundGatewayAmount = refundGatewayAmount;
    }
    
    
    
}
