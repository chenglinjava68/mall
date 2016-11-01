package com.plateno.booking.internal.bean.vo.hotel.convert;

import java.io.Serializable;
import java.util.List;

import com.plateno.booking.internal.bean.vo.hotel.base.HotelBaseParam;
import com.plateno.booking.internal.bean.vo.hotel.request.inner.CouponParam;
import com.plateno.booking.internal.bean.vo.hotel.request.inner.OrderGuestParam;
import com.plateno.booking.internal.bean.vo.hotel.request.inner.RoomRateParam;

public class AddHotelBookConvert extends HotelBaseParam implements Serializable {

	private static final long serialVersionUID = -476564803254904600L;

	private String innId; // 酒店编码
	private String roomTypeId; // 房间类型
	
	private String hotelName;//酒店名称
	
	private int roomCount; // 房间数量
	private int sourceTypeId; // 客人登记来源: 39中介，41客服中心，70网站, 94手机短信，107手机WAP，110分店大堂上网机， 191语音订房， 193桌面预订终端
	private String dtArrorig;// 计划抵店时间
	private String dtDeporig; // 计划离店时间
	private Integer marketPrice;

	private Integer sellPrice;

	private Integer orderAmount;
	
	private Integer mebId;// 预订会员编号
	private Integer mebType;// 预定会员等级

	private String bkGstName; // 预订客人姓名
	private String bkCardNo; // 预订会员卡号
	private String bkMobile; // 预订会员手机号码
	private String bkEmail; // 预订会员邮箱
	private String contactName; // 联系人姓名
	private String contactPhone; // 联系人电话; 和联系人电子邮箱中，至少有一个必填
	private String contactEmail; // 联系人电子邮箱; 和联系人电话中，至少有一个必填

	private String invoiceTitle;// 发票抬头
	private String invoiceVal;// 发票内容
	private Integer invoiceType;// 领取方式 1为离店时到前台领取
	
	/** V1.5更改新格式，上面的可为空 **/
	private String invoiceCode; // 发票类型    7day_0（7天专用发票，对应会员发票类型4增值税发票）,7day_1（7天普通发票对应会员发票类型3普通发票）

	// 发票信息{"Invoice_Type":0,"Name":"梅展","Customer_TaxNo":"T21212121","Address":"广州市海珠区新窖西路300号","Phone":"15914327856","Bank_Name":"中国工商银行","Bank_Account":"45515454515212154"}
	private String invoiceMsg;// 发票信息

	private Integer revPoint;// 会员担保积分;不同的积分可以延长不同的保留时效，如果传递0，则不需要用积分担保保留时效。
	private Integer assureType; // 担保类型:0:无担保，2:全程担保
	private String activityCode;// 市场活动编号
	private String remark;// 备注

	private Integer bkPropertyId; // 预订会员属性编码;比如使用会员卡，或者银行卡
	private Integer bkProTypeId; // 预订会员属性类型编码

	private List<OrderGuestParam> lstOrderGsts;// 入住人列表
	
	private String folioid;
	
	private Integer folioSellerId; // 订单销售来源渠道
	
	private String lastArrTime; // 最晚到店时间.预留字段，到付订单有效，可以不传，默认和最晚取消时间一致，最晚取消时间是根据会员权益判断，普卡18点，银卡及以上20点，过了时间点，就当前时间加2小时
	
	private String storeId;

    private String cardNo;

    private Integer sid;

    private Integer rate;

    private Integer commission;

    private Integer itemId;

    private Integer platformId;
    
    private String bookingid;

    private Integer shareid;
    
    private String openId;
    
    private String employeeName; // 员工名称

	private Integer payType; // 支付方式.0到付，1线上预付，2信用住
	private String bkIp;// 预订人ip
	private Integer dayLength; // 入住天数(计划抵店时间至计划离店时间的时间差)

	private String roomSourceType;// 供应商类型   ISS系统获得的房态，有供应商类型，如7days、JinJiang、XiRuan、Ebooking、EbkOverseas

