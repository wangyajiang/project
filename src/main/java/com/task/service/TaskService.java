package com.task.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.task.domain.TableRule;
import com.tool.constant.EnumConfig;
import com.tool.utils.ConvertUtils;
import com.tool.utils.DBHelper;


public class TaskService implements Runnable {
	private TableRule tableRule = null;
	
	DBHelper sourceDb = new DBHelper(EnumConfig.DB_NAME.SOURCE);
	DBHelper goldDb = new DBHelper(EnumConfig.DB_NAME.GOLD);
	private final int pageSize = 200;
	
	public TaskService (TableRule tableRule) {
		this.tableRule = tableRule;
	}
	
	public void run() {
		System.out.println(Thread.currentThread().getName() + ": source:" + tableRule.getSourceTableName() + "\tgold:" + tableRule.getGoldTableName());
		try {
			start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sourceDb.close();
			goldDb.close();
			System.out.println(Thread.currentThread().getName() + ": source:" + tableRule.getSourceTableName() + "\tgold:" + tableRule.getGoldTableName() + "\t【OK】");
		}
	}
	
	public void start() throws Exception {
		String sourceTableName = tableRule.getSourceTableName();
		String goldTableName = tableRule.getGoldTableName();
		String[] sourceColumns = null;
		String[] goldColumns = null;
		String sourceColumnsStr = null;
		String goldColumnsStr = null;
		List<Map<String, Object>> sourceTableList = null;
		if ("default".compareTo(tableRule.getSourceColumns()) == 0 || "*".compareTo(tableRule.getSourceColumns()) == 0) {
			String sql = "select column_name,data_type from information_schema.columns where table_name = '" + sourceTableName + "'";
			sourceTableList = sourceDb.queryList(Map.class, sql, null);
			sourceColumns = new String[sourceTableList.size()];
			StringBuilder str = new StringBuilder();
			for (int i = 0; i < sourceTableList.size(); i ++) {
				sourceColumns[i] = ConvertUtils.formatToStr(sourceTableList.get(i).get("COLUMN_NAME"));
				str.append(sourceColumns[i]);
				str.append(",");
			}
			str.delete(str.length() - 1, str.length());
			sourceColumnsStr = str.toString();
		} else {
			sourceColumns = tableRule.getSourceColumns().split(",");
			sourceColumnsStr = tableRule.getSourceColumns();
		}
		if ("default".compareTo(tableRule.getGoldColumns()) == 0 || "*".compareTo(tableRule.getGoldColumns()) == 0) {
			goldColumns = new String[sourceColumns.length];
			StringBuilder str = new StringBuilder();
			for (int i = 0; i < sourceColumns.length; i ++) {
				goldColumns[i] = sourceColumns[i];
				str.append(sourceColumns[i]);
				str.append(",");
			}
			str.delete(str.length() - 1, str.length());
			goldColumnsStr = str.toString();
		} else {
			goldColumns = tableRule.getGoldColumns().split(",");
			goldColumnsStr = tableRule.getGoldColumns();
		}
		int page = 1;
		while (true) {
			int start = (page - 1) * pageSize;
			String sql = "select " + sourceColumnsStr + " from " + sourceTableName + " limit " + start + "," + pageSize;
			List<Map<String, Object>> list = sourceDb.queryList(Map.class, sql , null);
			page ++;
			if (list == null || list.isEmpty()) {
				break;
			}
			StringBuilder insertSql = new StringBuilder("insert into ");//goldTableName + " (" + goldColumnsStr + ") values ()";
			insertSql.append(goldTableName);
			insertSql.append(" (");
			insertSql.append(goldColumnsStr);
			insertSql.append(" ) values (");
			for (int  i = 0; i < goldColumns.length; i ++) {
				insertSql.append("?,");
			}
			insertSql.delete(insertSql.length() - 1, insertSql.length());
			insertSql.append(")");
			List<Object[]> dataList = new ArrayList<Object[]>();
			for (Map<String, Object> param : list) {
				Object[] objArr = new Object[goldColumns.length];
				for (int j = 0; j < sourceColumns.length; j ++) {
					objArr[j] = param.get(sourceColumns[j]);
				}
				dataList.add(objArr);
			}
			goldDb.executeBatch(insertSql.toString(), dataList);
		}
		
	}

}
