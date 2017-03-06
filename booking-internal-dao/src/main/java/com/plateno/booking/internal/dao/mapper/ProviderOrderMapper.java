package com.plateno.booking.internal.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.plateno.booking.internal.base.model.ProviderOrderParam;
import com.plateno.booking.internal.dao.pojo.ProviderOrder;
import com.plateno.booking.internal.dao.pojo.ProviderOrderDetail;

public interface ProviderOrderMapper {

    /**
     * 
    * @Title: queryProviderOrder 
    * @Description: 供应商订单列表
    * @param @param param
    * @param @return    
    * @return List<ProviderOrder>    
    * @throws
     */
    List<ProviderOrder> queryProviderOrder(@Param("record")ProviderOrderParam param);
    
    Integer countProviderOrder(@Param("record")ProviderOrderParam param);
    
    /**
     * 
    * @Title: queryProviderOrderDetail 
    * @Description: 查询订单详情
    * @param @param orderSubNo
    * @param @return    
    * @return ProviderOrderDetail    
    * @throws
     */
    ProviderOrderDetail queryProviderOrderDetail(@Param("orderSubNo")String orderSubNo);
    
}
