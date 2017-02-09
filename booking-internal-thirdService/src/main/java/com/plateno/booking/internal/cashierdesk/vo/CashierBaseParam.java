package com.plateno.booking.internal.cashierdesk.vo;

public class CashierBaseParam {
    private String signData;
    private String appId;//商城为mall
    public String getSignData() {
        return signData;
    }

    public void setSignData(String signData) {
        this.signData = signData;
    }

    @Override
    public String toString() {
        return "CashierBaseParam [signData=" + signData + "]";
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
    
}
