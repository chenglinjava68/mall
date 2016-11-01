package com.plateno.booking.internal.bean.request.convert;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.plateno.booking.internal.bean.request.custom.AddBookingParam.BookInfo;
import com.plateno.booking.internal.bean.request.lvmama.order.TravellersInfo;

public class MConvertBookingParam implements Serializable {

	
	private Long goodsId;
	
	private Integer totalAmount;
	
	private Integer quantity;

	private String consigneeName;
	
	private String consigneeMobile;
	
	private String consigneeAddress;
	
	private String shippingType;
	
	private Integer platformId; // 平台 铂涛旅行APP:105 铂涛旅行官网 : 113 铂涛旅行微信 : 111 铂涛旅行M站 : 112 O2O营销通 115

	private String name;
	
	private String mobile;
	
	private Integer memberId;
	
	private Integer chanelId;
	
	private String orderNo;
	
	private String sellStrategy;//销售策略 1:金额(积分不足会员） 2：积分+金额 （够积分的会员使用)

	private Integer point;//积分
	
	private String skuProperties;//商品属性,按:分开;

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

	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
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

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getChanelId() {
		return chanelId;
	}

	public void setChanelId(Integer chanelId) {
		this.chanelId = chanelId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getSellStrategy() {
		return sellStrategy;
	}

	public void setSellStrategy(String sellStrategy) {
		this.sellStrategy = sellStrategy;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public String getSkuProperties() {
		return skuProperties;
	}

	public void setSkuProperties(String skuProperties) {
		this.skuProperties = skuProperties;
	}


	
	
	
}