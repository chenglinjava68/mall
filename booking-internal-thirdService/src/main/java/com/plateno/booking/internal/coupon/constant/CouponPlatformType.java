package com.plateno.booking.internal.coupon.constant;

/**
 * 优惠券平台（对应APP和微信）
 * @author mogt
 * @date 2017年1月5日
 */
public enum CouponPlatformType {

	WECHAT(1008, 1, "微信"),
	APP(1007, 2, "APP"),
	;
	
	/**
	 *  平台ID
	 */
	private int platformId;
	
	/**
	 * 铂物馆来源
	 */
	private int resource;
	
	/**
	 * 描述
	 */
	private String desc;

	private CouponPlatformType(int platformId, int resource, String desc) {
		this.platformId = platformId;
		this.desc = desc;
		this.resource = resource;
	}
	
	public static CouponPlatformType fromResource(int resource)throws IllegalArgumentException {
        for (CouponPlatformType one : values()) {
            if (one.getResource() == resource) {
                return one;
            }
        }
        throw new IllegalArgumentException("CouponPlatformType illegal resource:"+ resource);
    }

	public int getPlatformId() {
		return platformId;
	}

	public void setPlatformId(int platformId) {
		this.platformId = platformId;
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
