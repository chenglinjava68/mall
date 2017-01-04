package com.plateno.booking.internal.coupon.vo;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 查询购物券返回信息
 * @author mogt
 * @date 2016年12月27日
 */
public class QueryResponse extends BaseResponse {
	
	/**
	 * 优惠券信息
	 */
	private List<CouponInfo> couponInfo;

	public List<CouponInfo> getCouponInfo() {
		return couponInfo;
	}

	public void setCouponInfo(List<CouponInfo> couponInfo) {
		this.couponInfo = couponInfo;
	}
	@Override 
    public String toString() { 
		return ReflectionToStringBuilder.toString(this,ToStringStyle.SHORT_PREFIX_STYLE); 
    }
}
