package com.plateno.booking.internal.bean.contants;

import org.apache.commons.lang3.StringUtils;

/**
 * @author user
 *
 */
public enum ServiceEnum {
	
	/**
	 * lvmama
	 */
	LVMAMA_ADD_BOOKING(1,"_LvMaMa_", BookingActionConstants.ADD),

	LVMAMA_CANCEL_BOOKING(1,"_LvMaMa_", BookingActionConstants.CANCEL),


	/**
	 * tongcheng
	 */
	TONGCHENG_ADD_BOOKING(2,"_TongCheng_", BookingActionConstants.ADD),

	TONGCHENG_CANCEL_BOOKING(2,"_TongCheng_", BookingActionConstants.CANCEL),

	
	/**
	 * yaochufa
	 */
	YAOCHUFA_ADD_BOOKING(3,"_TongCheng_", BookingActionConstants.ADD),

	YAOCHUFA_CANCEL_BOOKING(3,"_TongCheng_", BookingActionConstants.CANCEL),
	
	
	/**
	 * activity
	 */
	ACTIVITY_ADD_BOOKING(100,"_Activity_", BookingActionConstants.ADD),

	ACTIVITY_CANCEL_BOOKING(100,"_Activity_", BookingActionConstants.CANCEL),

	
	
	/**
	 * plateno
	 */
	PLATENO_ADD_BOOKING(101,"_PLATENO_", BookingActionConstants.ADD),

	PLATENO_CANCEL_BOOKING(101,"_PLATENO_", BookingActionConstants.CANCEL),
	
	
	
	/**
	 * UNKNOW
	 */
	UNKNOW(0,"", "");
	
	private Integer channel;

	/**
	 * 渠道 to
	 */
	private String channelSourceType;

	/**
	 * 操作
	 */
	private String action;

	/**
	 * 构造器
	 * 
	 * @param channelSourceType
	 */
	private ServiceEnum(Integer channel,String channelSourceType, String action) {
		this.channel = channel;
		this.channelSourceType = channelSourceType;
		this.action = action;
	}
	
	public Integer getChannel() {
		return channel;
	}

	/**
	 * @return
	 */
	public String getChannelSourceType() {
		return channelSourceType;
	}

	/**
	 * @param channelSourceType
	 */
	public void setChannelSourceType(String channelSourceType) {
		this.channelSourceType = channelSourceType;
	}



	/**
	 * @return
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * 根据渠道来源和房源获取服务类型
	 * 
	 * @param channelSourceType
	 */
	public static ServiceEnum getServiceEnum(Integer channel, String action) {
		if (StringUtils.isBlank(action)) {
			return UNKNOW;
		}
		if (channel <= 0) {
			return UNKNOW;
		}

		for (ServiceEnum serviceEnum : ServiceEnum.values()) {
			if (serviceEnum.getChannel().equals(channel) && action.equals(serviceEnum.getAction())) {
				return serviceEnum;
			}
		}
		return UNKNOW;
	}

}
