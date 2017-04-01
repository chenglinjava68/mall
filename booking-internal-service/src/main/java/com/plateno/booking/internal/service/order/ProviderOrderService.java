package com.plateno.booking.internal.service.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.base.constant.PayStatusEnum;
import com.plateno.booking.internal.base.constant.PlateFormEnum;
import com.plateno.booking.internal.base.model.ProviderOrderDetailParam;
import com.plateno.booking.internal.base.model.ProviderOrderParam;
import com.plateno.booking.internal.bean.request.common.LstOrder;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.ConsigneeInfo;
import com.plateno.booking.internal.dao.mapper.ProviderOrderMapper;
import com.plateno.booking.internal.dao.pojo.ProviderOrder;
import com.plateno.booking.internal.dao.pojo.ProviderOrderDetail;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.order.build.OrderBuildService;
import com.plateno.booking.internal.service.order.build.ProviderOrderBuildService;

@Service
public class ProviderOrderService {

    @Autowired
    private ProviderOrderMapper providerOrderMapper;
    
    @Autowired
    private OrderBuildService orderBuildService;
    
    @Autowired
    private ProviderOrderBuildService providerOrderBuildService;
    
    public ResultVo<LstOrder<ProviderOrder>> queryOrderByPage(ProviderOrderParam param){
        ResultVo<LstOrder<ProviderOrder>> vo = new ResultVo<LstOrder<ProviderOrder>>();
        LstOrder<ProviderOrder> lst = new LstOrder<ProviderOrder>();
        vo.setData(lst);
        List<ProviderOrder> list = new ArrayList<ProviderOrder>();
        // 显示状态转变成数据库记录的状态
        if (param.getViewStatus() != null) {
            List<Integer> payStatus = PayStatusEnum.toPayStatus(param.getViewStatus());
            if (param.getStatusList() != null) {
                param.getStatusList().addAll(payStatus);
            } else {
                param.setStatusList(payStatus);
            }
        }
        
        list = providerOrderMapper.queryProviderOrder(param);
        buildProviderOrder(list,param);
        
        Integer count = providerOrderMapper.countProviderOrder(param);
        Double num = (Double.valueOf(count) / Double.valueOf(param.getPageNumber()));
        lst.setPageSize(param.getPageNumber());
        lst.setTotal(count);
        lst.setOrderList(list);
        lst.setTotalPage(new Double(Math.ceil(num)).intValue());
        return vo;
    }
    
    private void buildProviderOrder(List<ProviderOrder> list,ProviderOrderParam param){
        for(ProviderOrder provider : list){
            provider.setViewStatus(PayStatusEnum.toViewStatus(provider.getSubPayStatus()));
            providerOrderBuildService.buildProductInfosAndCal(provider);
            //查询收货人地址，采用前端查询，返回替换后的最新收件人姓名，地址，电话
            ConsigneeInfo consigneeInfo = orderBuildService.buildConsigneeInfo(provider.getOrderNo(), PlateFormEnum.APP.getPlateForm());
            provider.setConsigneeMobile(consigneeInfo.getMobile());
            provider.setConsigneeName(consigneeInfo.getConsigneeName());
            provider.setConsigneeAddress(consigneeInfo.getConsigneeAddress());
        }
    }
    
    
    public ResultVo<ProviderOrderDetail> queryOrderDetail(ProviderOrderDetailParam  param){
        ResultVo<ProviderOrderDetail> result = new ResultVo<ProviderOrderDetail>();
        ProviderOrderDetail detail = providerOrderMapper.queryProviderOrderDetail(param.getOrderSubNo());
        if(null == detail)
            return result;
        //查询收件人信息
        detail.setConsigneeInfo(orderBuildService.buildConsigneeInfo(detail.getOrderNo(), param.getPlateForm()));
        detail.setViewStatus(PayStatusEnum.toViewStatus(detail.getSubPayStatus()));
        providerOrderBuildService.buildPackage(detail);
        providerOrderBuildService.buildProductInfosAndCal(detail);
        result.setData(detail);
        return result;
    }
    
}
