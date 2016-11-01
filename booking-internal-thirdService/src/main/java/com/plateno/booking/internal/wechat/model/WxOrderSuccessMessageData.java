package com.plateno.booking.internal.wechat.model;



/**
 * 预订成功微信消息体
 * @author stefan
 *
 */
public class WxOrderSuccessMessageData {
	
	/**
	 *   first 商品名称
	 */
	private WxMessageDataValue first = new WxMessageDataValue() ;
	
	/**
	 * 预定类型
	 */
	private WxMessageDataValue keyword1 = new WxMessageDataValue() ;
	
	/**
	 * 出行日期
	 */
	private WxMessageDataValue  keyword2 = new WxMessageDataValue() ;
	
	/**
	 * 订单金额
	 */
	private WxMessageDataValue keyword3 = new WxMessageDataValue() ;
	
	/**
	 * 订单金额
	 */
	private WxMessageDataValue keyword4 = new WxMessageDataValue() ;
	
	
	/**
	 * 备注
	 */
	private WxMessageDataValue remark = new WxMessageDataValue() ;

	public WxMessageDataValue getFirst() {
		return first;
	}

	public void setFirst(WxMessageDataValue first) {
		this.first = first;
	}
	
	public WxMessageDataValue getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(WxMessageDataValue keyword1) {
		this.keyword1 = keyword1;
	}

	public WxMessageDataValue getKeyword2() {
		return keyword2;
	}

	public void setKeyword2(WxMessageDataValue keyword2) {
		this.keyword2 = keyword2;
	}

	public WxMessageDataValue getKeyword3() {
		return keyword3;
	}

	public void setKeyword3(WxMessageDataValue keyword3) {
		this.keyword3 = keyword3;
	}

	public WxMessageDataValue getKeyword4() {
		return keyword4;
	}

	public void setKeyword4(WxMessageDataValue keyword4) {
		this.keyword4 = keyword4;
	}

	public WxMessageDataValue getRemark() {
		return remark;
	}

	public void setRemark(WxMessageDataValue remark) {
		this.remark = remark;
	}

}
