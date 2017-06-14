package com.tool.exception;

import com.tool.constant.EnumConfig;




public class DaoException extends ServiceException {
	private static final long serialVersionUID = 1L;
	private EnumConfig.S_CODE code = EnumConfig.S_CODE.DB_ERROR;
	
	public DaoException() {
		super();
	}
	
	public DaoException(String message) {
		super(message);
	}
	
	public DaoException(Throwable t) {
		super(t);
	}
	
	public DaoException(String message,Throwable t) {
		super(message,t);
	}

	public  EnumConfig.S_CODE getCode() {
		return code;
	}
	
	public String getMessage() {
		StringBuffer msg = new StringBuffer(super.getMessage());
		msg.append(" ");
		msg.append(this.code.toString());
		msg.append("(");
		msg.append(this.code.getCode());
		msg.append(")");
		return msg.toString();
	}
}
