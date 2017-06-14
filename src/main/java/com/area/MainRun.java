package com.area;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.tool.comm.BaseUtils;
import com.tool.constant.EnumConfig;
import com.tool.utils.CheckUtils;
import com.tool.utils.DBHelper;
import com.tool.utils.DTUtils;
import com.tool.utils.ElasticsearchUtils;

public class MainRun implements Runnable {
	
	private String localFilePath = null;
	private final Integer modal = 1000;
	public static AtomicInteger id = new AtomicInteger(34566);
	List<AreaRule> cityList = new ArrayList<AreaRule>();
	List<AreaRule> regionList = new ArrayList<AreaRule>();
	List<AreaRule> streetList = new ArrayList<AreaRule>();
	List<AreaRule> commitList = new ArrayList<AreaRule>();
	
	private String provenceName = null;
	private Integer provenceId = null;
	private String provenceCode = null;
	
	public MainRun(String line, Integer provenceId) {
		String[] arr = line.split(" ");
		this.provenceId = provenceId;
		this.provenceName = arr[1];
		this.provenceCode = arr[0] + "0000000000";
		this.localFilePath = "F://2/" + arr[1] + ".txt";
	}
	
	List<Object[]> list = new ArrayList<Object[]>();
	public static boolean isProvice(String code) {
		if (Integer.parseInt(code.substring(0, 2)) != 0 && Integer.parseInt(code.substring(2)) == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isCity(String code) {
		if (Integer.parseInt(code.substring(2, 4)) != 0 && Integer.parseInt(code.substring(4)) == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isRegion(String code) {
		if (Integer.parseInt(code.substring(4, 6)) != 0 && Integer.parseInt(code.substring(6)) == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isStreet(String code) {
		if (Integer.parseInt(code.substring(6, 9)) != 0 && Integer.parseInt(code.substring(9)) == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isCommunity(String code) {
		if (Integer.parseInt(code.substring(9)) != 0) {
			return true;
		}
		return false;
	}
	
	public void resultOP() throws Exception {//34469
		for (AreaRule item : cityList) {
			item.setId(id.incrementAndGet());
		}
		for (AreaRule item : regionList) {
			item.setId(id.incrementAndGet());
		}
		for (AreaRule item : streetList) {
			item.setId(id.incrementAndGet());
		}
		for (AreaRule item : commitList) {
			item.setId(id.incrementAndGet());
		}
		Map<String, Integer> flagMap = new HashMap<String, Integer>();
		String codeFlag = null;
		for (int i = 0; i < streetList.size(); i ++) {
			codeFlag = streetList.get(i).getCode().substring(0, 9);
			flagMap.put(codeFlag, i);
		}
		for (AreaRule item : commitList) {
			if (flagMap.containsKey(item.getCode().substring(0, 9))) {
				streetList.get(flagMap.get(item.getCode().substring(0, 9))).getList().add(item);
			} else {
				throw new Exception();
			}
		}
		commitList.clear();
		flagMap.clear();
		
		for (int i = 0; i < regionList.size(); i ++) {
			codeFlag = regionList.get(i).getCode().substring(0, 6);
			flagMap.put(codeFlag, i);
		}
		for (AreaRule item : streetList) {
			if (flagMap.containsKey(item.getCode().substring(0, 6))) {
				regionList.get(flagMap.get(item.getCode().substring(0, 6))).getList().add(item);
			} else {
				System.out.println(item.getCode() + " ====== " + item.getName());
				throw new Exception();
			}
		}
		streetList.clear();
		flagMap.clear();
		
		for (int i = 0; i < cityList.size(); i ++) {
			codeFlag = cityList.get(i).getCode().substring(0, 4);
			flagMap.put(codeFlag, i);
		}
		for (AreaRule item : regionList) {
			if (flagMap.containsKey(item.getCode().substring(0, 4))) {
				cityList.get(flagMap.get(item.getCode().substring(0, 4))).getList().add(item);
			} else {
				System.out.println(item.getCode() + " ====== " + item.getName());
				throw new Exception();
			}
		}
		regionList.clear();
		flagMap.clear();
		insert();
//		insertElasticsearch();
	}
	
	public void readFile(String path) {
		File file = new File(path);
		int index = 0;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf8"));
			String line = null;
			String[] arr = null;
			String code = null;
			long lineIndex = 0L;
	        while((line = br.readLine()) != null) {
	        	lineIndex ++;
	        	if (CheckUtils.isBlank(line)) {
	        		continue;
	        	}
	        	if (line.indexOf("代码 城乡分类 名称") >= 0) {
	        		continue;
	        	}
	        	arr = line.split(" ");
	        	code = arr[0];
	        	if (code.charAt(0) == '﻿') {
	        		code = code.substring(1);
	        	}
	        	if (code.length() != 12) {
	        		continue;
	        	}
	        	if (arr.length == 2 && arr[1].trim().length() > 24) {
	        		System.out.println("因名称过长，不插入数据库：>>>>>>>>>>\t" + line);
	        		continue;
	        	} else if (arr.length == 3 && arr[2].trim().length() > 24) {
	        		System.out.println("因名称过长，不插入数据库：>>>>>>>>>>\t" + line);
	        		continue;
	        	}
        		AreaRule item = new AreaRule();
        		item.setCode(code);
        		if (isCity(code)) {//设置市
        			item.setName(arr[1].trim());
        			cityList.add(item);
        		} else if (isRegion(code)) {//设置区、县
        			item.setName(arr[1].trim());
        			regionList.add(item);
        		} else if (isStreet(code)) {//设置街道
        			item.setName(arr[1].trim());
        			streetList.add(item);
        		} else if (isCommunity(code)) {//设置村
        			try {
        				item.setName(arr[2].trim());
					} catch (Exception e) {
						System.out.println(line);
						e.printStackTrace();
					}
        			commitList.add(item);
        		}
        		if (arr.length > 3) {
        			System.out.println(Thread.currentThread().getName() + ">待整理行>>>>>>>>>>：" + lineIndex);
        		}
        		index++;
	        }
	        System.out.println(Thread.currentThread().getName() + ">读取文件数据量：" + index);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			resultOP();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insert() {
		AreaRule provence = new AreaRule();
		provence.setCode(provenceCode);
		provence.setName(provenceName);
		provence.setId(provenceId);
		Object[] arr = getArr(provence, 1, 0);
		list.add(arr);
		for (AreaRule city : cityList) {
			list.add(getArr(city, 2, provence.getId()));
			for (AreaRule region : city.getList()) {
				list.add(getArr(region, 3, city.getId()));
				for (AreaRule street : region.getList()) {
					list.add(getArr(street, 4, region.getId()));
					for (AreaRule commit : street.getList()) {
						list.add(getArr(commit, 5, street.getId()));
					}
				}
			}
        }
		System.out.println(Thread.currentThread().getName() + ">数据库需要新增数据量：" + list.size());
		BaseUtils.newInstance();
		System.out.println(Thread.currentThread().getName() + ">开始数据库操作");
		DBHelper db = new DBHelper("test");
		String sql = "insert into organization (id, code, name, level, parent_id, create_time) values (?, ?, ?, ?, ?, ?)";
		try {
			List<Object[]> insertList = new ArrayList<Object[]>();
			int size = list.size();
			long total = 0;
			for (Object[] arrInsert : list) {
				insertList.add(arrInsert);
				if (insertList.size() % modal == 0) {
					long num = db.executeBatch(sql, insertList);
					total += num;
					System.out.println(Thread.currentThread().getName() + ">已经完成数据量：" + total + "\t" + Math.round(total * 100 / size) + "%");
					insertList.clear();
				}
			}
			long num = db.executeBatch(sql, insertList);
			total += num;
			list.clear();
			insertList.clear();
			System.out.println(Thread.currentThread().getName() + ">已经完成数据量：" + total + "\t" + Math.round(total * 100 / size) + "%");
			System.out.println(Thread.currentThread().getName() + ">数据库操作结束");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}
	
	public void insertElasticsearch() {
		AreaRule provence = new AreaRule();
		provence.setCode(provenceCode);
		provence.setName(provenceName);
		provence.setId(provenceId);
		Object[] arr = getArr(provence, 1, 0);
		list.add(arr);
		for (AreaRule city : cityList) {
			list.add(getArr(city, 2, 1));
			for (AreaRule region : city.getList()) {
				list.add(getArr(region, 3, city.getId()));
				for (AreaRule street : region.getList()) {
					list.add(getArr(street, 4, region.getId()));
					for (AreaRule commit : street.getList()) {
						list.add(getArr(commit, 5, street.getId()));
					}
				}
			}
        }
		System.out.println(Thread.currentThread().getName() + ">需要新增数据量：" + list.size());
		System.out.println(Thread.currentThread().getName() + ">开始Elasticsearch操作");
		try {
			List<Map<Object, Object>> insertList = new ArrayList<Map<Object, Object>>();
			int size = list.size();
			long total = 0;
			for (Object[] arrInsert : list) {
				Map<Object, Object> item = new HashMap<Object, Object>();
				item.put("id", arrInsert[0]);
				item.put("code", arrInsert[1]);
				item.put("name", arrInsert[2]);
				item.put("level", arrInsert[3]);
				item.put("parentId", arrInsert[4]);
				item.put("createTime", DTUtils.getNow());
				insertList.add(item);
				if (insertList.size() % modal == 0) {
					ElasticsearchUtils.addDocuments(insertList, "organization", "gpmapi_dev");
					total += insertList.size();
					System.out.println(Thread.currentThread().getName() + ">已经完成数据量：" + total + "\t" + Math.round(total * 100 / size) + "%");
					insertList.clear();
				}
			}
			ElasticsearchUtils.addDocuments(insertList, "organization", "gpmapi_dev");
			total += insertList.size();
			list.clear();
			insertList.clear();
			System.out.println(Thread.currentThread().getName() + ">已经完成数据量：" + total + "\t" + Math.round(total * 100 / size) + "%");
			System.out.println(Thread.currentThread().getName() + ">Elasticsearch操作结束");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Object[] getArr(AreaRule item, int level, Integer pid) {
		Object[] arr = new Object[6];
		arr[0] = item.getId();
		arr[1] = item.getCode();
		arr[2] = item.getName();
		arr[3] = level;
		arr[4] = pid;
		arr[5] = DTUtils.getNowDate();
		return arr;
	}
	
	public void run() {
		System.out.println("项目>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + provenceName);
		readFile(localFilePath);
		
	}
}
