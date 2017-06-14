package com.pro.dao;

import java.util.List;
import java.util.Map;

import com.pro.common.CacheKeys;
import com.pro.entity.FocusMap;
import com.tool.cache.annotation.DelCache;
import com.tool.cache.annotation.GetCache;
import com.tool.exception.DaoException;

public interface IFocusMapDao {

	@DelCache(names = {CacheKeys.FOCUS_MAP_ALL})
	void insert(FocusMap entity) throws DaoException;

	@DelCache(names = {CacheKeys.FOCUS_MAP_ALL })
	void delete(Integer id) throws DaoException;

	@DelCache(names = {CacheKeys.FOCUS_MAP_ALL})
	void delete(List<Integer> ids) throws DaoException;

	@DelCache(names = {CacheKeys.FOCUS_MAP_ALL })
	void update(FocusMap entity) throws DaoException;

	FocusMap queryById(Integer id) throws DaoException;

	@GetCache(name = CacheKeys.FOCUS_MAP_ALL)
	List<FocusMap> queryAll() throws DaoException;

	List<FocusMap> queryByConditions(Map<String, Object> param) throws DaoException;
	
	long queryCount(Map<String, Object> param) throws DaoException;

	@GetCache(name = CacheKeys.FOCUS_MAP_ALL)
	List<FocusMap> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException;

}
