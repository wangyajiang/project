package com.tool.utils;
/**
 * auto:wyj
 * 数据库链接
 * 支持多库操作
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tool.comm.BaseUtils;
import com.tool.constant.EnumConfig;
import com.tool.exception.DaoException;


public class DBHelper {
	private static final Logger logger = LoggerFactory.getLogger(DBHelper.class);
	
	private static ConcurrentHashMap<String, BasicDataSource> dataSourceMap = new ConcurrentHashMap<String, BasicDataSource>();
	private static AtomicBoolean isNewInstance = new AtomicBoolean(false); 
	
	private BasicDataSource dataSource = null;
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	
	public static void newInstance() {
		if (isNewInstance.get()) {
			return;
		}
		isNewInstance.set(true);
		ConcurrentHashMap<String, Map<String, String>> dbCache = BaseUtils.getDbCache();
		for (String key : dbCache.keySet()) {
			System.out.println("【" + key + "=" + dbCache.get(key) + "】");
			BasicDataSource dataSource = getDataSource(dbCache.get(key));
			dataSourceMap.put(key, dataSource);
		}
	}
	
	private static BasicDataSource getDataSource(Map<String, String> param) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl(param.get("jdbc.url"));
		dataSource.setUsername(param.get("jdbc.username"));
		dataSource.setPassword(param.get("jdbc.password"));
		dataSource.setDriverClassName(param.get("jdbc.driver"));
		dataSource.setInitialSize(ConvertUtils.getInt(BaseUtils.getVal("pool.initSize"), 1));
		dataSource.setMinIdle(ConvertUtils.getInt(BaseUtils.getVal("pool.minSize"), 1));
		dataSource.setMaxActive(ConvertUtils.getInt(BaseUtils.getVal("pool.maxSize"), 10));
		dataSource.setMaxWait(ConvertUtils.getInt(BaseUtils.getVal("pool.maxWait"), 50000));
		return dataSource;
	}
	
	
	public DBHelper(EnumConfig.DB_NAME dbName) {
		initConnection(dbName);
	}
	
	public DBHelper() {
		initConnection(null);
	}
	
	public DBHelper(String dbName) {
		if (CheckUtils.isNotBlank(dbName)) {
			initConnectionByName(dbName);
		} else {
			System.out.println("库名不能为空！");
		}
	}
	
	public DBHelper(String url, String userName, String password, String driver) {
		String key = AlgorithmUtils.EncoderByMd5(url + "_" + userName + "_" + password);
		try {
			if (dataSourceMap.containsKey(key)) {
				dataSource = dataSourceMap.get(key);
				conn = dataSource.getConnection();
			} else {
				BasicDataSource dataSource = new BasicDataSource();
				dataSource.setUrl(url);
				dataSource.setUsername(userName);
				dataSource.setPassword(password);
				dataSource.setDriverClassName(driver);
				dataSource.setInitialSize(1);
				dataSource.setMinIdle(1);
				dataSource.setMaxActive(10);
				dataSource.setMaxWait(50000);
				dataSourceMap.put(key, dataSource);
				this.dataSource = dataSource;
				conn = dataSource.getConnection();
			}
		} catch (Exception e) {
			System.out.println("连接失败");
			e.printStackTrace();
		}
	}
	
	private void initConnection(EnumConfig.DB_NAME dbName) {
		if (dataSourceMap.isEmpty()) {
			System.out.println("未初始化BaseUtils");
		}
		if (dbName == null) {
			dbName = EnumConfig.DB_NAME.BASE;
		}
		dataSource = dataSourceMap.get(dbName.getCode());
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			logger.error("获取数据库连接失败", e);
			e.printStackTrace();
		} 
	}
	
	private void initConnectionByName(String name) {
		if (dataSourceMap.isEmpty()) {
			System.out.println("未初始化BaseUtils");
		}
		dataSource = dataSourceMap.get(name);
		if (dataSource == null) {
			System.out.println("不存在库名：" + name);
		}
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			logger.error("获取数据库连接失败", e);
			e.printStackTrace();
		} 
	}
	
	public int getNumActive() {
		return dataSource.getNumActive();
	}
	
	public int getMaxActive() {
		return dataSource.getMaxActive();
	}
	
	public int getAbleActive() {
		return getMaxActive() - getNumActive();
	}
	
	public Connection getConnection() {
		return conn;
	}
	
	public PreparedStatement getPreparedStatement() {
		return ps;
	}
	
	public ResultSet getResultSet() {
		return rs;
	}
	
	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (null != conn) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			logger.error("关闭数据库连接异常", e);
		}
	}
	
	public String getSql() {
		String sql =  ps.toString();
		if (sql == null || sql.compareTo("") == 0) {
			return null;
		}
		sql = sql.substring(sql.indexOf(":") + 1).trim();
        return sql;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> queryList(Class clazz, final String sql, final Object[] params) throws Exception {
		List<T> list = new ArrayList<T>();
		ResultSetMetaData rmd = null;
        try {  
            ps = conn.prepareStatement(sql);
            if (params != null) {  
                for (int i = 0; i < params.length; i++) {  
                    ps.setObject(i + 1, params[i]);  
                }  
            }
            logger.debug("able connection number: {} >>>>>>>>>>>>[sql] : {}", (dataSource.getMaxActive() - dataSource.getNumActive()), getSql());
            rs = ps.executeQuery();
            rmd = rs.getMetaData();
            int  len = rmd.getColumnCount();
            boolean isChange = false;
            if ("Map".compareTo(clazz.getSimpleName()) != 0) {
            	isChange = true;
            }
            while (rs.next()) {
            	Map<String, Object> item = new HashMap<String, Object>();
            	for(int i = 1; i <= len; i++) {
            		if (isChange) {
            			item.put(ConvertUtils.underlineToCamel(CheckUtils.formatStr(rmd.getColumnName(i))), rs.getObject(i));
            		} else {
            			item.put(CheckUtils.formatStr(rmd.getColumnName(i)), rs.getObject(i));
            		}
            	}
            	if (!isChange) {
            		list.add((T) item);
            	} else {
            		list.add((T) ReflectUtils.mapToObj(clazz, item));
            	}
            }
        } catch (SQLException e) {
        	e.printStackTrace();
            throw e;
        } finally {
        	rmd = null;
        }
        return list;
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> queryListBySelf(Class clazz, final String sql, final Object[] params, String ... keys) throws Exception {
		List<T> list = new ArrayList<T>();
        try {  
            ps = conn.prepareStatement(sql);
            if (params != null) {  
                for (int i = 0; i < params.length; i++) {  
                    ps.setObject(i + 1, params[i]);  
                }  
            }
            logger.debug("able connection number: {} >>>>>>>>>>>>[sql] : {}", (dataSource.getMaxActive() - dataSource.getNumActive()), getSql());
            rs = ps.executeQuery();
            boolean isChange = false;
            if ("Map".compareTo(clazz.getSimpleName()) != 0) {
            	isChange = true;
            }
            while (rs.next()) {
            	Map<String, Object> item = new HashMap<String, Object>();
            	for(String key : keys) {
            		if (isChange) {
            			item.put(ConvertUtils.underlineToCamel(key), rs.getObject(key));
            		} else {
            			item.put(key, rs.getObject(key));
            		}
            	}
            	if (!isChange) {
            		list.add((T) item);
            	} else {
            		list.add((T) ReflectUtils.mapToObj(clazz, item));
            	}
            }
        } catch (SQLException e) {
        	e.printStackTrace();
            throw e;
        }
        return list;
    }
	
	@SuppressWarnings({"rawtypes" })
	public <T> T querySingle(Class clazz, final String sql, final Object[] params) throws Exception {
		List<T> list = queryList(clazz, sql, params);
		if (null == list || list.isEmpty()) {
			return null;
		}
		if (list.size() > 1) {
			throw new DaoException();
		}
        return list.get(0);
    }
	
	public Long queryCounts(String sql, Object[] params) throws Exception{
    	long count = 0L;
        try {
        	 ps = conn.prepareStatement(sql);
             if (params != null) {  
                 for (int i = 0; i < params.length; i++) {  
                     ps.setObject(i + 1, params[i]);  
                 }  
             }
             logger.debug("able connection number: {} >>>>>>>>>>>>[sql] : {}", (dataSource.getMaxActive() - dataSource.getNumActive()), getSql());
             rs = ps.executeQuery();
             if (rs.next()) {
            	 count = rs.getLong(1);
             }
		} catch (Exception e) {
			throw e;
		}
        return count;
    }
	
	public long executeBatch(final String sql, final List<Object[]> list) throws Exception {  
        int count[] = null;
        long total = 0;
        try {  
        	conn.setAutoCommit(false);
        	ps = conn.prepareStatement(sql);
        	if (null != list && !list.isEmpty()) {
        		for (Object[] params : list) {
        			for (int i = 0; i < params.length; i++) {
                        ps.setObject(i + 1, params[i]);
                    }
        			ps.addBatch();
        		}
        	}
        	logger.debug("able connection number: {} >>>>>>>>>>>>[sql] : {}", (dataSource.getMaxActive() - dataSource.getNumActive()), getSql());
        	count = ps.executeBatch();
        	for (int c : count) {
        		total += c;
        	}
            conn.commit();
            ps.close();
        } catch (SQLException e) {
        	conn.rollback();
        	throw e;
        }
        return total;
    }
	
	public int executeSingle(final String sql, final Object[] params) throws Exception {  
		int count = 0;
        try {  
        	ps = conn.prepareStatement(sql);
        	if (null != params && params.length > 0) {
    			for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
        	}
        	logger.debug("able connection number: {} >>>>>>>>>>>>[sql] : {}", (dataSource.getMaxActive() - dataSource.getNumActive()), getSql());
        	count = ps.executeUpdate();
        } catch (SQLException e) {
        	throw e;
        }
        return count;
    }
	
	public int executeSingle(final String sql, final List<Object> params) throws Exception {  
		int count = 0;
        try {  
        	ps = conn.prepareStatement(sql);
        	if (null != params && params.size() > 0) {
    			for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i + 1, params.get(i));
                }
        	}
        	logger.debug("able connection number: {} >>>>>>>>>>>>[sql] : {}", (dataSource.getMaxActive() - dataSource.getNumActive()), getSql());
        	count = ps.executeUpdate();
        } catch (SQLException e) {
        	throw e;
        }
        return count;
    }
	
}
