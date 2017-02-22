package com.plateno.booking.internal.dao.pojo;

import java.util.Date;

import com.plateno.booking.internal.bean.response.custom.OrderDetail.ConsigneeInfo;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.DeliverDetail;

public class ProviderOrderDetail extends ProviderOrder{

    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 支付时间
     */
    private Date payTime;
    
    /**
     * 收货人信息
     */
    private ConsigneeInfo consigneeInfo;
    
    /**
     * 发货信息
     */
    private DeliverDetail deliverDetail;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public ConsigneeInfo getConsigneeInfo() {
        return consigneeInfo;
    }

    public void setConsigneeInfo(ConsigneeInfo consigneeInfo) {
        this.consigneeInfo = consigneeInfo;
    }

    public DeliverDetail getDeliverDetail() {
        return deliverDetail;
    }

    public void setDeliverDetail(DeliverDetail deliverDetail) {
        this.deliverDetail = deliverDetail;
    }
    
    
    
}
