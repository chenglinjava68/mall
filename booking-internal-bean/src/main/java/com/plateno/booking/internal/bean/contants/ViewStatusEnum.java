package com.plateno.booking.internal.bean.contants;

public enum ViewStatusEnum {
	
	VIEW_STATUS_PAYING(11,"待付款"),
//	VIEW_STATUS_PAY_UNUSE(12,"已付款待使用"),
	VIEW_STATUS_REFUNDING(13,"退款审核中"),
	VIEW_STATUS_REFUND(14,"已退款"),
	VIEW_STATUS_REFUND_FAIL(15,"退款失败"),
	VIEW_STATUS_CANNEL(16,"已取消"),
//	VIEW_STATUS_PAY_OUTTIME(17,"已付款已过期"),
	VIEW_STATUS_PAY_USE(18,"已付款"),
	VIEW_STATUS_DELIVERS(19,"已发货"),
	VIEW_STATUS_RECEI(20,"已收货"),
	VIEW_STATUS_WATIDELIVER(22,"待发货"),
	VIEW_STATUS_COMPLETE(21,"已完成"),
	VIEW_STATUS_PAYFAIL(23,"支付失败");
	
	private Integer code;
	private String codeName;
	
	private ViewStatusEnum(Integer code,String codeName){
		this.code = code;
		this.codeName = codeName;
	}

	public Integer getCode() {
		return code;
	}

	public String getCodeName() {
		return codeName;
	}

	public static ViewStatusEnum getViewStatus(Integer code){
		ViewStatusEnum[] values = ViewStatusEnum.values();
		for(ViewStatusEnum c:values){
			if(code.equals(c.code)){
				return c;
			}
		}
		return null;
	}
}
