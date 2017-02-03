package com.plateno.booking.internal.cashierdesk.vo;

public class CashierBaseParam {
    private String signData;

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
}
