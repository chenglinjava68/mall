package com.plateno.booking.internal.base.constant;


/**
 * 
* @ClassName: PayTypeEnum 
* @Description: 1-微信支付，2-支付宝支付，3-只用储值支付，4-微信+储值支付，5-支付宝+储值支付 6-活动支付
* @author zhengchubin
* @date 2017年3月10日 下午5:42:25 
*
 */
public enum PayTypeEnum {
	
	/**
	 * 微信支付
	 */
	PAY_TYPE_WX(1, "微信支付"),
	/**
	 * 支付宝支付
	 */
	PAY_TYPE_ALI(2, "支付宝支付"),
	/**
	 * 储值支付
	 */
	PAY_TYPE_CURRENCY(3, "储值支付"),
	/**
	 * 微信+储值支付
	 */
	PAY_TYPE_WX_CURRENCY(4, "微信+储值支付"),
	/**
	 * 支付宝+储值支付
	 */
	PAY_TYPE_ALI_CURRENCY(5, "支付宝+储值支付"),
	/**
	 * 无需支付
	 */
	PAY_TYPE_ACTIVITY(6, "无需支付"),
	;
    
	/**
     * 数据库状态
     */
    private int payType;
    


    /**
     * 描述
     */
    private String payTypeDesc;
    

	private PayTypeEnum(int payType, String payTypeDesc) {
		this.payType = payType;
		this.payTypeDesc = payTypeDesc;
	}


    public int getPayType() {
        return payType;
    }


    public void setPayType(int payType) {
        this.payType = payType;
    }


    public String getPayTypeDesc() {
        return payTypeDesc;
    }


    public void setPayTypeDesc(String payTypeDesc) {
        this.payTypeDesc = payTypeDesc;
    }

	
}
