package com.plateno.booking.internal.goods.vo;

public class SkuStock implements java.io.Serializable{

	private static final long serialVersionUID = 9202901493608701392L;
	
	private Integer skuId;
	
	private Integer regularPrice;
	
	private Integer favorPrice;
	
	private Integer favorPoints;
	
	private Integer sellStrategy;
	
	private Integer stock;

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
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
	
	

}
