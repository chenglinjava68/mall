package com.plateno.booking.internal.bean.response.lvmama.order;

import com.plateno.booking.internal.bean.response.lvmama.base.ResponseState.State;

public class OrderCannelResponse {
	
	private State state;
	private Order order;
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public static class Order {
		private String orderId; // 由驴妈妈生成的订单 ID
		private String requestStatus; // 申请状态 PASS:已退款 REVIEWING:审核中 REJECT:申请驳回
		private Integer refundAmount; // 退款金额,单位：分
		private Integer factorage; // 手续费,单位：分
		private String refundInfo; // 备注

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public String getRequestStatus() {
			return requestStatus;
		}

		public void setRequestStatus(String requestStatus) {
			this.requestStatus = requestStatus;
		}

		public Integer getRefundAmount() {
			return refundAmount;
		}

		public void setRefundAmount(Integer refundAmount) {
			this.refundAmount = refundAmount;
		}

		public Integer getFactorage() {
			return factorage;
		}

		public void setFactorage(Integer factorage) {
			this.factorage = factorage;
		}

		public String getRefundInfo() {
			return refundInfo;
		}

		public void setRefundInfo(String refundInfo) {
			this.refundInfo = refundInfo;
		}
	}
}
