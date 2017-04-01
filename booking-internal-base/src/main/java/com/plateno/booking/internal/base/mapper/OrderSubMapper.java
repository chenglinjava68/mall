package com.plateno.booking.internal.base.mapper;

import com.plateno.booking.internal.base.pojo.OrderSub;
import com.plateno.booking.internal.base.pojo.OrderSubExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderSubMapper {
    int countByExample(OrderSubExample example);

    int deleteByExample(OrderSubExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderSub record);

    int insertSelective(OrderSub record);

    List<OrderSub> selectByExample(OrderSubExample example);

    OrderSub selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderSub record, @Param("example") OrderSubExample example);

    int updateByExample(@Param("record") OrderSub record, @Param("example") OrderSubExample example);

    int updateByPrimaryKeySelective(OrderSub record);

    int updateByPrimaryKey(OrderSub record);
}