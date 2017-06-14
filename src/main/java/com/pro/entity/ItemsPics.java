package com.pro.entity;

import java.io.Serializable;

public class ItemsPics implements Serializable {
	private static final long serialVersionUID = 1L;

	//id,itemId,picUrl,sort

	private Integer id;//主键；商品图片列表唯一标识
	private Integer itemId;//商品一标识
	private String picUrl;//商品图片地址
	private Integer sort;//排序

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
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
		sb.append("itemId : ");sb.append(itemId);sb.append(", ");
		sb.append("picUrl : ");sb.append(picUrl);sb.append(", ");
		sb.append("sort : ");sb.append(sort);
		sb.append("}");
		 return sb.toString();
	}
}
