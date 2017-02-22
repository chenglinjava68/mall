package com.plateno.booking.internal.controller.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.plateno.booking.internal.base.model.ProviderOrderParam;
import com.plateno.booking.internal.bean.request.common.LstOrder;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.controller.base.BaseController;
import com.plateno.booking.internal.dao.pojo.ProviderOrderResponse;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.order.ProviderOrderService;
@RestController
@RequestMapping("/providerOrder")
public class ProviderOrderController  extends BaseController{

    @Autowired
    private ProviderOrderService providerOrderService;
    
    @ResponseBody
    @RequestMapping(value="/queryOrderByPage",method = RequestMethod.POST)
    public ResultVo<LstOrder<ProviderOrderResponse>> queryOrderByPage(@RequestBody @Valid ProviderOrderParam  param,BindingResult result) throws Exception{
        log.info("查询订单列表项,请求参数:"+ JsonUtils.toJsonString(param));
        bindingResultHandler(result);
        checkBaseParam(param);
        if (param.getPageNo() == null || param.getPageNo() == 0)
            param.setPageNo(1);
        if(param.getPageNo()==null||param.getPageNo()==0){
            param.setPageNo(1);
        }
        if(param.getPageNumber()==null||param.getPageNumber()==0){
            param.setPageNumber(10);
        }
        int pageNo=1;
        pageNo=(param.getPageNo()-1)*param.getPageNumber();
        param.setPageNumber(param.getPageNumber());
        param.setPageNo(pageNo);
        return providerOrderService.queryOrderByPage(param);
    }
    
}
