package com.plateno.booking.internal.goods.vo;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class ProductSkuBean implements java.io.Serializable {
	
	private static final long serialVersionUID = -3581020681450054101L;

	private List<SkuPropertyInfos> skuPropertyInfos;
	
	private Integer productId;
	
	private String title;
	
	private String category;
	
	private String properties;
	
	private Integer expressFee;
	
	/**
	 * 商品价格
	 */
	private int price;
	
	
	private String imgPath;
	

	private Integer stock; //库存为0 禁止下单 
	
	private Integer status; //1 上架 2下架 状态为2，禁止下单
	
	
	/**
	 * 渠道号
	 */
	private Integer channelId; 
	
	
	/**
	 * 限购数量（null不限购）
	 */
	private Integer maxSaleQty;
	
	
	/**
	 * 成本价
	 */
	private Integer costPrice;
	
	/**
	 * 成本快递费
	 */
	private Integer costExpress;
	
	/**
	 * 类目ID
	 */
	private Integer categoryId;
	
	/**
	 * 供应商ID
	 */
	private Integer providerId;

	
	

	public List<SkuPropertyInfos> getSkuPropertyInfos() {
        return skuPropertyInfos;
    }

    public void setSkuPropertyInfos(List<SkuPropertyInfos> skuPropertyInfos) {
        this.skuPropertyInfos = skuPropertyInfos;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Integer costPrice) {
        this.costPrice = costPrice;
    }

    public Integer getCostExpress() {
        return costExpress;
    }

    public void setCostExpress(Integer costExpress) {
        this.costExpress = costExpress;
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



	public Integer getMaxSaleQty() {
		return maxSaleQty;
	}

	public void setMaxSaleQty(Integer maxSaleQty) {
		this.maxSaleQty = maxSaleQty;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	@Override 
    public String toString() { 
            return ReflectionToStringBuilder.toString(this,ToStringStyle.SHORT_PREFIX_STYLE); 
    }
	
}
