package com.plateno.booking.internal.base.mapper;


import java.util.List;

import com.plateno.booking.internal.base.pojo.MOrderCouponPO;
import com.plateno.booking.internal.base.vo.MOrderCouponSearchVO;

public interface MOrderCouponMapper extends BaseMapper {
	
	/**
	 * 根据搜索条件返回列表
	 * @param svo
	 * @return
	 */
	List<MOrderCouponPO> list(MOrderCouponSearchVO svo);
	
	/**
	 * 根据搜索条件返回列表总数
	 * @param svo
	 * @return
	 */
	int count(MOrderCouponSearchVO svo);
	
	/**
	 * 根据主键id获取单条记录
	 * @param id
	 * @return
	 */
	MOrderCouponPO get(Long id);
	
	/**
	 * 插入记录
	 * @param vo
	 */
	void insert(MOrderCouponPO po);
	
	/**
	 * 更新记录
	 * @param vo
	 */
	void update(MOrderCouponPO po);
	
	/**
	 * 根据主键id删除记录
	 * @param id
	 */
	void delete(Long id);

}
