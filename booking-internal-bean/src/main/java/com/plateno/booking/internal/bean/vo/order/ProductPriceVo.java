package com.plateno.booking.internal.bean.vo.order;

public class ProductPriceVo {

    /**
     * 商品总价格
     */
    private Integer totalProductPrice;
    
    /**
     * 快递成本价
     */
    private Integer totalExpressCost;
    
    /**
     * 商品成本价
     */
    private Integer totalProductCost;

    public Integer getTotalProductPrice() {
        return totalProductPrice;
    }

    public void setTotalProductPrice(Integer totalProductPrice) {
        this.totalProductPrice = totalProductPrice;
    }

    public Integer getTotalExpressCost() {
        return totalExpressCost;
    }

    public void setTotalExpressCost(Integer totalExpressCost) {
        this.totalExpressCost = totalExpressCost;
    }

    public Integer getTotalProductCost() {
        return totalProductCost;
    }

    public void setTotalProductCost(Integer totalProductCost) {
        this.totalProductCost = totalProductCost;
    }
    
    
    
}
