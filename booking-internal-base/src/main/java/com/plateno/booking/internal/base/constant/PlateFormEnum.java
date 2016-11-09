package com.plateno.booking.internal.base.constant;

/**
 * 平台来源
 * @author mogt
 * @date 2016年11月9日
 */
public enum PlateFormEnum {
    /**
     * 供应商后台
     */
	PROVIDER_ADMIN(1, "供应商后台"),
    /**
     * 营销通后台
     */
    ADMIN(2, "营销通后台"),
    /**
     * 商城前端
     */
    USER(3, "商城前端");
    
    /**
     * 来源
     */
    private int plateForm;

    /**
     * 描述
     */
    private String desc;
    
    private PlateFormEnum(int plateForm, String desc) {
		this.plateForm = plateForm;
		this.desc = desc;
	}

	public int getPlateForm() {
		return plateForm;
	}

	public void setPlateForm(int plateForm) {
		this.plateForm = plateForm;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static PlateFormEnum from(int plateForm)throws IllegalArgumentException {
        for (PlateFormEnum one : values()) {
            if (one.getPlateForm() == plateForm) {
                return one;
            }
        }
        throw new IllegalArgumentException("PlateFormEnum illegal plateForm:"+ plateForm);
    }
	
	/**
	 * 是否包含
	 * @param plateForm
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static boolean has(int plateForm)throws IllegalArgumentException {
        for (PlateFormEnum one : values()) {
            if (one.getPlateForm() == plateForm) {
                return true;
            }
        }
        return false;
    }
}
