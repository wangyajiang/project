package com.pachong;

import java.io.BufferedWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.tool.constant.ProxyServer;
import com.tool.utils.CheckUtils;
import com.tool.utils.FileUtils;

public class MainRun {
	private final static String path = "F:/1/";
	private static BufferedWriter bw = null;
	
	public static void init(String fileName) {
		bw = FileUtils.getBufferedWriter(path + fileName + ".txt");
	}
	
	public static Document getDocumentByProxy(String url) {
		String[] ipPort = ProxyServer.getRandomProxyServer().split(" ");
		String ip = ipPort[0];
		String port = ipPort[1];
		Document doc = null;
		try {
			System.getProperties().setProperty("proxySet", "true");
	        System.getProperties().setProperty("http.proxyHost", ip);
	        System.getProperties().setProperty("http.proxyPort", port);
	        Connection conn = Jsoup.connect(url);
	        conn.timeout(20000);
	        doc = conn.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	private static int agin = 1;
	public static Document getDocumentTryAgin(String url) {
		Document doc = null;
		try {
			URL httpUrl = new URL(url);
			doc = Jsoup.parse(httpUrl, 20000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (doc == null) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>> try connection agin sleep 5 sec\t count:" + agin);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			agin ++;
			return getDocumentTryAgin(url);
		} else {
			return doc;
		}
		
	}
	
	public static Elements getTrElements(String url) {
		Document doc = null;
		try {
			agin = 1;
			System.out.println(url);
			doc = getDocumentTryAgin(url);
//			doc = getDocumentByProxy(url);
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
	
	private static String getUrl(String url) {
		String retUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2013/";
		String code = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
		if (code.length() == 4) {
			retUrl = retUrl + code.substring(0, 2) + "/" + code + ".html";
		} else if (code.length() == 6) {
			retUrl = retUrl + code.substring(0, 2) + "/" + code.substring(2, 4) + "/" + code + ".html";
		} else if (code.length() == 9) {
			retUrl = retUrl + code.substring(0, 2) + "/" + code.substring(2, 4) + "/" + code.substring(4, 6) + "/" + code + ".html";
		} else {
			return null;
		}
		return retUrl;
	}
	
	public static void getHtmlByData(String url, String name) {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<String> links = new ArrayList<String>();
		Elements trs = getTrElements(url);
		String line = "";
		String link = "";
		for (Element tr : trs) {
		  line = "";
		  if (tr.getElementsByTag("a").size() > 0) {
			  for (Element a : tr.getElementsByTag("a")) {
				  line += a.text() + " ";
				  link = a.attr("href");
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
			  FileUtils.writeLine(bw, line);
		  }
		  if (CheckUtils.isNotBlank(link)) {
			  if (CheckUtils.isNotBlank(link)) {
				  link = getUrl(link);
				  links.add(link);
			  }
		  }
		}
		if (links != null && !links.isEmpty()) {
			for (String href : links) {
				getHtmlByData(href, name);
			}
		}
	}
//	private static long total = 0L;
//	public static void addCookie(String line) {
//		total ++;
//		JSONObject param = new JSONObject();
//		String[] arr = line.split(" ");
//		param.put("id", arr[0]);
//		param.put("code", arr[0]);
//		param.put("name", arr.length == 3 ? arr[2] : arr[1]);
//		String id = arr[0];
//		String index = ConvertUtils.formatToStr(total);
//		if (com.area.MainRun.isCity(arr[0])) {
//			param.put("level", 2);
//			param.put("children_code", arr[0].subSequence(0, 4));
//			param.put("parent_code", arr[0].subSequence(0, 2));
//		} else if (com.area.MainRun.isRegion(arr[0])) {
//			param.put("level", 3);
//			param.put("children_code", arr[0].subSequence(0, 6));
//			param.put("parent_code", arr[0].subSequence(0, 4));
//		} else if (com.area.MainRun.isStreet(arr[0])) {
//			param.put("level", 4);
//			param.put("children_code", arr[0].subSequence(0, 9));
//			param.put("parent_code", arr[0].subSequence(0, 6));
//		} else if (com.area.MainRun.isCommunity(arr[0])) {
//			param.put("level", 4);
//			param.put("parent_code", arr[0].subSequence(0, 9));
//		}
//		ElasticsearchUtils.addDocument(param, index, eType, id);
//	}
	public static void run() {
		String url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2013/15.html";
		String name = "内蒙古自治区";
		init(name);
		getHtmlByData(url, name);
		FileUtils.close(bw);
		System.out.println("结束：关闭流>>>>>>>>>>>>");
//		try {
//			QuartzManager.removeJob(Constants.JOB_NAME);
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}  
	}
	public static void main(String[] args) {
		
	}

}
