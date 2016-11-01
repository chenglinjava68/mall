package com.plateno.booking.internal.bean.request.convert;

import java.io.Serializable;
import java.util.List;

import com.plateno.booking.internal.bean.request.custom.AddBookingParam.BookInfo;
import com.plateno.booking.internal.bean.request.lvmama.order.TravellersInfo;

public class ConvertBookingParam implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long goodsId; 	// 商品Id
	
	private String orderNo;	//TODO 生成订单ID

	private Long visitDate; // 游玩日期

	private Long time; // 场次

	private Integer quantity; // 票数

	private Integer memberId; // 会员ID

	private Integer totalAmount;

	private String folioId; // 房单号/工作账，店内销售,支付方式为挂房帐才有

	private Integer platformId; // 平台 铂涛旅行APP:105 铂涛旅行官网 : 113 铂涛旅行微信 : 111 铂涛旅行M站 : 112 O2O营销通 115

	private String storeId; // 分店ID

	private String cardNo; // 工号

	private String employeeName; // 员工名称

	private String openId; // 微信openId

	private Integer sid; // 销售人员ID
	
	private String busTypeId;//业务类型
	
	private Integer shareId;//分享现金赠送储值Id

	private BookInfo bookInfo;

	private Integer channel;
	
	private String bookingId;

	private List<TravellersInfo> travellers; // 游客信息

	/********** 同程场次信息 *************/
	//场次ID                     
	private String tcScreeningId;
	//场次开始时间
	private long tcBeginTime;
	//场次结束时间
	private long tcEndTime;
	
	/*********** 新增信息 **************/
	private Integer sellPrice;			//商品单价
	private Long channelGoodsId;		//渠道商品ID
	private Long channelProductId;		//渠道景点产品ID
	private Integer marketPrice;		//市场价
	private Integer ticketType;			//票类型
	private Integer goodsType;			//商品类型  ticketStatus
	private Integer lowestPrice;		//最低价
	private Integer rate;				//佣金率
	private Integer type;				//类型
	private String  name;				//商品名称
	
	private Integer b2bPrice;			// 结算价
	
	public Long getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Long visitDate) {
		this.visitDate = visitDate;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getFolioId() {
		return folioId;
	}

	public void setFolioId(String folioId) {
		this.folioId = folioId;
	}

	public String getStoreId() {
		return storeId;
	}

	
	public String getBusTypeId() {
		return busTypeId;
	}

	public void setBusTypeId(String busTypeId) {
		this.busTypeId = busTypeId;
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

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public BookInfo getBookInfo() {
		return bookInfo;
	}

	public void setBookInfo(BookInfo bookInfo) {
		this.bookInfo = bookInfo;
	}

	public List<TravellersInfo> getTravellers() {
		return travellers;
	}

	public void setTravellers(List<TravellersInfo> travellers) {
		this.travellers = travellers;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}
	
	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getTcScreeningId() {
		return tcScreeningId;
	}

	public void setTcScreeningId(String tcScreeningId) {
		this.tcScreeningId = tcScreeningId;
	}

	public long getTcBeginTime() {
		return tcBeginTime;
	}

	public void setTcBeginTime(long tcBeginTime) {
		this.tcBeginTime = tcBeginTime;
	}

	public long getTcEndTime() {
		return tcEndTime;
	}

	public void setTcEndTime(long tcEndTime) {
		this.tcEndTime = tcEndTime;
	}
	
	public Integer getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Integer sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Long getChannelProductId() {
		return channelProductId;
	}

	public Long getChannelGoodsId() {
		return channelGoodsId;
	}

	public void setChannelGoodId(Long channelGoodsId) {
		this.channelGoodsId = channelGoodsId;
	}

	public void setChannelProductId(Long channelProductId) {
		this.channelProductId = channelProductId;
	}
	
	public void setChannelGoodsId(Long channelGoodsId) {
		this.channelGoodsId = channelGoodsId;
	}

	public Integer getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(Integer marketPrice) {
		this.marketPrice = marketPrice;
	}

	public Integer getTicketType() {
		return ticketType;
	}

	public void setTicketType(Integer ticketType) {
		this.ticketType = ticketType;
	}

	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}

	public Integer getLowestPrice() {
		return lowestPrice;
	}

	public void setLowestPrice(Integer lowestPrice) {
		this.lowestPrice = lowestPrice;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getShareId() {
		return shareId;
	}

	public void setShareId(Integer shareId) {
		this.shareId = shareId;
	}

	public Integer getB2bPrice() {
		return b2bPrice;
	}

	public void setB2bPrice(Integer b2bPrice) {
		this.b2bPrice = b2bPrice;
	}
}