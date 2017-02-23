package com.plateno.booking.internal.base.constant;

/**
 * 
* @ClassName: PackageStatusEnum 
* @Description: 包裹状态
* @author zhengchubin
* @date 2017年2月23日 上午11:27:35 
*
 */
public enum PackageStatusEnum {
    
    /**
     * 已发货
     */
    START(1, "已发货"),
    
    /**
     * 确认收货
     */
    CONFIRM(2, "确认收货"),
    ;
    
    /**
     * 类型
     */
    private int type;

    /**
     * 描述
     */
    private String desc;

    private PackageStatusEnum(int type, String desc) {
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
    
    
    
}
