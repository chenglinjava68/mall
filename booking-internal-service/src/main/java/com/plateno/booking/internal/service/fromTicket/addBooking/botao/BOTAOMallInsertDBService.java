package com.plateno.booking.internal.service.fromTicket.addBooking.botao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.support.json.JSONUtils;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.ViewStatusEnum;
import com.plateno.booking.internal.bean.response.custom.MAddBookResponse;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.common.util.redis.RedisUtils;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceFailRetryTimes;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceOrder;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceType;
import com.plateno.booking.internal.interceptor.adam.service.IService;
import com.plateno.booking.internal.service.abs.MAbsAddOrderService;
import com.plateno.booking.internal.service.fromTicket.vo.MAddBookingIncomeVo;
import com.plateno.booking.internal.service.log.OrderLogService;


@Component
@ServiceType(BookingConstants.BOTAO_MALL_BOTAO_ADD_BOOKING)
@ServiceOrder(30)
@ServiceErrorCode(BookingConstants.CODE_DB_BOOK_ERROR)
@ServiceFailRetryTimes()
public class BOTAOMallInsertDBService extends MAbsAddOrderService implements IService<MAddBookingIncomeVo, MAddBookResponse> {
	
	protected final static Logger logger = LoggerFactory.getLogger(BOTAOMallInsertDBService.class);

	@Autowired
	private OrderLogService orderLogService;
	@Autowired
	private RedisUtils redisUtils;
	
	@Override
	public void doService(MAddBookingIncomeVo income,ResultVo<MAddBookResponse> output) throws Exception {
		insertBooking(income, output);
	}

	@Override
	public void doSuccess(MAddBookingIncomeVo income,ResultVo<MAddBookResponse> output) throws Exception {
		//LogUtils.sysLoggerInfo(output.getResultMsg());
		/*if(income.getAddBookingParam().getSellStrategy().equals(2)) {
			logger.info("下单扣减积分， orderNo:{}, sellStrategy:{}, point:{}", output.getData().getOrderNo(), income.getAddBookingParam().getSellStrategy(), income.getAddBookingParam().getPoint());
			minusPoint(income);
		}*/
		/*if(!updateStock(income)) {
			//LogUtils.httpLoggerInfo(String.format("该商品【%s】,更新库存失败", income.getAddBookingParam().getGoodsId()));
			logger.error(String.format("该商品【%s】,更新库存失败", income.getAddBookingParam().getGoodsId()));
		}*/
		//redisUtils.set(output.getData().getOrderNo(), JsonUtils.toJsonString(income.getAddBookingParam()), BookingConstants.ORDER_SUCCESS_PAY_TTL); 	//设置下单对象,超时时间24h * 7
		orderLogService.saveGSOrderLog(output.getData().getOrderNo(), BookingConstants.PAY_STATUS_1, "下单成功", "下单成功，等待30分钟内付款", 0,ViewStatusEnum.VIEW_STATUS_PAYING.getCode());
	}

	@Override
	public void doFail(MAddBookingIncomeVo income,ResultVo<MAddBookResponse> output) throws Exception {
		LogUtils.sysErrorLoggerError(output.getResultMsg(), null);
		logger.error("订单创建失败, {}, {}", JSONUtils.toJSONString(income), output);
		//orderLogService.saveGSOrderLog("暂无账单ID", 100, "预定失败", output.getResultMsg(), 0,ViewStatusEnum.VIEW_STATUS_PAYING.getCode());
	}

	@Override
	public void doComplate(MAddBookingIncomeVo income,ResultVo<MAddBookResponse> output) throws Exception {}

}
