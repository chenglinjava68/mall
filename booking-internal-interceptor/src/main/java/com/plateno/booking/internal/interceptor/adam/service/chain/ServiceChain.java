/**
 * 
 */
package com.plateno.booking.internal.interceptor.adam.service.chain;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.common.bean.ThreadHolder;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceAsyn;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceFailRetryTimes;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceOrder;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceType;
import com.plateno.booking.internal.interceptor.adam.common.bean.contants.AdamSysConstants;
import com.plateno.booking.internal.interceptor.adam.common.bean.contants.BaseReslutCodeConstants;
import com.plateno.booking.internal.interceptor.adam.common.util.AdamClassUtils;
import com.plateno.booking.internal.interceptor.adam.common.util.ThreadLocalHolder;
import com.plateno.booking.internal.interceptor.adam.common.util.TransactionUtil;
import com.plateno.booking.internal.interceptor.adam.common.util.context.SpringContextUtils;
import com.plateno.booking.internal.interceptor.adam.service.IService;
import com.plateno.booking.internal.interceptor.adam.service.common.AsynExcutor;

/**
 * @author user
 *
 */
@Component
@ServiceErrorCode(BaseReslutCodeConstants.CODE_SYSTEM_ERROR)
public class ServiceChain {

	private static Logger log = Logger.getLogger(ServiceChain.class);

	/**
	 * servicesMap
	 */
	private Map<String, List<IService>> servicesMap = new ConcurrentHashMap<String, List<IService>>();

	private AtomicBoolean isReady = new AtomicBoolean(false);

	/**
	 * 初始化
	 */
	private synchronized void initServiceChain() {
		//isReady.set(false);
		// 判断过滤器表中是否有对象
		if (servicesMap != null && servicesMap.size() > 0) {
			return;
		}

		String[] serviceNames = SpringContextUtils.getSpringBeanNamesByType(IService.class);

		for (String name : serviceNames) {
			IService service = SpringContextUtils.getBean(name);

			Class clazz = AdamClassUtils.getTargetClass(service);

			ServiceType serviceType = (ServiceType) clazz.getAnnotation(ServiceType.class);
			ServiceOrder serviceOrder = (ServiceOrder) clazz.getAnnotation(ServiceOrder.class);

			String serviceTypeValue = "";
			int serviceOrderValue = 0;

			if (null == serviceType) {
				continue;
			} else {
				serviceTypeValue = serviceType.value();
			}

			if (null == serviceOrder) {
				serviceOrderValue = 0;
			} else {
				serviceOrderValue = serviceOrder.value();
			}
			putServiceInServicesMap(serviceTypeValue, serviceOrderValue, service);
		}

		isReady.set(true);
		log.info(this);
	}

	/**
	 * 把服务按顺序放进服务链
	 * 
	 * @param serviceEnum
	 * @param serviceOrderValue
	 * @param serivce
	 */
	private void putServiceInServicesMap(String serviceEnum, int serviceOrderValue, IService serivce) {
		List<IService> serviceList = servicesMap.get(serviceEnum);
		if (CollectionUtils.isEmpty(serviceList)) {
			serviceList = Collections.synchronizedList(new LinkedList<IService>());
			servicesMap.put(serviceEnum, serviceList);
		}
		if (serviceList.size() == 0) {
			serviceList.add(serivce);
			return;
		}
		int realIndex = 0;
		for (int index = 0; index < serviceList.size(); index++) {
			IService serviceTmp = serviceList.get(index);
			ServiceOrder serviceOrderTmp = (ServiceOrder) AdamClassUtils.getTargetClass(serviceTmp).getAnnotation(ServiceOrder.class);
			if (null == serviceOrderTmp) {
				realIndex = 0;
				break;
			}
			if (serviceOrderValue <= serviceOrderTmp.value()) {
				realIndex = index;
				break;
			} else {
				realIndex++;
			}
		}
		serviceList.add(realIndex, serivce);
	}

