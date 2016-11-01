package com.plateno.booking.internal.bean.contants;

public enum OrderStatusEnum {
	
	ORDER_READY(100,"初始"),
	ORDER_CANNEL(101,"已取消"),
	ORDER_GATEWAY_PAYING(200,"网关支付中"),
	ORDER_GATEWAY_PAY_SUCCESS(201,"网关支付成功"),
	ORDER_GATEWAY_PAY_FAIL(202,"网关支付失败"),
	ORDER_OTA_PAYING(300,"OTA支付中"),
	ORDER_OTA_PAY_SUCCESS(301,"OTA支付成功"),
	ORDER_OTA_PAY_FAIL(302,"OTA支付失败"),
	ORDER_OTA_PAY_TICKETING(303,"OTA出票中"),
	ORDER_OTA_REFUNDING(400,"OTA退款申请中"),
	ORDER_OTA_REFUND_SUCCESS(401,"OTA退款成功"),
	ORDER_OTA_REFUND_FAIL(402,"OTA退款失败"),
	ORDER_GATEWAY_REFUNDING(500,"网关退款申请中"),
	ORDER_GATEWAY_REFUND_SUCCESS(501,"网关退款成功"),
	ORDER_GATEWAY_REFUND_FAIL(502,"网关退款失败");
	
	private Integer code;
	private String codeName;
	
	private OrderStatusEnum(Integer code,String codeName){
		this.code = code;
		this.codeName = codeName;
	}

	public Integer getCode() {
		return code;
	}

	public String getCodeName() {
		return codeName;
	}

	public static OrderStatusEnum getOrderStatus(Integer code){
		OrderStatusEnum[] values = OrderStatusEnum.values();
		for(OrderStatusEnum c:values){
			if(code.equals(c.code)){
				return c;
			}
		}
		return null;
	}
}
