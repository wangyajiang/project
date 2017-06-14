package com.task.domain;

public class TableRule {

	private String sourceTableName;
	private String sourceColumns;
	private String goldTableName;
	private String goldColumns;
	private String formats;
	
	public String getSourceTableName() {
		return sourceTableName;
	}
	public void setSourceTableName(String sourceTableName) {
		this.sourceTableName = sourceTableName;
	}
	public String getSourceColumns() {
		return sourceColumns;
	}
	public void setSourceColumns(String sourceColumns) {
		this.sourceColumns = sourceColumns;
	}
	public String getGoldTableName() {
		return goldTableName;
	}
	public void setGoldTableName(String goldTableName) {
		this.goldTableName = goldTableName;
	}
	public String getGoldColumns() {
		return goldColumns;
	}
	public void setGoldColumns(String goldColumns) {
		this.goldColumns = goldColumns;
	}
	public String getFormats() {
		return formats;
	}
	public void setFormats(String formats) {
		this.formats = formats;
	}
	
}
