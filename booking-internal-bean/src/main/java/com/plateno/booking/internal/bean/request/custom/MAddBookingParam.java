package com.plateno.booking.internal.bean.request.custom;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.plateno.booking.internal.base.model.BaseParam;

public class MAddBookingParam extends BaseParam implements Serializable {

	private static final long serialVersionUID = -5028619260613976314L;
	
	@NotNull(message = "商品ID,不能为空")
	private Long goodsId;
	
	@NotNull(message = "订单总价,不能为空")
	private Integer totalAmount;
	
	@NotNull(message = "选购数量,不能为空")
	private Integer quantity;

	@NotNull(message = "收货人,不能为空")
	@NotEmpty(message = "收货人,不能为空")
	private String consigneeName;
	
	@NotNull(message = "收货人手机,不能为空")
	@NotEmpty(message = "收货人手机,不能为空")
	private String consigneeMobile;
	
	@NotNull(message = "收货地址,不能为空")
	@NotEmpty(message = "收货地址,不能为空")
	private String consigneeAddress;
	
	@NotNull(message = "配送方式,不能为空")
	private Integer shippingType;
	
	@NotNull(message = "平台来源,不能为空")
	private Integer platformId; // 平台 铂涛旅行APP:105 铂涛旅行官网 : 113 铂涛旅行微信 : 111 铂涛旅行M站 : 112 O2O营销通 115

	@NotNull(message = "会员姓名,不能为空")
	@NotEmpty(message = "会员姓名,不能为空")
	private String name;
	
	@NotNull(message = "会员手机,不能为空")
	@NotEmpty(message = "会员手机,不能为空")
	private String mobile;
	
	@NotNull(message = "下单来源,不能为空")
	private Integer resource;
	
	private Integer chanelId;
	
	@NotNull(message = "销售策略,不能为空")
	private Integer sellStrategy;//销售策略 1:金额(积分不足会员） 2：积分+金额 （够积分的会员使用)
	
	private Integer point;//积分
	
	private String skuProperties;//商品属性,按:分开;
	
	/**
	 * 订单子来源
	 */
	private Integer subResource; 
	
	/**
	 * 优惠券ID
	 */
	private Integer couponId;
	
	/**
	 * 优惠券ID（非前端传）
	 */
	private String couponName;
	
	/**
	 * 优惠券的金额（非前端传）
	 */
	private BigDecimal couponAmount;
	
	/**
	 * 有效的优惠券金额（当商品需要支付的金额小于优惠券金额时，就是商品需要支付的金额）
	 */
	private BigDecimal validCouponAmount;

	public BigDecimal getValidCouponAmount() {
		return validCouponAmount;
	}

	public void setValidCouponAmount(BigDecimal validCouponAmount) {
		this.validCouponAmount = validCouponAmount;
	}

	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	public Integer getSellStrategy() {
		return sellStrategy;
	}

	public void setSellStrategy(Integer sellStrategy) {
		this.sellStrategy = sellStrategy;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}


	public Integer getResource() {
		return resource;
	}

	public void setResource(Integer resource) {
		this.resource = resource;
	}

	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public Integer getShippingType() {
		return shippingType;
	}

	public void setShippingType(Integer shippingType) {
		this.shippingType = shippingType;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
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

	public String getSkuProperties() {
		return skuProperties;
	}

	public void setSkuProperties(String skuProperties) {
		this.skuProperties = skuProperties;
	}

	public Integer getChanelId() {
		return chanelId;
	}

	public void setChanelId(Integer chanelId) {
		this.chanelId = chanelId;
	}

	public Integer getSubResource() {
		return subResource;
	}

	public void setSubResource(Integer subResource) {
		this.subResource = subResource;
	}
}
