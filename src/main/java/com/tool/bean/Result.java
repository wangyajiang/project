package com.tool.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.tool.constant.EnumConfig;
import com.tool.utils.CheckUtils;



/**
 * 接口返回结果
 * @author wyj
 */
public class Result implements Serializable {

	private static final long serialVersionUID = 8634580794272004072L;
	
	private int code = EnumConfig.S_CODE.OK.getCode();
	private String message = EnumConfig.S_CODE.OK.getName();
	private Object data;
	private Map<Object, Object> param = null;
	
	public int getCode() {
		return code;
	}
	public void setCode(EnumConfig.S_CODE scode) {
		this.code = scode.getCode();
		this.message = EnumConfig.S_CODE.getEnum(this.code).getName();
	}
	public void setCode(EnumConfig.S_CODE scode, String msg) {
		this.code = scode.getCode();
		if (CheckUtils.isBlank(msg)) {
			this.message = EnumConfig.S_CODE.getEnum(this.code).getName();
		} else {
			this.message = msg;
		}
	}
	public String getMessage() {
		return message;
	}
	public void setMsg(String msg) {
		this.message = msg;
	}
	public Object getData() {
		if (null != this.param) {
			return param;
		}
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public void setData(Object data, EnumConfig.S_CODE scode) {
		this.data = data;
		this.code = scode.getCode();
	}
	public void setData(EnumConfig.S_CODE scode, String msg) {
		this.code = scode.getCode();
		this.message = msg;
	}
	public void setData(Object data, String msg) {
		this.data = data;
		this.message = msg;
	}
	public void setData(Object data, EnumConfig.S_CODE scode, String msg) {
		this.data = data;
		this.code = scode.getCode();
		this.message = msg;
	}
	public void put(Object key, Object value) {
		if (null == param) {
			param = new HashMap<Object, Object>();
		}
		this.param.put(key, value);
	}
	public long getNowTime() {
		return System.currentTimeMillis();
	}
}
