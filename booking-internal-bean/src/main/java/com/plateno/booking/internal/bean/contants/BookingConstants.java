package com.plateno.booking.internal.bean.contants;

public class BookingConstants implements BookingLockConstants ,BookingActionConstants,BookingResultCodeContants,BookingServiceEnumConstants{
	
	/**
	 * 锁获取失败
	 */
	public static final String CODE_LOCK_ERROR = "100";
	public static final String CODE_100001 = "100001"; // 会员锁获取失败，请勿频繁下单
	public static final String CODE_100002 = "100002"; // 会员锁获取失败，请勿频繁取消单
	
	/**
	 * 参数逻辑校验失败
	 */
	public static final String CODE_VERIFY_ERROR = "101";
	public static final String CODE_101001 = "101001"; // 下单payType有误
	
	
	/**
	 * 支付网关出错
	 */
	public static final String CODE_GATEWAY_ERROR = "201";
	public static final String CODE_201001 = "201001";	//支付网关请求异常
	public static final String CODE_201002 = "201002";	//支付网关请求异常,无返回结果
	public static final String CODE_201003 = "201003";	//支付网关请求异常,退款失败
	public static final String CODE_201004 = "201004";	//支付网关支付请求异常,
	public static final String CODE_201005 = "201005";	//支付网关支付请求异常,无返回结果
	public static final String CODE_201006 = "201006";	//支付网关支付请求异常,支付失败
	public static final String CODE_201007 = "201007";	//发起网关退款操作,发送异常
	public static final String CODE_201008 = "201008";	//发起网关退款操作,无返回结果
	public static final String CODE_201009 = "201009";	//发起网关退款操作,无返回结果
	
	public static final String CODE_201010 = "201010";	//网关支付回调,支付失败
	public static final String CODE_201011 = "201011";	//网关支付回调,支付失败,获取不到订单
	public static final String CODE_201012 = "201012";	//网关支付回调,支付失败,订单状态错误
	public static final String CODE_201013 = "201013";	//网关支付回调,支付失败,订单金额不一致
	public static final String CODE_201014 = "201014";	//网关支付回调,支付失败,redis订单数据丢失
	
	
	public static final String CODE_ORDER_ERROR = "601";
	public static final String CODE_601001 = "601-001";	//
	public static final String CODE_601002 = "601-002";	//
	public static final String CODE_601003 = "601-003";	// 退款审核中
	public static final String CODE_601004 = "601-004";	// 退款失败,申请被驳回
	public static final String CODE_601005 = "601-005"; // 退款失败,订单编号所对应的账单信息不存在,请核实好信息.
	public static final String CODE_601006 = "601-006"; // 退款失败,订单还未支付,不能进行退款服务
	public static final String CODE_601007 = "601-007"; // 退款失败,订单状态已经被使用
	
	public static final String CODE_601001001 = "601-001-001"; // 获取订单退款流程详情失败
	public static final String CODE_601001002 = "601-001-002"; // 获取订单退款详情错误,订单状态不处于退款阶段
	
	
	
	
	public static final String CODE_OTA_BOOK_ERROR = "701";
	public static final String CODE_701001 = "701-001"; // 订单已完成退款
	public static final String CODE_701015 = "701-015"; // 发现没有此单
	
	
	
	//----------------------------驴妈妈------------------------

	public static final String CODE_LVMAMA_BOOK_ERROR = "702";
	public static final String CODE_702001 = "702-001"; // 驴妈妈订单校验失败
	public static final String CODE_702002 = "702-002"; // 驴妈妈订单查询接口返回空串
	public static final String CODE_702003 = "702-003"; // 驴妈妈订单查询接口异常错误信息
	public static final String CODE_702004 = "702-004"; // 驴妈妈订单第三方订单号查询接口异常错误信息
	public static final String CODE_702005 = "702-005"; // 驴妈妈订单第三方订单号查询接口返回错误信息
	public static final String CODE_702006 = "702-006"; // 驴妈妈订单第三方订单号查询接口返回异常错误信息
	public static final String CODE_702007 = "702-007"; // 驴妈妈订单校验失败,接口返回空串
	public static final String CODE_702008 = "702-008"; // 驴妈妈订单校验失败,接口返回错误信息
	public static final String CODE_702009 = "702-009"; // 驴妈妈订单校验失败,接口返回异常错误信息
	
