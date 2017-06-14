package com.pro.dao;

import java.util.List;
import java.util.Map;
import com.tool.exception.DaoException;
import com.pro.entity.ItemsPics;

public interface IItemsPicsDao {

	void insert(ItemsPics entity) throws DaoException;

	void delete(Integer id) throws DaoException;

	void delete(List<Integer> ids) throws DaoException;

	void update(ItemsPics entity) throws DaoException;

	ItemsPics queryById(Integer id) throws DaoException;

	List<ItemsPics> queryAll() throws DaoException;

	List<ItemsPics> queryByConditions(Map<String, Object> param) throws DaoException;

	long queryCount(Map<String, Object> param) throws DaoException;

	List<ItemsPics> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException;

}
