package com.plateno.booking.internal.validator.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.coupon.constant.CouponPlatformType;
import com.plateno.booking.internal.coupon.service.CouponService;
import com.plateno.booking.internal.coupon.vo.AttrValInfo;
import com.plateno.booking.internal.coupon.vo.CouponInfo;
import com.plateno.booking.internal.coupon.vo.QueryParam;
import com.plateno.booking.internal.coupon.vo.QueryResponse;
import com.plateno.booking.internal.goods.vo.OrderCheckDetail;
import com.plateno.booking.internal.goods.vo.OrderCheckInfo;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;

@Service
@ServiceErrorCode(BookingConstants.CODE_VERIFY_ERROR)
public class CouponValidateService {

    private final static Logger logger = LoggerFactory.getLogger(CouponValidateService.class);
    
    @Autowired
    private CouponService couponService;
    
    /**
     * 
    * @Title: checkCoupon 
    * @Description: 校验优惠券规则
    * @param @param addBookingParam
    * @param @param output    
    * @return void    
    * @throws
     */
    public void checkCoupon(MAddBookingParam addBookingParam,ResultVo output){
        QueryParam param = new QueryParam();
        param.setPlatformId(CouponPlatformType.fromResource(addBookingParam.getResource()).getPlatformId());
        param.setCouponId(addBookingParam.getCouponId());
        param.setMebId(addBookingParam.getMemberId());
        ResultVo<QueryResponse> result = couponService.queryCoupon(param);
        if(!result.success()) {
            logger.error("memberId:{}, couponId:{}, 获取有优惠券信息失败:{}", addBookingParam.getMemberId(), addBookingParam.getCouponId(), result);
            output.setResultCode(getClass(), ResultCode.FAILURE);
            output.setResultMsg("下单失败，获取优惠券信息失败，请稍后重试！");
            return;
        }
        
        if(result.getData().getCouponInfo().size() <= 0 || result.getData().getCouponInfo().get(0).getFlag() == null || result.getData().getCouponInfo().get(0).getFlag() != 1) {
            logger.error("memberId:{}, couponId:{}, 优惠券不可用:{}", addBookingParam.getMemberId(), addBookingParam.getCouponId(), result.getData().getCouponInfo());
            output.setResultCode(getClass(), ResultCode.FAILURE);
            output.setResultMsg("下单失败，优惠券无效或者不符合使用条件！");
            return;
        }
        
        if(result.getData().getCouponInfo().get(0).getAmount() == null) {
            logger.error("memberId:{}, couponId:{}, 优惠券金额为空", addBookingParam.getMemberId(), addBookingParam.getCouponId());
            output.setResultCode(getClass(), ResultCode.FAILURE);
            output.setResultMsg("下单失败，获取优惠券信息失败，请稍后重试！");
            return;
        }
        
        //找出适用商品
        CouponInfo couponInfo = result.getData().getCouponInfo().get(0);
        Map<String, List<AttrValInfo>> extAttrs = couponInfo.getExtAttrs();
        //适用商品金额
        int productApplyAmout = 0;
        //适用商品集合,空则为全部商品
        List<OrderCheckInfo> couponProductList = Lists.newArrayList();
        OrderCheckDetail orderCheckDetail = (OrderCheckDetail) output.getData();
        orderCheckDetail.setCouponProductList(couponProductList);
        List<OrderCheckInfo> orderCheckInfos = orderCheckDetail.getOrderCheckInfo();
        //判断是否有商品符合，查询出使用商品以及适用商品的金额
        if(null != extAttrs.get("productId")){
            List<AttrValInfo> productAttrValInfoList = extAttrs.get("productId");
            AttrValInfo attrValInfo = productAttrValInfoList.get(0);
            String productIds = attrValInfo.getAttrVal1();
            String[] productIdArr = productIds.split(",");
            boolean hasProduct = false;
            for(String temp : productIdArr){
                for(OrderCheckInfo orderCheckInfo : orderCheckInfos){
                    //优惠券的适用商品为spuId
                    if(orderCheckInfo.getSpuId() == Long.valueOf(temp)){
                        productApplyAmout += orderCheckInfo.getPrice() * orderCheckInfo.getQuantity();
                        couponProductList.add(orderCheckInfo);
                        hasProduct = true;
                        break;
                    }
                }
            }
            //订单中的商品都不符合优惠券的使用商品
            if(!hasProduct){
                logger.error("memberId:{}, couponId:{}, 订单中的商品都不符合优惠券适用商品:{}", addBookingParam.getMemberId(), addBookingParam.getCouponId(),productIds);
                output.setResultCode(getClass(), ResultCode.FAILURE);
                output.setResultMsg("订单中的商品都不符合优惠券适用商品");
                return;
            }
        }else{
            productApplyAmout = orderCheckDetail.getTotalPrice();
        }
        //适用商品金额
        BigDecimal productApplyAmoutBig = new BigDecimal(productApplyAmout).divide(new BigDecimal("100"));
        if(null != extAttrs.get("orderAmount")){
            List<AttrValInfo> orderAmountAttrValInfoList = extAttrs.get("orderAmount");
            AttrValInfo orderAmountAttrValInfo = orderAmountAttrValInfoList.get(0);
            //50-10,表示满50优惠10元，符合限制条件即可
            String couponAmoutStr = orderAmountAttrValInfo.getAttrVal1();
            String[]  couponAmoutStrArr = couponAmoutStr.split("-");
            BigDecimal couponOrderAmout = new BigDecimal(couponAmoutStrArr[0]);
            //商品金额是分，优惠券金额为元，需同步,将适用商品订单金额除以100
            
            if(productApplyAmoutBig.compareTo(couponOrderAmout) < 0){
                logger.error("memberId:{}, couponId:{}, 适用商品金额:{}小于优惠券限制金额:{}", addBookingParam.getMemberId(), addBookingParam.getCouponId(),productApplyAmoutBig,couponOrderAmout);
                output.setResultCode(getClass(), ResultCode.FAILURE);
                output.setResultMsg("下单失败，不符合满减券规则");
                return;
            }
        }
        orderCheckDetail.setCouponOrderAmount(productApplyAmoutBig);
        addBookingParam.setCouponName(StringUtils.trimToEmpty(result.getData().getCouponInfo().get(0).getCouponName()));
        addBookingParam.setCouponAmount(result.getData().getCouponInfo().get(0).getAmount());
        addBookingParam.setValidCouponAmount(result.getData().getCouponInfo().get(0).getAmount());
        
    }
    
}
