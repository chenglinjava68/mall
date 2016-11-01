package com.plateno.booking.internal.bean.request.lvmama.order;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request")
public class PushOrderInfo {

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
		private String orderId; // 驴妈妈订单ID
		private String status; // 订单状态 				正常：NORMAL 完成：FINISHED 订单取消：CANCEL 已退款：REFUND
		private String approveStatus; // 订单审核状态
		private String paymentStatus; // 支付状态 		未支付：UNPAY 已支付：PAYED 已支付：PAYED
		private String waitPaymentTime; // 支付等待时间
		private String credenctStatus; // 凭证发送状态 	已发送：CREDENCE_SEND 未发送：CREDENCE_NO_SEND
		private String performStatus; // 凭证使用状态 	已使用：USED 未使用:UNUSE

		private List<Credential> credentials;

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

		public String getApproveStatus() {
			return approveStatus;
		}

		public void setApproveStatus(String approveStatus) {
			this.approveStatus = approveStatus;
		}

		public String getPaymentStatus() {
			return paymentStatus;
		}

		public void setPaymentStatus(String paymentStatus) {
			this.paymentStatus = paymentStatus;
		}

		public String getWaitPaymentTime() {
			return waitPaymentTime;
		}

		public void setWaitPaymentTime(String waitPaymentTime) {
			this.waitPaymentTime = waitPaymentTime;
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

		public List<Credential> getCredentials() {
			return credentials;
		}

		public void setCredentials(List<Credential> credentials) {
			this.credentials = credentials;
		}

		public static class Credential {
			private String goodsId; // 商品ID
			private String serialCode; // 通关凭证码 一般为数值或字母组合，作为主要的通关凭证
			private String QRcode; // 二维码 可扫描的二维码通过Base64加密
			private String additional; // 辅助码 通关所用的辅助数字，作为辅助的通关凭证

			public String getGoodsId() {
				return goodsId;
			}

			public void setGoodsId(String goodsId) {
				this.goodsId = goodsId;
			}

			public String getSerialCode() {
				return serialCode;
			}

			public void setSerialCode(String serialCode) {
				this.serialCode = serialCode;
			}

			public String getQRcode() {
				return QRcode;
			}

			public void setQRcode(String qRcode) {
				QRcode = qRcode;
			}

			public String getAdditional() {
				return additional;
			}

			public void setAdditional(String additional) {
				this.additional = additional;
			}
		}

	}
}
