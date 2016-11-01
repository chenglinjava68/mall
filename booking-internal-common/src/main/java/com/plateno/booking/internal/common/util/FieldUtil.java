package com.plateno.booking.internal.common.util;


public class FieldUtil{

	
	public final static String STRING = "class java.lang.String";
	
	public static String type(Class<?> clazz,String name) throws Exception{
		return clazz.getDeclaredField(name).getGenericType().toString();
	}
	
}
