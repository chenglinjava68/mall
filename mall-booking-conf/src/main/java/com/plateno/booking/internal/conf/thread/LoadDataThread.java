package com.plateno.booking.internal.conf.thread;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.plateno.booking.internal.conf.data.InitData;

/**
 * 重新刷新缓存线程
 * @author mogt
 * @date 2016年11月29日
 */
@Component
public class LoadDataThread extends Thread {

    private Logger logger = LoggerFactory.getLogger(LoadDataThread.class);

    /**
     * 线程是否在工作中
     */
    private boolean working;

    /**
     * 重新加载时间
     */
    private int interval = 300000;

    private LoadDataThread() {
        super("com.plateno.booking.internal.conf.thread.LoadDataThread");
    }
    
    private static LoadDataThread instance = new LoadDataThread();
    
    /**
     * 获得实例
     * @return
     */
    public static LoadDataThread getInstance() {
        return instance;
    }

    @PreDestroy
    public void stopWorking() {
        working = false;
        interrupt();
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        if (interval > 1) {
            this.interval = interval;
        }
    }
    
    @PostConstruct
    @Override
    public synchronized void start() {
    	super.start();
    }

    @Override
    public void run() {
        working = true;
        logger.info(super.getName() + " started ...");
        
        //等待20s，等待spring容器初始化完成
        try {
            Thread.sleep(20000);
        } catch (Exception e) {
            logger.info(super.getName() + " init wait exception", e);
        }
        
        while (working) {
            try {
                long t1 = System.currentTimeMillis();
                InitData.initData();
                long time = System.currentTimeMillis() - t1;
                logger.info("Data reloaded completed, time=" + time + " ms");
            } catch (Exception e) {
                logger.error("Data reloaded exception: " + e.getMessage(), e);
            }

            try {
                Thread.sleep(interval);
            } catch (Exception e) {
                logger.info(super.getName() + " was exception", e);
            }
        }
        logger.info(super.getName() + " stopped ...");
    }
}
