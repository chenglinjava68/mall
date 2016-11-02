package com.plateno.booking.internal.bean.contants;

/**
 * 支付网关响应代码
 * @author mogt
 * @date 2016年10月27日
 */
public class PayGateCode {
	
	/**
	 * 交易成功
	 */
	public static final String SUCCESS = "0000";
	
	/**
	 * 交易失败
	 */
	public static final String FAIL = "0100";
	
	/**
	 * 处理中
	 */
	public static final String HADNLING = "1000";
	
	/**
	 * 用户支付中
	 */
	public static final String PAY_HADNLING = "0600";
	
	/**
	 * 未知状态
	 */
	public static final String UNKNOWN_STATUS = "1004";
	
	/**
	 * 退款成功
	 */
	public static final String REFUND_SUCCESS = "0010";
	
	/**
	 * 退款失败
	 */
	public static final String REFUND_FAIL = "0020";
	
	/**
	 * 请求异常
	 */
	public static final String REQUEST_EXCEPTION = "0900";
}
