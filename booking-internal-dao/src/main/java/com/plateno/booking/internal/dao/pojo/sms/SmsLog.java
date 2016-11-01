package com.plateno.booking.internal.dao.pojo.sms;

import java.math.BigInteger;
import java.util.Date;

/**
 * 短信历史记录表
 * @author yi.wang
 * @date 2016年7月25日上午11:09:03
 * @version 1.0
 * @Description
 */
public class SmsLog {
	private BigInteger id;
	private String objectNo;
	private String phone;
	private String content;
	private Integer isSuccess;
	private Date createTime;
	private Date updateTime;

	public String getObjectNo() {
		return objectNo;
	}

	public void setObjectNo(String objectNo) {
		this.objectNo = objectNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Integer getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}

	@Override
	public String toString() {
		return "SmsLog [id=" + id + ", objectNo=" + objectNo + ", phone=" + phone + ", content=" + content + ", isSuccess=" + isSuccess
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
}
