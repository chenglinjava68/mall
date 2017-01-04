package com.plateno.booking.internal.coupon.vo;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 优惠券限制条件
 * @author mogt
 * @date 2016年12月27日
 */
public class Conditions {
	/**
	 * 预定-满减券限制
	 */
	private BigDecimal orderAmount;
	
	/**
	 * 铂物管-商品 Id
	 */
	private Integer produceId;
	
	/**
	 * 铂物管-类目 Id
	 */
	private Integer categoryId;

	/**
	 * 使用渠道
	 */
	private String sourceType;
	
	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Integer getProduceId() {
		return produceId;
	}

	public void setProduceId(Integer produceId) {
		this.produceId = produceId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Override 
    public String toString() { 
		return ReflectionToStringBuilder.toString(this,ToStringStyle.SHORT_PREFIX_STYLE); 
    }
}
