package com.plateno.booking.internal.common.util;

import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;

import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.common.util.http.HttpUtils;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.common.util.number.MD5Maker;


/**
 * @author yi.wang
 * @date 2016年7月7日下午5:49:25
 * @version 1.0
 * @Description
 */
public class WeChatPayUtil {
	private static final Logger log = Logger.getLogger(WeChatPayUtil.class);

	private static ReadWriteLock rwLock = new ReentrantReadWriteLock();

	private WeChatPayUtil() {
	}

	private static WeChatPayUtil instance;

	public static WeChatPayUtil getInstance() {
		try {
			rwLock.readLock().lock();
			if (null == instance) {
				rwLock.readLock().unlock();
				rwLock.writeLock().lock();
				try {
					if (null == instance) {
						instance = new WeChatPayUtil();
					}
				} finally {
					rwLock.writeLock().unlock();
				}
				rwLock.readLock().lock();
			}
		} finally {
			rwLock.readLock().unlock();
		}
		return instance;
	}

	/**
	 * 支付网关退款
	 * @throws OrderException
	 * @throws PlatenoException
	 */
	public JsonNode getWeixinPayPrepayId(String oldBillLogTradeNo, String billLogTradeNo, String remark, int orderAmount, String notifyUrl)
			throws OrderException {
		try {
			String postData = getRefundPayGatePostData(oldBillLogTradeNo, billLogTradeNo, remark, orderAmount, notifyUrl);
			log.info("拉起支付网关退款，传参：" + postData);
			String result = HttpUtils.httpPostNotJsonRequest(Config.MERCHANT_PAY_URL + "refund/apply.html", postData);
			log.info("拉起支付网关退款，支付网关返回信息：" + result);
			if (!StringUtils.isEmpty(result)) {
				JsonNode jsonObject = JsonUtils.parseJson(result);
				return jsonObject;
			}
		} catch (Exception e) {
			log.error("获取支付网关退款异常", e);
		}
		throw new OrderException("调用支付网关退款接口失败");
	}

	private String getRefundPayGatePostData(String oldBillLogTradeNo, String billLogTradeNo, String remark, int orderAmount,
			String notifyUrl) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("merchantNo", Config.MERCHANT_NO);
		params.put("orderNo", oldBillLogTradeNo);//原交易订单号
		params.put("refundOrderNo", billLogTradeNo);//退款订单号
		params.put("refundAmount", String.valueOf(orderAmount)); //退款金额
		params.put("remark", remark);
		if (!StringUtils.isEmpty(notifyUrl)) {
			params.put("notifyUrl", notifyUrl);
		}

		//params.put("ext2", orderNo);
		StringBuffer signData = new StringBuffer(createLinkString(params));
		String signatureData = createLinkString(params) + "&key=" + Config.MERCHANT_PAY_KEY;
		log.info("拉起订单退款，待签名串：" + signatureData);
		signData.append("&signData=").append(MD5Maker.getMD5(signatureData).toLowerCase());
		return signData.toString();
	}

	private String createLinkString(Map<String, String> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		return prestr;
	}
}
