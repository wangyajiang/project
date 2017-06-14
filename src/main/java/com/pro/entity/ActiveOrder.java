package com.pro.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ActiveOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	//orderId,userId,activeId,itemId,activeName,typeId,itemPicUrl,price,amnt,num,status,nickname,userPic,orderIp,ipName,orderEndTime,createTime

	private Integer orderId;//主键；订单唯一标识
	private Integer userId;//用户唯一标识
	private Integer activeId;//活动唯一标识
	private Integer itemId;//商品唯一标识
	private String activeName;//活动名称
	private Integer typeId;//商品类型
	private String itemPicUrl;//活动商品主图
	private BigDecimal price;//订单总额(元)
	private BigDecimal amnt;//活动每份价格(元)
	private Integer num;//参与份数(参与次数)
	private Integer status;//订单状态（0：未支付，1：支付中，2：支付成功）
	private String nickname;//用户昵称
	private String userPic;//用户头像
	private String orderIp;//用户订单IP
	private String ipName;//用户订单IP地址
	private Date orderEndTime;//订单结束时间
	private Date createTime;//创建时间

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
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getUserPic() {
		return userPic;
	}
	public void setUserPic(String userPic) {
		this.userPic = userPic;
	}
	public String getOrderIp() {
		return orderIp;
	}
	public void setOrderIp(String orderIp) {
		this.orderIp = orderIp;
	}
	public String getIpName() {
		return ipName;
	}
	public void setIpName(String ipName) {
		this.ipName = ipName;
	}
	public Date getOrderEndTime() {
		return orderEndTime;
	}
	public void setOrderEndTime(Date orderEndTime) {
		this.orderEndTime = orderEndTime;
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
		sb.append("orderId : ");sb.append(orderId);sb.append(", ");
		sb.append("userId : ");sb.append(userId);sb.append(", ");
		sb.append("activeId : ");sb.append(activeId);sb.append(", ");
		sb.append("itemId : ");sb.append(itemId);sb.append(", ");
		sb.append("activeName : ");sb.append(activeName);sb.append(", ");
		sb.append("typeId : ");sb.append(typeId);sb.append(", ");
		sb.append("itemPicUrl : ");sb.append(itemPicUrl);sb.append(", ");
		sb.append("price : ");sb.append(price);sb.append(", ");
		sb.append("amnt : ");sb.append(amnt);sb.append(", ");
		sb.append("num : ");sb.append(num);sb.append(", ");
		sb.append("status : ");sb.append(status);sb.append(", ");
		sb.append("nickname : ");sb.append(nickname);sb.append(", ");
		sb.append("userPic : ");sb.append(userPic);sb.append(", ");
		sb.append("orderIp : ");sb.append(orderIp);sb.append(", ");
		sb.append("ipName : ");sb.append(ipName);sb.append(", ");
		sb.append("orderEndTime : ");sb.append(orderEndTime);sb.append(", ");
		sb.append("createTime : ");sb.append(createTime);
		sb.append("}");
		 return sb.toString();
	}
}
