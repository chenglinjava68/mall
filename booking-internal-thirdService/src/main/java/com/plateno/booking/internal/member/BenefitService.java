package com.plateno.booking.internal.member;

import java.util.HashMap;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.springframework.stereotype.Service;
import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.Bendfit.DeGrowValueBean;
import com.plateno.booking.internal.bean.request.Bendfit.GrowBean;
import com.plateno.booking.internal.bean.request.Bendfit.GrowSumReqs;
import com.plateno.booking.internal.bean.request.Bendfit.GrowValueBean;
import com.plateno.booking.internal.bean.request.Bendfit.GrowValueReq;
import com.plateno.booking.internal.bean.request.point.ValueBean;
import com.plateno.booking.internal.common.util.http.HttpUtils;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.www.webservice.service.mebpoint.MebPointServiceSoap;

/**
 * 成长值接口
 * 
 * @author user
 *
 */
@Service
public class BenefitService {

	private final static Logger logger = Logger.getLogger(BenefitService.class);
	
	@Resource
	private MebPointServiceSoap mebPointServiceSoap;
	
	
	/**
	 * 
	 * @Description: 赠送会员成长值
	 * @param @param valueb
	 * @param @return
	 * @param @throws OrderException   
	 * @return int  
	 * @throws
	 * @author yokoboy
	 * @date 2016年5月16日
	 */
	public int addGrowValue(ValueBean valueb) throws OrderException {
		GrowValueBean gv=new GrowValueBean();
		GrowValueReq req = new GrowValueReq();
		req.setGrowTypeId(Config.GROW_TYPE);
		req.setMebId(valueb.getMebId());
		req.setGrowValue(valueb.getGrowValue());
		req.setRemark(String.format("微信预订门票赠送===>订单:%s", valueb.getTrandNo())); 
		req.setSpecRemark("会员通过微信渠道预订门票赠送成长值");
		gv.setInGrowValueReq(req);
		String response = "";
		Integer keyId = 0;
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("x-auth-header", Config.MEMBERGROW_X_AUTH_HEADER);
		try {
			String url = Config.MEMBER_GROWVALUE_URL + "increaseGrowValue";
			logger.info(String.format("发起会员成长值赠送的参数:%s", JsonUtils.toJsonString(gv)));
			response = HttpUtils.httpPostRequest(url,JsonUtils.toJsonString(gv), headers);
			logger.info(String.format("底层会员成长值赠送数据:%s", response));
			if (response != null) {
				JsonNode responseNode = JsonUtils.parseJson(response);
				Integer resultCode = responseNode.path("status").asInt();
				if (resultCode == BookingConstants.GROWVALUE_SUCCESS_CODE) {
					keyId = responseNode.path("keyid").asInt();
				} else {
					throw new OrderException("系统故障");
				}
			}
		} catch (Exception e) {
			logger.error(String.format("底层会员成长值赠送数据,%s", response), e);
			throw new OrderException("系统故障");
		}
		return keyId;
	}
	
	/**
	 * 
	 * @Description: 扣减会员成长值
	 * @param @param valueb
	 * @param @return
	 * @param @throws OrderException   
	 * @return int  
	 * @throws
	 * @author yokoboy
	 * @date 2016年5月16日
	 */
	public int deCreaseGrowValue(ValueBean valueb) throws OrderException {
		DeGrowValueBean gv=new DeGrowValueBean();
		GrowValueReq req = new GrowValueReq();
		req.setGrowTypeId(Config.GROW_TYPE);
		req.setMebId(valueb.getMebId());
		req.setGrowValue(valueb.getGrowValue());
		req.setRemark(String.format("微信预订门票扣减===>订单:%s", valueb.getTrandNo())); 
		req.setSpecRemark("会员通过微信渠道预订门票退款扣减成长值");
		gv.setDeGrowValueReq(req);
		String response = "";
		Integer keyId = 0;
		HashMap<String, String> headers = new HashMap<String, String>();
		logger.info("会员剩余成长值为:====>"+getMebGrowValue(valueb.getMebId()));
		headers.put("x-auth-header", Config.MEMBERGROW_X_AUTH_HEADER);
		if(getMebGrowValue(valueb.getMebId())<valueb.getGrowValue()){
			logger.info("会员剩余成长值不够扣减，扣减失败!");
			return 0;
		}
		try {
			String url = Config.MEMBER_GROWVALUE_URL + "decreaseGrowValue";
			logger.info(String.format("发起会员成长值扣减的参数:%s", JsonUtils.toJsonString(gv)));
			response = HttpUtils.httpPostRequest(url,JsonUtils.toJsonString(gv), headers);
			logger.info(String.format("底层会员成长值扣减数据:%s", response));
			if (response != null) {
				JsonNode responseNode = JsonUtils.parseJson(response);
				Integer resultCode = responseNode.path("status").asInt();
				if (resultCode == BookingConstants.GROWVALUE_SUCCESS_CODE) {
					keyId = responseNode.path("keyid").asInt();
				} else {
					throw new OrderException("系统故障");
				}
			}
		} catch (Exception e) {
			logger.error(String.format("底层会员成长值扣减数据,%s", response), e);
			throw new OrderException("系统故障");
		}
		return keyId;
	}
	
	/**
	 * 查询会员成长值
	 * 
	 * @param mebId
	 * @return
	 * @throws OrderException
	 */
	public int getMebGrowValue(Integer mebId) throws OrderException {
		String response = "";
		GrowBean growBean=new GrowBean();
		GrowSumReqs req=new GrowSumReqs();
		req.setMebId(mebId);
		growBean.setGrowSumReqs(req);
		
		HashMap<String, String> headers = new HashMap<String, String>();
		headers.put("x-auth-header", Config.MEMBERGROW_X_AUTH_HEADER);
		try {
			String url = Config.MEMBER_GROWVALUE_URL+ "queryGrowSum";
			logger.info(String.format("发起获取会员成长值的参数:%s", JsonUtils.toJsonString(growBean)));
			response = HttpUtils.httpPostRequest(url,JsonUtils.toJsonString(growBean), headers);
			logger.info(String.format("底层预定返回会员成长值数据:%s", response));
			if (response != null) {
				JsonNode responseNode = JsonUtils.parseJson(response);
				if(!responseNode.path("GrowSumResp").isNull()){
					if (responseNode.path("GrowSumResp").path("status").asInt() == BookingConstants.GROWVALUE_SUCCESS_CODE) {
						Integer value = responseNode.path("GrowSumResp").path("totalGrowValue").asInt();
						return value;
					} else {
						throw new OrderException("系统故障");
					}
				}else{
					throw new OrderException("系统故障");
				}	
			}
		} catch (Exception e) {
			logger.error(String.format("底层预定返回会员成长值数据,%s", response), e);
			throw new OrderException("系统故障");
		}
		return 0;
	}
	
}
