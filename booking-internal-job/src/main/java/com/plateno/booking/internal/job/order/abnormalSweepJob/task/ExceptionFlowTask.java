package com.plateno.booking.internal.job.order.abnormalSweepJob.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.common.util.log.MDCUtil;
import com.plateno.booking.internal.job.order.abnormalSweepJob.service.MallExceptionFlowService;


/**
 * 异常扫单处理任务,处理订单状态 200,300,500 ==> 订单修复
 * 
 * 初始化订单(100) =====> 101 (超过半小时未支付)
 * 
 * @author user
 *
 */
@Service
public class ExceptionFlowTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionFlowTask.class);

    @Autowired
    private MallExceptionFlowService exceptionFlowService;

    @Override
    public void run() {
        if (Config.START_JOB == 1) {

            try {
                // log注入id
                MDCUtil.addTraceId();
                Long pre_times = System.currentTimeMillis();
                logger.info(String.format("异常扫单job==>当前执行时间(毫秒):==>%s", pre_times));
                try {
                    exceptionFlowService.handleException();
                } catch (Exception e) {
                    logger.error("异常订单处理异常", e);
                }
                Long end_times = System.currentTimeMillis();

                logger.info(String.format("异常扫单job==>执行完时间(毫秒):==>%s", end_times));

                logger.info(String.format("异常扫单job==>共消耗时间(毫秒):==>%s", end_times - pre_times));

            } finally {

                // 清除logID
                MDCUtil.removeTraceId();
            }

        }
    }

}
