package com.plateno.booking.internal.bean.request.convert;

/**
 * @author user
 *
 */
public class ConvertThirdPayParam extends ConvertBookingParam {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7623705921416240972L;

	private String referenceId; // 外部订单号

	private String partnerOrderId;

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

}
