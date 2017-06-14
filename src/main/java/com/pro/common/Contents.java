package com.pro.common;

import java.util.List;

import com.pro.entity.ItemType;

public class Contents {
	
	//缓存服务是否开启 false：未开启，true：开启
	public static boolean isCache = false;
	
	//分页默认每页数目
	public static int PAGE_SIZE = 10;
	
	//商品类型
	public static List<ItemType> itemTypes = null;
	
	//token失效时间（秒）
	public static int TOKEN_TIME = 60 * 30;
}
