package com.plateno.booking.internal.bean.request.custom;

import java.util.List;

public class DeliverGoodParam {
    private String logisticsNo; // 物流编号
    
    private Integer logisticsType; // 物流类型(1 圆通、2申通、3韵达、4百事通、5顺丰、6 EMS)
    
    private List<Integer> orderProductIds;

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public Integer getLogisticsType() {
        return logisticsType;
    }

    public void setLogisticsType(Integer logisticsType) {
        this.logisticsType = logisticsType;
    }

    public List<Integer> getOrderProductIds() {
        return orderProductIds;
    }

    public void setOrderProductIds(List<Integer> orderProductIds) {
        this.orderProductIds = orderProductIds;
    }
    
    
    
    
}
