package com.plateno.booking.internal.bean.request.tc.order;

import java.util.List;

/**
 * 1.1.13分销商订单查询接口(根据第三方流水号查询)
 * @author lingjw
 * @version 创建时间：2016年6月1日 下午3:52:15 
 * 说明:
 */
public class OrderListBody {

	private List<String> thirdSerialId;

	
	public OrderListBody() {
	}

	public OrderListBody(List<String> orders) {
		thirdSerialId = orders;
	}

	public List<String> getThirdSerialId() {
		return thirdSerialId;
	}

	public void setThirdSerialId(List<String> thirdSerialId) {
		this.thirdSerialId = thirdSerialId;
	}
	
}
