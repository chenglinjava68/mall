package com.plateno.booking.internal.base.constant;

/**
 * 请求端
 * @author mogt
 * @date 2017年1月4日
 */
public enum ResourceEnum {
    /**
     * 微信
     */
	WECHAT(1, "微信"),
    /**
     * APP
     */
    APP(2, "APP"),
    
    ;
    
    /**
     * 来源
     */
    private int resource;

    /**
     * 描述
     */
    private String desc;
    
    private ResourceEnum(int resource, String desc) {
		this.resource = resource;
		this.desc = desc;
	}

	public int getResource() {
		return resource;
	}

	public void setResource(int resource) {
		this.resource = resource;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static ResourceEnum from(int resource)throws IllegalArgumentException {
        for (ResourceEnum one : values()) {
            if (one.getResource() == resource) {
                return one;
            }
        }
        throw new IllegalArgumentException("ResourceEnum illegal resource:"+ resource);
    }
	
	/**
	 * 是否包含
	 * @param resource
	 * @return
	 */
	public static boolean has(int resource) {
        for (ResourceEnum one : values()) {
            if (one.getResource() == resource) {
                return true;
            }
        }
        return false;
    }
}
