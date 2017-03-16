package com.plateno.booking.internal.validator.order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.bean.request.custom.MOrderGoodsParam;
import com.plateno.booking.internal.goods.MallGoodsService;
import com.plateno.booking.internal.goods.vo.OrderCheckDetail;
import com.plateno.booking.internal.goods.vo.OrderCheckInfo;
import com.plateno.booking.internal.goods.vo.OrderCheckParamInfo;
import com.plateno.booking.internal.goods.vo.OrderCheckReq;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultCode;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.interceptor.adam.common.bean.annotation.service.ServiceErrorCode;
import com.plateno.booking.internal.member.PointService;
import com.plateno.booking.internal.service.order.MOrderService;

@Service
@ServiceErrorCode(BookingConstants.CODE_VERIFY_ERROR)
public class ProductValidateService {

    private final static Logger logger = LoggerFactory.getLogger(ProductValidateService.class);

    @Autowired
    private MallGoodsService mallGoodsService;

    @Autowired
    private MOrderService mOrderService;

    @Autowired
    private PointService pointService;
    
    /**
     * 
     * @Title: checkProduct
     * @Description: 校验商品
     * @param @param addBookingParam
     * @param @param output
     * @return void
     * @throws
     */
    public void checkProductAndCal(MAddBookingParam addBookingParam, ResultVo output) {

        OrderCheckReq orderCheckReq = new OrderCheckReq();
        List<OrderCheckParamInfo> orderCheckParamInfos = Lists.newArrayList();
        orderCheckReq.setOrderCheckParamInfos(orderCheckParamInfos);
        orderCheckReq.setMemberPoints(addBookingParam.getPoint());
        for (MOrderGoodsParam orderGoodsParam : addBookingParam.getGoodsList()) {
            OrderCheckParamInfo orderCheckParamInfo = new OrderCheckParamInfo();
            orderCheckParamInfo.setGoodsId(orderGoodsParam.getGoodsId());
            orderCheckParamInfo.setQuantity(orderGoodsParam.getQuantity());
            orderCheckParamInfos.add(orderCheckParamInfo);
        }
        OrderCheckDetail orderCheckDetail = mallGoodsService.orderCheck(orderCheckReq);
        if (null == orderCheckDetail) {
            output.setResultCode(getClass(), MsgCode.VALIDATE_ORDER_ERROR_PRODUCTNULL.getMsgCode());
            output.setResultMsg(MsgCode.VALIDATE_ORDER_ERROR_PRODUCTNULL.getMessage());
            return;
        }
        // list to map ，方便获取
        Map<Long, OrderCheckInfo> orderCheckInfoMap = Maps.newHashMap();
        for (OrderCheckInfo orderCheckInfo : orderCheckDetail.getOrderCheckInfo()) {
            if (null == orderCheckInfoMap.get(orderCheckInfo.getGoodsId())) {
                orderCheckInfoMap.put(orderCheckInfo.getGoodsId(), orderCheckInfo);
            }

        }

        // 检查单个商品
        for (MOrderGoodsParam orderGoodsParam : addBookingParam.getGoodsList()) {
            checkSingleProduct(addBookingParam, orderGoodsParam, orderCheckInfoMap, output);
            if (!output.getResultCode().equals(ResultCode.SUCCESS)) {
                return;
            }
        }
        // 比较积分
        if (null != orderCheckDetail.getPointDeductValue()) {
            if (addBookingParam.getPoint().intValue() != orderCheckDetail.getPointDeductValue()
                    .getCostPoints().intValue()) {
                output.setResultCode(getClass(), MsgCode.VALIDATE_POINT_ERROR.getMsgCode());
                output.setResultMsg(MsgCode.VALIDATE_POINT_ERROR.getMessage());
                return;
            }
          //判断积分是够足够
            if(addBookingParam.getPoint() > 0) {
                int userPoint = pointService.getPointSum(addBookingParam.getMemberId());
                if(userPoint < addBookingParam.getPoint()){
                    logger.info(String.format("需要积分：%s, 账户积分:%s, memberId:%s", addBookingParam.getPoint(), userPoint, addBookingParam.getMemberId()));
                    output.setResultCode(getClass(),MsgCode.VALIDATE_POINT_ERROR.getMsgCode());
                    output.setResultMsg("您的积分余额不足以购买商品，可以修改商品数量再进行支付。");
                    return;
                }
                
            } else {
                addBookingParam.setPoint(0);
            }
            
        }
        orderCheckDetail.setOrderCheckInfoMap(orderCheckInfoMap);
        output.setData(orderCheckDetail);

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
    public void checkSingleProduct(MAddBookingParam addBookingParam,
            MOrderGoodsParam orderGoodsParam, Map<Long, OrderCheckInfo> orderCheckInfoMap,
            ResultVo output) {

        OrderCheckInfo orderCheckInfo = orderCheckInfoMap.get(orderGoodsParam.getGoodsId());

        // 是否已经下架或未到开售时间
        if (!Integer.valueOf(1).equals(orderCheckInfo.getStatus())) {
            output.setResultCode(getClass(), MsgCode.VALIDATE_ORDER_STATUS_ERROR.getMsgCode());
            output.setResultMsg("商品未到开售时间或者已经下架！skuId:" + orderCheckInfo.getGoodsId());
            return;
        }

        // 库存
        if (null == orderCheckInfo.getStock()
                || orderGoodsParam.getQuantity() > orderCheckInfo.getStock()) {
            output.setResultCode(getClass(), MsgCode.VALIDATE_ORDER_STOCK_ERROR.getMsgCode());
            output.setResultMsg(String.format("msg:%s,skuId:%s,quantity:%s",
                    MsgCode.VALIDATE_ORDER_STOCK_ERROR.getMessage(), orderCheckInfo.getGoodsId(),
                    orderGoodsParam.getQuantity()));
            return;
        }

        // 限购
        if (orderCheckInfo.getMaxSaleQty() != null && orderCheckInfo.getMaxSaleQty() > 0) {
            // 查询用户已经购买的数量
            int queryUserProductSum =
                    mOrderService.queryUserProductSum(addBookingParam.getMemberId(), orderCheckInfo
                            .getGoodsId().intValue());

            logger.warn("限购：{}， 已购：{}， 准备购：{}", orderCheckInfo.getMaxSaleQty(),
                    queryUserProductSum, orderGoodsParam.getQuantity());

            int num = orderCheckInfo.getMaxSaleQty() - queryUserProductSum;
            if (num < orderGoodsParam.getQuantity()) {
                output.setResultCode(getClass(), MsgCode.VALIDATE_ORDER_STOCK_ERROR.getMsgCode());
                output.setResultMsg("您购买的总数量已经超过限制的数量，目前您最多只能购买：" + (num >= 0 ? num : 0) + "件");
                return;
            }
        }
        return;
    }

    /**
     * 
     * @Title: checkPayMoney
     * @Description: 比较实付金额是否一致
     * @param @param addBookingParam
     * @param @param output
     * @return void
     * @throws
     */
    public void checkPayMoney(MAddBookingParam addBookingParam, ResultVo output) {
        OrderCheckDetail orderCheckDetail = (OrderCheckDetail) output.getData();
        int payMoney = orderCheckDetail.getTotalPrice() + orderCheckDetail.getTotalExpressFee();

        if (null != orderCheckDetail.getPointDeductValue()) {
            payMoney = payMoney - orderCheckDetail.getPointDeductValue().getPointValue();
        }
        if (null != addBookingParam.getCouponId() && addBookingParam.getCouponId() > 0) {
            payMoney =
                    payMoney
                            - addBookingParam.getCouponAmount().multiply(new BigDecimal(100))
                                    .intValue();
        }
        //金额小于0，统一按0计算
        if(payMoney <= 0)
            payMoney = 0;
        if (addBookingParam.getTotalAmount() != payMoney) {
            output.setResultCode(getClass(), MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMsgCode());
            output.setResultMsg(MsgCode.VALIDATE_ORDERAMOUNT_ERROR.getMessage());
            return;
        }

    }

}
