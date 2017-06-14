package com.tool.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.alibaba.fastjson.JSONObject;
import com.tool.bean.Judge;
import com.tool.constant.EnumConfig;
import com.tool.exception.ServiceException;


/**
* 验证工具类
* @author wyj
*/
public class CheckUtils {
	
	/**
	 * 字符串为空校验
	 */
	public static boolean isBlank(final CharSequence cs) {
		int strLen;
	    if (cs == null || (strLen = cs.length()) == 0) {
	        return true;
	    }
	    for (int i = 0; i < strLen; i++) {
	        if (Character.isWhitespace(cs.charAt(i)) == false) {
	            return false;
	        }
	    }
	    return true;
	}
	
	/**
	 * 字符串不为空校验
	 */
	public static boolean isNotBlank(final CharSequence str) {
		return !isBlank(str);
	}
	
	/**
	 * 字符串数组为空校验
	 */
	public static boolean isExistBlank(final CharSequence ... strs) {
		if (strs == null || strs.length == 0) {
			return true;
		}
		for (CharSequence str : strs) {
			if (isBlank(str)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 字符串数组不为空校验
	 */
	public static boolean isNotExistBlank(final CharSequence ... strs) {
		return !isExistBlank(strs);
	}
	
	/**
	 * JSONObject数据为空校验
	 */
	public static Judge isBlank(JSONObject obj, String ... keys) {
		Judge judge = new Judge();
		if (obj == null || obj.isEmpty()) {
			judge.setBlank(true);
			judge.setKey("对象");
			return judge;
		}
		if (keys != null && keys.length > 0) {
			for (String key : keys) {
				if (obj.get(key) == null || isBlank(obj.getString(key))) {
					judge.setBlank(true);
					judge.setKey(key);
					return judge;
				}
			}
		}
		return judge;
	}
	
	/**
	 * JSONObject数据为空key返回
	 */
	public static String isBlankKey(JSONObject obj, String ... keys) {
		if (obj == null || obj.isEmpty()) {
			return null;
		}
		if (keys != null && keys.length > 0) {
			for (String key : keys) {
				if (obj.get(key) == null) {
					return key;
				}
			}
		}
		return null;
	}
	
	/**
	 * 对象数据为空key判断
	 * @throws Exception 
	 */
	public static <T> Judge isBlank(T obj, String ... keys) throws Exception {
		Judge judge = new Judge();
		if (null == obj) {
			judge.setBlank(true);
			judge.setKey("对象");
			return judge;
		}
		if (keys == null || keys.length == 0) {
			judge.setBlank(false);
			return judge;
		}
		try {
			@SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>) obj.getClass();
			Field field = null;
			PropertyDescriptor pd = null;
			Method getMethod = null;
			for (String key : keys) {
				field = clazz.getDeclaredField(key);
				pd = new PropertyDescriptor(field.getName(), clazz);
	            getMethod = pd.getReadMethod();
	            if (getMethod.invoke(obj) == null || ("String".compareTo(field.getType().getSimpleName()) == 0 && isBlank(getMethod.invoke(obj).toString()))) {
	            	judge.setBlank(true);
	            	judge.setKey(key);
	                return judge;
	            }
			}
			
		} catch (Exception e) {
        	throw new Exception();
		}
		return judge;
	}
	
	public static String formatStr(String str) {
		if (isBlank(str)) {
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
	
	@SuppressWarnings("rawtypes")
	public static <T> void setObjField(T item, String column, Object val, String dateFormat) throws ServiceException {
		if (column == null) {
			return;
		}
		try {
			Class clazz = item.getClass();
			Field field = clazz.getDeclaredField(column);
			field.setAccessible(true);
			String type = field.getType().getSimpleName().toLowerCase();
			if (type.indexOf("byte") >= 0) {
				field.set(item, Byte.parseByte(formatToStr(val)));
			} else if (type.indexOf("short") >= 0) {
				field.set(item, Short.parseShort(formatToStr(val)));
			} else if (type.indexOf("int") >= 0) {
				field.set(item, Integer.parseInt(formatToStr(val)));
			} else if (type.indexOf("char") >= 0) {
				field.set(item, formatToStr(val).charAt(0));
			} else if (type.indexOf("long") >= 0) {
				field.set(item, Long.parseLong(formatToStr(val)));
			}else if (type.indexOf("double") >= 0) {
				field.set(item, Double.parseDouble(formatToStr(val)));
			} else if (type.indexOf("float") >= 0) {
				field.set(item, Float.parseFloat(formatToStr(val)));
			} else if (type.indexOf("boolean") >= 0) {
				field.set(item, Boolean.parseBoolean(formatToStr(val)));
			} else if (type.indexOf("string") >= 0) {
				field.set(item, formatToStr(val));
			} else if (type.indexOf("date") >= 0) {
				if (val.getClass().getSimpleName().toLowerCase().indexOf("date") >= 0) {
					field.set(item, val);
				} else {
					field.set(item, DTUtils.parse(formatToStr(val), dateFormat));
				}
			} else {
				field.set(item, val);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(EnumConfig.S_CODE.VALIDATE_CODE_ERROR);
			
		}
	}
}
