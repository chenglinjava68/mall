package com.plateno.booking.internal.wechat.model;



 /**预订成功 
 * @type WxOrderSuccessMessage
 * @description  
 * @author zhangfan
 */
public class WxOrderSuccessMessage
{
	/**
	 *  模版数据
	 */
	private WxOrderSuccessMessageData data = new WxOrderSuccessMessageData() ;

	/**
	 * 模板连接地址
	 */
	private String url ;
	
	private String topColor = "#4169E1" ;

	/**
	 * @return the data
	 */
	public WxOrderSuccessMessageData getData()
	{
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(WxOrderSuccessMessageData data)
	{
		this.data = data;
	}

	/**
	 * @return the url
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}

	/**
	 * @return the topColor
	 */
	public String getTopColor()
	{
		return topColor;
	}

	/**
	 * @param topColor the topColor to set
	 */
	public void setTopColor(String topColor)
	{
		this.topColor = topColor;
	}

	/**
	 * 设置标题
	 * @param value
	 */
	public void addFirst( String value )
	{
		this.data.getFirst().setValue(value);
	}
	
	/**
	 * 设置标题颜色
	 * @param color
	 */
	public void addFirstColor( String color )
	{
		this.data.getFirst().setColor(color);
	}
	
	/**
	 * 预定类型
	 * @param value
	 */
	public void addGoodName( String value )
	{
		this.data.getKeyword1().setValue(value);
	}
	
	public void addGoodNameColor( String color )
	{
		this.data.getKeyword1().setColor(color);
	}
	
	/**
	 * 预定类型
	 * @param value
	 */
	public void addType( String value )
	{
		this.data.getKeyword2().setValue(value);
	}
	
	public void addTypeColor( String color )
	{
		this.data.getKeyword2().setColor(color);
	}
	
	/**
	 * 出行日期
	 * @param value
	 */
	public void addVisitDate( String value )
	{
		this.data.getKeyword3().setValue(value);
	}
	
	public void addVisitDateColor( String color )
	{
		this.data.getKeyword3().setColor(color);
	}
	
	/**
	 * 订单金额
	 * @param value
	 */
	public void addAmount( String value )
	{
		this.data.getKeyword4().setValue(value);
	}
	
	/**
	 * 设置支付价格颜色
	 * @param color
	 */
	public void addAmountColor( String color )
	{
		this.data.getKeyword4().setColor(color);
	}
	
	
	public void addRemark( String value )
	{
		this.data.getRemark().setValue(value);
	}
	
	
}
