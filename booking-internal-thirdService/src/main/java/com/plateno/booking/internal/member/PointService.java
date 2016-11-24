package com.plateno.booking.internal.member;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.request.point.ValueBean;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.www.webservice.service.mebpoint.EPointState;
import com.plateno.www.webservice.service.mebpoint.MebPointServiceSoap;
import com.plateno.www.webservice.service.mebpoint.PointAddInfo;

/**
 * 积分接口
 * 
 * @author user
 * 
 */
@Service
public class PointService {

	private final static Logger logger = Logger.getLogger(PointService.class);

	@Resource
	private MebPointServiceSoap mebPointServiceSoap;

	/**
	 * 赠送积分
	 * 
	 * @param valueb
	 * @return
	 * @throws Exception
	 */
	public int addPoint(ValueBean valueb) throws Exception {
		PointAddInfo pinfo = new PointAddInfo();
		pinfo.setNMebID(valueb.getMebId());
		pinfo.setNBusType(Config.POINT_TYPE);
		pinfo.setSRemark(String.format("微信预订门票赠送积分===>订单:%s", valueb.getTrandNo()));
		pinfo.setSSpecRemark("会员通过微信渠道订门票赠送积分");
		pinfo.setNPoint(valueb.getPointvalue());

		logger.info("发送赠送积分的请求参数结构：" + JsonUtils.toJsonString(pinfo));
		int re = 0;
		int ree = 0;
		try {
			re = mebPointServiceSoap.addPoint(pinfo);
			logger.info(String.format("积分赠送成功：%s,%s", valueb.getMebId(), valueb.getPointvalue()));
		} catch (Exception e) {
			logger.info(valueb.getMebId() + ":" + valueb.getPointvalue() + "," + String.format("积分赠送时,接口发生异常，请联系管理员:%s", e));
		}

		if (re > 0) {
			logger.info(valueb.getMebId() + ":" + valueb.getPointvalue() + ":" + pinfo.getNPoint() + ":推送成功");
		} else {
			logger.info("失败：推送" + valueb.getMebId() + ":" + valueb.getPointvalue());
			ree = 1;
		}
		return ree;
	}

	/**
	 * 扣减积分
	 * 
	 * @param valueb
	 * @return
	 */
	public int minusPoint(ValueBean valueb) {
		PointAddInfo pinfo = new PointAddInfo();
		pinfo.setNMebID(valueb.getMebId());
		pinfo.setNBusType(Config.POINT_TYPE);
		pinfo.setSRemark(String.format("微信预订门票退款扣减积分===>订单:%s",valueb.getTrandNo()));

		pinfo.setSSpecRemark("会员通过微信渠道订门票退款扣减积分");
		pinfo.setNPoint(valueb.getPointvalue());

		int re = 0;
		int ree = 0;
		try {
			re = mebPointServiceSoap.addPoint(pinfo);
			logger.info(String.format("积分扣减成功：%s,%s", valueb.getMebId(),valueb.getPointvalue()));
		} catch (Exception e) {
			logger.info(valueb.getMebId() + ":" + valueb.getPointvalue() + "," + String.format("积分扣减时,接口发生异常，请联系管理员:%s", e));
		}
		if (re > 0) {
			logger.info(valueb.getMebId() + ":" + valueb.getPointvalue() + ":" + pinfo.getNPoint() + ":推送成功");
		} else {
			logger.info("失败：推送" + valueb.getMebId() + ":" + valueb.getPointvalue());
			ree = 1;
		}
		return ree;
	}
	
	
	/**
	 * 扣减积分
	 * 
	 * @param valueb
	 * @return
	 */
	public int mallMinusPoint(ValueBean valueb) {
		PointAddInfo pinfo = new PointAddInfo();
		pinfo.setNMebID(valueb.getMebId());
		pinfo.setNBusType(Config.POINT_TYPE);
		pinfo.setSRemark(String.format("铂物馆"));

		pinfo.setSSpecRemark("会员通过铂物馆购买商品扣减积分");
		pinfo.setNPoint(valueb.getPointvalue());
		
		String jsonString = "";
		try {
			jsonString = JsonUtils.toJsonString(pinfo);
		} catch (IOException e1) {
		}
		
		logger.info(String.format("商城下单扣减积分，参数:%s", jsonString));

		int re = 0;
		int ree = 0;
		try {
			re = mebPointServiceSoap.addPoint(pinfo);
			logger.info(String.format("积分扣减成功：%s,%s", valueb.getMebId(),valueb.getPointvalue()));
		} catch (Exception e) {
			logger.info(valueb.getMebId() + ":" + valueb.getPointvalue() + "," + String.format("积分扣减时,接口发生异常，请联系管理员:%s", e), e);
		}
		if (re > 0) {
			logger.info(valueb.getMebId() + ":" + valueb.getPointvalue() + ":" + pinfo.getNPoint() + ":推送成功");
		} else {
			logger.info("失败：推送" + valueb.getMebId() + ":" + valueb.getPointvalue());
			ree = 1;
		}
		return ree;
	}

	public int getPointSum(Integer mebId){
		int ree = 0;
		try {
			ree = mebPointServiceSoap.getPointSum(mebId, EPointState.USABLE);
		}catch(Exception e){
			logger.info(String.format("积分查询时,接口发生异常，请联系管理员:%s", e));
		}
		
		return ree;
	}
	/**
	 * 返还积分
	 * 
	 * @param valueb
	 * @return
	 */
	public int mallAddPoint(ValueBean valueb) {
		PointAddInfo pinfo = new PointAddInfo();
		pinfo.setNMebID(valueb.getMebId());
		pinfo.setNBusType(Config.POINT_TYPE);
		pinfo.setSRemark(String.format("铂物馆"));

		pinfo.setSSpecRemark("会员通过铂物馆购买商品取消/退款返还积分");
		pinfo.setNPoint(valueb.getPointvalue());
		
		String jsonString = "";
		try {
			jsonString = JsonUtils.toJsonString(pinfo);
		} catch (IOException e1) {
		}
		
		logger.info(String.format("商城返回积分，参数:%s", jsonString));

		int re = 0;
		int ree = 0;
		try {
			re = mebPointServiceSoap.addPoint(pinfo);
			logger.info(String.format("积分返还成功：%s,%s", valueb.getMebId(),valueb.getPointvalue()));
		} catch (Exception e) {
			logger.info(valueb.getMebId() + ":" + valueb.getPointvalue() + "," + String.format("积分扣减时,接口发生异常，请联系管理员:%s", e), e);
		}
		if (re > 0) {
			logger.info(valueb.getMebId() + ":" + valueb.getPointvalue() + ":" + pinfo.getNPoint() + ":推送成功");
		} else {
			logger.info("失败：推送" + valueb.getMebId() + ":" + valueb.getPointvalue());
			ree = 1;
		}
		return ree;
	}
}
