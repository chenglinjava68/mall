package com.plateno.booking.internal.dao.pojo;

import java.util.Date;
import java.util.List;

import com.plateno.booking.internal.bean.response.custom.OrderDetail.ConsigneeInfo;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.DeliverDetail;
import com.plateno.booking.internal.bean.response.logistics.PackageProduct;

public class ProviderOrderDetail extends ProviderOrder{


    
    /**
     * 支付时间
     */
    private Date payTime;
    

    
    /**
     * 发货信息
     */
    private List<DeliverDetail> deliverDetails;

    private List<PackageProduct> packageProducts;
    
    /**
     * 收货人信息
     */
    private ConsigneeInfo consigneeInfo;
    
    
    
    
    public ConsigneeInfo getConsigneeInfo() {
        return consigneeInfo;
    }

    public void setConsigneeInfo(ConsigneeInfo consigneeInfo) {
        this.consigneeInfo = consigneeInfo;
    }

    public List<PackageProduct> getPackageProducts() {
        return packageProducts;
    }

    public void setPackageProducts(List<PackageProduct> packageProducts) {
        this.packageProducts = packageProducts;
    }



    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }



    public List<DeliverDetail> getDeliverDetails() {
        return deliverDetails;
    }

    public void setDeliverDetails(List<DeliverDetail> deliverDetails) {
        this.deliverDetails = deliverDetails;
    }

    
}
