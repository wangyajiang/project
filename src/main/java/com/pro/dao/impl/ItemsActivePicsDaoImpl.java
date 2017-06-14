package com.pro.dao.impl;

import java.util.List;
import java.util.Map;

import com.pro.dao.IItemsActivePicsDao;
import com.pro.entity.ItemsActivePics;
import com.tool.exception.DaoException;
import com.tool.mysql.BaseDao;

public class ItemsActivePicsDaoImpl extends BaseDao<ItemsActivePics, Integer> implements IItemsActivePicsDao {

	public void insert(ItemsActivePics entity) throws DaoException {
		insertEntity(null, entity);
	}

	public void delete(Integer id) throws DaoException {
		deleteEntityById(null, id);
	}

	public void delete(List<Integer> ids) throws DaoException {
		deleteEntityByIds(null, ids);
	}

	public void update(ItemsActivePics  entity) throws DaoException {
		updateEntity(null, entity);
	}

	public ItemsActivePics  queryById(Integer id) throws DaoException {
		return queryEntityById(null, id);
	}

	public List<ItemsActivePics> queryAll() throws DaoException {
		return queryEntityAll(null);
	}

	public List<ItemsActivePics> queryByConditions(Map<String, Object> param) throws DaoException {
		return queryEntityByConditions(null, param);
	}

	public long queryCount(Map<String, Object> param) throws DaoException {
		return queryEntityCount(null, param);
	}

	public List<ItemsActivePics> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException {
		return queryEntityList(null, param, page, pageSize, sort, desc);
	}

}
