package com.tool.bean;

public class Judge {

	private boolean isBlank = false;
	private String key;
	
	public boolean isBlank() {
		return isBlank;
	}
	public void setBlank(boolean isBlank) {
		this.isBlank = isBlank;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("isBlank：");
		sb.append(isBlank);
		sb.append("\t");
		sb.append("key：");
		sb.append(key);
		return sb.toString();
	}
	
}
