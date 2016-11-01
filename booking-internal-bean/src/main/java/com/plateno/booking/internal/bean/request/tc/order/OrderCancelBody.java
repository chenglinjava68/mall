package com.plateno.booking.internal.bean.request.tc.order;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 订单取消Body
 * @author yi.wang
 * @date 2016年6月1日下午6:33:01
 * @version 1.0
 * @Description
 */
@JsonAutoDetect(JsonMethod.FIELD)
public class OrderCancelBody {

	//同程订单流水号
	@JsonProperty("serialId")
	private String serialId;

	//退票数量,全部退款可以不传退票数量
	@JsonProperty("refundTicketsNum")
	private int refundTicketsNum;

	//退款类型 ,0：部分退款 1：全部退款
	@JsonProperty("RefundType")
	private int RefundType;

	//退款原因
	@JsonProperty("RefundReason")
	private String RefundReason;

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public int getRefundTicketsNum() {
		return refundTicketsNum;
	}

	public void setRefundTicketsNum(int refundTicketsNum) {
		this.refundTicketsNum = refundTicketsNum;
	}

	public int getRefundType() {
		return RefundType;
	}

	public void setRefundType(int refundType) {
		RefundType = refundType;
	}

	public String getRefundReason() {
		return RefundReason;
	}

	public void setRefundReason(String refundReason) {
		RefundReason = refundReason;
	}

}
