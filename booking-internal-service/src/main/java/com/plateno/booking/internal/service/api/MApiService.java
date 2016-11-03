package com.plateno.booking.internal.service.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.bean.request.custom.OrderParam;
import com.plateno.booking.internal.goods.MallGoodsService;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.member.PointService;
import com.plateno.booking.internal.validator.order.MOrderValidate;
import com.plateno.booking.internal.wechat.model.ProductSkuBean;

/**
 * @author user
 * 
 * 策略模式实现,根据渠道获取对象实例
 *
 */
@Service
public class MApiService {
	
	@Autowired
	private MOrderValidate morderValidate;
	
	@Autowired
	private  MallGoodsService mallGoodsService;

	@Autowired
	private PointService pointService;
	
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
		ProductSkuBean pskubean=new ProductSkuBean() ;
		try {
			pskubean = mallGoodsService.getProductAndskuStock(addBookingParam.getGoodsId().toString());
		} catch (OrderException e) {
			e.printStackTrace();
			output.setResultCode(getClass(),MsgCode.VALIDATE_ORDER_ERROR_PRODUCTNULL.getMsgCode());
			output.setResultMsg(MsgCode.VALIDATE_ORDER_ERROR_PRODUCTNULL.getMessage());
			return output;	
		}
		
		if(pskubean==null){
			output.setResultCode(getClass(),MsgCode.VALIDATE_ORDER_ERROR_PRODUCTNULL.getMsgCode());
			output.setResultMsg(MsgCode.VALIDATE_ORDER_ERROR_PRODUCTNULL.getMessage());
			return output;
		}
		
		if(addBookingParam.getQuantity()>pskubean.getStock()){
			output.setResultCode(getClass(),MsgCode.VALIDATE_ORDER_STOCK_ERROR.getMsgCode());
			output.setResultMsg(MsgCode.VALIDATE_ORDER_STOCK_ERROR.getMessage());
			return output;
		}
		
		if(pskubean.getStatus().equals(BookingResultCodeContants.PAY_STATUS_2)){
			output.setResultCode(getClass(),MsgCode.VALIDATE_ORDER_STATUS_ERROR.getMsgCode());
			output.setResultMsg(MsgCode.VALIDATE_ORDER_STATUS_ERROR.getMessage());
			return output;
		}
		
		if(addBookingParam.getSellStrategy().equals(1)){
			/*if(addBookingParam.getShippingType().equals(1)){
				if(!addBookingParam.getTotalAmount().equals((addBookingParam.getQuantity()*pskubean.getRegularPrice()))){
					output.setResultCode(getClass(),MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMsgCode());
					output.setResultMsg(MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMessage());
					return output;
				}
			}else{
				if(!addBookingParam.getTotalAmount().equals((addBookingParam.getQuantity()*pskubean.getRegularPrice()+pskubean.getExpressFee()))){
					output.setResultCode(getClass(),MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMsgCode());
					output.setResultMsg(MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMessage());
					return output;
				}
			}*/
			
			//pskubean.getExpressFee() > 0 代表需要邮费
			if(pskubean.getExpressFee() != null && pskubean.getExpressFee() > 0) {
				if(!addBookingParam.getShippingType().equals(2)) {
					output.setResultCode(getClass(),MsgCode.VALIDATE_ORDERSJIPPINGTYPE_ERROR.getMsgCode());
					output.setResultMsg(MsgCode.VALIDATE_ORDERSJIPPINGTYPE_ERROR.getMessage());
					return output;
				}
				
				if(!addBookingParam.getTotalAmount().equals((addBookingParam.getQuantity()*pskubean.getRegularPrice()+pskubean.getExpressFee()))){
					output.setResultCode(getClass(),MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMsgCode());
					output.setResultMsg(MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMessage());
					return output;
				}
			} else {
				
				if(!addBookingParam.getShippingType().equals(1)) {
					output.setResultCode(getClass(),MsgCode.VALIDATE_ORDERSJIPPINGTYPE_ERROR.getMsgCode());
					output.setResultMsg(MsgCode.VALIDATE_ORDERSJIPPINGTYPE_ERROR.getMessage());
					return output;
				}
				
				if(!addBookingParam.getTotalAmount().equals((addBookingParam.getQuantity()*pskubean.getRegularPrice()))){
					output.setResultCode(getClass(),MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMsgCode());
					output.setResultMsg(MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMessage());
					return output;
				}
			}
			
		}else{
			if(pointService.getPointSum(addBookingParam.getMemberId())<addBookingParam.getPoint()){
				output.setResultCode(getClass(),MsgCode.VALIDATE_POINT_ERROR.getMsgCode());
				output.setResultMsg(MsgCode.VALIDATE_POINT_ERROR.getMessage());
				return output;
			}
			
			//判断金额总金额是否对应
			/*if(addBookingParam.getShippingType().equals(1)){
				if(!addBookingParam.getTotalAmount().equals((addBookingParam.getQuantity()*pskubean.getFavorPrice()))){
					output.setResultCode(getClass(),MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMsgCode());
					output.setResultMsg(MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMessage());
					return output;
				}
			}else{
				if(!addBookingParam.getTotalAmount().equals((addBookingParam.getQuantity()*pskubean.getFavorPrice()+pskubean.getExpressFee()))){
					output.setResultCode(getClass(),MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMsgCode());
					output.setResultMsg(MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMessage());
					return output;
				}
			}*/
			
			//pskubean.getExpressFee() > 0 代表需要邮费
			if(pskubean.getExpressFee() != null && pskubean.getExpressFee() > 0) {
				if(!addBookingParam.getShippingType().equals(2)) {
					output.setResultCode(getClass(),MsgCode.VALIDATE_ORDERSJIPPINGTYPE_ERROR.getMsgCode());
					output.setResultMsg(MsgCode.VALIDATE_ORDERSJIPPINGTYPE_ERROR.getMessage());
					return output;
				}
				
				if(!addBookingParam.getTotalAmount().equals((addBookingParam.getQuantity()*pskubean.getFavorPrice()+pskubean.getExpressFee()))){
					output.setResultCode(getClass(),MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMsgCode());
					output.setResultMsg(MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMessage());
					return output;
				}
			} else {
				
				if(!addBookingParam.getShippingType().equals(1)) {
					output.setResultCode(getClass(),MsgCode.VALIDATE_ORDERSJIPPINGTYPE_ERROR.getMsgCode());
					output.setResultMsg(MsgCode.VALIDATE_ORDERSJIPPINGTYPE_ERROR.getMessage());
					return output;
				}
				
				if(!addBookingParam.getTotalAmount().equals((addBookingParam.getQuantity()*pskubean.getFavorPrice()))){
					output.setResultCode(getClass(),MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMsgCode());
					output.setResultMsg(MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMessage());
					return output;
				}
			}
		}
		

		if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
			return output;
		}
		return output;
	}
	
	
	
	/**
	 * 下单
	 * 
	 * @param addBookingParam
	 * @param orderNo     铂涛订单ID( O开头  ) TODO: O1468920676436108
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
