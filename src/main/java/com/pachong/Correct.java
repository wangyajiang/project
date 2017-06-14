package com.pachong;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.area.MainRun;
import com.tool.utils.CheckUtils;
import com.tool.utils.FileUtils;
public class Correct {
	public static final String prifxx = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2013/";
	public static String getGoldLink(String code) {
		StringBuilder retUrl = new StringBuilder(prifxx);
		if (MainRun.isCity(code)) {
			retUrl.append(code.substring(0, 2));
			retUrl.append(".html");
		} else if (MainRun.isRegion(code)) {
			retUrl.append(code.substring(0, 2));
			retUrl.append("/");
			retUrl.append(code.substring(0, 4));
			retUrl.append(".html");
		} else if (MainRun.isStreet(code)) {
			retUrl.append(code.substring(0, 2));
			retUrl.append("/");
			retUrl.append(code.substring(2, 4));
			retUrl.append("/");
			retUrl.append(code.substring(0, 6));
			retUrl.append(".html");
		} else if (MainRun.isCommunity(code)) {
			retUrl.append(code.substring(0, 2));
			retUrl.append("/");
			retUrl.append(code.substring(2, 4));
			if ("00".compareTo(code.substring(4, 6)) != 0) {
				retUrl.append("/");
				retUrl.append(code.substring(4, 6));
			}
			retUrl.append("/");
			retUrl.append(code.substring(0, 9));
			retUrl.append(".html");
		}
		return retUrl.toString();
	}
	
	public static String getHtml(String url) {
		StringBuffer sb = new StringBuffer();
		try {
			URL u = new URL(url);
	        URLConnection connection = u.openConnection();
	        HttpURLConnection htCon = (HttpURLConnection) connection;
	        int code = htCon.getResponseCode();
	        
            if (code == HttpURLConnection.HTTP_OK) { 
                BufferedReader in = new BufferedReader(new InputStreamReader(htCon.getInputStream(), "gbk"));
                String inputLine;
                while ((inputLine = in.readLine()) != null) 
                	sb.append(inputLine);
                    in.close();
            } else {
                System.out.println("Can not access the website");
            }
		} catch (Exception e) {
		}
		  return sb.toString();
	}
	
	public static Elements getTrElements(String url) {
		Document doc = null;
		try {
			String html = getHtml(url);
			doc = Jsoup.parse(html);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Elements trs = doc.getElementsByClass("citytr");
		if (trs == null || trs.isEmpty()) {
			trs = doc.getElementsByClass("countytr");
		}
		if (trs == null || trs.isEmpty()) {
			trs = doc.getElementsByClass("towntr");
		}
		if (trs == null || trs.isEmpty()) {
			trs = doc.getElementsByClass("villagetr");
		}
		return trs;
	}
	
	public static String getLine(String code) {
		String url = getGoldLink(code);
		Elements trs = getTrElements(url);
		String line = "";
		for (Element tr : trs) {
		  line = "";
		  if (tr.getElementsByTag("a").size() > 0) {
			  for (Element a : tr.getElementsByTag("a")) {
				  line += a.html() + " ";
			  }
		  } else {
			  for (Element td : tr.getElementsByTag("td")) {
				  line += td.text() + " ";
			  }
		  }
		  if (line.length() > 0) {
			  line = line.substring(0, line.length() - 1);
		  }
		  line.trim();
		  if (CheckUtils.isNotBlank(line)) {
			  if (code.compareTo(line.split(" ")[0]) == 0) {
				  return line;
			  }
			  
		  }
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		String name = "浙江省";
		name = "安徽省";
		name = "北京市";
		name = "福建省";
		name = "甘肃省";
		name = "广东省";
		name = "广西壮族自治区";
		name = "贵州省";
		name = "海南省";
		name = "河北省";
		name = "河南省";
		name = "黑龙江省";
		name = "湖北省";
		name = "湖南省";
		name = "吉林省";
		name = "江苏省";
		name = "江西省";
		name = "辽宁省";
		name = "内蒙古自治区";
		name = "宁夏回族自治区";
		name = "青海省";
		name = "山东省";
		name = "山西省";
		name = "陕西省";
		name = "上海市";
		name = "四川省";
		name = "天津市";
		name = "西藏自治区";
		name = "新疆维吾尔自治区";
		name = "云南省";
		name = "重庆市";
		BufferedReader br = FileUtils.getBufferedReader("F:/2/" + name + ".txt", "utf8");
		BufferedWriter bw = FileUtils.getBufferedWriter("F:/3/" + name + ".txt");
		BufferedWriter zqBw = FileUtils.getBufferedWriter("F:/zq/" + name + ".txt");
		Map<String, String> codeLinePparam = new HashMap<String, String>();
		Set<String> urlSet = new HashSet<String>();
		List<String> dataList = new ArrayList<String>();
		Map<Long, String> indexParam = new HashMap<Long, String>();
		String line = "";
		long index = 0L;
		while((line = br.readLine()) != null) {
			if (CheckUtils.isBlank(line)) {
				index ++;
				continue;
			}
			if (line.indexOf("�") >= 0) {
				urlSet.add(getGoldLink(line.split(" ")[0]));
				indexParam.put(index, line);
			}
			dataList.add(line);
			index ++;
		}
		FileUtils.close(br);
		if (indexParam.isEmpty()) {
			for (String str : dataList) {
				FileUtils.writeLine(bw, str);
			} 
			FileUtils.close(bw);
			return;
		}
		System.out.println("读取页码数量" + urlSet.size());
		Elements trs = null;
		int uindex = 0;
		for (String url : urlSet) {
			try {
				Thread.sleep(200);
			} catch (Exception e) {
			}
			uindex ++;
			System.out.println((Math.round(uindex * 100 / urlSet.size())) + "%\t" + url);
			trs = getTrElements(url);
			line = "";
			for (Element tr : trs) {
			  line = "";
			  if (tr.getElementsByTag("a").size() > 0) {
				  for (Element a : tr.getElementsByTag("a")) {
					  line += a.html() + " ";
				  }
			  } else {
				  for (Element td : tr.getElementsByTag("td")) {
					  line += td.text() + " ";
				  }
			  }
			  if (line.length() > 0) {
				  line = line.substring(0, line.length() - 1);
			  }
			  line.trim();
			  if (CheckUtils.isNotBlank(line)) {
				  codeLinePparam.put(line.split(" ")[0], line);
				  FileUtils.writeLine(zqBw, line);
			  }
			}
		}
		FileUtils.close(zqBw);
		for (Long key : indexParam.keySet()) {
			line = indexParam.get(key);
			if (codeLinePparam.containsKey(line.split(" ")[0])) {
				indexParam.put(key, codeLinePparam.get(line.split(" ")[0]));
			}
		}
		int len = dataList.size();
		String str = "";
		for (Integer i = 0; i < len; i ++) {
			str = dataList.get(i);
			if (indexParam.containsKey(Long.valueOf(String.valueOf(i)))) {
				System.out.println("校验：line：" + (i + 1) + "\t原值：" + str + "\t转换：" + indexParam.get(Long.valueOf(String.valueOf(i))));
				str = indexParam.get(Long.valueOf(String.valueOf(i)));
			}
			FileUtils.writeLine(bw, str);
		} 
		FileUtils.close(bw);
		
	}
}
