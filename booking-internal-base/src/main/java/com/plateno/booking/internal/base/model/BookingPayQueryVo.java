package com.plateno.booking.internal.base.model;

/**
 * 
* @ClassName: BookingPayQueryVo 
* @Description: 回调跟主动查询支付的共用类
* @author zhengchubin
* @date 2017年2月8日 下午3:10:18 
*
 */
public class BookingPayQueryVo {

    private String tradeNo;//支付流水号
    private Integer currencyDepositAmount;
    private Integer gatewayAmount;
    private Integer amount;
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

    
    
}
