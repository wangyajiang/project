package com.pro.dao.impl;

import java.util.List;
import java.util.Map;

import com.pro.dao.IItemsActiveDao;
import com.pro.entity.ItemsActive;
import com.tool.exception.DaoException;
import com.tool.mysql.BaseDao;

public class ItemsActiveDaoImpl extends BaseDao<ItemsActive, Integer> implements IItemsActiveDao {

	public void insert(ItemsActive entity) throws DaoException {
		insertEntity(null, entity);
	}

	public void delete(Integer id) throws DaoException {
		deleteEntityById(null, id);
	}

	public void delete(List<Integer> ids) throws DaoException {
		deleteEntityByIds(null, ids);
	}

	public void update(ItemsActive  entity) throws DaoException {
		updateEntity(null, entity);
	}

	public ItemsActive  queryById(Integer id) throws DaoException {
		return queryEntityById(null, id);
	}

	public List<ItemsActive> queryAll() throws DaoException {
		return queryEntityAll(null);
	}

	public List<ItemsActive> queryByConditions(Map<String, Object> param) throws DaoException {
		return queryEntityByConditions(null, param);
	}

	public long queryCount(Map<String, Object> param) throws DaoException {
		return queryEntityCount(null, param);
	}

	public List<ItemsActive> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException {
		return queryEntityList(null, param, page, pageSize, sort, desc);
	}

}
