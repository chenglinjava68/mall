package com.plateno.booking.internal.bean.contants;

import com.plateno.booking.internal.base.constant.PayStatusEnum;


public enum LogisticsEnum {
	
	YT(1,"圆通"),
	ST(2,"申通"),
	YD(3,"韵达"),
	BST(4,"百事通"),
	SF(5,"顺丰"),
	EMS(6,"EMS"),
	ZT(7,"自提"),
	;
	
	private Integer type;
	
	private String  name;

	
	
	private LogisticsEnum(Integer type, String name) {
		this.type = type;
		this.name = name;
	}



	public Integer getType() {
		return type;
	}



	public String getName() {
		return name;
	}

	public static String getNameBytype(Integer type){
		for(LogisticsEnum logisticsEnum : LogisticsEnum.values()){
			if(logisticsEnum.getType().equals(type)){
				return logisticsEnum.getName();
			}
		}
		return null;
	}
	
	public static LogisticsEnum from(int type)throws IllegalArgumentException {
        for (LogisticsEnum one : values()) {
            if (one.getType() == type) {
                return one;
            }
        }
        throw new IllegalArgumentException("LogisticsEnum illegal type:"+ type);
    }
	
	public static boolean has(Integer type){
		if(type == null) {
			return false;
		}
		for(LogisticsEnum logisticsEnum : LogisticsEnum.values()){
			if(logisticsEnum.getType().equals(type)){
				return true;
			}
		}
		return false;
	}
}
