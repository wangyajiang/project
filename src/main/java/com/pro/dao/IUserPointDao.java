package com.pro.dao;

import java.util.List;
import java.util.Map;
import com.tool.exception.DaoException;
import com.pro.entity.UserPoint;

public interface IUserPointDao {

	void insert(UserPoint entity) throws DaoException;

	void delete(Integer id) throws DaoException;

	void delete(List<Integer> ids) throws DaoException;

	void update(UserPoint entity) throws DaoException;

	UserPoint queryById(Integer id) throws DaoException;

	List<UserPoint> queryAll() throws DaoException;

	List<UserPoint> queryByConditions(Map<String, Object> param) throws DaoException;

	long queryCount(Map<String, Object> param) throws DaoException;

	List<UserPoint> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException;

}
