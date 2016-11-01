package com.plateno.booking.internal.bean.vo.hotel.convert;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.plateno.booking.internal.bean.vo.hotel.response.inner.OrderAccount;
import com.plateno.booking.internal.bean.vo.hotel.response.inner.OrderInvoice;

public class OrderInnResponseConvert implements Serializable {
	
	private static final long serialVersionUID = -621846312286237251L;

	private String orderCode;					//订单编码

	private String chainId;						//第三方酒店id

	private String thirdRoomtypeId;				//第三方房型id	

	private String innId;						//酒店id

	private String innName;						//酒店名称

	private String bkName;						//会员名称
	
	private Integer bkMebId;					//会员ID

	private String bkMobile;					//会员手机

	private String bkEmail;						//会员邮件

	private Integer bkMebType;					//会员等级

	private String bkCardNo;					//会员卡号

	private Integer bkPropertyId;				//预定属性id
	
	private Integer bkProtypeId;				//预订属性类型id

	private String contactName;					//联系人名字

	private String contactPhone;				//联系人手机

	private String contactEmail;				//联系人电子邮箱

	private String guestsName;					//入住人姓名（字符串）

	private Integer guestsNum;					//入住人总人数

	private String bkIp;						//预订人ip地址

	private Integer supplierId;					//供应商id			1.铂涛，2.锦江

	private Integer assureType;					//担保类型

	private Integer assureMebPoint;				//担保积分
	
	private Integer sourceType;					//来源

	private String innerRemarks;				//员工内部备注

	private String remarks;						//备注

	private Integer orderState;					//订单状态 订单状态： 0 待确认，  1预订成功， 2 已取消，  3预订未到， 4 已入住，  5已完成， 6 确认失败，7 待酒店确认， 8 酒店拒绝 （注：0 待确认，6 确认失败 用户暂时不会看到这两个状态；8 包含酒店确认失败）

	private String activityCode;				//市场活动类型

	private Integer sellerId;					//业绩销售员

	private Integer payState;					//支付状态

	private Integer canCancel;					//是否支持取消

	private Integer needInvoice;				//是否需要发票

	private String roomTypeId;					//房间类型

	private String roomTypeName;				//房间类型名称

	private Integer payType;					//支付方式

	private BigDecimal origRate;				//原价

	private BigDecimal totalRate;				//总价

	private BigDecimal payRate;					//支付价

	private BigDecimal discPrice;				//优惠金额

	private String rateCode;					//价格代码

	private Date origArrDate;					//预计抵店时间

	private Date origDepDate;					//预计离店时间

	private Date arrDate;						//实际到店时间

	private Date depDate;						//实际离店时间

	private Integer dayLength;					//入住天数

	private Integer roomQty;					//房间数

	private Date canCancelTime;					//最晚取消时间

	private Date keepDate;						//保留时间

	private Date createTime;					//创建时间
	
	private String roomSourceType;				//房源编码

	private OrderInvoice invoice;				//发票
	
	private String lastOrderCode;				//上个订单号
	
	private List<String> lstThirdOrderIds;		//第三方订单号列表
	
	private Double oldPayRate;					//旧订单支付价
	
	private String externalId;					//外部跟踪号
	
	private String	cityCode;					//城市编号
	
	private Double chargedRate;					//后付订单实扣金额
	
	private String timeZone;					//时区
	private String innEngName;					//酒店英文名
	private String roomTypeEngName;				//房型英文名
	private String bkLastName;					//预订人英文姓
	private String bkFirstName;					//预订人英文名
	private String countryCode;					//国家编码
	private String preference;					//客户偏好
	private String currency;					//货币代码
    private Integer overtimeState;				//超时状态
    private Integer accState;
    private String concatLastName;				//联系人英文姓
    private String concatFirstName;				//联系人英文名

	private List<String> lstRoomNos;			//房间号列表

	private List<OrderAccount> lstAccounts;		//账单列表

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getChainId() {
		return chainId;
	}

	public void setChainId(String chainId) {
		this.chainId = chainId;
	}

	public String getThirdRoomtypeId() {
		return thirdRoomtypeId;
	}

	public void setThirdRoomtypeId(String thirdRoomtypeId) {
		this.thirdRoomtypeId = thirdRoomtypeId;
	}

	public String getInnId() {
		return innId;
	}

	public void setInnId(String innId) {
		this.innId = innId;
	}

	public String getInnName() {
		return innName;
	}

	public void setInnName(String innName) {
		this.innName = innName;
	}

	public String getBkName() {
		return bkName;
	}

	public void setBkName(String bkName) {
		this.bkName = bkName;
	}

	public Integer getBkMebId() {
		return bkMebId;
	}

