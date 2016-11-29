package com.plateno.booking.internal.bean.contants;

/**
 * 过期，改成数据库维护 LogisticsTypeData
 * @author mogt
 * @date 2016年11月29日
 */
@Deprecated
public enum LogisticsEnum {
	
	YT(1,"圆通快递"),
	ST(2,"申通快递"),
	YD(3,"韵达快递"),
	SF(5,"顺丰快递"),
	BST(4,"百事通"),
	EMS(6,"EMS"),
	ZT(7,"用户自提"),
	ZHONG_TONG(8,"中通快递"),
	ZHAI_JI_SONG(9,"宅急送"),
	BAI_SHI(10,"百世快递"),
	TIAN_TIAN(11,"天天快递"),
	QUAN_FENG(12,"全峰快递"),
	YOU_SU(13,"优速快递"),
	KUAI_JIE(14,"快捷快递"),
	GUO_TONG(15,"国通快递"),
	YOU_ZHENG(16,"中国邮政"),
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
