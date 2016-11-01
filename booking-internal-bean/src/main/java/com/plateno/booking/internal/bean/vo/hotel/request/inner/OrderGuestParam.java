package com.plateno.booking.internal.bean.vo.hotel.request.inner;

import java.io.Serializable;

public class OrderGuestParam implements Serializable {
    
	private static final long serialVersionUID = -98090725489388547L;

	// 姓名
    private String name;
    
    // 性别
    private String sex;
    
    // 证件类型
    /**
     * 10	身份证/驾驶证
	 * 11	台湾居民来往大陆通行证
	 * 12	港澳居民来往大陆通行证
	 * 13	外籍护照
	 * 14	外交护照
	 * 15	公务护照
	 * 52	因公普通护照
	 * 53	其它证件
	 * 55	军官证
	 * 56	士兵证
     * */
    private Integer doctType; 
    
    // 证件号码
    private String doctNo;
    
    // 手机
    private String mobile;
    
    // 电话
    private String tel;
    
    // 备注
    private String remark;
    
    //姓
    private String lastName;
    
    //名
    private String firstName;
    
    //国家编码
    private String countryCode;
    
    //年龄
    private Integer age;
    
    //是否成人
    private String isAdult;
    
    //邮箱
    private String email;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getDoctType() {
		return doctType;
	}

	public void setDoctType(Integer doctType) {
		this.doctType = doctType;
	}

	public String getDoctNo() {
		return doctNo;
	}

	public void setDoctNo(String doctNo) {
		this.doctNo = doctNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getIsAdult() {
		return isAdult;
	}

	public void setIsAdult(String isAdult) {
		this.isAdult = isAdult;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}