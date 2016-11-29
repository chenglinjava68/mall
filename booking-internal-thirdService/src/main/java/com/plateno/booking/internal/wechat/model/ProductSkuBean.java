package com.plateno.booking.internal.wechat.model;

import java.util.Date;
import java.util.List;


public class ProductSkuBean implements java.io.Serializable {
	
	private static final long serialVersionUID = -3581020681450054101L;

	private List<SkuPropertyInfos> skuPropertyInfos;
	
	private Integer productId;
	
	private String title;
	
	private String category;
	
	private String properties;
	
	private Integer expressFee;
	
	private Integer marketPrice;
	
	private Integer regularPrice;
	
	private Integer favorPrice;
	
	private Integer favorPoints;
	
	private String imgPath;
	
	private Integer sellStrategy; //1 非积分价格 2 积分+价格

	private Integer stock; //库存为0 禁止下单 
	
	private Integer status; //1 上架 2下架 状态为2，禁止下单
	
	/**
	 * 价格策略	1 默认价格 2 促销价
	 */
	private Integer priceStrategy;
	/**
	 * 价格策略名称
	 */
	private String priceName;
	
	/**
	 * 渠道号
	 */
	private Integer channelId; 
	
	/**
	 * 积分抵扣金额
	 */
	private Integer deductPrice;
	
	/**
	 * 限购数量（null不限购）
	 */
	private Integer maxSaleQty;
	
	/**
	 * 促销价
	 */
	private Integer promotPrice;

	
	public Integer getPromotPrice() {
		return promotPrice;
	}

	public void setPromotPrice(Integer promotPrice) {
		this.promotPrice = promotPrice;
	}

	public List<SkuPropertyInfos> getSkuPropertyInfos() {
		return skuPropertyInfos;
	}

	public void setSkuPropertyInfos(List<SkuPropertyInfos> skuPropertyInfos) {
		this.skuPropertyInfos = skuPropertyInfos;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Integer getPriceStrategy() {
		return priceStrategy;
	}

	public void setPriceStrategy(Integer priceStrategy) {
		this.priceStrategy = priceStrategy;
	}

	public String getPriceName() {
		return priceName;
	}

	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}

	public static class  SkuPropertyInfos implements java.io.Serializable{
		private static final long serialVersionUID = -3241250582252552167L;
		private String proName;
		private String proValue;
		public String getProName() {
			return proName;
		}
		public void setProName(String proName) {
			this.proName = proName;
		}
		public String getProValue() {
			return proValue;
		}
		public void setProValue(String proValue) {
			this.proValue = proValue;
		}
		@Override
		public String toString() {
			return "SkuPropertyInfos [proName=" + proName + ", proValue="
					+ proValue + "]";
		}
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public Integer getExpressFee() {
		return expressFee;
	}

	public void setExpressFee(Integer expressFee) {
		this.expressFee = expressFee;
	}

	public Integer getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Integer marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Integer getRegularPrice() {
		return regularPrice;
	}

	public void setRegularPrice(Integer regularPrice) {
		this.regularPrice = regularPrice;
	}

	public Integer getFavorPrice() {
		return favorPrice;
	}

	public void setFavorPrice(Integer favorPrice) {
		this.favorPrice = favorPrice;
	}

	public Integer getFavorPoints() {
		return favorPoints;
	}

	public void setFavorPoints(Integer favorPoints) {
		this.favorPoints = favorPoints;
	}

	public Integer getSellStrategy() {
		return sellStrategy;
	}

	public void setSellStrategy(Integer sellStrategy) {
		this.sellStrategy = sellStrategy;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getDeductPrice() {
		return deductPrice;
	}

	public void setDeductPrice(Integer deductPrice) {
		this.deductPrice = deductPrice;
	}

	public Integer getMaxSaleQty() {
		return maxSaleQty;
	}

	public void setMaxSaleQty(Integer maxSaleQty) {
		this.maxSaleQty = maxSaleQty;
	}

	@Override
	public String toString() {
		return "ProductSkuBean [skuPropertyInfos=" + skuPropertyInfos
				+ ", productId=" + productId + ", title=" + title
				+ ", category=" + category + ", properties=" + properties
				+ ", expressFee=" + expressFee + ", marketPrice=" + marketPrice
				+ ", regularPrice=" + regularPrice + ", favorPrice="
				+ favorPrice + ", favorPoints=" + favorPoints + ", imgPath="
				+ imgPath + ", sellStrategy=" + sellStrategy + ", stock="
				+ stock + ", status=" + status + ", priceStrategy="
				+ priceStrategy + ", priceName=" + priceName + ", channelId="
				+ channelId + ", deductPrice=" + deductPrice + ", maxSaleQty="
				+ maxSaleQty + "]";
	}
}
