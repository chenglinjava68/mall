package com.plateno.booking.internal.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.plateno.booking.internal.base.model.SelectOrderParam;
import com.plateno.booking.internal.base.model.bill.BillOrderDetail;
import com.plateno.booking.internal.base.model.bill.ProdSellAmountData;
import com.plateno.booking.internal.base.pojo.Order;




public interface OrderMapper extends BaseMapper {
  
	List<Order> getOrderByNo(@Param("orderNo")String orderNo);
	
	List<Order> getPre30Min(@Param("status")Integer  status);
	
	List<Order> getOrderByStatus(@Param("status")Integer  status,@Param("day")Integer  days);
	
	
	List<Order> getPageOrders(@Param("record")SelectOrderParam selectOrderParam);
	
	
	Integer  getCountOrder(@Param("record")SelectOrderParam selectOrderParam);
	
	BillOrderDetail  getOrderNoByTradeNo(@Param("tradeNo")String tradeNo);
	
	List<ProdSellAmountData> getPruSellAmountByPreDay(@Param("days")Integer  days);

	/**
	 * 查询支付中的订单，且流水在5分钟前生成（避免太快查询，支付网关还没有生成订单）
	 * @return
	 */
	List<Order> getPayingAndPayLogPre5Min(@Param("status")Integer  status);

	
}