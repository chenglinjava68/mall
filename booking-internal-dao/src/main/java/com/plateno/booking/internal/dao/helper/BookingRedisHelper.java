package com.plateno.booking.internal.dao.helper;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;
import com.plateno.cache.RedisHelper;
import com.plateno.cache.lock.LockCallback;

/**
 * @author user
 *
 */
@ServiceErrorCode(BookingConstants.CODE_LOCK_ERROR)
public class BookingRedisHelper extends RedisHelper {

	public static <T> ResultVo<T> distributedLock(LockCallback<ResultVo<T>> action, String key, int expireSeconds, String errorCode, String errorMsg) {
		ResultVo<T> resultVo = new ResultVo<T>();
		try {
			if (setnx(key + "_disLockExec", "TRUE", Integer.valueOf(expireSeconds))) {
				try {
					ResultVo<T> t = action.exec();
					ResultVo<T> localObject2 = t;
					return localObject2;
				} catch(Exception e){
					e.printStackTrace();
				}finally {
					del(key + "_disLockExec");
				}
			}
			resultVo = new ResultVo<T>();
			resultVo.setResultCode(BookingRedisHelper.class, errorCode);
			resultVo.setResultMsg(errorMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultVo;
	}

}