package com.tool.cache.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DelCache {
	/**
	 * 一组缓存key的前缀（prefix） 比如names包含2个prefix， keyNum=1,参数为int
	 * userId,删除的时候将会删除name[0].userId.,name[1].userId.
	 * 
	 * @return
	 */
	String[] names();

	/**
	 * 缓存key由name加上方法前keyNum个参数组成，默认为1,这种参数必须是简单类型
	 * 
	 * 如果参数为 int userId,int groupId 比如keyNum=1,那么key格式为 name.userId.
	 * 比如keyNum=2，那么key格式为 name.userId.groupId.
	 * 
	 * @return
	 */
	int keyNum() default 0;
}
