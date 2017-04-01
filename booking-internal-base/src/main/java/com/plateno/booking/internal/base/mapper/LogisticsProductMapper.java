package com.plateno.booking.internal.base.mapper;

import com.plateno.booking.internal.base.pojo.LogisticsProduct;
import com.plateno.booking.internal.base.pojo.LogisticsProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogisticsProductMapper {
    int countByExample(LogisticsProductExample example);

    int deleteByExample(LogisticsProductExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LogisticsProduct record);

    int insertSelective(LogisticsProduct record);

    List<LogisticsProduct> selectByExample(LogisticsProductExample example);

    LogisticsProduct selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LogisticsProduct record, @Param("example") LogisticsProductExample example);

    int updateByExample(@Param("record") LogisticsProduct record, @Param("example") LogisticsProductExample example);

    int updateByPrimaryKeySelective(LogisticsProduct record);

    int updateByPrimaryKey(LogisticsProduct record);
}