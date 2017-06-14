package com.tool.mysql;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.tool.constant.EnumConfig;
import com.tool.exception.DaoException;

public interface IBaseDao<T, ID extends Serializable> {
	
	void insertEntity(EnumConfig.DB_NAME dbName, T entity) throws DaoException;

	void deleteEntityById(EnumConfig.DB_NAME dbName, ID id)  throws DaoException;
	
	void deleteEntityByIds(EnumConfig.DB_NAME dbName, List<ID> ids)  throws DaoException;
	
	void updateEntity(EnumConfig.DB_NAME dbName, T entity)  throws DaoException;

	T queryEntityById(EnumConfig.DB_NAME dbName, ID id) throws DaoException;

	List<T> queryEntityAll(EnumConfig.DB_NAME dbName)  throws DaoException;
	
	long queryEntityCount(EnumConfig.DB_NAME dbName, Map<String, Object> param)  throws DaoException;
	
	List<T> queryEntityList(EnumConfig.DB_NAME dbName, Map<String, Object> param, int page, int pageSize, String sort, boolean desc)  throws DaoException;
	
}
