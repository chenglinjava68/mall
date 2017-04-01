package com.plateno.booking.internal.bean.response.custom;

import java.util.List;

import com.plateno.booking.internal.bean.response.custom.OrderDetail.ProductInfo;

public class SubOrderDetail {

    private String subOrderNo;//子订单号
    private Integer channelId;//仓库id
    private Integer subViewStatus;//子订单显示状态
    private Integer subOrderStatus;//子订单状态
    
    private Integer packageStatus;//包裹状态
    
    private List<ProductInfo> productInfo;
    
    
    
    public Integer getPackageStatus() {
        return packageStatus;
    }
    public void setPackageStatus(Integer packageStatus) {
        this.packageStatus = packageStatus;
    }
    public List<ProductInfo> getProductInfo() {
        return productInfo;
    }
    public void setProductInfo(List<ProductInfo> productInfo) {
        this.productInfo = productInfo;
    }
    public String getSubOrderNo() {
        return subOrderNo;
    }
    public void setSubOrderNo(String subOrderNo) {
        this.subOrderNo = subOrderNo;
    }
    public Integer getChannelId() {
        return channelId;
    }
    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }
    public Integer getSubViewStatus() {
        return subViewStatus;
    }
    public void setSubViewStatus(Integer subViewStatus) {
        this.subViewStatus = subViewStatus;
    }
    public Integer getSubOrderStatus() {
        return subOrderStatus;
    }
    public void setSubOrderStatus(Integer subOrderStatus) {
        this.subOrderStatus = subOrderStatus;
    }
    
    
    
}
