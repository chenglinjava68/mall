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

	/**
	 * 更新商品的返回库存数量
	 * @param skuCount
	 * @param id
	 * @return
	 */
	int updateReturnSkuCount(@Param("returnSkuCount")Integer returnSkuCount, @Param("id")Integer id);

	/**
	 * 查询库存已经售出的数量
	 * @param skuId
	 * @return
	 */
	int querySkuSoldNum(@Param("skuId")Integer skuId);
   
}