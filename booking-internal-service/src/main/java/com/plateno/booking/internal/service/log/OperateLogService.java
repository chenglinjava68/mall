package com.plateno.booking.internal.service.log;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.base.mapper.OperatelogMapper;
import com.plateno.booking.internal.base.pojo.Operatelog;
import com.plateno.booking.internal.bean.request.custom.MOperateLogParam;
import com.plateno.booking.internal.common.util.LogUtils;

@Service("operateLogService")
public class OperateLogService {
	
	@Autowired
	private OperatelogMapper operatelogMapper;
	
	public void saveOperateLog(MOperateLogParam orderParam) throws  Exception {
		Operatelog logs=new Operatelog();
		BeanUtils.copyProperties(orderParam, logs);
		logs.setOperateTime(new Date());
		logs.setOperateUsername(orderParam.getOperateUserName());
		if(operatelogMapper.insertSelective(logs)>0){
			LogUtils.sysLoggerInfo(String.format("操作日志记录成功,[%s]",orderParam.getOrderCode()));
		}else{
			LogUtils.sysLoggerInfo(String.format("操作日志记录失败,[%s]",orderParam.getOrderCode()));
		}
	}
	
}
