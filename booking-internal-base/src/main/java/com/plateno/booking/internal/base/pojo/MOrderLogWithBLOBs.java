package com.plateno.booking.internal.base.pojo;

public class MOrderLogWithBLOBs extends MOrderLog {
	/**
	 * 异常信息
	 */
    private String exception;

    /**
	 * 请求参数
	 */
    private String request;


    /**
	 * 返回参数
	 */
    private String response;

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception == null ? null : exception.trim();
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request == null ? null : request.trim();
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response == null ? null : response.trim();
    }
}