package com.plateno.booking.internal.bean.response.tc;

/**
 * 返回
 * @author yi.wang
 * @date 2016年5月27日上午11:12:33
 * @version 1.0
 * @Description
 */
public class Response {

	private Integer respCode;

	private String respMsg;

	private String respTime;

	public Integer getRespCode() {
		return respCode;
	}

	public void setRespCode(Integer respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public String getRespTime() {
		return respTime;
	}

	public void setRespTime(String respTime) {
		this.respTime = respTime;
	}

}
