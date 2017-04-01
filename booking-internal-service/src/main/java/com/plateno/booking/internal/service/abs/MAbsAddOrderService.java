package com.plateno.booking.internal.service.abs;

import org.springframework.beans.factory.annotation.Autowired;

import com.plateno.booking.internal.base.constant.PayStatusEnum;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.bean.request.point.ValueBean;
import com.plateno.booking.internal.bean.response.custom.MAddBookResponse;
import com.plateno.booking.internal.goods.MallGoodsService;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.api.MApiService;
import com.plateno.booking.internal.service.fromTicket.vo.MAddBookingIncomeVo;
import com.plateno.booking.internal.service.order.MOrderService;
import com.plateno.booking.internal.member.PointService;

public abstract class MAbsAddOrderService {
	
	@Autowired
	private MOrderService morderService;
	
	
	@Autowired
	private MApiService mapiService;
	
	
	@Autowired
	private PointService pointService;
	
	
	@Autowired
	private MallGoodsService mallGoodsService;
	
	
	/**
	 * 验证下单
	 * 
	 * @param income
	 * @param output
	 */
	public void validateBooking(MAddBookingIncomeVo income, ResultVo<MAddBookResponse> output){
		MAddBookingParam addBookingParam = income.getAddBookingParam();
		mapiService.Validate(addBookingParam,output);
	}
	
	
	public void addBooking(MAddBookingIncomeVo income, ResultVo<MAddBookResponse> output) throws Exception{
		/*AddBookingParam addBookingParam = income.getAddBookingParam();
		String orderNo = StringUtil.getCurrentAndRamobe("O");
		mapiService.Booking(addBookingParam,orderNo,output);
		income.setOrderNo(orderNo);*/
	}
	
	protected void insertBooking(MAddBookingIncomeVo income, ResultVo output) throws Exception {
		Order insertOrder = morderService.insertOrder(income,output);
		MAddBookResponse addBookResponse = new MAddBookResponse();
		//不需要回传
		addBookResponse.setOrderNo(insertOrder.getOrderNo());
		addBookResponse.setViewStatus(PayStatusEnum.toViewStatus(insertOrder.getPayStatus()));
		addBookResponse.setPayStatus(insertOrder.getPayStatus());
		output.setData(addBookResponse);
	}
	
	
	
}
