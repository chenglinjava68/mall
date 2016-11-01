package com.plateno.booking.internal.bean.contants;

public enum PushOrderEnum {
	
	ORDER_STATUS_200(200,"网关支付中超过15分钟"),
	ORDER_STATUS_300(300,"OTA支付中超过15分钟"),
	ORDER_STATUS_301(301,"短信发送失败"),
	ORDER_STATUS_302(302,"OTA支付失败"),
	ORDER_STATUS_303(303,"出票时间超过30分钟"),
	ORDER_STATUS_400(400,"OTA退款超过72小时"),
	ORDER_STATUS_500(500,"网关退款超过24小时"),
	ORDER_STATUS_502(502,"网关退款失败");
	
	private Integer code;
	private String codeName;
	
	private PushOrderEnum(Integer code,String codeName){
		this.code = code;
		this.codeName = codeName;
	}

	public Integer getCode() {
		return code;
	}

	public String getCodeName() {
		return codeName;
	}

	public static PushOrderEnum getPayType(Integer code){
		PushOrderEnum[] values = PushOrderEnum.values();
		for(PushOrderEnum c:values){
			if(code.equals(c.code)){
				return c;
			}
		}
		return null;
	}
}
