package com.plateno.booking.internal.bean.response.tc.order;

import java.util.List;

import com.plateno.booking.internal.bean.response.tc.Response;

public class OrderListVerificationResponse extends Response {
	
	private Integer maxIncrementId;
	private List<Order> orderList;

	public Integer getMaxIncrementId() {
		return maxIncrementId;
	}

	public void setMaxIncrementId(Integer maxIncrementId) {
		this.maxIncrementId = maxIncrementId;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}
	
	public static class Order{
		private String orderSerialId;
		private String travelDate;
		private String createTime;
		private String orderState;
		private Integer ticketsNumber;
		private String checkOrderTime;
		
		public String getOrderSerialId() {
			return orderSerialId;
		}
		public void setOrderSerialId(String orderSerialId) {
			this.orderSerialId = orderSerialId;
		}
		public String getTravelDate() {
			return travelDate;
		}
		public void setTravelDate(String travelDate) {
			this.travelDate = travelDate;
		}
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
		public String getOrderState() {
			return orderState;
		}
		public void setOrderState(String orderState) {
			this.orderState = orderState;
		}
		public Integer getTicketsNumber() {
			return ticketsNumber;
		}
		public void setTicketsNumber(Integer ticketsNumber) {
			this.ticketsNumber = ticketsNumber;
		}
		public String getCheckOrderTime() {
			return checkOrderTime;
		}
		public void setCheckOrderTime(String checkOrderTime) {
			this.checkOrderTime = checkOrderTime;
		}
	}
}


