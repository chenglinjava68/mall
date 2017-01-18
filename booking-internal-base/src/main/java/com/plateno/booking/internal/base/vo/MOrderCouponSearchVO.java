package com.plateno.booking.internal.base.vo;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.plateno.booking.internal.util.vo.BaseSearchVO;

public class MOrderCouponSearchVO extends BaseSearchVO {

	/** 订单编号 */
	private String orderNo;
	
	/** 优惠券ID */
	private Integer couponId;
	
	/** 优惠券类型 */
	private Integer couponType;
	
	/** 优惠券子类型 */
	private Integer subCouponType;
	
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
	
	@Override 
    public String toString() { 
            return ReflectionToStringBuilder.toString(this,ToStringStyle.SHORT_PREFIX_STYLE); 
    }
}
