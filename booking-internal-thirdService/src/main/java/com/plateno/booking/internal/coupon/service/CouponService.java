package com.plateno.booking.internal.coupon.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.common.util.http.HttpUtils;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.coupon.vo.BaseResponse;
import com.plateno.booking.internal.coupon.vo.CancelParam;
import com.plateno.booking.internal.coupon.vo.CancelResponse;
import com.plateno.booking.internal.coupon.vo.CouponInfo;
import com.plateno.booking.internal.coupon.vo.QueryParam;
import com.plateno.booking.internal.coupon.vo.QueryResponse;
import com.plateno.booking.internal.coupon.vo.UseParam;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;

/**
 * 优惠券交互
 * @author mogt
 * @date 2016年12月27日
 */
@Service
public class CouponService {
	
	protected final static Logger logger = LoggerFactory.getLogger(CouponService.class);
	
	/**
	 * 查询优惠券
	 * @param param
	 * @return
	 */
	public ResultVo<QueryResponse> queryCoupon(QueryParam param) {
		
		try {
			
			String paramJson = JSON.toJSONStringWithDateFormat(param, "yyyy-MM-dd HH:mm:ss");
			logger.info("查询优惠券参数:{}", paramJson);
			
			String url = Config.COUPON_SERVER_URL + "/couponServices/queryCoupons";
			HashMap<String, String> headers = new HashMap<>();
			headers.put("X-AUTH-HEADER", Config.COUPON_SERVER_KEY);
			
			String response = HttpUtils.postJson(url, paramJson, headers);
			
			logger.info("查询优惠券信息返回内容:{}", response);
			
			//解析内容返回
			QueryResponse queryResponse = JsonUtils.jsonToObject(response, QueryResponse.class);
			
			logger.info("查询优惠券,解析参数返回参数:{}", queryResponse);
			
			if(queryResponse == null) {
				logger.error("查询优惠券信息失败, 解析返回参数失败");
				return new ResultVo<>(ResultCode.FAILURE, null, "查询优惠券失败，解析返回参数失败");
			}
			
			if(queryResponse.getStatus() != null && queryResponse.getStatus() != 0) {
				logger.error("查询优惠券信息失败, status:{}, reason:{}", queryResponse.getStatus(), queryResponse.getReason());
				return new ResultVo<>(ResultCode.FAILURE, null, "查询优惠券失败，响应吗：" + queryResponse.getStatus() + "， 错误信息：" + queryResponse.getReason());
			}
			
			if(queryResponse.getCouponInfo() == null) {
				queryResponse.setCouponInfo(new ArrayList<CouponInfo>(0));
			}
			
			return new ResultVo<>(ResultCode.SUCCESS, queryResponse);
			
		} catch (Exception e) {
			logger.error("查询优惠券发生异常", e);
			return new ResultVo<>(ResultCode.FAILURE, null, "查询优惠券异常:" + e.getMessage());
		}
	}
	
	
	/**
	 * 使用优惠券
	 * @param param
	 * @return
	 */
	public ResultVo<BaseResponse> useCoupon(UseParam param) {
		
		try {
			
			String paramJson = JSON.toJSONStringWithDateFormat(param, "yyyy-MM-dd HH:mm:ss");
			logger.info("使用优惠券参数:{}", paramJson);
			
			String url = Config.COUPON_SERVER_URL + "/couponServices/useCoupon";
			HashMap<String, String> headers = new HashMap<>();
			headers.put("X-AUTH-HEADER", Config.COUPON_SERVER_KEY);
			
			try {
				String response = HttpUtils.postJson(url, paramJson, headers);
				
				logger.info("使用优惠券返回内容:{}", response);
				
				//解析内容返回
				BaseResponse baseResponse = JsonUtils.jsonToObject(response, BaseResponse.class);
				
				logger.info("使用优惠券,解析参数返回参数:{}", baseResponse);
				
				if(baseResponse == null) {
					logger.error("使用优惠券失败, 解析返回参数失败");
					return new ResultVo<>(ResultCode.FAILURE, null, "使用优惠券失败，解析返回参数失败");
				}
				
				if(baseResponse.getStatus() == null || baseResponse.getStatus() != 0) {
					logger.error("使用优惠券失败, status:{}, reason:{}", baseResponse.getStatus(), baseResponse.getReason());
					return new ResultVo<>(ResultCode.FAILURE, null, "使用优惠券失败，响应码：" + baseResponse.getStatus() + "， 错误信息：" + baseResponse.getReason());
				}
				
				return new ResultVo<>(ResultCode.SUCCESS, baseResponse);
			} catch (Exception e) {
				
				logger.error("使用优惠券，调用服务后异常，memberId:{}, couponId:{}, orderNo:{}", param.getMebId(), param.getCouponId(), param.getOrderCode());
				LogUtils.DISPERSED_ERROR_LOGGER.error("使用优惠券，调用服务后异常，memberId:{}, couponId:{}, orderNo:{}", param.getMebId(), param.getCouponId(), param.getOrderCode());
				
				throw e;
			}
			
		} catch (Exception e) {
			logger.error("使用优惠券异常", e);
			return new ResultVo<>(ResultCode.FAILURE, null, "使用优惠券异常:" + e.getMessage());
		}
	}
	
	
	/**
	 * 返还优惠券
	 * @param param
	 * @return
	 */
	public ResultVo<CancelResponse> cancelCoupon(CancelParam param) {
		
		try {
			
			String paramJson = JSON.toJSONStringWithDateFormat(param, "yyyy-MM-dd HH:mm:ss");
			logger.info("返还优惠券参数:{}", paramJson);
			
			String url = Config.COUPON_SERVER_URL + "/couponServices/cancelCoupon";
			HashMap<String, String> headers = new HashMap<>();
			headers.put("X-AUTH-HEADER", Config.COUPON_SERVER_KEY);
			
			try {
				String response = HttpUtils.postJson(url, paramJson, headers);
				
				logger.info("返还优惠券返回内容:{}", response);
				
				//解析内容返回
				CancelResponse cancelResponse = JsonUtils.jsonToObject(response, CancelResponse.class);
				
				logger.info("返还优惠券,解析参数返回参数:{}", cancelResponse);
				
				if(cancelResponse == null) {
					logger.error("返还优惠券失败, 解析返回参数失败");
					return new ResultVo<>(ResultCode.FAILURE, null, "返还优惠券失败，解析返回参数失败");
				}
				
				if(cancelResponse.getStatus() == null || cancelResponse.getStatus() != 0) {
					logger.error("返还优惠券失败, status:{}, reason:{}", cancelResponse.getStatus(), cancelResponse.getReason());
					return new ResultVo<>(ResultCode.FAILURE, null, "返还优惠券失败，响应吗：" + cancelResponse.getStatus() + "， 错误信息：" + cancelResponse.getReason());
				}
				
				return new ResultVo<>(ResultCode.SUCCESS, cancelResponse);
			} catch (Exception e) {
				
				logger.error("返还优惠券，调用服务后异常，memberId:{}, couponId:{}", param.getMebId(), param.getCouponId());
				LogUtils.DISPERSED_ERROR_LOGGER.error("返还优惠券，调用服务后异常，memberId:{}, couponId:{}", param.getMebId(), param.getCouponId());
				
				throw e;
			}
			
		} catch (Exception e) {
			logger.error("返还优惠券异常", e);
			return new ResultVo<>(ResultCode.FAILURE, null, "返还优惠券异常:" + e.getMessage());
		}
	}
}
