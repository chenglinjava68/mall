package com.plateno.booking.internal.service.order;

import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.base.mapper.MLogisticsMapper;
import com.plateno.booking.internal.base.mapper.MOrderCouponMapper;
import com.plateno.booking.internal.base.pojo.MLogistics;
import com.plateno.booking.internal.base.pojo.MOrderCouponPO;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.bean.request.point.ValueBean;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.coupon.constant.CouponEnum;
import com.plateno.booking.internal.coupon.constant.CouponPlatformType;
import com.plateno.booking.internal.coupon.service.CouponService;
import com.plateno.booking.internal.coupon.vo.BaseResponse;
import com.plateno.booking.internal.coupon.vo.Conditions;
import com.plateno.booking.internal.coupon.vo.UseParam;
import com.plateno.booking.internal.goods.MallGoodsService;
import com.plateno.booking.internal.goods.vo.OrderCheckDetail;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.member.PointService;

@Service
public class OrderInsertActorService {

    private final static Logger logger = LoggerFactory.getLogger(OrderInsertActorService.class);
    @Autowired
    private MLogisticsMapper mLogisticsMapper;
    @Autowired
    private MOrderCouponMapper mOrderCouponMapper;
    @Autowired
    private MallGoodsService mallGoodsService;
    @Autowired
    private PointService pointService;
    @Autowired
    private CouponService couponService;

    public void insertAfter(MAddBookingParam book, Order order,OrderCheckDetail orderCheckDetail) throws Exception {


        // 插入快递单信息
        logger.info("插入快递单信息");
        MLogistics logistics = new MLogistics();
        logistics.setOrderNo(order.getOrderNo());
        logistics.setShippingType(1);// 1包邮,2普通快递
        logistics.setConsigneeName(book.getConsigneeName());
        logistics.setConsigneeAddress(book.getConsigneeAddress());
        logistics.setConsigneeMobile(book.getConsigneeMobile());
        logistics.setProvince(book.getProvince());
        logistics.setCity(book.getCity());
        logistics.setArea(book.getArea());
        mLogisticsMapper.insertSelective(logistics);

        // 如果使用优惠券，记录优惠券的使用信息
        if (book.getCouponId() != null && book.getCouponId() > 0) {
            MOrderCouponPO mOrderCouponPO = new MOrderCouponPO();
            mOrderCouponPO.setCouponId(book.getCouponId());
            mOrderCouponPO.setOrderNo(order.getOrderNo());
            mOrderCouponPO.setCouponType(CouponEnum.MONEY_COUPON.getType());
            mOrderCouponPO.setSubCouponType(CouponEnum.MONEY_COUPON.getSubType());
            mOrderCouponPO.setCouponName(book.getCouponName());
            mOrderCouponPO.setAmount(book.getCouponAmount());
            mOrderCouponPO.setOrderCouponAmount(book.getValidCouponAmount());
            mOrderCouponPO.setCreateTime(new Date());
            mOrderCouponMapper.insert(mOrderCouponPO);
        }


        // 扣减库存
        boolean modifyStock = mallGoodsService.deductBatchStock(book.getGoodsList());
        if (!modifyStock) {
            logger.error("扣减库存失败， {}", modifyStock);
            throw new OrderException("系统正忙，扣减库存失败，请重试！");
        }

        // 扣减积分
        if (book.getSellStrategy().equals(2)) {
            logger.info("下单扣减积分， sellStrategy:{}, point:{}", book.getSellStrategy(),
                    book.getPoint());
            boolean minusPoint = minusPoint(book.getMemberId(), book.getPoint());
            if (!minusPoint) {
                logger.error("扣积分失败， {}， {}", book.getMemberId(), minusPoint);

                // 事务回滚，归还库存
                boolean result = mallGoodsService.returnBatchStock(book.getGoodsList());
                if (!result) {
                    logger.error("下单扣减积分失败，回滚事务，归还库存失败，goodsJson:{}",
                            JsonUtils.toJsonString(book.getGoodsList()));
                }
                throw new OrderException("系统正忙，扣减积分，请重试！");
            }
        }

        // 使用优惠券
        if (book.getCouponId() != null && book.getCouponId() > 0) {
            UseParam useCouponParam = new UseParam();
            useCouponParam.setCouponId(book.getCouponId());
            useCouponParam.setMebId(book.getMemberId());
            useCouponParam.setOrderCode(order.getOrderNo());
            useCouponParam.setPlatformId(CouponPlatformType.fromResource(book.getResource())
                    .getPlatformId());
            Conditions conditions = new Conditions();
            useCouponParam.setConditions(conditions);
            conditions.setOrderAmount(orderCheckDetail.getCouponOrderAmount());
            if(CollectionUtils.isNotEmpty(orderCheckDetail.getCouponProductList())){
                conditions.setProductId(orderCheckDetail.getCouponProductList().get(0).getSpuId().intValue());
                conditions.setCategoryId(orderCheckDetail.getCouponProductList().get(0).getCategoryId().intValue());
            }
            ResultVo<BaseResponse> useCouponResult = couponService.useCoupon(useCouponParam);
            if (!useCouponResult.success()) {

                logger.error("优惠券使用失败, memberId:{}, couponId:{}, result:{}", book.getMemberId(),
                        book.getCouponId(), useCouponResult);

                // 事务回滚，归还库存
                boolean result = mallGoodsService.returnBatchStock(book.getGoodsList());
                if (!result) {
                    logger.error("下单扣减优惠券失败，回滚事务，归还库存失败，goodsJson:{}",
                            JsonUtils.toJsonString(book.getGoodsList()));
                }

                // 事务回滚，退还积分
                if (book.getSellStrategy().equals(2)) {
                    logger.info("使用优惠券失败，退还积分， memberId:{}, point:{}", book.getMemberId(),
                            book.getPoint());

                    ValueBean vb = new ValueBean();
                    vb.setPointvalue(book.getPoint());
                    vb.setMebId(book.getMemberId());
                    vb.setTrandNo(order.getOrderNo());
                    int mallAddPoint = pointService.mallAddPoint(vb);
                    if (mallAddPoint > 0) {
                        logger.error("下单事务回滚，退还积分失败，orderNo:{}, memberId:{}, point:{}",
                                order.getOrderNo(), book.getMemberId(), book.getPoint());
                        LogUtils.DISPERSED_ERROR_LOGGER.error(
                                "下单事务回滚，退还积分失败，orderNo:{}, memberId:{}, point:{}",
                                order.getOrderNo(), book.getMemberId(), book.getPoint());
                    }
                }
                throw new OrderException("系统正忙，使用优惠券失败，请重试！");
            }
        }

    }

    /**
     * 扣减积分
     * 
     * @param income
     * @param output
     * @throws Exception
     */
    public boolean minusPoint(int memberId, int point) throws Exception {
        ValueBean v = new ValueBean();
        v.setMebId(memberId);
        v.setPointvalue(-point);
        int r = pointService.mallMinusPoint(v);
        if (r > 0) {
            return false;
        } else {
            return true;
        }
    }

}
