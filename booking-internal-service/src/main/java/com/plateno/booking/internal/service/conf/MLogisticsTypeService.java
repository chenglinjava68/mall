package com.plateno.booking.internal.service.conf;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.base.mapper.MLogisticsTypeMapper;
import com.plateno.booking.internal.base.pojo.MLogisticsType;
import com.plateno.booking.internal.service.abs.AbsBookingService;




/**
 * 物流类型
 * @author mogt
 * @date 2016年11月29日
 */
@Service
public class MLogisticsTypeService extends AbsBookingService {

	protected final static Logger logger = LoggerFactory.getLogger(MLogisticsTypeService.class);

	@Autowired
	private MLogisticsTypeMapper mapper;

	/**
	 * 获取所有数据
	 * @return
	 */
	public List<MLogisticsType> listAll() {
		return mapper.listAll();
	}
}
