package com.plateno.booking.internal.base.model;

import java.util.ArrayList;
import java.util.List;


public class SelectOrderParam extends BaseParam implements java.io.Serializable{

	private static final long serialVersionUID = 9176534725953681588L;
	
	private Integer payStatus;//订单状态
	
	private String orderNo; // 订单编码

	private String mobile; //下单人手机号码
	
	private Long bookingStartDate; //下单开始日期
	
	private Long bookingEndDate; //下单结束日期
	
	private Integer  resource;//下单来源
	
	private Integer pageNo;

	private Integer pageNumber;
	
	/**
	 * 多个状态
	 */
	private List<Integer> statusList = new ArrayList<>();
	
	/**
	 * 是否查询删除的订单
	 */
	private Boolean queryDel = true;
	
	/**
	 * 下单人姓名
	 */
	private String name;
	
	private boolean showLimit = true;
	
	private Integer requstPlatenoform;
	
	/**
	 * 显示状态
	 * 100待付款、200已取消、300待发货、400待收货、500已完成、600退款审核中、700已退款、800退款审核不通过
	 */
	private Integer viewStatus;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRequstPlatenoform() {
		return requstPlatenoform;
	}

	public void setRequstPlatenoform(Integer requstPlatenoform) {
		this.requstPlatenoform = requstPlatenoform;
	}

	public Integer getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(Integer viewStatus) {
		this.viewStatus = viewStatus;
	}

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

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
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

	public List<Integer> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Integer> statusList) {
		this.statusList = statusList;
	}
	
}
