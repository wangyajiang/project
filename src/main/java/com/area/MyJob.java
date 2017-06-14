package com.area;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.tool.utils.ElasticsearchUtils;

public class MyJob {

	public void start() {
		List<String> list = new ArrayList<String>();
//		list.add("33 浙江省");
//		list.add("11 北京市");
//		list.add("12 天津市");
//		list.add("13 河北省");
//		list.add("14 山西省");
//		list.add("15 内蒙古自治区");
//		list.add("21 辽宁省");
		list.add("22 吉林省");//TODO
//		list.add("23 黑龙江省");
//		list.add("31 上海市");
//		list.add("32 江苏省");
//		list.add("34 安徽省");
//		list.add("35 福建省");
//		list.add("36 江西省");
//		list.add("37 山东省");
//		list.add("41 河南省");
//		list.add("42 湖北省");
//		list.add("43 湖南省");
//		list.add("44 广东省");//TODO
//		list.add("45 广西壮族自治区");//TODO 行政代码不规范
//		list.add("46 海南省");
//		list.add("50 重庆市");
//		list.add("51 四川省");
//		list.add("52 贵州省");
//		list.add("53 云南省");
//		list.add("54 西藏自治区");
//		list.add("61 陕西省");
//		list.add("62 甘肃省");
//		list.add("63 青海省");
//		list.add("64 宁夏回族自治区");
//		list.add("65 新疆维吾尔自治区");//TODO
		
		
		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
		Integer provenceId = 1;
		for (String line : list) {
			singleThreadExecutor.execute(new MainRun(line, provenceId));
			provenceId ++;
		}
		singleThreadExecutor.shutdown();
	}
	
	public static void main(String[] args) {
		ElasticsearchUtils.newInstance();
		new MyJob().start();
	}
}
