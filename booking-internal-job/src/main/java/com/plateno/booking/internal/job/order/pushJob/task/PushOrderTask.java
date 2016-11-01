/*package com.plateno.booking.internal.job.order.pushJob.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.job.order.pushJob.service.PushOrderService;

@Service
public class PushOrderTask implements Runnable{
	
	private static final Logger logger = LoggerFactory.getLogger(PushOrderTask.class);
	
	@Autowired
	private PushOrderService pushOrderService;

	@Override
	public void run() {
		if (Config.START_JOB == 1) {
			Long pre_times=System.currentTimeMillis();
			logger.info(String.format("生成异常订单入redis的job==>当前执行时间(毫秒):%s", pre_times));
			try {
				pushOrderService.selectBillByStatus(20);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			Long end_times=System.currentTimeMillis();
			logger.info(String.format("生成异常订单入redis的job==>执行完时间(毫秒):%s", end_times));
			
			logger.info(String.format("生成异常订单入redis的job==>共消耗时间(毫秒):%s", end_times-pre_times));
		}		
	}

}
*/