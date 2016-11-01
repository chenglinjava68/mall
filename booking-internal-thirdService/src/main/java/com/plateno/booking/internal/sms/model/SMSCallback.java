package com.plateno.booking.internal.sms.model;
public interface SMSCallback {
	/**
	 * 短信发送前处理
	 */
	void preSendSMS(SmsMessageReq message);

	/**
	 * 短信发送成功或者失败后处理
	 * @param isSuccess
	 */
	void postSendSMS(SmsMessageReq message, boolean isSuccess);

}