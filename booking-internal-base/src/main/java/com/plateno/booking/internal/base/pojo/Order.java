package com.plateno.booking.internal.base.pojo;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private Integer id;

    private String orderNo;

    private Integer amount;

    private String name;

    private String mobile;

    private Integer memberId;

    private Integer sid;

    private Integer chanelid;

    private Integer itemId;

    private Integer payType;

    private Date payTime;

    private Date waitPayTime;

    private Date createTime;

    private Date upTime;

    private Integer point;

    private Integer refundAmount;

    private Date refundTime;

    private Integer payStatus;

    private Integer payMoney;

    private Date deliverTime;

    private Date refundSuccesstime;

    private String refundReason;

    private Integer resource;

    private Integer refundPoint;

    private String remark;
    
    /**
     * 退款失败原因
     */
    private String refundFailReason;
    
    /**
     * 逻辑删除 ， 1-正常，2-删除
     */
    private Integer logicDel;

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getChanelid() {
        return chanelid;
    }

    public void setChanelid(Integer chanelid) {
        this.chanelid = chanelid;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getWaitPayTime() {
        return waitPayTime;
    }

    public void setWaitPayTime(Date waitPayTime) {
        this.waitPayTime = waitPayTime;
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

    public Integer getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Integer refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Integer payMoney) {
        this.payMoney = payMoney;
    }

    public Date getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    public Date getRefundSuccesstime() {
        return refundSuccesstime;
    }

    public void setRefundSuccesstime(Date refundSuccesstime) {
        this.refundSuccesstime = refundSuccesstime;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason == null ? null : refundReason.trim();
    }

    public Integer getResource() {
        return resource;
    }

    public void setResource(Integer resource) {
        this.resource = resource;
    }

    public Integer getRefundPoint() {
        return refundPoint;
    }

    public void setRefundPoint(Integer refundPoint) {
        this.refundPoint = refundPoint;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getRefundFailReason() {
		return refundFailReason;
	}

	public void setRefundFailReason(String refundFailReason) {
		this.refundFailReason = refundFailReason;
	}

	public Integer getLogicDel() {
		return logicDel;
	}

	public void setLogicDel(Integer logicDel) {
		this.logicDel = logicDel;
	}
}