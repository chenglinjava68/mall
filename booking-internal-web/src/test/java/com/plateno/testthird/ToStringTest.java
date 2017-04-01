package com.plateno.testthird;

import java.util.List;

import com.google.common.collect.Lists;
import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.goods.vo.OrderCheckParamInfo;
import com.plateno.booking.internal.goods.vo.OrderCheckReq;
import com.plateno.booking.internal.service.fromTicket.vo.MAddBookingIncomeVo;

public class ToStringTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        MAddBookingIncomeVo income = new MAddBookingIncomeVo();
        MAddBookingParam addBookingParam = new MAddBookingParam();
        addBookingParam.setArea("aaaa");
        income.setAddBookingParam(addBookingParam);
        income.setOrderNo("bbbbb");
        System.out.println(income.toString());
        
        OrderCheckReq req = new OrderCheckReq();
        req.setMemberPoints(233);
        List<OrderCheckParamInfo> orderCheckParamInfos = Lists.newArrayList();
        OrderCheckParamInfo orderCheckParamInfo = new OrderCheckParamInfo();
        orderCheckParamInfo.setGoodsId(111L);
        orderCheckParamInfos.add(orderCheckParamInfo);
        req.setOrderCheckParamInfos(orderCheckParamInfos);
        System.out.println(req.toString());
    }

}
