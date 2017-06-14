package com.sync;

import java.util.List;
import java.util.Map;

import com.test.Organization;
import com.tool.comm.BaseUtils;
import com.tool.mysql.DBUtils;
import com.tool.utils.DBHelper;

public class SyncTable {
	DBHelper source = null;
	DBHelper gold = null;
	
	public SyncTable() {
		BaseUtils.newInstance();
		gold = new DBHelper("udcweb");
		source = new DBHelper("gpmapi55");
	}
	
	public void sync() {
		//organization,organizationnew
		try {
			List<Organization> list = source.queryList(Organization.class, "select * from organization", null);
			DBUtils.insertBatch(gold, list, "organizationnew");
			System.out.println(list.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SyncTable sync = new SyncTable();
		sync.sync();
	}
}
