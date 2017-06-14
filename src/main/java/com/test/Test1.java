package com.test;

import com.tool.comm.BaseUtils;
import com.tool.utils.DBHelper;

public class Test1 {
	private DBHelper db = new DBHelper();
	
	/**
	 * 创建数据源表
	 */
	public void createDbSource() {
		try {
			StringBuilder reportSourceSql = new StringBuilder("create table if not exists ");
			reportSourceSql.append("report_db_source(");
			reportSourceSql.append("id INT PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '数据源id',");
			reportSourceSql.append("db_name VARCHAR(50) NOT NULL COMMENT '数据源名称',");
			reportSourceSql.append("db_url VARCHAR(100) NOT NULL COMMENT '数据源url',");
			reportSourceSql.append("db_username VARCHAR(30) COMMENT '账户',");
			reportSourceSql.append("db_password VARCHAR(30) COMMENT '密码',");
			reportSourceSql.append("db_driver VARCHAR(100) COMMENT '数据库驱动',");
			reportSourceSql.append("is_delete int(1) DEFAULT 0 COMMENT '0正常，1删除',");
			reportSourceSql.append("create_time datetime");
			reportSourceSql.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='报表中心-数据源';");
			db.executeSingle(reportSourceSql.toString(), new Object[]{});
			
//			Object[] params = {1, "基本库", "jdbc:mysql://localhost:3306/test?characterEncoding=utf8", "root", "123456", "com.mysql.jdbc.Driver"};
//			db.executeSingle("insert into report_db_source values(?, ?, ?, ?, ?, ?, 0, CURRENT_TIMESTAMP)", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("report_db_source ok");
	}
	
	/**
	 * 创建应用中心表
	 */
	public void createApplicationCenter() {
		try {
			StringBuilder reportSourceSql = new StringBuilder("create table if not exists ");
			reportSourceSql.append("report_application_center(");
			reportSourceSql.append("id INT PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '应用id',");
			reportSourceSql.append("name VARCHAR(50) NOT NULL COMMENT '应用名称',");
			reportSourceSql.append("db_id INT(11) NOT NULL COMMENT '数据源id',");
			reportSourceSql.append("tab_name VARCHAR(50) NOT NULL COMMENT 'sql',");
			reportSourceSql.append("is_del INT(1) DEFAULT 1 COMMENT '是否可删除  0 否，1是',");
			reportSourceSql.append("is_more INT(1) DEFAULT 1 COMMENT '是否多选  0 否，1是',");
			reportSourceSql.append("is_export INT(1) DEFAULT 1 COMMENT '是否有导出  0 否，1是',");
			reportSourceSql.append("sort int(3) COMMENT DEFAULT 0 '排序',");
			reportSourceSql.append("is_delete int(1) DEFAULT 0 COMMENT '0正常，1删除',");
			reportSourceSql.append("create_time datetime");
			reportSourceSql.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='报表中心-应用中心';");
			db.executeSingle(reportSourceSql.toString(), new Object[]{});
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("report_application_center ok");
	}
	
	/**
	 * 创建字段挂载表
	 */
	public void createColumnsMount() {
		try {
			StringBuilder reportSourceSql = new StringBuilder("create table if not exists ");
			reportSourceSql.append("report_Columns_mount(");
			reportSourceSql.append("id INT PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '字段挂载id',");
			reportSourceSql.append("application_center_id INT(11) NOT NULL COMMENT '应用id',");
			reportSourceSql.append("column VARCHAR(50) NOT NULL COMMENT '字段属性名称',");
			reportSourceSql.append("column_type VARCHAR(15) NOT NULL COMMENT '字段类型',");
			reportSourceSql.append("is_search INT(1) DEFAULT 0 COMMENT '是否搜索字段  0 否，1是',");
			reportSourceSql.append("is_list INT(1) DEFAULT 1 COMMENT '是否列表字段  0 否，1是',");
			reportSourceSql.append("is_detial INT(1) DEFAULT 1 COMMENT '是否详情字段  0 否，1是',");
			reportSourceSql.append("is_edit INT(1) DEFAULT 1 COMMENT '是否可编辑字段  0 否，1是',");
			reportSourceSql.append("search_sort int(3) DEFAULT 0 COMMENT '搜索排序',");
			reportSourceSql.append("list_sort int(3) DEFAULT 0 COMMENT '列表排序',");
			reportSourceSql.append("detial_sort int(3) DEFAULT 0 COMMENT '搜索排序',");
			reportSourceSql.append("date_type VARCHAR(20) DEFAULT 0 COMMENT '格式化日期，和column_type配合使用',");
			reportSourceSql.append("num_type VARCHAR(20) DEFAULT 0 COMMENT '保留小数点后几位，和column_type配合使用',");
			reportSourceSql.append("is_delete int(1) DEFAULT 0 COMMENT '0正常，1删除',");
			reportSourceSql.append("create_time datetime DEFAULT CURRENT_TIMESTAMP");
			reportSourceSql.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='报表中心-应用中心';");
			db.executeSingle(reportSourceSql.toString(), new Object[]{});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		BaseUtils.newInstance();
		Test1 t = new Test1();
		t.createDbSource();
		t.createApplicationCenter();
		t.createColumnsMount();
		System.out.println(System.currentTimeMillis() / 1000);
	}
}
