package com.pro.dao.impl;

import java.util.List;
import java.util.Map;

import com.pro.dao.IActiveOrderDao;
import com.pro.entity.ActiveOrder;
import com.tool.exception.DaoException;
import com.tool.mysql.BaseDao;

public class ActiveOrderDaoImpl extends BaseDao<ActiveOrder, Integer> implements IActiveOrderDao {

	public void insert(ActiveOrder entity) throws DaoException {
		insertEntity(null, entity);
	}

	public void delete(Integer id) throws DaoException {
		deleteEntityById(null, id);
	}

	public void delete(List<Integer> ids) throws DaoException {
		deleteEntityByIds(null, ids);
	}

	public void update(ActiveOrder  entity) throws DaoException {
		updateEntity(null, entity);
	}

	public ActiveOrder  queryById(Integer id) throws DaoException {
		return queryEntityById(null, id);
	}

	public List<ActiveOrder> queryAll() throws DaoException {
		return queryEntityAll(null);
	}

	public List<ActiveOrder> queryByConditions(Map<String, Object> param) throws DaoException {
		return queryEntityByConditions(null, param);
	}

	public long queryCount(Map<String, Object> param) throws DaoException {
		return queryEntityCount(null, param);
	}

	public List<ActiveOrder> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException {
		return queryEntityList(null, param, page, pageSize, sort, desc);
	}

}
