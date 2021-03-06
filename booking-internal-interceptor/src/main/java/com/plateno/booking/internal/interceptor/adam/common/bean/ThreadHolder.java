/**
 * 
 */
package com.plateno.booking.internal.interceptor.adam.common.bean;

/**
 * @author user
 *
 */
public class ThreadHolder {

	private String runningAccountId;

	private Integer runningAccountFlag;

	private Integer requestLogFlag;

	public String getRunningAccountId() {
		return runningAccountId;
	}

	public void setRunningAccountId(String runningAccountId) {
		this.runningAccountId = runningAccountId;
	}

	public Integer getRunningAccountFlag() {
		return runningAccountFlag;
	}

	public void setRunningAccountFlag(Integer runningAccountFlag) {
		this.runningAccountFlag = runningAccountFlag;
	}

	public Integer getRequestLogFlag() {
		return requestLogFlag;
	}

	public void setRequestLogFlag(Integer requestLogFlag) {
		this.requestLogFlag = requestLogFlag;
	}

	@Override
	public String toString() {
		return "ThreadHolder [runningAccountId=" + runningAccountId + ", runningAccountFlag=" + runningAccountFlag + ", requestLogFlag=" + requestLogFlag + "]";
	}
}
