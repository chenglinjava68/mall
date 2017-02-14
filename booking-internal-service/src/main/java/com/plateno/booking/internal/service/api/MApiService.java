package com.plateno.booking.internal.service.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.coupon.service.CouponService;
import com.plateno.booking.internal.goods.MallGoodsService;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.member.PointService;
import com.plateno.booking.internal.service.order.MOrderService;
import com.plateno.booking.internal.validator.order.MOrderValidate;
import com.plateno.booking.internal.validator.order.ProductValidateService;

/**
 * @author user
 * 
 * 策略模式实现,根据渠道获取对象实例
 *
 */
@Service
public class MApiService {
	
	private final static Logger logger = LoggerFactory.getLogger(MApiService.class);
	
	@Autowired
	private MOrderValidate morderValidate;
	
	@Autowired
	private  MallGoodsService mallGoodsService;

	@Autowired
	private PointService pointService;
	
	@Autowired
	private MOrderService mOrderService;
	
	@Autowired
	private CouponService couponService;
	
	@Autowired
	private ProductValidateService productValidateService;
	
	/*

	@Resource(name = "ticketStrategyMap")
    private OwnerStrategyMap ownerStrategyMap ;
	
	
	private ThirdApiService getStrategyMap(ResultVo vo,Integer channel){
		ThirdApiService thirdApiService = ownerStrategyMap.getFindOwnerStrategyMap().get(ThirdServiceEnum.getServiceNameByChannel(channel));
		if (thirdApiService == null) {
			vo.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
			vo.setResultMsg("渠道有误,获取不到对应的服务对象");
		}
		return thirdApiService;
	}
*/
	
	
	
	
	/**
	 * 校验订单
	 * 
	 * @param convertBookingParam
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResultVo Validate(MAddBookingParam addBookingParam,ResultVo output){
		productValidateService.checkProductAndCal(addBookingParam, output);
		//校验不通过则直接返回
		if(!output.getResultCode().equals(ResultCode.SUCCESS))
		    return output;
		return output;
	}
	
	
	
	/**
	 * 下单
	 * 
	 * @param addBookingParam
	 * @param orderNo     铂涛订单ID( O开头  ) 
	 * @return
	 *//*
	public ResultVo Booking(AddBookingParam addBookingParam,String orderNo,ResultVo output){
		ConvertBookingParam convertBookingParam = new ConvertBookingParam();
		ThirdApiService thirdApiService = getStrategyMap(output,addBookingParam.getChannel());
		if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
			return output;
		}
		BeanUtils.copyProperties(addBookingParam, convertBookingParam);
		convertBookingParam.setOrderNo(orderNo);
		return thirdApiService.Booking(convertBookingParam,output);
	}*/
	
	
	/**
	 * 支付接口
	 * 
	 * @return
	 *//*
	public ResultVo Pay(ConvertThirdPayParam payParam,Integer channel,ResultVo output){
		ThirdApiService thirdApiService = getStrategyMap(output,channel);
		if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
			return output;
		}
		Runnable runnable = null;
		if (channel.equals(Config.CHANNEL_TC)) {
			runnable = new TCRunnable(payParam);
		}
		return thirdApiService.Pay(payParam,output,runnable);
	}*/
	
	
	
	
}
