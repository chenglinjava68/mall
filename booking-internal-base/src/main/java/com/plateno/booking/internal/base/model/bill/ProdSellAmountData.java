package com.plateno.booking.internal.base.model.bill;

public class ProdSellAmountData implements java.io.Serializable{
	
	
	private static final long serialVersionUID = 7077817161661816310L;

	private String productId;
	
	private Integer sellCount;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getSellCount() {
		return sellCount;
	}

	public void setSellCount(Integer sellCount) {
		this.sellCount = sellCount;
	}

	
}
