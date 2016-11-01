package com.plateno.booking.internal.bean.contants;


public enum LogisticsEnum {
	
	YT(1,"圆通"),
	ST(2,"申通"),
	YD(3,"韵达"),
	BST(4,"百事通"),
	SF(5,"顺丰"),
	EMS(6,"EMS");
	
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
}
