package com.pro.service.impl;

import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.pro.service.IUserService;
import com.pro.utils.TokenUtils;
import com.pro.common.CacheKeys;
import com.pro.common.Contents;
import com.pro.common.SessionUser;
import com.pro.dao.IUserDao;
import com.pro.entity.User;
import com.tool.bean.Paging;
import com.tool.cache.CacheService;
import com.tool.constant.EnumConfig;
import com.tool.constant.RegexConstant;
import com.tool.exception.ServiceException;
import com.tool.utils.CheckUtils;
import com.tool.utils.ReflectUtils;

public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao;
	@Autowired
	private CacheService cacheService;

	public List<User> queryUserListByConditions(Map<String, Object> param) throws ServiceException {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		return userDao.queryByConditions(param);
	}

	public Paging<User> queryUserPaging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		Paging<User> paging = new Paging<User>();
		paging.setPage(page);
		paging.setPageSize(pageSize);
		paging.setTota(userDao.queryCount(param));
		if (paging.getTotal() > 0L) {
			paging.setList(userDao.queryList(param, page, pageSize, sort, desc));
		}
		return paging;
	}
	
	public Map<String, Object> login(String mobile, String password) throws ServiceException {
		if (CheckUtils.isBlank(mobile)) {
			throw new ServiceException(EnumConfig.S_CODE.USERNAME_OR_PWD_ERROR, "账户不能为空！");
		}
		if (CheckUtils.isBlank(password)) {
			throw new ServiceException(EnumConfig.S_CODE.USERNAME_OR_PWD_ERROR, "密码不能为空！");
		}
		if (Pattern.compile(RegexConstant.COMMON.MOBILE).matcher(mobile).find()) {
			throw new ServiceException(EnumConfig.S_CODE.USERNAME_OR_PWD_ERROR, "手机格式不正确！");
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("mobile", mobile);
		param.put("password", password);
		param.put("status", EnumConfig.STATUS.OK.getCode());
		List<User> list = userDao.queryByConditions(param);
		if (list == null || list.isEmpty() || list.size() != 1) {
			throw new ServiceException(EnumConfig.S_CODE.USERNAME_OR_PWD_ERROR, "账户密码不正确！");
		}
		User user = list.get(0);
		SessionUser key = new SessionUser();
		key.setUserId(user.getUserId());
		key.setName(user.getNickname());
		String token = TokenUtils.createToken(key);
		if (Contents.isCache) {
			cacheService.setCache(CacheKeys.USER_LOGIN_PRIFXX + user.getUserId(), user, Contents.TOKEN_TIME);
		}
		Map<String, Object> rt = ReflectUtils.objToMap(user, "", "userId", "nickname", "picHead", "mobile", "address");
		rt.put("token", token);
		return rt;
	}
	

}
