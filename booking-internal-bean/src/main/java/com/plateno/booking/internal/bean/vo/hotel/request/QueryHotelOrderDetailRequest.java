package com.plateno.booking.internal.bean.vo.hotel.request;

import java.io.Serializable;

/**
 * 酒店订单详情查询bean
 * 
 * @author user
 *
 */
public class QueryHotelOrderDetailRequest implements Serializable {

	private static final long serialVersionUID = 1943140519227112819L;

	private String orderCode; // 订单编码
	private Integer memberId; // 会员ID
	private boolean hasAccounts; // 是否需要账单
	
	private boolean hasGuests; // 是否需要客单,默认否
	private Integer flag; // 是否分页查询标识   默认1,0:删除,1:有效,2:全部

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public boolean isHasAccounts() {
		return hasAccounts;
	}

	public void setHasAccounts(boolean hasAccounts) {
		this.hasAccounts = hasAccounts;
	}

	public boolean isHasGuests() {
		return hasGuests;
	}

	public void setHasGuests(boolean hasGuests) {
		this.hasGuests = hasGuests;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}
