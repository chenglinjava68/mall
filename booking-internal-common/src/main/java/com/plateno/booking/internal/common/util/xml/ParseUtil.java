package com.plateno.booking.internal.common.util.xml;

import java.util.HashMap;

/**
 * @Description: 快钱VOP_CNP的Interface接口程序
 * @Copyright (c) 上海快钱信息服务有限公司
 * @version 2.0
 */

/**
 * 该类用来拼接XML串和解析XML
 * */
public class ParseUtil {
	/**
	 * 具体解析XML方法，返回一个HashMap
	 * resXml：快钱返回的XML数据流
	 * */
	@SuppressWarnings("rawtypes")
	public static HashMap parseXML(String resXml,boolean flag,String text1,String text2){
		HashMap returnRespXml=null;
		ParseXMLUtil pxu=ParseXMLUtil.initParseXMLUtil();//初始化ParseXMLUtil
		if(resXml!=null){
			//下面判断选用那个方法获取XML数据
			if(flag){
				returnRespXml= pxu.returnXMLData(pxu.parseXML(resXml),text1, text2);
			}else{
				returnRespXml= pxu.returnXMLDataList(pxu.parseXML(resXml),text1,text2);
			}
		}
		return returnRespXml;
	}
}
