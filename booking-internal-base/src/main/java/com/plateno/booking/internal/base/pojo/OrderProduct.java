package com.plateno.booking.internal.base.pojo;

import java.io.Serializable;
import java.util.Date;

public class OrderProduct implements Serializable {
    private Integer id;

    private String orderNo;

    private Integer skuid;

    private Integer skuCount;

    private Integer price;

    private Integer productId;

    private String productName;

    private String productProperty;

    private Date createTime;

    private Date upTime;

    private Integer point;

    private Integer sellStrategy;

    private String disImages;

    private static final long serialVersionUID = 1L;

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

    public Integer getSkuid() {
        return skuid;
    }

    public void setSkuid(Integer skuid) {
        this.skuid = skuid;
    }

    public Integer getSkuCount() {
        return skuCount;
    }

    public void setSkuCount(Integer skuCount) {
        this.skuCount = skuCount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductProperty() {
        return productProperty;
    }

    public void setProductProperty(String productProperty) {
        this.productProperty = productProperty == null ? null : productProperty.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getSellStrategy() {
        return sellStrategy;
    }

    public void setSellStrategy(Integer sellStrategy) {
        this.sellStrategy = sellStrategy;
    }

    public String getDisImages() {
        return disImages;
    }

    public void setDisImages(String disImages) {
        this.disImages = disImages == null ? null : disImages.trim();
    }
}