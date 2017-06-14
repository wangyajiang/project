package com.pro.entity;

import java.io.Serializable;

public class ItemsActivePics implements Serializable {
	private static final long serialVersionUID = 1L;

	//id,activeId,picUrl,sort

	private Integer id;//主键；商品活动图片列表唯一标识
	private Integer activeId;//活动唯一标识
	private String picUrl;//活动图片列表
	private Integer sort;//排序

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getActiveId() {
		return activeId;
	}
	public void setActiveId(Integer activeId) {
		this.activeId = activeId;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("id : ");sb.append(id);sb.append(", ");
		sb.append("activeId : ");sb.append(activeId);sb.append(", ");
		sb.append("picUrl : ");sb.append(picUrl);sb.append(", ");
		sb.append("sort : ");sb.append(sort);
		sb.append("}");
		 return sb.toString();
	}
}
