package com.plateno.booking.internal.bean.response.ycf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 要出发可预订检查返回参数
 * @author yi.wang
 * @date 2016年6月16日下午3:47:14
 * @version 1.0
 * @Description
 */
public class YcfCheckAvailResponse implements Serializable {

	private static final long serialVersionUID = -8927343507208909931L;

	//产品编号
	private String productID;

	//价格库存列表
	private List<YcfSaleInfos> saleInfos;

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public List<YcfSaleInfos> getSaleInfos() {
		return saleInfos;
	}

	public void setSaleInfos(List<YcfSaleInfos> saleInfos) {
		this.saleInfos = saleInfos;
	}

	public static class YcfSaleInfos implements Serializable {

		private static final long serialVersionUID = -3215548138234446810L;

		//价格与库存日期
		private String date;

		//价格  两位小数，30.00（单位：元）
		private BigDecimal price;

		//价格类型 0：售价模式 1：底价模式
		private Integer priceType;

		//库存列表
		private List<YcfStockItem> stockList;

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

		public Integer getPriceType() {
			return priceType;
		}

		public void setPriceType(Integer priceType) {
			this.priceType = priceType;
		}

		public List<YcfStockItem> getStockList() {
			return stockList;
		}

		public void setStockList(List<YcfStockItem> stockList) {
			this.stockList = stockList;
		}
	}

	public static class YcfStockItem implements Serializable {

		private static final long serialVersionUID = -3559865510559458900L;

		//元素编号
		private String itemId;

		//库存量
		private Integer stock;

		public String getItemId() {
			return itemId;
		}

		public void setItemId(String itemId) {
			this.itemId = itemId;
		}

		public Integer getStock() {
			return stock;
		}

		public void setStock(Integer stock) {
			this.stock = stock;
		}
	}
}
