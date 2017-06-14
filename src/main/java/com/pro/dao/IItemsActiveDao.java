package com.pro.dao;

import java.util.List;
import java.util.Map;
import com.tool.exception.DaoException;
import com.pro.entity.ItemsActive;

public interface IItemsActiveDao {

	void insert(ItemsActive entity) throws DaoException;

	void delete(Integer id) throws DaoException;

	void delete(List<Integer> ids) throws DaoException;

	void update(ItemsActive entity) throws DaoException;

	ItemsActive queryById(Integer id) throws DaoException;

	List<ItemsActive> queryAll() throws DaoException;

	List<ItemsActive> queryByConditions(Map<String, Object> param) throws DaoException;

	long queryCount(Map<String, Object> param) throws DaoException;

	List<ItemsActive> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException;

}
