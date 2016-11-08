package com.plateno.booking.internal.service.fromTicket.addBooking.botao;

import org.springframework.stereotype.Component;

import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.response.custom.MAddBookResponse;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceFailRetryTimes;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceOrder;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceType;
import com.plateno.booking.internal.interceptor.adam.service.IService;
import com.plateno.booking.internal.service.abs.MAbsAddOrderService;
import com.plateno.booking.internal.service.fromTicket.vo.MAddBookingIncomeVo;


@Component
@ServiceType(BookingConstants.BOTAO_MALL_BOTAO_ADD_BOOKING)
@ServiceOrder(20)
@ServiceErrorCode(BookingConstants.CODE_OTA_BOOK_ERROR)
@ServiceFailRetryTimes(fail = 3, sleep = 100)
public class BOTAOMallValidateService extends MAbsAddOrderService implements IService<MAddBookingIncomeVo, MAddBookResponse> {
	

	@Override
	public void doService(MAddBookingIncomeVo income,ResultVo<MAddBookResponse> output) throws Exception {
		validateBooking(income,output);
	}

	@Override
	public void doSuccess(MAddBookingIncomeVo income,ResultVo<MAddBookResponse> output) throws Exception {
		LogUtils.sysLoggerInfo("商城订单入参校验成功");
	}

	@Override
	public void doFail(MAddBookingIncomeVo income,ResultVo<MAddBookResponse> output) throws Exception {
		LogUtils.sysErrorLoggerError("商城订单入参校验失败,失败原因：" + output.getResultMsg(),null);
	}

	@Override
	public void doComplate(MAddBookingIncomeVo income,ResultVo<MAddBookResponse> output) throws Exception {}

	

}