	public static final String CODE_LVMAMA_MESSAGE_ERROR  = "712";
	public static final String CODE_712001 = "712-001"; // 驴妈妈短信接口失败
	public static final String CODE_712002 = "712-002"; // 驴妈妈短信接口返回空串
	public static final String CODE_712003 = "712-003"; // 驴妈妈短信接口返回错误信息
	public static final String CODE_712004 = "712-004"; // 驴妈妈短信接口异常错误信息
	
	public static final String CODE_LVMAMA_PAY_ERROR  = "722";
	public static final String CODE_722001 = "722-001"; // 驴妈妈支付接口失败
	public static final String CODE_722002 = "722-002"; // 驴妈妈支付接口返回空串
	public static final String CODE_722003 = "722-003"; // 驴妈妈支付接口返回错误信息
	public static final String CODE_722004 = "722-004"; // 驴妈妈支付接口异常错误信息
	
	public static final String CODE_LVMAMA_REFUND_ERROR  = "732";
	public static final String CODE_732001 = "732-001"; // 驴妈妈退款接口失败
	public static final String CODE_732002 = "732-002"; // 驴妈妈退款接口返回空串
	public static final String CODE_732003 = "732-003"; // 驴妈妈退款接口返回错误信息
	public static final String CODE_732004 = "732-004"; // 驴妈妈退款接口异常错误信息
	
	
	
	//----------------------------同程------------------------
	
	public static final String CODE_TONGCHENG_BOOK_ERROR = "703";
	public static final String CODE_703001 = "703-001"; // 同程订单校验失败
	public static final String CODE_703002 = "703-002"; // 同程订单查询接口返回空串
	public static final String CODE_703003 = "703-003"; // 同程订单查询接口异常错误信息
	public static final String CODE_703004 = "703-004"; // 同程订单第三方订单号查询接口异常错误信息
	public static final String CODE_703005 = "703-005"; // 同程订单第三方订单号查询接口返回错误信息
	public static final String CODE_703006 = "703-006"; // 同程订单第三方订单号查询接口返回异常错误信息
	
	
	public static final String CODE_TONGCHENG_MESSAGE_ERROR  = "713";
	public static final String CODE_713001 = "713-001"; // 同程短信接口失败
	public static final String CODE_713002 = "713-002"; // 同程短信接口返回错误信息
	public static final String CODE_713003 = "713-003"; // 同程短信接口异常错误信息
	
	public static final String CODE_TONGCHENG_PAY_ERROR  = "723";
	public static final String CODE_723001 = "723-001"; // 同程支付接口失败
	public static final String CODE_723002 = "723-002"; // 同程支付接口返回空串
	public static final String CODE_723003 = "723-003"; // 同程支付接口返回错误信息
	public static final String CODE_723004 = "723-004"; // 同程支付接口异常错误信息
	
	public static final String CODE_TONGCHENG_REFUND_ERROR  = "733";
	public static final String CODE_733001 = "733-001"; // 同程退款接口失败
	public static final String CODE_733002 = "733-002"; // 同程退款接口返回空串
	public static final String CODE_733003 = "733-003"; // 同程退款接口返回错误信息
	public static final String CODE_733004 = "733-004"; // 同程退款接口异常错误信息
	

	//----------------------------要出发------------------------
	
	public static final String CODE_YAOCHUFA_BOOK_ERROR = "704";
	public static final String CODE_704001 = "704-001"; // 要出发订单校验失败
	public static final String CODE_704002 = "704-002"; // 要出发订单查询接口返回空串
	public static final String CODE_704003 = "704-003"; // 要出发订单查询接口异常错误信息
	public static final String CODE_704004 = "704-004"; // 要出发订单第三方订单号查询接口异常错误信息
	public static final String CODE_704005 = "704-005"; // 要出发订单第三方订单号查询接口返回错误信息
	public static final String CODE_704306 = "704-006"; // 要出发订单第三方订单号查询接口返回异常错误信息
	
	public static final String CODE_YAOCHUFA_MESSAGE_ERROR  = "714";
	public static final String CODE_714001 = "714-001"; // 要出发短信接口失败
	public static final String CODE_714002 = "714-002"; // 要出发短信接口返回错误信息
	public static final String CODE_714003 = "714-003"; // 要出发短信接口异常错误信息
	public static final String CODE_714004 = "714-004"; // 要出发短信接口异常错误信息
	public static final String CODE_YAOCHUFA_PAY_ERROR  = "724";
	public static final String CODE_724001 = "724-001"; // 要出发支付接口失败
	public static final String CODE_724002 = "724-002"; // 要出发支付接口返回空串
	public static final String CODE_724003 = "724-003"; // 要出发支付接口返回错误信息
	public static final String CODE_724004 = "724-004"; // 要出发支付接口异常错误信息
	
