package com.plateno.booking.internal.bean.response.logistics;

import java.util.Date;
import java.util.List;

import com.plateno.booking.internal.bean.response.custom.OrderDetail.ProductInfo;

public class PackageProduct {

    private Integer logisticsType;//快递类型
    private String logisticsName;//快递名称
    private String logisticsNo;
    private Integer  expressFee;
    private Integer packageFlag;//包裹状态
    private String packageFlagName;//包裹状态名称
    private List<ProductInfo> products;
    private Date createTime;
    
    
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getPackageFlagName() {
        return packageFlagName;
    }
    public void setPackageFlagName(String packageFlagName) {
        this.packageFlagName = packageFlagName;
    }
    public String getLogisticsName() {
        return logisticsName;
    }
    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }
    public List<ProductInfo> getProducts() {
        return products;
    }
    public void setProducts(List<ProductInfo> products) {
        this.products = products;
    }
    public Integer getLogisticsType() {
        return logisticsType;
    }
    public void setLogisticsType(Integer logisticsType) {
        this.logisticsType = logisticsType;
    }
    public String getLogisticsNo() {
        return logisticsNo;
    }
    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }
    public Integer getExpressFee() {
        return expressFee;
    }
    public void setExpressFee(Integer expressFee) {
        this.expressFee = expressFee;
    }
    public Integer getPackageFlag() {
        return packageFlag;
    }
    public void setPackageFlag(Integer packageFlag) {
        this.packageFlag = packageFlag;
    }
    
    
    
}
