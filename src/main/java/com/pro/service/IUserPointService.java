package com.pro.service;

import java.util.List;
import java.util.Map;

import com.pro.entity.UserPoint;
import com.tool.bean.Paging;
import com.tool.exception.ServiceException;

public interface IUserPointService {

	List<UserPoint> queryUserPointListByConditions(Map<String, Object> param) throws ServiceException;

	Paging<UserPoint> queryUserPointPaging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException;

}
