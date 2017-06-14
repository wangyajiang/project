package com.pro.dao.impl;

import java.util.List;
import java.util.Map;

import com.pro.dao.IUserDao;
import com.pro.entity.User;
import com.tool.exception.DaoException;
import com.tool.mysql.BaseDao;

public class UserDaoImpl extends BaseDao<User, Integer> implements IUserDao {

	public void insert(User entity) throws DaoException {
		insertEntity(null, entity);
	}

	public void delete(Integer id) throws DaoException {
		deleteEntityById(null, id);
	}

	public void delete(List<Integer> ids) throws DaoException {
		deleteEntityByIds(null, ids);
	}

	public void update(User  entity) throws DaoException {
		updateEntity(null, entity);
	}

	public User  queryById(Integer id) throws DaoException {
		return queryEntityById(null, id);
	}

	public List<User> queryAll() throws DaoException {
		return queryEntityAll(null);
	}

	public List<User> queryByConditions(Map<String, Object> param) throws DaoException {
		return queryEntityByConditions(null, param);
	}

	public long queryCount(Map<String, Object> param) throws DaoException {
		return queryEntityCount(null, param);
	}

	public List<User> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException {
		return queryEntityList(null, param, page, pageSize, sort, desc);
	}

}
