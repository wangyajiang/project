package com.area;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tool.comm.BaseUtils;
import com.tool.utils.ConvertUtils;
import com.tool.utils.DBHelper;

public class OldOrgToNewOrg {
	
	DBHelper source = null;
	DBHelper gold = null;
	List<Object[]> objlist = new ArrayList<Object[]>();
	
	public OldOrgToNewOrg() {
		BaseUtils.newInstance();
		source = new DBHelper("udcweb");
		gold = new DBHelper("gpmapi55");
	}
	
	public void getOrg() {
		int souceId = 40;
		int goldId = 1;
		try {
			Map<String, Object> sourcePrivence = source.querySingle(Map.class, "select id,name,parentid from organization where id = ?", new Object[]{souceId});
			Map<String, Object> goldPrivence = gold.querySingle(Map.class, "select * from organization where  id = ?", new Object[]{goldId});
//			List<Map<String, Object>> l = source.queryList(Map.class, "select id,name,parentid from organization where id = 40", null);
			System.out.println(sourcePrivence);
//			System.out.println(goldPrivence);
			test(souceId, goldId);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void test(Integer souceId, Integer goldId) {
		try {
			List<Map<String, Object>> souceList = source.queryList(Map.class, "select id,name,parentid from organization where parentid = ?", new Object[]{souceId});
//			Map<String, Map<String, Object>> sourceParam = new HashMap<String, Map<String, Object>>();
//			for (Map<String, Object> item : souceList) {
//				sourceParam.put(item.get("name").toString(), item);
//			}
//			List<Map<String, Object>> goldList = gold.queryList(Map.class, "select id,name,parent_id,level from organization where parent_id = ?", new Object[]{goldId});
//			List<Object[]> dataList = new ArrayList<Object[]>();
//			for (Map<String, Object> item : goldList) {
//				if (sourceParam.containsKey(item.get("name").toString())) {
//					Object[] arr = new Object[2];
//					arr[0] = sourceParam.get(item.get("name").toString()).get("id");
//					arr[1] = item.get("id");
//					dataList.add(arr);
//					System.out.println(Arrays.toString(arr));
//				}
//			}
//			
			
//			if (dataList != null && !dataList.isEmpty()) {
////				gold.executeBatch("update organization set old_id = ? where id = ?", dataList);
//				for (Object[] arr : dataList) {
//					test(ConvertUtils.getInt(arr[1], 0), ConvertUtils.getInt(arr[0], 0));
//				}
//			}
			
			for (Map<String, Object> param : souceList) {
				System.out.println(param);
			}
			for (Map<String, Object> param : souceList) {
				test(ConvertUtils.getInt(param.get("id"), 0), null);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		OldOrgToNewOrg oorno = new OldOrgToNewOrg();
		oorno.getOrg();
	}
}
