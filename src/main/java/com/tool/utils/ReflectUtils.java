package com.tool.utils;

/**
 * 反射工具
 * @author wyj
 */
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.tool.constant.EnumConfig;
import com.tool.exception.ServiceException;

public class ReflectUtils {

	private static String getMethodName(String fildeName) throws Exception{
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] + 'a' - 'A');
		return new String(items);
	}
	
	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> Map<String, Boolean> getObjFiled(Class<T> clazz) {
		Map<String, Boolean> param = new HashMap<String, Boolean>();
		if (null == clazz) {
			return param;
		}
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
			for (int i = 0; i< propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
		        param.put(descriptor.getName(), true);
		   }
		   return param;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> Map<String, Object> objToMap(T obj) {
		String[] strs = null;
		return objToMap(obj, null, strs);
	}
	
	public static <T> Map<String, Object> objToMap(T obj, String nullDefault) {
		String[] strs = null;
		return objToMap(obj, nullDefault, strs);
	}
	
	public static <T> Map<String, Object> objToMap(T obj, String nullDefault, String ... keys) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		try {
			if (null == keys) {
				@SuppressWarnings("unchecked")
				Class<T> clazz = (Class<T>) obj.getClass();
				Method[] methods = clazz.getDeclaredMethods();
				for (Method method : methods) {
					String methodName = method.getName();  
					if (methodName.startsWith("get")) {
		                Object value = method.invoke(obj);
		                if (null == value) {
		                	value = nullDefault;
		                }
		                rtMap.put(getMethodName(methodName.replaceFirst("(get)", "")), value);
		            }
			    }
				return rtMap;
			}
			if (keys.length == 0) {
				return rtMap;
			}
			@SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>) obj.getClass();
			StringBuffer rtKey = new StringBuffer();
			for (String key : keys) {
				rtKey.delete(0, rtKey.length());
				rtKey.append("get");
				rtKey.append(String.valueOf(key.charAt(0)).toUpperCase());
				if (key.length() > 1) {
					rtKey.append(key.substring(1));
				}
				try {
					Method mth = clazz.getMethod(rtKey.toString());
					rtMap.put(key, mth.invoke(obj));
				} catch (Exception e) {}
				if (null == rtMap.get(key)) {
					rtMap.put(key, "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return rtMap;
	}
	public static <T> List<Map<String, Object>> objToMapList(List<T> list, String ... keys) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		if (list == null || list.isEmpty()) {
			return rtList;
		}
		try {
			for (T item : list) {
				rtList.add(objToMap(item, null, keys));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return rtList;
	}
	
	public static <T> List<Map<String, Object>> objToMapList(List<T> list, String nullDefault, String ... keys) {
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		if (list == null || list.isEmpty()) {
			return rtList;
		}
		try {
			for (T item : list) {
				rtList.add(objToMap(item, nullDefault, keys));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return rtList;
	}
	
	public static <T> Map<String, Object> mergeObjToMap(T ... objs) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		try {
			if (null == objs || objs.length == 0) {
				return rtMap;
			}
			for (T obj : objs) {
				rtMap.putAll(objToMap(obj));
			}
			return rtMap;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return rtMap;
	}
	
	public static <T> T mapToObj(Class<T> clazz, Map<String, Object> param) {
		if (param == null || param.isEmpty()) {
			return null;
		}
		if (null == clazz) {
			return null;
		}
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			T item = clazz.newInstance();
			PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
			for (int i = 0; i< propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
		        String propertyName = descriptor.getName();
	            if (param.containsKey(propertyName)) {
	                Object value = param.get(propertyName);
	                Object[] args = new Object[1];
	                args[0] = value;
	                descriptor.getWriteMethod().invoke(item, args);
	            }
		   }
		   return item;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T mapToObj(T obj, Map<String, Object> param) {
		if (param == null || param.isEmpty()) {
			return null;
		}
		if (null == obj) {
			return null;
		}
		try {
			@SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>) obj.getClass();
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			T item = clazz.newInstance();
			PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
			for (int i = 0; i< propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
		        String propertyName = descriptor.getName();
	            if (param.containsKey(propertyName)) {
	                Object value = param.get(propertyName);
	                Object[] args = new Object[1];
	                args[0] = value;
	                descriptor.getWriteMethod().invoke(item, args);
	            }
		   }
		   return item;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T JSONObjectToObj(Class clazz, JSONObject obj, String dateFormat) throws ServiceException {
		T item = null;
		if (clazz == null) {
			return item;
		}
		try {
			item = (T) clazz.newInstance();
			if (obj == null || obj.size() == 0) {
				return item;
			}
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
			for (int i = 0; i< propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
		        String propertyName = descriptor.getName();
	            if (obj.containsKey(propertyName)) {
	               CheckUtils.setObjField(item, propertyName, obj.get(propertyName), dateFormat);
	            }
		   }
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(EnumConfig.S_CODE.TRANSFORMATION);
		}
		return item;
	}
	
	public static <T> Object getObjByColumn(T item, Class<?> clazz, String key) {
		try {
			StringBuffer rtKey = new StringBuffer();
			rtKey.append("get");
			rtKey.append(String.valueOf(key.charAt(0)).toUpperCase());
			if (key.length() > 1) {
				rtKey.append(key.substring(1));
			}
			Method mth = clazz.getMethod(rtKey.toString());
			return mth.invoke(item);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	public static List<String> getColumnsByClass(Class<?> clazz) {
		List<String> list = new ArrayList<String>();
		try {
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method : methods) {
				String methodName = method.getName();
				if (methodName.startsWith("get")) {
					list.add(ConvertUtils.camelToUnderline(getMethodName(methodName.replaceFirst("(get)", ""))));
	            }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
