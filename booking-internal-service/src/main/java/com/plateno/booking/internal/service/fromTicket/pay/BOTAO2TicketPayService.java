/*package com.plateno.booking.internal.service.fromTicket.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.plateno.booking.internal.base.pojo.MBill;
import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.ViewStatusEnum;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceFailRetryTimes;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceOrder;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceType;
import com.plateno.booking.internal.interceptor.adam.service.IService;
import com.plateno.booking.internal.service.abs.AbsPayService;
import com.plateno.booking.internal.service.fromTicket.vo.PayTicketIncomeVo;
import com.plateno.booking.internal.service.log.OrderLogService;
import com.plateno.booking.internal.service.order.BillService;
import com.plateno.booking.internal.wechat.WechatTemplementSerivce;


@Component
@ServiceType(BookingConstants.BOTAO_2_TICKET_PAY)
@ServiceOrder(10)
@ServiceErrorCode(BookingConstants.CODE_ORDER_ERROR)
@ServiceFailRetryTimes(fail = 2,sleep = 100)
public class BOTAO2TicketPayService extends AbsPayService implements IService<PayTicketIncomeVo, Boolean> {

	@Autowired
	private WechatTemplementSerivce wechatTemplementSerivce;
	@Autowired
	private BillService billService;
	@Autowired
	private OrderLogService orderLogService;
	
	@Override
	public void doService(PayTicketIncomeVo income,ResultVo<Boolean> output) throws Exception {
		LogUtils.sysErrorLoggerInfo("OTA支付入参" + JsonUtils.toJsonString(income));
		orderLogService.saveGSOrderLog(income.getTradeNo(), BookingConstants.PAY_STATUS_201, "网关支付成功", "网关支付成功,支付金额:" + income.getAmount() + "分 ",income.getChannel(), ViewStatusEnum.VIEW_STATUS_PAYING.getCode());
		AddPayInfo(income, output);
	}

	@Override
	public void doSuccess(PayTicketIncomeVo income,ResultVo<Boolean> output) throws Exception {
		if (output.success() && output.getData()) {
			//如果不是要出发,更新订单状态
			if (!income.getChannel().equals(Config.CHANNEL_YCF)) {
				MBill record = new MBill();
				record.setStatus(301);
				billService.updateBillByTradeNo(income.getTradeNo(), record, null, null);
			}
			//发送微信模版
			wechatTemplementSerivce.sendWechatTemplement(income.getBillDetails());
			orderLogService.saveGSOrderLog(income.getTradeNo(), BookingConstants.PAY_STATUS_301, "OTA支付成功", "订单支付成功，支付金额:" + income.getAmount() + "分 ",income.getChannel(), ViewStatusEnum.VIEW_STATUS_PAY_UNUSE.getCode());
		}else{
			orderLogService.saveGSOrderLog(income.getTradeNo(), BookingConstants.PAY_STATUS_302, "OTA支付失败", output.getResultMsg(),income.getChannel(), ViewStatusEnum.VIEW_STATUS_PAYING.getCode());
			sendFaultPayMail(income, output);
		}
	}

	@Override
	public void doFail(PayTicketIncomeVo income,ResultVo<Boolean> output) throws Exception {
		LogUtils.sysErrorLoggerError(output.getResultMsg(),null);
		orderLogService.saveGSOrderLog(income.getTradeNo(), BookingConstants.PAY_STATUS_302, "OTA支付失败", output.getResultMsg(),income.getChannel(), ViewStatusEnum.VIEW_STATUS_PAYING.getCode());
		MBill record = new MBill();
		record.setStatus(302);
		billService.updateBillByTradeNo(income.getTradeNo(), record, null, null);
		sendFaultPayMail(income, output);
	}

	@Override
	public void doComplate(PayTicketIncomeVo income,ResultVo<Boolean> output) throws Exception {}

}
*/