package com.plateno.booking.internal.bean.response.ycf;

import java.io.Serializable;

import java.util.List;

import com.plateno.booking.internal.bean.request.ycf.Vocher;


/**
 * 要出发重发凭证返回参数
 * @author yi.wang
 * @date 2016年7月6日上午11:47:02
 * @version 1.0
 * @Description
 */
public class YcfVoucherResponse implements Serializable {

	private static final long serialVersionUID = 7432270587899547826L;
	
	private List<Vocher> vochers;

	public List<Vocher> getVochers() {
		return vochers;
	}

	public void setVochers(List<Vocher> vochers) {
		this.vochers = vochers;
	}
}
