package com.plateno.booking.internal.runnable;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.bean.contants.ConstEnum;
import com.plateno.booking.internal.common.util.number.AmountUtils;
import com.plateno.booking.internal.dao.pojo.sms.SmsMessage;
import com.plateno.booking.internal.service.fromTicket.vo.RefundAccountIncomeVo;
import com.plateno.booking.internal.sms.SMSSendService;
import com.plateno.booking.internal.sms.model.SmsMessageReq;

/**
 * 发短信callback
 * 
 * @author user
 *
 */
public class SMSRunnable implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	/*@Autowired
	private BillMapper billMapper;
	@Autowired
	private SMSSendService sendService;
	
	private RefundAccountIncomeVo incomeVo;
	
	public SMSRunnable(RefundAccountIncomeVo refundAccountIncomeVo){
		this.incomeVo = refundAccountIncomeVo;
	}

	@Override
	public void run() {
		if (!incomeVo.getChannel().equals(ConstEnum.ChannelEnum.YAOCHUFA.getChannel()))
			return;
		
		SmsMessage message = billMapper.getSmsMessage(incomeVo.getOrderNo());
		SmsMessageReq messageReq = new SmsMessageReq();
		messageReq.setPhone(message.getPhone());
		Map<String, String> params = new HashMap<String, String>();
		try {
			if(incomeVo.getRefundDetailParam().getCode().equals(BookingResultCodeContants.YCF_REFUND_SUCCESS_CODE)){
				//运算退款金额
				Integer money = incomeVo.getMoney() - incomeVo.getRefundDetailParam().getRefundCharge();
				if (incomeVo.getStatus().equals(BookingResultCodeContants.PAY_STATUS_400)) {//400 退款短信
					messageReq.setType(Integer.parseInt(Config.SMS_SERVICE_TEMPLATE_FOUR));
					params.put("order", message.getTradeNo());
					params.put("refundAmount", AmountUtils.changeF2Y(money.toString()));
					params.put("handlingCharge", AmountUtils.changeF2Y(incomeVo.getRefundDetailParam().getRefundCharge().toString()));
				} else if (incomeVo.getStatus().equals(BookingResultCodeContants.PAY_STATUS_303)) {//303出票失败短信
					messageReq.setType(Integer.parseInt(Config.SMS_SERVICE_TEMPLATE_FIVE));
					params.put("travelName", message.getTravelName());
					params.put("commodityName", message.getCommodityName());
					params.put("refundAmount", AmountUtils.changeF2Y(money.toString()));
				}
				messageReq.setParams(params);
				sendService.sendMessage(messageReq);
			}else if(incomeVo.getRefundDetailParam().getCode().equals(BookingResultCodeContants.YCF_REFUND_ERROR_CODE)){
				params.put("code", message.getTradeNo());
				if(StringUtils.isNotBlank(incomeVo.getRefundDetailParam().getRemark())) {
					params.put("reason", incomeVo.getRefundDetailParam().getRemark());
				} else {
					params.put("reason", "要出发拒绝退款.");
				}
				messageReq.setType(Integer.parseInt(Config.SMS_SERVICE_TEMPLATE_SIX));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sendService.sendMessage(messageReq);
	}*/
}
