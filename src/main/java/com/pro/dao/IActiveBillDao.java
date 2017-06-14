package com.pro.dao;

import java.util.List;
import java.util.Map;
import com.tool.exception.DaoException;
import com.pro.entity.ActiveBill;

public interface IActiveBillDao {

	void insert(ActiveBill entity) throws DaoException;

	void delete(Integer id) throws DaoException;

	void delete(List<Integer> ids) throws DaoException;

	void update(ActiveBill entity) throws DaoException;

	ActiveBill queryById(Integer id) throws DaoException;

	List<ActiveBill> queryAll() throws DaoException;

	List<ActiveBill> queryByConditions(Map<String, Object> param) throws DaoException;

	long queryCount(Map<String, Object> param) throws DaoException;

	List<ActiveBill> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException;

}
