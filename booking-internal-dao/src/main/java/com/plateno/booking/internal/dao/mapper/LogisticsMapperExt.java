package com.plateno.booking.internal.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface LogisticsMapperExt {

    
    int insertBatch(@Param("packageId")Integer packageId,@Param("orderProductIds")List<Integer> orderProductIds);
    
    
    void delPackageByOrderSubNo(@Param("orderSubNo")String orderSubNo);
    
    void delPackageProductByOrderSubNo(@Param("orderSubNo")String orderSubNo);
    
}
