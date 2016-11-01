/*package com.plateno.booking.internal.service.abs;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.plateno.booking.internal.base.pojo.MBill;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.request.convert.ConvertBookingParam;
import com.plateno.booking.internal.bean.request.convert.ConvertThirdPayParam;
import com.plateno.booking.internal.bean.request.custom.BillLogParam;
import com.plateno.booking.internal.bean.response.gateway.pay.PayQueryResponse;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.common.util.date.DateUtil;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.common.util.redis.RedisUtils;
import com.plateno.booking.internal.email.MailService;
import com.plateno.booking.internal.email.model.EmailObject;
import com.plateno.booking.internal.gateway.PaymentService;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.api.ApiService;
import com.plateno.booking.internal.service.fromTicket.vo.PayTicketIncomeVo;
import com.plateno.booking.internal.service.order.BillService;

public abstract class AbsPayService {

	@Autowired
	private PaymentService paymentService;
	@Autowired
	private BillService billService;
	@Autowired
	private ApiService apiService;
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private MailService mailService;

	
	public void AddPayInfo(PayTicketIncomeVo income, ResultVo<Boolean> result) throws Exception{
		checkAddPayInfo(income,result);
		if (!result.success())
			return;
		updateDB(income);
		//构造参数
		ConvertThirdPayParam payParam = null;
		DataFormat(payParam,income);
		if (payParam == null) {
			result.setData(false);
			result.setResultMsg("获取不到redis的下单数据");
			result.setResultCode(getClass(), BookingConstants.CODE_201014);
			return;
		}
		apiService.Pay(payParam, income.getChannel(), result);
		if (result.success()) {
			result.setData(true);
		}else{
			result.setData(false);
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	private void updateDB(PayTicketIncomeVo income) throws Exception{
		//更新订单状态
		MBill record = new MBill();
		record.setStatus(BookingConstants.PAY_STATUS_300);
		billService.updateBillByTradeNo(income.getTradeNo(), record, null, null);
		//更新订单的支付流水状态
		BillLogParam billLogParam = new BillLogParam();
		billLogParam.setTradeNo(income.getBillLogtradeNo());
		billLogParam.setStatus(BookingConstants.BILL_LOG_SUCCESS);
		billService.updateBillLog(billLogParam);
	}
	
	private void DataFormat(ConvertThirdPayParam param , PayTicketIncomeVo income) throws Exception{
		String strBookingRequestParam = redisUtils.get(param.getOrderNo());
		if(strBookingRequestParam != null){
			ConvertBookingParam bookingRequestParam = JsonUtils.jsonToObject(strBookingRequestParam, ConvertBookingParam.class);
			
			BeanUtils.copyProperties(bookingRequestParam, param);
			param.setReferenceId(income.getReferenceId());
			param.setPartnerOrderId(income.getPartnerOrderId());
		}else{
			param = null;
		}
	}
	
	*//**
	 * 反查支付入账
	 * 
	 * @param addTran
	 * @param result
	 *//*
	private void checkAddPayInfo(PayTicketIncomeVo addTran, ResultVo<Boolean> result) throws Exception {
		PayQueryResponse payQueryResponse = null;
		try {
			payQueryResponse = paymentService.payOrderQuery(addTran.getBillLogtradeNo());
		} catch (Exception e) {
			LogUtils.httpLoggerError(ExceptionUtils.getStackTrace(e), e);
			result.setResultCode(this.getClass(), BookingConstants.CODE_201004);
			result.setResultMsg("支付服务发生异常：" + e);
			result.setData(false);
			throw e;
		}
		if (payQueryResponse == null) {
			String msg = "支付服务查询订单状态无返回结果";
			LogUtils.sysErrorLoggerError(msg,null);
			result.setResultCode(this.getClass(), BookingConstants.CODE_201005);
			result.setResultMsg(msg);
			result.setData(false);
			return;
		}

		if (!BookingConstants.GATEWAY_PAY_SUCCESS_CODE.equals(payQueryResponse.getCode())) {
			String msg = "支付服务查询该订单不是交易成功状态，原因:" + payQueryResponse.getMessage();
			LogUtils.sysErrorLoggerError(msg,null);
			result.setResultCode(this.getClass(), BookingConstants.CODE_201006);
			result.setResultMsg(msg);
			result.setData(false);
			return;
		}
	}
	
	*//**
	 * 发送故障邮件
	 *//*
	protected void sendFaultPayMail(PayTicketIncomeVo income,ResultVo<Boolean> result){
		
		EmailObject eo = new EmailObject();
		eo.setSendTo(new String[] { "dp_trip@platenogroup.com" });
		eo.setSubject("OTA支付失败,告警");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", income.getBillDetails().getMemberId());
		map.put("username", income.getBillDetails().getUsername());
		map.put("mobile", income.getBillDetails().getMobile());
		map.put("tradeNo", income.getBillDetails().getTradeNo());
		map.put("channel", income.getBillDetails().getChannel());
		map.put("visitDate", DateUtil.dateToFormatStr(income.getBillDetails().getVisitDate(),DateUtil.YYYY_MM_DD));
		map.put("createTime", DateUtil.dateToFormatStr(income.getBillDetails().getCreateTime(),DateUtil.YYYY_MM_DD));
		map.put("status", income.getBillDetails().getStatus());
		map.put("errorMsg", result.getResultMsg());
		eo.setContentMap(map);
		
		mailService.sendMimeMailAsyn(eo, "FaultPayTemplate.ftl");
	}
}
*/