package com.plateno.booking.internal.base.pojo;

import java.io.Serializable;

public class MLogistics implements Serializable {
    private Integer id;

    private String orderNo;

    private Integer logisticsType;

    private String logisticsNo;

    private Integer expressFee;

    private String consigneeAddress;

    private Integer shippingType;

    private String consigneeName;

    private String consigneeMobile;

    private String consigneeNewaddress;
    
    /**
     * 最新收货人姓名
     */
    private String consigneeNewName;
    
    /**
     * 最新收货人地址
     */
    private String consigneeNewMobile;
    
    /**
     * 省
     */
    private String province;
    
    /**
     * 市
     */
    private String city;
    
    /**
     * 县
     */
    private String area;
    
    /**
     * 最新省
     */
    private String newProvince;
    
    /**
     * 最新市
     */
    private String newCity;
    
    /**
     * 最新区
     */
    private String newArea;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getLogisticsType() {
        return logisticsType;
    }

    public void setLogisticsType(Integer logisticsType) {
        this.logisticsType = logisticsType;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo == null ? null : logisticsNo.trim();
    }

    public Integer getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(Integer expressFee) {
        this.expressFee = expressFee;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress == null ? null : consigneeAddress.trim();
    }

    public Integer getShippingType() {
        return shippingType;
    }

    public void setShippingType(Integer shippingType) {
        this.shippingType = shippingType;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName == null ? null : consigneeName.trim();
    }

    public String getConsigneeMobile() {
        return consigneeMobile;
    }

    public void setConsigneeMobile(String consigneeMobile) {
        this.consigneeMobile = consigneeMobile == null ? null : consigneeMobile.trim();
    }

    public String getConsigneeNewaddress() {
        return consigneeNewaddress;
    }

    public void setConsigneeNewaddress(String consigneeNewaddress) {
        this.consigneeNewaddress = consigneeNewaddress == null ? null : consigneeNewaddress.trim();
    }

	public String getConsigneeNewName() {
		return consigneeNewName;
	}

	public void setConsigneeNewName(String consigneeNewName) {
		this.consigneeNewName = consigneeNewName == null ? null : consigneeNewName.trim();
	}

	public String getConsigneeNewMobile() {
		return consigneeNewMobile;
	}

	public void setConsigneeNewMobile(String consigneeNewMobile) {
		this.consigneeNewMobile = consigneeNewMobile == null ? null : consigneeNewMobile.trim();
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getNewProvince() {
		return newProvince;
	}

	public void setNewProvince(String newProvince) {
		this.newProvince = newProvince;
	}

	public String getNewCity() {
		return newCity;
	}

	public void setNewCity(String newCity) {
		this.newCity = newCity;
	}

	public String getNewArea() {
		return newArea;
	}

	public void setNewArea(String newArea) {
		this.newArea = newArea;
	}
}