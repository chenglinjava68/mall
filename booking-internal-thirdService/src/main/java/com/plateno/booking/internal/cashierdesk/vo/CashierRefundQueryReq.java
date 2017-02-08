package com.plateno.booking.internal.cashierdesk.vo;

public class CashierRefundQueryReq extends CashierBaseParam{
    private String refundTradeNo;
    private Integer updatePayStatusFlag;
    public String getRefundTradeNo() {
        return refundTradeNo;
    }
    public void setRefundTradeNo(String refundTradeNo) {
        this.refundTradeNo = refundTradeNo;
    }
    public Integer getUpdatePayStatusFlag() {
        return updatePayStatusFlag;
    }
    public void setUpdatePayStatusFlag(Integer updatePayStatusFlag) {
        this.updatePayStatusFlag = updatePayStatusFlag;
    }
    
    
    
}
