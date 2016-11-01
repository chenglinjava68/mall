package com.plateno.booking.internal.bean.request.Bendfit;

import org.codehaus.jackson.annotate.JsonProperty;

public class GrowValueBean {
	
	@JsonProperty("InGrowValueReq")
	private GrowValueReq inGrowValueReq;

	public GrowValueReq getInGrowValueReq() {
		return inGrowValueReq;
	}

	public void setInGrowValueReq(GrowValueReq inGrowValueReq) {
		this.inGrowValueReq = inGrowValueReq;
	}

	

}