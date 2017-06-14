package com.pro.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	//userId,nickname,picHead,mobile,password,realName,ctno,status,provinceId,cityId,countyId,address,createTime

	private Integer userId;//主键；用户唯一标识
	private String nickname;//昵称
	private String picHead;//用户头像
	private String mobile;//手机
	private String password;//密码
	private String realName;//用户真实姓名
	private String ctno;//身份证号
	private Integer status;//状态（0：正常，1：关闭）
	private Integer provinceId;//省
	private Integer cityId;//市
	private Integer countyId;//县/区
	private String address;//详细地址
	private Date createTime;//创建时间

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPicHead() {
		return picHead;
	}
	public void setPicHead(String picHead) {
		this.picHead = picHead;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getCtno() {
		return ctno;
	}
	public void setCtno(String ctno) {
		this.ctno = ctno;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getCountyId() {
		return countyId;
	}
	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("userId : ");sb.append(userId);sb.append(", ");
		sb.append("nickname : ");sb.append(nickname);sb.append(", ");
		sb.append("picHead : ");sb.append(picHead);sb.append(", ");
		sb.append("mobile : ");sb.append(mobile);sb.append(", ");
		sb.append("password : ");sb.append(password);sb.append(", ");
		sb.append("realName : ");sb.append(realName);sb.append(", ");
		sb.append("ctno : ");sb.append(ctno);sb.append(", ");
		sb.append("status : ");sb.append(status);sb.append(", ");
		sb.append("provinceId : ");sb.append(provinceId);sb.append(", ");
		sb.append("cityId : ");sb.append(cityId);sb.append(", ");
		sb.append("countyId : ");sb.append(countyId);sb.append(", ");
		sb.append("address : ");sb.append(address);sb.append(", ");
		sb.append("createTime : ");sb.append(createTime);
		sb.append("}");
		 return sb.toString();
	}
}
