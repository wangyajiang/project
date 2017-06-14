package com.pro.dao.impl;

import java.util.List;
import java.util.Map;

import com.pro.dao.IUserPointLogDao;
import com.pro.entity.UserPointLog;
import com.tool.exception.DaoException;
import com.tool.mysql.BaseDao;

public class UserPointLogDaoImpl extends BaseDao<UserPointLog, Integer> implements IUserPointLogDao {

	public void insert(UserPointLog entity) throws DaoException {
		insertEntity(null, entity);
	}

	public void delete(Integer id) throws DaoException {
		deleteEntityById(null, id);
	}

	public void delete(List<Integer> ids) throws DaoException {
		deleteEntityByIds(null, ids);
	}

	public void update(UserPointLog  entity) throws DaoException {
		updateEntity(null, entity);
	}

	public UserPointLog  queryById(Integer id) throws DaoException {
		return queryEntityById(null, id);
	}

	public List<UserPointLog> queryAll() throws DaoException {
		return queryEntityAll(null);
	}

	public List<UserPointLog> queryByConditions(Map<String, Object> param) throws DaoException {
		return queryEntityByConditions(null, param);
	}

	public long queryCount(Map<String, Object> param) throws DaoException {
		return queryEntityCount(null, param);
	}

	public List<UserPointLog> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException {
		return queryEntityList(null, param, page, pageSize, sort, desc);
	}

}
