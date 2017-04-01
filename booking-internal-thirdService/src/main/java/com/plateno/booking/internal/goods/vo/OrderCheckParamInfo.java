package com.plateno.booking.internal.goods.vo;

public class OrderCheckParamInfo {
    
    /**
     * 购买的skuId
     */
    private Long goodsId;
    
    /**
     * 购买数量
     */
    private Integer quantity;

    

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
    
    
}
 