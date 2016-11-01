package com.plateno.booking.internal.bean.request.lvmama.refund;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
public class OrderRefundResponse {

	private Header header;
	private Body body;

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public static class Header {
		private String signed;

		public String getSigned() {
			return signed;
		}

		public void setSigned(String signed) {
			this.signed = signed;
		}
	}

	public static class Body {
		private Order order;

		public Order getOrder() {
			return order;
		}

		public void setOrder(Order order) {
			this.order = order;
		}
	}

	public static class Order {
		private String orderId; // 驴妈妈订单号			第三方订单号 ==>  partner_order_id
		private String partnerOrderID; // 分销商订单号	铂涛订单号     ==>  orderNo 
		private String orderStatus; // 订单状态
		private String requestStatus; // 申请状态
		private Integer refundAmount; // 退款金额，单位分
		private Integer factorage; // 手续费，单位分
		private String refundInfo; // 备注说明

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}


		public String getPartnerOrderID() {
			return partnerOrderID;
		}

		public void setPartnerOrderID(String partnerOrderID) {
			this.partnerOrderID = partnerOrderID;
		}

		public String getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
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