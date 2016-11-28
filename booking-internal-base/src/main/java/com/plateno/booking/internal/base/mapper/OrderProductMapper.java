package com.plateno.booking.internal.base.mapper;

import org.apache.ibatis.annotations.Param;


public interface OrderProductMapper extends BaseMapper {

	/**
	 * 查询会员已经购买的商品的数量（只要有支付成功，就算是退款也算是已经购买的数量了）
	 * @param memberId
	 * @param productId
	 * @return
	 */
	int queryUserProductSum(@Param("memberId")int memberId, @Param("productId")int productId);
   
}