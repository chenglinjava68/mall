package com.plateno.booking.internal.wechat;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.ConstEnum;
import com.plateno.booking.internal.bean.contants.ConstEnum.ChannelCode;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.common.util.date.DateUtil;
import com.plateno.booking.internal.common.util.http.HttpUtils;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.dao.pojo.BillDetails;
import com.plateno.booking.internal.goods.GoodsService;
import com.plateno.booking.internal.member.MemberService;
import com.plateno.booking.internal.wechat.model.BookingDetailReturn;
import com.plateno.booking.internal.wechat.model.PayDetailReturn;
import com.plateno.booking.internal.wechat.model.WxOrderSuccessMessage;
import com.plateno.www.webservice.service.userinterface.MemberProperty;

@Service
public class WechatTemplementSerivce {

	private static final Logger logger = Logger.getLogger(WechatTemplementSerivce.class);

	@Autowired
	private MemberService memberService;
	@Autowired
	private GoodsService goodsService;

	
	public void sendWechatTemplement(BillDetails billDetails)throws Exception {
		if (billDetails != null) {
			String openId = "";
			if (billDetails.getPlatformId().equals(Config.WX_PLATFORM_ID)) {
				if (StringUtils.isNotBlank(billDetails.getOpenId())&&StringUtils.isNotEmpty(billDetails.getOpenId())) {
					openId = billDetails.getOpenId();
				}else{
					if (StringUtils.isNotBlank(billDetails.getMobile())&&StringUtils.isNotEmpty(billDetails.getMobile())) {
						com.plateno.www.webservice.service.userinterface.MemberProperty memberProperty = memberService.getMemerByMobile(billDetails.getMobile());
						MemberProperty memberPropertys = memberService.getMemberProperty(memberProperty.getMebId(),Config.PROPERTY_TYPE_WEIXIN_MCH_OPENID);
			    		if (memberPropertys != null && memberPropertys.getMebId() > 0 && memberPropertys.getPropertyId() > 0) {
			    			openId = memberPropertys.getPropertyValues();
			    		}
					}
				}
				logger.info("发送模版,openId："+ openId);
				if (StringUtils.isNotBlank(openId)&&StringUtils.isNotEmpty(openId)) {
					WxOrderSuccessMessage wechat = wechatTemplement(billDetails);
					sendWeChatTemplement(openId, Config.TEMPLATEID, wechat);
				}
			}
			
		}
	}

	private WxOrderSuccessMessage wechatTemplement(BillDetails billDetails) throws IOException,OrderException {
		PayDetailReturn payDetailReturn = getPayDetail(billDetails);
		if (payDetailReturn == null){
			logger.error("获取商品信息有误");
			return null;
		}
		// 构造消息
		WxOrderSuccessMessage msg = new WxOrderSuccessMessage();
		msg.setTopColor("#f47920");
		msg.addFirst("您已购买" + payDetailReturn.getScenicName() + "门票");
		msg.addFirstColor("#436EEE");
		msg.addGoodName(ChannelCode.getByCode(billDetails.getChannel()) + payDetailReturn.getGoodName());
		msg.addGoodNameColor("#436EEE");
		msg.addType(payDetailReturn.getTicketType() + " " + billDetails.getQuantity() +"张");
		msg.addTypeColor("#436EEE");
		Double money = Double.valueOf(billDetails.getAmount())/100;
		msg.addAmount(money + "元");
		msg.addAmountColor("#436EEE");
		msg.addVisitDate(DateUtil.dateToFormatStr(billDetails.getVisitDate(),DateUtil.YYYY_MM_DD));
		msg.addVisitDateColor("#436EEE");
		//msg.addRemark("铂涛·旅行-连接有温度的旅行体验！");

		// 链接到 订单详情 页面
		msg.setUrl(Config.BILL_DETAIL_URL+billDetails.getTradeNo());

		return msg;
	}

	public boolean sendWeChatTemplement(String openId, String templateId,WxOrderSuccessMessage wechat) throws OrderException {
		if (wechat == null) {
			return false;
		}
		String response = "";
		try {
			String url = Config.WECHAT_TEMPLATE_URL + "template-message";
			url += "?openid=" + openId + "&templateId=" + templateId;
			logger.info("发起微信发送模版接口的参数:" + JsonUtils.toJsonString(wechat));
			response = HttpUtils.httpPostRequest(url,JsonUtils.toJsonString(wechat),Config.CONNECT_SOKET_TIME_OUT_LONG,Config.CONNECT_TIME_OUT_LONG);

			logger.info("调模版接口返回数据结构:" + response);
			if (response != null) {
				JsonNode responseNode = JsonUtils.parseJson(response);
				String resultCode = responseNode.path("errcode").asText();
				if (!resultCode.equals("0")) {
					String resultMsg = responseNode.path("errmsg").asText();
					logger.error("调模版接口成功，接口返回信息：" + resultMsg);
					return false;
				} else {
					return true;
				}
			} else {
				logger.error("调模版接口出错，接口返回错误信息：" + response);
				return false;
			}
		} catch (Exception e) {
			logger.error(String.format("调模版接口出错,%s", response), e);
			return false;
		}
	}

	public PayDetailReturn getPayDetail(BillDetails billDetails) throws OrderException {
		BookingDetailReturn book = goodsService.getGoodByGoodIdAndPiceDate(billDetails.getGoodsId(), 0);
		if (book == null)
			return null;
		PayDetailReturn payDetailReturn = new PayDetailReturn();
		// 获取可支付类型
		payDetailReturn.setTicketType(ConstEnum.TicketpCode.getByCode(billDetails.getTicketType()));
		payDetailReturn.setGoodName(book.getGoodName());
		payDetailReturn.setScenicName(book.getScenicName());
		return payDetailReturn;
	}

}
