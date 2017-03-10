package com.plateno.booking.internal.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface LogisticsMapperExt {

    
    int insertBatch(@Param("packageId")Integer packageId,@Param("orderProductIds")List<Integer> orderProductIds);
    
    
    void delPackageByOrderSubNo(@Param("orderSubNo")String orderSubNo);
    
    void delPackageProductByOrderSubNo(@Param("orderSubNo")String orderSubNo);
    
    /**
     * 
    * @Title: queryOrderSubCount 
    * @Description: 查询订单的子订单包裹数量
    * @param @param orderNo
    * @param @return    
    * @return Integer    
    * @throws
     */
    Integer queryOrderSubCount(@Param("orderNo")String orderNo);
    
}
