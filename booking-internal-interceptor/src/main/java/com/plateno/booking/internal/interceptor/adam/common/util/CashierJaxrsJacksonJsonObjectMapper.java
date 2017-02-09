package com.plateno.booking.internal.interceptor.adam.common.util;

import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * 
* @ClassName: CashierJaxrsJacksonJsonObjectMapper 
* @Description: 收银台单独配置
* @author zhengchubin
* @date 2017年2月7日 上午11:53:48 
*
 */
public class CashierJaxrsJacksonJsonObjectMapper extends ObjectMapper {

	public CashierJaxrsJacksonJsonObjectMapper() {
	        super();
	        super.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
	        super.configure(SerializationConfig.Feature.SORT_PROPERTIES_ALPHABETICALLY,true);
	        super.setSerializationInclusion(Inclusion.NON_NULL);  
	        this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

}



