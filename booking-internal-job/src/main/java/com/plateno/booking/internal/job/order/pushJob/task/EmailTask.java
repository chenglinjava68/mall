/*package com.plateno.booking.internal.job.order.pushJob.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.job.order.pushJob.service.EmailService;

@Service
public class EmailTask implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(EmailTask.class);
	@Autowired
	private EmailService emailService;
	
	@Override
	public void run() {
		if (Config.START_JOB == 1) {
			Long pre_times=System.currentTimeMillis();
			logger.info(String.format("异常订单邮件推送job==>当前执行时间(毫秒):%s", pre_times));
			try {
				emailService.sendEmail();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			Long end_times=System.currentTimeMillis();
			logger.info(String.format("异常订单邮件推送job==>执行完时间(毫秒):%s", end_times));
			
			logger.info(String.format("异常订单邮件推送job==>共消耗时间(毫秒):%s", end_times-pre_times));
		}
	}

}
*/