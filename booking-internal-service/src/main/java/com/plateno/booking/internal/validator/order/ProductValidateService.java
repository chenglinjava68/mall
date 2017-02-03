package com.plateno.booking.internal.validator.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.bean.request.custom.MOrderGoodsParam;
import com.plateno.booking.internal.goods.MallGoodsService;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;
import com.plateno.booking.internal.service.order.MOrderService;
import com.plateno.booking.internal.wechat.model.ProductSkuBean;

@Service
@ServiceErrorCode(BookingConstants.CODE_VERIFY_ERROR)
public class ProductValidateService {

    private final static Logger logger = LoggerFactory.getLogger(ProductValidateService.class);
    
    @Autowired
    private  MallGoodsService mallGoodsService;

    @Autowired
    private MOrderService mOrderService;
    
    /**
     * 
    * @Title: checkProduct 
    * @Description: 校验商品
    * @param @param addBookingParam
    * @param @param output    
    * @return void    
    * @throws
     */
    public void checkProductAndCal(MAddBookingParam addBookingParam,ResultVo output){
        int totalGoodsPrice = 0;
        for(MOrderGoodsParam orderGoodsParam : addBookingParam.getGoodsList()){
            //计算每个商品价格
            ResultVo<Integer> result = calProduct(addBookingParam, orderGoodsParam);
            if(result.getResultCode().equals(ResultCode.SUCCESS)){
                totalGoodsPrice += result.getData();
            }else{
                return;
            }
        }
        output.setData(totalGoodsPrice);
    }
    
    /**
     * 
    * @Title: calProduct 
    * @Description: 校验并返回单个商品价格
    * @param @param addBookingParam
    * @param @param output
    * @param @return    
    * @return ResultVo    
    * @throws
     */
    public ResultVo<Integer> calProduct(MAddBookingParam addBookingParam,MOrderGoodsParam orderGoodsParam){
        ResultVo<Integer> output = new ResultVo<Integer>(ResultCode.SUCCESS);
        ProductSkuBean pskubean=new ProductSkuBean() ;
        try {
            pskubean = mallGoodsService.getProductAndskuStock(orderGoodsParam.getGoodsId().toString());
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
        if(orderGoodsParam.getQuantity()>pskubean.getStock()){
            output.setResultCode(getClass(),MsgCode.VALIDATE_ORDER_STOCK_ERROR.getMsgCode());
            output.setResultMsg(MsgCode.VALIDATE_ORDER_STOCK_ERROR.getMessage());
            return output;
        }
        
        //限购
        if(pskubean.getMaxSaleQty() != null && pskubean.getMaxSaleQty() > 0) {
            //查询用户已经购买的数量 
            int queryUserProductSum = mOrderService.queryUserProductSum(addBookingParam.getMemberId(), pskubean.getProductId());
            
            logger.info("限购：{}， 已购：{}， 准备购：{}", pskubean.getMaxSaleQty(), queryUserProductSum, orderGoodsParam.getQuantity());
            
            int num = pskubean.getMaxSaleQty() - queryUserProductSum;
            if(num < orderGoodsParam.getQuantity()) {
                output.setResultCode(getClass(),MsgCode.VALIDATE_ORDER_STOCK_ERROR.getMsgCode());
                output.setResultMsg("您购买的总数量已经超过限制的数量，目前您最多只能购买：" + (num >=0 ? num : 0) + "件");
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
        
        int price2 = price;
        
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
        
        
        
        
        //判断优惠券
        
        //判断金额是否足够，外移
        
        //返回商品金额
        output.setData(orderGoodsParam.getQuantity()*price + pskubean.getExpressFee());
        
        return output;
    }
    
    
}
