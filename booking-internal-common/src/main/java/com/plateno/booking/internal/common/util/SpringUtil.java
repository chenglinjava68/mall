package com.plateno.booking.internal.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware {

	public static ApplicationContext context;

	public static ApplicationContext getApplicationContext() {
		return context;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtil.context = applicationContext;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException{
		return (T)context.getBean(name);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<?> clazz) throws BeansException{
		return (T)context.getBean(clazz);
	}

}