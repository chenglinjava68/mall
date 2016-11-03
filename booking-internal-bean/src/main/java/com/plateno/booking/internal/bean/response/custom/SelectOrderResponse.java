package com.plateno.booking.internal.bean.response.custom;

public class SelectOrderResponse implements java.io.Serializable{

	private static final long serialVersionUID = -8927131530279226143L;
	
	private String orderNo;
	
	private Long memberId;
	
	private Integer payStatus;
	
	private String goodsName;
	
	private String disImage;
	
	private String goodsProperties;
	
	private Integer quatity;
	
	private Integer amount;
	
	private Integer point;
	
	private Long createTime;
	
	private Integer payType;
	
	private String bookingName;
	
	private String mobile;
	
	private Integer resource;
	
	/**
	 * 实际支付的金额
	 */
	private int payMoney;
	
	/**
	 * 退款金额（如果已经生成退款金额，就是实际退款的金额，否则是可以退款的金额）
	 */
	private int refundAmount;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsProperties() {
		return goodsProperties;
	}

	public void setGoodsProperties(String goodsProperties) {
		this.goodsProperties = goodsProperties;
	}

	public Integer getQuatity() {
		return quatity;
	}

	public void setQuatity(Integer quatity) {
		this.quatity = quatity;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getBookingName() {
		return bookingName;
	}

	public void setBookingName(String bookingName) {
		this.bookingName = bookingName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getResource() {
		return resource;
	}

	public void setResource(Integer resource) {
		this.resource = resource;
	}

	public String getDisImage() {
		return disImage;
	}

	public void setDisImage(String disImage) {
		this.disImage = disImage;
	}

	public int getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(int payMoney) {
		this.payMoney = payMoney;
	}

	public int getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(int refundAmount) {
		this.refundAmount = refundAmount;
	}
}
