package com.plateno.booking.internal.coupon.constant;


/**
 * 优惠券的类型
 * @author mogt
 * @date 2016年12月30日
 */
public enum CouponEnum {
	
	/**
	 * 抵用券
	 */
	MONEY_COUPON(1, 13, "抵用券"),
	
	;
	
	private int type;
	private int subType;
	private String desc;
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSubType() {
		return subType;
	}
	public void setSubType(int subType) {
		this.subType = subType;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	private CouponEnum(int type, int subType, String desc) {
		this.type = type;
		this.subType = subType;
		this.desc = desc;
	}
}
