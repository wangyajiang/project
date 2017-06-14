package com.pro.dao.impl;

import java.util.List;
import java.util.Map;

import com.pro.dao.IItemsPicsDao;
import com.pro.entity.ItemsPics;
import com.tool.exception.DaoException;
import com.tool.mysql.BaseDao;

public class ItemsPicsDaoImpl extends BaseDao<ItemsPics, Integer> implements IItemsPicsDao {

	public void insert(ItemsPics entity) throws DaoException {
		insertEntity(null, entity);
	}

	public void delete(Integer id) throws DaoException {
		deleteEntityById(null, id);
	}

	public void delete(List<Integer> ids) throws DaoException {
		deleteEntityByIds(null, ids);
	}

	public void update(ItemsPics  entity) throws DaoException {
		updateEntity(null, entity);
	}

	public ItemsPics  queryById(Integer id) throws DaoException {
		return queryEntityById(null, id);
	}

	public List<ItemsPics> queryAll() throws DaoException {
		return queryEntityAll(null);
	}

	public List<ItemsPics> queryByConditions(Map<String, Object> param) throws DaoException {
		return queryEntityByConditions(null, param);
	}

	public long queryCount(Map<String, Object> param) throws DaoException {
		return queryEntityCount(null, param);
	}

	public List<ItemsPics> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException {
		return queryEntityList(null, param, page, pageSize, sort, desc);
	}

}
