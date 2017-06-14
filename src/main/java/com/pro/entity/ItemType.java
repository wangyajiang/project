package com.pro.entity;

import java.io.Serializable;

public class ItemType implements Serializable {
	private static final long serialVersionUID = 1L;

	//typeId,typeName,sort

	private Integer typeId;//主键；商品类型唯一标识
	private String typeName;//商品类型名称
	private Integer sort;//排序

	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
		sb.append("typeId : ");sb.append(typeId);sb.append(", ");
		sb.append("typeName : ");sb.append(typeName);sb.append(", ");
		sb.append("sort : ");sb.append(sort);
		sb.append("}");
		 return sb.toString();
	}
}
