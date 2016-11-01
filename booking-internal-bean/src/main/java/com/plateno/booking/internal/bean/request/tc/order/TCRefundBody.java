package com.plateno.booking.internal.bean.request.tc.order;

public class TCRefundBody {
	private String returnCode;
	private String returnMsg;
	private String refundTime;				//退款时间
	private String serialId;				//订单流水号,第三方订单号 ==>  partner_order_id
	private Integer RefundType;				//退款类型        0：部分退款   1：全部退款
	private Integer refundTicketsNum;		//退票数	      全部退款可不传退票数量
	private Double refundAmount;			//退款金额
	private Double poundageAmount;			//手续费

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public Integer getRefundType() {
		return RefundType;
	}

	public void setRefundType(Integer refundType) {
		RefundType = refundType;
	}

	public Integer getRefundTicketsNum() {
		return refundTicketsNum;
	}

	public void setRefundTicketsNum(Integer refundTicketsNum) {
		this.refundTicketsNum = refundTicketsNum;
	}

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Double getPoundageAmount() {
		return poundageAmount;
	}

	public void setPoundageAmount(Double poundageAmount) {
		this.poundageAmount = poundageAmount;
	}
}
