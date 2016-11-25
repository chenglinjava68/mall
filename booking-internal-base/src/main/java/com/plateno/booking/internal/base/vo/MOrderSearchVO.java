package com.plateno.booking.internal.base.vo;



public class MOrderSearchVO extends MallBaseSearchVO {
	
	/**
	 * 是否查询删除的订单
	 */
	private Boolean queryDel = true;
	
	/**
	 * 查询开始日期
	 */
	private Long bookingStartDate = 0L;
	
	/**
	 * 下单结束日期
	 */
	private Long bookingEndDate = 0L;

	public Boolean getQueryDel() {
		return queryDel;
	}

	public void setQueryDel(Boolean queryDel) {
		this.queryDel = queryDel;
	}

	public Long getBookingStartDate() {
		return bookingStartDate;
	}

	public void setBookingStartDate(Long bookingStartDate) {
		this.bookingStartDate = bookingStartDate;
	}

	public Long getBookingEndDate() {
		return bookingEndDate;
	}

	public void setBookingEndDate(Long bookingEndDate) {
		this.bookingEndDate = bookingEndDate;
	}

}
