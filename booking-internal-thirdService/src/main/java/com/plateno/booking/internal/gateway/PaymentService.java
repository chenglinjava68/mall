package com.plateno.booking.internal.gateway;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.request.gateway.RefundOrderParam;
import com.plateno.booking.internal.bean.response.gateway.pay.PayQueryResponse;
import com.plateno.booking.internal.bean.response.gateway.refund.RefundOrderResponse;
import com.plateno.booking.internal.bean.response.gateway.refund.RefundQueryResponse;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.common.util.http.HttpUtils;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.common.util.number.MD5Maker;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;

@Service
@ServiceErrorCode(BookingConstants.CODE_GATEWAY_ERROR)
public class PaymentService {
	
	
	/**
	 * 网关退款查询
	 * 
	 * @param billLogTradeNo
	 * @return
	 * @throws Exception
	 */
	public RefundQueryResponse refundOrderQuery(String billLogTradeNo) throws Exception{
		Integer type = 1;	//退款查询
		String postData = getQueryPostData(billLogTradeNo,type);
		LogUtils.sysErrorLoggerInfo("拉起退款查询查询，传参：" + postData);
		String result =  HttpUtils.httpPostNotJsonRequest(Config.MERCHANT_PAY_URL+"refund/query.html", postData);
		LogUtils.sysErrorLoggerInfo("拉起退款查询查询，支付网关返回信息：" + result);
		if (!StringUtils.isEmpty(result)) {
			RefundQueryResponse  refundQueryResponse= JsonUtils.jsonToObject(result, RefundQueryResponse.class);
			return refundQueryResponse;
		}
		return null;
	}
	
	
	/**
	 * 网关支付查询
	 * 
	 * @param billLogTradeNo   支付流水
	 * @return
	 * @throws Exception
	 */
	public PayQueryResponse payOrderQuery(String billLogTradeNo) throws Exception{
		Integer type = 2;	//支付查询
		String postData = getQueryPostData(billLogTradeNo,type);
		LogUtils.sysErrorLoggerInfo("拉起支付查询，传参：" + postData);
		String result =  HttpUtils.httpPostNotJsonRequest(Config.MERCHANT_PAY_URL+"payment/query.html", postData);
		LogUtils.sysErrorLoggerInfo("拉起支付查询，支付网关返回信息：" + result);
		if (!StringUtils.isEmpty(result)) {
			PayQueryResponse  payQueryResponse= JsonUtils.jsonToObject(result, PayQueryResponse.class);
			return payQueryResponse;
		}
		return null;
	}
	
	
	/**
	 * 网关退款操作
	 * 
	 * @param billLogTradeNo
	 * @return
	 * @throws Exception
	 */
	public RefundOrderResponse refundOrder(RefundOrderParam refundOrderParam) throws Exception{
		String postData = refundOrderPostData(refundOrderParam);
		LogUtils.sysErrorLoggerInfo("拉起支付网关退款，传参：" + postData);
		String result = HttpUtils.httpPostNotJsonRequest(Config.MERCHANT_PAY_URL + "refund/apply.html", postData);
		LogUtils.sysErrorLoggerInfo("拉起支付网关退款，支付网关返回信息：" + result);
		if (!StringUtils.isEmpty(result)) {
			RefundOrderResponse  refundOrderResponse = JsonUtils.jsonToObject(result, RefundOrderResponse.class);
			return refundOrderResponse;
		}
		return null;
	}
	
	
	
	/**
	 * 网关查询状态封签
	 * 
	 * @param billLogTradeNo
	 * @param type   1: 退款查询   2：订单查询
	 * @return
	 */
	private String getQueryPostData(String billLogTradeNo,Integer type) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("merchantNo", Config.MERCHANT_NO);
		if (type.equals(1)) {
			params.put("refundOrderNo", billLogTradeNo);//原交易订单号
		}else if(type.equals(2)){
			params.put("orderNo", billLogTradeNo);//原交易订单号
		}
		StringBuffer signData = new StringBuffer(createLinkString(params));
		String signatureData = createLinkString(params)+"&key="+Config.MERCHANT_PAY_KEY;
		LogUtils.sysErrorLoggerInfo("拉起退款查询，待签名串：" + signatureData);
		signData.append("&signData=").append(MD5Maker.getMD5(signatureData).toLowerCase());
		return signData.toString();
	}
	
	private String refundOrderPostData(RefundOrderParam refundOrderParam){
		Map<String, String> params = new HashMap<String, String>();
		params.put("merchantNo", Config.MERCHANT_NO);
		params.put("orderNo", refundOrderParam.getOrderNo());//原交易订单号
		params.put("refundOrderNo", refundOrderParam.getRefundOrderNo());//退款订单号
		params.put("refundAmount", String.valueOf(refundOrderParam.getRefundAmount())); //退款金额
		params.put("remark", refundOrderParam.getRemark());

		StringBuffer signData = new StringBuffer(createLinkString(params));
		String signatureData = createLinkString(params) + "&key=" + Config.MERCHANT_PAY_KEY;
		LogUtils.sysErrorLoggerInfo("拉起订单退款，待签名串：" + signatureData);
		signData.append("&signData=").append(MD5Maker.getMD5(signatureData).toLowerCase());
		return signData.toString();
	}
	
	private static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            
            if(StringUtils.isBlank(value)) {
            	continue;
            }
            
            prestr = prestr + key + "=" + value + "&";
        }
        
        if(prestr.length() > 0) {
        	prestr = prestr.substring(0, prestr.length() - 1);
        }
        
        return prestr;
    }
	
	public static void main(String[] args) {
		Map<String, String> params = new HashMap<>();
		params.put("key3", "value3");
		params.put("key2", "");
		params.put("key1", "value1");
		System.out.println(createLinkString(params));
	}

}
