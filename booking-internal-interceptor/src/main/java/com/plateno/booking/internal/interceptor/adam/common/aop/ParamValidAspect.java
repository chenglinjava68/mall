/**
 * 
 */
package com.plateno.booking.internal.interceptor.adam.common.aop;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ParamDefault;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ParamNotNull;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.contants.BaseReslutCodeConstants;
import com.plateno.booking.internal.interceptor.adam.common.util.AdamClassUtils;
import com.plateno.booking.internal.interceptor.adam.common.util.AdamDateUtil;

/**
 * @author user
 *
 */
@Component
@Aspect
@Order(0)
@ServiceErrorCode(BaseReslutCodeConstants.CODE_FIELD_NULL_ERROR)
public class ParamValidAspect {
	private static Logger log = Logger.getLogger(ParamValidAspect.class);

	@Around("@annotation(com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ParamValid)")
	public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
		return doinvoke(pjp);
	}

	private Object doinvoke(ProceedingJoinPoint pjp) throws Throwable {

		Object[] args = pjp.getArgs();

		ResultVo resultVo = new ResultVo();
		if (args != null && args.length != 0) {
			// 遍历所有field找NotNull的注解
			for (Object argument : args) {
				List<Field> fields = AdamClassUtils.getBeanAllFields(argument.getClass());
				if (fields == null || fields.isEmpty()) {
					continue;
				}
				for (Field field : fields) {
					ParamDefault paramDefault = field.getAnnotation(ParamDefault.class);
					if (paramDefault != null) {
						handleDefault(argument, field, paramDefault, resultVo);
					}

					ParamNotNull paramNotNull = field.getAnnotation(ParamNotNull.class);
					if (paramNotNull != null) {
						if (paramIsNull(argument, field, paramNotNull, resultVo)) {
							return resultVo;
						}
					}
				}
			}
		}
		Object result = pjp.proceed();
		// 返回通知
		return result;

	}

	/**
	 * @param argument
	 * @param field
	 * @param paramDefault
	 * @param resultVo
	 * @throws Exception
	 */
	private void handleDefault(Object argument, Field field, ParamDefault paramDefault, ResultVo resultVo) throws Exception {
		field.setAccessible(true);
		Object fieldValue = field.get(argument);
		if (checkNull(fieldValue, true)) {
			String defaultVal = paramDefault.value();
			if (paramDefault.isJson()) {
				Gson gson = new Gson();
				field.set(argument, gson.fromJson(defaultVal, field.getGenericType()));
			} else {
				if (field.getGenericType().equals(String.class)) {
					field.set(argument, defaultVal);
				} else if (field.getGenericType().equals(Integer.class)) {
					field.set(argument, Integer.valueOf(defaultVal));
				} else if (field.getGenericType().equals(Long.class)) {
					field.set(argument, Long.valueOf(defaultVal));
				} else if (field.getGenericType().equals(BigDecimal.class)) {
					field.set(argument, new BigDecimal(defaultVal));
				} else if (field.getGenericType().equals(Double.class)) {
					field.set(argument, Double.valueOf(defaultVal));
				} else if (field.getGenericType().equals(Date.class)) {
					field.set(argument, AdamDateUtil.stringToDate(defaultVal));
				} else if (field.getGenericType().equals(Boolean.class)) {
					if ("true".equals(defaultVal) || "Y".equals(defaultVal)) {
						field.set(argument, Boolean.TRUE);
					} else {
						field.set(argument, Boolean.FALSE);
					}
				} else {
					field.set(argument, defaultVal);
				}
			}
		}
	}

	/**
	 * @param argument
	 * @param field
	 * @param paramNotNull
	 * @param resultVo
	 * @return
	 * @throws Exception
	 */
	private boolean paramIsNull(Object argument, Field field, ParamNotNull paramNotNull, ResultVo resultVo) throws Exception {
		field.setAccessible(true);
		Object fieldValue = field.get(argument);
		if (checkNull(fieldValue, paramNotNull.allowBlank())) {
			resultVo.setResultCode(this.getClass(), paramNotNull.code());
			StringBuffer sb = new StringBuffer("arg:");
			sb.append(argument.getClass().getSimpleName());
			sb.append(" field:");
			sb.append(field.getName());
			sb.append(" needs value-");
			sb.append(paramNotNull.msg());
			log.info(sb.toString());
			resultVo.setResultMsg(sb.toString());
			return true;
		}
		return false;
	}

	/**
	 * @param fieldValue
	 * @param allowBlank
	 * @return
	 */
	private boolean checkNull(Object fieldValue, boolean allowBlank) {
		if (null == fieldValue) {
			return true;
		}

		if (allowBlank) {
			return false;
		}

		if (fieldValue instanceof String) {
			if (StringUtils.isBlank(fieldValue.toString())) {
				return true;
			}
		}

		if (fieldValue instanceof Collection) {
			if (CollectionUtils.isEmpty((Collection) fieldValue)) {
				return true;
			}
		}

		return false;
	}

}
