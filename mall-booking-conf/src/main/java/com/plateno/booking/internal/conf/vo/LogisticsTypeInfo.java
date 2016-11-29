package com.plateno.booking.internal.conf.vo;


/**
 * 物流类型
 * @author mogt
 * @date 2016年11月29日
 */
public class LogisticsTypeInfo {
	
	/**
	 * 类型
	 */
    private Integer type;

    /**
     *  描述
     */
    private String description;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}