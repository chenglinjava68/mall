package com.plateno.booking.internal.base.constant;


/**
 * 逻辑删除
 * @author mogt
 * @date 2016年11月28日
 */
public enum LogicDelEnum {
	
	/**
	 * 待付款
	 */
	NORMAL(1, "正常"),
	/**
	 * 已取消
	 */
	DEL(2, "删除"),
	;
    
	/**
     * 类型
     */
    private int type;

    /**
     * 描述
     */
    private String desc;
    

	private LogicDelEnum(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static LogicDelEnum from(int type)throws IllegalArgumentException {
        for (LogicDelEnum one : values()) {
            if (one.getType() == type) {
                return one;
            }
        }
        throw new IllegalArgumentException("LogicDelEnum illegal type:"+ type);
    }
	
	/**
	 * 是否包含
	 * @param plateForm
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static boolean has(int type)throws IllegalArgumentException {
        for (LogicDelEnum one : values()) {
            if (one.getType() == type) {
                return true;
            }
        }
        return false;
    }
}
