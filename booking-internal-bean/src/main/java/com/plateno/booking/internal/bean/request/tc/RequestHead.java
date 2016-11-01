package com.plateno.booking.internal.bean.request.tc;



/**
 * 请求头部
 * @author yi.wang
 * @date 2016年5月27日上午10:29:32
 * @version 1.0
 * @Description
 */
public class RequestHead {
	private String digitalSign = "";
	private String agentAccount ="";

	public RequestHead() {

	}

	
	public RequestHead(String agentAccount, String digitalSign) {
		this.agentAccount = agentAccount;
		this.digitalSign = digitalSign;
	}


	public RequestHead(String digitalSign) {
		this.digitalSign = digitalSign;
	}

	public String getAgentAccount() {
		return agentAccount;
	}

	public void setAgentAccount(String agentAccount) {
		this.agentAccount = agentAccount;
	}

	public String getDigitalSign() {
		return digitalSign;
	}

	public void setDigitalSign(String digitalSign) {
		this.digitalSign = digitalSign;
	}

}