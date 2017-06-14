package com.pro.dao.impl;

import java.util.List;
import java.util.Map;

import com.pro.dao.IUserPointDao;
import com.pro.entity.UserPoint;
import com.tool.exception.DaoException;
import com.tool.mysql.BaseDao;

public class UserPointDaoImpl extends BaseDao<UserPoint, Integer> implements IUserPointDao {

	public void insert(UserPoint entity) throws DaoException {
		insertEntity(null, entity);
	}

	public void delete(Integer id) throws DaoException {
		deleteEntityById(null, id);
	}

	public void delete(List<Integer> ids) throws DaoException {
		deleteEntityByIds(null, ids);
	}

	public void update(UserPoint  entity) throws DaoException {
		updateEntity(null, entity);
	}

	public UserPoint  queryById(Integer id) throws DaoException {
		return queryEntityById(null, id);
	}

	public List<UserPoint> queryAll() throws DaoException {
		return queryEntityAll(null);
	}

	public List<UserPoint> queryByConditions(Map<String, Object> param) throws DaoException {
		return queryEntityByConditions(null, param);
	}

	public long queryCount(Map<String, Object> param) throws DaoException {
		return queryEntityCount(null, param);
	}

	public List<UserPoint> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException {
		return queryEntityList(null, param, page, pageSize, sort, desc);
	}

}
