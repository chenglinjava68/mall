package com.plateno.booking.internal.base.mapper;

import com.plateno.booking.internal.base.pojo.OrderPayLog;
import com.plateno.booking.internal.base.pojo.OrderPayLogExample;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface OrderPayLogMapper extends BaseMapper {
    int countByExample(OrderPayLogExample example);

    int deleteByExample(OrderPayLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(OrderPayLog record);

    int insertSelective(OrderPayLog record);

    List<OrderPayLog> selectByExampleWithRowbounds(OrderPayLogExample example, RowBounds rowBounds);

    List<OrderPayLog> selectByExample(OrderPayLogExample example);

    OrderPayLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") OrderPayLog record, @Param("example") OrderPayLogExample example);

    int updateByExample(@Param("record") OrderPayLog record, @Param("example") OrderPayLogExample example);

    int updateByPrimaryKeySelective(OrderPayLog record);

    int updateByPrimaryKey(OrderPayLog record);

    /**
     * 查询支付中的支付流水
     * @param startTime
     * @param endTime
     * @param num
     * @return
     */
	List<OrderPayLog> queryPayingLog(@Param("startTime")Date startTime, @Param("endTime")Date endTime, @Param("num")int num);
}