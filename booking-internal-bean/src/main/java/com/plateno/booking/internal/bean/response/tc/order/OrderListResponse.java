package com.plateno.booking.internal.bean.response.tc.order;

import java.util.List;

import com.plateno.booking.internal.bean.response.tc.Response;

/**
 * 1.1.13分销商订单查询接口(根据第三方流水号查询)
 * @author lingjw
 * @version 创建时间：2016年6月1日 下午3:54:29 
 * 说明:
 */
public class OrderListResponse extends Response {

	private List<OrderList> orderList;
	
	
	
	public List<OrderList> getOrderList() {
		return orderList;
	}



	public void setOrderList(List<OrderList> orderList) {
		this.orderList = orderList;
	}



	public static class OrderList{
		
		private String thirdSerialId;
		private String tcSerialId;
		private String createTime;
		private String travelDate;
		private String orderState;
		private String payState;
		public String getThirdSerialId() {
			return thirdSerialId;
		}
		public void setThirdSerialId(String thirdSerialId) {
			this.thirdSerialId = thirdSerialId;
		}
		public String getTcSerialId() {
			return tcSerialId;
		}
		public void setTcSerialId(String tcSerialId) {
			this.tcSerialId = tcSerialId;
		}
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
		public String getTravelDate() {
			return travelDate;
		}
		public void setTravelDate(String travelDate) {
			this.travelDate = travelDate;
		}
		public String getOrderState() {
			return orderState;
		}
		public void setOrderState(String orderState) {
			this.orderState = orderState;
		}
		public String getPayState() {
			return payState;
		}
		public void setPayState(String payState) {
			this.payState = payState;
		}
		
	}
}
