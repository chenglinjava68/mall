package com.plateno.booking.internal.bean.exception;

public class OrderException extends Exception {

	private static final long serialVersionUID = 1L;

	private int errorCode;
	private String errorMessage;

	public OrderException() {
		super();
	}

	public OrderException(String message) {
		super(message);
	}

	public OrderException(int messagecode, String message) {
		errorCode = messagecode;
		errorMessage = message;
	}
	
	public OrderException(String message, Throwable cause) {
		super(message, cause);
	}
	
	protected OrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}