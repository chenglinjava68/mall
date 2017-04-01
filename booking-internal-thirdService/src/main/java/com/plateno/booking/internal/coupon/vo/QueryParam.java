package com.plateno.booking.internal.coupon.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.plateno.booking.internal.common.util.date.DateUtil;
import com.plateno.booking.internal.coupon.constant.CouponConstant;

/**
 * 查询参数
 * @author mogt
 * @date 2016年12月27日
 */
public class QueryParam {
	/**
	 * 查询页码
	 */
	private Integer pageNum = 1;
	/**
	 * 页面大小
	 */
	private Integer pageSize = 10;
	/**
	 * 优惠券ID
	 */
	private Integer couponId;
	/**
	 * 配置ID
	 */
	private Integer configId;
	/**
	 * 会员ID
	 */
	private Integer mebId;
	/**
	 * 适用分店(商品)Id
	 */
	private String chainId = "1";
	/**
	 * 适用品牌(景点)Id
	 */
	private String brandId = "1";
	/**
	 * 适用城市 Id
	 */
	private String cityId = "1";
	/**
	 * 优惠券业务类型(此定义在魔方管理)
	 */
	private Integer addBusType;
	/**
	 * 优惠券业务类型(此定义在魔方管理)
	 */
	private List<Integer> inAddBusType;
	/**
	 * 优惠券业务子类型(此定义在魔方管理)
	 */
	private Integer addSubBusType;
	/**
	 * 优惠券业务子类型(此定义在魔方管理)
	 */
	private List<Integer> inAddSubBusType;
	/**
	 * 面额
	 */
	private BigDecimal amount;
	/**
	 * 状态标识0：所有(不含删除),1:可用,2.已使用,3.删除
	 */
	private Integer flag;
	/**
	 * 券名称
	 */
	private String couponName;
	
	/**
	 * 使用条件
	 */
	private Conditions conditions;
	
	/**
	 * 使用平台 
	 */
	private Integer platformId;
	
	/**
	 * 根据限制条件查询可用抵用券 0：所有,1:可用
	 */
	private Integer isEnabledByUseLimit;
	
	/**
	 * 开始使用时间
	 */
	private Date beginUsedTime = new Date();
	
	public Date getBeginUsedTime() {
		return beginUsedTime;
	}
	public void setBeginUsedTime(Date beginUsedTime) {
		this.beginUsedTime = beginUsedTime;
	}
	public Integer getIsEnabledByUseLimit() {
		return isEnabledByUseLimit;
	}
	public void setIsEnabledByUseLimit(Integer isEnabledByUseLimit) {
		this.isEnabledByUseLimit = isEnabledByUseLimit;
	}
	public Integer getPlatformId() {
		return platformId;
	}
	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}
	public Conditions getConditions() {
		return conditions;
	}
	public void setConditions(Conditions conditions) {
		this.conditions = conditions;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getCouponId() {
		return couponId;
	}
	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}
	public Integer getConfigId() {
		return configId;
	}
	public void setConfigId(Integer configId) {
		this.configId = configId;
	}
	public Integer getMebId() {
		return mebId;
	}
	public void setMebId(Integer mebId) {
		this.mebId = mebId;
	}
	public String getChainId() {
		return chainId;
	}
	public void setChainId(String chainId) {
		this.chainId = chainId;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public Integer getAddBusType() {
		return addBusType;
	}
	public void setAddBusType(Integer addBusType) {
		this.addBusType = addBusType;
	}
	public List<Integer> getInAddBusType() {
		return inAddBusType;
	}
	public void setInAddBusType(List<Integer> inAddBusType) {
		this.inAddBusType = inAddBusType;
	}
	public Integer getAddSubBusType() {
		return addSubBusType;
	}
	public void setAddSubBusType(Integer addSubBusType) {
		this.addSubBusType = addSubBusType;
	}
	public List<Integer> getInAddSubBusType() {
		return inAddSubBusType;
	}
	public void setInAddSubBusType(List<Integer> inAddSubBusType) {
		this.inAddSubBusType = inAddSubBusType;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	
	@Override 
    public String toString() { 
		return ReflectionToStringBuilder.toString(this,ToStringStyle.SHORT_PREFIX_STYLE); 
    }
}
