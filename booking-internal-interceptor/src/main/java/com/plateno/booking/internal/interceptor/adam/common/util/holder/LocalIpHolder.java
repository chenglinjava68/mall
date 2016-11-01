/**
 * 
 */
package com.plateno.booking.internal.interceptor.adam.common.util.holder;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.plateno.booking.internal.interceptor.adam.common.util.AdamRegexUtil;

/**
 * @author user
 *
 */
public class LocalIpHolder {

	private static Logger log = Logger.getLogger(LocalIpHolder.class);

	private static String localIp = null;

	public static String getIp() {
		if (StringUtils.isBlank(localIp)) {
			try {
				initLocalIp();
			} catch (Exception e) {
				log.error(e, e);
			}
		}

		return localIp;
	}

	private static void initLocalIp() throws Exception {
		Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
		String thisIp = "";
		while (e.hasMoreElements()) {
			Enumeration<InetAddress> ee = e.nextElement().getInetAddresses();
			while (ee.hasMoreElements()) {
				String ip = ee.nextElement().getHostAddress();
				if (!AdamRegexUtil.isIp(ip)) {
					continue;
				}
				if ("127.0.0.1".equals(ip)) {
					continue;
				}
				thisIp = ip;
			}
		}
		if (StringUtils.isBlank(thisIp)) {
			thisIp = InetAddress.getLocalHost().getHostAddress();
		}

		String pid = ManagementFactory.getRuntimeMXBean().getName();
		localIp = thisIp + "_" + pid;
	}

}
