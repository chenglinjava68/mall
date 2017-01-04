package com.plateno.booking.internal.coupon.constant;


public enum CouponSourceType {

	WECHAT("1008", 1, "微信"),
	APP("1007", 2, "APP"),
	;
	
	/**
	 * 优惠券类型
	 */
	private String type;
	
	/**
	 * 铂物馆来源
	 */
	private int resource;
	
	/**
	 * 描述
	 */
	private String desc;

	private CouponSourceType(String type, int resource, String desc) {
		this.type = type;
		this.desc = desc;
		this.resource = resource;
	}
	
	public static CouponSourceType fromResource(int resource)throws IllegalArgumentException {
        for (CouponSourceType one : values()) {
            if (one.getResource() == resource) {
                return one;
            }
        }
        throw new IllegalArgumentException("CouponSourceType illegal resource:"+ resource);
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getResource() {
		return resource;
	}

	public void setResource(int resource) {
		this.resource = resource;
	} 
	
}
