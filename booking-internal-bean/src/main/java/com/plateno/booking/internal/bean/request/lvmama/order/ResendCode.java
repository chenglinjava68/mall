package com.plateno.booking.internal.bean.request.lvmama.order;
import java.io.IOException;

import com.plateno.booking.internal.bean.request.lvmama.base.BaseParam;
import com.plateno.booking.internal.bean.util.JsonUtils;

public class ResendCode extends BaseParam{

	public ResendCode() throws Exception {}
	
	private LvMaMaResendCode order;

	public ResendCode(BaseParam.DataFormat dataFormat,LvMaMaResendCode order) throws Exception {
		this.order = order;
		this.setMessageFormat(dataFormat.name());
	}

	public ResendCode(LvMaMaResendCode order)throws Exception {
		this.order = order;
	}

	public LvMaMaResendCode getOrder() {
		return order;
	}

	public void setOrder(LvMaMaResendCode order) {
		this.order = order;
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
			   .append("&request={\"order\":")
			   .append(JsonUtils.toJsonString(order))
			   .append("}");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return buf.toString();
	}
	
	public static class LvMaMaResendCode{
		private String PartnerOrderNo;
		
		private String orderId;

		public String getPartnerOrderNo() {
			return PartnerOrderNo;
		}

		public void setPartnerOrderNo(String partnerOrderNo) {
			PartnerOrderNo = partnerOrderNo;
		}

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
	}
}