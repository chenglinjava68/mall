package com.plateno.booking.internal.wechat.model;

import java.util.List;

public class SkuBean implements java.io.Serializable {
	
	private static final long serialVersionUID = -7493176610002470916L;
	
	private Integer skuId;
	
	private List<SkuProperties> skuProperties;

	public static class SkuProperties{
		
		private Integer pId;
		private String proName;
		private List<ValueList> valueList;
		public Integer getpId() {
			return pId;
		}
		public void setpId(Integer pId) {
			this.pId = pId;
		}
		public String getProName() {
			return proName;
		}
		public void setProName(String proName) {
			this.proName = proName;
		}
		public List<ValueList> getValueList() {
			return valueList;
		}
		public void setValueList(List<ValueList> valueList) {
			this.valueList = valueList;
		}
	}

	public static class ValueList{
		
		private Integer vId;
		private String proValue;
		private Integer stock;
		public Integer getvId() {
			return vId;
		}
		public void setvId(Integer vId) {
			this.vId = vId;
		}
		public String getProValue() {
			return proValue;
		}
		public void setProValue(String proValue) {
			this.proValue = proValue;
		}
		public Integer getStock() {
			return stock;
		}
		public void setStock(Integer stock) {
			this.stock = stock;
		}
		
		
	}

		public Integer getSkuId() {
			return skuId;
		}
	
		public void setSkuId(Integer skuId) {
			this.skuId = skuId;
		}

		public List<SkuProperties> getSkuProperties() {
			return skuProperties;
		}

		public void setSkuProperties(List<SkuProperties> skuProperties) {
			this.skuProperties = skuProperties;
		}
	
}
