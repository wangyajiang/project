package com.tool.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

/**
* 转换工具类
* @author wyj
*/

public class ConvertUtils {
	private static final char UNDERLINE='_';
	static ObjectMapper om = new ObjectMapper();
	
	//转汉子数字
	public static String strNumToChina(String str) {
		String[] numberArr = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "点"};
		StringBuilder result = new StringBuilder();
		if (CheckUtils.isBlank(str)) {
			return null;
		}
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '.') {
				result.append(numberArr[11]);
			} else {
				result.append(numberArr[Integer.parseInt(String.valueOf(c))]);
			}
			
		}
		return result.toString();
	}
	
	 /**
	  * 驼峰命名转换
	  * time_zone_leap_second => timeZoneLeapSecond
	  */
	 public static String underlineToCamel(String tabName){  
       if (CheckUtils.isBlank(tabName)){  
           return "";  
       }  
       int len = tabName.length();  
       StringBuilder sb = new StringBuilder(len);  
       for (int i = 0; i < len; i++) {  
           char c = tabName.charAt(i);  
           if (c == UNDERLINE){  
              if (++i<len){  
                  sb.append(Character.toUpperCase(tabName.charAt(i)));  
              }  
           }else{  
               sb.append(c);  
           }  
       }  
       return sb.toString();  
     }
	 
	 /**
	  * 驼峰命名转换
	  * timeZoneLeapSecond => time_zone_leap_second
	  */
	 public static String camelToUnderline(String tabName){  
		if (CheckUtils.isBlank(tabName)){  
	       return "";  
	    }    
        int len = tabName.length();  
        StringBuilder sb = new StringBuilder(len);  
        for (int i = 0; i < len; i++) {  
           char c = tabName.charAt(i);  
           if (Character.isUpperCase(c)){  
               sb.append(UNDERLINE);  
               sb.append(Character.toLowerCase(c));  
           }else{  
               sb.append(c);  
           }  
        }  
        return sb.toString();  
    }  
	
	public static String upcaseFirstName(String name) {
		String str = String.valueOf(name.charAt(0)).toUpperCase();
		if (name.length() > 1) {
			str += name.substring(1);
		}
		return str;
	}
	
	public static String lowerFirstName(String name) {
		String str = String.valueOf(name.charAt(0)).toLowerCase();
		if (name.length() > 1) {
			str += name.substring(1);
		}
		return str;
	}
	
	public static String object2json(Object obj) {
		try {
			return om.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T json2object(String json, Class<T> c) {
		try {
			return om.readValue(json, c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static <K, V> Map<K, V> json2map(String json, Class<K> kc, Class<V> vc) {
		JavaType javaType = om.getTypeFactory().constructParametricType(HashMap.class, new Class[] { kc, vc });
		try {
			return om.readValue(json, javaType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> List<T> json2list(String json, Class<T> c) {
		JavaType javaType = om.getTypeFactory().constructParametricType(ArrayList.class, c);
		try {
			return om.readValue(json, javaType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//数字转汉子数字
	public static String numToChina(int num) {
		return strNumToChina(String.valueOf(num));
	}
	
	public static String numToStr(int num) {
		return String.valueOf(num);
	}
	
	public static String formatStr(String str) {
		if (CheckUtils.isBlank(str)) {
			return "";
		}
		return str;
	}
	
	public static String formatToStr(Object obj) {
		if (obj == null) {
			return "";
		}
		return String.valueOf(obj);
	}
	
	public static Integer formatToInt(Object obj) {
		if (obj == null) {
			return 0;
		}
		return (int) Double.parseDouble(String.valueOf(obj));
	}
	
	public static Integer getInt(String str, int defaultVal) {
		if (CheckUtils.isBlank(str)) {
			return defaultVal;
		}
		return Integer.parseInt(str);
	}
	public static Integer getInt(Object obj, int defaultVal) {
		if (obj == null) {
			return defaultVal;
		}
		return Integer.parseInt(String.valueOf(obj));
	}
	
	public static Long getLong(Object obj, Long defaultVal) {
		if (obj == null) {
			return defaultVal;
		}
		return Long.parseLong(String.valueOf(obj));
	}
	
	public static String getStr(Object obj, String defaultVal) {
		if (obj == null) {
			return defaultVal;
		}
		return String.valueOf(obj);
	}
	
	public static Boolean getBoolean(Object obj, Boolean defaultVal) {
		return formatToBoolean(obj, defaultVal);
	}
	
	public static String trim(Object obj) {
		return formatToStr(obj).trim();
	}
	
	public static Boolean formatToBoolean(Object obj, Boolean defaultVal) {
		if (obj == null) {
			return defaultVal;
		}
		return Boolean.parseBoolean(String.valueOf(obj));
	}
}
