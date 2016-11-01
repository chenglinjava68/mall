package com.plateno.booking.internal.bean.request.lvmama.order;

public class BookInfo {

		public String name;
		
		public String mobile;

		public String email;
		//证件号码
		public String credentials;

		//证件类型
		public String credentialsType;

		public String getName() {
			return name;
		}

		public void setName(String name){
			this.name = name;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getCredentials() {
			return credentials;
		}

		public void setCredentials(String credentials) {
			this.credentials = credentials;
		}

		public String getCredentialsType() {
			return credentialsType;
		}

		public void setCredentialsType(String credentialsType) {
			this.credentialsType = credentialsType;
		}
	}