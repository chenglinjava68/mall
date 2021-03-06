package com.plateno.booking.internal.bean.request.custom;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.plateno.booking.internal.base.model.BaseParam;

public class MAddBookingParam extends BaseParam implements Serializable {

	private static final long serialVersionUID = -5028619260613976314L;
	
	
	private List<MOrderGoodsParam> goodsList;
	
	@NotNull(message = "订单总价,不能为空")
	private Integer totalAmount;
	

	private String consigneeName;
	
	private String consigneeMobile;
	

	private String consigneeAddress;
	
	@NotNull(message = "配送方式,不能为空")
	private Integer shippingType;
	
	@NotNull(message = "平台来源,不能为空")
	private Integer platformId; // 平台 铂涛旅行APP:105 铂涛旅行官网 : 113 铂涛旅行微信 : 111 铂涛旅行M站 : 112 O2O营销通 115

	@NotNull(message = "会员姓名,不能为空")
	@NotEmpty(message = "会员姓名,不能为空")
	private String name;
	
	@NotNull(message = "会员手机,不能为空")
	@NotEmpty(message = "会员手机,不能为空")
	private String mobile;
	
	@NotNull(message = "下单来源,不能为空")
	private Integer resource;
	
	private Integer chanelId;
	
	@NotNull(message = "销售策略,不能为空")
	private Integer sellStrategy;//销售策略 1:金额(积分不足会员） 2：积分+金额 （够积分的会员使用)
	
	private Integer point;//积分
	
	/**
	 * 订单子来源
	 */
	private Integer subResource; 
	
	/**
	 * 优惠券ID
	 */
	private Integer couponId;
	
	/**
	 * 优惠券ID（非前端传）
	 */
	private String couponName;
	
	/**
	 * 优惠券的金额（非前端传）
	 */
	private BigDecimal couponAmount;
	
	
	/**
	 * 优惠券配置id
	 */
	private Integer configId;
	
	/**
	 * 有效的优惠券金额（当商品需要支付的金额小于优惠券金额时，就是商品需要支付的金额）
	 */
	private BigDecimal validCouponAmount;
	
	
	
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
     * 销售人员id
     */
    private int sid;
    
    /**
     * 是否为线下交易，1是0否
     */
    private Integer offline;
    
    
	@Override
    public String toString() {
        return "MAddBookingParam [goodsList=" + goodsList + ", totalAmount=" + totalAmount
                + ", consigneeName=" + consigneeName + ", consigneeMobile=" + consigneeMobile
                + ", consigneeAddress=" + consigneeAddress + ", shippingType=" + shippingType
                + ", platformId=" + platformId + ", name=" + name + ", mobile=" + mobile
                + ", resource=" + resource + ", chanelId=" + chanelId + ", sellStrategy="
                + sellStrategy + ", point=" + point + ", subResource=" + subResource
                + ", couponId=" + couponId + ", couponName=" + couponName + ", couponAmount="
                + couponAmount + ", validCouponAmount=" + validCouponAmount + ", province="
                + province + ", city=" + city + ", area=" + area + "]";
    }





    public Integer getConfigId() {
        return configId;
    }






    public void setConfigId(Integer configId) {
        this.configId = configId;
    }





    public int getSid() {
        return sid;
    }





    public void setSid(int sid) {
        this.sid = sid;
    }





    public List<MOrderGoodsParam> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<MOrderGoodsParam> goodsList) {
        this.goodsList = goodsList;
    }
        

    
    
    
	public Integer getOffline() {
        return offline;
    }

    public void setOffline(Integer offline) {
        this.offline = offline;

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

	public BigDecimal getValidCouponAmount() {
		return validCouponAmount;
	}

	public void setValidCouponAmount(BigDecimal validCouponAmount) {
		this.validCouponAmount = validCouponAmount;
	}

	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public BigDecimal getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}

	public Integer getSellStrategy() {
		return sellStrategy;
	}

	public void setSellStrategy(Integer sellStrategy) {
		this.sellStrategy = sellStrategy;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}


	public Integer getResource() {
		return resource;
	}

	public void setResource(Integer resource) {
		this.resource = resource;
	}



	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}



	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getConsigneeMobile() {
		return consigneeMobile;
	}

	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}

	public String getConsigneeAddress() {
		return consigneeAddress;
	}

	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}

	public Integer getShippingType() {
		return shippingType;
	}

	public void setShippingType(Integer shippingType) {
		this.shippingType = shippingType;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}



	public Integer getChanelId() {
		return chanelId;
	}

	public void setChanelId(Integer chanelId) {
		this.chanelId = chanelId;
	}

	public Integer getSubResource() {
		return subResource;
	}

	public void setSubResource(Integer subResource) {
		this.subResource = subResource;
	}
}
