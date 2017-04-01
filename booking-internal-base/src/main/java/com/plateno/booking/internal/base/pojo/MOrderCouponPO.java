package com.plateno.booking.internal.base.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MOrderCouponPO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**  */
	protected Long id;
	
	/** 订单编号 */
	protected String orderNo;
	
	/** 优惠券ID */
	protected Integer couponId;
	
	/** 优惠券类型 */
	protected Integer couponType;
	
	/** 优惠券子类型 */
	protected Integer subCouponType;
	
	/** 优惠券名称 */
	protected String couponName;
	
	/** 优惠券金额或者折扣率 */
	protected BigDecimal amount;
	
	/** 订单使用优惠券的抵扣金额 */
	protected BigDecimal orderCouponAmount;
	
	/** 创建时间 */
	protected Date createTime;
	
	/**
	 * 优惠券配置id
	 */
	private Integer configId;
	
	
	
	public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getOrderNo() {
		return orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public Integer getCouponId() {
		return couponId;
	}
	
	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}
	
	public Integer getCouponType() {
		return couponType;
	}
	
	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}
	
	public Integer getSubCouponType() {
		return subCouponType;
	}
	
	public void setSubCouponType(Integer subCouponType) {
		this.subCouponType = subCouponType;
	}
	
	public String getCouponName() {
		return couponName;
	}
	
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public BigDecimal getOrderCouponAmount() {
		return orderCouponAmount;
	}
	
	public void setOrderCouponAmount(BigDecimal orderCouponAmount) {
		this.orderCouponAmount = orderCouponAmount;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Override 
    public String toString() { 
            return ReflectionToStringBuilder.toString(this,ToStringStyle.SHORT_PREFIX_STYLE); 
    }
}
