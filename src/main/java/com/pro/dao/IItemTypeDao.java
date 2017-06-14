package com.pro.dao;

import java.util.List;
import java.util.Map;

import com.tool.cache.annotation.DelCache;
import com.tool.cache.annotation.GetCache;
import com.tool.exception.DaoException;
import com.pro.common.CacheKeys;
import com.pro.entity.ItemType;

public interface IItemTypeDao {

	void insert(ItemType entity) throws DaoException;

	@DelCache(names = {CacheKeys.ITEM_TYPE_ALL})
	void delete(Integer id) throws DaoException;
	
	@DelCache(names = {CacheKeys.ITEM_TYPE_ALL})
	void delete(List<Integer> ids) throws DaoException;

	@DelCache(names = { CacheKeys.ITEM_TYPE_ALL })
	void update(ItemType entity) throws DaoException;

	ItemType queryById(Integer id) throws DaoException;
	
	@GetCache(name = CacheKeys.ITEM_TYPE_ALL)
	List<ItemType> queryAll() throws DaoException;

	List<ItemType> queryByConditions(Map<String, Object> param) throws DaoException;

	long queryCount(Map<String, Object> param) throws DaoException;

	List<ItemType> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException;

}
