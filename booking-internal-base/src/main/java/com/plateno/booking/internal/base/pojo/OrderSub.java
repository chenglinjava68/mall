package com.plateno.booking.internal.base.pojo;

public class OrderSub {
    private Integer id;

    private String orderNo;

    private String orderSubNo;

    private Integer subFlag;

    private Integer subPrice;

    private Integer channelId;

    private Integer providedId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getOrderSubNo() {
        return orderSubNo;
    }

    public void setOrderSubNo(String orderSubNo) {
        this.orderSubNo = orderSubNo == null ? null : orderSubNo.trim();
    }

    public Integer getSubFlag() {
        return subFlag;
    }

    public void setSubFlag(Integer subFlag) {
        this.subFlag = subFlag;
    }

    public Integer getSubPrice() {
        return subPrice;
    }

    public void setSubPrice(Integer subPrice) {
        this.subPrice = subPrice;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getProvidedId() {
        return providedId;
    }

    public void setProvidedId(Integer providedId) {
        this.providedId = providedId;
    }
}