package com.plateno.booking.internal.bean.response.custom;

import java.util.List;

import com.plateno.booking.internal.bean.response.custom.OrderDetail.ProductInfo;

public class SelectOrderResponse implements java.io.Serializable{

	private static final long serialVersionUID = -8927131530279226143L;
	
	private String orderNo;
	
	private Long memberId;
	
	private Integer payStatus;
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
	 * 逻辑删除， 1-正常，2-删除
	 */
	private Integer logicDel;
	
	
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
    
    /**
	 * 订单子来源
	 */
	private Integer subResource; 
	
	/**
<<<<<<< HEAD
	 * 储值金额
	 */
	private Integer currencyDepositAmount;
	
	private List<ProductInfo> productInfos;
	
    /**
     * 优惠券抵扣金额
     */
    private Integer couponAmount;
    /**
     * 积分抵扣金额
     */
    private Integer pointMoney;
	
    /**
     * 总快递费
     */
    private Integer totalExpressAmount;
    /**
     * 仓库id
     */
    private Integer chanelid;
    
    

	

	

	

	
    
    
    
	public Integer getChanelid() {
        return chanelid;
    }

    public void setChanelid(Integer chanelid) {
        this.chanelid = chanelid;
    }

    public Integer getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(Integer couponAmount) {
        this.couponAmount = couponAmount;
    }

    public Integer getPointMoney() {
        return pointMoney;
    }

    public void setPointMoney(Integer pointMoney) {
        this.pointMoney = pointMoney;
    }

    public Integer getTotalExpressAmount() {
        return totalExpressAmount;
    }

    public void setTotalExpressAmount(Integer totalExpressAmount) {
        this.totalExpressAmount = totalExpressAmount;
    }

    public Integer getCurrencyDepositAmount() {
        return currencyDepositAmount;
    }

    public void setCurrencyDepositAmount(Integer currencyDepositAmount) {
        this.currencyDepositAmount = currencyDepositAmount;
    }

    public List<ProductInfo> getProductInfos() {
        return productInfos;
    }

    public void setProductInfos(List<ProductInfo> productInfos) {
        this.productInfos = productInfos;
    }


    public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public Integer getSubResource() {
		return subResource;
	}

	public void setSubResource(Integer subResource) {
		this.subResource = subResource;
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


	public Integer getLogicDel() {
		return logicDel;
	}

	public void setLogicDel(Integer logicDel) {
		this.logicDel = logicDel;
	}

}
