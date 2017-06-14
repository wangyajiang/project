package com.tool.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class BaiDuMapUtils {
	private static final String AK= "vvAgaNbP8VNOaf3ohNB5dGQczpFLMXoR";
	public static Map<String, String> getLatitude(String address) {
		try {
			address = URLEncoder.encode(address, "UTF-8");          //将地址转换成utf-8的16进制
	        URL resjson = new URL("http://api.map.baidu.com/geocoder/v2/?address=" + address +"&output=json&ak="+ AK); 
	        BufferedReader in = new BufferedReader(new InputStreamReader(resjson.openStream()));
	        String res;
	        StringBuilder sb = new StringBuilder("");
	        while ((res = in.readLine()) != null) {
	        	sb.append(res.trim());
	        }
	        in.close();
	        String str = sb.toString();
	        Map<String,String> map = null;
	        if (str!=null) {
	        	int lngStart = str.indexOf("lng\":");
	        	int lngEnd = str.indexOf(",\"lat");
	        	int latEnd = str.indexOf("},\"precise");
	        	if( lngStart > 0 && lngEnd > 0 && latEnd > 0) {
	        		 String lng = str.substring(lngStart+5, lngEnd);
	        		 String lat = str.substring(lngEnd+7, latEnd);
	        		 map = new HashMap<String,String>();
	        		 map.put("lng", lng);
	        		 map.put("lat", lat);
	        		 return map;
		        }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
