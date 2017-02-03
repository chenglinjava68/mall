package com.plateno.booking.internal.base.model;

/**
 * 
* @ClassName: PayNotifyVo 
* @Description: 支付结果通知对象
* @author zhengchubin
* @date 2017年2月3日 上午10:59:29 
*
 */
public class PayNotifyVo {
    
    private String code;
    private String message;
    private String merchantNo;
    private String merchantOrderNo;
    private String signData;
    private Integer gateId;
    private String platenopayFlowNo;
    private Integer currencyDepositAmount;
    private Integer gatewayAmount;
    private Integer amount;
    private Integer payChannel;
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
    public String getPlatenopayFlowNo() {
        return platenopayFlowNo;
    }
    public void setPlatenopayFlowNo(String platenopayFlowNo) {
        this.platenopayFlowNo = platenopayFlowNo;
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
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public Integer getPayChannel() {
        return payChannel;
    }
    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }
    
    
    
}
