package com.plateno.booking.internal.coupon.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 优惠券信息
 * @author mogt
 * @date 2016年12月27日
 */
public class CouponInfo {
	/**
	 * 优惠券ID
	 */
	private Integer couponId;
	
	/**
	 * 会员 Id
	 */
	private Integer mebId;
	
	/**
	 * 配置 Id
	 */
	private Integer configId;
	
	/**
	 * 券类型(1:抵用券，2:返现券, 3:房券)
	 */
	private Integer addBusType;
	
	/**
	 * 券子类型
	 */
	private Integer addSubBusType;
	
	/**
	 * 券名称
	 */
	private String couponName;
	
	/**
	 * 券描述
	 */
	private String remark;
	
	/**
	 * 面额
	 */
	private BigDecimal amount;
	
	/**
	 * 领取优惠券时间
	 * 格式： yyyy-MM-dd HH:mm:ss
	 */
	private Date gainTime;
	
	/**
	 * 券使用到的分店(商品)
	 */
	private String usedChainId;
	
	/**
	 * 券使用到的订单
	 */
	private String usedOrderCode;
	
	/**
	 * 使用优惠券时间
	 * 格式： yyyy-MM-dd HH:mm:ss
	 */
	private Date usedTime;
	
	/**
	 * 使用有效期-开始时间
	 * 格式： yyyy-MM-dd HH:mm:ss
	 */
	private Date usedBeginDate;
	
	/**
	 * 使用有效期-结束时间
	 * 格式： yyyy-MM-dd HH:mm:ss
	 */
	private Date usedEndDate;
	
	/**
	 * 状态标识
	 * 1:可用,2.已使用
	 */
	private Integer flag;
	
	/**
	 * 适用品牌(景点)Ids
	 */
	private List<String> brandIds;
	
	/**
	 * 适用分店(商品)Ids
	 */
	private List<String> chainIds;
	
	/**
	 * 适用城市 Ids
	 */
	private List<String> cityIds;
	
	/**
	 * 适用周期
	 */
	private List<Integer> week;
	
	/**
	 * 适用平台 Id
	 */
	private List<Integer> platformId;
	
	/**
	 * 操作人
	 */
	private Integer createOprtId;
	
	/**
	 * 发放类型
	 */
	private Integer issueType;
	
	/**
	 * 销售员
	 */
	private Integer sellerId;
	
	/**
	 * 1、未生效：指该优惠券还没有到开始时间
	 * 2、可用：指该优惠券还没有使用，并且在使用有效期内
	 * 3、已用：指该优惠券已经使用，不管是否过期
	 * 4、已过期：指该优惠券
	 */
	private Integer status;
	
	/**
	 * 会员类型：
	 * 0:通用， 1:会员， 2 老会员
	 */
	private Integer usedMebCategory;

	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	public Integer getMebId() {
		return mebId;
	}

	public void setMebId(Integer mebId) {
		this.mebId = mebId;
	}

	public Integer getConfigId() {
		return configId;
	}

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public Integer getAddBusType() {
		return addBusType;
	}

	public void setAddBusType(Integer addBusType) {
		this.addBusType = addBusType;
	}

	public Integer getAddSubBusType() {
		return addSubBusType;
	}

	public void setAddSubBusType(Integer addSubBusType) {
		this.addSubBusType = addSubBusType;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getGainTime() {
		return gainTime;
	}

	public void setGainTime(Date gainTime) {
		this.gainTime = gainTime;
	}

	public String getUsedChainId() {
		return usedChainId;
	}

	public void setUsedChainId(String usedChainId) {
		this.usedChainId = usedChainId;
	}

	public String getUsedOrderCode() {
		return usedOrderCode;
	}

	public void setUsedOrderCode(String usedOrderCode) {
		this.usedOrderCode = usedOrderCode;
	}

	public Date getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

	public Date getUsedBeginDate() {
		return usedBeginDate;
	}

	public void setUsedBeginDate(Date usedBeginDate) {
		this.usedBeginDate = usedBeginDate;
	}

	public Date getUsedEndDate() {
		return usedEndDate;
	}

	public void setUsedEndDate(Date usedEndDate) {
		this.usedEndDate = usedEndDate;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public List<String> getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(List<String> brandIds) {
		this.brandIds = brandIds;
	}

	public List<String> getChainIds() {
		return chainIds;
	}

	public void setChainIds(List<String> chainIds) {
		this.chainIds = chainIds;
	}

	public List<String> getCityIds() {
		return cityIds;
	}

	public void setCityIds(List<String> cityIds) {
		this.cityIds = cityIds;
	}

	public List<Integer> getWeek() {
		return week;
	}

	public void setWeek(List<Integer> week) {
		this.week = week;
	}

	public List<Integer> getPlatformId() {
		return platformId;
	}

	public void setPlatformId(List<Integer> platformId) {
		this.platformId = platformId;
	}

	public Integer getCreateOprtId() {
		return createOprtId;
	}

	public void setCreateOprtId(Integer createOprtId) {
		this.createOprtId = createOprtId;
	}

	public Integer getIssueType() {
		return issueType;
	}

	public void setIssueType(Integer issueType) {
		this.issueType = issueType;
	}

	public Integer getSellerId() {
		return sellerId;
	}

	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getUsedMebCategory() {
		return usedMebCategory;
	}

	public void setUsedMebCategory(Integer usedMebCategory) {
		this.usedMebCategory = usedMebCategory;
	}
	
	@Override 
    public String toString() { 
		return ReflectionToStringBuilder.toString(this,ToStringStyle.SHORT_PREFIX_STYLE); 
    }
}
