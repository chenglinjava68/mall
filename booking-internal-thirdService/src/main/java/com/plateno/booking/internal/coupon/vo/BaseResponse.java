package com.plateno.booking.internal.coupon.vo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 响应内容
 * @author mogt
 * @date 2016年12月27日
 */
public class BaseResponse {
	
	/**
	 * 返回状态， 0 表示成功，负数失败 (参考附录)
	 */
	private Integer status;
	
	/**
	 * 错误描述
	 */
	private String reason;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	@Override 
    public String toString() { 
		return ReflectionToStringBuilder.toString(this,ToStringStyle.SHORT_PREFIX_STYLE); 
    }

}
