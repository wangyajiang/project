package com.pro.entity;

import java.io.Serializable;
import java.util.Date;

public class UserPoint implements Serializable {
	private static final long serialVersionUID = 1L;

	//userId,point,modifyTime,createTime

	private Integer userId;//主键；用户唯一标识
	private Integer point;//可用积分
	private Date modifyTime;//修改时间
	private Date createTime;//创建时间

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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
		sb.append("point : ");sb.append(point);sb.append(", ");
		sb.append("modifyTime : ");sb.append(modifyTime);sb.append(", ");
		sb.append("createTime : ");sb.append(createTime);
		sb.append("}");
		 return sb.toString();
	}
}
