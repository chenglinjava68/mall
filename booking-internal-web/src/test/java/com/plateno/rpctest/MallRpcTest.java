package com.plateno.rpctest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.plateno.booking.internal.base.model.SelectOrderParam;
import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.common.util.http.HttpUtils;
import com.plateno.booking.internal.common.util.json.JsonUtils;

/**
 * 测试rpc接口
 * @author mogt
 * @date 2016年10月28日
 */
public class MallRpcTest {
	
	private final static String BASE_URL = "http://localhost:9970/booking-internal-web";
	
	/**
	 * 下单
	 * @throws IOException
	 */
	@Test
	public void testAddBooking() throws IOException {
		MAddBookingParam param = new MAddBookingParam();
		
		String url = BASE_URL+ "/mbookingService/addBooking";
		String response = HttpUtils.httpPostRequest(url, JsonUtils.toJsonString(param));
		
		System.out.println("返 结果：" + response);
	}
	
	/**
	 *  拉起支付
	 * @throws IOException
	 */
	@Test
	public void testPullerPay() throws IOException {
		MOrderParam param = new MOrderParam();
		param.setOrderNo("O1474959953609872686");
		param.setMemberId(121212);
		
		String url = BASE_URL+ "/mOrderService/pullerPay";
		String response = HttpUtils.httpPostRequest(url, JsonUtils.toJsonString(param));
		
		System.out.println("返 结果：" + response);
	}
	
	/**
	 * 支付网关回调
	 * @throws IOException
	 */
	@Test
	public void testPayNotifyUrl() throws IOException {
		Map<String, String> param = new HashMap<>();
		param.put("code", "2000");
		param.put("orderNo", "L1477622679267793168");
		param.put("signData", "2323232");
		param.put("message", "支付成功");
		param.put("referenceId", "ewew3232332");
		param.put("orderAmount", "20000");
		
		String url = BASE_URL+ "/mbookingService/payNotifyUrl";
		String response = HttpUtils.httpGetRequest(url, param);
		
		System.out.println("返 结果：" + response);
	}
	
	
	/**
	 * 拒绝退款
	 * @throws IOException
	 */
	@Test
	public void testConsentRefund() throws IOException {
		MOrderParam param = new MOrderParam();
		param.setOrderNo("O1474959953609872686");
		
		String url = BASE_URL+ "/mOrderService/consentRefund";
		String response = HttpUtils.httpPostRequest(url, JsonUtils.toJsonString(param));
		
		System.out.println("返 结果：" + response);
	}
	
	/**
	 *  拉起支付
	 * @throws IOException
	 */
	@Test
	public void testQueryOrderByPage() throws IOException {
		SelectOrderParam param = new SelectOrderParam();
		param.setChannelId(1);
		param.setRequstPlatenoform(3);
		List<Integer> statusList = new ArrayList<>();
		statusList.add(3);
		param.setStatusList(statusList);
		
		String url = BASE_URL+ "/mOrderService/queryOrderByPage";
		String response = HttpUtils.httpPostRequest(url, JsonUtils.toJsonString(param));
		
		System.out.println("返 结果：" + response);
	}
}
