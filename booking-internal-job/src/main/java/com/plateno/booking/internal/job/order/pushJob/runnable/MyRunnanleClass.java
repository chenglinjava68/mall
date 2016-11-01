/*package com.plateno.booking.internal.job.order.pushJob.runnable;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.PushOrderEnum;
import com.plateno.booking.internal.bean.request.common.EmailExecl;
import com.plateno.booking.internal.common.util.date.DateUtil;
import com.plateno.booking.internal.common.util.number.AmountUtils;
import com.plateno.booking.internal.common.util.redis.RedisUtils;
import com.plateno.booking.internal.dao.pojo.BillDetails;
import com.plateno.booking.internal.job.order.pushJob.callback.RunnableCallBack;

@Service
public class MyRunnanleClass implements Runnable{

		private Integer key;
		private List<EmailExecl> emailExecls = new CopyOnWriteArrayList<EmailExecl>();
		private static RedisUtils redisUtils;
		private Lock lock = new ReentrantLock();
		private static BillService billService;
		
		//回调赋值
		private RunnableCallBack<EmailExecl> callback;
		
		//线程执行完成信号灯
		private CountDownLatch countDownLatch;
		
		public  RedisUtils getRedisUtils() {
			return redisUtils;
		}

		@Resource
		public void setRedisUtils(RedisUtils redisUtils) {
			MyRunnanleClass.redisUtils = redisUtils;
		}

		public static BillService getBillService() {
			return billService;
		}

		@Resource
		public void setBillService(BillService billService) {
			MyRunnanleClass.billService = billService;
		}

		public MyRunnanleClass() {}

		public MyRunnanleClass(Integer value,final RunnableCallBack<EmailExecl> callback,CountDownLatch countDownLatch) {
			this.key = value;
			this.callback = callback;
			this.countDownLatch = countDownLatch;
		}

		@Override
		public void run() {
			try {
				lock.lock();
				Set<String> values =  redisUtils.smembers(BookingConstants.ORDER_STATUS+key);
				if(values == null || values.size() == 0)
					return;
				for(String tradeNo :values){
					BillDetails billDetails = billService.getBillByTradeNo(tradeNo);
					if (billDetails == null)
						break;
					if (billDetails.getStatus().equals(key)) {
						EmailExecl emailExecl = new EmailExecl();
						BeanUtils.copyProperties(billDetails, emailExecl);
						emailExecl.setCreateTime(DateUtil.dateToFormatStr(billDetails.getCreateTime(),DateUtil.YYYY_MM_DD_HH_MM_SS));
						emailExecl.setUpdateTime(DateUtil.dateToFormatStr(billDetails.getUpTime(),DateUtil.YYYY_MM_DD_HH_MM_SS));
						emailExecl.setRemark(PushOrderEnum.getPayType(key).getCodeName());
						emailExecl.setStatus(billDetails.getStatus());
						emailExecl.setPayType(billDetails.getPayId());
						emailExecl.setUserName(billDetails.getUsername());
                        emailExecl.setMoney(AmountUtils.changeF2Y(billDetails.getAmount().toString()));
						emailExecls.add(emailExecl);
					}
				}
				//清除key
				redisUtils.del(BookingConstants.ORDER_STATUS+key);
				callback.call(emailExecls);
				countDownLatch.countDown();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				lock.unlock();
			}
		}
	}*/