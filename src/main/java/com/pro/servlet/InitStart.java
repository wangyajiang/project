package com.pro.servlet;

import javax.annotation.PostConstruct;

import com.pro.common.Contents;
import com.tool.cache.interceptor.MethodCacheInterceptor;


public class InitStart {

	@PostConstruct
	public void init() {
		System.out.println("---------------------------------");
		System.out.println("-\t\t系统初始化\t\t-");
		System.out.println("---------------------------------");
		Contents.isCache = MethodCacheInterceptor.cache;
		System.out.println("Contents.isCache : " + Contents.isCache);
		System.out.println("---------------------------------");
	}
}
