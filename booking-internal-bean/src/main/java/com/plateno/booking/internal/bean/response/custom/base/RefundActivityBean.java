package com.plateno.booking.internal.bean.response.custom.base;
import java.util.List;

public class RefundActivityBean {
	
	private List<RefundBill> refundBills;
	
	public List<RefundBill> getRefundBills() {
		return refundBills;
	}

	public void setRefundBills(List<RefundBill> refundBills) {
		this.refundBills = refundBills;
	}



	public static class RefundBill{
		private String tradeNo;
		private String orderNo;
		private String memberId;
		private String billLogTradeNo;
		public String getTradeNo() {
			return tradeNo;
		}
		public void setTradeNo(String tradeNo) {
			this.tradeNo = tradeNo;
		}
		public String getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}
		public String getMemberId() {
			return memberId;
		}
		public void setMemberId(String memberId) {
			this.memberId = memberId;
		}
		public String getBillLogTradeNo() {
			return billLogTradeNo;
		}
		public void setBillLogTradeNo(String billLogTradeNo) {
			this.billLogTradeNo = billLogTradeNo;
		}
	}

}