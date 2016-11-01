package com.plateno.booking.internal.bean.vo.hotel.request.inner;

public class CouponParam {
	private Integer couponId;// 券id
	private Integer couponConfigid;// 券定义Id

	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	public Integer getCouponConfigid() {
		return couponConfigid;
	}

	public void setCouponConfigid(Integer couponConfigid) {
		this.couponConfigid = couponConfigid;
	}

}