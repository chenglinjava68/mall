package com.plateno.booking.internal.service.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
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
	
	private final static Logger logger = LoggerFactory.getLogger(MApiService.class);
	
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
			logger.info("查询商品信息失败", e);
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
		
		if(addBookingParam.getSellStrategy().equals(1)){ //使用金额购买
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
			
			addBookingParam.setPoint(0);
			
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
			
		}else{ //使用积分购买
			 
			//判断商品是否允许使用积分购买
			if(pskubean.getSellStrategy() != 2) {
				output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
				output.setResultMsg("商品不允许使用订单+积分的方式购买，订单校验失败");
				return output;
			}

			if(addBookingParam.getPoint() != pskubean.getFavorPoints() * addBookingParam.getQuantity()) {
				output.setResultCode(getClass(), MsgCode.VALIDATE_ORDERPOINT_ERROR.getMsgCode());
				output.setResultMsg(MsgCode.VALIDATE_ORDERPOINT_ERROR.getMessage());
				return output;
			}
			
			int point = pointService.getPointSum(addBookingParam.getMemberId());
			if(point < addBookingParam.getPoint()){
				logger.info(String.format("需要积分：%s, 账户积分:%s, memberId:%s", addBookingParam.getPoint(), point, addBookingParam.getMemberId()));
				output.setResultCode(getClass(),MsgCode.VALIDATE_POINT_ERROR.getMsgCode());
				output.setResultMsg("您的积分余额不足以购买" + addBookingParam.getQuantity() + "个商品，可以修改商品数量再进行支付。");
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