	public void setBkMebId(Integer bkMebId) {
		this.bkMebId = bkMebId;
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

	public Integer getBkMebType() {
		return bkMebType;
	}

	public void setBkMebType(Integer bkMebType) {
		this.bkMebType = bkMebType;
	}

	public String getBkCardNo() {
		return bkCardNo;
	}

	public void setBkCardNo(String bkCardNo) {
		this.bkCardNo = bkCardNo;
	}

	public Integer getBkPropertyId() {
		return bkPropertyId;
	}

	public void setBkPropertyId(Integer bkPropertyId) {
		this.bkPropertyId = bkPropertyId;
	}

	public Integer getBkProtypeId() {
		return bkProtypeId;
	}

	public void setBkProtypeId(Integer bkProtypeId) {
		this.bkProtypeId = bkProtypeId;
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

	public String getGuestsName() {
		return guestsName;
	}

	public void setGuestsName(String guestsName) {
		this.guestsName = guestsName;
	}

	public Integer getGuestsNum() {
		return guestsNum;
	}

	public void setGuestsNum(Integer guestsNum) {
		this.guestsNum = guestsNum;
	}

	public String getBkIp() {
		return bkIp;
	}

	public void setBkIp(String bkIp) {
		this.bkIp = bkIp;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getAssureType() {
		return assureType;
	}

	public void setAssureType(Integer assureType) {
		this.assureType = assureType;
	}

	public Integer getAssureMebPoint() {
		return assureMebPoint;
	}

	public void setAssureMebPoint(Integer assureMebPoint) {
		this.assureMebPoint = assureMebPoint;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public String getInnerRemarks() {
		return innerRemarks;
	}

	public void setInnerRemarks(String innerRemarks) {
		this.innerRemarks = innerRemarks;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getPayState() {
		return payState;
	}

	public void setPayState(Integer payState) {
		this.payState = payState;
	}

	public Integer getCanCancel() {
		return canCancel;
	}

	public void setCanCancel(Integer canCancel) {
		this.canCancel = canCancel;
	}

	public Integer getNeedInvoice() {
		return needInvoice;
	}

	public void setNeedInvoice(Integer needInvoice) {
		this.needInvoice = needInvoice;
	}

	public String getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public String getRoomTypeName() {
		return roomTypeName;
	}

	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public BigDecimal getOrigRate() {
		return origRate;
	}

	public void setOrigRate(BigDecimal origRate) {
		this.origRate = origRate;
	}

	public BigDecimal getTotalRate() {
		return totalRate;
	}

	public void setTotalRate(BigDecimal totalRate) {
		this.totalRate = totalRate;
	}

	public BigDecimal getPayRate() {
		return payRate;
	}

	public void setPayRate(BigDecimal payRate) {
		this.payRate = payRate;
	}

	public BigDecimal getDiscPrice() {
		return discPrice;
	}

	public void setDiscPrice(BigDecimal discPrice) {
		this.discPrice = discPrice;
	}

	public String getRateCode() {
		return rateCode;
	}

	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	public Date getOrigArrDate() {
		return origArrDate;
	}

	public void setOrigArrDate(Date origArrDate) {
		this.origArrDate = origArrDate;
	}

	public Date getOrigDepDate() {
		return origDepDate;
	}

	public void setOrigDepDate(Date origDepDate) {
		this.origDepDate = origDepDate;
	}

	public Date getArrDate() {
		return arrDate;
	}

	public void setArrDate(Date arrDate) {
		this.arrDate = arrDate;
	}

	public Date getDepDate() {
		return depDate;
	}

	public void setDepDate(Date depDate) {
		this.depDate = depDate;
	}

	public Integer getDayLength() {
		return dayLength;
	}

	public void setDayLength(Integer dayLength) {
		this.dayLength = dayLength;
	}

	public Integer getRoomQty() {
		return roomQty;
	}

	public void setRoomQty(Integer roomQty) {
		this.roomQty = roomQty;
	}

	public Date getCanCancelTime() {
		return canCancelTime;
	}

	public void setCanCancelTime(Date canCancelTime) {
		this.canCancelTime = canCancelTime;
	}

	public Date getKeepDate() {
		return keepDate;
	}

	public void setKeepDate(Date keepDate) {
		this.keepDate = keepDate;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRoomSourceType() {
		return roomSourceType;
	}

	public void setRoomSourceType(String roomSourceType) {
		this.roomSourceType = roomSourceType;
	}

	public OrderInvoice getInvoice() {
		return invoice;
	}

	public void setInvoice(OrderInvoice invoice) {
		this.invoice = invoice;
	}

	public String getLastOrderCode() {
		return lastOrderCode;
	}

	public void setLastOrderCode(String lastOrderCode) {
		this.lastOrderCode = lastOrderCode;
	}

	public List<String> getLstThirdOrderIds() {
		return lstThirdOrderIds;
	}

	public void setLstThirdOrderIds(List<String> lstThirdOrderIds) {
		this.lstThirdOrderIds = lstThirdOrderIds;
	}

	public Double getOldPayRate() {
		return oldPayRate;
	}

	public void setOldPayRate(Double oldPayRate) {
		this.oldPayRate = oldPayRate;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Double getChargedRate() {
		return chargedRate;
	}

	public void setChargedRate(Double chargedRate) {
		this.chargedRate = chargedRate;
	}

	public List<String> getLstRoomNos() {
		return lstRoomNos;
	}

	public void setLstRoomNos(List<String> lstRoomNos) {
		this.lstRoomNos = lstRoomNos;
	}

	public List<OrderAccount> getLstAccounts() {
		return lstAccounts;
	}

	public void setLstAccounts(List<OrderAccount> lstAccounts) {
		this.lstAccounts = lstAccounts;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getOvertimeState() {
		return overtimeState;
	}

	public void setOvertimeState(Integer overtimeState) {
		this.overtimeState = overtimeState;
	}

	public Integer getAccState() {
		return accState;
	}

	public void setAccState(Integer accState) {
		this.accState = accState;
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
}
