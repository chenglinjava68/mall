/**
 * 
 */
package com.plateno.booking.internal.interceptor.adam.common.aop;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.contants.AdamSysConstants;
import com.plateno.booking.internal.interceptor.adam.common.bean.contants.BaseReslutCodeConstants;
import com.plateno.booking.internal.interceptor.adam.common.util.AdamClassUtils;
import com.plateno.booking.internal.interceptor.adam.common.util.ThreadLocalHolder;
import com.plateno.booking.internal.interceptor.adam.common.util.context.SpringContextUtils;
import com.plateno.booking.internal.interceptor.adam.service.IRequestHook;

/**
 * @author user
 *
 */
@Component
@Aspect
@Order(0)
@ServiceErrorCode(BaseReslutCodeConstants.CODE_SYSTEM_ERROR)
public class RpcServiceAspect {

	private static Logger logger = Logger.getLogger(RpcServiceAspect.class);

	@Around("@annotation(com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.RpcService)")
	public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
		return doinvoke(pjp);
	}

	private Object doinvoke(ProceedingJoinPoint pjp) {
		Gson gson = new Gson();
		Object[] args = pjp.getArgs();
		Signature method = pjp.getSignature();

		StringBuilder sb = new StringBuilder();

		StringBuilder headerSB = new StringBuilder();
		Map<String, String> headersMap = new HashMap<String, String>();
		headersMap.put("header", "dubbo");
		for (String key : headersMap.keySet()) {
			headerSB.append(key + ":");
			headerSB.append(headersMap.get(key));
			headerSB.append(AdamSysConstants.LINE_SEPARATOR);
		}
		sb.append(headerSB);
		sb.append("Invoking [");
		sb.append(method.toString());
		sb.append("] method with arguments ");
		sb.append(AdamSysConstants.LINE_SEPARATOR);
		for (Object arg : args) {
			sb.append(arg + ":");
			sb.append(gson.toJson(arg));
			sb.append(AdamSysConstants.LINE_SEPARATOR);
		}
		ThreadLocalHolder.initRunningAccount();
		String runningAccount = ThreadLocalHolder.getRunningAccount();
		logger.info("RA:" + runningAccount + " " + sb.toString());
		long beginTime = System.currentTimeMillis();
		Object returnValue = null;
		String methodName = AdamClassUtils.getTargetClass(this).getSimpleName() + ".doinvoke";
		ThreadLocalHolder.setRunningAccountFlag(1);
		try {
			returnValue = doBefore(method.toString(), headersMap, args, returnValue);
			if (returnValue == null) {
				returnValue = pjp.proceed();
			}
			returnValue = doAfter(method.toString(), headersMap, args, returnValue);
		} catch (Throwable t) {
			logger.error("returnValue:[" + gson.toJson(returnValue) + "]" + t, t);
			ResultVo<String> resultVo = new ResultVo<String>();
			resultVo.setResultCode(this.getClass(), BaseReslutCodeConstants.CODE_SYSTEM_ERROR);
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			t.printStackTrace(pw);
			resultVo.setResultMsg(sw.toString());
			returnValue = resultVo;
		}

		if (returnValue instanceof ResultVo) {
			ResultVo resultVo = (ResultVo) returnValue;
			resultVo.setResultMsg("ra:" + runningAccount);
			if (resultVo.getResultCode().startsWith(BaseReslutCodeConstants.CODE_SYSTEM_ERROR)) {
				//logService.sendTechnologyErrorAccountLog(args, returnValue, methodName, "系统异常");
				logger.error("请求方法：" + methodName + "系统异常");
			}
		}
		long endTime = System.currentTimeMillis();

		logger.info("RA:" + runningAccount + " " + "Method [" + method.toString() + "] " + AdamSysConstants.LINE_SEPARATOR + "returned [" + gson.toJson(returnValue) + "]" + "useTime:" + (endTime - beginTime));
		return returnValue;
	}

	private Object doBefore(String url, Map<String, String> headersMap, Object[] income, Object output) throws Exception {
		IRequestHook requestHook = SpringContextUtils.getSpringBeanByType(IRequestHook.class);
		if (null != requestHook) {
			return requestHook.doBefore(url, headersMap, income, output);
		}
		return null;
	}

	private Object doAfter(String url, Map<String, String> headersMap, Object[] income, Object output) throws Exception {
		IRequestHook requestHook = SpringContextUtils.getSpringBeanByType(IRequestHook.class);
		if (null != requestHook) {
			return requestHook.doAfter(url, headersMap, income, output);
		}
		return null;
	}
}
