package com.pro.dao.impl;

import java.util.List;
import java.util.Map;

import com.pro.dao.IFocusMapDao;
import com.pro.entity.FocusMap;
import com.tool.exception.DaoException;
import com.tool.mysql.BaseDao;

public class FocusMapDaoImpl extends BaseDao<FocusMap, Integer> implements IFocusMapDao {

	public void insert(FocusMap entity) throws DaoException {
		insertEntity(null, entity);
	}

	public void delete(Integer id) throws DaoException {
		deleteEntityById(null, id);
	}

	public void delete(List<Integer> ids) throws DaoException {
		deleteEntityByIds(null, ids);
	}

	public void update(FocusMap  entity) throws DaoException {
		updateEntity(null, entity);
	}

	public FocusMap  queryById(Integer id) throws DaoException {
		return queryEntityById(null, id);
	}

	public List<FocusMap> queryAll() throws DaoException {
		return queryEntityAll(null);
	}

	public List<FocusMap> queryByConditions(Map<String, Object> param) throws DaoException {
		return queryEntityByConditions(null, param);
	}

	public long queryCount(Map<String, Object> param) throws DaoException {
		return queryEntityCount(null, param);
	}

	public List<FocusMap> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException {
		return queryEntityList(null, param, page, pageSize, sort, desc);
	}

}
