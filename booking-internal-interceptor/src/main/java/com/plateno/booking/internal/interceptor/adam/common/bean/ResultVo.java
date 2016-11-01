/**
 * 
 */
package com.plateno.booking.internal.interceptor.adam.common.bean;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.google.gson.Gson;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.contants.BaseReslutCodeConstants;
import com.plateno.booking.internal.interceptor.adam.service.chain.ServiceChain;
import com.plateno.booking.internal.interceptor.adam.service.common.AsynExcutor;

/**
 * @author user
 *
 */
public class ResultVo<T> implements Serializable {

	private static Logger log = Logger.getLogger(ResultVo.class);

	private String resultCode = "100"; // 返回代码

	private String resultMsg = "请求成功"; // 返回信息

	private transient String latestServiceName = "";

	private T data;

	/**
	 * copy resultVo
	 * 
	 * 1. copy resultCode
	 * 
	 * 2. append resultMsg
	 * 
	 * 3. not copy data
	 * 
	 * @param orig
	 */
	public void copyResult(ResultVo orig) {
		copyResult(orig, null);
	}

	/**
	 * copy resultVo
	 * 
	 * 1. copy resultCode
	 * 
	 * 2. append resultMsg
	 * 
	 * 3. copy data (defaultData is not null)
	 * 
	 * @param orig
	 */
	public void copyResult(ResultVo orig, T defaultData) {
		this.resultCode = orig.getResultCode();
		this.setResultMsg(orig.getResultMsg());
		if (null != defaultData) {
			if (null != orig.getData()) {
				this.data = defaultData;
				BeanUtils.copyProperties(orig.getData(), this.data);
				if (!orig.getData().equals(this.data)) {
					this.data = (T) orig.getData();
				}
			} else {
				this.data = defaultData;
			}
		}
	}

	/**
	 * copy resultVo
	 * 
	 * 1. copy thisResultCode + resultCode
	 * 
	 * 2. append thisResultCode + resultMsg
	 * 
	 * 3. copy data (defaultData is not null)
	 * 
	 * @param thisClass
	 * @param thisResultCode
	 * @param thisMessage
	 * @param orig
	 * @param defaultData
	 */
	public void copyResult(Class<? extends Object> thisClass, String thisResultCode, String thisMessage, ResultVo orig, T defaultData) {
		this.setResultCode(thisClass, thisResultCode + orig.getResultCode());
		this.setResultMsg(orig.getResultMsg());
		if (null != defaultData) {
			if (null != orig.getData()) {
				this.data = defaultData;
				BeanUtils.copyProperties(orig.getData(), this.data);
				if (!orig.getData().equals(this.data)) {
					this.data = (T) orig.getData();
				}
			} else {
				this.data = defaultData;
			}
		}
	}

	/**
	 * setResultCode
	 * 
	 * @param thisClass
	 * @param resultCode
	 */
	public void setResultCode(Class<? extends Object> thisClass, String resultCode) {
		if (!ResultVo.ForceSet.class.equals(thisClass)) {
			ServiceErrorCode errorCode = thisClass.getAnnotation(ServiceErrorCode.class);
			if (null != errorCode) {
				if (BaseReslutCodeConstants.CODE_NOT_SUPPORT.equals(errorCode.value())) {
					return;
				}
				if (!resultCode.startsWith(errorCode.value()) && !success() && !resultCode.startsWith(BaseReslutCodeConstants.CODE_ERROR_BUT_CONTINUE)) {
					if (!thisClass.equals(ServiceChain.class) && !thisClass.equals(AsynExcutor.class)) {
						log.warn(resultCode + "错误代码要以" + errorCode.value() + "开头");
					}
				}
			} else {
				log.warn("类" + thisClass.getSimpleName() + "要设置@ServiceErrorCode注解规范错误代码");
			}
		}
		this.resultCode = resultCode;
	}

	/**
	 * is this resultVo is success
	 * 
	 * @return
	 */
	public boolean success() {
		if (resultCode.equals(BaseReslutCodeConstants.CODE_SUCCESS) || resultCode.equals(BaseReslutCodeConstants.CODE_SUCCESS_AND_BREAK)) {
			return true;
		}
		return false;
	}

	public String getResultCode() {
		return resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	/**
	 * setResultMsg
	 * 
	 * append resultMsg
	 * 
	 * @return
	 */
	public void setResultMsg(String resultMsg) {
		setResultMsg(resultMsg, true);
	}

	/**
	 * setResultMsg
	 * 
	 * @param resultMsg
	 * @param isAppend
	 *            is append resultMsg
	 */
	public void setResultMsg(String resultMsg, boolean isAppend) {
		if (isAppend) {
			if (StringUtils.isBlank(resultMsg)) {
				return;
			}
			if (StringUtils.isBlank(this.resultMsg)) {
				this.resultMsg = resultMsg;
			} else {
				this.resultMsg = resultMsg;
			}
		} else {
			this.resultMsg = resultMsg;
		}
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String latestServiceName() {
		return latestServiceName;
	}

	public void setLatestServiceName(String latestServiceName) {
		this.latestServiceName = latestServiceName;
	}

	@Override
	public String toString() {
		Gson gson = new Gson();
		return "ResultVo [resultCode=" + resultCode + ", resultMsg=" + resultMsg + ", data=" + gson.toJson(data) + "]";
	}

	public static class ForceSet {

	}
}
