package com.plateno.booking.internal.service.abs;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.request.gateway.RefundOrderParam;
import com.plateno.booking.internal.bean.response.gateway.refund.RefundOrderResponse;
import com.plateno.booking.internal.bean.response.gateway.refund.RefundQueryResponse;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.common.util.number.StringUtil;
import com.plateno.booking.internal.gateway.PaymentService;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.fromTicket.vo.RefundAccountIncomeVo;

public abstract class AbsRefundService {
	/*
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private BillService billService;
	
	*//**
	 * @param refundAccountIncomeVo
	 * @param result
	 * @throws Exception
	 *//*
	protected void refundPayInfo(RefundAccountIncomeVo refundAccountIncomeVo, ResultVo<Boolean> result) throws Exception {
		checkRefundPayInfo(refundAccountIncomeVo, result);
		if (!result.success())
			return;
		MBill record = new MBill();
		record.setStatus(500);	//设置订单状态为网关退款申请中
		billService.updateBillByTradeNo(refundAccountIncomeVo.getTradeNo(), record, null, null);
		String refundTradeNo = StringUtil.getCurrentAndRamobe("L");
		//计算退款金额
		Integer money = refundAccountIncomeVo.getMoney() - refundAccountIncomeVo.getRefundDetailParam().getRefundCharge();
		//创建支付流水
		billService.ctreateRefundBillLog(refundTradeNo, money, refundAccountIncomeVo.getRefundDetailParam().getRefundCharge(), refundAccountIncomeVo.getBillId());
		
		RefundOrderParam refundOrderParam = new RefundOrderParam();
		refundOrderParam.setOrderNo(refundAccountIncomeVo.getBillLogTradeNo());
		refundOrderParam.setRefundAmount(money);
		refundOrderParam.setRefundOrderNo(refundTradeNo);
		refundOrderParam.setRemark("旅行票务底层预定服务,发起退款");
		RefundOrderResponse refundOrderResponse = null;
		try {
			refundOrderResponse = paymentService.refundOrder(refundOrderParam);
		} catch (Exception e) {
			LogUtils.sysErrorLoggerError(ExceptionUtils.getStackTrace(e), e);
			result.setResultCode(getClass(), BookingConstants.CODE_201007);
			result.setResultMsg("退款接口错误：" + e);
			result.setData(false);
			return;
		}
		if (null == refundOrderResponse || !BookingConstants.GATEWAY_PAY_SUCCESS_CODE.equals(refundOrderResponse.getCode())) {
			String msg = "退款不成功 code:";
			if (null == refundOrderResponse) {
				msg = msg + "refundOrderResponse为空";
			} else {
				msg = msg + refundOrderResponse.getCode();
			}
			msg = msg + " msg:" + refundOrderResponse.getMessage();
			LogUtils.sysErrorLoggerError(msg,null);
			result.setResultCode(getClass(), BookingConstants.CODE_201008);
			result.setData(false);
			result.setResultMsg(msg);
			return;
		}
	}
	
	
	*//**
	 * 检查退款入账
	 * 
	 * @param addTran
	 * @param result
	 *//*
	private void checkRefundPayInfo(RefundAccountIncomeVo addTran, ResultVo<Boolean> result) throws Exception {
		RefundQueryResponse refundQueryResponse = null;
		try {
			refundQueryResponse = paymentService.refundOrderQuery(addTran.getBillLogTradeNo());
		} catch (Exception e) {
			LogUtils.httpLoggerError(ExceptionUtils.getStackTrace(e), e);
			result.setResultCode(this.getClass(), BookingConstants.CODE_201001);
			result.setResultMsg("支付服务发生异常：" + e);
			result.setData(false);
			throw e;
		}
		if (refundQueryResponse == null) {
			String msg = "支付服务退款查询订单状态无返回结果";
			LogUtils.sysErrorLoggerError(msg,null);
			result.setResultCode(this.getClass(), BookingConstants.CODE_201002);
			result.setResultMsg(msg);
			result.setData(false);
			return;
		}
		if (!BookingConstants.GATEWAY_REFUND_SUCCESS_CODE.equals(refundQueryResponse.getCode())) {
			String msg = "支付服务查询该订单不是交易成功状态，原因:" + refundQueryResponse.getMessage();
			LogUtils.sysErrorLoggerError(msg,null);
			result.setResultCode(this.getClass(), BookingConstants.CODE_201003);
			result.setResultMsg(msg);
			result.setData(false);
			return;
		}
	}*/

}
