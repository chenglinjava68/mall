package com.plateno.booking.internal.bean.contants;

import org.apache.log4j.Logger;

import com.plateno.booking.internal.bean.config.PropertiesUtil;

public class HttpContants {

	protected final static Logger log = Logger.getLogger(HttpContants.class);

	public static int CONNECT_SOKET_TIME_OUT_LONG;
	public static int CONNECT_TIME_OUT_LONG;
	public static int VERIFICATION_CODE_DEBUG;
	// 载入配置文件
	public final static String PROFILE_CXF = "config.properties";

	/* 载入配置 */
	static {
		try {

			CONNECT_SOKET_TIME_OUT_LONG = Integer.parseInt(PropertiesUtil.readPropertiesTools(PROFILE_CXF, "connect.timeout"));
			CONNECT_TIME_OUT_LONG = Integer.parseInt(PropertiesUtil.readPropertiesTools(PROFILE_CXF, "connect.soket.timeout"));
			VERIFICATION_CODE_DEBUG = Integer.parseInt(PropertiesUtil.readPropertiesTools(PROFILE_CXF,"verification.code.debug"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}
}
