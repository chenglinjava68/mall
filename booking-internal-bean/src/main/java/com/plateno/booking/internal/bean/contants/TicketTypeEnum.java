package com.plateno.booking.internal.bean.contants;

/**
 * 票类型
 * @author yi.wang
 * @date 2016年7月28日上午9:19:11
 * @version 1.0
 * @Description
 */
public enum TicketTypeEnum {

	QINZI("亲子票", 1),
	JIATING("家庭票", 2),
	QINGLV("情侣票", 3),
	SHUANGREN("双人票", 4),
	CHENGREN("成人票", 5),
	ERTONG("儿童票", 6),
	LAOREN("老人票", 7),
	XUESHENG("学生票", 8),
	HUODONG("活动票", 9),
	JUNREN("军人票", 10),
	NANSHI("男士票", 11),
	NVSHI("女士票", 12),
	JIAOSHI("教师票", 13),
	CANJI("残疾票", 14),
	TUANTI("团体票", 15),
	DIY("自定义", 16),
	YOUDAI("优待票", 17),
	PUTONG("普通票", 18),
	TESHU("特殊票", 19),
	YOUHUITAO("优惠套票", 20);
	
	private String name;
	private int index;
	
	private TicketTypeEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
	
    public static String getName(int index) {
        for (TicketTypeEnum c : TicketTypeEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
