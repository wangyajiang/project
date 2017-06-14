package com.tool.mysql;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tool.cache.interceptor.MethodCacheInterceptor;
import com.tool.constant.EnumConfig.DB_NAME;
import com.tool.exception.DaoException;
import com.tool.utils.CheckUtils;
import com.tool.utils.ConvertUtils;
import com.tool.utils.DBHelper;
import com.tool.utils.ReflectUtils;
import com.tool.utils.SqlFilter;



public class BaseDao<T, ID extends Serializable> implements IBaseDao<T, ID> {
	Logger logger = LoggerFactory.getLogger(MethodCacheInterceptor.class);
	
	private Class<T> entityClass;
	private String idName;

	public BaseDao() {
		this.entityClass = getEntityClass();
		this.idName = getId();
	}

	@SuppressWarnings("unchecked")
	private Class<T> getEntityClass() {
		return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	private String getId() {
		Table desc = entityClass.getAnnotation(Table.class);
		String idName = "";
		if (desc != null) {
			idName = desc.id();
		}
		if (idName == null || idName.equals(""))
			idName = "id";
		return idName;
	}

	public void insertEntity(DB_NAME dbName, T entity) throws DaoException {
		if (entity == null) {
			throw new DaoException();
		}
		long t1 = System.currentTimeMillis();
		Map<String, Object> item = ReflectUtils.objToMap(entity);
		if (item == null || item.isEmpty()) {
			throw new DaoException();
		}
		DBHelper db = new DBHelper(dbName);
		String tabName = null;
		try {
			int count = 0;
			String entityName = ConvertUtils.lowerFirstName(entityClass.getSimpleName());
			tabName = ConvertUtils.camelToUnderline(entityName);
			StringBuilder sql = new StringBuilder("insert into ");
			sql.append(tabName);
			sql.append(" (");
			List<Object> params = new ArrayList<Object>();
			for (String key : item.keySet()) {
				if (item.get(key) != null) {
					params.add(item.get(key));
					sql.append(ConvertUtils.camelToUnderline(key));
					sql.append(",");
				}
			}
			sql.delete(sql.length() - 1, sql.length());
			sql.append(") values (");
			for (int i = 0; i < params.size(); i ++) {
				sql.append("?,");
			}
			sql.delete(sql.length() - 1, sql.length());
			sql.append(")");
			count = db.executeSingle(sql.toString(), params);
			if (count == 0) {
				throw new DaoException();
			}
		} catch (Exception e) {
			throw new DaoException();
		} finally {
			db.close();
			logger.debug("method {} entity {} 耗时 ：{} milliseconds", this.getClass(), tabName, System.currentTimeMillis() - t1);
		}
	}

	public void deleteEntityById(DB_NAME dbName, ID id) throws DaoException {
		if (id == null) {
			throw new DaoException();
		}
		long t1 = System.currentTimeMillis();
		String tabName = null;
		DBHelper db = new DBHelper(dbName);
		try {
			String entityName = ConvertUtils.lowerFirstName(entityClass.getSimpleName());
			tabName = ConvertUtils.camelToUnderline(entityName);
			StringBuilder sql = new StringBuilder("delete from ");
			sql.append(tabName);
			sql.append(" where ");
			sql.append(ConvertUtils.camelToUnderline(idName));
			sql.append(" = ?");
			int count = db.executeSingle(sql.toString(), new Object[] {id});
			if (count == 0) {
				throw new DaoException();
			}
		} catch (Exception e) {
			throw new DaoException();
		} finally {
			db.close();
			logger.debug("method {} entity {} 耗时 ：{} milliseconds", this.getClass(), tabName, System.currentTimeMillis() - t1);
		}
	}

	public void deleteEntityByIds(DB_NAME dbName, List<ID> ids) throws DaoException {
		if (ids == null || !ids.isEmpty()) {
			throw new DaoException();
		}
		long t1 = System.currentTimeMillis();
		String tabName = null;
		DBHelper db = new DBHelper(dbName);
		try {
			String entityName = ConvertUtils.lowerFirstName(entityClass.getSimpleName());
			tabName = ConvertUtils.camelToUnderline(entityName);
			StringBuilder sql = new StringBuilder("delete from ");
			sql.append(tabName);
			sql.append(" where ");
			sql.append(ConvertUtils.camelToUnderline(idName));
			sql.append(" = ?");
			List<Object[]> list = new ArrayList<Object[]>();
			for (ID id : ids) {
				list.add(new Object[] {id});
			}
			long count = db.executeBatch(sql.toString(), list);
			if (count == 0L) {
				throw new DaoException();
			}
		} catch (Exception e) {
			throw new DaoException();
		} finally {
			db.close();
			logger.debug("method {} entity {} 耗时 ：{} milliseconds", this.getClass(), tabName, System.currentTimeMillis() - t1);
		}
	}

	public void updateEntity(DB_NAME dbName, T entity) throws DaoException {
		if (entity == null) {
			throw new DaoException();
		}
		Map<String, Object> item = ReflectUtils.objToMap(entity);
		if (item == null || item.isEmpty()) {
			throw new DaoException();
		}
		long t1 = System.currentTimeMillis();
		String tabName = null;
		DBHelper db = new DBHelper(dbName);
		try {
			int count = 0;
			String entityName = ConvertUtils.lowerFirstName(entityClass.getSimpleName());
			tabName = ConvertUtils.camelToUnderline(entityName);
			StringBuilder sql = new StringBuilder("update ");
			sql.append(tabName);
			sql.append(" set ");
			List<Object> params = new ArrayList<Object>();
			for (String key : item.keySet()) {
				if (item.get(key) != null) {
					params.add(item.get(key));
					sql.append(ConvertUtils.camelToUnderline(key));
					sql.append(" = ?,");
				}
			}
			sql.delete(sql.length() - 1, sql.length());
			sql.append(" where ");
			sql.append(ConvertUtils.camelToUnderline(idName));
			sql.append(" = ?");
			params.add(item.get(idName));
			count = db.executeSingle(sql.toString(), params);
			if (count == 0) {
				throw new DaoException();
			}
		} catch (Exception e) {
			throw new DaoException();
		} finally {
			db.close();
			logger.debug("method {} entity {} 耗时 ：{} milliseconds", this.getClass(), tabName, System.currentTimeMillis() - t1);
		}
	}

	public T queryEntityById(DB_NAME dbName, ID id) throws DaoException {
		if (id == null) {
			throw new DaoException();
		}
		long t1 = System.currentTimeMillis();
		String tabName = null;
		DBHelper db = new DBHelper(dbName);
		try {
			String entityName = ConvertUtils.lowerFirstName(entityClass.getSimpleName());
			tabName = ConvertUtils.camelToUnderline(entityName);
			StringBuilder sql = new StringBuilder("select * from ");
			sql.append(tabName);
			sql.append(" where ");
			sql.append(ConvertUtils.camelToUnderline(idName));
			sql.append(" = ?");
			return db.querySingle(entityClass, sql.toString(), new Object[] {id});
		} catch (Exception e) {
			throw new DaoException();
		} finally {
			db.close();
			logger.debug("method {} entity {} 耗时 ：{} milliseconds", this.getClass(), tabName, System.currentTimeMillis() - t1);
		}
	}

	public List<T> queryEntityAll(DB_NAME dbName) throws DaoException {
		long t1 = System.currentTimeMillis();
		String tabName = null;
		DBHelper db = new DBHelper(dbName);
		try {
			String entityName = ConvertUtils.lowerFirstName(entityClass.getSimpleName());
			tabName = ConvertUtils.camelToUnderline(entityName);
			StringBuilder sql = new StringBuilder("select * from ");
			sql.append(tabName);
			return db.querySingle(entityClass, sql.toString(), null);
		} catch (Exception e) {
			throw new DaoException();
		} finally {
			db.close();
			logger.debug("method {} entity {} 耗时 ：{} milliseconds", this.getClass(), tabName, System.currentTimeMillis() - t1);
		}
	}
	
	public List<T> queryEntityByConditions(DB_NAME dbName, Map<String, Object> param) throws DaoException {
		long t1 = System.currentTimeMillis();
		String tabName = null;
		DBHelper db = new DBHelper(dbName);
		try {
			String entityName = ConvertUtils.lowerFirstName(entityClass.getSimpleName());
			tabName = ConvertUtils.camelToUnderline(entityName);
			StringBuilder sql = new StringBuilder("select * from ");
			sql.append(tabName);
			Object[] params = null;
			if (param != null && !param.isEmpty()) {
				params = new Object[param.size()];
				int i = 0;
				sql.append(" where 1 = 1");
				for (String key : param.keySet()) {
					params[i ++] = param.get(key);
					sql.append(" and ");
					sql.append(ConvertUtils.camelToUnderline(key));
					sql.append(" = ?");
				}
			}
			return db.queryList(entityClass, sql.toString(), params);
		} catch (Exception e) {
			throw new DaoException();
		} finally {
			db.close();
			logger.debug("method {} entity {} 耗时 ：{} milliseconds", this.getClass(), tabName, System.currentTimeMillis() - t1);
		}
	}

	public long queryEntityCount(DB_NAME dbName, Map<String, Object> param) throws DaoException {
		long t1 = System.currentTimeMillis();
		String tabName = null;
		DBHelper db = new DBHelper(dbName);
		try {
			String entityName = ConvertUtils.lowerFirstName(entityClass.getSimpleName());
			tabName = ConvertUtils.camelToUnderline(entityName);
			StringBuilder sql = new StringBuilder("select count(1) from ");
			sql.append(tabName);
			Object[] params = null;
			if (param != null && !param.isEmpty()) {
				params = new Object[param.size()];
				int i = 0;
				sql.append(" where 1 = 1");
				for (String key : param.keySet()) {
					params[i ++] = param.get(key);
					sql.append(" and ");
					sql.append(ConvertUtils.camelToUnderline(key));
					sql.append(" = ?");
				}
			}
			return db.queryCounts(sql.toString(), params);
		} catch (Exception e) {
			throw new DaoException();
		} finally {
			db.close();
			logger.debug("method {} entity {} 耗时 ：{} milliseconds", this.getClass(), tabName, System.currentTimeMillis() - t1);
		}
	}

	public List<T> queryEntityList(DB_NAME dbName, Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException {
		long t1 = System.currentTimeMillis();
		String tabName = null;
		DBHelper db = new DBHelper(dbName);
		try {
			String entityName = ConvertUtils.lowerFirstName(entityClass.getSimpleName());
			tabName = ConvertUtils.camelToUnderline(entityName);
			StringBuilder sql = new StringBuilder("select * from ");
			sql.append(tabName);
			Object[] params = null;
			if (param != null && !param.isEmpty()) {
				params = new Object[param.size()];
				int i = 0;
				sql.append(" where 1 = 1");
				for (String key : param.keySet()) {
					params[i ++] = param.get(key);
					sql.append(" and ");
					sql.append(ConvertUtils.camelToUnderline(key));
					sql.append(" = ?");
				}
			}
			if (CheckUtils.isNotBlank(sort)) {
				sql.append(" ");
				sql.append("order by ");
				sql.append(SqlFilter.filter(sort));
				sql.append(desc ? " desc" : " asc");
			}
			sql.append(" limit ");
			sql.append((page - 1) * pageSize);
			sql.append(",");
			sql.append(pageSize);
			return db.queryList(entityClass, sql.toString(), params);
		} catch (Exception e) {
			throw new DaoException();
		} finally {
			db.close();
			logger.debug("method {} entity {} 耗时 ：{} milliseconds", this.getClass(), tabName, System.currentTimeMillis() - t1);
		}
	}
}
