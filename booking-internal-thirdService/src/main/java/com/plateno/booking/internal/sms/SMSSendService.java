package com.plateno.booking.internal.sms;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.sms.model.SMSCallback;
import com.plateno.booking.internal.sms.model.SmsMessageReq;

/**
 * 短信发送
 * @author yi.wang
 * @date 2016年7月20日上午10:10:04
 * @version 1.0
 * @Description
 */
@Service
public class SMSSendService{


	@Autowired
	public TaskExecutor taskExecutor;

	/**
	 * 发送短信
	 * @param message
	 */
	public Boolean sendMessage(SmsMessageReq message) {
		return doSendMessage(message, null);
	}

	/**
	 * 发送短信，带回调
	 * @param message
	 * @param callBack
	 */
	public Boolean sendMessage(SmsMessageReq message, SMSCallback callBack) {
		return doSendMessage(message, callBack);
	}

	/**
	 * 异步发送短信
	 * @param message
	 */
	public void sendMessageAsyn(final SmsMessageReq message) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				doSendMessage(message, null);
			}
		});
	}

	/**
	 * 异步发送短信，带回调
	 * @param message
	 * @param callBack
	 */
	public void sendMessageAsyn(final SmsMessageReq message, final SMSCallback callBack) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				doSendMessage(message, callBack);
			}
		});
	}

	private Boolean doSendMessage(SmsMessageReq message, SMSCallback callBack) {
		boolean isSuccess = false;
		try {
			if (callBack != null) {
				callBack.preSendSMS(message);
			}
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			HttpHeaders headers = setHeaders();
			HttpEntity<String> entity = new HttpEntity<String>(gson.toJson(message), headers);
			RestTemplate rest = new RestTemplate();
			String resp = rest.postForObject(Config.SMS_SERVICE_URL, entity, String.class);
			LogUtils.httpLoggerInfo("===================短信发送：" + gson.toJson(headers) + "body:" + gson.toJson(message) + "返回码：" + resp);
			if (StringUtils.indexOf(resp, "-") < 0) {
				isSuccess = true;
			}
		} catch (Exception e) {
			LogUtils.sysErrorLoggerError("发送短信失败", e);
		}
		if (null != callBack) {
			callBack.postSendSMS(message, isSuccess);
		}
		return isSuccess;
	}

	private HttpHeaders setHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		headers.add("accept-charset", "utf-8");
		headers.add("Content-Type", "application/json; charset=UTF-8");
		headers.add("X-AUTH-HEADER", Config.SMS_SERVICE_TOKEN);
		return headers;
	}
}