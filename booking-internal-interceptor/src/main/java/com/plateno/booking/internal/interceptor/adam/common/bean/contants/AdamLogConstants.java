package com.plateno.booking.internal.interceptor.adam.common.bean.contants;


public interface AdamLogConstants {

	/**
	 * runningAccount插入失败重试次数
	 */
	public static final Integer RUNNINGACCOUNT_RETRY_TIME = 3;

	/**
	 * requestLog插入失败重试次数
	 */
	public static final Integer REQUESTLOG_RETRY_TIME = 3;

	/**
	 * errorAccountLog插入失败重试次数
	 */
	public static final Integer ERRORACCOUNTLOG_RETRY_TIME = 5;

	/**
	 * runningAccount最大队列个数
	 */
	public static final Integer RUNNINGACCOUNT_LOG_LIMIT = 7000;

	/**
	 * requestLog最大队列个数
	 */
	public static final Integer REQUESTLOG_LOG_LIMIT = 7000;

	/**
	 * runningAccount最大队列个数
	 */
	public static final Integer RUNNINGACCOUNT_PAGE_SIZE = 1000;

	/**
	 * requestLog最大队列个数
	 */
	public static final Integer REQUESTLOG_PAGE_SIZE = 1000;

}
