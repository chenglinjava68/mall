package com.plateno.booking.internal.bean.response.ycf;

import java.util.Date;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.util.HttpUtils;
import com.plateno.booking.internal.bean.util.MD5Util;



/**
 * 要出发请求工具类
 * @author yi.wang
 * @date 2016年6月7日下午3:26:32
 * @version 1.0
 * @Description
 */
public final class YcfRequestUtil {

	private static Logger log = Logger.getLogger(YcfRequestUtil.class);

	/**
	 * 要出发header获取
	 * @param reqData
	 * @return
	 */
	public static HashMap<String, String> getHeadersMap(String reqData) {
		if (StringUtils.isNotBlank(reqData)) {
			Long timestamp = new Date().getTime();
			//md5(access_token+timestamp+secret+reqData)
			String signed = MD5Util.encode(Config.YCF_ACCESSTOKEN + timestamp + Config.YCF_SECRETKEY + reqData);
			HashMap<String, String> h = new HashMap<String, String>();
			h.put("partnerId", Config.YCF_PARTNERID);
			h.put("access_Token", Config.YCF_ACCESSTOKEN);
			h.put("secretkey", Config.YCF_SECRETKEY);
			h.put("version", Config.YCF_VERSION);
			h.put("signed", signed);
			h.put("timeStamp", timestamp.toString());
			return h;
		}
		return null;
	}

	/**
	 * 封装要出发请求
	 * @param json
	 * @param url
	 * @param interfaceName
	 * @return
	 * @throws OrderException
	 */
	public static String httpPostRequest(String url, String json, String interfaceName) throws OrderException {
		log.info(interfaceName + ",请求参数:" + json);
		String result = HttpUtils.httpPostRequest(url, json, YcfRequestUtil.getHeadersMap(json));
		if (StringUtils.isBlank(result)) {
			throw new OrderException(interfaceName + "请求失败,参数:" + json);
		}
		if (YcfRequestUtil.isTimeout(result)) {
			throw new OrderException(interfaceName + "请求超时,参数:" + json);
		}
		return result;
	}

	/**
	 * 判断是否网关超时
	 * @param response
	 */
	public static boolean isTimeout(String responseStr) {
		if (responseStr.contains("<html>") && responseStr.contains("<head>") && responseStr.contains("504")) {
			return true;
		}
		return false;
	}
}
