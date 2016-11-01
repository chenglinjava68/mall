package com.plateno.booking.internal.bean.vo.hotel.request;

import java.io.Serializable;
import java.util.List;

import com.plateno.booking.internal.bean.vo.hotel.request.inner.CouponParam;
import com.plateno.booking.internal.bean.vo.hotel.request.inner.OrderGuestParam;
import com.plateno.booking.internal.bean.vo.hotel.request.inner.RoomRateParam;

public class AddBookCommonRequest implements Serializable {

	private static final long serialVersionUID = 4080531421776131773L;

	private String innId; // 酒店编码
	private String roomTypeId; // 房间类型
	private int roomCount; // 房间数量
	private int sourceTypeId; // 客人登记来源: 39中介，41客服中心，70网站, 94手机短信， 107手机WAP，110分店大堂上网机， 191语音订房， 193桌面预订终端
	private String dtArrorig;// 计划抵店时间
	private String dtDeporig; // 计划离店时间
	private Integer mebId;// 预订会员编号
	private Integer mebType;//预定会员等级

	private String bkGstName; // 预订客人姓名
	private String bkCardNo; // 预订会员卡号
	private String bkMobile; // 预订会员手机号码
	private String bkEmail; // 预订会员邮箱
	private String contactName; // 联系人姓名
	private String contactPhone; // 联系人电话; 和联系人电子邮箱中，至少有一个必填
	private String contactEmail; // 联系人电子邮箱; 和联系人电话中，至少有一个必填
	
	private String invoiceTitle;// 发票抬头
	private String invoiceVal;// 发票内容
	private int invoiceType;// 领取方式 1为离店时到前台领取
	/** V1.5更改新格式，上面的可为空 **/
	private String invoiceCode; // 发票类型 7day_0（7天专用发票，对应会员发票类型4增值税发票）, 7day_1（7天普通发票对应会员发票类型3普通发票）
	
	// 发票信息{"Invoice_Type":0, "Name":"梅展","Customer_TaxNo":"T21212121","Address":"广州市海珠区新窖西路300号", "Phone":"15914327856","Bank_Name":"中国工商银行", "Bank_Account":"45515454515212154"}
	private String invoiceMsg;//发票信息
	
	private int revPoint;// 会员担保积分;不同的积分可以延长不同的保留时效，如果传递0，则不需要用积分担保保留时效。
	private int assureType; // 担保类型:0:无担保，2:全程担保
	private String activityCode;//市场活动编号
	private String remark;// 备注

	private int bkPropertyId; // 预订会员属性编码;比如使用会员卡，或者银行卡
	private int bkProTypeId; // 预订会员属性类型编码

	private List<OrderGuestParam> lstOrderGsts;// 入住人列表
	private int folioSellerId; // 订单销售来源渠道
	private String lastArrTime; // 最晚到店时间.预留字段，到付订单有效，可以不传，默认和最晚取消时间一致，最晚取消时间是根据会员权益判断，普卡18点，银卡及以上20点，过了时间点，就当前时间加2小时
	
	private int payType; // 支付方式.0到付，1线上预付，2信用住
	private String bkIp;// 预订人ip
	private int dayLength; // 入住天数
	
	private String roomSourceType;// 供应商类型
	private String channelSourceType = "1";// 可以不传，要传就传1，默认1铂涛会平台
	private String sn;// 交易订单号
	private List<RoomRateParam> roomRates;// 价格

	private List<CouponParam> coupons;//优惠券

	private Integer usePoint;//使用积分
	private Double useCash;//使用储值
	
	public String getInnId() {
		return innId;
	}

	public void setInnId(String innId) {
		this.innId = innId;
	}

	public String getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public int getRoomCount() {
		return roomCount;
	}

	public void setRoomCount(int roomCount) {
		this.roomCount = roomCount;
	}

	public int getSourceTypeId() {
		return sourceTypeId;
	}

	public void setSourceTypeId(int sourceTypeId) {
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

	public int getMebId() {
		return mebId;
	}

	public void setMebId(int mebId) {
		this.mebId = mebId;
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

	public int getRevPoint() {
		return revPoint;
	}

	public void setRevPoint(int revPoint) {
		this.revPoint = revPoint;
	}

	public int getAssureType() {
		return assureType;
	}

	public void setAssureType(int assureType) {
		this.assureType = assureType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getBkPropertyId() {
		return bkPropertyId;
	}

	public void setBkPropertyId(int bkPropertyId) {
		this.bkPropertyId = bkPropertyId;
	}

	public int getBkProTypeId() {
		return bkProTypeId;
	}

	public void setBkProTypeId(int bkProTypeId) {
		this.bkProTypeId = bkProTypeId;
	}

	public List<OrderGuestParam> getLstOrderGsts() {
		return lstOrderGsts;
	}

	public void setLstOrderGsts(List<OrderGuestParam> lstOrderGsts) {
		this.lstOrderGsts = lstOrderGsts;
	}

	public int getFolioSellerId() {
		return folioSellerId;
	}

	public void setFolioSellerId(int folioSellerId) {
		this.folioSellerId = folioSellerId;
	}

	public String getLastArrTime() {
		return lastArrTime;
	}

	public void setLastArrTime(String lastArrTime) {
		this.lastArrTime = lastArrTime;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public String getBkIp() {
		return bkIp;
	}

	public void setBkIp(String bkIp) {
		this.bkIp = bkIp;
	}

	public int getDayLength() {
		return dayLength;
	}

	public void setDayLength(int dayLength) {
		this.dayLength = dayLength;
	}

	public Integer getMebType() {
		return mebType;
	}

	public void setMebType(Integer mebType) {
		this.mebType = mebType;
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

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
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

	public int getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(int invoiceType) {
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
}