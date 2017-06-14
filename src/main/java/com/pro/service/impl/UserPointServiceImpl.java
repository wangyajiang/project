package com.pro.service.impl;

import java.util.List;
 import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pro.service.IUserPointService;
import com.pro.dao.IUserPointDao;
import com.pro.entity.UserPoint;
import com.tool.bean.Paging;
import com.tool.exception.ServiceException;

public class UserPointServiceImpl implements IUserPointService {

	@Autowired
	private IUserPointDao userPointDao;

	public List<UserPoint> queryUserPointListByConditions(Map<String, Object> param) throws ServiceException {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		return userPointDao.queryByConditions(param);
	}

	public Paging<UserPoint> queryUserPointPaging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		Paging<UserPoint> paging = new Paging<UserPoint>();
		paging.setPage(page);
		paging.setPageSize(pageSize);
		paging.setTota(userPointDao.queryCount(param));
		if (paging.getTotal() > 0L) {
			paging.setList(userPointDao.queryList(param, page, pageSize, sort, desc));
		}
		return paging;
	}

}
