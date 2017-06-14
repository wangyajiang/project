package com.pro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pro.common.CacheKeys;
import com.pro.common.Contents;
import com.pro.common.SessionUser;
import com.pro.dao.IUserPointLogDao;
import com.pro.entity.UserPointLog;
import com.pro.service.IUserPointLogService;
import com.tool.cache.CacheService;
import com.tool.exception.ServiceException;
import com.tool.utils.ConvertUtils;
import com.tool.utils.DTUtils;

public class UserPointLogServiceImpl implements IUserPointLogService {

	@Autowired
	private IUserPointLogDao userPointLogDao;
	@Autowired
	private CacheService cacheService;
	
	public UserPointLog getUserPointLogLast(SessionUser key) throws ServiceException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user_id", key.getUserId());
		List<UserPointLog> list = userPointLogDao.queryList(param, 1, 1, "create_time", true);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 最后签到时间 0为未签到
	 */
	public long lastSignTime(SessionUser key) throws ServiceException {
		String KEY = CacheKeys.SIGN_LAST_TIME_PRIFXX + key.getUserId();
		if (Contents.isCache) {
			Object obj = cacheService.getCache(KEY);
			if (obj != null) {
				return ConvertUtils.getLong(cacheService.getCache(KEY), 0L);
			}
		}
		UserPointLog userPointLog = getUserPointLogLast(key);
		long time = 0L;
		if (userPointLog != null) {
			time = userPointLog.getCreateTime().getTime();
		}
		cacheService.setCache(KEY, time, 86400 * 1000L);
		return time;
		
	}
	
	/**
	 * 已经签到
	 */
	public boolean isSign(SessionUser key) throws ServiceException {
		Long time = lastSignTime(key);
		long nowZeroTime = DTUtils.getZeroDate(DTUtils.getNowDate()).getTime();
		if (time < nowZeroTime) {
			return false;
		}
		return true;
	}

	public void sign(SessionUser key) throws ServiceException {
		
//		cacheService.getCache(key);
	}


}
