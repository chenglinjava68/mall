package com.plateno.booking.internal.base.pojo;

import java.util.Date;

public class LogisticsPackage {
    private Integer id;

    private String orderNo;

    private String orderSubNo;

    private Integer logisticsType;

    private String logisticsNo;

    private Integer expressFee;

    private Integer packageFlag;
    
    /**
     * 包裹状态名称
     */
    private String packageFlagName;
    
    private Date createTime;

    private Date upTime;

    
    
    public String getPackageFlagName() {
        return packageFlagName;
    }

    public void setPackageFlagName(String packageFlagName) {
        this.packageFlagName = packageFlagName;
    }

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
        this.logisticsNo = logisticsNo == null ? null : logisticsNo.trim();
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
}