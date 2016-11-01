package com.plateno.booking.internal.bean.request.lvmama.order;

import java.io.IOException;
import java.net.URLEncoder;

import com.plateno.booking.internal.bean.request.lvmama.base.BaseParam;
import com.plateno.booking.internal.bean.util.JsonUtils;

public class OrderStatusInfo  extends BaseParam{

	public OrderStatusInfo(){
	}
	
	private Request request;
	
	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public static class Request{
		private Order order;
		
		public Order getOrder() {
			return order;
		}

		public void setOrder(Order order) {
			this.order = order;
		}
	}
	



	public static class Order{
		private String partnerOrderNos;

		public String getPartnerOrderNos() {
			return partnerOrderNos;
		}

		public void setPartnerOrderNos(String partnerOrderNos) {
			this.partnerOrderNos = partnerOrderNos;
		}
	}



	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer("timestamp=");
		try {
			buf.append(getTimestamp())
			   .append("&appKey=")
			   .append(getAppKey())
			   .append("&messageFormat=")
			   .append(getMessageFormat())
			   .append("&sign=")
			   .append(getSign())
			   .append("&request=")
			   .append(URLEncoder.encode(JsonUtils.toJsonString(request),"utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return buf.toString();
	}

	public static void main(String[] args) throws Exception {
		OrderStatusInfo orderStatusInfo = new OrderStatusInfo();
		Request request = new Request();
		Order order = new Order();
		order.setPartnerOrderNos("O1459878886140318");
		request.setOrder(order);
		orderStatusInfo.setRequest(request);
		System.out.println(orderStatusInfo.toString());
		System.out.println(JsonUtils.toJsonString(orderStatusInfo));
	}
}