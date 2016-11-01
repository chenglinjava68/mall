/**
 * 
 */
package com.plateno.booking.internal.service.abs;

import java.io.IOException;

import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.fromTicket.vo.MAddBookingIncomeVo;

/**
 * @author user
 *
 */
public abstract class AbsParamVerifyService {

	protected void verifyIncome(MAddBookingIncomeVo income,ResultVo<?> output) throws IOException{
		MAddBookingParam addBookingParam = income.getAddBookingParam();
		if (addBookingParam == null) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("下单失败,下单对象不能为空");
			return;
		}
		if (addBookingParam.getGoodsId() == null || addBookingParam.getGoodsId() <= 0) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("下单失败,商品ID不能为空");
			return;
		}
		if (addBookingParam.getQuantity() <= 0) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("下单失败,数量必须大于0");
			return;
		}
		if (addBookingParam.getTotalAmount() <= 0) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("下单失败,订单总价必须大于0");
			return;
		}
		if (addBookingParam.getPlatformId() <= 0) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("下单失败,平台来源,不能为空");
			return;
		}
		if (addBookingParam.getResource() <=0) {
			output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			output.setResultMsg("下单失败,订单来源不能为空");
			return;
		}
	}

	
}
