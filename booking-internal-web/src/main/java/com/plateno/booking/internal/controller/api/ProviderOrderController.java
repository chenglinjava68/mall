package com.plateno.booking.internal.controller.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.plateno.booking.internal.base.model.ProviderOrderDetailParam;
import com.plateno.booking.internal.base.model.ProviderOrderParam;
import com.plateno.booking.internal.bean.request.common.LstOrder;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.controller.base.BaseController;
import com.plateno.booking.internal.dao.pojo.ProviderOrder;
import com.plateno.booking.internal.dao.pojo.ProviderOrderDetail;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.order.ProviderOrderService;
@RestController
@RequestMapping("/providerOrder")
public class ProviderOrderController  extends BaseController{

    @Autowired
    private ProviderOrderService providerOrderService;
    
    /**
     * 
    * @Title: queryOrderByPage 
    * @Description: 查询供应商订单列表
    * @param @param param
    * @param @param result
    * @param @return
    * @param @throws Exception    
    * @return ResultVo<LstOrder<ProviderOrder>>    
    * @throws
     */
    @ResponseBody
    @RequestMapping(value="/queryOrderByPage",method = RequestMethod.POST)
    public ResultVo<LstOrder<ProviderOrder>> queryOrderByPage(@RequestBody @Valid ProviderOrderParam  param,BindingResult result) throws Exception{
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
    
    /**
     * 
    * @Title: queryOrderDetail 
    * @Description: 查询供应商订单详情
    * @param @param param
    * @param @param result
    * @param @return
    * @param @throws Exception    
    * @return ResultVo<ProviderOrderDetail>    
    * @throws
     */
    @ResponseBody
    @RequestMapping(value="/queryOrderDetail",method = RequestMethod.POST)
    public ResultVo<ProviderOrderDetail> queryOrderDetail(@RequestBody @Valid ProviderOrderDetailParam  param,BindingResult result) throws Exception{
        log.info("查询订单详情,请求参数:"+ JsonUtils.toJsonString(param));
        bindingResultHandler(result);
        checkBaseParam(param);
        return providerOrderService.queryOrderDetail(param);
    }
    
}
