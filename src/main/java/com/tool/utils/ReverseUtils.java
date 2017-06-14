package com.tool.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tool.comm.BaseUtils;
import com.tool.constant.EnumConfig;

public class ReverseUtils {

	public static boolean isCover = false;//true 覆盖;false 不覆盖
//	private static final String entityPackage = "D:/workspace/pro/src/main/java/com/pro/entity/";
//	private static final String daoPackage = "D:/workspace/pro/src/main/java/com/pro/dao/";
//	private static final String servicePackage = "D:/workspace/pro/src/main/java/com/pro/service/";
//	private static final String springDaoXmlPath = "D:/workspace/pro/src/main/resources/spring-applicationcontext/spring-dao.xml";
//	private static final String springServiceXmlPath = "D:/workspace/pro/src/main/resources/spring-applicationcontext/spring-service.xml";
	private static final String entityPackage = "D:/workspace/panicbuy/src/main/java/com/pro/entity/";
	private static final String daoPackage = "D:/workspace/panicbuy/src/main/java/com/pro/dao/";
	private static final String servicePackage = "D:/workspace/panicbuy/src/main/java/com/pro/service/";
	private static final String springDaoXmlPath = "D:/workspace/panicbuy/src/main/resources/spring-applicationcontext/spring-dao.xml";
	private static final String springServiceXmlPath = "D:/workspace/panicbuy/src/main/resources/spring-applicationcontext/spring-service.xml";
	
	private static Map<String, String> columnsMap = new HashMap<String, String>();
	public static DBHelper DB = null;
	
	public static void main(String[] args) throws Exception {
		dbInit();
		String[] tables = {"focus_map", "items", "items_pics", "item_type", "items_active", "items_active_pics", "active_order", "active_bill", "user", "user_point", "user_point_log"};
		for (int i = 0; i < tables.length; i ++) {
			DB = new DBHelper(EnumConfig.DB_NAME.PANICBUY);
			String tableName = tables[i];//数据库表名称
			System.out.println("等待【" + tableName + "】执行······");
			Thread.sleep(1000);
			createEntity(tableName);
			createIdao(tableName);//创建IDao
			createdao(tableName);//创建Dao
			reloadDaoXml(tableName);
			createIService(tableName);//创建IService
			createService(tableName);//创建Service
			reloadServiceXml(tableName);
			DB.close();
		}
	}
	
