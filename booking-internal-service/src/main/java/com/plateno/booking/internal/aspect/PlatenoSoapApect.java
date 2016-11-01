package com.plateno.booking.internal.aspect;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.plateno.booking.internal.common.util.json.JsonUtils;

/**
 * 调用plateno.ws服务的切面,监控调用外部soap性能过慢的接口
 * 
 */
@Component
@Aspect
public class PlatenoSoapApect {
	
	private static Logger log = Logger.getLogger(PlatenoSoapApect.class);
	
	@Around("execution (* com.plateno.www.webservice.service.**.*Soap.*(..))")
	public Object aroundMethod(ProceedingJoinPoint pjd) throws IOException {
		Object result = null;
		String methodName = pjd.getSignature().getDeclaringTypeName() + "." + pjd.getSignature().getName();
		long begin = System.currentTimeMillis();
		long end = System.currentTimeMillis();
		String error = "";
		//执行目标方法
		try {
			//前置通知
			result = pjd.proceed();
			//返回通知
			end = System.currentTimeMillis();
			return result;
		} catch(Throwable e) {
			//异常通知
			end = System.currentTimeMillis();
			log.error("The method " + methodName + " occurs expection  " ,e);
			error = e.getMessage();
			throw new RuntimeException(e);
		} finally {

			//后置通知
			Integer overtime = 2000;
			//添加请求超过2秒的日志信息
			if((end - begin) >= overtime) {
				DecimalFormat decimalFormat = new DecimalFormat("000000");
				Map<String, String> map = new HashMap<String, String>();
				map.put("method", methodName);
				map.put("request", methodName + ":" + Arrays.asList(pjd.getArgs()));
				map.put("response", JsonUtils.toJsonString(result) + error);
				map.put("useTime", decimalFormat.format(end - begin) + "");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				map.put("requestTime", sdf.format(new Date(begin)));
				
				log.info("请求  ws 接口服务："+JsonUtils.toJsonString(map));
			}
			
		}
	}
	
}
