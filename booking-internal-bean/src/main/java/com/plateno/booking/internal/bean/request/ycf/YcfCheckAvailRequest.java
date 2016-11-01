package com.plateno.booking.internal.bean.request.ycf;

import java.io.Serializable;

/**
 * 要出发可预订检查入参
 * @author yi.wang
 * @date 2016年6月16日下午3:38:32
 * @version 1.0
 * @Description
 */
public class YcfCheckAvailRequest implements Serializable {

	private static final long serialVersionUID = 3554636463460048188L;

	//【要】产品编号
	private String ProductId;

	//开始日期 2016-06-20
	private String beginDate;

	//结束日期2016-06-20
	private String endDate;

	public String getProductId() {
		return ProductId;
	}

	public void setProductId(String productId) {
		ProductId = productId;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
