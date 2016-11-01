package com.plateno.booking.internal.job.order.pushJob.callback;

import java.util.List;

/**
 * 钩子
 * 
 * @author user
 *
 * @param <T>
 */
public interface RunnableCallBack<T> {
	public void call(List<T> emailExecls) throws Exception;
}