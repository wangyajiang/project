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

public class PaChongTaskRun implements Runnable {
	
	private final static String path = "F:/3/";
	private BufferedWriter bw = null;
	private String parentUrl = null;
	private int agin = 1;
	private long index = 0;
	
	PaChongTaskRun(String url, String name) {
		bw = FileUtils.getBufferedWriter(path + name + ".txt");
		parentUrl = url;
	}
	
	public Document getDocumentByProxy(String url) {
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
	
	public Document getDocumentTryAgin(String url) {
		Document doc = null;
		try {
//			doc = getDocumentByProxy(url);
			URL httpUrl = new URL(url);
			doc = Jsoup.parse(httpUrl, 20000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (doc == null) {
			System.out.println(url);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>> try connection agin sleep 5 sec\t count:" + agin);
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				System.out.println(">:" + Thread.currentThread().getName());
				e.printStackTrace();
			}
			agin ++;
			return getDocumentTryAgin(url);
		} else {
			return doc;
		}
	}
	
	public Elements getTrElements(String url) {
		Document doc = null;
		try {
			agin = 1;
			System.out.println("line:" + index + "\t" + Thread.currentThread().getName() + "");
			System.out.println(url);
			doc = getDocumentTryAgin(url);
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
	
	public String getUrl(String url) {
		StringBuilder retUrl = new StringBuilder("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/");
		String code = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
		if (code.length() == 4) {
			retUrl.append(code.substring(0, 2));
			retUrl.append("/");
			retUrl.append(code);
			retUrl.append(".html");
		} else if (code.length() == 6) {
			retUrl.append(code.substring(0, 2));
			retUrl.append("/");
			retUrl.append(code.substring(2, 4));
			retUrl.append("/");
			retUrl.append(code);
			retUrl.append(".html");
		} else if (code.length() == 9) {
			retUrl.append(code.substring(0, 2));
			retUrl.append("/");
			retUrl.append(code.substring(2, 4));
			if ("00".compareTo(code.substring(4, 6)) != 0) {
				retUrl.append("/");
				retUrl.append(code.substring(4, 6));
			} else {
				System.out.println(">>>>>>>>>>>>>>>>:" + code);
			}
			retUrl.append("/");
			retUrl.append(code);
			retUrl.append(".html");
		} else {
			return null;
		}
		return retUrl.toString();
	}
	
	public void getHtmlByData(String url) {
		try {
			Thread.sleep(200);
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
			  index ++;
//			  System.out.println("line:" + index + "\t" + Thread.currentThread().getName() + ">>>>>>>>>>\t" + line);
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
				getHtmlByData(href);
			}
			links.clear();
			links = null;
		}
	}

	public void run() {
		getHtmlByData(parentUrl);
		FileUtils.close(bw);
		System.out.println("结束：关闭流>>>>>>>>>>>>");
	}
}
