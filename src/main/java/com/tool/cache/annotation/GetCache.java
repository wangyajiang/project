package com.tool.cache.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface GetCache {
	/**
	 * 缓存key的前缀（prefix）
	 * 
	 * @return
	 */
	String name();

	/**
	 * 缓存key由name加上方法前keyNum个参数组成，默认为1,这种参数必须是简单类型
	 * 
	 * 如果参数为 int userId,int groupId 比如keyNum=1,那么key格式为 name.userId.
	 * 比如keyNum=2，那么key格式为 name.userId.groupId.
	 * 
	 * @return
	 */
	int keyNum() default 0;

	/**
	 * 过期时间，单位ms,如果为0，那么表示不过期，默认为31天
	 * 
	 * @return
	 */
	long expiry() default 2678400000l;
}
