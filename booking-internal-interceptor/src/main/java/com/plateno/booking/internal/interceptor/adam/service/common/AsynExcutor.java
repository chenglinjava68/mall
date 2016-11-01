/**
 * 
 */
package com.plateno.booking.internal.interceptor.adam.service.common;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.common.bean.ThreadHolder;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceFailRetryTimes;
import com.plateno.booking.internal.interceptor.adam.common.bean.contants.BaseReslutCodeConstants;
import com.plateno.booking.internal.interceptor.adam.common.util.AdamClassUtils;
import com.plateno.booking.internal.interceptor.adam.common.util.ThreadLocalHolder;
import com.plateno.booking.internal.interceptor.adam.common.util.context.SpringContextUtils;
import com.plateno.booking.internal.interceptor.adam.service.IService;

/**
 * @author user
 *
 */
@ServiceErrorCode(BaseReslutCodeConstants.CODE_SYSTEM_ERROR)
public class AsynExcutor implements Callable<ResultVo>, Runnable {

	private static Logger log = Logger.getLogger(AsynExcutor.class);

	public AsynExcutor(IService service, Object param, ResultVo resultVo, String methodName, int retryTime, int sleep, ThreadHolder threadHolder) {
		super();
		this.service = service;
		setParam(param);
		setResultVo(resultVo);
		this.methodName = methodName;
		this.retryTime = retryTime;
		setThreadHolder(threadHolder);
	}

	private IService service;

	private Object param;

	private ResultVo resultVo;

	private String methodName;

	private int retryTime;

	private int sleep;

	private ThreadHolder threadHolder;

	public ThreadHolder getThreadHolder() {
		return threadHolder;
	}

	public void setThreadHolder(ThreadHolder threadHolder) {
		ThreadHolder threadHolderClone = new ThreadHolder();
		BeanUtils.copyProperties(threadHolder, threadHolderClone);
		this.threadHolder = threadHolderClone;
	}

	public int getRetryTime() {
		return retryTime;
	}

	public void setRetryTime(int retryTime) {
		this.retryTime = retryTime;
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		try {
			Class paramCloneClass = param.getClass();
			Object paramClone = paramCloneClass.newInstance();
			BeanUtils.copyProperties(param, paramClone);
			this.param = paramClone;
		} catch (Exception e) {
			log.error(e, e);
		}
	}

	public ResultVo getResultVo() {
		return resultVo;
	}

	public void setResultVo(ResultVo resultVo) {
		ResultVo resultVoClone = new ResultVo();
		BeanUtils.copyProperties(resultVo, resultVoClone);
		this.resultVo = resultVoClone;
	}

	public IService getService() {
		return service;
	}

	public void setService(IService service) {
		this.service = service;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	private ResultVo work() {
		ThreadLocalHolder.setThreadHolder(threadHolder);
		for (int retryTimeindex = 0; retryTimeindex < retryTime; retryTimeindex++) {
			String oldResultCode = resultVo.getResultCode();
			long begin = System.currentTimeMillis();
			addBeginLog(service, param, resultVo, methodName);
			try {
				if ("doService".equals(methodName)) {
					service.doService(param, resultVo);
				} else if ("doSuccess".equals(methodName)) {
					service.doSuccess(param, resultVo);
				} else if ("doFail".equals(methodName)) {
					service.doFail(param, resultVo);
				} else if ("doComplate".equals(methodName)) {
					service.doComplate(param, resultVo);
				} else {
					throw new Exception("没有对应方法名");
				}
				addLog(service, param, resultVo, methodName, "end", begin);
				break;
			} catch (Exception e) {
				log.error(e, e);
				if (resultVo.success()) {
					resultVo.setResultCode(this.getClass(), BaseReslutCodeConstants.CODE_900000);
				}
				resultVo.setResultMsg("system error occor" + e);
				addLog(service, param, resultVo, methodName, "end", begin);
				if (retryTimeindex < retryTime - 1) {
					resultVo.setResultCode(this.getClass(), oldResultCode);
				}
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e1) {
					log.error(e, e);
				}
			}
		}
		return resultVo;
	}

	/**
	 * @param service
	 * @param income
	 * @param output
	 * @param methodName
	 * @param remark
	 */
	private void addBeginLog(IService service, Object income, ResultVo output, String methodName) {
		addLog(service, income, output, methodName, "begin", null);
	}

	/**
	 * 增加日志
	 * 
	 * @param service
	 * @param income
	 * @param output
	 * @param methodName
	 * @param remark
	 * @param begin
	 */
	private void addEndLog(IService service, Object income, ResultVo output, String methodName, Long beginTime) {
		addLog(service, income, output, methodName, "end", beginTime);
	}

	/**
	 * 增加日志
	 * 
	 * @param service
	 * @param income
	 * @param output
	 * @param methodName
	 * @param remark
	 * @param begin
	 */
	private void addLog(IService service, Object income, ResultVo output, String methodName, String remark, Long beginTime) {
		ServiceFailRetryTimes failRetryTimes = (ServiceFailRetryTimes) AdamClassUtils.getTargetClass(service).getAnnotation(ServiceFailRetryTimes.class);
		if (null == failRetryTimes) {
			return;
		}
		if (!failRetryTimes.log()) {
			return;
		}
		methodName = AdamClassUtils.getTargetClass(service).getSimpleName() + "." + methodName;
		//logService.sendRunningAccountLog(income, output, methodName, remark, beginTime);
		log.info("请求方法：" + methodName  + "备注：" + remark);
	}

	@Override
	public void run() {
		work();
	}

	@Override
	public ResultVo call() throws Exception {
		return work();
	}

	public int getSleep() {
		return sleep;
	}

	public void setSleep(int sleep) {
		this.sleep = sleep;
	}
}
