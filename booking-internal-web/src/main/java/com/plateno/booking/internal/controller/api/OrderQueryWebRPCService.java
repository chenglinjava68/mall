package com.plateno.booking.internal.controller.api;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.plateno.booking.internal.base.model.SelectOrderParam;
import com.plateno.booking.internal.bean.request.common.LstOrder;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.bean.response.custom.OrderDetail;
import com.plateno.booking.internal.bean.response.custom.SelectOrderResponse;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.controller.base.BaseController;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.order.OrderQueryService;

@RestController
@RequestMapping("/mOrderService")
public class OrderQueryWebRPCService extends BaseController{

    private final static Logger log = LoggerFactory.getLogger(OrderQueryWebRPCService.class);
    
    @Autowired
    private OrderQueryService orderQueryService;
    
    @ResponseBody
    @RequestMapping(value="/queryOrderByPage",method = RequestMethod.POST)
    public ResultVo<LstOrder<SelectOrderResponse>> queryOrderByPage(@RequestBody @Valid SelectOrderParam  param,BindingResult result) throws Exception{
        log.info("查询订单列表项,请求参数:"+ JsonUtils.toJsonString(param));
        bindingResultHandler(result);
        checkBaseParam(param);
        return orderQueryService.queryOrderByPage(param);
    }
    

    @ResponseBody
    @RequestMapping(value = "/getOrderDetail",method = RequestMethod.POST)
    public ResultVo<OrderDetail> getOrderDetail(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
        log.info("查询订单详情,请求参数:"+ JsonUtils.toJsonString(param));
        bindingResultHandler(result);
        checkBaseParam(param);
        
        return orderQueryService.getOrderDetail(param);
    }
    
    @ResponseBody
    @RequestMapping(value = "/getPaySuccessDetail" ,method = RequestMethod.POST)
    public ResultVo<OrderDetail> getPaySuccessDetail(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
        log.info("购买成功页面参数请求:"+ JsonUtils.toJsonString(param));
        bindingResultHandler(result);
        checkBaseParam(param);
        
        return orderQueryService.getPaySuccessDetail(param);
    }
    
    @ResponseBody
    @RequestMapping(value = "/getOrderInfo" ,method = RequestMethod.POST)
    public ResultVo<Object> getOrderInfo(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
        log.info("获取订单状态参数，用于前端轮询支付结果:"+ JsonUtils.toJsonString(param));
        bindingResultHandler(result);
        //checkBaseParam(param);
        return orderQueryService.getOrderInfo(param);
    }
    
}
