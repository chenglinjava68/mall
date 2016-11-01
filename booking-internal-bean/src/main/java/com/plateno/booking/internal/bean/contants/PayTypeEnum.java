package com.plateno.booking.internal.bean.contants;


public enum PayTypeEnum {
		ALI_PAY(1,"支付宝支付"),
		WECHAT_PAY(2,"微信支付"),
		MONEY_PAY(3,"现金支付"),
		BANK_PAY(4,"银行卡支付"),
		GUA_PAY(5,"挂账支付");
		
		private Integer code;
		private String codeName;
		
		private PayTypeEnum(Integer code,String codeName){
			this.code = code;
			this.codeName = codeName;
		}

		public Integer getCode() {
			return code;
		}

		public String getCodeName() {
			return codeName;
		}

		public static PayTypeEnum getPayType(Integer code){
			PayTypeEnum[] values = PayTypeEnum.values();
			for(PayTypeEnum c:values){
				if(code == c.code){
					return c;
				}
			}
			return null;
		}
	}