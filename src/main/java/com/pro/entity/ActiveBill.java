package com.pro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ActiveBill implements Serializable {
	private static final long serialVersionUID = 1L;

	//billId,orderId,userId,activeName,price,amnt,num,createTime

	private Integer billId;//主键；账单唯一标识
	private Integer orderId;//订单唯一标识
	private Integer userId;//用户唯一标识
	private String activeName;//活动名称
	private BigDecimal price;//账单金额
	private BigDecimal amnt;//活动每份价格(元)
	private Integer num;//参与份数(参与次数)
	private Date createTime;//创建时间

	public Integer getBillId() {
		return billId;
	}
	public void setBillId(Integer billId) {
		this.billId = billId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getActiveName() {
		return activeName;
	}
	public void setActiveName(String activeName) {
		this.activeName = activeName;
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
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
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
		sb.append("billId : ");sb.append(billId);sb.append(", ");
		sb.append("orderId : ");sb.append(orderId);sb.append(", ");
		sb.append("userId : ");sb.append(userId);sb.append(", ");
		sb.append("activeName : ");sb.append(activeName);sb.append(", ");
		sb.append("price : ");sb.append(price);sb.append(", ");
		sb.append("amnt : ");sb.append(amnt);sb.append(", ");
		sb.append("num : ");sb.append(num);sb.append(", ");
		sb.append("createTime : ");sb.append(createTime);
		sb.append("}");
		 return sb.toString();
	}
}
