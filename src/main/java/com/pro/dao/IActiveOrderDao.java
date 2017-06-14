package com.pro.dao;

import java.util.List;
import java.util.Map;
import com.tool.exception.DaoException;
import com.pro.entity.ActiveOrder;

public interface IActiveOrderDao {

	void insert(ActiveOrder entity) throws DaoException;

	void delete(Integer id) throws DaoException;

	void delete(List<Integer> ids) throws DaoException;

	void update(ActiveOrder entity) throws DaoException;

	ActiveOrder queryById(Integer id) throws DaoException;

	List<ActiveOrder> queryAll() throws DaoException;

	List<ActiveOrder> queryByConditions(Map<String, Object> param) throws DaoException;

	long queryCount(Map<String, Object> param) throws DaoException;

	List<ActiveOrder> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException;

}
