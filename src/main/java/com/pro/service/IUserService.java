package com.pro.service;

import java.util.List;
import java.util.Map;

import com.pro.entity.User;
import com.tool.bean.Paging;
import com.tool.exception.ServiceException;

public interface IUserService {

	List<User> queryUserListByConditions(Map<String, Object> param) throws ServiceException;

	Paging<User> queryUserPaging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException;
	
	Map<String, Object> login(String mobile, String password) throws ServiceException;
}
