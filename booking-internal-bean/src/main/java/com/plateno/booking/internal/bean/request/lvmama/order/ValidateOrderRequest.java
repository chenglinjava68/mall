package com.plateno.booking.internal.bean.request.lvmama.order;
import java.io.IOException;
import java.net.URLEncoder;

import com.plateno.booking.internal.bean.request.lvmama.base.BaseParam;
import com.plateno.booking.internal.bean.util.JsonUtils;

public class ValidateOrderRequest extends BaseParam{

	public ValidateOrderRequest(String messageFormat) throws Exception {
		this.setMessageFormat(messageFormat);
	}
	
	public ValidateOrderRequest() throws Exception {}

	private RequestInfo request;

	public RequestInfo getRequest() {
		return request;
	}

	public void setRequest(RequestInfo request) {
		this.request = request;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer("timestamp=");
		try {
			buf.append(getTimestamp())
			   .append("&appKey=")
			   .append(getAppKey())
			   .append("&messageFormat=")
			   .append(getMessageFormat())
			   .append("&sign=")
			   .append(getSign())
			   .append("&request=")
			   .append(URLEncoder.encode(JsonUtils.toJsonString(request),"UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return buf.toString();
	}
}