	/**
	 * 处理服务
	 * 
	 * @param income
	 * @param output
	 * @param serviceEnum
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void doServer(Object income, ResultVo output, String serviceEnum) {
		if (servicesMap == null || servicesMap.size() == 0) {
			initServiceChain();
		}
		checkReady();
		// 遍历过滤器
		List<IService> doneServiceStack = new ArrayList<IService>();
		boolean isSuccess = true;
		if (CollectionUtils.isEmpty(servicesMap.get(serviceEnum))) {
			String msg = serviceEnum + "未能找到服务类别";
			log.error(msg);
			output.setResultCode(this.getClass(), BaseReslutCodeConstants.CODE_900001);
			output.setResultMsg(msg);
			return;
		}

		List<IService> serviceList = new LinkedList<IService>();
		serviceList.addAll(servicesMap.get(serviceEnum));

		for (IService service : serviceList) {
			doneServiceStack.add(service);
			output.setLatestServiceName(AdamClassUtils.getTargetClass(service).getName());
			dealService(service, income, output);
			if (!BaseReslutCodeConstants.CODE_SUCCESS.equals(output.getResultCode())) { // success是成功且继续下一步
				if (!BaseReslutCodeConstants.CODE_SUCCESS_AND_BREAK.equals(output.getResultCode())) { // successAndBreak是成功且但不继续往下走
					isSuccess = false; // 其它情况均为错误
				}
				if (output.getResultCode().startsWith(BaseReslutCodeConstants.CODE_ERROR_BUT_CONTINUE)) { // errorButContinue是错误但是继续往下执行
					continue;
				}
				break;
			}
		}

		for (int index = 0; index < doneServiceStack.size(); index++) {
			IService service = doneServiceStack.get(doneServiceStack.size() - (index + 1));
			try {
				if (isSuccess) {
					dealSuccess(service, income, output);
				} else {
					dealFail(service, income, output);
				}
			} finally {
				dealComplate(service, income, output);
			}
		}

		if (!isSuccess) {
			TransactionUtil.transactionRollBack();
		}
	}

	/**
	 * 查处理链是否已经准备好
	 */
	private void checkReady() {
		for (int i = 0; i < 20; i++) {
			if (isReady.get()) {
				return;
			} else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					log.error(e, e);
				}
			}
		}
	}

	/**
	 * 处理完成信息
	 * 
	 * @param doneServiceStack
	 * @param income
	 * @param output
	 * @param retryTime
	 */
	private void dealComplate(IService service, Object income, ResultVo output) {

		ServiceFailRetryTimes failRetryTimes = (ServiceFailRetryTimes) AdamClassUtils.getTargetClass(service).getAnnotation(ServiceFailRetryTimes.class);
		int retryTime = 1;
		if (null != failRetryTimes) {
			retryTime = failRetryTimes.complate();
		}

		int sleep = 0;
		if (null != failRetryTimes) {
			sleep = failRetryTimes.sleep();
		}

		String methodName = "doComplate";
		boolean isAsyn = getIsAsyn(service, methodName);
		if (isAsyn) {
			excASyn(service, income, output, methodName, retryTime, sleep, false);
		} else {
			excSyn(service, income, output, methodName, retryTime, sleep, false);
		}
	}

	/**
	 * 处理失败
	 * 
	 * @param service
	 * @param income
	 * @param output
	 * @param retryTime
	 */
	private void dealFail(IService service, Object income, ResultVo output) {

		ServiceFailRetryTimes failRetryTimes = (ServiceFailRetryTimes) AdamClassUtils.getTargetClass(service).getAnnotation(ServiceFailRetryTimes.class);
		int retryTime = 1;
		if (null != failRetryTimes) {
			retryTime = failRetryTimes.fail();
		}

		int sleep = 0;
		if (null != failRetryTimes) {
			sleep = failRetryTimes.sleep();
		}

		String methodName = "doFail";
		boolean isAsyn = getIsAsyn(service, methodName);
		if (isAsyn) {
			excASyn(service, income, output, methodName, retryTime, sleep, false);
		} else {
			excSyn(service, income, output, methodName, retryTime, sleep, false);
		}
	}

	/**
	 * 处理成功信息
	 * 
	 * @param service
	 * @param income
	 * @param output
	 * @param retryTime
	 */
	private void dealSuccess(IService service, Object income, ResultVo output) {

		ServiceFailRetryTimes failRetryTimes = (ServiceFailRetryTimes) AdamClassUtils.getTargetClass(service).getAnnotation(ServiceFailRetryTimes.class);
		int retryTime = 1;
		if (null != failRetryTimes) {
			retryTime = failRetryTimes.success();
		}

		int sleep = 0;
		if (null != failRetryTimes) {
			sleep = failRetryTimes.sleep();
		}

		String methodName = "doSuccess";
		boolean isAsyn = getIsAsyn(service, methodName);
		if (isAsyn) {
			excASyn(service, income, output, methodName, retryTime, sleep, false);
		} else {
			excSyn(service, income, output, methodName, retryTime, sleep, false);
		}
	}

	/**
	 * 进行服务
	 * 
	 * @param service
	 * @param income
	 * @param output
	 */
	private void dealService(IService service, Object income, ResultVo output) {
		ServiceFailRetryTimes failRetryTimes = (ServiceFailRetryTimes) AdamClassUtils.getTargetClass(service).getAnnotation(ServiceFailRetryTimes.class);
		int retryTime = 1;
		if (null != failRetryTimes) {
			retryTime = failRetryTimes.server();
		}

		int sleep = 0;
		if (null != failRetryTimes) {
			sleep = failRetryTimes.sleep();
		}

		String methodName = "doService";
		boolean isAsyn = getIsAsyn(service, methodName);
		if (isAsyn) {
			excASyn(service, income, output, methodName, retryTime, sleep, true);
		} else {
			excSyn(service, income, output, methodName, retryTime, sleep, true);
		}
	}

	private void excASyn(IService service, Object income, ResultVo output, String methodName, int retryTime, int sleep, boolean isSetResultCode) {
		ThreadHolder threadHolder = ThreadLocalHolder.getThreadHolder();
		try {
			AsynExcutor asynExcutor = new AsynExcutor(service, income, output, methodName, retryTime, sleep, threadHolder);
			Thread thread = new Thread(asynExcutor);
			thread.start();
		} catch (Exception e) {
			log.error(e, e);
			if (isSetResultCode) {
				if (output.success()) {
					output.setResultCode(this.getClass(), BaseReslutCodeConstants.CODE_900000);
				}
			}
			output.setResultMsg("system error occor:" + e);
		}
	}

	private void excSyn(IService service, Object income, ResultVo output, String methodName, int retryTime, int sleep, boolean isSetResultCode) {
		String oldResultCode = output.getResultCode();
		for (int retryTimeindex = 0; retryTimeindex < retryTime; retryTimeindex++) {
			long begin = System.currentTimeMillis();
			addBeginLog(service, income, output, methodName);
			try {
				if ("doService".equals(methodName)) {
					service.doService(income, output);
				} else if ("doSuccess".equals(methodName)) {
					service.doSuccess(income, output);
				} else if ("doFail".equals(methodName)) {
					service.doFail(income, output);
				} else if ("doComplate".equals(methodName)) {
					service.doComplate(income, output);
				} else {
					throw new Exception("没有对应方法名");
				}
				addEndLog(service, income, output, methodName, begin);
				break;
			} catch (Exception e) {
				log.error(e, e);
				if (isSetResultCode) {
					if (output.success()) {
						output.setResultCode(this.getClass(), BaseReslutCodeConstants.CODE_900000);
					}
				}
				output.setResultMsg("system error occor:" + e);
				// 不能放finally，要不然resultCode就不是真实的
				addEndLog(service, income, output, methodName, begin);
				if (retryTimeindex < retryTime - 1) {
					output.setResultCode(this.getClass(), oldResultCode);
				}
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e1) {
					log.error(e, e);
				}
			}
		}

	}

	private boolean getIsAsyn(IService service, String methodName) {
		Class<?>[] paramClass = new Class<?>[2];
		paramClass[0] = Object.class;
		paramClass[1] = ResultVo.class;
		boolean isAsyn = false;
		try {
			Method[] methods = AdamClassUtils.getTargetClass(service).getDeclaredMethods();
			ServiceAsyn serviceAsyn = null;
			for (Method thisMethod : methods) {
				if (methodName.equals(thisMethod.getName())) {
					serviceAsyn = thisMethod.getAnnotation(ServiceAsyn.class);
					if (null != serviceAsyn) {
						break;
					}
				}
			}

			if (null != serviceAsyn && serviceAsyn.value) {
				isAsyn = true;
			}
		} catch (Exception e1) {
			log.error(e1, e1);
		}
		return isAsyn;
	}

	/**
	 * 重刷
	 */
	public void reset() {
		servicesMap = new ConcurrentHashMap<String, List<IService>>();
		initServiceChain();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("ServiceChain [");
		sb.append(AdamSysConstants.LINE_SEPARATOR);
		int lineLong = 80;
		int orderLong = 6;
		for (Entry<String, List<IService>> entry : servicesMap.entrySet()) {
			sb.append(" MAP :" + entry.getKey());
			sb.append(AdamSysConstants.LINE_SEPARATOR);
			List<IService> serviceList = entry.getValue();
			for (IService service : serviceList) {
				String serverLine = "    ";
				ServiceOrder serviceOrder = (ServiceOrder) AdamClassUtils.getTargetClass(service).getAnnotation(ServiceOrder.class);
				if (null != serviceOrder) {
					String orderStr = serverLine + serviceOrder.value();
					if (orderStr.length() < orderLong) {
						for (int spaceIndex = 0; spaceIndex < (orderLong - orderStr.length()); spaceIndex++) {
							orderStr = orderStr + " ";
						}
					}
					serverLine = serverLine + orderStr + "  ";
				}
				serverLine = serverLine + AdamClassUtils.getTargetClass(service).getSimpleName();
				sb.append(serverLine);
				if (serverLine.length() < lineLong) {
					for (int spaceIndex = 0; spaceIndex < (lineLong - serverLine.length()); spaceIndex++) {
						sb.append(" ");
					}
				}
				sb.append("(" + service.getClass().getName() + ")");
				sb.append(AdamSysConstants.LINE_SEPARATOR);
			}
		}
		sb.append("]");
		return sb.toString();
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
		//log.info("请求方法:" + methodName);
		//logService.sendRunningAccountLog(income, output, methodName, remark, beginTime);
	}

}
