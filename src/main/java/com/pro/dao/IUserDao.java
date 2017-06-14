package com.pro.dao;

import java.util.List;
import java.util.Map;
import com.tool.exception.DaoException;
import com.pro.entity.User;

public interface IUserDao {

	void insert(User entity) throws DaoException;

	void delete(Integer id) throws DaoException;

	void delete(List<Integer> ids) throws DaoException;

	void update(User entity) throws DaoException;

	User queryById(Integer id) throws DaoException;

	List<User> queryAll() throws DaoException;

	List<User> queryByConditions(Map<String, Object> param) throws DaoException;

	long queryCount(Map<String, Object> param) throws DaoException;

	List<User> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException;

}
