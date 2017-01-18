package com.plateno.booking.internal.coupon.vo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 查询参数
 * @author mogt
 * @date 2016年12月27日
 */
public class CancelParam {
	/**
	 * 优惠券 Id 或优惠券 UUID
	 */
	private Integer couponId;
	/**
	 * mebId
	 */
	private Integer mebId;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 特殊备注
	 */
	private String specRemark;
	/**
	 * 操作人 Id
	 */
	private Integer oprtId;
	
	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	public Integer getMebId() {
		return mebId;
	}

	public void setMebId(Integer mebId) {
		this.mebId = mebId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSpecRemark() {
		return specRemark;
	}

	public void setSpecRemark(String specRemark) {
		this.specRemark = specRemark;
	}

	public Integer getOprtId() {
		return oprtId;
	}

	public void setOprtId(Integer oprtId) {
		this.oprtId = oprtId;
	}

	@Override 
    public String toString() { 
		return ReflectionToStringBuilder.toString(this,ToStringStyle.SHORT_PREFIX_STYLE); 
    }
}
