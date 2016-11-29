package com.plateno.booking.internal.service.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.goods.MallGoodsService;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.member.PointService;
import com.plateno.booking.internal.service.order.MOrderService;
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
	
	@Autowired
	private MOrderService mOrderService;
	
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
		
		//是否已经下架或未到开售时间
		if(!Integer.valueOf(1).equals(pskubean.getStatus())){
			output.setResultCode(getClass(),MsgCode.VALIDATE_ORDER_STATUS_ERROR.getMsgCode());
			output.setResultMsg("商品未到开售时间或者已经下架！");
			return output;
		}

		//库存
		if(addBookingParam.getQuantity()>pskubean.getStock()){
			output.setResultCode(getClass(),MsgCode.VALIDATE_ORDER_STOCK_ERROR.getMsgCode());
			output.setResultMsg(MsgCode.VALIDATE_ORDER_STOCK_ERROR.getMessage());
			return output;
		}
		
		//限购
		if(pskubean.getMaxSaleQty() != null && pskubean.getMaxSaleQty() > 0) {
			//查询用户已经购买的数量 
			int queryUserProductSum = mOrderService.queryUserProductSum(addBookingParam.getMemberId(), pskubean.getProductId());
			
			int num = pskubean.getMaxSaleQty() - queryUserProductSum;
			if(num < addBookingParam.getQuantity()) {
				output.setResultCode(getClass(),MsgCode.VALIDATE_ORDER_STOCK_ERROR.getMsgCode());
				output.setResultMsg("您购买的总数量已经超过限制的数量，目前您最多只能购买：" + (num >=0 ? num : 0) + "件");
				return output;
			}
		}

		//快递费
		int expressFee = 0;
		if(pskubean.getExpressFee() != null && pskubean.getExpressFee() > 0) {
			
			expressFee = pskubean.getExpressFee();
			
			if(!addBookingParam.getShippingType().equals(2)) {
				output.setResultCode(getClass(),MsgCode.VALIDATE_ORDERSJIPPINGTYPE_ERROR.getMsgCode());
				output.setResultMsg(MsgCode.VALIDATE_ORDERSJIPPINGTYPE_ERROR.getMessage());
				return output;
			}
		}
		
		//价格
		int price = 0;
		int point = 0;
		
		//判断是否有促销价
		if(pskubean.getPromotPrice() != null && pskubean.getPromotPrice() > 0) {
			price = pskubean.getPromotPrice();
		} else {
			price = pskubean.getRegularPrice();
		}
		
		//使用积分购买
		if(!addBookingParam.getSellStrategy().equals(1)) {
			
			//判断商品是否允许使用积分购买
			if(pskubean.getFavorPrice() == null || pskubean.getFavorPrice() <= 0) {
				output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
				output.setResultMsg("商品不允许使用订单+积分的方式购买，订单校验失败");
				return output;
			}
			
			price = pskubean.getFavorPrice();
			point = pskubean.getFavorPoints();
		}
		
		//判断积分是够足够
		if(point > 0) {
			if(!addBookingParam.getPoint().equals(point * addBookingParam.getQuantity())) {
				output.setResultCode(getClass(), MsgCode.VALIDATE_ORDERPOINT_ERROR.getMsgCode());
				output.setResultMsg(MsgCode.VALIDATE_ORDERPOINT_ERROR.getMessage());
				return output;
			}
			
			int userPoint = pointService.getPointSum(addBookingParam.getMemberId());
			if(userPoint < addBookingParam.getPoint()){
				logger.info(String.format("需要积分：%s, 账户积分:%s, memberId:%s", addBookingParam.getPoint(), userPoint, addBookingParam.getMemberId()));
				output.setResultCode(getClass(),MsgCode.VALIDATE_POINT_ERROR.getMsgCode());
				output.setResultMsg("您的积分余额不足以购买" + addBookingParam.getQuantity() + "个商品，可以修改商品数量再进行支付。");
				return output;
			}
			
		} else {
			addBookingParam.setPoint(0);
		}
		
		//判断金额是否足够
		if(!addBookingParam.getTotalAmount().equals((addBookingParam.getQuantity() * price + expressFee))){
			output.setResultCode(getClass(),MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMsgCode());
			output.setResultMsg(MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMessage());
			return output;
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
