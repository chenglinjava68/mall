package com.plateno.booking.internal.dao.pojo;

import java.util.Date;
import java.util.List;

import com.plateno.booking.internal.bean.response.custom.OrderDetail.ProductInfo;

public class ProviderOrder {

    
    /**
     * 父订单号
     */
    private String orderNo;
    
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
    

    
    
    /**
     * 订单的积分抵扣金额
     */
    private Integer deductPrice;
    
    /**
     * 使用优惠券的抵扣金额
     */
    private Integer couponAmount;

    /**
     * 运费
     */
    private Integer fee;

    /**
     * 商品总额
     */
    private Integer productAmout;
    
    /**
     * 收货人姓名
     */
    private String consigneeName;
    
    /**
     * 收货人手机
     */
    private String consigneeMobile;
    
    /**
     * 收货人地址
     */
    private String consigneeAddress;
    
    /**
     * 订单创建时间
     */
    private Date createTime;
    
    
    
    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getConsigneeMobile() {
        return consigneeMobile;
    }

    public void setConsigneeMobile(String consigneeMobile) {
        this.consigneeMobile = consigneeMobile;
    }

    public Integer getDeductPrice() {
        return deductPrice;
    }

    public void setDeductPrice(Integer deductPrice) {
        this.deductPrice = deductPrice;
    }

    public Integer getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Integer couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Integer getProductAmout() {
        return productAmout;
    }

    public void setProductAmout(Integer productAmout) {
        this.productAmout = productAmout;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

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
