package com.plateno.booking.internal.bean.request.custom;

import javax.validation.constraints.NotNull;

public class MOrderGoodsParam {

    @NotNull(message = "商品ID,不能为空")
    private Long goodsId;
    
    @NotNull(message = "选购数量,不能为空")
    private Integer quantity;
    
    private String skuProperties;//商品属性,按:分开;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSkuProperties() {
        return skuProperties;
    }

    public void setSkuProperties(String skuProperties) {
        this.skuProperties = skuProperties;
    }
    
    
    
    
}
