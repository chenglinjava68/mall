package com.plateno.booking.internal.email.model;

public interface EmailCallback {
	/**
	 * 邮件发送前处理
	 * 
	 * @param eo
	 */
	void preSendEmail(EmailObject eo);

	/**
	 * 邮件发送成功或者失败后处理
	 * 
	 * @param eo
	 * @param isSuccess
	 */
	void postSendEmail(EmailObject eo, boolean isSuccess);

}
