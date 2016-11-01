package com.plateno.booking.internal.base.model;


public class SelectOrderParam implements java.io.Serializable{

	private static final long serialVersionUID = 9176534725953681588L;
	
	private Integer payStatus;//订单状态
	
	private Integer requstPlatenoform;// 1 商城,2营销通,3供应商后台
	
	private String memberId;//会员ID
	
	private String orderNo; // 订单编码

	private String mobile; //下单人手机号码
	
	private Long bookingStartDate; //下单开始日期
	
	private Long bookingEndDate; //下单结束日期
	
	private Integer  resource;//下单来源
	
	private Integer pageNo;

	private Integer pageNumber;
	
	/**
	 * 供应商的渠道
	 */
	private Integer channelId;
	
	private boolean showLimit = true;

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

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public boolean isShowLimit() {
		return showLimit;
	}

	public void setShowLimit(boolean showLimit) {
		this.showLimit = showLimit;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public Integer getResource() {
		return resource;
	}

	public void setResource(Integer resource) {
		this.resource = resource;
	}

	public Integer getRequstPlatenoform() {
		return requstPlatenoform;
	}

	public void setRequstPlatenoform(Integer requstPlatenoform) {
		this.requstPlatenoform = requstPlatenoform;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	
}
