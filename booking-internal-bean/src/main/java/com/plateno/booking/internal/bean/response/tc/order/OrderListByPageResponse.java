package com.plateno.booking.internal.bean.response.tc.order;

import java.util.List;

import com.plateno.booking.internal.bean.response.tc.Response;

/**
 * 1.1.13分销商订单查询接口
 * @author lingjw
 * @version 创建时间：2016年6月1日 下午3:54:29 
 * 说明:
 */
public class OrderListByPageResponse extends Response {

	private List<OrderList> orderList;
	
	private Integer totalCount;
	
	public List<OrderList> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<OrderList> orderList) {
		this.orderList = orderList;
	}
	
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}





	public static class OrderList{
		
		private String orderSerialId;
		private String travelDate;
		private String orderState;
		private Integer ticketsNumber;
		public String getOrderSerialId() {
			return orderSerialId;
		}
		public void setOrderSerialId(String orderSerialId) {
			this.orderSerialId = orderSerialId;
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
		public Integer getTicketsNumber() {
			return ticketsNumber;
		}
		public void setTicketsNumber(Integer ticketsNumber) {
			this.ticketsNumber = ticketsNumber;
		}
		
		
		
	}
}
