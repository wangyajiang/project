package com.tool.mysql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.tool.exception.DaoException;
import com.tool.utils.CheckUtils;
import com.tool.utils.ConvertUtils;
import com.tool.utils.DBHelper;
import com.tool.utils.ReflectUtils;

public class DBUtils {
	
	/**
	 * 单个新增
	 * @param db
	 * @param entity
	 * @throws DaoException
	 */
	public static <T> int insertSingle(DBHelper db, T entity) throws DaoException{
		if (entity == null) {
			throw new DaoException();
		}
		Map<String, Object> param = ReflectUtils.objToMap(entity, "");
		String entityName = ConvertUtils.lowerFirstName(entity.getClass().getSimpleName());
		String tabName = ConvertUtils.camelToUnderline(entityName);
		
		StringBuilder whyStr = new StringBuilder();
		StringBuilder sql = new StringBuilder("insert into ");
		sql.append(tabName);
		sql.append(" (");
		List<String> columns = new ArrayList<String>();
		for (String key : param.keySet()) {
			columns.add(key);
			sql.append(ConvertUtils.camelToUnderline(key));
			sql.append(",");
			whyStr.append("?,");
		}
		sql.delete(sql.length() - 1, sql.length());
		whyStr.delete(whyStr.length() - 1, whyStr.length());
		sql.append(") values (");
		sql.append(whyStr);
		sql.append(")");
		int len = columns.size();
		Object[] arr = new Object[len];
		Class<?> clazz = entity.getClass();
		for (int i = 0; i < len; i ++) {
			arr[i] = ReflectUtils.getObjByColumn(entity, clazz, columns.get(i));
		}
		try {
			return db.executeSingle(sql.toString(), arr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException();
		}
	} 
	
	/**
	 * 批量新增
	 * @param db
	 * @param entityList
	 * @throws DaoException
	 */
	@Autowired
	public static <T> long insertBatch(DBHelper db, List<T> entityList) throws DaoException{
		return insertBatch(db, entityList, null);
	}
	
	@Autowired
	public static <T> long insertBatch(DBHelper db, List<T> entityList, String tabName) throws DaoException{
		if (entityList == null || entityList.isEmpty()) {
			throw new DaoException();
		}
		T entity = entityList.get(0);
		Map<String, Object> param = ReflectUtils.objToMap(entity, "");
		if (CheckUtils.isBlank(tabName)) {
			String entityName = ConvertUtils.lowerFirstName(entity.getClass().getSimpleName());
			tabName = ConvertUtils.camelToUnderline(entityName);
		}
		
		StringBuilder whyStr = new StringBuilder();
		StringBuilder sql = new StringBuilder("insert into ");
		sql.append(tabName);
		sql.append(" (");
		List<String> columns = new ArrayList<String>();
		for (String key : param.keySet()) {
			columns.add(key);
			sql.append(ConvertUtils.camelToUnderline(key));
			sql.append(",");
			whyStr.append("?,");
		}
		sql.delete(sql.length() - 1, sql.length());
		whyStr.delete(whyStr.length() - 1, whyStr.length());
		sql.append(") values (");
		sql.append(whyStr);
		sql.append(")");
		
		List<Object[]> dataList = new ArrayList<Object[]>();
		int len = columns.size();
		for (T item : entityList) {
			Object[] arr = new Object[len];
			Class<?> clazz = item.getClass();
			for (int i = 0; i < len; i ++) {
				arr[i] = ReflectUtils.getObjByColumn(item, clazz, columns.get(i));
			}
			dataList.add(arr);
		}
		try {
			return db.executeBatch(sql.toString(), dataList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException();
		}
	}
	
	
}
