package com.plateno.booking.internal.bean.request.lvmama.pay;

import java.io.IOException;
import java.net.URLEncoder;

import com.plateno.booking.internal.bean.request.lvmama.base.BaseParam;
import com.plateno.booking.internal.bean.util.JsonUtils;

public class PayInfo  extends BaseParam{

	public PayInfo(){
	}
	
	private Requests request;
	
	public Requests getRequest() {
		return request;
	}

	public void setRequest(Requests requests) {
		this.request = requests;
	}

	public static class Requests{
		private Orders order;
		
		public Orders getOrder() {
			return order;
		}

		public void setOrder(Orders orders) {
			this.order = orders;
		}
	}
	



	public static class Orders{
		private String partnerOrderNo;
		private String orderId;
		private String serialNum;

		public String getPartnerOrderNo() {
			return partnerOrderNo;
		}

		public void setPartnerOrderNo(String partnerOrderNo) {
			this.partnerOrderNo = partnerOrderNo;
		}

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public String getSerialNum() {
			return serialNum;
		}

		public void setSerialNum(String serialNum) {
			this.serialNum = serialNum;
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
		PayInfo orderStatusInfo = new PayInfo();
		Requests request = new Requests();
		Orders order = new Orders();
		order.setPartnerOrderNo("O1459878886140318");
		order.setOrderId("123");
		order.setSerialNum("123");
		request.setOrder(order);
		orderStatusInfo.setRequest(request);
		System.out.println(orderStatusInfo.toString());
		System.out.println(JsonUtils.toJsonString(orderStatusInfo));
	}
}