package com.plateno.booking.internal.bean.contants;

import java.util.Arrays;
import java.util.List;

public interface BookingResultCodeContants {

	/**
	 * 驴妈妈请求成功返回状态码
	 */
	public final static String LVMAMA_SUCCESS_CODE = "1000";
	
	
	/**
	 * 景点服务请求成功返回状态码
	 */
	public static final Integer TRIP_GOOD_SERVER_SUCCESS_CODE=100;
	
	/**
	 * 网关请求成功返回状态码
	 */
	public static final String GATEWAY_PAY_SUCCESS_CODE = "0000";
	
	/**
	 * 网关请求成功返回状态码
	 */
	public static final String GATEWAY_REFUND_SUCCESS_CODE = "0010";
	
	/**
	 * 成长值请求成功返回状态码
	 */
	public static final Integer GROWVALUE_SUCCESS_CODE = 0;

	/**
	 * 同程请求成功返回状态码
	 */
	public final static Integer TC_SUCCESS_CODE = 1;
	
	
	/**
	 * 要出发退款回调成功返回状态码
	 */
	public final static Integer YCF_REFUND_SUCCESS_CODE = 1;
	
	/**
	 * 要出发退款回调失败返回状态码
	 */
	public final static Integer YCF_REFUND_ERROR_CODE = -1;
	
	
	/**
	 * 铂涛酒店预定成功状态码
	 */
	public final static String BOTAO_SUCCESS_CODE = "0";

	// 状态(100-初始;200-网关支付中;201-网关支付成功;202-网关支付失败;300-OTA支付中;301-OTA支付成功;302-OTA支付失败;400-OTA退款申请中;401-OTA退款成功;402-OTA退款失败;500-OTA网关退款申请中;501-网关退款成功;502-网关退款失败;)
	// 不允许执行被执行更新操作的订单状态
	public final static List<Integer> BILL_STATUS = Arrays.asList(201,300,301,400);
	
	// 不允许执行被执行删除操作的订单状态
	public final static List<Integer> ORDERS_STATUS = Arrays.asList(2,5,9);
	
	//不允许被修改的状态
	public final static List<Integer> ORDERS_MODIFY_STATUS = Arrays.asList(2,7,9,10);

	
	// 不允许执行被执行取消操作的订单状态
	public final static List<Integer> ORDERS_CANCEL_STATUS = Arrays.asList(3,4,5,6,7,8,9,10);
	
	/**
	 * 允许发货的状态
	 */
	public final static List<Integer> DELIVER_GOODS_STATUS = Arrays.asList(3, 8);

	// 允许执行被执行更新操作的订单状态
	public final static List<Integer> READY_PAY_STATUS = Arrays.asList(1,7,5);
	
	// 允许展示退款详情的订单状态
	public final static List<Integer> REFUND_PAY_STATUS = Arrays.asList(400,401,402,500,501,502);
	
	// 故障推送订单状态
	public final static List<Integer> PUSH_ORDER_STATUS = Arrays.asList(200,300,301,302,303,400,500,502);
	
	// 异常跑单处理订单状态
	public final static List<Integer> ERROR_ORDER_STATUS = Arrays.asList(200,300,500);
	
	// 待取消处理的订单状态
	public final static List<Integer> READY_ORDER_STATUS = Arrays.asList(100);
	
	// 本地订单状态发生改变
	public final static List<Integer> LOCAL_PAY_STATUS = Arrays.asList(202,300,301,302,303);
	
	// 支付流水类型： 1 ：说明是收入 2:说明是支出
	public final static Integer PAYCODE_INTEGER = 1;
	public final static Integer REFUNDCODE_INTEGER = 2;

	// 电子凭证使用状态
	public final static Integer TICKET_STATE_OBJECTS = 1;
	public final static Integer TICKET_STATE_NOUSE = 2;
	public final static Integer TICKET_STATE_USE = 3;
	
	
	//流水记录状态
	public final static Integer BILL_LOG_NORMAL = 1;
	public final static Integer BILL_LOG_SUCCESS = 2;
	public final static Integer BILL_LOG_FAIL = 3;
	
	
	// 账单表 支付方式 1-支付宝,2-微信,3-现金,4-银行卡,5-挂账
	public final static Integer PAY_TYPE_1 = 1;
	public final static Integer PAY_TYPE_2 = 2;
	public final static Integer PAY_TYPE_3 = 3;
	public final static Integer PAY_TYPE_4 = 4;
	public final static Integer PAY_TYPE_5 = 5;

