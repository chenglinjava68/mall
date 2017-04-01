package com.plateno.booking.internal.service.log;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.base.mapper.OperatelogMapper;
import com.plateno.booking.internal.base.pojo.Operatelog;
import com.plateno.booking.internal.bean.request.custom.MOperateLogParam;

@Service("operateLogService")
public class OperateLogService {
	
	@Autowired
	private OperatelogMapper operatelogMapper;
	
	public void saveOperateLog(MOperateLogParam orderParam){
		Operatelog logs=new Operatelog();
		BeanUtils.copyProperties(orderParam, logs);
		logs.setOperateTime(new Date());
		logs.setOperateUsername(orderParam.getOperateUsername());
		operatelogMapper.insertSelective(logs);
	}
	
}
