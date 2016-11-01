/*package com.plateno.booking.internal.service.fromTicket.refund;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

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
import com.plateno.booking.internal.runnable.SMSRunnable;
import com.plateno.booking.internal.service.abs.AbsRefundService;
import com.plateno.booking.internal.service.fromTicket.vo.RefundAccountIncomeVo;
import com.plateno.booking.internal.service.log.OrderLogService;


@Component
@ServiceType(BookingConstants.BOTAO_2_TICKET_REFUND)
@ServiceOrder(10)
@ServiceErrorCode(BookingConstants.CODE_ORDER_ERROR)
@ServiceFailRetryTimes(fail = 2,sleep = 100)
public class BOTAO2TicketRefundService extends AbsRefundService implements IService<RefundAccountIncomeVo, Boolean>{

	@Autowired
	private OrderLogService orderLogService;
	@Autowired
	public TaskExecutor taskExecutor;

	@Override
	public void doService(RefundAccountIncomeVo income, ResultVo<Boolean> output)throws Exception {
		LogUtils.sysErrorLoggerInfo("退款入参" + JsonUtils.toJsonString(income));
		orderLogService.saveGSOrderLog(income.getTradeNo(), BookingConstants.PAY_STATUS_401, "OTA退款成功", "OTA订单退款成功,退款金额:" + income.getRefundDetailParam().getRefundPrice() + "分 ",income.getChannel(), ViewStatusEnum.VIEW_STATUS_REFUND.getCode());
		refundPayInfo(income, output);
	}

	@Override
	public void doSuccess(RefundAccountIncomeVo income, ResultVo<Boolean> output)throws Exception {
		if (output.getData()) {
			//发短信接口
			taskExecutor.execute(new SMSRunnable(income));
			orderLogService.saveGSOrderLog(income.getTradeNo(), BookingConstants.PAY_STATUS_500, "网关退款中", "网关退款处理中,退款金额:" + income.getRefundDetailParam().getRefundPrice() + "分 ",income.getChannel(), ViewStatusEnum.VIEW_STATUS_REFUNDING.getCode());
		}else{
			orderLogService.saveGSOrderLog(income.getTradeNo(), BookingConstants.PAY_STATUS_502, "网关退款失败", output.getResultMsg(), income.getChannel(),ViewStatusEnum.VIEW_STATUS_REFUND_FAIL.getCode());
		}
	}

	@Override
	public void doFail(RefundAccountIncomeVo income, ResultVo<Boolean> output)throws Exception {
		LogUtils.sysErrorLoggerError(output.getResultMsg(),null);
		orderLogService.saveGSOrderLog(income.getTradeNo(), BookingConstants.PAY_STATUS_502, "网关退款失败", output.getResultMsg(), income.getChannel(),ViewStatusEnum.VIEW_STATUS_REFUND_FAIL.getCode());
	}

	@Override
	public void doComplate(RefundAccountIncomeVo income, ResultVo<Boolean> output)throws Exception {}
}
*/