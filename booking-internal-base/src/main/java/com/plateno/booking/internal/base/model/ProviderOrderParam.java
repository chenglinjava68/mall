package com.plateno.booking.internal.base.model;

import java.util.ArrayList;
import java.util.List;

public class ProviderOrderParam extends BaseParam{
    /** 
    * @Fields serialVersionUID : TODO
    */ 
    private static final long serialVersionUID = 1L;
    private Integer payStatus;//订单状态
    private String mobile; //下单人手机号码
    private String orderSubNo; // 子订单编码
    private Integer  resource;//下单来源
    private Long bookingStartDate; //下单开始日期
    private Long bookingEndDate; //下单结束日期
    private Integer pageNo;
    private Integer pageNumber;
    /**
     * 显示状态
     * 100待付款、200已取消、300待发货、400待收货、500已完成、600退款审核中、700已退款、800退款审核不通过
     */
    private Integer viewStatus;
    
    /**
     * 多个状态
     */
    private List<Integer> statusList = new ArrayList<>();
    
    private boolean showLimit = true;
    
    
    
    
    public boolean isShowLimit() {
        return showLimit;
    }
    public void setShowLimit(boolean showLimit) {
        this.showLimit = showLimit;
    }
    public List<Integer> getStatusList() {
        return statusList;
    }
    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }
    public Integer getViewStatus() {
        return viewStatus;
    }
    public void setViewStatus(Integer viewStatus) {
        this.viewStatus = viewStatus;
    }
    public Integer getPayStatus() {
        return payStatus;
    }
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getOrderSubNo() {
        return orderSubNo;
    }
    public void setOrderSubNo(String orderSubNo) {
        this.orderSubNo = orderSubNo;
    }
    public Integer getResource() {
        return resource;
    }
    public void setResource(Integer resource) {
        this.resource = resource;
    }
    public Long getBookingStartDate() {
        return bookingStartDate;
    }
    public void setBookingStartDate(Long bookingStartDate) {
        this.bookingStartDate = bookingStartDate;
    }
    public Long getBookingEndDate() {
        return bookingEndDate;
    }
    public void setBookingEndDate(Long bookingEndDate) {
        this.bookingEndDate = bookingEndDate;
    }
    public Integer getPageNo() {
        return pageNo;
    }
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
    public Integer getPageNumber() {
        return pageNumber;
    }
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
    
    
    
}
