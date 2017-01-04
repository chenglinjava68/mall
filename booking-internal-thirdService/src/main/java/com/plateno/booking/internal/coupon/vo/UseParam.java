package com.plateno.booking.internal.coupon.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 使用优惠券
 * @author mogt
 * @date 2016年12月27日
 */
public class UseParam {
	/**
	 * 优惠券 Id 或优惠券 UUID
	 */
	private Integer couponId;
	/**
	 * 会员 id
	 */
	private Integer mebId;
	/**
	 * 分店(商品)Id
	 */
	private String chainId = "1";
	/**
	 * 订单
	 */
	private String orderCode;
	/**
	 * 品牌(景点)Id
	 */
	private String brandId = "1";
	/**
	 * 入住日期(验证使用有效期)
	 * 格式： yyyy-MM-dd HH:mm:ss
	 */
	private Date checkInDate = new Date();
	/**
	 * 离店日期
	 * 格式： yyyy-MM-dd HH:mm:ss
	 */
	private Date checkOutDate = new Date();
	/**
	 * 适用城市 Id
	 */
	private String cityId = "1";
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 特殊备注
	 */
	private String specRemark;
	/**
	 * 操作人 ID
	 */
	private Integer oprtId;
	/**
	 * 优惠券业务子类型(此定义在魔方管理)
	 */
	private Conditions conditions;
	
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

	public String getChainId() {
		return chainId;
	}

	public void setChainId(String chainId) {
		this.chainId = chainId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
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

	public Conditions getConditions() {
		return conditions;
	}

	public void setConditions(Conditions conditions) {
		this.conditions = conditions;
	}

	@Override 
    public String toString() { 
		return ReflectionToStringBuilder.toString(this,ToStringStyle.SHORT_PREFIX_STYLE); 
    }
}
