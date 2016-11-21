package com.plateno.booking.internal.service.fromTicket.addBooking.botao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.response.custom.MAddBookResponse;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceFailRetryTimes;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceOrder;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceType;
import com.plateno.booking.internal.interceptor.adam.service.IService;
import com.plateno.booking.internal.service.abs.AbsParamVerifyService;
import com.plateno.booking.internal.service.fromTicket.vo.MAddBookingIncomeVo;


/**
 * 预定下单参数校验service
 * 
 * @author user
 *
 */
@Component
@ServiceType(BookingConstants.BOTAO_MALL_BOTAO_ADD_BOOKING)
@ServiceOrder(10)
@ServiceErrorCode(BookingConstants.CODE_VERIFY_ERROR)
@ServiceFailRetryTimes()
public class BOTAOMallParamVerifyService extends AbsParamVerifyService implements IService<MAddBookingIncomeVo, MAddBookResponse>{
	
	protected final static Logger logger = LoggerFactory.getLogger(BOTAOMallParamVerifyService.class);

	/* (non-Javadoc)
	 * @see com.plateno.booking.internal.interceptor.adam.service.IService#doService(java.lang.Object, com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo)
	 * 
	 * 业务执行主体
	 */
	@Override
	public void doService(MAddBookingIncomeVo income,ResultVo<MAddBookResponse> output) throws Exception {
		//LogUtils.sysLoggerInfo("入参" + JsonUtils.toJsonString(income));
		logger.info("入参:{}", JsonUtils.toJsonString(income));
		verifyIncome(income, output);
	}

	/* (non-Javadoc)
	 * @see com.plateno.booking.internal.interceptor.adam.service.IService#doSuccess(java.lang.Object, com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo)
	 * 
	 * 业务执行后成功的主体
	 */
	@Override
	public void doSuccess(MAddBookingIncomeVo income,ResultVo<MAddBookResponse> output) throws Exception {
		//LogUtils.sysLoggerInfo("商城下单业务参数校验通过");
		logger.info("商城下单业务参数校验通过");
	}

	/* (non-Javadoc)
	 * @see com.plateno.booking.internal.interceptor.adam.service.IService#doFail(java.lang.Object, com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo)
	 * 
	 * 业务执行后失败的主体
	 */
	@Override
	public void doFail(MAddBookingIncomeVo income,ResultVo<MAddBookResponse> output) throws Exception {
		//LogUtils.sysErrorLoggerError(output.getResultMsg(),null);
		logger.error("" + output);
	}

	/* (non-Javadoc)
	 * @see com.plateno.booking.internal.interceptor.adam.service.IService#doComplate(java.lang.Object, com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo)
	 * 
	 * 业务最后执行的操作实体
	 */
	@Override
	public void doComplate(MAddBookingIncomeVo income,ResultVo<MAddBookResponse> output) throws Exception {}

}
