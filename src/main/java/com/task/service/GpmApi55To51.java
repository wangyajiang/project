package com.task.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.task.entity.Company;
import com.task.entity.CompanyHiddenInfo;
import com.tool.comm.BaseUtils;
import com.tool.utils.ConvertUtils;
import com.tool.utils.DBHelper;

public class GpmApi55To51 {
	private Random random = new Random();
	private List<Integer> userIdList = new ArrayList<Integer>();
	private List<Company> companyList = null;
	private List<CompanyHiddenInfo> hideList = null;
	private Integer companyBeginId = 1;
	private Integer hideBeginId = 1;
	
	private DBHelper source = null;
	private DBHelper gold = null;
	
	public GpmApi55To51() {
		BaseUtils.newInstance();
		source = new DBHelper("gpmapi55");
		gold = new DBHelper("gpmapi51");
		System.out.println("初始化数据>>>>>>>>>>:");
		try {
			List<Map<String, Object>> users = gold.queryList(Map.class, "select * from user", null);
			for (Map<String, Object> user : users) {
				userIdList.add(ConvertUtils.getInt(user.get("id"), 0));
			}
			companyList = source.queryList(Company.class, "select * from company", null);
			hideList = source.queryList(CompanyHiddenInfo.class, "select * from company_hidden_info", null);
			Map<String, Object> item = gold.querySingle(Map.class, "select max(id) id from company", null);
			Map<String, Object> hideItem = gold.querySingle(Map.class, "select max(id) id from company_hidden_info", null);
			companyBeginId = ConvertUtils.getInt(item.get("id"), 0);
			hideBeginId = ConvertUtils.getInt(hideItem.get("id"), 0);
			companyBeginId ++;
			hideBeginId ++;
			System.out.print("企业：" + companyList.size());
			System.out.print("\t隐患：" + hideList.size());
			System.out.print("\tcompanyBeginId：" + companyBeginId);
			System.out.println("\thideBeginId：" + hideBeginId);
			System.out.println(">>>>>>>>>初始化数据完成");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void result() {
		List<CompanyHiddenInfo> rtHideList = new ArrayList<CompanyHiddenInfo>();
		
		Map<Integer, List<CompanyHiddenInfo>> hideParam = new HashMap<Integer, List<CompanyHiddenInfo>>();
		for (CompanyHiddenInfo item : hideList) {
			if (!hideParam.containsKey(item.getCompanyId())) {
				List<CompanyHiddenInfo> list = new ArrayList<CompanyHiddenInfo>();
				hideParam.put(item.getCompanyId(), list);
			}
			hideParam.get(item.getCompanyId()).add(item);
		}
		int userLen = userIdList.size();
		for (Company company : companyList) {
			List<CompanyHiddenInfo> list = hideParam.get(company.getId());
			company.setId(companyBeginId);
			if (list != null) {
				for (CompanyHiddenInfo item : list) {
					item.setCompanyId(company.getId());
					item.setId(hideBeginId);
					if (item.getUserId() != null && item.getUserId() != 0) {
						item.setUserId(userIdList.get(random.nextInt(userLen)));
					}
					rtHideList.add(item);
					hideBeginId ++;
				}
			}
			companyBeginId ++;
		}
		try {
//			DBUtils.insertBatch(gold, companyList);
//			DBUtils.insertBatch(gold, rtHideList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		GpmApi55To51 gmm = new GpmApi55To51();
		gmm.result();
	}

}
