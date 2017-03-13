package com.plateno.booking.internal.service.order;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.base.constant.PayStatusEnum;
import com.plateno.booking.internal.base.mapper.LogisticsPackageMapper;
import com.plateno.booking.internal.base.mapper.OrderMapper;
import com.plateno.booking.internal.base.mapper.OrderProductMapper;
import com.plateno.booking.internal.base.model.ProviderOrderDetailParam;
import com.plateno.booking.internal.base.model.ProviderOrderParam;
import com.plateno.booking.internal.base.pojo.LogisticsPackage;
import com.plateno.booking.internal.base.pojo.LogisticsPackageExample;
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
    private OrderMapper mallOrderMapper;
    
    @Autowired
    private ProviderOrderMapper providerOrderMapper;
    
    @Autowired
    private OrderProductMapper orderProductMapper;
    
    @Autowired
    private LogisticsPackageMapper packageMapper;
    
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
            //如父订单状态为已发货，则查询是否有包裹，如无，则修改为未发货
            if(provider.getViewStatus() == PayStatusEnum.PAY_STATUS_4.getViewStstus()){
                LogisticsPackageExample example = new LogisticsPackageExample();
                example.createCriteria().andOrderSubNoEqualTo(provider.getOrderSubNo());
                List<LogisticsPackage> packageList = packageMapper.selectByExample(example);
                if(CollectionUtils.isEmpty(packageList)){
                    provider.setViewStatus(PayStatusEnum.PAY_STATUS_3.getViewStstus());
                    provider.setSubPayStatus(PayStatusEnum.PAY_STATUS_3.getPayStatus());
                }
            }
            providerOrderBuildService.buildProductInfosAndCal(provider);
            //查询收货人地址，采用前端查询，返回替换后的最新收件人姓名，地址，电话
            ConsigneeInfo consigneeInfo = orderBuildService.buildConsigneeInfo(provider.getOrderNo(), 3);
            provider.setConsigneeMobile(consigneeInfo.getMobile());
            provider.setConsigneeName(consigneeInfo.getConsigneeName());
            provider.setConsigneeAddress(consigneeInfo.getConsigneeAddress());
        }
    }
    
    
    public ResultVo<ProviderOrderDetail> queryOrderDetail(ProviderOrderDetailParam  param){
        ResultVo<ProviderOrderDetail> result = new ResultVo<ProviderOrderDetail>();
        ProviderOrderDetail detail = providerOrderMapper.queryProviderOrderDetail(param.getOrderSubNo());
        //查询收件人信息
        detail.setConsigneeInfo(orderBuildService.buildConsigneeInfo(detail.getOrderNo(), param.getPlateForm()));
        
        //如父订单状态为已发货，则查询是否有包裹，如无，则修改为未发货
        detail.setViewStatus(PayStatusEnum.toViewStatus(detail.getSubPayStatus()));
        //查询包裹
        LogisticsPackageExample example = new LogisticsPackageExample();
        example.createCriteria().andOrderSubNoEqualTo(detail.getOrderSubNo());
        List<LogisticsPackage> logisticsPackageList = packageMapper.selectByExample(example);
        if(detail.getViewStatus() == PayStatusEnum.PAY_STATUS_4.getViewStstus()){
            if(CollectionUtils.isNotEmpty(logisticsPackageList)){
                detail.setViewStatus(PayStatusEnum.PAY_STATUS_3.getViewStstus());
            }
        }
        //查询快递单信息
        if(CollectionUtils.isNotEmpty(logisticsPackageList)){
            providerOrderBuildService.buildPackage(logisticsPackageList, detail);
        }
        providerOrderBuildService.buildProductInfosAndCal(detail);
        result.setData(detail);
        return result;
    }
    
}
