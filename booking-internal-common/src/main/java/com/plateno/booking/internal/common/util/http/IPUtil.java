package com.plateno.booking.internal.common.util.http;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class IPUtil{
	
	public static String getRequestIP(HttpServletRequest request) {
		String ip = request.getHeader("X-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if(ip!=null&&!"".equals(ip)&&ip.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")){
			return ip;
		}
		return "";
	}

	public static List<String> getLocalIP() throws Exception{
		List<String> ipList = new ArrayList<String>();
		Enumeration<NetworkInterface> iEnum = NetworkInterface.getNetworkInterfaces();
		
		while(iEnum.hasMoreElements()){
			NetworkInterface netInterface = (NetworkInterface)iEnum.nextElement();
			Enumeration<InetAddress> aEnum = netInterface.getInetAddresses();
			while(aEnum.hasMoreElements()){
				InetAddress ip = (InetAddress) aEnum.nextElement();
				if (ip!=null && ip instanceof Inet4Address){
					ipList.add(ip.getHostAddress());
				}
			}
		}
		return ipList;
	}

}
