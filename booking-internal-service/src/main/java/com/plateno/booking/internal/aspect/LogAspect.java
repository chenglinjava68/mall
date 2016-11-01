package com.plateno.booking.internal.aspect;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.common.util.json.JsonUtils;

/**
 * Created by jicexosl on 2015/2/3.
 */

@Aspect
@Component
public class LogAspect {

	
	
	@Around("execution (* com.plateno.booking.internal.service.thirdService.impl.**.*Service.*(..))")
	public Object aroundMethod(ProceedingJoinPoint pjd) throws IOException {
		Object result = null;
		String methodName = pjd.getSignature().getDeclaringTypeName() + "." + pjd.getSignature().getName();
		long begin = System.currentTimeMillis();
		long end = System.currentTimeMillis();
		LogUtils.countTimeLoggerInfo("请求方法：" + methodName);
		LogUtils.countTimeLoggerInfo("请求参数：" + JsonUtils.toJsonString(pjd.getArgs()));
		String error = "";
		// 执行目标方法
		try {
			// 前置通知
			result = pjd.proceed();
			// 返回通知
			end = System.currentTimeMillis();
			return result;
		} catch (Throwable e) {
			// 异常通知
			end = System.currentTimeMillis();
			LogUtils.countTimeLoggerError("The method " + methodName + " occurs expection  ", e);
			error = e.getMessage();
			throw new RuntimeException(e);
		} finally {
			// 后置通知
			Integer overtime = 2000;
			DecimalFormat decimalFormat = new DecimalFormat("000000");
			// 添加请求超过2秒的日志信息
			if ((end - begin) >= overtime) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("method", methodName);
				map.put("request",methodName + ":" + Arrays.asList(pjd.getArgs()));
				map.put("response", JsonUtils.toJsonString(result) + error);
				map.put("useTime", decimalFormat.format(end - begin) + "");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				map.put("requestTime", sdf.format(new Date(begin)));

				LogUtils.countTimeLoggerInfo("请求外部接口服务：" + map);
			}else{
				LogUtils.countTimeLoggerInfo("请求外部接口服务,耗费时间(毫秒)：" + decimalFormat.format(end - begin));
			}
		}
	}
}
