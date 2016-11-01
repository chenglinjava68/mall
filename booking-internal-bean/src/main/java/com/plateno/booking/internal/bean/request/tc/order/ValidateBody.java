package com.plateno.booking.internal.bean.request.tc.order;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 下单验证Body
 * @author yi.wang
 * @date 2016年5月27日上午10:32:38
 * @version 1.0
 * @Description
 */
@JsonAutoDetect(JsonMethod.FIELD)
public class ValidateBody {

	//产品Id
	@JsonProperty("PriceId")
	private Long PriceId;

	//旅游日期
	@JsonProperty("TravelDate")
	private String TravelDate;

	//取票数
	@JsonProperty("Tickets")
	private Integer Tickets;

	//同程价
	@JsonProperty("TCAmount")
	private BigDecimal TCAmount;

	//分销结算总价
	@JsonProperty("AgentAmount")
	private BigDecimal AgentAmount;

	//取票人身份证号码
	@JsonProperty("TravelerIdCardNo")
	private String TravelerIdCardNo;

	//取票人手机号码
	@JsonProperty("TravelerMobile")
	private String TravelerMobile;

	//预定人手机号码识别码
	@JsonProperty("MobileIdentifier")
	private String MobileIdentifier;

	//实名制列表
	@JsonProperty("RealBookInfo")
	private RealBookInfo[] RealBookInfo;

	//预定人邮箱
	@JsonProperty("BookEmail")
	private String BookEmail;

	//场次信息
	@JsonProperty("ScreeningInfo")
	private ScreeningInfo ScreeningInfo;

	public Long getPriceId() {
		return PriceId;
	}

	public void setPriceId(Long priceId) {
		PriceId = priceId;
	}

	public String getTravelDate() {
		return TravelDate;
	}

	public void setTravelDate(String travelDate) {
		TravelDate = travelDate;
	}

	public Integer getTickets() {
		return Tickets;
	}

	public void setTickets(Integer tickets) {
		Tickets = tickets;
	}

	public BigDecimal getTCAmount() {
		return TCAmount;
	}

	public void setTCAmount(BigDecimal tCAmount) {
		TCAmount = tCAmount;
	}

	public BigDecimal getAgentAmount() {
		return AgentAmount;
	}

	public void setAgentAmount(BigDecimal agentAmount) {
		AgentAmount = agentAmount;
	}

	public String getTravelerIdCardNo() {
		return TravelerIdCardNo;
	}

	public void setTravelerIdCardNo(String travelerIdCardNo) {
		TravelerIdCardNo = travelerIdCardNo;
	}

	public String getTravelerMobile() {
		return TravelerMobile;
	}

	public void setTravelerMobile(String travelerMobile) {
		TravelerMobile = travelerMobile;
	}

	public String getMobileIdentifier() {
		return MobileIdentifier;
	}

	public void setMobileIdentifier(String mobileIdentifier) {
		MobileIdentifier = mobileIdentifier;
	}

	public String getBookEmail() {
		return BookEmail;
	}

	public void setBookEmail(String bookEmail) {
		BookEmail = bookEmail;
	}

	public ScreeningInfo getScreeningInfo() {
		return ScreeningInfo;
	}

	public void setScreeningInfo(ScreeningInfo screeningInfo) {
		ScreeningInfo = screeningInfo;
	}

	public RealBookInfo[] getRealBookInfo() {
		return RealBookInfo;
	}

	public void setRealBookInfo(RealBookInfo[] realBookInfo) {
		RealBookInfo = realBookInfo;
	}

	//实名制列表
	@JsonAutoDetect(JsonMethod.FIELD)
	public static class RealBookInfo {
		//取票人姓名
		@JsonProperty("Name")
		private String Name;
		//取票人手机号码
		@JsonProperty("Mobile")
		private String Mobile;
		//身份证/护照号码
		@JsonProperty("IdCard")
		private String IdCard;
		//取票人邮箱
		@JsonProperty("Email")
		private String Email;

		public String getName() {
			return Name;
		}

		public void setName(String name) {
			Name = name;
		}

		public String getMobile() {
			return Mobile;
		}

		public void setMobile(String mobile) {
			Mobile = mobile;
		}

		public String getIdCard() {
			return IdCard;
		}

		public void setIdCard(String idCard) {
			IdCard = idCard;
		}

		public String getEmail() {
			return Email;
		}

		public void setEmail(String email) {
			Email = email;
		}

	}

	//场次信息
	@JsonAutoDetect(JsonMethod.FIELD)
	public static class ScreeningInfo {
		//场次ID
		@JsonProperty("ScreeningId")
		private String ScreeningId;
		//场次开始时间
		@JsonProperty("BeginTime")
		private String BeginTime;
		//场次结束时间
		@JsonProperty("EndTime")
		private String EndTime;

		public String getScreeningId() {
			return ScreeningId;
		}

		public void setScreeningId(String screeningId) {
			ScreeningId = screeningId;
		}

		public String getBeginTime() {
			return BeginTime;
		}

		public void setBeginTime(String beginTime) {
			BeginTime = beginTime;
		}

		public String getEndTime() {
			return EndTime;
		}

		public void setEndTime(String endTime) {
			EndTime = endTime;
		}
	}

}