	private String channelSourceType = "1";// 可以不传，要传就传1，默认1铂涛会平台
	private String sn;// 交易订单号
	private List<RoomRateParam> roomRates;// 价格

	private List<CouponParam> coupons;// 优惠券

	private Integer usePoint;// 使用积分
	private Double useCash;// 使用储值

	private Integer mebBrandId;// 会员品牌
	private String innChannelCode;// 渠道编码
	private Boolean forceBooking;// 是否爆房下单
	private String innEngName;// 酒店英文名
	private String roomTypeEngName;// 房型英文名
	private String bkLastName;// 预订人英文姓
	private String bkFirstName;// 预订人英文名
	private String concatLastName;// 联系人英文姓
	private String concatFirstName;// 联系人英文名
	private String countryCode;// 国家编码
	private String preference;// 客户偏好
	private String timeZone;// 时区

	public String getInnId() {
		return innId;
	}

	public void setInnId(String innId) {
		this.innId = innId;
	}
	
	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

	public String getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public Integer getRoomCount() {
		return roomCount;
	}

	public void setRoomCount(Integer roomCount) {
		this.roomCount = roomCount;
	}

	public Integer getSourceTypeId() {
		return sourceTypeId;
	}

	public void setSourceTypeId(Integer sourceTypeId) {
		this.sourceTypeId = sourceTypeId;
	}

	public String getDtArrorig() {
		return dtArrorig;
	}

	public void setDtArrorig(String dtArrorig) {
		this.dtArrorig = dtArrorig;
	}

	public String getDtDeporig() {
		return dtDeporig;
	}

	public void setDtDeporig(String dtDeporig) {
		this.dtDeporig = dtDeporig;
	}

	public Integer getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Integer marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Integer getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Integer sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Integer getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Integer getMebId() {
		return mebId;
	}

	public void setMebId(Integer mebId) {
		this.mebId = mebId;
	}

	public Integer getMebType() {
		return mebType;
	}

	public void setMebType(Integer mebType) {
		this.mebType = mebType;
	}

	public String getBkGstName() {
		return bkGstName;
	}

	public void setBkGstName(String bkGstName) {
		this.bkGstName = bkGstName;
	}

	public String getBkCardNo() {
		return bkCardNo;
	}

	public void setBkCardNo(String bkCardNo) {
		this.bkCardNo = bkCardNo;
	}

	public String getBkMobile() {
		return bkMobile;
	}

	public void setBkMobile(String bkMobile) {
		this.bkMobile = bkMobile;
	}

	public String getBkEmail() {
		return bkEmail;
	}

