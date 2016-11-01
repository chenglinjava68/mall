package com.plateno.booking.internal.bean.request.ycf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 要出发创建订单入参
 * @author yi.wang
 * @date 2016年6月17日上午10:55:11
 * @version 1.0
 * @Description
 */
public class YcfOrderRequest implements Serializable {

	private static final long serialVersionUID = 6180425288115947755L;

	//【合】订单号,必填
	private String partnerOrderId;

	//【要】产品编号,必填
	private String productId;

	//【要】产品名称
	private String productName;

	//产品数量,必填
	private Integer qunatity;

	//总价,必填
	private BigDecimal amount;
	
	//销售总价
	private BigDecimal sellAmount;

	//联系人中文姓名,套餐预定规则要求，则必填
	private String cName;

	//联系人英文姓名,套餐预定规则要求，则必填
	private String eName;

	//联系人手机,套餐预定规则要求，则必填
	private String mobile;

	//联系人邮箱,套餐预定规则要求，则必填
	private String email;

	//联系人证件号,套餐预定规则要求，则必填
	private String credential;

	//联系人证件类型
	private Integer credentialType;

	//出行客人集合,套餐预定规则要求，则必填
	private List<YcfGuest> Guests;

	//价格集合,必填
	private List<YcfPriceItem> priceDetail;

	//房资源组
	private List<YcfRoom> roomDetail;

	//门票资源组
	private List<YcfTicket> ticketDetail;

	//餐饮资源组
	private List<YcfFood> foodDetail;
	
	public BigDecimal getSellAmount() {
		return sellAmount;
	}

	public void setSellAmount(BigDecimal sellAmount) {
		this.sellAmount = sellAmount;
	}

	public String getPartnerOrderId() {
		return partnerOrderId;
	}

	public void setPartnerOrderId(String partnerOrderId) {
		this.partnerOrderId = partnerOrderId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getQunatity() {
		return qunatity;
	}

	public void setQunatity(Integer qunatity) {
		this.qunatity = qunatity;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
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

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

	public Integer getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(Integer credentialType) {
		this.credentialType = credentialType;
	}

	public List<YcfGuest> getGuests() {
		return Guests;
	}

	public void setGuests(List<YcfGuest> guests) {
		Guests = guests;
	}

	public List<YcfPriceItem> getPriceDetail() {
		return priceDetail;
	}

	public void setPriceDetail(List<YcfPriceItem> priceDetail) {
		this.priceDetail = priceDetail;
	}

	public List<YcfRoom> getRoomDetail() {
		return roomDetail;
	}

	public void setRoomDetail(List<YcfRoom> roomDetail) {
		this.roomDetail = roomDetail;
	}

	public List<YcfTicket> getTicketDetail() {
		return ticketDetail;
	}

	public void setTicketDetail(List<YcfTicket> ticketDetail) {
		this.ticketDetail = ticketDetail;
	}

	public List<YcfFood> getFoodDetail() {
		return foodDetail;
	}

	public void setFoodDetail(List<YcfFood> foodDetail) {
		this.foodDetail = foodDetail;
	}

	public static class YcfGuest implements Serializable {

		private static final long serialVersionUID = -3824042853628326434L;
		//中文姓名,套餐预定规则要求，则必填
		private String cName;
		//英文姓名,套餐预定规则要求，则必填
		private String eName;
		//手机,套餐预定规则要求，则必填
		private String mobile;
		//邮箱,套餐预定规则要求，则必填
		private String email;
		//证件号,套餐预定规则要求，则必填
		private String credential;
		//证件类型
		private Integer credentialType;

		public String getcName() {
			return cName;
		}

		public void setcName(String cName) {
			this.cName = cName;
		}

		public String geteName() {
			return eName;
		}

		public void seteName(String eName) {
			this.eName = eName;
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

		public String getCredential() {
			return credential;
		}

		public void setCredential(String credential) {
			this.credential = credential;
		}

		public Integer getCredentialType() {
			return credentialType;
		}

		public void setCredentialType(Integer credentialType) {
			this.credentialType = credentialType;
		}
	}

	public static class YcfPriceItem implements Serializable {

		private static final long serialVersionUID = -5153048522876710230L;
		//日期 2016-05-01
		private String date;
		//价格
		private BigDecimal price;

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public BigDecimal getPrice() {
			return price;
		}

		public void setPrice(BigDecimal price) {
			this.price = price;
		}
	}

	public static class YcfRoom implements Serializable {

		private static final long serialVersionUID = -5513735984868202776L;
		//房型编号
		private String roomId;
		//开始使用日期 2016-05-01
		private String checkInDate;
		//结束使用日期 2016-05-02
		private String checkOutDate;

		public String getRoomId() {
			return roomId;
		}

		public void setRoomId(String roomId) {
			this.roomId = roomId;
		}

		public String getCheckInDate() {
			return checkInDate;
		}

		public void setCheckInDate(String checkInDate) {
			this.checkInDate = checkInDate;
		}

		public String getCheckOutDate() {
			return checkOutDate;
		}

		public void setCheckOutDate(String checkOutDate) {
			this.checkOutDate = checkOutDate;
		}

	}

	public static class YcfTicket implements Serializable {

		private static final long serialVersionUID = 6131758436332816170L;
		//门票编号
		private String ticketId;
		//开始使用日期 2016-05-02
		private String checkInDate;

		public String getTicketId() {
			return ticketId;
		}

		public void setTicketId(String ticketId) {
			this.ticketId = ticketId;
		}

		public String getCheckInDate() {
			return checkInDate;
		}

		public void setCheckInDate(String checkInDate) {
			this.checkInDate = checkInDate;
		}
	}

	public static class YcfFood implements Serializable {

		private static final long serialVersionUID = 3082112978628878183L;
		//餐饮编号
		private String foodId;
		//开始使用日期 2016-05-02
		private String checkInDate;

		public String getFoodId() {
			return foodId;
		}

		public void setFoodId(String foodId) {
			this.foodId = foodId;
		}

		public String getCheckInDate() {
			return checkInDate;
		}

		public void setCheckInDate(String checkInDate) {
			this.checkInDate = checkInDate;
		}
	}

}
