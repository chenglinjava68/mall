package com.plateno.booking.internal.bean.request.tc.order;

import java.util.Date;
import java.util.List;

/**
 * 同程分销商下单接口返回body
 * @author lingjw
 * @version 创建时间：2016年5月30日 上午11:58:38 
 * 说明:
 */
public class OrderBody {

	/**
	 * 购买数量
	 */
	private List<Integer> ticketsNum;
	/**
	 * 预定人
	 */
	private List<String> bookMan;
	/**
	 * 预定人手机类型(1：大陆 0：非大陆)
	 */
	private Integer bookMobileType;
	/**
	 * 预定人手机号码
	 */
	private List<String> bookMobile;
	/**
	 * 预定人地址
	 */
	private String bookAddress;
	/**
	 * 预定人邮编
	 */
	private String bookPostCode;
	/**
	 * 预定人邮箱
	 */
	private String bookEmail;
	/**
	 * 取票人姓名
	 */
	private List<String> travelerName;
	/**
	 * 取票人手机号码
	 */
	private List<String> travelerMobile;
	/**
	 * 取票人手机类型(1：大陆 0：非大陆
	 */
	private Integer travelerMobileType;
	/**
	 * 产品ID
	 */
	private Integer priceID;
	/**
	 * 旅游日期
	 */
	private Date travelDate;
	/**
	 * 实名制内容
	 */
	private String remark;
	/**
	 * 预定身份证号
	 */
	private String travelerIdCardNo;
	/**
	 * 取票人身份证号码 
	 */
	private List<String> travelerIdCardNoList;
	/**
	 * 第三方流水号
	 */
	private List<String> thirdSerialId;
	/**
	 * 场次ID
	 */
	private String screeningId;
	/**
	 * 场次开始时间
	 */
	private String beginTime;
	/**
	 * 场次结束时间
	 */
	private String endTime;
	public List<Integer> getTicketsNum() {
		return ticketsNum;
	}
	public void setTicketsNum(List<Integer> ticketsNum) {
		this.ticketsNum = ticketsNum;
	}
	public List<String> getBookMan() {
		return bookMan;
	}
	public void setBookMan(List<String> bookMan) {
		this.bookMan = bookMan;
	}
	public Integer getBookMobileType() {
		return bookMobileType;
	}
	public void setBookMobileType(Integer bookMobileType) {
		this.bookMobileType = bookMobileType;
	}
	public List<String> getBookMobile() {
		return bookMobile;
	}
	public void setBookMobile(List<String> bookMobile) {
		this.bookMobile = bookMobile;
	}
	public String getBookAddress() {
		return bookAddress;
	}
	public void setBookAddress(String bookAddress) {
		this.bookAddress = bookAddress;
	}
	public String getBookPostCode() {
		return bookPostCode;
	}
	public void setBookPostCode(String bookPostCode) {
		this.bookPostCode = bookPostCode;
	}
	public String getBookEmail() {
		return bookEmail;
	}
	public void setBookEmail(String bookEmail) {
		this.bookEmail = bookEmail;
	}
	public List<String> getTravelerName() {
		return travelerName;
	}
	public void setTravelerName(List<String> travelerName) {
		this.travelerName = travelerName;
	}
	public List<String> getTravelerMobile() {
		return travelerMobile;
	}
	public void setTravelerMobile(List<String> travelerMobile) {
		this.travelerMobile = travelerMobile;
	}
	public Integer getTravelerMobileType() {
		return travelerMobileType;
	}
	public void setTravelerMobileType(Integer travelerMobileType) {
		this.travelerMobileType = travelerMobileType;
	}
	public Integer getPriceID() {
		return priceID;
	}
	public void setPriceID(Integer priceID) {
		this.priceID = priceID;
	}
	public Date getTravelDate() {
		return travelDate;
	}
	public void setTravelDate(Date travelDate) {
		this.travelDate = travelDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTravelerIdCardNo() {
		return travelerIdCardNo;
	}
	public void setTravelerIdCardNo(String travelerIdCardNo) {
		this.travelerIdCardNo = travelerIdCardNo;
	}
	public List<String> getTravelerIdCardNoList() {
		return travelerIdCardNoList;
	}
	public void setTravelerIdCardNoList(List<String> travelerIdCardNoList) {
		this.travelerIdCardNoList = travelerIdCardNoList;
	}
	public List<String> getThirdSerialId() {
		return thirdSerialId;
	}
	public void setThirdSerialId(List<String> thirdSerialId) {
		this.thirdSerialId = thirdSerialId;
	}
	public String getScreeningId() {
		return screeningId;
	}
	public void setScreeningId(String screeningId) {
		this.screeningId = screeningId;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Override
	public String toString() {
		return "OrderBody [ticketsNum=" + ticketsNum + ", bookMan=" + bookMan + ", bookMobileType=" + bookMobileType
				+ ", bookMobile=" + bookMobile + ", bookAddress=" + bookAddress + ", bookPostCode=" + bookPostCode
				+ ", bookEmail=" + bookEmail + ", travelerName=" + travelerName + ", travelerMobile=" + travelerMobile
				+ ", travelerMobileType=" + travelerMobileType + ", priceID=" + priceID + ", travelDate=" + travelDate
				+ ", remark=" + remark + ", travelerIdCardNo=" + travelerIdCardNo + ", travelerIdCardNoList="
				+ travelerIdCardNoList + ", thirdSerialId=" + thirdSerialId + ", screeningId=" + screeningId
				+ ", beginTime=" + beginTime + ", endTime=" + endTime + "]";
	}
	
	
}
