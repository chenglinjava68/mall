package com.plateno.booking.internal.dao.pojo;

import java.util.List;

import com.plateno.booking.internal.bean.response.custom.OrderDetail.ProductInfo;

public class ProviderOrderResponse {

    /**
     * 子订单号
     */
    private String orderSubNo;
    
    /**
     * 子订单实付金额
     */
    private Integer subPayMoney;
    
    /**
     * 子订单状态
     */
    private Integer subPayStatus;
    
    /**
     * 支付方式
     */
    private Integer payType;
    
    /**
     * 下单人
     */
    private String name;
    
    /**
     * 下单人手机号码
     */
    private String mobile;
    
    
    /**
     * 下单来源
     */
    private Integer resource;
    
    /**
     * 前端状态
     */
    private Integer viewStatus;

    private List<ProductInfo> productInfos;
    
    
    
    
    public List<ProductInfo> getProductInfos() {
        return productInfos;
    }

    public void setProductInfos(List<ProductInfo> productInfos) {
        this.productInfos = productInfos;
    }

    public String getOrderSubNo() {
        return orderSubNo;
    }

    public void setOrderSubNo(String orderSubNo) {
        this.orderSubNo = orderSubNo;
    }

    public Integer getSubPayMoney() {
        return subPayMoney;
    }

    public void setSubPayMoney(Integer subPayMoney) {
        this.subPayMoney = subPayMoney;
    }

    public Integer getSubPayStatus() {
        return subPayStatus;
    }

    public void setSubPayStatus(Integer subPayStatus) {
        this.subPayStatus = subPayStatus;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getResource() {
        return resource;
    }

    public void setResource(Integer resource) {
        this.resource = resource;
    }

    public Integer getViewStatus() {
        return viewStatus;
    }

    public void setViewStatus(Integer viewStatus) {
        this.viewStatus = viewStatus;
    }
    
    
    
    
}
