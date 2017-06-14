package com.test;

import java.util.ArrayList;
import java.util.List;

import com.tool.comm.BaseUtils;
import com.tool.constant.EnumConfig;
import com.tool.utils.DBHelper;
import com.tool.utils.DTUtils;

public class test2 {

	public void insertFileInfo() {
		DBHelper db = new DBHelper(EnumConfig.DB_NAME.SOURCE);
		try {
			String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			List<Object[]> list = new ArrayList<Object[]>();
			for (int i = 12; i <= 20; i ++) {
				Object[] arr = new Object[13];
				int index = 0;
				arr[index ++] = i;
				arr[index ++] = "zhangsan" + i;
				arr[index ++] = "e10adc3949ba59abbe56e057f20f883e";
				arr[index ++] = "张三" + i;
				arr[index ++] = "138888888" + i;
				arr[index ++] = 281;
				arr[index ++] = "0571-8888_" + i;
				arr[index ++] = "专员" + i;
				arr[index ++] = 1;
				arr[index ++] = 0;
				arr[index ++] = DTUtils.getNowDate();
				arr[index ++] = DTUtils.getNowDate();
				arr[index ++] = 0;
				list.add(arr);
			}
			db.executeBatch(sql, list);
			System.out.println("ok");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		BaseUtils.newInstance();
		test2 t = new test2();
		t.insertFileInfo();

	}

}
