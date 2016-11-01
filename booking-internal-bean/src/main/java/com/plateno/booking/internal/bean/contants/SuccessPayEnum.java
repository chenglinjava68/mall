package com.plateno.booking.internal.bean.contants;

public enum SuccessPayEnum {
	
	PAY_TICKET_SUCCESS(1, "成功"),
	PAY_TICKET_FAIL(-1, "出票失败"),
	PAY_TICKET_STAY(2, "OTA出票中"),
	PAY_TICKET_DOING(0, "付款待确认");

	private Integer index;
	private String name;

	private SuccessPayEnum(Integer index, String name) {
		this.index = index;
		this.name = name;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
