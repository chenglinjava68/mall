/*package com.plateno.booking.internal.service.fromTicket.cannelBooking.lvmama;

import org.springframework.stereotype.Component;

import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.response.custom.CannelBookResponse;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceFailRetryTimes;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceOrder;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceType;
import com.plateno.booking.internal.interceptor.adam.service.IService;
import com.plateno.booking.internal.service.abs.AbsCannelBookingService;
import com.plateno.booking.internal.service.fromTicket.vo.CancelBookingIncomeVo;


@Component
@ServiceType(BookingConstants.BOTAO_2_LVMAMA_CANCEL_BOOKING)
@ServiceOrder(10)
@ServiceErrorCode(BookingConstants.CODE_VERIFY_ERROR)
@ServiceFailRetryTimes()
public class BOTAO2LvMaMaCanelParamVerifyService extends AbsCannelBookingService implements IService<CancelBookingIncomeVo, CannelBookResponse> {

	
	@Override
	public void doService(CancelBookingIncomeVo income,ResultVo<CannelBookResponse> output) throws Exception {
		cancelOrder(income,output);
	}

	@Override
	public void doSuccess(CancelBookingIncomeVo income,ResultVo<CannelBookResponse> output) throws Exception {
		LogUtils.sysErrorLoggerInfo("驴妈妈取消订单参数验证成功");
	}

	@Override
	public void doFail(CancelBookingIncomeVo income,ResultVo<CannelBookResponse> output) throws Exception {
		LogUtils.sysErrorLoggerError(output.getResultMsg(),null);
	}

	@Override
	public void doComplate(CancelBookingIncomeVo income,ResultVo<CannelBookResponse> output) throws Exception {}

}
*/