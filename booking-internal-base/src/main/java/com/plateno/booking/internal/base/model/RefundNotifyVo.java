package com.plateno.booking.internal.base.model;

/**
 * 
* @ClassName: RefundNotifyVo 
* @Description: 退款结果通知
* @author zhengchubin
* @date 2017年2月3日 上午11:03:17 
*
 */
public class RefundNotifyVo extends BaseNotifyVo{
    private String merchantNo;//收银台分配的商户号
    private String refundTradeNo;//退款流水号
    private String signData;
    private Integer currencyDepositAmount;//储值金额
    private Integer gatewayAmount;//网关金额
    private Integer amount;//收银台金额
    private String ext1;//支付方式：1-支付网关+个人储值；2-只用支付网关支付；3-只用个人储值支付；4-企业储值
    private String ext2;
    public String getMerchantNo() {
        return merchantNo;
    }
    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }
    public String getRefundTradeNo() {
        return refundTradeNo;
    }
    public void setRefundTradeNo(String refundTradeNo) {
        this.refundTradeNo = refundTradeNo;
    }
    public String getSignData() {
        return signData;
    }
    public void setSignData(String signData) {
        this.signData = signData;
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
    public String getExt1() {
        return ext1;
    }
    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }
    public String getExt2() {
        return ext2;
    }
    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
    

    
    
}
