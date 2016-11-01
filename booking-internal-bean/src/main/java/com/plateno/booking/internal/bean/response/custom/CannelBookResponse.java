package com.plateno.booking.internal.bean.response.custom;

/**
 * @author user
 *
 */
public class CannelBookResponse {

	private String goodsId; // 商品ID
	private String tradeNo; // 账单ID
	private String orderNo; // 订单ID
	private Integer channel; // 渠道ID

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getChannel() {
		return channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}
}
