package com.pro.dao;

import java.util.List;
import java.util.Map;
import com.tool.exception.DaoException;
import com.pro.entity.UserPointLog;

public interface IUserPointLogDao {

	void insert(UserPointLog entity) throws DaoException;

	void delete(Integer id) throws DaoException;

	void delete(List<Integer> ids) throws DaoException;

	void update(UserPointLog entity) throws DaoException;

	UserPointLog queryById(Integer id) throws DaoException;

	List<UserPointLog> queryAll() throws DaoException;

	List<UserPointLog> queryByConditions(Map<String, Object> param) throws DaoException;

	long queryCount(Map<String, Object> param) throws DaoException;

	List<UserPointLog> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException;

}
