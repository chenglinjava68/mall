package com.plateno.booking.internal.base.pojo;

import java.io.Serializable;
import java.util.Date;


public class OrderProduct implements Serializable {
    private Integer id;

    private String orderNo;

    private Integer skuid;

    private Integer skuCount;

    private Integer price;

    private Integer productId;

    private String productName;

    private String productProperty;

    private Date createTime;

    private Date upTime;

    private Integer point;

    private Integer sellStrategy;

    private String disImages;
    
    /**
     * 价格策略, 1-默认价格，2-促销价
     */
    private Integer priceStrategy;
    
    /**
     * 价格策略描述
     */
    private String priceStrategyDesc;
    
    /**
     * 积分抵扣价格
     */
    private Integer deductPrice;
    
    /**
     * 每件产品的成本价格
     */
    private Integer productCost;
    
    /**
     * 每件产品的发货费用
     */
    private Integer expressCost;
    
    /**
     * 退还库存的数量
     */
    private Integer returnSkuCount;

    private String orderSubNo;//子订单编号
    
    private int  channelId;//仓库id
    
    private int providedId;//供应商id
    
    private int coupouReduceAmount;//优惠券优惠金额
    
    private static final long serialVersionUID = 1L;

    public Integer getProductCost() {
		return productCost;
	}

	public void setProductCost(Integer productCost) {
		this.productCost = productCost;
	}

	public Integer getExpressCost() {
		return expressCost;
	}

	public void setExpressCost(Integer expressCost) {
		this.expressCost = expressCost;
	}

	public Integer getDeductPrice() {
		return deductPrice;
	}

	public void setDeductPrice(Integer deductPrice) {
		this.deductPrice = deductPrice;
	}

	public Integer getPriceStrategy() {
		return priceStrategy;
	}

	public void setPriceStrategy(Integer priceStrategy) {
		this.priceStrategy = priceStrategy;
	}

	public String getPriceStrategyDesc() {
		return priceStrategyDesc;
	}

	public void setPriceStrategyDesc(String priceStrategyDesc) {
		this.priceStrategyDesc = priceStrategyDesc;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getSkuid() {
        return skuid;
    }

    public void setSkuid(Integer skuid) {
        this.skuid = skuid;
    }

    public Integer getSkuCount() {
        return skuCount;
    }

    public void setSkuCount(Integer skuCount) {
        this.skuCount = skuCount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductProperty() {
        return productProperty;
    }

    public void setProductProperty(String productProperty) {
        this.productProperty = productProperty == null ? null : productProperty.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpTime() {
        return upTime;
    }

    public void setUpTime(Date upTime) {
        this.upTime = upTime;
    }

    public Integer getPoint() {
        return point;
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

    public String getDisImages() {
        return disImages;
    }

    public void setDisImages(String disImages) {
        this.disImages = disImages == null ? null : disImages.trim();
    }

	public Integer getReturnSkuCount() {
		return returnSkuCount;
	}

	public void setReturnSkuCount(Integer returnSkuCount) {
		this.returnSkuCount = returnSkuCount;
	}

    public String getOrderSubNo() {
        return orderSubNo;
    }

    public void setOrderSubNo(String orderSubNo) {
        this.orderSubNo = orderSubNo;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getProvidedId() {
        return providedId;
    }

    public void setProvidedId(int providedId) {
        this.providedId = providedId;
    }

    public int getCoupouReduceAmount() {
        return coupouReduceAmount;
    }

    public void setCoupouReduceAmount(int coupouReduceAmount) {
        this.coupouReduceAmount = coupouReduceAmount;
    }
	
	
}