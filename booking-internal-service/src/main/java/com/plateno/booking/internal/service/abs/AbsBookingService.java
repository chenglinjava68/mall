package com.plateno.booking.internal.service.abs;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.plateno.booking.internal.bean.contants.ServiceEnum;
public class AbsBookingService {

	private static Logger log = Logger.getLogger(AbsBookingService.class);

	/**
	 * 类的内部使用 根据渠道来源区分使用的service
	 * 
	 * @param roomSourceType
	 * @param channelSourceType
	 * @return
	 */
	protected static String getBookingService(Integer channelSourceType, String action) {
		
		//统一使用101, 目前所有渠道使用的service相同
		channelSourceType = ServiceEnum.PLATENO_ADD_BOOKING.getChannel();

		String channelSourceTypeStr = "";
		if (null != channelSourceType) {
			channelSourceTypeStr = channelSourceType.toString();
		}
		if (StringUtils.isBlank(action)) {
			action = "";
		}
		
		String serviceEnum = channelSourceTypeStr + ServiceEnum.getServiceEnum(channelSourceType, action).getChannelSourceType() + action;
		if(log.isDebugEnabled()) {
			log.debug("当前使用的服务是" + serviceEnum);
		}
		return serviceEnum;
	}
	
	public static void main(String[] args) {
		System.out.println(getBookingService(101, "ADD"));
	}
}