	// //账单表 支付状态(100-初始;200-网关支付中;201-网关支付成功;202-网关支付失败;
	// 300-OTA支付中;301-OTA支付成功;302-OTA支付失败;303-OTA确认中;400-OTA退款申请中;401-OTA退款成功;
	// 402-OTA退款失败;500-OTA网关退款申请中;501-网关退款成功;502-网关退款失败;)
	public final static Integer PAY_STATUS_100 = 100;
	public final static Integer PAY_STATUS_101 = 101;
	public final static Integer PAY_STATUS_200 = 200;
	public final static Integer PAY_STATUS_201 = 201;
	public final static Integer PAY_STATUS_202 = 202;
	public final static Integer PAY_STATUS_300 = 300;
	public final static Integer PAY_STATUS_301 = 301;
	public final static Integer PAY_STATUS_302 = 302;
	public final static Integer PAY_STATUS_303 = 303;
	public final static Integer PAY_STATUS_400 = 400;
	public final static Integer PAY_STATUS_401 = 401;
	public final static Integer PAY_STATUS_402 = 402;
	public final static Integer PAY_STATUS_500 = 500;
	public final static Integer PAY_STATUS_501 = 501;
	public final static Integer PAY_STATUS_502 = 502;

	// 下单成功后,存放缓存
	public final static Integer ORDER_SUCCESS_PAY_TTL = 60 * 60 * 24 * 7; // 一星期
	
	public final static Integer ACTIVITY_ORDER_SUCCESS_PAY_TTL = 60 * 60 * 24 * 3; // 一星期

	// 订单状态 (1-正常;2-完成;3-取消;4-已退款)
	public final static Integer NOMARL = 1;
	public final static Integer FINISH = 2;
	public final static Integer CANCEL = 3;
	public final static Integer REFUND = 4;

	// 驴妈妈申请退款返回信息
	public final static String PASS = "PASS"; // 已退款
	public final static String REVIEWING = "REVIEWING"; // 审核中
	public final static String REJECT = "REJECT"; // 申请驳回

	// 流水状态 (1-初始,2-成功,3-失败)
	public final static Integer READY = 1;
	public final static Integer SUCCESS = 2;
	public final static Integer FAIL = 3;

	public final static String BILLLOG_KEY = "BILLKEY";
	
	
	//异常订单邮件推送 redis——key
	public final static String ORDER_STATUS = "order:status:";	//redis Key
	
	public final static String ORDER_STATUS_200 = "order:status:200";	//网关支付中超过15分钟
	public final static String ORDER_STATUS_300 = "order:status:300";	//OTA支付中超过15分钟
	public final static String ORDER_STATUS_301 = "order:status:301";	//短信发送失败
	public final static String ORDER_STATUS_302 = "order:status:302";	//OTA支付失败
	public final static String ORDER_STATUS_303 = "order:status:303";	//出票时间超过30分钟
	public final static String ORDER_STATUS_400 = "order:status:400";	//OTA退款超过72小时
	public final static String ORDER_STATUS_500 = "order:status:500";	//网关退款超过24小时
	public final static String ORDER_STATUS_502 = "order:status:502";	//网关退款失败
	
	//支付状态1待付款、2已取消 3待发货，4待收货，5已完成 6退款审核中、7已退款，8退款审核不通过 9已删除,10 退款中,11支付中,12支付失败,13退款失败,
	public final static Integer PAY_STATUS_1 = 1;
	public final static Integer PAY_STATUS_2 = 2;
	public final static Integer PAY_STATUS_3 = 3;
	public final static Integer PAY_STATUS_4 = 4;
	public final static Integer PAY_STATUS_5 = 5;
	public final static Integer PAY_STATUS_6 = 6;
	public final static Integer PAY_STATUS_7 = 7;
	public final static Integer PAY_STATUS_8 = 8;
	public final static Integer PAY_STATUS_9 = 9;
	public final static Integer PAY_STATUS_10 = 10;
	public final static Integer PAY_STATUS_11 = 11;
	
	/**
	 * 支付失败的状态过期，支付失败 --> 待付款
	 */
	@Deprecated
	public final static Integer PAY_STATUS_12 = 12;
	public final static Integer PAY_STATUS_13 = 13;
	
	//活动==>订单key
	public final static String ACTIVITY_ORDER_KEY = "ACTIVITY_ORDER_";
	
	//ALLMEMBERINFO
	public final static String MEMBER_INFO_ALL = "member:info:all:";

