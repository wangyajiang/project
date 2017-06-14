package com.pro.entity;

import java.io.Serializable;
import java.util.Date;

public class FocusMap implements Serializable {
	private static final long serialVersionUID = 1L;

	//id,name,url,status,sort,type,createTime

	private Integer id;//主键；焦点图唯一标识
	private String name;//名称
	private String url;//触发
	private Integer status;//0：正常，1：关闭
	private Integer sort;//排序
	private Integer type;//0：外部连接，1：内部函数，3：无连接
	private Date createTime;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
		sb.append("id : ");sb.append(id);sb.append(", ");
		sb.append("name : ");sb.append(name);sb.append(", ");
		sb.append("url : ");sb.append(url);sb.append(", ");
		sb.append("status : ");sb.append(status);sb.append(", ");
		sb.append("sort : ");sb.append(sort);sb.append(", ");
		sb.append("type : ");sb.append(type);sb.append(", ");
		sb.append("createTime : ");sb.append(createTime);
		sb.append("}");
		 return sb.toString();
	}
}
