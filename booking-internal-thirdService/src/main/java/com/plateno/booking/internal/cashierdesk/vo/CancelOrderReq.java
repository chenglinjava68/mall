package com.plateno.booking.internal.cashierdesk.vo;

public class CancelOrderReq extends CashierBaseParam{

    /***
     * 交易流水号
     */
    private String tradeNo;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    @Override
    public String toString() {
        return "CancelOrderReq [tradeNo=" + tradeNo + "]";
    }
    
    
    
    
}
