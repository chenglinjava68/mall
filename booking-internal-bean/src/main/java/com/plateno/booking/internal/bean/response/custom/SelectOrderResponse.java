package com.plateno.booking.internal.bean.response.custom;

import java.util.Date;

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
	private Integer payMoney;
	
	/**
	 * 退款金额（如果已经生成退款金额，就是实际退款的金额，否则是可以退款的金额）
	 */
	private Integer refundAmount;
	
	/**
	 * 前端用户显示状态
	 * 100待付款、200已取消、300待发货、400待收货、500已完成、600退款审核中、700已退款、800退款审核不通过、900确认支付结果中
	 */
	private Integer viewStatus;
	
	/**
	 * 商品详情页面地址
	 */
	private String goodsUrl;
	
	/**
	 * 逻辑删除， 1-正常，2-删除
	 */
	private Integer logicDel;
	
	/**
	 * 发货单号
	 */
	private String deliverNo;
	
	/**
	 * deliverDate
	 */
	private Date deliverDate;
	
	/**
	 * logisticsType
	 */
	private Integer logisticsType;
	
	/**
	 * 物流类型描述
	 */
	private String logisticsTypeDesc;
	
	/**
	 * 收货人地址
	 */
    private String consigneeAddress;

    /**
     * 收货人姓名
     */
    private String consigneeName;

    /**
     * 收货人手机
     */
    private String consigneeMobile;
	
	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}

	public String getDeliverNo() {
		return deliverNo;
	}

	public void setDeliverNo(String deliverNo) {
		this.deliverNo = deliverNo;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public Integer getLogisticsType() {
		return logisticsType;
	}

	public void setLogisticsType(Integer logisticsType) {
		this.logisticsType = logisticsType;
	}

	public String getLogisticsTypeDesc() {
		return logisticsTypeDesc;
	}

	public void setLogisticsTypeDesc(String logisticsTypeDesc) {
		this.logisticsTypeDesc = logisticsTypeDesc;
	}

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

	public Integer getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Integer payMoney) {
		this.payMoney = payMoney;
	}

	public Integer getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Integer refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Integer getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(Integer viewStatus) {
		this.viewStatus = viewStatus;
	}

	public String getGoodsUrl() {
		return goodsUrl;
	}

	public void setGoodsUrl(String goodsUrl) {
		this.goodsUrl = goodsUrl;
	}

	public Integer getLogicDel() {
		return logicDel;
	}

	public void setLogicDel(Integer logicDel) {
		this.logicDel = logicDel;
	}

}
