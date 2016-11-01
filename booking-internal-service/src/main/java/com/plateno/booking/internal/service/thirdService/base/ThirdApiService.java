package com.plateno.booking.internal.service.thirdService.base;

import java.util.List;

import com.plateno.booking.internal.bean.request.convert.ConvertBookingParam;
import com.plateno.booking.internal.bean.request.convert.ConvertThirdPayParam;
import com.plateno.booking.internal.dao.pojo.BillDetails;
import com.plateno.booking.internal.dao.pojo.BillLogDetail;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;

/**
 * 第三方基础服务接口定义
 * 
 * @author user
 *
 */
public interface ThirdApiService {
	
	/**
	 * 重发短信接口
	 * 
	 * @return
	 */
	public ResultVo SendMessageService(BillDetails billDetails,ResultVo output);
	
	
	/**
	 * 根据铂涛订单号查询
	 * 
	 * @return
	 */
	public ResultVo QueryOrderListByOrderNo(List<BillDetails> billDetails,ResultVo output);
	
	
	
	/**
	 * 根据第三方(同程)的订单号查询
	 * 
	 * @return
	 */
	public ResultVo QueryOrderList(List<BillLogDetail> billLogDetails,ResultVo output);
	
	
	
	/**
	 * 
	 * 订单校验接口
	 * @return
	 */
	public ResultVo Validate(ConvertBookingParam convertBookingParam,ResultVo output);
	
	
	
	/**
	 * 下单接口
	 * 
	 * @return
	 */
	public ResultVo Booking(ConvertBookingParam convertBookingParam,ResultVo output);
	
	
	
	
	/**
	 * 支付接口
	 * 
	 * @return
	 */
	public ResultVo Pay(ConvertThirdPayParam convertThirdPayParam,ResultVo output,Runnable runnable);
	
	
	/**
	 * 
	 * 取消订单接口
	 * @return
	 */
	public ResultVo Refund(BillDetails billDetails,ResultVo output);
}
