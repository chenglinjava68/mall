package com.plateno.booking.internal.goods;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.common.util.http.HttpUtils;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.wechat.model.BookingDetailReturn;

@Service
public class GoodsService {
	
	public BookingDetailReturn getGoodByGoodIdAndPiceDate(String goodId,long priceDate) throws OrderException {
		BookingDetailReturn bookingDetailReturn = new BookingDetailReturn();
		String response = "";
		try {
			ObjectNode node = JsonUtils.createObjectNode();
			node.put("goodsId", goodId);
			if (priceDate != 0) {
				node.put("priceDate", priceDate);
			}
			String url = Config.SCENIC_TRIP_URL+ "plateno-trip-goods/good/getGoodInfo.html"; // 商品服务接口
			LogUtils.sysLoggerInfo("发起商品查询请求的参数:" + JsonUtils.toJsonString(node));
			response = HttpUtils.httpPostRequest(url,JsonUtils.toJsonString(node),Config.CONNECT_SOKET_TIME_OUT_LONG,Config.CONNECT_TIME_OUT_LONG);
			LogUtils.httpLoggerInfo("底层商品服务返回数据结构:" + response);
			if (response != null && !StringUtils.isBlank(response)) {
				JsonNode responseNode = JsonUtils.parseJson(response);
				Integer resultCode = responseNode.path("msgCode").asInt();
				if (!resultCode.equals(BookingConstants.TRIP_GOOD_SERVER_SUCCESS_CODE)) {
					String resultMsg = responseNode.path("message").asText();
					LogUtils.sysErrorLoggerError("底层商品服务出错，接口返回错误信息：" + resultMsg,null);
					return null;
				} else {
					if (!responseNode.has("result"))
						return null;
					if (responseNode.path("result").isNull())
						return null;
					BeanUtils.copyProperties(responseNode.path("result"),bookingDetailReturn);
					bookingDetailReturn.setGoodName(responseNode.path("result").path("goodsName").asText());
					bookingDetailReturn.setScenicName(responseNode.path("result").path("scenicName").asText());
					return bookingDetailReturn;
				}
			} else {
				LogUtils.sysErrorLoggerError("商品服务返回数据异常",null);
				return null;
			}
		} catch (Exception e) {
			LogUtils.sysErrorLoggerError(String.format("底层商品服务请求失败,%s", response), e);
			return null;
		}
	}

}
