package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.tool.utils.ConvertUtils;
import com.tool.utils.FileUtils;

public class Test {
	private static String url = "http://m.baidu.com/tcx?appui=alaxs&page=detail&gid=4315646958&from=dushu&index=1&cid=";
	private static String path = "F://b.txt";
	
	public static String getHtmlByTime(String url) {
		StringBuffer sb = new StringBuffer();
		try {
			URL u = new URL(url);
	        URLConnection connection = u.openConnection();
//	        connection.setConnectTimeout(30000);
//	        connection.setReadTimeout(10000);
	        HttpURLConnection urlConn = (HttpURLConnection) connection;

			urlConn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
	        urlConn.setRequestProperty("Accept",
	                "image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-powerpoint, application/vnd.ms-excel, application/msword, */*");
	        urlConn.setRequestProperty("Accept-Language", "zh-cn");
	        urlConn.setRequestProperty("UA-CPU", "x86");
	        urlConn.setRequestProperty("Accept-Encoding", "gzip");//为什么没有deflate呢
	        urlConn.setRequestProperty("Content-type", "text/html");
	        urlConn.setRequestProperty("Connection", "close"); //keep-Alive，有什么用呢，你不是在访问网站，你是在采集。嘿嘿。减轻别人的压力，也是减轻自己。

	        urlConn.setUseCaches(false);//不要用cache，用了也没有什么用，因为我们不会经常对一个链接频繁访问。（针对程序）
	        urlConn.setConnectTimeout(6 * 1000);
	        urlConn.setReadTimeout(6*1000);
	        urlConn.setDoOutput(true);
	        urlConn.setDoInput(true);
	        
	        int code = urlConn.getResponseCode();
	        
            if (code == HttpURLConnection.HTTP_OK) { 
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "utf-8"));
                String inputLine;
                while ((inputLine = in.readLine()) != null) 
                	sb.append(inputLine);
                    in.close();
            } else {
                System.out.println("Can not access the website");
            }
		} catch (Exception e) {
			// TODO: handle exception
		}
		return sb.toString();
	}
	
	public static String getHtml(String url) {
		StringBuffer sb = new StringBuffer();
		try {
			URL u = new URL(url);
	        URLConnection connection = u.openConnection();
	        connection.setConnectTimeout(30000);
	        connection.setReadTimeout(10000);
	        HttpURLConnection htCon = (HttpURLConnection) connection;
	        int code = htCon.getResponseCode();
	        
            if (code == HttpURLConnection.HTTP_OK) { 
                BufferedReader in = new BufferedReader(new InputStreamReader(htCon.getInputStream(), "utf-8"));
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
	
	public static String getSuffix() {
		String suffix = "10156709";
		try {
			if (!new File(path).exists()) {
				return suffix;
			}
			BufferedReader br = FileUtils.getBufferedReader(path, null);
			suffix = br.readLine();
			FileUtils.close(br);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return suffix;
	}
	
	public static void setNum(String str) {
		BufferedWriter bw = null;
		bw = FileUtils.getBufferedWriter(path);
		FileUtils.writeLine(bw, ConvertUtils.formatToStr(str));
		FileUtils.close(bw);
	}
	
	public static void main(String[] args) throws MalformedURLException, IOException {
		String u = url + getSuffix();
		String html = getHtmlByTime(u);
//		Document doc = Jsoup.parse(new URL(url), 20000);
		Document doc = Jsoup.parse(html);
		Element element = doc.getElementById("chapterContent");
		System.out.println(element);
//		Elements xyyE = element.getElementsByAttribute("");
		
//		setNum(xyyE.attr("href").toString());
//		System.out.println(element.html().replaceAll("&nbsp;", "").replaceAll("<br>", ""));
//		String str = element.text();
//		int num = 25;
//		while (str.length() > num) {
//			System.out.println(str.substring(0, num).trim());
//			str = str.substring(num);
//			System.out.println();
//			System.out.println();
//		}
//		System.out.println(str.trim());
	}
}
