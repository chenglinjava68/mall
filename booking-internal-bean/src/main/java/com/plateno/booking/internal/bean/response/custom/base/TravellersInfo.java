package com.plateno.booking.internal.bean.response.custom.base;
public class TravellersInfo extends BookInfo {

	public String enName; 	
	public String email; 	
	public String birthday;

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
}