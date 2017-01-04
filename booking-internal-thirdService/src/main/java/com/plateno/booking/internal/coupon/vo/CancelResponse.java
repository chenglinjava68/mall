package com.plateno.booking.internal.coupon.vo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 取消优惠券返回
 * @author mogt
 * @date 2016年12月27日
 */
public class CancelResponse extends BaseResponse {
	
	/**
	 * 优惠券 Id,
	 * 大于 0 标示取消返还的优惠券 Id
	 */
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override 
    public String toString() { 
		return ReflectionToStringBuilder.toString(this,ToStringStyle.SHORT_PREFIX_STYLE); 
    }
}
