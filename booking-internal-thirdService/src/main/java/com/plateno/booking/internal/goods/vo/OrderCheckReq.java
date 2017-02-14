package com.plateno.booking.internal.goods.vo;

import java.util.List;


public class OrderCheckReq {
    
    private Integer memberPoints;
    
    private List<OrderCheckParamInfo> orderCheckParamInfos;

    public Integer getMemberPoints() {
        return memberPoints;
    }

    public void setMemberPoints(Integer memberPoints) {
        this.memberPoints = memberPoints;
    }

    public List<OrderCheckParamInfo> getOrderCheckParamInfos() {
        return orderCheckParamInfos;
    }

    public void setOrderCheckParamInfos(List<OrderCheckParamInfo> orderCheckParamInfos) {
        this.orderCheckParamInfos = orderCheckParamInfos;
    }

    @Override
    public String toString() {
        return "OrderCheckReq [memberPoints=" + memberPoints + ", orderCheckParamInfos="
                + orderCheckParamInfos + "]";
    }


    
    
    
    
}
