package com.plateno.booking.internal.goods.vo;

import java.util.List;

public class SkuInfo {
    private String goodsId;//skuId
    private String title;//标题
    private String imgPath;//图片路径
    private Integer price;//正常价格
    private Integer status;//状态
    private Integer stock;//库存
    private Integer priceStrategy;//价格策略
    private String priceName;//价格策略名称
    private List<SkuProperty> skuProperties;
    private Integer quantity;//购买数量
    public String getGoodsId() {
        return goodsId;
    }
    public void setGoodsId(String goodsId) {
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
