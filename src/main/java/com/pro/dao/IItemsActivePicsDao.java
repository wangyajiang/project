package com.pro.dao;

import java.util.List;
import java.util.Map;
import com.tool.exception.DaoException;
import com.pro.entity.ItemsActivePics;

public interface IItemsActivePicsDao {

	void insert(ItemsActivePics entity) throws DaoException;

	void delete(Integer id) throws DaoException;

	void delete(List<Integer> ids) throws DaoException;

	void update(ItemsActivePics entity) throws DaoException;

	ItemsActivePics queryById(Integer id) throws DaoException;

	List<ItemsActivePics> queryAll() throws DaoException;

	List<ItemsActivePics> queryByConditions(Map<String, Object> param) throws DaoException;

	long queryCount(Map<String, Object> param) throws DaoException;

	List<ItemsActivePics> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException;

}
