package com.plateno.booking.internal.interceptor.adam.service;

import java.util.Map;

public interface IRequestHook {

	public Object doBefore(String url, Map<String, String> headersMap, Object[] income, Object output) throws Exception;

	public Object doAfter(String url, Map<String, String> headersMap, Object[] income, Object output) throws Exception;

}
