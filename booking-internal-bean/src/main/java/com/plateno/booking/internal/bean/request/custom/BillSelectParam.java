package com.plateno.booking.internal.bean.request.custom;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class BillSelectParam {

	private String mobile;

	private String state;
	
	private Integer ticketStatus;

	private String tradeNo;

	private Long bookingStartDate;

	private Long bookingEndDate;
	
	private Long filterDate;
	
	private Integer memberId;
	
	private String storeId;
	
	private Integer pageNo = 1;

	private Integer pageNumber = 10;
	
	private boolean showLimit = true;
	
	//下单平台
	private Integer platformId;
	
	@SuppressWarnings("unused")
	private List<Integer> ids;
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public Long getFilterDate() {
		return filterDate;
	}

	public void setFilterDate(Long filterDate) {
		this.filterDate = filterDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state.replace("[", "").replace("]", "");
	}

	public Integer getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(Integer ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
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

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = (pageNo <= 0) ? 1 : pageNo;
	}
	
	public Integer getCurPage() {
		return (pageNo - 1) * pageNumber;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public boolean isShowLimit() {
		return showLimit;
	}

	public void setShowLimit(boolean showLimit) {
		this.showLimit = showLimit;
	}
	
	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public List<Integer> getIds() {
		List <Integer> outputCollection = new ArrayList<Integer>() ;
		if (StringUtils.isBlank(state)) {
			return null;
		}
		String keys[] = state.split(",");
		for(String key : keys){
			outputCollection.add(Integer.valueOf(StringUtils.trim(key)));
		}
		return outputCollection;
	}

	public void setIds(List<Integer> ids) {
			this.ids = ids;
	}

	/*public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public List<Integer> getIds() {
		List <Integer> outputCollection = new ArrayList<Integer>() ;
		if (state.equals("")) {
			return outputCollection;
		}
		String keys[] = state.split(",");
		for(String key : keys){
			outputCollection.add(Integer.valueOf(StringUtils.trim(key)));
		}
		return outputCollection;
	}*/
	
	
}