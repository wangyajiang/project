package com.pro.utils;

import com.pro.common.Contents;
import com.pro.common.SessionUser;
import com.tool.constant.EnumConfig.S_CODE;
import com.tool.exception.ServiceException;
import com.tool.utils.CheckUtils;
import com.tool.utils.EncryptUtils;

public class TokenUtils {
	private static final String secKey = "check token key"; 

	public static String createToken(SessionUser key) throws ServiceException {
		if (key == null) {
			throw new ServiceException(S_CODE.NO_RECORD, "token创建失败！");
		}
		key.setCreateTime((int)(System.currentTimeMillis() / 1000));
		StringBuilder str = new StringBuilder();
		str.append(key.getUserId());
		str.append(":");
		str.append(key.getName());
		str.append(":");
		str.append(key.getDbNo());
		str.append(":");
		str.append(key.getCreateTime());
		try {
			return EncryptUtils.encrypt3DES(str.toString(), secKey);
		} catch (Exception e) {
			throw new ServiceException(S_CODE.NO_RECORD, "token创建失败！");
		}
	}
	
	public static SessionUser tokenToSessionUser(String token) throws ServiceException {
		SessionUser key = null;
		try {
			String str = EncryptUtils.decrypt3DES(token, secKey);
			if (CheckUtils.isBlank(str)) {
				return null;
			}
			String[] arr = str.split(":");
			if (arr.length != 4) {
				return null;
			}
			key = new SessionUser();
			key.setUserId(Integer.parseInt(arr[0]));
			key.setName(arr[1]);
			key.setDbNo(Integer.parseInt(arr[2]));
			key.setCreateTime(Integer.parseInt(arr[3]));
		} catch (Exception e) {
			return null;
		}
		return key;
	}
	
	public static boolean isTokenTimeOut(String token, Integer lastRequestTime) throws ServiceException {
		SessionUser key = tokenToSessionUser(token);
		lastRequestTime = lastRequestTime == null ? key.getCreateTime() : lastRequestTime;
		int subTime = (int)(System.currentTimeMillis() / 1000) - lastRequestTime;
		if (subTime < Contents.TOKEN_TIME) {
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		SessionUser key = new SessionUser();
		key.setUserId(1);
		key.setName("张三");
		try {
			String token = TokenUtils.createToken(key);
			System.out.println(token);
			System.out.println(TokenUtils.tokenToSessionUser(token));
			System.out.println(isTokenTimeOut(token, null));
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
