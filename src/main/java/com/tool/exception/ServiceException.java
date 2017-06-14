package com.tool.exception;

import com.tool.constant.EnumConfig;




public class ServiceException extends Exception {
	private static final long serialVersionUID = 1L;
	private  EnumConfig.S_CODE code = EnumConfig.S_CODE.FAIL;
	private String message = "";
	
	public ServiceException() {
		super();
	}
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(Throwable t) {
		super(t);
	}
	
	public ServiceException(String message,Throwable t) {
		super(message,t);
	}
	
	public ServiceException(EnumConfig.S_CODE code) {
		this.code = code;
	}
	
	public ServiceException(EnumConfig.S_CODE code,String message) {
		this.code = code;
		this.message = message;
	}

	public EnumConfig.S_CODE getCode() {
		return code;
	}

	public void setCode(EnumConfig.S_CODE code) {
		this.code = code;
	}
	
	public String getMessage() {
		if (null == super.getMessage()) {
			return message;
		}
		return super.getMessage();
	}
}
