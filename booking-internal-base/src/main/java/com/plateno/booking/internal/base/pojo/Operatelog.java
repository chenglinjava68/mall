package com.plateno.booking.internal.base.pojo;

import java.io.Serializable;
import java.util.Date;

public class Operatelog implements Serializable {
    private Integer id;

    private Integer operateType;

    private String operateUserid;

    private String operateUsername;

    private String remark;

    private Integer plateForm;

    private String orderCode;

    private Date operateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public String getOperateUserid() {
        return operateUserid;
    }

    public void setOperateUserid(String operateUserid) {
        this.operateUserid = operateUserid == null ? null : operateUserid.trim();
    }

    public String getOperateUsername() {
        return operateUsername;
    }

    public void setOperateUsername(String operateUsername) {
        this.operateUsername = operateUsername == null ? null : operateUsername.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getPlateForm() {
        return plateForm;
    }

    public void setPlateForm(Integer plateForm) {
        this.plateForm = plateForm;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
}