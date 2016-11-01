package com.plateno.booking.internal.bean.annotation.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by jicexosl on 2015/2/3.
 */
@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.METHOD)  
public @interface LogService {

    public enum LogType{ALL,REQUEST,RESPONSE}

    public LogType value() default LogType.ALL;

}
