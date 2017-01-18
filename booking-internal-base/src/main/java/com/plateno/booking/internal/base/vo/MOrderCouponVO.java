package com.plateno.booking.internal.base.vo;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.plateno.booking.internal.base.pojo.MOrderCouponPO;

public class MOrderCouponVO  extends MOrderCouponPO {

	private static final long serialVersionUID = 1L;
	
	public MOrderCouponVO(MOrderCouponPO po){
		this.id=po.getId();
		this.orderNo=po.getOrderNo();
		this.couponId=po.getCouponId();
		this.couponType=po.getCouponType();
		this.subCouponType=po.getSubCouponType();
		this.couponName=po.getCouponName();
		this.amount=po.getAmount();
		this.orderCouponAmount=po.getOrderCouponAmount();
		this.createTime=po.getCreateTime();
	}
	
	@Override 
    public String toString() { 
            return ReflectionToStringBuilder.toString(this,ToStringStyle.SHORT_PREFIX_STYLE); 
    }
}
