/**
 * 
 */
package com.plateno.booking.internal.service.abs;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.bean.request.custom.MOrderGoodsParam;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.fromTicket.vo.MAddBookingIncomeVo;

/**
 * @author user
 *
 */
public abstract class AbsParamVerifyService {

    /**
     * 是否包邮
     */
    private static List<Integer> shipTypeList = Arrays.asList(1, 2);
    /**
     * 销售策略
     */
    private static List<Integer> sellStrategyList = Arrays.asList(1, 2);

    protected void verifyIncome(MAddBookingIncomeVo income, ResultVo<?> output) throws IOException {
        MAddBookingParam addBookingParam = income.getAddBookingParam();
        if (addBookingParam == null) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("下单失败,下单对象不能为空");
            return;
        }
        //v1.3新增，支持批量新增商品
        if (CollectionUtils.isEmpty(addBookingParam.getGoodsList())) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("下单失败，商品集合为空");
            return;
        }
        for (MOrderGoodsParam goodsParam : addBookingParam.getGoodsList()) {
            if (null == goodsParam.getGoodsId() || goodsParam.getGoodsId() <= 0) {
                output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
                output.setResultMsg("下单失败,商品ID不能为空");
                return;
            }
            if (null == goodsParam.getQuantity() || goodsParam.getQuantity() <= 0) {
                output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
                output.setResultMsg("下单失败,数量必须大于0");
                return;
            }
        }
        if (addBookingParam.getTotalAmount() < 0) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("下单失败,订单总价必须大于0");
            return;
        }
        if (addBookingParam.getTotalAmount() == 0
                && (addBookingParam.getCouponId() == null || addBookingParam.getCouponId() <= 0)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("下单失败,订单总价必须大于0");
            return;
        }
        if (addBookingParam.getPlatformId() <= 0) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("下单失败,平台来源,不能为空");
            return;
        }
        if (addBookingParam.getResource() <= 0) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("下单失败,订单来源不能为空");
            return;
        }

        if (addBookingParam.getMemberId() == null || addBookingParam.getMemberId() <= 0) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("下单失败,会员ID不能为空");
            return;
        }

        if (!sellStrategyList.contains(addBookingParam.getSellStrategy())) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("下单失败,销售策略错误");
            return;
        }

        if (!shipTypeList.contains(addBookingParam.getShippingType())) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("下单失败,配送方式错误");
            return;
        }

        if (addBookingParam.getSellStrategy() == 2) {
            if (addBookingParam.getPoint() == null || addBookingParam.getPoint() <= 0) {
                output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
                output.setResultMsg("下单失败,积分错误");
                return;
            }
        }

        if (addBookingParam.getSubResource() != null && addBookingParam.getSubResource() <= 0) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("下单失败,订单子来源错误:" + addBookingParam.getSubResource());
            return;
        }

        // 因为一开始省市县没有分开传输，所以为了兼容不要求必传，如果不传填空串
        addBookingParam.setProvince(StringUtils.trimToEmpty(addBookingParam.getProvince()));
        addBookingParam.setCity(StringUtils.trimToEmpty(addBookingParam.getCity()));
        addBookingParam.setArea(StringUtils.trimToEmpty(addBookingParam.getArea()));
    }


}
