package com.plateno.booking.internal.bean.request.Bendfit;

import org.codehaus.jackson.annotate.JsonProperty;

public class DeGrowValueBean {

	@JsonProperty("DeGrowValueReq")
	private GrowValueReq deGrowValueReq;

	public GrowValueReq getDeGrowValueReq() {
		return deGrowValueReq;
	}

	public void setDeGrowValueReq(GrowValueReq deGrowValueReq) {
		this.deGrowValueReq = deGrowValueReq;
	}

	
	
}