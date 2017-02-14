package com.plateno.booking.internal.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.plateno.booking.internal.bean.request.logistics.OrderLogisticsQueryReq;
import com.plateno.booking.internal.bean.response.logistics.PackageProduct;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.controller.base.BaseController;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.logistics.LogisticsService;

@RestController
@RequestMapping("/logistics")
public class LogisticsController extends BaseController{
    private final static Logger log = Logger.getLogger(LogisticsController.class);
    
    @Autowired
    private LogisticsService logisticsService;
    
    @ResponseBody
    @RequestMapping(value="/queryOrderLogistics",method = RequestMethod.POST)
    public ResultVo<List<PackageProduct>> queryOrderLogistics(@RequestBody @Valid OrderLogisticsQueryReq  param,BindingResult result) throws Exception{
        log.info("查询订单物流信息,请求参数:"+ JsonUtils.toJsonString(param));
        bindingResultHandler(result);
        checkBaseParam(param);
        ResultVo<List<PackageProduct>> resultVo = new ResultVo<List<PackageProduct>>();
        resultVo.setData(logisticsService.queryOrderLogistics(param));
        return resultVo;
    }
    
}
