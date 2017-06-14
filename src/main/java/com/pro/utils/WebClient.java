package com.pro.utils;


import java.io.IOException;
import java.util.Map;

import com.tool.constant.EnumConfig;
import com.tool.exception.ServiceException;
import com.tool.utils.CheckUtils;
import com.tool.utils.WebUtils;


public class WebClient {

	private static final String ENCODE = "UTF-8";
	private static int maxConnOut = 500;

	public static String request(String url, Map<String, String> paramMap) throws ServiceException {
		synchronized (WebClient.class) {}
		String str = null;
		try {
			str = WebUtils.doPost(url, paramMap, ENCODE, maxConnOut, maxConnOut);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ServiceException(EnumConfig.S_CODE.FAIL);
		}
		if (CheckUtils.isBlank(str)) {
			throw new ServiceException(EnumConfig.S_CODE.NO_PAGE);
		}
		return str;
	}
	
}
