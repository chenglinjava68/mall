package com.plateno.booking.internal.cashierdesk.vo;

public class PayQueryReq extends CashierBaseParam{
    
    private String tradeNo;//交易流水号
    private Integer updatePayStatusFlag;//查询是否为了更新支付状态，1-是，0-否，不填为否。
    public String getTradeNo() {
        return tradeNo;
    }
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
    public Integer getUpdatePayStatusFlag() {
        return updatePayStatusFlag;
    }
    public void setUpdatePayStatusFlag(Integer updatePayStatusFlag) {
        this.updatePayStatusFlag = updatePayStatusFlag;
    }
    
    
    
}