	public static final String CODE_YAOCHUFA_REFUND_ERROR  = "734";
	public static final String CODE_734001 = "734-001"; // 要出发退款接口失败
	public static final String CODE_734002 = "734-002"; // 要出发退款接口返回空串
	public static final String CODE_734003 = "734-003"; // 要出发退款接口返回错误信息
	public static final String CODE_734004 = "734-004"; // 要出发退款接口异常错误信息
	
	
	//----------------------------铂涛------------------------
	
	public static final String CODE_BOTAO_BOOK_ERROR = "804";
	public static final String CODE_804001 = "804-001"; // 铂涛订单校验失败
	public static final String CODE_804002 = "804-002"; // 铂涛订单查询接口返回空串
	public static final String CODE_804003 = "804-003"; // 铂涛订单查询接口异常错误信息
	public static final String CODE_804004 = "804-004"; // 铂涛订单第三方订单号查询接口异常错误信息
	public static final String CODE_804005 = "804-005"; // 铂涛订单第三方订单号查询接口返回错误信息
	public static final String CODE_804306 = "804-006"; // 铂涛订单第三方订单号查询接口返回异常错误信息
		
	public static final String CODE_BOTAO_SN_ERROR  = "814";
	public static final String CODE_814001 = "814-001"; // 铂涛获取下单交易凭证接口失败
	public static final String CODE_814002 = "814-002"; // 铂涛获取下单交易凭证接口返回空串
	public static final String CODE_814003 = "814-003"; // 铂涛获取下单交易凭证接口返回错误信息
	public static final String CODE_814004 = "814-004"; // 铂涛获取下单交易凭证接口异常错误信息
	public static final String CODE_814005 = "814-005"; // 铂涛获取下单交易凭证接口异常错误信息
	
	public static final String CODE_BOTAO_PAY_ERROR  = "824";
	public static final String CODE_824001 = "824-001"; // 铂涛支付接口失败
	public static final String CODE_824002 = "824-002"; // 铂涛支付接口返回空串
	public static final String CODE_824003 = "824-003"; // 铂涛支付接口返回错误信息
	public static final String CODE_824004 = "824-004"; // 铂涛支付接口异常错误信息
		
	public static final String CODE_BOTAO_REFUND_ERROR  = "834";
	public static final String CODE_834001 = "834-001"; // 铂涛退款接口失败
	public static final String CODE_834002 = "834-002"; // 铂涛退款接口返回空串
	public static final String CODE_834003 = "834-003"; // 铂涛退款接口返回错误信息
	public static final String CODE_834004 = "834-004"; // 铂涛退款接口异常错误信息
	
	public static final String CODE_BOTAO_BOOKING_ERROR  = "844";
	public static final String CODE_844001 = "844-001"; // 铂涛预定下单接口失败
	public static final String CODE_844002 = "844-002"; // 铂涛预定下单接口返回空串
	public static final String CODE_844003 = "844-003"; // 铂涛预定下单接口返回错误信息
	public static final String CODE_844004 = "844-004"; // 铂涛预定下单接口异常错误信息
	
	public static final String CODE_BOTAO_QUERY_ERROR  = "854";
	public static final String CODE_854001 = "854-001"; // 铂涛预定查询酒店订单详情接口失败
	public static final String CODE_854002 = "854-002"; // 铂涛预定查询酒店订单详情接口返回空串
	public static final String CODE_854003 = "854-003"; // 铂涛预定查询酒店订单详情接口返回错误信息
	public static final String CODE_854004 = "854-004"; // 铂涛预定查询酒店订单详情接口异常错误信息
	
	public static final String CODE_BOTAO_CHECK_PAY_ERROR  = "864";
	public static final String CODE_864001 = "864-001"; // 铂涛预定查询酒店订单详情接口失败
	public static final String CODE_864002 = "864-002"; // 铂涛预定查询酒店订单详情接口返回空串
	public static final String CODE_864003 = "864-003"; // 铂涛预定查询酒店订单详情接口返回错误信息
	public static final String CODE_864004 = "864-004"; // 铂涛预定查询酒店订单详情接口异常错误信息
	
	
	/**
	 * 本地数据库操作失败
	 */
	public static final String CODE_DB_BOOK_ERROR = "801";
	public static final String CODE_801001 = "801-001";	//数据库插入异常
	public static final String CODE_801002 = "801-002";	//数据库更新异常
	
	
}