	/**
	 * msgCode为三位数字 第1位： 1-成功
	 * 
	 * 2-工具类
	 * 
	 * 3-参数类
	 * 
	 * 4-权限类
	 * 
	 * 5-系统类
	 * 
	 * 6-下订单错误
	 * 
	 * 7-第三方接口 70100 第3位 1：说明重发凭证错误 70200 第3位 2：说明创建订单错误 70300 第3位 3：说明订单支付错误
	 * 70400 第3位 4：说明订单信息错误
	 * 
	 * 第2位： 如果有需要可再进行类别细分
	 * 
	 * 第3位： 序号，递增
	 * 
	 * @author Administrator
	 * 
	 */
	public enum MsgCode {
		SUCCESSFUL("100", "请求成功"),
		JSON_CONVERSION_ERROR("200", "系统错误,JSON转换出错"),
		BAD_REQUEST("300", "请求参数有误"), 
		BAD_REQUEST_MEMBERID("301","请求参数有误,会员id不能为空"),

		SYSTEM_ERROR("500", "系统繁忙，请稍后再试"),

		SYSTEM_ORDER_DELETE("501", "删除失败,订单已被删除"), 
		SYSTEM_ORDER_CANCEL("901", "订单已经取消，请勿重复操作"), 
		
		SYSTEM_ORDER_ENTER("903", "订单所处状态有误，不能进行确定收货操作"), 
		
		SYSTEM_ORDER_DLIVER("902", "订单所处状态有误，不能进行发货操作"), 
		
		SYSTEM_ORDER_AUDI("904", "订单所处状态有误，不能进行退款审核操作"), 
		
		SYSTEM_ORDER_APPLICATION("905", "订单所处状态有误，不能进行申请退款操作"),
		
		GATEWAY_ERROR("910", "调用网关退款失败"),
		
		/**
		 * 支付网关退款中
		 */
		REFUND_HANDLING("911", "支付网关退款中"),
		
		SYSTEM_ORDER_NULL("502","删除失败,账单信息不存在,请核实信息"), 
		SYSTEM_ORDER_STATUS_DELETE_ERROR("503","账单所处状态,不支持被删除"), 
		SYSTEM_ORDER_STATUS_CANCEL_ERROR("504","账单所处状态,不支持被取消"), 

		SYSTEM_ORDER_STATUS_ERROR("505", "订单所处状态,不支持被修改"), 

		SYSTEM_SEND_MESSAGE_STATUS_ERROR("504","发送短信失败,账单所处状态有误,还未完成支付"), 
		SYSTEM_SEND_MESSAGE_TICKET_ERROR("505", "发送短信失败,门票已被使用"),

		VALIDATE_ORDER_ERROR("800","获取商品基本信息为空，订单校验失败"),
		
		VALIDATE_POINT_ERROR("809","积分不足,下单校验失败"),

		
		VALIDATE_ORDER_ERROR_PRODUCTNULL("801","获取商品基本信息为空，订单校验失败"),
		
		VALIDATE_ORDERAMOUNT_ERROR("804","订单总价核对不匹配，订单校验失败"),
		
		VALIDATE_ORDER_STOCK_ERROR("802","商品库存不足，订单校验失败"),
		
		VALIDATE_ORDER_STATUS_ERROR("803","商品已下架，订单校验失败"),
		
		VALIDATE_ORDERSJIPPINGTYPE_ERROR("805","配送方式错误"),
		
		VALIDATE_ORDERPOINT_ERROR("806","订单积分核对不匹配，订单校验失败"),
		
		BAD_ORDER_ID("602", "无法找到该订单"), 
		CANCEL_ORDER("603", "取消订单确认"), 
		CANCEL_ORDER_FAIL("604", "取消订单失败"), 
		ADD_BOOKING_FAIL("606", "提交订单失败"), 
		NO_SUCH_ORDER("608", "无效的订单号"),

		THIRD_SEND_CODE_ERROR("70100", "重发凭证失败,第三方没有返回响应结果"), 
		THIRD_SEND_CODE_NULL("70101", "重发凭证失败,第三方没有返回响应结果"),

		THIRD_ORDER_NULL("70400", "查询订单失败,第三方没有返回响应结果");

		private String msgCode;
		private String message;

		MsgCode(String msgCode, String message) {
			this.msgCode = msgCode;
			this.message = message;
		}

		public String getMsgCode() {
			return msgCode;
		}

		public String getMessage() {
			return message;
		}
	}

}
