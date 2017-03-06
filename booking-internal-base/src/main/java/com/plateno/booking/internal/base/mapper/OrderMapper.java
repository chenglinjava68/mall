package com.plateno.booking.internal.base.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.plateno.booking.internal.base.model.SelectOrderParam;
import com.plateno.booking.internal.base.model.bill.BillOrderDetail;
import com.plateno.booking.internal.base.model.bill.ProdSellAmountData;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.base.pojo.OrderExample;
import com.plateno.booking.internal.base.vo.MOrderSearchVO;




public interface OrderMapper extends BaseMapper<Order,OrderExample> {
  
	List<Order> getOrderByNo(@Param("orderNo")String orderNo);
	
	List<Order> getPre30Min(@Param("status")Integer  status);
	
	List<Order> getOrderByStatusAndDeliverTime(@Param("status")Integer  status,@Param("day")Integer  days);
	
	
	List<Order> getPageOrders(@Param("record")SelectOrderParam selectOrderParam);
	
	
	Integer  getCountOrder(@Param("record")SelectOrderParam selectOrderParam);
	
	BillOrderDetail  getOrderNoByTradeNo(@Param("tradeNo")String tradeNo);
	
	List<ProdSellAmountData> getPruSellAmountByPreDay(@Param("days")Integer  days);

	/**
	 * 查询支付中的订单，且流水在5分钟前生成（避免太快查询，支付网关还没有生成订单）
	 * @return
	 */
	List<Order> getPayingAndPayLogPre5Min(@Param("status")Integer  status);

	/**
	 * 根据订单号和会员ID查询
	 * @param orderNo
	 * @param orderNo
	 * @return
	 */
	List<Order> getOrderByNoAndMemberId(@Param("orderNo")String orderNo, @Param("memberId")Integer memberId);
	
	/**
	 * 根据订单号和会员ID查询、渠道ID查询
	 * @param orderNo
	 * @param orderNo
	 * @return
	 */
	List<Order> getOrderByNoAndMemberIdAndChannelId(@Param("orderNo")String orderNo, @Param("memberId")Integer memberId, @Param("channelId")Integer channelId);
	
	/**
	 * 获取并加写锁
	 * @param orderNo
	 * @return
	 */
	Order getByOrderNoForUpdate(@Param("orderNo")String orderNo);

	/**
	 * 查询列表
	 * @param svo
	 * @return
	 */
	List<Order> list(MOrderSearchVO svo);

	/**
	 * 查询列表的总记录数
	 * @param svo
	 * @return
	 */
	int count(MOrderSearchVO svo);
	
	/**
     * 获取并加写锁
     * @param orderId
     * @return
     */
    Order getByOrderIdForUpdate(@Param("orderId")Integer orderId);
	
}