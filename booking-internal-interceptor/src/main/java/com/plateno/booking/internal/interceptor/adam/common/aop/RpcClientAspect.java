/**
 * 
 */
package com.plateno.booking.internal.interceptor.adam.common.aop;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.plateno.booking.internal.interceptor.adam.common.util.TimeoutUtil;

/**
 * @author user
 *
 */
@Component
@Aspect
@Order(0)
public class RpcClientAspect {
	private static Logger log = Logger.getLogger(RpcClientAspect.class);

	@Around("@annotation(com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.RpcClient)")
	public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
		return doinvoke(pjp);
	}

	private Object doinvoke(ProceedingJoinPoint pjp) throws Throwable {
		Gson gson = new Gson();
		Object result = null;
		String logStr = "";
		Object[] args = pjp.getArgs();
		String reqStr = gson.toJson(args);
		String processId = getNextProcessId();
		String header = "dubbo";
		Signature method = pjp.getSignature();
		long begin = System.currentTimeMillis();
		log.info(new StringBuffer(processId).append(" ").append(method.toString()).append(" input: ").append(reqStr).toString());
		try {
			result = pjp.proceed();
			logStr = gson.toJson(result);
			return result;
		} catch (Exception e) {
			logStr = e.getMessage();
				if (TimeoutUtil.isTimeOut(e)) {
					log.error(method.toString()+"请求超时");
				} else {
					log.error(method.toString()+"系统异常");
				}
			throw e;
		} finally {
			long end = System.currentTimeMillis();
			log.info(new StringBuffer(processId).append(" output: ").append(logStr).append(" used time: ").append(end - begin).toString());
		}
	}

	private String getNextProcessId() {
		return UUID.randomUUID().toString();
	}
}
