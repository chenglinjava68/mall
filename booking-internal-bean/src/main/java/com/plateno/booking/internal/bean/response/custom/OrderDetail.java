package com.plateno.booking.internal.bean.response.custom;

import java.util.List;

public class OrderDetail implements java.io.Serializable{


	private static final long serialVersionUID = 8205353469129513676L;
	
	private OrderInfo orderInfo;
	
	private DeliverDetail deliverDetail;
	
	private ConsigneeInfo consigneeInfo;
	
	private List<ProductInfo> productInfo;
	
	
	public OrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}

	public DeliverDetail getDeliverDetail() {
		return deliverDetail;
	}

	public void setDeliverDetail(DeliverDetail deliverDetail) {
		this.deliverDetail = deliverDetail;
	}

	public ConsigneeInfo getConsigneeInfo() {
		return consigneeInfo;
	}

	public void setConsigneeInfo(ConsigneeInfo consigneeInfo) {
		this.consigneeInfo = consigneeInfo;
	}

	

	public List<ProductInfo> getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(List<ProductInfo> productInfo) {
		this.productInfo = productInfo;
	}



	public static class OrderInfo implements java.io.Serializable{
		
		private static final long serialVersionUID = 9208163618659933890L;
		private String orderNo;
		private Integer payAmount;//支付金额
		private Integer fee;
		private Integer orderAmount;//实付金额
		private Integer point;//抵扣积分
		private Long createDate;
		private Long payTime;//支付时间
		private Integer payType;
		private Integer payStatus;
		private String orderDetailRemark;
		private Long refundTime;//退款申请时间
		private Long refundSuccessTime;//退款成功时间
		private String failReason;//退款失败原因
		private String name;
		private String mobile;
		
		/**
		 * 退款金额
		 */
		private Integer refundAmount;
		
		/**
		 * 退款原因
		 */
		private String refundReason;
		
		
		public Long getPayTime() {
			return payTime;
		}
		public void setPayTime(Long payTime) {
			this.payTime = payTime;
		}
		public Integer getPayStatus() {
			return payStatus;
		}
		public void setPayStatus(Integer payStatus) {
			this.payStatus = payStatus;
		}

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
		public String getOrderDetailRemark() {
			return orderDetailRemark;
		}
		public void setOrderDetailRemark(String orderDetailRemark) {
			this.orderDetailRemark = orderDetailRemark;
		}
		public Long getRefundTime() {
			return refundTime;
		}
		public void setRefundTime(Long refundTime) {
			this.refundTime = refundTime;
		}
		public Long getRefundSuccessTime() {
			return refundSuccessTime;
		}
		public void setRefundSuccessTime(Long refundSuccessTime) {
			this.refundSuccessTime = refundSuccessTime;
		}
		public String getFailReason() {
			return failReason;
		}
		public void setFailReason(String failReason) {
			this.failReason = failReason;
		}
		public String getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}
		public Integer getPayAmount() {
			return payAmount;
		}
		public void setPayAmount(Integer payAmount) {
			this.payAmount = payAmount;
		}
		public Integer getFee() {
			return fee;
		}
		public void setFee(Integer fee) {
			this.fee = fee;
		}
		public Integer getOrderAmount() {
			return orderAmount;
		}
		public void setOrderAmount(Integer orderAmount) {
			this.orderAmount = orderAmount;
		}
		public Integer getPoint() {
			return point;
		}
		public void setPoint(Integer point) {
			this.point = point;
		}
		public Integer getPayType() {
			return payType;
		}
		public void setPayType(Integer payType) {
			this.payType = payType;
		}
		public Long getCreateDate() {
			return createDate;
		}
		public void setCreateDate(Long createDate) {
			this.createDate = createDate;
		}
		public Integer getRefundAmount() {
			return refundAmount;
		}
		public void setRefundAmount(Integer refundAmount) {
			this.refundAmount = refundAmount;
		}
		public String getRefundReason() {
			return refundReason;
		}
		public void setRefundReason(String refundReason) {
			this.refundReason = refundReason;
		}
		
	}
	
	public static class DeliverDetail implements java.io.Serializable{
		

		private static final long serialVersionUID = 5820752242731297932L;

		private String deliverNo;//发货单号
		
		private Long deliverDate;//发货日期
		
		private Integer logisticsType;//物流类型

		public String getDeliverNo() {
			return deliverNo;
		}

		public void setDeliverNo(String deliverNo) {
			this.deliverNo = deliverNo;
		}
		

		public Long getDeliverDate() {
			return deliverDate;
		}

		public void setDeliverDate(Long deliverDate) {
			this.deliverDate = deliverDate;
		}

		public Integer getLogisticsType() {
			return logisticsType;
		}

		public void setLogisticsType(Integer logisticsType) {
			this.logisticsType = logisticsType;
		}
		
	}
	
	public static class ConsigneeInfo implements java.io.Serializable{
		
	
		private static final long serialVersionUID = 6954366982498942917L;

		private String consigneeName;
		
		private String mobile;
		
		private String consigneeAddress;
		
		private String newAddress;
		
		/**
		 * 修改后的收货人姓名
		 */
		private String newName;
		/**
		 * 修改后的收货人手机
		 */
		private String newMobile;

		public String getConsigneeName() {
			return consigneeName;
		}

		public void setConsigneeName(String consigneeName) {
			this.consigneeName = consigneeName;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getConsigneeAddress() {
			return consigneeAddress;
		}

		public void setConsigneeAddress(String consigneeAddress) {
			this.consigneeAddress = consigneeAddress;
		}

		public String getNewAddress() {
			return newAddress;
		}

		public void setNewAddress(String newAddress) {
			this.newAddress = newAddress;
		}

		public String getNewName() {
			return newName;
		}

		public void setNewName(String newName) {
			this.newName = newName;
		}

		public String getNewMobile() {
			return newMobile;
		}

		public void setNewMobile(String newMobile) {
			this.newMobile = newMobile;
		}
		
	}
	
	public static class ProductInfo implements java.io.Serializable{
		
		private static final long serialVersionUID = 7221564211594276296L;
		
		/**
		 * 商品ID
		 */
		private Integer productId;

		private String productName;
		
		private Integer price;
		
		private Integer count;
		
		private String productPropertis;
		
		private String disImages;
		
		private Integer point;
		
		private Integer sellStrategy;//销售策略 1:金额(积分不足会员） 2：积分+金额 （够积分的会员使用）
		
		

		public String getDisImages() {
			return disImages;
		}

		public void setDisImages(String disImages) {
			this.disImages = disImages;
		}

		public Integer getPoint() {
			return point;
		}

		public Integer getProductId() {
			return productId;
		}

		public void setProductId(Integer productId) {
			this.productId = productId;
		}

		public void setPoint(Integer point) {
			this.point = point;
		}

		public Integer getSellStrategy() {
			return sellStrategy;
		}

		public void setSellStrategy(Integer sellStrategy) {
			this.sellStrategy = sellStrategy;
		}

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public Integer getPrice() {
			return price;
		}

		public void setPrice(Integer price) {
			this.price = price;
		}

		public Integer getCount() {
			return count;
		}

		public void setCount(Integer count) {
			this.count = count;
		}

		public String getProductPropertis() {
			return productPropertis;
		}

		public void setProductPropertis(String productPropertis) {
			this.productPropertis = productPropertis;
		}

	}
}
