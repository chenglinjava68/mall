package com.plateno.booking.internal.bean.response.lvmama.order;

import java.util.List;

import com.plateno.booking.internal.bean.response.lvmama.base.ResponseState.State;

public class LvMaMaOrderBean {
	
	private State  state;
	
	private List<SuccessOrder> orderList;
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<SuccessOrder> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<SuccessOrder> orderList) {
		this.orderList = orderList;
	}



	public static class SuccessOrder {
		private String partnerOrderNo; // 分销商订单编号 多个时以英文逗号分割
		private String orderId; // 驴妈妈订单号
		private String status; // 订单状态                                   正常：NORMAL            取消：CANCEL
		private String paymentStatus; // 支付状态              未支付：UNPAY           已支付：PAYED             部分支付：PARTPAY
		private String credenctStatus; // 凭证发送状态   已发送：CREDENCE_SEND   未发送：CREDENCE_NO_SEND
		private String performStatus; // 凭证使用状态      已使用： USED            未使用：UNUSE

		public String getPartnerOrderNo() {
			return partnerOrderNo;
		}

		public void setPartnerOrderNo(String partnerOrderNo) {
			this.partnerOrderNo = partnerOrderNo;
		}

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getPaymentStatus() {
			return paymentStatus;
		}

		public void setPaymentStatus(String paymentStatus) {
			this.paymentStatus = paymentStatus;
		}

		public String getCredenctStatus() {
			return credenctStatus;
		}

		public void setCredenctStatus(String credenctStatus) {
			this.credenctStatus = credenctStatus;
		}

		public String getPerformStatus() {
			return performStatus;
		}

		public void setPerformStatus(String performStatus) {
			this.performStatus = performStatus;
		}
	}

}