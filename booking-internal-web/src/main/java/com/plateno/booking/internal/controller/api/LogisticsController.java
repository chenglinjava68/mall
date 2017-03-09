package com.plateno.booking.internal.controller.api;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.plateno.booking.internal.base.constant.PlateFormEnum;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.contants.LogisticsEnum;
import com.plateno.booking.internal.bean.request.custom.DeliverOrderParam;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.bean.request.custom.ReceiptParam;
import com.plateno.booking.internal.bean.request.logistics.OrderLogisticsQueryReq;
import com.plateno.booking.internal.bean.response.logistics.PackageProduct;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.conf.data.LogisticsTypeData;
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
        ResultVo<List<PackageProduct>> resultVo = new ResultVo<List<PackageProduct>>();
        bindingResultHandler(result);
        checkBaseParam(param);
        if(StringUtils.isBlank(param.getOrderNo()) && StringUtils.isBlank(param.getOrderSubNo())){
            resultVo.setResultCode(this.getClass(), BookingResultCodeContants.MsgCode.BAD_REQUEST.getMsgCode());
            resultVo.setResultMsg("父订单号或者子订单号不能为空");
            return resultVo;
        }
        
        
        resultVo.setData(logisticsService.queryOrderLogistics(param));
        return resultVo;
    }
    
    @ResponseBody
    @RequestMapping(value = "/deliverGoods" ,method = RequestMethod.POST)
    public ResultVo<Object> deliverGoods(@RequestBody @Valid DeliverOrderParam param,BindingResult result) throws Exception{
        log.info("订单发货通知接口,请求参数:"+ JsonUtils.toJsonString(param));
        bindingResultHandler(result);
        checkBaseParam(param);
        
        if(StringUtils.isBlank(param.getOperateUserid())) {
            ResultVo<Object> response = new ResultVo<Object>();
            response.setResultCode(this.getClass(), BookingResultCodeContants.MsgCode.BAD_REQUEST.getMsgCode());
            response.setResultMsg("请输入操作人ID");
            return response;
        }
        
        if(StringUtils.isBlank(param.getOperateUsername())) {
            ResultVo<Object> response = new ResultVo<Object>();
            response.setResultCode(this.getClass(), BookingResultCodeContants.MsgCode.BAD_REQUEST.getMsgCode());
            response.setResultMsg("请输入操作用户");
            return response;
        }
        
        if(CollectionUtils.isEmpty(param.getDeliverGoodParams())){
            ResultVo<Object> response = new ResultVo<Object>();
            response.setResultCode(this.getClass(), BookingResultCodeContants.MsgCode.BAD_REQUEST.getMsgCode());
            response.setResultMsg("包裹数据为空");
            return response;
        }
        
        return logisticsService.deliverOrder(param);
    }
    
    @ResponseBody
    @RequestMapping(value = "/modifydeliverInfo" ,method = RequestMethod.POST)
    public ResultVo<Object> modifydeliverInfo(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
        log.info("修改发货信息,请求参数:"+ JsonUtils.toJsonString(param));
        bindingResultHandler(result);
        checkBaseParam(param);
        
        if(StringUtils.isBlank(param.getOperateUserid())) {
            ResultVo<Object> response = new ResultVo<Object>();
            response.setResultCode(this.getClass(), BookingResultCodeContants.MsgCode.BAD_REQUEST.getMsgCode());
            response.setResultMsg("请输入操作人ID");
            return response;
        }
        
        if(StringUtils.isBlank(param.getOperateUsername())) {
            ResultVo<Object> response = new ResultVo<Object>();
            response.setResultCode(this.getClass(), BookingResultCodeContants.MsgCode.BAD_REQUEST.getMsgCode());
            response.setResultMsg("请输入操作用户");
            return response;
        }
        
        if(param.getLogisticsType() == null || !LogisticsTypeData.hasType(param.getLogisticsType())) {
            ResultVo<Object> response = new ResultVo<Object>();
            response.setResultCode(this.getClass(), BookingResultCodeContants.MsgCode.BAD_REQUEST.getMsgCode());
            response.setResultMsg("请输入正确的物流类型:" + param.getLogisticsType());
            return response;
        }
        
        //自提不去要物流编号
        if(LogisticsEnum.from(param.getLogisticsType()) != LogisticsEnum.ZT) {
            if(StringUtils.isBlank(param.getLogisticsNo())) {
                ResultVo<Object> response = new ResultVo<Object>();
                response.setResultCode(this.getClass(), BookingResultCodeContants.MsgCode.BAD_REQUEST.getMsgCode());
                response.setResultMsg("请输入物流编号");
                return response;
            }
        }

        return logisticsService.modifydeliverOrder(param);
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/enterReceipt" ,method = RequestMethod.POST)
    public ResultVo<Object> enterReceipt(@RequestBody @Valid MOrderParam param,BindingResult result) throws Exception{
        log.info("确定收货的操作,请求参数:"+ JsonUtils.toJsonString(param));
        bindingResultHandler(result);
        checkBaseParam(param);
        if(param.getPlateForm() != null && (param.getPlateForm() == PlateFormEnum.ADMIN.getPlateForm() || param.getPlateForm() == PlateFormEnum.PROVIDER_ADMIN.getPlateForm())) {
            
            if(StringUtils.isBlank(param.getOperateUserid())) {
                ResultVo<Object> out = new ResultVo<Object>();
                out.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
                out.setResultMsg("请输入操作人ID");
                return out;
            }
            if(StringUtils.isBlank(param.getOperateUsername())) {
                ResultVo<Object> out = new ResultVo<Object>();
                out.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
                out.setResultMsg("请输入操作人姓名");
                return out;
            }
        }
        return logisticsService.enterReceipt(param);
    }
    
    @ResponseBody
    @RequestMapping(value = "/modifyReceiptInfo" ,method = RequestMethod.POST)
    public ResultVo<Object> modifyReceiptInfo(@RequestBody @Valid ReceiptParam param,BindingResult result) throws Exception{
        log.info("修改收货人请求参数:"+ JsonUtils.toJsonString(param));
        bindingResultHandler(result);
        checkBaseParam(param);
        
        return logisticsService.modifyReceiptInfo(param);
    }
    
}
