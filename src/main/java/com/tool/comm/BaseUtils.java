package com.tool.comm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import com.tool.utils.CheckUtils;
import com.tool.utils.ConvertUtils;
import com.tool.utils.DBHelper;

public class BaseUtils {
	private static AtomicBoolean isNewInstance = new AtomicBoolean(false); 
	private static ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<String, String>();
	private static ConcurrentHashMap<String, Map<String, String>> dbCache = new ConcurrentHashMap<String, Map<String, String>>();
	
	private BaseUtils() {}
	
	public static void newInstance() {
//		String dbpro = BaseUtils.class.getClassLoader().getResource("prop/db_config.properties").getFile();
		String dbpro = BaseUtils.class.getClassLoader().getResource("prop/db_config.props").getFile();
//		String dbpro = "D:/workspace/myapi/resources/prop/db_config.props";
		newInstance(dbpro);
	}
	
	public static void newInstance(String dbpro) {
//		System.out.println(Object.class.getResourceAsStream("/prop/db_config.properties"));
//		BaseUtils.init(Object.class.getResource("/prop/db_config.properties").getFile());
		if (!new File(dbpro).exists()) {
			System.out.println("找不到数据连接配置：" + dbpro);
			return;
		}
		System.out.println("加载配置：" + dbpro);
		BaseUtils.init(dbpro);
		DBHelper.newInstance();
	}
	
	private synchronized static void init(String path) {
		if (isNewInstance.get()) {
			return;
		}
		isNewInstance.set(true);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf8"));
			String str = null;
			boolean isDb = false;
			int num = 0;
			String key = null;
			boolean isNormal = true;
			while ((str = br.readLine()) != null) {
				if (CheckUtils.isBlank(str)) {
					isDb = false;
					num = 0;
					continue;
				}
				if (str.indexOf("#") >= 0) {
					str = str.substring(0, str.indexOf("#"));
				}
				if (CheckUtils.isBlank(str)) {
					continue;
				}
				isNormal = true;
				if (isDb) {
					isNormal = false;
					Map<String, String> map = dbCache.get(key);
					String[] kv = str.split("=");
					map.put(kv[0].trim(), kv.length < 2 ? "" : ConvertUtils.formatStr(kv[1]).trim());
					num ++;
				}
				if (num == 4) {
					isDb = false;
					num = 0;
				}
				if (str.indexOf("[") == 0 && str.indexOf("]") > 0) {
					isDb = true;
					num = 0;
					key = str.substring(1, str.indexOf("]"));
					Map<String, String> map = new HashMap<String, String>();
					dbCache.put(key, map);
					isNormal = false;
				}
				if (isNormal) {
					String[] kv = str.split("=");
					cache.put(kv[0].trim(), kv.length < 2 ? "" : ConvertUtils.formatStr(kv[1]).trim());
				}
			}
		} catch (IOException e) {  
            e.printStackTrace();  
        }
	}
	
	public static Map<String, String> getDbMap(String key) {
		return dbCache.get(key);
	}
	
	public static ConcurrentHashMap<String, Map<String, String>> getDbCache() {
		return dbCache;
	}
	
	public static String getVal(String key) {
		return cache.get(key);
	}
	
	public static ConcurrentHashMap<String, String> getProps() {
		return cache;
	}
	
	public static void main(String[] args) {
		newInstance();
		System.out.println(dbCache.size());
		
	}

}
