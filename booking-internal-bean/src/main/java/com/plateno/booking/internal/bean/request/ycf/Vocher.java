package com.plateno.booking.internal.bean.request.ycf;

import java.io.Serializable;

public class Vocher implements Serializable {

	private static final long serialVersionUID = -1668653036584355558L;

	//电子凭证码
	private String vocherNo;

	//二维码地址
	private String vocherUrl;

	public String getVocherNo() {
		return vocherNo;
	}

	public void setVocherNo(String vocherNo) {
		this.vocherNo = vocherNo;
	}

	public String getVocherUrl() {
		return vocherUrl;
	}

	public void setVocherUrl(String vocherUrl) {
		this.vocherUrl = vocherUrl;
	}

}