package com.plateno.booking.internal.goods.vo;

import java.util.List;
import java.util.Map;

public class OrderCheckDetail {

    private List<OrderCheckInfo> orderCheckInfo;
    
    private PointDeductValue pointDeductValue;
    
    /**
     * 总价
     */
    private Integer totalPrice;
    
    /**
     * 总快递费
     */
    private Integer totalExpressFee;
    
    /**
     * 商品的总成本
     */
    private Integer totalProductCost;
    
    /**
     * 快递的总成本
     */
    private Integer totalExpressCost;

    

    public Integer getTotalProductCost() {
        return totalProductCost;
    }

    public void setTotalProductCost(Integer totalProductCost) {
        this.totalProductCost = totalProductCost;
    }

    public Integer getTotalExpressCost() {
        return totalExpressCost;
    }

    public void setTotalExpressCost(Integer totalExpressCost) {
        this.totalExpressCost = totalExpressCost;
    }

    private Map<Long, OrderCheckInfo> orderCheckInfoMap;


    public Map<Long, OrderCheckInfo> getOrderCheckInfoMap() {
        return orderCheckInfoMap;
    }

    public void setOrderCheckInfoMap(Map<Long, OrderCheckInfo> orderCheckInfoMap) {
        this.orderCheckInfoMap = orderCheckInfoMap;
    }

    public List<OrderCheckInfo> getOrderCheckInfo() {
        return orderCheckInfo;
    }

    public void setOrderCheckInfo(List<OrderCheckInfo> orderCheckInfo) {
        this.orderCheckInfo = orderCheckInfo;
    }

    public PointDeductValue getPointDeductValue() {
        return pointDeductValue;
    }

    public void setPointDeductValue(PointDeductValue pointDeductValue) {
        this.pointDeductValue = pointDeductValue;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalExpressFee() {
        return totalExpressFee;
    }

    public void setTotalExpressFee(Integer totalExpressFee) {
        this.totalExpressFee = totalExpressFee;
    }
    

    
}
