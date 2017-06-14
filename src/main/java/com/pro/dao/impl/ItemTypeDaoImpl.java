package com.pro.dao.impl;

import java.util.List;
import java.util.Map;

import com.pro.dao.IItemTypeDao;
import com.pro.entity.ItemType;
import com.tool.exception.DaoException;
import com.tool.mysql.BaseDao;

public class ItemTypeDaoImpl extends BaseDao<ItemType, Integer> implements IItemTypeDao {

	public void insert(ItemType entity) throws DaoException {
		insertEntity(null, entity);
	}

	public void delete(Integer id) throws DaoException {
		deleteEntityById(null, id);
	}

	public void delete(List<Integer> ids) throws DaoException {
		deleteEntityByIds(null, ids);
	}

	public void update(ItemType  entity) throws DaoException {
		updateEntity(null, entity);
	}

	public ItemType  queryById(Integer id) throws DaoException {
		return queryEntityById(null, id);
	}

	public List<ItemType> queryAll() throws DaoException {
		return queryEntityAll(null);
	}

	public List<ItemType> queryByConditions(Map<String, Object> param) throws DaoException {
		return queryEntityByConditions(null, param);
	}

	public long queryCount(Map<String, Object> param) throws DaoException {
		return queryEntityCount(null, param);
	}

	public List<ItemType> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException {
		return queryEntityList(null, param, page, pageSize, sort, desc);
	}

}
