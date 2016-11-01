package com.plateno.booking.internal.bean.vo.hotel.request.inner;

import java.io.Serializable;
import java.math.BigDecimal;

public class RoomRateParam implements Serializable {

	private static final long serialVersionUID = 7102592392588175330L;

	private BigDecimal roomRate; // 每日房价

	private String endOfDay; // 营业日

	private String currencyCode = "CNY"; // 货币代码，默认人民币CNY，使用ISO4217标准

	public BigDecimal getRoomRate() {
		return roomRate;
	}

	public void setRoomRate(BigDecimal roomRate) {
		this.roomRate = roomRate;
	}

	public String getEndOfDay() {
		return endOfDay;
	}

	public void setEndOfDay(String endOfDay) {
		this.endOfDay = endOfDay;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

}
