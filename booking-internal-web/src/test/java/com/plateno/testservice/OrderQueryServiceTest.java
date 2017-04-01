package com.plateno.testservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.plateno.booking.internal.base.model.SelectOrderParam;
import com.plateno.booking.internal.base.vo.MOrderSearchVO;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.common.LstOrder;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.bean.response.custom.OrderDetail;
import com.plateno.booking.internal.bean.response.custom.SelectOrderResponse;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.order.OrderQueryService;
import com.plateno.booking.internal.util.vo.PageInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OrderQueryServiceTest {

    @Autowired
    private OrderQueryService orderQueryService;
    
    @Test
    public void testGetOrderDetail() throws OrderException, Exception{
        
        
        MOrderParam orderParam = new MOrderParam();
        orderParam.setOrderNo("O1484130275182529269");
        //orderParam.setMemberId(135964714);
        //orderParam.setChannelId(1);
        orderParam.setPlateForm(1);
        ResultVo<OrderDetail> orderDetail = orderQueryService.getOrderDetail(orderParam );
        System.out.println("结果：" + orderDetail);
    }
    
    @Test
    public void testQueryOrderByPage() throws OrderException, Exception{
        
        
        SelectOrderParam param = new SelectOrderParam();
        //param.setPlateForm(3);
        param.setOrderNo("O1483584685913632952");
        //param.setResource(1);
        //param.setBookingStartDate(DateUtil.dateToFormatStr("2016-11-03 08:00:01", "yyyy-MM-dd HH:mm:ss"));
        //param.setBookingEndDate(DateUtil.dateToFormatStr("2016-11-09 08:00:01", "yyyy-MM-dd HH:mm:ss"));
        //param.setViewStatus(0);
        //param.setMemberId("181295316");
        /*param.setChannelId(1);
        List<Integer> statusList = new ArrayList<>();
        statusList.add(2);
        param.setStatusList(statusList);*/
        //param.setName("四");
        
        ResultVo<LstOrder<SelectOrderResponse>> queryOrderByPage = orderQueryService.queryOrderByPage(param);
        System.out.println(queryOrderByPage);
        
    }
    
    
}
