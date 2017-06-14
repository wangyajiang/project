package com.pro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ItemsActive implements Serializable {
	private static final long serialVersionUID = 1L;

	//activeId,itemId,activeName,typeId,itemPicUrl,price,amnt,totalNum,currentNum,surplusNum,status,startTime,publishTime,endTime,createTime

	private Integer activeId;//主键；活动唯一标识
	private Integer itemId;//商品唯一标识
	private String activeName;//活动名称
	private Integer typeId;//商品类型
	private String itemPicUrl;//活动商品主图
	private BigDecimal price;//活动价格(元)
	private BigDecimal amnt;//活动每份价格(元)
	private Integer totalNum;//活动总份数
	private Integer currentNum;//当前参与份数
	private Integer surplusNum;//剩余份数
	private Integer status;//活动状态（0：活动未开启，1：关闭活动，2：活动等待开始，3：开启活动，4：活动进行中，5：活动已结束，6：公布结果）
	private Date startTime;//活动开始时间
	private Date publishTime;//公布时间
	private Date endTime;//活动结束时间
	private Date createTime;//创建时间

	public Integer getActiveId() {
		return activeId;
	}
	public void setActiveId(Integer activeId) {
		this.activeId = activeId;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getActiveName() {
		return activeName;
	}
	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getItemPicUrl() {
		return itemPicUrl;
	}
	public void setItemPicUrl(String itemPicUrl) {
		this.itemPicUrl = itemPicUrl;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getAmnt() {
		return amnt;
	}
	public void setAmnt(BigDecimal amnt) {
		this.amnt = amnt;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Integer getCurrentNum() {
		return currentNum;
	}
	public void setCurrentNum(Integer currentNum) {
		this.currentNum = currentNum;
	}
	public Integer getSurplusNum() {
		return surplusNum;
	}
	public void setSurplusNum(Integer surplusNum) {
		this.surplusNum = surplusNum;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
		sb.append("activeId : ");sb.append(activeId);sb.append(", ");
		sb.append("itemId : ");sb.append(itemId);sb.append(", ");
		sb.append("activeName : ");sb.append(activeName);sb.append(", ");
		sb.append("typeId : ");sb.append(typeId);sb.append(", ");
		sb.append("itemPicUrl : ");sb.append(itemPicUrl);sb.append(", ");
		sb.append("price : ");sb.append(price);sb.append(", ");
		sb.append("amnt : ");sb.append(amnt);sb.append(", ");
		sb.append("totalNum : ");sb.append(totalNum);sb.append(", ");
		sb.append("currentNum : ");sb.append(currentNum);sb.append(", ");
		sb.append("surplusNum : ");sb.append(surplusNum);sb.append(", ");
		sb.append("status : ");sb.append(status);sb.append(", ");
		sb.append("startTime : ");sb.append(startTime);sb.append(", ");
		sb.append("publishTime : ");sb.append(publishTime);sb.append(", ");
		sb.append("endTime : ");sb.append(endTime);sb.append(", ");
		sb.append("createTime : ");sb.append(createTime);
		sb.append("}");
		 return sb.toString();
	}
}