	public static void reloadDaoXml(String tableName) {
		try {
			String name = upcaseFirstName(formatName(tableName));
			String url =  daoPackage + "impl/";
			String daoName = name + "DaoImpl";
			String packageName = url.substring(url.indexOf("com")).replaceAll("/", ".");
			packageName = packageName.substring(0, packageName.length() - 1);
			String id = ConvertUtils.lowerFirstName(name) + "Dao";
			String clazz = packageName + "." + daoName;
			String rt = "\t<bean id=\"" + id + "\" class=\"" + clazz + "\"></bean>";
			boolean flag = false;
			BufferedReader br = FileUtils.getBufferedReader(springDaoXmlPath, "UTF-8");
			List<String> list = new ArrayList<String>();
			String str = null;
			while ((str = br.readLine()) != null) {
				if (str.indexOf("\"" + id + "\"") > 0) {
					flag = true;
				}
				if (str.indexOf("</beans>") >= 0) {
					list.add(rt);
					list.add(str);
				} else {
					list.add(str);
				}
			}
			FileUtils.close(br);
			if (flag) {
				return;
			}
			BufferedWriter bw = FileUtils.getBufferedWriter(springDaoXmlPath, "UTF-8");
			for (String line : list) {
				FileUtils.writeLine(bw, line);
			}
			FileUtils.close(bw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void reloadServiceXml(String tableName) {
		try {
			String name = upcaseFirstName(formatName(tableName));
			String url =  servicePackage + "impl/";
			String daoName = name + "ServiceImpl";
			String packageName = url.substring(url.indexOf("com")).replaceAll("/", ".");
			packageName = packageName.substring(0, packageName.length() - 1);
			String id = ConvertUtils.lowerFirstName(name) + "Service";
			String clazz = packageName + "." + daoName;
			String rt = "\t<bean id=\"" + id + "\" class=\"" + clazz + "\"></bean>";
			boolean flag = false;
			BufferedReader br = FileUtils.getBufferedReader(springServiceXmlPath, "UTF-8");
			List<String> list = new ArrayList<String>();
			String str = null;
			while ((str = br.readLine()) != null) {
				if (str.indexOf("\"" + id + "\"") > 0) {
					flag = true;
				}
				if (str.indexOf("</beans>") >= 0) {
					list.add(rt);
					list.add(str);
				} else {
					list.add(str);
				}
			}
			FileUtils.close(br);
			if (flag) {
				return;
			}
			BufferedWriter bw = FileUtils.getBufferedWriter(springServiceXmlPath, "UTF-8");
			for (String line : list) {
				FileUtils.writeLine(bw, line);
			}
			FileUtils.close(bw);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void dbInit() {
		BaseUtils.newInstance();
	}
	
	private static String formatName(String name) {
		name = name.toLowerCase();
		StringBuffer str = new StringBuffer();
		if (name.indexOf("_") >= 0) {
			String[] arr = name.split("_");
			for (int i = 0; i < arr.length; i ++) {
				if (i == 0) {
					str.append(arr[i]);
				} else {
					if (CheckUtils.isNotBlank(arr[i])) {
						byte[] items = arr[i].getBytes();
						items[0] = (byte) ((char) items[0] - 'a' + 'A');
						str.append(new String(items).toString());
					}
				}
			}
		} else {
			str.append(name);
		}
		return str.toString();
	};
	private static String upcaseFirstName(String name) {
		String str = String.valueOf(name.charAt(0)).toUpperCase();
		if (name.length() > 1) {
			str += name.substring(1);
		}
		return str;
	}
	public static String getDataImportant(int type, int scale){
        String dataType = null;
        switch(type){
	        case Types.FLOAT:
	        	 dataType = "import java.math.BigDecimal;";
	   		 	break;
	    	case Types.REAL:
	    		 dataType = "import java.math.BigDecimal;";
	   		 	break;
	    	case Types.DOUBLE:
	    		 dataType = "import java.math.BigDecimal;";
	   		 	break;
	    	case Types.DECIMAL:
	    		 dataType = "import java.math.BigDecimal;";
       		 	break;
            case Types.NUMERIC: //2
                switch(scale){
                    case 0:
                        break;
                    case -127:
                        dataType = "import java.math.BigDecimal;";
                        break;
                }
                break;
            case Types.TIME:  //91
            	dataType = "import java.util.Date;";
                break;
            case Types.VARCHAR:  //12
                break;
            case Types.DATE:  //91
            	dataType = "import java.util.Date;";
                break;
            case Types.TIMESTAMP: //93
                dataType = "import java.util.Date;";
                break;
            case Types.BLOB :
                break;
        }
        return dataType;
    }
	 public static String getMySqlType(int type, int scale){
        String dataType = "";
        switch(type){
        	case Types.TINYINT:
        		 dataType = "INTEGER";
        		 break;
        	case Types.SMALLINT:
       		 	dataType = "INTEGER";
       		 	break;
        	case Types.INTEGER:
       		 	dataType = "INTEGER";
       		 	break;
        	case Types.BIGINT:
       		 	dataType = "LONG";
       		 	break;
        	case Types.FLOAT:
       		 	dataType = "FLOAT";
       		 	break;
        	case Types.REAL:
       		 	dataType = "REAL";
       		 	break;
        	case Types.DOUBLE:
       		 	dataType = "DOUBLE";
       		 	break;
        	case Types.NUMERIC: //2
                switch(scale){
                    case 0:
                        dataType = "INTEGER";
                        break;
                    case -127:
                        dataType = "DECIMAL";
                        break;
                    default:
                        dataType = "INTEGER";
                }
                break;
        	case Types.DECIMAL:
       		 	dataType = "DECIMAL";
       		 	break;
        	case Types.CHAR:
       		 	dataType = "CHAR";
       		 	break;
        	 case Types.VARCHAR:    //1
                 dataType = "VARCHAR";
                 break;
            case Types.LONGVARCHAR: //-1
                dataType = "LONGVARCHAR";
                break;
            case Types.DATE:  //91
                dataType = "DATE";
                break;
            case Types.TIME:  //91
                dataType = "DATE";
                break;
            case Types.TIMESTAMP: //93
                dataType = "DATE";
                break;
            case Types.BINARY:  //12
                dataType = "BINARY";
                break;
            case Types.VARBINARY:  //12
                dataType = "VARBINARY";
                break;
            case Types.LONGVARBINARY:  //12
                dataType = "LONGVARBINARY";
                break;
            case Types.BLOB :
                dataType = "BLOB";
                break;
            default:
                dataType = "VARCHAR";
        }
        return dataType;
    }
	public static String getDataType(int type, int scale){
        String dataType = "";
        switch(type){
        	case Types.TINYINT:
        		 dataType = "Integer";
        		 break;
        	case Types.SMALLINT:
       		 	dataType = "Integer";
       		 	break;
        	case Types.INTEGER:
       		 	dataType = "Integer";
       		 	break;
        	case Types.BIGINT:
       		 	dataType = "Long";
       		 	break;
        	case Types.FLOAT:
       		 	dataType = "BigDecimal";
       		 	break;
        	case Types.REAL:
       		 	dataType = "BigDecimal";
       		 	break;
        	case Types.DOUBLE:
       		 	dataType = "BigDecimal";
       		 	break;
        	case Types.NUMERIC: //2
                switch(scale){
                    case 0:
                        dataType = "Integer";
                        break;
                    case -127:
                        dataType = "BigDecimal";
                        break;
                    default:
                        dataType = "Integer";
                }
                break;
        	case Types.DECIMAL:
       		 	dataType = "BigDecimal";
       		 	break;
        	case Types.CHAR:
       		 	dataType = "String";
       		 	break;
        	 case Types.VARCHAR:    //1
                 dataType = "String";
                 break;
            case Types.LONGVARCHAR: //-1
                dataType = "String";
                break;
            case Types.DATE:  //91
                dataType = "Date";
                break;
            case Types.TIME:  //91
                dataType = "Date";
                break;
            case Types.TIMESTAMP: //93
                dataType = "Date";
                break;
            case Types.BINARY:  //12
                dataType = "String";
                break;
            case Types.VARBINARY:  //12
                dataType = "String";
                break;
            case Types.LONGVARBINARY:  //12
                dataType = "String";
                break;
            case Types.BLOB :
                dataType = "String";
                break;
            default:
                dataType = "String";
        }
        return dataType;
    }
	public static void createEntity(String tableName) throws Exception {
		String entityName = "";
		
		List<Map<String, String>> list = DB.queryList(Map.class, "show full fields from " + tableName, null);
		String url =  entityPackage;
		String packageName = url.substring(url.indexOf("com")).replaceAll("/", ".");
		packageName = "package " + packageName.substring(0, packageName.length() - 1) + ";";
		entityName = upcaseFirstName(formatName(tableName));
		String path = url + entityName + ".java";
		File file = new File(path);
		if (isCover || !file.exists()) {
			try {
		        BufferedWriter bw = FileUtils.getBufferedWriter(path);
		        Map<String, Table> tabMap = new HashMap<String, Table>();
		        Map<String, String> typeMap = new HashMap<String, String>();
		        List<String> columnList = new ArrayList<String>();
		        StringBuilder columns = new StringBuilder();
		        for (Map<String, String> param : list) {
		        	Table tab = new Table();
		        	tab.setColumn(param.get("COLUMN_NAME"));
		        	tab.setField(formatName(param.get("COLUMN_NAME")));
		        	tab.setType(param.get("COLUMN_TYPE"));
		        	tab.setComment(ConvertUtils.formatStr(param.get("COLUMN_COMMENT")));
		        	if (CheckUtils.isNotBlank(param.get("COLUMN_KEY")) && "pri".compareToIgnoreCase(param.get("COLUMN_KEY")) == 0) {
		        		tab.setComment("主键；" + tab.getComment());
		        		tab.setPri(true);
		        	}
		        	tabMap.put(tab.getField(), tab);
		        	columns.append(tab.getField());
		        	columns.append(",");
		        }
		        columns.delete(columns.length() - 1, columns.length());
		        columnsMap.put(tableName, columns.toString());
		        Connection conn = DB.getConnection();
		        PreparedStatement ps = DB.getPreparedStatement();
		        ResultSet rs = DB.getResultSet();
			    ps = conn.prepareStatement("SELECT * FROM " + tableName + " limit 1");
			    rs = ps.executeQuery();
			    ResultSetMetaData rsmd = rs.getMetaData();
			    int len = rsmd.getColumnCount();
			    file.createNewFile();
			    FileUtils.writeLine(bw, packageName);
			    bw.newLine();
			    FileUtils.writeLine(bw, "import java.io.Serializable;");
			    Map<String, Boolean> param = new HashMap<String, Boolean>();
			    for (int i = 1; i <= len; i ++) {
			    	String str = getDataImportant(rsmd.getColumnType(i), rsmd.getScale(i));
			    	if (CheckUtils.isNotBlank(str) && ! param.containsKey(str)) {
			    		FileUtils.writeLine(bw, str);
			    		param.put(str, true);
			    	}
			    }
			    bw.newLine();
			    FileUtils.writeLine(bw, "public class " + entityName + " implements Serializable {");
			    FileUtils.writeLine(bw, "\tprivate static final long serialVersionUID = 1L;");
			    bw.newLine();
			    FileUtils.writeLine(bw, "\t//" + columns.toString());
			    bw.newLine();
			    for (int i = 1; i <= len; i ++) {
			    	String column = formatName(rsmd.getColumnName(i));
			    	columnList.add(column);
			    	typeMap.put(column,getMySqlType(rsmd.getColumnType(i), rsmd.getScale(i)));
			    	String line = "\tprivate ";
			    	line += getDataType(rsmd.getColumnType(i), rsmd.getScale(i)) + " ";
			    	line += column + ";";
			    	if (CheckUtils.isNotBlank(tabMap.get(column).getComment())) {
			    		line += "//" + tabMap.get(column).getComment();
			    	}
			    	FileUtils.writeLine(bw, line);
			    }
			    bw.newLine();
			    for (int i = 1; i <= len; i ++) {
			    	String columneName = formatName(rsmd.getColumnName(i));
			    	String type = getDataType(rsmd.getColumnType(i), rsmd.getScale(i));
			    	FileUtils.writeLine(bw, "\tpublic " + type + " get" + upcaseFirstName(columneName) + "() {");
			    	FileUtils.writeLine(bw, "\t\treturn " + columneName + ";");
			    	FileUtils.writeLine(bw, "\t}");
			    	FileUtils.writeLine(bw, "\tpublic void set" + upcaseFirstName(columneName) + "(" + type + " " + columneName + ") {");
			    	FileUtils.writeLine(bw, "\t\tthis." + columneName + " = " + columneName + ";");
			    	FileUtils.writeLine(bw, "\t}");
			    }
			    
			    String[] toStrArr = columns.toString().split(",");
			    FileUtils.writeLine(bw, "\tpublic String toString() {");
			    FileUtils.writeLine(bw, "\t\tStringBuilder sb = new StringBuilder();");
			    FileUtils.writeLine(bw, "\t\tsb.append(\"{\");");
			    for (int i = 0; i < toStrArr.length; i ++) {
			    	String col = toStrArr[i];
			    	String str = "sb.append(\"" + col + " : \");sb.append(" + col + ");";
			    	if (i < toStrArr.length - 1) {
			    		str += "sb.append(\", \");";
			    	}
			    	FileUtils.writeLine(bw, "\t\t" + str);
			    }
			    FileUtils.writeLine(bw, "\t\tsb.append(\"}\");");
			    FileUtils.writeLine(bw, "\t\t return sb.toString();");
			    FileUtils.writeLine(bw, "\t}");
			   
			    
			    FileUtils.writeLine(bw, "}");
			    FileUtils.close(bw);
			    System.out.println(file.getName() + "\t完成");
			} catch (Exception e) {
				e.printStackTrace();
				return;
			} finally {
				DB.close();
			}
		} else {
			BufferedReader br = FileUtils.getBufferedReader(path, "utf8");
			String str = null;
			while ((str = br.readLine()) != null) {
				if (CheckUtils.isBlank(str)) {
					continue;
				}
				if (str.split(",").length > 1) {
					columnsMap.put(tableName, str.replaceAll("/", "").trim());
				}
				break;
			}
			FileUtils.close(br);
			System.out.println(file.getName() + "\t已经存在");
		}
//		String name = entityName;
//		createIdao(name);//创建IDao
//		createdao(name);//创建Dao
//		createIService(name);//创建IService
//		createService(name);//创建Service
//		System.out.println("======Dao=======");
//		url =  daoPackage + "impl/";
//		packageName = url.substring(url.indexOf("com")).replaceAll("/", ".");
//		packageName = packageName.substring(0, packageName.length() - 1) + "." + name + "DaoImpl";
//		System.out.println("<bean id=\"" + getMethodName(name) + "Dao\" class=\"" + packageName + "\">");
//		System.out.println("\t<property name=\"sqlSessionFactory\" ref=\"sqlSessionFactory\" />");
//		System.out.println("</bean>");
//		System.out.println("======Service=======");
//		url = servicePackage + "impl/";
//		String packageNames = url.substring(url.indexOf("com")).replaceAll("/", ".");
//		packageNames = packageNames.substring(0, packageNames.length() - 1) + "." + name + "ServiceImpl";
//		System.out.println("<bean id=\"" + getMethodName(name) +"Service\" class=\"" + packageNames + "\"></bean>");
	};
	
	private static void createIdao(String tableName) {
		String name = upcaseFirstName(formatName(tableName));
		String IDaoName = "I" + name + "Dao";
		String path = daoPackage + IDaoName + ".java";
		String packageName = daoPackage.substring(daoPackage.indexOf("com")).replaceAll("/", ".");
		packageName = "package " + packageName.substring(0, packageName.length() - 1) + ";";
		File file = new File(path);
		if (!isCover && file.exists()) {
			System.out.println(file.getName() + "\t已经存在");
			return;
		}
		String entityPackageName = entityPackage.substring(entityPackage.indexOf("com")).replaceAll("/", ".");
		try {
			file.createNewFile();
	        BufferedWriter bw = FileUtils.getBufferedWriter(path);
	        FileUtils.writeLine(bw, packageName);
	        bw.newLine();
	        FileUtils.writeLine(bw, "import java.util.List;");
	        FileUtils.writeLine(bw, "import java.util.Map;");
	        FileUtils.writeLine(bw, "import com.tool.exception.DaoException;");
	        FileUtils.writeLine(bw, "import " + entityPackageName + name + ";");
	        bw.newLine();
	        FileUtils.writeLine(bw, "public interface " + IDaoName + " {");
	        bw.newLine();
	        FileUtils.writeLine(bw, "\tvoid insert(" + name + " entity) throws DaoException;");
	        bw.newLine();
	        FileUtils.writeLine(bw, "\tvoid delete(Integer id) throws DaoException;");
	        bw.newLine();
	        FileUtils.writeLine(bw, "\tvoid delete(List<Integer> ids) throws DaoException;");
	        bw.newLine();
	        FileUtils.writeLine(bw, "\tvoid update(" + name + " entity) throws DaoException;");
	        bw.newLine();
	        FileUtils.writeLine(bw, "\t" + name + " queryById(Integer id) throws DaoException;");
	        bw.newLine();
	        FileUtils.writeLine(bw, "\tList<" + name + "> queryAll() throws DaoException;");
	        bw.newLine();
	        FileUtils.writeLine(bw, "\tList<" + name + "> queryByConditions(Map<String, Object> param) throws DaoException;");
	        bw.newLine();
	        FileUtils.writeLine(bw, "\tlong queryCount(Map<String, Object> param) throws DaoException;");
	        bw.newLine();
	        FileUtils.writeLine(bw, "\tList<" + name + "> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException;");
	        bw.newLine();
	        FileUtils.writeLine(bw, "}");
	        bw.flush();
	        bw.close();
	        System.out.println(file.getAbsolutePath() + "\t完成");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void createdao(String tableName) {
		String name = upcaseFirstName(formatName(tableName));
		String url =  daoPackage + "impl/";
		String daoName = name + "DaoImpl";
		String path = url + daoName + ".java";
		String packageName = url.substring(url.indexOf("com")).replaceAll("/", ".");
		packageName = "package " + packageName.substring(0, packageName.length() - 1) + ";";
		String entityPackageName = entityPackage.substring(entityPackage.indexOf("com")).replaceAll("/", ".");
		String IDaoPackageName = daoPackage.substring(daoPackage.indexOf("com")).replaceAll("/", ".") + "I" + name + "Dao";
		File file = new File(path);
		System.out.println();
		if (!new File(file.getParent()).exists()) {
			new File(file.getParent()).mkdirs();
		}
		if (!isCover && file.exists()) {
			System.out.println(file.getName() + "\t已经存在");
			return;
		}
		try {
			file.createNewFile();
	        BufferedWriter bw = FileUtils.getBufferedWriter(path);
	        FileUtils.writeLine(bw, packageName);
	        bw.newLine();
	        FileUtils.writeLine(bw, "import java.util.List;");
	        FileUtils.writeLine(bw, "import java.util.Map;");
	        bw.newLine();
	        FileUtils.writeLine(bw, "import " + IDaoPackageName + ";");
	        FileUtils.writeLine(bw, "import " + entityPackageName + name + ";");
	        FileUtils.writeLine(bw, "import com.tool.exception.DaoException;");
	        FileUtils.writeLine(bw, "import com.tool.mysql.BaseDao;");
	        bw.newLine();
	        FileUtils.writeLine(bw, "public class " + daoName + " extends BaseDao<" + name + ", Integer> implements I" + name + "Dao {");
	        bw.newLine();
	        
	        FileUtils.writeLine(bw, "\tpublic void insert(" + name + " entity) throws DaoException {");
	        FileUtils.writeLine(bw, "\t\tinsertEntity(null, entity);");
	        FileUtils.writeLine(bw, "\t}");
	        bw.newLine();
	       
	        FileUtils.writeLine(bw, "\tpublic void delete(Integer id) throws DaoException {");
	        FileUtils.writeLine(bw, "\t\tdeleteEntityById(null, id);");
	        FileUtils.writeLine(bw, "\t}");
	        bw.newLine();
	        
	        FileUtils.writeLine(bw, "\tpublic void delete(List<Integer> ids) throws DaoException {");
	        FileUtils.writeLine(bw, "\t\tdeleteEntityByIds(null, ids);");
	        FileUtils.writeLine(bw, "\t}");
	        bw.newLine();
	        
	        FileUtils.writeLine(bw, "\tpublic void update(" + name + "  entity) throws DaoException {");
	        FileUtils.writeLine(bw, "\t\tupdateEntity(null, entity);");
	        FileUtils.writeLine(bw, "\t}");
	        bw.newLine();
	        
	        FileUtils.writeLine(bw, "\tpublic " + name + "  queryById(Integer id) throws DaoException {");
	        FileUtils.writeLine(bw, "\t\treturn queryEntityById(null, id);");
	        FileUtils.writeLine(bw, "\t}");
	        bw.newLine();
	        
	        FileUtils.writeLine(bw, "\tpublic List<" + name + "> queryAll() throws DaoException {");
	        FileUtils.writeLine(bw, "\t\treturn queryEntityAll(null);");
	        FileUtils.writeLine(bw, "\t}");
	        bw.newLine();
	        
	        FileUtils.writeLine(bw, "\tpublic List<" + name + "> queryByConditions(Map<String, Object> param) throws DaoException {");
	        FileUtils.writeLine(bw, "\t\treturn queryEntityByConditions(null, param);");
	        FileUtils.writeLine(bw, "\t}");
	        bw.newLine();
	        
	        
	        FileUtils.writeLine(bw, "\tpublic long queryCount(Map<String, Object> param) throws DaoException {");
	        FileUtils.writeLine(bw, "\t\treturn queryEntityCount(null, param);");
	        FileUtils.writeLine(bw, "\t}");
	        bw.newLine();
	        
	        FileUtils.writeLine(bw, "\tpublic List<" + name + "> queryList(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws DaoException {");
	        FileUtils.writeLine(bw, "\t\treturn queryEntityList(null, param, page, pageSize, sort, desc);");
	        FileUtils.writeLine(bw, "\t}");
	        bw.newLine();
	        
	        FileUtils.writeLine(bw, "}");
	        FileUtils.close(bw);
	        System.out.println(file.getAbsolutePath() + "\t完成");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void createIService(String tableName) {
		String name = upcaseFirstName(formatName(tableName));
		String IName = "I" + name + "Service";
		String path = servicePackage + IName + ".java";
		String packageName = servicePackage.substring(servicePackage.indexOf("com")).replaceAll("/", ".");
		packageName = "package " + packageName.substring(0, packageName.length() - 1) + ";";
		File file = new File(path);
		if (!isCover && file.exists()) {
			System.out.println(file.getName() + "\t已经存在");
			return;
		}
		String entityPackageName = entityPackage.substring(entityPackage.indexOf("com")).replaceAll("/", ".");
		try {
			file.createNewFile();
	        BufferedWriter bw = FileUtils.getBufferedWriter(path);
	        FileUtils.writeLine(bw, packageName);
	        bw.newLine();
	      
	        FileUtils.writeLine(bw, "import java.util.List;");
	        FileUtils.writeLine(bw, "import java.util.Map;");
	        bw.newLine();
	        FileUtils.writeLine(bw, "import " + entityPackageName + name + ";");
	        FileUtils.writeLine(bw, "import com.tool.bean.Paging;");
	        FileUtils.writeLine(bw, "import com.tool.exception.ServiceException;");
	        bw.newLine();
	        FileUtils.writeLine(bw, "public interface " + IName + " {");
	        bw.newLine();
	        FileUtils.writeLine(bw, "\tList<" + name + "> query" + name + "ListByConditions(Map<String, Object> param) throws ServiceException;");
	        bw.newLine();
	        FileUtils.writeLine(bw, "\tPaging<" + name + "> query" + name + "Paging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException;");
	        bw.newLine();
	        FileUtils.writeLine(bw, "}");
	        bw.flush();
	        bw.close();
	        System.out.println(file.getAbsolutePath() + "\t完成");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void createService(String tableName) {
		String name = upcaseFirstName(formatName(tableName));
		String IName = servicePackage.substring(servicePackage.indexOf("com")).replaceAll("/", ".") + "I" + name + "Service";
		String url =  servicePackage + "impl/";
		String serviceName = name + "ServiceImpl";
		String path = url + serviceName + ".java";
		String packageName = url.substring(url.indexOf("com")).replaceAll("/", ".");
		packageName = "package " + packageName.substring(0, packageName.length() - 1) + ";";
		
		String IDaoName = daoPackage.substring(daoPackage.indexOf("com")).replaceAll("/", ".") + "I" + name + "Dao";
		File file = new File(path);
		if (!isCover && file.exists()) {
			System.out.println(file.getName() + "\t已经存在");
			return;
		}
		String entityPackageName = entityPackage.substring(entityPackage.indexOf("com")).replaceAll("/", ".");
		try {
			file.createNewFile();
	        BufferedWriter bw = FileUtils.getBufferedWriter(path);
	        FileUtils.writeLine(bw, packageName);
	        bw.newLine();
	        FileUtils.writeLine(bw, "import java.util.List;");
	        FileUtils.writeLine(bw, " import java.util.HashMap;");
	        FileUtils.writeLine(bw, "import java.util.Map;");
	        bw.newLine();
	        FileUtils.writeLine(bw, "import org.springframework.beans.factory.annotation.Autowired;");
	        bw.newLine();
	        FileUtils.writeLine(bw, "import " + IName + ";");
	        FileUtils.writeLine(bw, "import " + IDaoName + ";");
	        FileUtils.writeLine(bw, "import " + entityPackageName + name + ";");
	        FileUtils.writeLine(bw, "import com.tool.bean.Paging;");
	        FileUtils.writeLine(bw, "import com.tool.exception.ServiceException;");
	        bw.newLine();
	        FileUtils.writeLine(bw, "public class " + name + "ServiceImpl implements I" + name + "Service {");
	        bw.newLine();
	    	
	        String daoName = ConvertUtils.lowerFirstName(name) + "Dao";
	        FileUtils.writeLine(bw, "\t@Autowired");
	        FileUtils.writeLine(bw, "\tprivate I" + name + "Dao " + daoName + ";");
	        bw.newLine();
	        
	        FileUtils.writeLine(bw, "\tpublic List<" + name + "> query" + name + "ListByConditions(Map<String, Object> param) throws ServiceException {");
	        FileUtils.writeLine(bw, "\t\tif (param == null) {");
	        FileUtils.writeLine(bw, "\t\t\tparam = new HashMap<String, Object>();");
	        FileUtils.writeLine(bw, "\t\t}");
	        FileUtils.writeLine(bw, "\t\treturn " + daoName + ".queryByConditions(param);");
	        FileUtils.writeLine(bw, "\t}");
	        bw.newLine();
	        
	        FileUtils.writeLine(bw, "\tpublic Paging<" + name + "> query" + name + "Paging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException {");
	        FileUtils.writeLine(bw, "\t\tif (param == null) {");
	        FileUtils.writeLine(bw, "\t\t\tparam = new HashMap<String, Object>();");
	        FileUtils.writeLine(bw, "\t\t}");
	        FileUtils.writeLine(bw, "\t\tPaging<" + name + "> paging = new Paging<" + name + ">();");
	        FileUtils.writeLine(bw, "\t\tpaging.setPage(page);");
	        FileUtils.writeLine(bw, "\t\tpaging.setPageSize(pageSize);");
	        FileUtils.writeLine(bw, "\t\tpaging.setTota(" + daoName + ".queryCount(param));");
	        FileUtils.writeLine(bw, "\t\tif (paging.getTotal() > 0L) {");
	        FileUtils.writeLine(bw, "\t\t\tpaging.setList(" + daoName + ".queryList(param, page, pageSize, sort, desc));");
	        FileUtils.writeLine(bw, "\t\t}");
	        FileUtils.writeLine(bw, "\t\treturn paging;");
	        FileUtils.writeLine(bw, "\t}");
	        bw.newLine();
	        
	        
	        FileUtils.writeLine(bw, "}");
	        bw.flush();
	        bw.close();
	        System.out.println(file.getAbsolutePath() + "\t完成");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class Table {
	private String field;
	private String column;
	private String type;
	private boolean isPri = false;
	private String comment;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isPri() {
		return isPri;
	}
	public void setPri(boolean isPri) {
		this.isPri = isPri;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}