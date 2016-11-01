package com.plateno.booking.internal.common.util.number;

import java.math.BigDecimal;

public class DecimalUtil{

	public static BigDecimal multiply(double d1,double d2){
		BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
	    BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
		return bd1.multiply(bd2);
	}

}
