package com.pro.dao;

import java.util.List;
import java.util.Map;
import com.tool.exception.DaoException;
import com.pro.entity.Items;

public interface IItemsDao {

	void insert(Items entity) throws DaoException;

	void delete(Integer id) throws DaoException;

	void delete(List<Integer> ids) throws DaoException;

	void update(Items entity) throws DaoException;

	Items queryById(Integer id) throws DaoException;

	List<Items> queryAll() throws DaoException;

	List<Items> queryByConditions(Map<String, Object> param) throws DaoException;

	long queryCount(Map<String, Object> param) throws DaoException;

	List<Items> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException;

}