	public void setBkEmail(String bkEmail) {
		this.bkEmail = bkEmail;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getInvoiceVal() {
		return invoiceVal;
	}

	public void setInvoiceVal(String invoiceVal) {
		this.invoiceVal = invoiceVal;
	}

	public Integer getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(Integer invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public String getInvoiceMsg() {
		return invoiceMsg;
	}

	public void setInvoiceMsg(String invoiceMsg) {
		this.invoiceMsg = invoiceMsg;
	}

	public Integer getRevPoint() {
		return revPoint;
	}

	public void setRevPoint(Integer revPoint) {
		this.revPoint = revPoint;
	}

	public Integer getAssureType() {
		return assureType;
	}

	public void setAssureType(Integer assureType) {
		this.assureType = assureType;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getBkPropertyId() {
		return bkPropertyId;
	}

	public void setBkPropertyId(Integer bkPropertyId) {
		this.bkPropertyId = bkPropertyId;
	}

	public Integer getBkProTypeId() {
		return bkProTypeId;
	}

	public void setBkProTypeId(Integer bkProTypeId) {
		this.bkProTypeId = bkProTypeId;
	}

	public List<OrderGuestParam> getLstOrderGsts() {
		return lstOrderGsts;
	}

	public void setLstOrderGsts(List<OrderGuestParam> lstOrderGsts) {
		this.lstOrderGsts = lstOrderGsts;
	}

	public String getFolioid() {
		return folioid;
	}

	public void setFolioid(String folioid) {
		this.folioid = folioid;
	}

	public Integer getFolioSellerId() {
		return folioSellerId;
	}

	public void setFolioSellerId(Integer folioSellerId) {
		this.folioSellerId = folioSellerId;
	}

	public String getLastArrTime() {
		return lastArrTime;
	}

	public void setLastArrTime(String lastArrTime) {
		this.lastArrTime = lastArrTime;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public Integer getCommission() {
		return commission;
	}

	public void setCommission(Integer commission) {
		this.commission = commission;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}
	
	public String getBookingid() {
		return bookingid;
	}

	public void setBookingid(String bookingid) {
		this.bookingid = bookingid;
	}

	public Integer getShareid() {
		return shareid;
	}

	public void setShareid(Integer shareid) {
		this.shareid = shareid;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getBkIp() {
		return bkIp;
	}

	public void setBkIp(String bkIp) {
		this.bkIp = bkIp;
	}

	public Integer getDayLength() {
		return dayLength;
	}

	public void setDayLength(Integer dayLength) {
		this.dayLength = dayLength;
	}

	public String getRoomSourceType() {
		return roomSourceType;
	}

	public void setRoomSourceType(String roomSourceType) {
		this.roomSourceType = roomSourceType;
	}

	public String getChannelSourceType() {
		return channelSourceType;
	}

	public void setChannelSourceType(String channelSourceType) {
		this.channelSourceType = channelSourceType;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public List<RoomRateParam> getRoomRates() {
		return roomRates;
	}

	public void setRoomRates(List<RoomRateParam> roomRates) {
		this.roomRates = roomRates;
	}

	public List<CouponParam> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<CouponParam> coupons) {
		this.coupons = coupons;
	}

	public Integer getUsePoint() {
		return usePoint;
	}

	public void setUsePoint(Integer usePoint) {
		this.usePoint = usePoint;
	}

	public Double getUseCash() {
		return useCash;
	}

	public void setUseCash(Double useCash) {
		this.useCash = useCash;
	}

	public Integer getMebBrandId() {
		return mebBrandId;
	}

	public void setMebBrandId(Integer mebBrandId) {
		this.mebBrandId = mebBrandId;
	}

	public String getInnChannelCode() {
		return innChannelCode;
	}

	public void setInnChannelCode(String innChannelCode) {
		this.innChannelCode = innChannelCode;
	}

	public Boolean getForceBooking() {
		return forceBooking;
	}

	public void setForceBooking(Boolean forceBooking) {
		this.forceBooking = forceBooking;
	}

	public String getInnEngName() {
		return innEngName;
	}

	public void setInnEngName(String innEngName) {
		this.innEngName = innEngName;
	}

	public String getRoomTypeEngName() {
		return roomTypeEngName;
	}

	public void setRoomTypeEngName(String roomTypeEngName) {
		this.roomTypeEngName = roomTypeEngName;
	}

	public String getBkLastName() {
		return bkLastName;
	}

	public void setBkLastName(String bkLastName) {
		this.bkLastName = bkLastName;
	}

	public String getBkFirstName() {
		return bkFirstName;
	}

	public void setBkFirstName(String bkFirstName) {
		this.bkFirstName = bkFirstName;
	}

	public String getConcatLastName() {
		return concatLastName;
	}

	public void setConcatLastName(String concatLastName) {
		this.concatLastName = concatLastName;
	}

	public String getConcatFirstName() {
		return concatFirstName;
	}

	public void setConcatFirstName(String concatFirstName) {
		this.concatFirstName = concatFirstName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getPreference() {
		return preference;
	}

	public void setPreference(String preference) {
		this.preference = preference;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public void setRoomCount(int roomCount) {
		this.roomCount = roomCount;
	}

	public void setSourceTypeId(int sourceTypeId) {
		this.sourceTypeId = sourceTypeId;
	}
}
