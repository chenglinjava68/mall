/**
 * 
 */
package com.plateno.booking.internal.bean.annotation.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author user
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamNotNull {
	int code();

	String msg() default "";
	
	boolean allowBlank() default false;
}
