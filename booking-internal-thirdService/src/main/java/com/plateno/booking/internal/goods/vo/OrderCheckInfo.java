package com.plateno.booking.internal.goods.vo;

import java.util.List;

public class OrderCheckInfo {
    
    /**
     * skuId
     */
    private Long goodsId;
    private String title;
    private String imgPath;
    private Integer price;
    private Integer status;
    private Integer stock;
    private Integer priceStrategy;
    private String priceName;
    private Integer maxSaleQty;
    private Integer categoryId;
    private Integer channelId;
    private String channelName;
    private Integer providerId;
    private String providerName;
    private Integer quantity;
    private Integer costPrice;
    private Integer costExpress;
    /**
     * 商品id
     */
    private Long spuId;
    
    private List<SkuProperty> skuProperties;
    
    
    
 
    public Long getSpuId() {
        return spuId;
    }
    public void setSpuId(Long spuId) {
        this.spuId = spuId;
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
    public Long getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getImgPath() {
        return imgPath;
    }
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
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
    public Integer getChannelId() {
        return channelId;
    }
    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }
    public String getChannelName() {
        return channelName;
    }
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
    public Integer getProviderId() {
        return providerId;
    }
    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }
    public String getProviderName() {
        return providerName;
    }
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public List<SkuProperty> getSkuProperties() {
        return skuProperties;
    }
    public void setSkuProperties(List<SkuProperty> skuProperties) {
        this.skuProperties = skuProperties;
    }
    
    
    
    
}
