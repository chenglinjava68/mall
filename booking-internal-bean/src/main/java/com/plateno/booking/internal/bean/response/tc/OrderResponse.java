package com.plateno.booking.internal.bean.response.tc;

import java.util.List;

/**
 * 同程分销商下单接口返回
 * @author lingjw
 * @version 创建时间：2016年5月30日 下午2:20:06 
 * 说明:
 */
public class OrderResponse extends Response {

	private List<OrderOrderResponse> orderResponse;


	
	public List<OrderOrderResponse> getOrderResponse() {
		return orderResponse;
	}

	public void setOrderResponse(List<OrderOrderResponse> orderResponse) {
		this.orderResponse = orderResponse;
	}

	public static class OrderOrderResponse {

		/**
		 * 同程流水号
		 */
		private String serialId;
		/**
		 * 第三方流水号
		 */
		private String thirdSerialId;
		/**
		 * 同程流水号
		 */
		private String tcSerialId;
		
		public String getSerialId() {
			return serialId;
		}
		public void setSerialId(String serialId) {
			this.serialId = serialId;
		}
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
		
	}
}
