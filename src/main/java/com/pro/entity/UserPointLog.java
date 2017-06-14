package com.pro.entity;

import java.io.Serializable;
import java.util.Date;

public class UserPointLog implements Serializable {
	private static final long serialVersionUID = 1L;

	//userId,name,point,status,createTime

	private Integer userId;//主键；用户唯一标识
	private String name;//积分获取或者消费名称
	private Integer point;//积分
	private Integer status;//0：获得积分，1：消费积分
	private Date createTime;//创建时间

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
		sb.append("name : ");sb.append(name);sb.append(", ");
		sb.append("point : ");sb.append(point);sb.append(", ");
		sb.append("status : ");sb.append(status);sb.append(", ");
		sb.append("createTime : ");sb.append(createTime);
		sb.append("}");
		 return sb.toString();
	}
}
