package com.plateno.booking.internal.service.api;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.coupon.constant.CouponEnum;
import com.plateno.booking.internal.coupon.constant.CouponPlatformType;
import com.plateno.booking.internal.coupon.service.CouponService;
import com.plateno.booking.internal.coupon.vo.Conditions;
import com.plateno.booking.internal.coupon.vo.QueryParam;
import com.plateno.booking.internal.coupon.vo.QueryResponse;
import com.plateno.booking.internal.goods.MallGoodsService;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.member.PointService;
import com.plateno.booking.internal.service.order.MOrderService;
import com.plateno.booking.internal.validator.order.MOrderValidate;
import com.plateno.booking.internal.validator.order.ProductValidateService;
import com.plateno.booking.internal.wechat.model.ProductSkuBean;

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
		
		//商品总金额，对比优惠券金额处理，校验优惠券等
		int productAmount = (int) output.getData();
//		if(productAmount < 0) {
//			logger.info("商品需要支付的金额小于优惠券金额, productAmount:{}", productAmount);
//			addBookingParam.setValidCouponAmount(new BigDecimal((addBookingParam.getQuantity() * price) + "").divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_DOWN));
//			productAmount = 0;
//		}
		
		//判断金额是否足够
		if(!addBookingParam.getTotalAmount().equals(productAmount)){
			output.setResultCode(getClass(),MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMsgCode());
			output.setResultMsg(MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMessage());
			return output;
		}
		
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
