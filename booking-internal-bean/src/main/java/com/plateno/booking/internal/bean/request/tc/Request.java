package com.plateno.booking.internal.bean.request.tc;

/**
 * 请求
 * @author yi.wang
 * @date 2016年5月27日上午10:28:57
 * @version 1.0
 * @Description
 */
public class Request<Body> {
	private RequestHead requestHead;
	private Body requestBody;

	public Request() {

	}

	public Request(RequestHead requestHead, Body requestBody) {
		this.requestHead = requestHead;
		this.requestBody = requestBody;

	}

	public RequestHead getRequestHead() {
		return requestHead;
	}

	public void setRequestHead(RequestHead requestHead) {
		this.requestHead = requestHead;
	}

	public Body getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(Body requestBody) {
		this.requestBody = requestBody;
	}

}