package com.plateno.booking.internal.base.model;

/**
 * 
* @ClassName: RefundNotifyVo 
* @Description: 退款结果通知
* @author zhengchubin
* @date 2017年2月3日 上午11:03:17 
*
 */
public class RefundNotifyVo {
    private String code;
    private String message;
    private String merchantNo;
    private String merchantOrderNo;
    private String signData;
    private Integer gateId;
    private String platenopayRefundFlowNo;
    private Integer refundAmount;
    private Integer refundCurrencyDepositAmount;
    private Integer refundGatewayAmount;
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
    public String getMerchantNo() {
        return merchantNo;
    }
    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }
    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }
    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }
    public String getSignData() {
        return signData;
    }
    public void setSignData(String signData) {
        this.signData = signData;
    }
    public Integer getGateId() {
        return gateId;
    }
    public void setGateId(Integer gateId) {
        this.gateId = gateId;
    }
    public String getPlatenopayRefundFlowNo() {
        return platenopayRefundFlowNo;
    }
    public void setPlatenopayRefundFlowNo(String platenopayRefundFlowNo) {
        this.platenopayRefundFlowNo = platenopayRefundFlowNo;
    }
    public Integer getRefundAmount() {
        return refundAmount;
    }
    public void setRefundAmount(Integer refundAmount) {
        this.refundAmount = refundAmount;
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
