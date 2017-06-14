package com.pro.dao.impl;

import java.util.List;
import java.util.Map;

import com.pro.dao.IActiveBillDao;
import com.pro.entity.ActiveBill;
import com.tool.exception.DaoException;
import com.tool.mysql.BaseDao;

public class ActiveBillDaoImpl extends BaseDao<ActiveBill, Integer> implements IActiveBillDao {

	public void insert(ActiveBill entity) throws DaoException {
		insertEntity(null, entity);
	}

	public void delete(Integer id) throws DaoException {
		deleteEntityById(null, id);
	}

	public void delete(List<Integer> ids) throws DaoException {
		deleteEntityByIds(null, ids);
	}

	public void update(ActiveBill  entity) throws DaoException {
		updateEntity(null, entity);
	}

	public ActiveBill  queryById(Integer id) throws DaoException {
		return queryEntityById(null, id);
	}

	public List<ActiveBill> queryAll() throws DaoException {
		return queryEntityAll(null);
	}

	public List<ActiveBill> queryByConditions(Map<String, Object> param) throws DaoException {
		return queryEntityByConditions(null, param);
	}

	public long queryCount(Map<String, Object> param) throws DaoException {
		return queryEntityCount(null, param);
	}

	public List<ActiveBill> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException {
		return queryEntityList(null, param, page, pageSize, sort, desc);
	}

}
