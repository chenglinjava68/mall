/*package com.plateno.booking.internal.job.order.pushJob.service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.request.common.EmailExecl;
import com.plateno.booking.internal.common.util.date.DateUtil;
import com.plateno.booking.internal.common.util.poi.Data2Execl;
import com.plateno.booking.internal.common.util.redis.RedisUtils;
import com.plateno.booking.internal.email.MailService;
import com.plateno.booking.internal.email.model.EmailObject;
import com.plateno.booking.internal.job.order.pushJob.callback.RunnableCallBack;
import com.plateno.booking.internal.job.order.pushJob.runnable.MyRunnanleClass;

@Service
public class EmailService {
	
	@Autowired
	public TaskExecutor taskExecutor;
	@Autowired
	public RedisUtils redisUtils;
	@Autowired
	public MailService mailService;
	
	//引入线程安全的集合类
	private static List<EmailExecl> mailList= new CopyOnWriteArrayList<EmailExecl>();
	
	public void sendEmail() throws Exception{
		List<Integer> list = BookingConstants.PUSH_ORDER_STATUS;
		Set<Integer> set = new HashSet<Integer>();
		
		//获取所有需要操作的异常订单状态,由于垃圾shardedJedis 不支持 keys操作,不得已使用了smembers操作 
		//TODO 有待优化
		for(Integer key : list){
			Set<String> keys =  redisUtils.smembers(BookingConstants.ORDER_STATUS+key);
			if (key != null && keys.size() > 0) {
				set.add(key);
			}
		}
		//优化代码,具体看keys处理的性能
		//Set<String> keys = redisUtils.keys(BookingConstants.ORDER_STATUS+"*");
		
		
		Iterator<Integer> it = set.iterator();
		
		//设置任务信号灯
		CountDownLatch countDownLatch = new CountDownLatch(set.size());
		
		//设置回调钩子
		RunnableCallBack<EmailExecl> callBack = new RunnableCallBack<EmailExecl>() {
			@Override
			public void call(List<EmailExecl> emailExecls) throws Exception {
				//赋值(符合条件,需要处理的订单)
				mailList.addAll(emailExecls);
			}
		};
		
		//开放多个线程,执行策略,为不同的异常订单状态,开放专线线程去执行
		while (it.hasNext()) {
			taskExecutor.execute(new MyRunnanleClass(it.next(),callBack,countDownLatch));
		}
		
		//主线程堵塞,等待所有任务完成
		countDownLatch.await();
		
		//poi操作
		Data2Execl.saveWithDisks(mailList, "errorOrder");
		
		//发送邮件操作
		EmailObject eo = new EmailObject();
		String[] email =  Config.EMAIL_GROUP.split(",");// 字符串转字符数组  
		eo.setSendTo(email);
		eo.setSubject("异常订单邮件推送");
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", "异常订单邮件推送");
		eo.setContentMap(map);
		
		String time = DateUtil.dateToFormatStr(new Date(), DateUtil.YYYY_MM_DD);
        File f = new File(System.getProperty("user.dir")+ System.getProperty("file.separator") +"send"+System.getProperty("file.separator")+"errorOrder_"+time+".xls");
		
		eo.addAttachment("故障订单描述说明.xls", f);

		mailService.sendMimeMailAsyn(eo, "mailTemplate.ftl");
	}
	
}
*/