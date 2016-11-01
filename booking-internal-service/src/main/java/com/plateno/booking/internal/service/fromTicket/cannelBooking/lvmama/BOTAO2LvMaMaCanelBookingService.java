/*package com.plateno.booking.internal.service.fromTicket.cannelBooking.lvmama;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.plateno.booking.internal.base.pojo.MBill;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.ViewStatusEnum;
import com.plateno.booking.internal.bean.response.custom.CannelBookResponse;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceFailRetryTimes;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceOrder;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceType;
import com.plateno.booking.internal.interceptor.adam.service.IService;
import com.plateno.booking.internal.service.abs.AbsCannelBookingService;
import com.plateno.booking.internal.service.fromTicket.vo.CancelBookingIncomeVo;
import com.plateno.booking.internal.service.log.OrderLogService;


@Component
@ServiceType(BookingConstants.BOTAO_2_LVMAMA_CANCEL_BOOKING)
@ServiceOrder(30)
@ServiceErrorCode(BookingConstants.CODE_ORDER_ERROR)
@ServiceFailRetryTimes(fail = 3,sleep = 100)
public class BOTAO2LvMaMaCanelBookingService extends AbsCannelBookingService implements IService<CancelBookingIncomeVo, CannelBookResponse> {

	@Autowired
	private OrderLogService orderLogService;
	
	@Override
	public void doService(CancelBookingIncomeVo income,ResultVo<CannelBookResponse> output) throws Exception {
		cancelBooking(income, output);
	}

	@Override
	public void doSuccess(CancelBookingIncomeVo income,ResultVo<CannelBookResponse> output) throws Exception {
		if (output.success()) {
			orderLogService.saveGSOrderLog(income.getTradeNo(), BookingConstants.PAY_STATUS_400, "OTA退款申请中", "OTA退款申请中",income.getChannel(), ViewStatusEnum.VIEW_STATUS_REFUNDING.getCode());
		}else{
			orderLogService.saveGSOrderLog(income.getTradeNo(), BookingConstants.PAY_STATUS_402, "OTA退款失败", output.getResultMsg(),income.getChannel(), ViewStatusEnum.VIEW_STATUS_REFUND_FAIL.getCode());
		}
	}

	@Override
	public void doFail(CancelBookingIncomeVo income,ResultVo<CannelBookResponse> output) throws Exception {
		MBill record = new MBill();
		record.setStatus(BookingConstants.PAY_STATUS_402); //设置进入OTA退款申请中
		record.setRefundFail(output.getResultMsg());
		record.setRefundTime(new Date());
		updateBillStatus(income, record, output);
	}

	@Override
	public void doComplate(CancelBookingIncomeVo income,ResultVo<CannelBookResponse> output) throws Exception {}

}
*/