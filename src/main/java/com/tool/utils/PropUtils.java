package com.tool.utils;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class PropUtils {
	
	private static ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
	
	public PropUtils(String ... propNames) {
		InputStream in = null;
		Properties pros = null;
		try {
			for (String fileName : propNames) {
				pros = new Properties();
				in = new BufferedInputStream (new FileInputStream(fileName));
				pros.load(in);
				for (Object key : pros.keySet()) {
					System.out.println(key + "\t" + String.valueOf(pros.get(key)));
					map.put(String.valueOf(key), String.valueOf(pros.get(key)));
				}
				in.close();
			}
			System.out.println("Properties文件成功加载");
		} catch (IOException e) {  
            e.printStackTrace();  
        }
	}
      
    public static String get(String key){  
        return map.get(key);
    }
    
    public static int getInt(String key, int num){  
        return null != map.get(key) ? Integer.parseInt(map.get(key)) : num;
    }
}
