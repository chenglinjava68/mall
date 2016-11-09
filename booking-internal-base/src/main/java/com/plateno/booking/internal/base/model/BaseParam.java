package com.plateno.booking.internal.base.model;


/**
 * 公共请求参数（如非接口注明不需要，所有接口都需要传递）
 * 所有请求参数封装都需要继承
 * @author mogt
 * @date 2016年11月9日
 */
public class BaseParam implements java.io.Serializable{

	private static final long serialVersionUID = 9176534725953681588L;
	
	/**
	 * 请求来源
	 * 请求来源：

		1-供应商后台
		
		2-营销通后台
		
		3-商城前端
	 */
	private Integer plateForm;
	
	/**
	 * 请求来源为 3-商城前端必填，其他选填（其他来源需作为过滤条件时填写）
	 */
	private Integer memberId;
	
	/**
	 * 请求来源为 1-供应商后台必填，其他选填（其他来源需作为过滤条件时填写）
	 */
	private Integer channelId;

	public Integer getPlateForm() {
		return plateForm;
	}

	public void setPlateForm(Integer plateForm) {
		this.plateForm = plateForm;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
}
