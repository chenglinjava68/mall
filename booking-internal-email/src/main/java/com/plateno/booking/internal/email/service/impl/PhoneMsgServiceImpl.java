package com.plateno.booking.internal.email.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.plateno.booking.internal.base.mapper.SmsLogMapper;
import com.plateno.booking.internal.base.pojo.SmsLog;
import com.plateno.booking.internal.common.util.bean.BeanUtils;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.email.model.BaseContent;
import com.plateno.booking.internal.email.service.PhoneMsgService;
import com.plateno.booking.internal.sms.SMSSendService;
import com.plateno.booking.internal.sms.model.SmsMessageReq;

@Component
public class PhoneMsgServiceImpl implements PhoneMsgService {
	
	protected final static Logger logger = LoggerFactory.getLogger(PhoneMsgServiceImpl.class);
	
	@Autowired
	private SMSSendService sendService;
	
	@Autowired
	private SmsLogMapper smsLogMapper;
	
	@Autowired
	private TaskExecutor taskExecutor;

	@Override
	public void sendPhoneMessage(String phone, String templateId, BaseContent content) {
		
		try {

			String contentStr = JsonUtils.toJsonString(content);
			
			
			logger.info("发送短信, phone:{}, templateId:{}, content:{}", phone, templateId, contentStr);

			int type = Integer.parseInt(templateId);
			
			// 发送内容
			Map<String, String> data = new HashMap<String, String>();
			BeanUtils.beanToMap(data, content);
			
			data.remove("objectNo");
			
			SmsMessageReq messageReq = new SmsMessageReq();
			messageReq.setType(type);
			messageReq.setParams(data);
			messageReq.setPhone(phone);
			
			Boolean res = sendService.sendMessage(messageReq);
			
			//记录短信日志
			SmsLog smslog = new SmsLog();
			smslog.setCreateTime(new Date());
			smslog.setIsSuccess(res == true ? 1 : 0);
			
			smslog.setContent(contentStr);
			smslog.setObjectNo(content.getObjectNo());
			smslog.setPhone(phone);
			smslog.setUpdateTime(new Date());
			smslog.setType(type);
			smsLogMapper.insertSelective(smslog);
			
		} catch (Exception e) {
			logger.error("发送短信异常", e);
		}
	}

	@Override
	public void sendPhoneMessageAsync(final String phone, final String templateId,
			final BaseContent content) {
		//发送退款短信
		try {
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					sendPhoneMessage(phone, templateId, content);
				}
			});
		} catch (Exception e) {
			logger.error("发送短信异常", e);
		}
	}

}
