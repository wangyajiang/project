package com.pachong;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tool.utils.CheckUtils;
import com.tool.utils.FileUtils;

public class Test {
	public static void main(String[] args) throws Exception {
		String name = "浙江省";
//		name = "安徽省";
//		name = "北京市";
//		name = "福建省";
//		name = "福建省";
//		name = "甘肃省";
//		name = "广东省";
//		name = "广西壮族自治区";
//		name = "贵州省";
//		name = "海南省";
//		name = "河北省";
//		name = "河南省";
//		name = "黑龙江省";
//		name = "湖北省";
//		name = "湖南省";
		name = "吉林省";
//		name = "江苏省";
//		name = "江西省";
//		name = "辽宁省";
//		name = "内蒙古自治区";
//		name = "宁夏回族自治区";
//		name = "青海省";
//		name = "山东省";
//		name = "山西省";
//		name = "陕西省";
//		name = "上海市";
//		name = "四川省";
//		name = "天津市";
//		name = "西藏自治区";
//		name = "新疆维吾尔自治区";
//		name = "云南省";
//		name = "重庆市";
		BufferedReader br = FileUtils.getBufferedReader("F:/3/" + name + ".txt", "utf8");
		Set<String> urlSet = new HashSet<String>();
		List<String> dataList = new ArrayList<String>();
		String line = "";
		long index = 0L;
		while((line = br.readLine()) != null) {
			if (CheckUtils.isBlank(line)) {
				index ++;
				continue;
			}
			if (line.indexOf("?") >= 0) {
				urlSet.add(Correct.getGoldLink(line.split(" ")[0]));
				System.out.println(index + "\t" + line);
			}
			dataList.add(line);
			index ++;
		}
		FileUtils.close(br);
		System.out.println("error:" + urlSet.size());
	}
}
