package com.plateno.booking.internal.bean.request.tc.order;

public class OrderVerificationBody {
	private Integer maxIncrementId;
	private Integer queryNumber;

	public Integer getMaxIncrementId() {
		return maxIncrementId;
	}

	public void setMaxIncrementId(Integer maxIncrementId) {
		this.maxIncrementId = maxIncrementId;
	}

	public Integer getQueryNumber() {
		return queryNumber;
	}

	public void setQueryNumber(Integer queryNumber) {
		this.queryNumber = queryNumber;
	}
}
