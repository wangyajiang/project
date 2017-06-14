package com.area;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AreaRule {

	private Integer id;
	private String code;
	private String name;
	
	private List<AreaRule> list = new ArrayList<AreaRule>();
	
	private Map<String, Integer> nodeMap = new HashMap<String, Integer>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AreaRule> getList() {
		return list;
	}

	public void setList(List<AreaRule> list) {
		this.list = list;
	}

	public Map<String, Integer> getNodeMap() {
		return nodeMap;
	}

	
}
