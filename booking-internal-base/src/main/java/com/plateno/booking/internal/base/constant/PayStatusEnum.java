package com.plateno.booking.internal.base.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 订单状态
 * 支付状态：1待付款、2已取消 3待发货，4待收货，5已完成 6退款审核中、7已退款，8退款审核不通过 9已删除,10 退款中,11支付中,12支付失败,13退款失败
 * 显示状态：100待付款、200已取消、300待发货、400待收货、500已完成、600退款中、700已退款、800退款审核不通过
 * </pre>
 * @author mogt
 * @date 2016年11月9日
 */
public enum PayStatusEnum {
	
	/**
	 * 待付款
	 */
	PAY_STATUS_1(1, 100, "待付款"),
	/**
	 * 已取消
	 */
	PAY_STATUS_2(2, 200, "已取消 "),
	/**
	 * 待发货
	 */
	PAY_STATUS_3(3, 300, "待发货"),
	/**
	 * 待收货
	 */
	PAY_STATUS_4(4, 400, "待收货"),
	/**
	 * 已完成
	 */
	PAY_STATUS_5(5, 500, "已完成"),
	/**
	 * 退款审核中
	 */
	PAY_STATUS_6(6, 600, "退款审核中"),
	/**
	 * 已退款
	 */
	PAY_STATUS_7(7, 700, "已退款"),
	/**
	 * 退款审核不通过
	 */
	PAY_STATUS_8(8, 800, "退款审核不通过"),
	/**
	 * 已删除
	 */
	PAY_STATUS_9(9, -1, "已删除"),
	/**
	 * 退款中
	 */
	PAY_STATUS_10(10, 600, "退款中"), 
	/**
	 * 支付中
	 */
	PAY_STATUS_11(11, 100, "支付中"), 
	/**
	 * 支付失败
	 */
	@Deprecated
	PAY_STATUS_12(12, 100, "支付失败"), 
	/**
	 * 退款失败
	 */
	PAY_STATUS_13(13, 600, "退款失败"), 
	;
    
	/**
     * 数据库状态
     */
    private int payStatus;
    
    /**
     * 用户显示的状态
     */
    private int viewStstus;

    /**
     * 描述
     */
    private String desc;
    

	private PayStatusEnum(int payStatus, int viewStstus, String desc) {
		this.payStatus = payStatus;
		this.viewStstus = viewStstus;
		this.desc = desc;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	public int getViewStstus() {
		return viewStstus;
	}

	public void setViewStstus(int viewStstus) {
		this.viewStstus = viewStstus;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static PayStatusEnum from(int payStatus)throws IllegalArgumentException {
        for (PayStatusEnum one : values()) {
            if (one.getPayStatus() == payStatus) {
                return one;
            }
        }
        throw new IllegalArgumentException("PayStatusEnum illegal payStatus:"+ payStatus);
    }
	
	/**
	 * 是否包含
	 * @param plateForm
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static boolean has(int payStatus)throws IllegalArgumentException {
        for (PayStatusEnum one : values()) {
            if (one.getPayStatus() == payStatus) {
                return true;
            }
        }
        return false;
    }
	
	/**
	 * 数据库存储的状态改成显示的状态
	 * @param payStatus
	 * @return
	 */
	public static int toViewStatus(int payStatus) {
		return from(payStatus).getViewStstus();
	}
	
	/**
	 * 显示状态转成数据库储存状态
	 * @param viewStatus
	 * @return
	 */
	public static List<Integer> toPayStatus(int viewStatus) {
		List<Integer> list = new ArrayList<>();
		for (PayStatusEnum one : values()) {
            if (one.getViewStstus() == viewStatus) {
            	list.add(one.getPayStatus());
            }
        }
        return list;
	}
}
