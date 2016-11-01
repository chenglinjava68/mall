package com.plateno.booking.internal.bean.request.Bendfit;

import org.codehaus.jackson.annotate.JsonProperty;

public class GrowBean {
	
	@JsonProperty("GrowSumReq")
	private GrowSumReqs growSumReqs;

	public GrowSumReqs getGrowSumReqs() {
		return growSumReqs;
	}

	public void setGrowSumReqs(GrowSumReqs growSumReqs) {
		this.growSumReqs = growSumReqs;
	}
	
}
