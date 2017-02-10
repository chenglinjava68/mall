package com.plateno.booking.internal.base.mapper;

import com.plateno.booking.internal.base.pojo.LogisticsPackage;
import com.plateno.booking.internal.base.pojo.LogisticsPackageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LogisticsPackageMapper {
    int countByExample(LogisticsPackageExample example);

    int deleteByExample(LogisticsPackageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LogisticsPackage record);

    int insertSelective(LogisticsPackage record);

    List<LogisticsPackage> selectByExample(LogisticsPackageExample example);

    LogisticsPackage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LogisticsPackage record, @Param("example") LogisticsPackageExample example);

    int updateByExample(@Param("record") LogisticsPackage record, @Param("example") LogisticsPackageExample example);

    int updateByPrimaryKeySelective(LogisticsPackage record);

    int updateByPrimaryKey(LogisticsPackage record);
}