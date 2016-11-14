package com.plateno.booking.internal.job.order.abnormalSweepJob.task;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.common.util.log.MDCUtil;
import com.plateno.booking.internal.job.order.abnormalSweepJob.service.PayGatewaySyncService;


/**
 * 支付网关同步任务
 * @author mogt
 * @date 2016年11月14日
 */
@Service
public class PayGatewaySyncTask implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(PayGatewaySyncTask.class);
	
	@Autowired
	private PayGatewaySyncService payGatewaySyncService;
	
	@Override
	public void run() {
		if (Config.START_JOB==1) {
			
			//log注入id
			MDCUtil.addTraceId();
			Long pre_times=System.currentTimeMillis();
				logger.info(String.format("支付网关同步job==>当前执行时间(毫秒):==>%s", pre_times));
			try {
				payGatewaySyncService.sync();			
			} catch (Exception e) {
				e.printStackTrace();
				logger.info(ExceptionUtils.getStackTrace(e));
				
				//清除logID
				MDCUtil.removeTraceId();
			}
			Long end_times=System.currentTimeMillis();
			
			logger.info(String.format("支付网关同步job==>执行完时间(毫秒):==>%s", end_times));
		
			logger.info(String.format("支付网关同步job==>共消耗时间(毫秒):==>%s", end_times-pre_times));
		}
	}

}
