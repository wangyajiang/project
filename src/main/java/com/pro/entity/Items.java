package com.pro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Items implements Serializable {
	private static final long serialVersionUID = 1L;

	//itemId,userId,title,picUrl,price,status,typeId,createTime

	private Integer itemId;//主键；商品唯一标识
	private Integer userId;//用户唯一标识
	private String title;//商品名称
	private String picUrl;//商品主图
	private BigDecimal price;//商品价格
	private Integer status;//商品状态，0正常，1关闭
	private Integer typeId;//商品类型
	private Date createTime;//创建时间

	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
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
		sb.append("itemId : ");sb.append(itemId);sb.append(", ");
		sb.append("userId : ");sb.append(userId);sb.append(", ");
		sb.append("title : ");sb.append(title);sb.append(", ");
		sb.append("picUrl : ");sb.append(picUrl);sb.append(", ");
		sb.append("price : ");sb.append(price);sb.append(", ");
		sb.append("status : ");sb.append(status);sb.append(", ");
		sb.append("typeId : ");sb.append(typeId);sb.append(", ");
		sb.append("createTime : ");sb.append(createTime);
		sb.append("}");
		 return sb.toString();
	}
}
