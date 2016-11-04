package com.plateno.booking.internal.common.util.redis;

import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.cache.RedisHelper;

/**
 * redis实现一套同步锁
 * @author mogt
 * @date 2016年11月4日
 */
public class RedisLock {
	
	/**
	 * 上锁
	 * @param lockName 锁的名称
	 * @return
	 */
	public static boolean lock(String lockName) {
		
		//key为空，返回时失败
		if(lockName == null || lockName.trim().length() <= 0) {
			return false;
		}
		
		//默认锁60s
		boolean lock = RedisHelper.setnx(lockName, "TRUE", 60);
		return lock;
	}
	
	/**
	 * 上锁
	 * @param lockName 锁的名称
	 * @param seconds  锁定的时间
	 * @return
	 */
	public static boolean lock(String lockName, int seconds) {
		
		//key为空，返回时失败
		if(lockName == null || lockName.trim().length() <= 0) {
			return false;
		}
		
		//默认锁30s
		boolean lock = RedisHelper.setnx(lockName, "TRUE", seconds);
		return lock;
	}
	
	/**
	 * 解锁
	 * @param lockName	锁的名称
	 */
	public static void unLock(String lockName) {
		
		//key为空，返回时失败
		if(lockName == null || lockName.trim().length() <= 0) {
			return;
		}
		
		RedisHelper.del(lockName);
	}
	
	/**
	 * 上锁执行方法
	 * @param lockName
	 * @param holder
	 * @return
	 * @throws Exception 
	 */
	public static Object lockExec(String lockName, Holder holder) throws Exception {
		
		boolean lock = false;
		try {
			lock = RedisLock.lock(lockName);
			if(!lock) {
				ResultVo<Object> output = new ResultVo<Object>();
				output.setResultCode(RedisLock.class, MsgCode.BAD_REQUEST.getMsgCode());
				output.setResultMsg("访问过于频繁，请勿重复访问");
				return output;
			}
			
			//执行业务方法
			return holder.exec();
			
		} finally {
			if(lock) {
				RedisLock.unLock(lockName);
			}
		}
	}
	
	/**
	 * 业务方法钩子
	 * @author mogt
	 * @date 2016年11月4日
	 */
	public static interface Holder {
		Object exec() throws Exception;
	}
}
