package com.tool.cache;

public interface CacheSource {
	
	public	String	getKey();
	
	public 	Object	getValue() throws Throwable;
}
