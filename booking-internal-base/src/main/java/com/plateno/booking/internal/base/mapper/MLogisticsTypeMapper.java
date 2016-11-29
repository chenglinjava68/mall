package com.plateno.booking.internal.base.mapper;

import com.plateno.booking.internal.base.pojo.MLogisticsType;
import com.plateno.booking.internal.base.pojo.MLogisticsTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MLogisticsTypeMapper {
    int countByExample(MLogisticsTypeExample example);

    int deleteByExample(MLogisticsTypeExample example);

    int deleteByPrimaryKey(Integer type);

    int insert(MLogisticsType record);

    int insertSelective(MLogisticsType record);

    List<MLogisticsType> selectByExample(MLogisticsTypeExample example);

    MLogisticsType selectByPrimaryKey(Integer type);

    int updateByExampleSelective(@Param("record") MLogisticsType record, @Param("example") MLogisticsTypeExample example);

    int updateByExample(@Param("record") MLogisticsType record, @Param("example") MLogisticsTypeExample example);

    int updateByPrimaryKeySelective(MLogisticsType record);

    int updateByPrimaryKey(MLogisticsType record);

    /**
     * 获取所有类型
     * @return
     */
	List<MLogisticsType> listAll();
}