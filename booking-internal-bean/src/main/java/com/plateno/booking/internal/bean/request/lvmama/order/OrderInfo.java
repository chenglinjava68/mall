package com.plateno.booking.internal.bean.request.lvmama.order;

import java.util.List;

public class OrderInfo {

	public String partnerOrderNo; // required
	public double orderAmount; // required
	public ProductInfo product; // optional
	public Booker booker; // optional
	public List<TravellersInfo> travellers; // optional

	public String getPartnerOrderNo() {
		return partnerOrderNo;
	}

	public void setPartnerOrderNo(String partnerOrderNo) {
		this.partnerOrderNo = partnerOrderNo;
	}

	public double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public ProductInfo getProduct() {
		return product;
	}

	public void setProduct(ProductInfo product) {
		this.product = product;
	}

	public Booker getBooker() {
		return booker;
	}

	public void setBooker(Booker booker) {
		this.booker = booker;
	}

	public List<TravellersInfo> getTravellers() {
		return travellers;
	}

	public void setTravellers(List<TravellersInfo> travellers) {
		this.travellers = travellers;
	}

	public static class Booker {
		public String name;
		public String mobile;
		public String email;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

	}

}