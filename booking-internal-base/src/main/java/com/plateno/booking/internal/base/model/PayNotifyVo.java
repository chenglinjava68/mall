package com.plateno.booking.internal.base.model;

/**
 * 
* @ClassName: PayNotifyVo 
* @Description: 支付结果通知对象
* @author zhengchubin
* @date 2017年2月3日 上午10:59:29 
*
 */
public class PayNotifyVo extends BaseNotifyVo{
    

    private String merchantNo;//收银台分配的商户号
    private String tradeNo;//支付流水号
    private Integer currencyDepositAmount;
    private Integer gatewayAmount;
    private Integer amount;
    private Integer payChannel;//支付网关渠道ID，如果只使用储值则为0
    private String ext1;//支付方式：1-支付网关+个人储值；2-只用支付网关支付；3-只用个人储值支付；4-企业储值
    private String ext2;//保留的扩展字段
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
