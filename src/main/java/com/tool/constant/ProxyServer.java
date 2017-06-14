package com.tool.constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProxyServer {
	private static List<String> proxyList = new ArrayList<String>(); 
	
	static {
		proxyList.add("211.155.234.99 80");
		proxyList.add("218.75.100.114 8080");
		proxyList.add("211.167.248.228 8080");
		proxyList.add("60.12.227.208 80");
		proxyList.add("221.8.9.6 80");
		proxyList.add("218.26.219.186 8080");
		proxyList.add("222.68.207.11 80");
		proxyList.add("61.53.137.50 8080");
		proxyList.add("218.75.75.133 8080");
		proxyList.add("221.204.246.116 3128");
		proxyList.add("125.39.129.67 80");
		proxyList.add("220.194.55.244 3128");
		proxyList.add("125.70.229.30 8080");
		proxyList.add("220.194.55.160 3128");
		proxyList.add("202.98.11.101 8080");
		proxyList.add("59.76.81.3 8080");
		proxyList.add("121.11.87.171 80");
		proxyList.add("121.9.221.188 80");
		proxyList.add("221.195.40.145 80");
		proxyList.add("219.132.142.10 8080");
		proxyList.add("61.178.63.197 3128");
		proxyList.add("221.130.202.206 80");
		proxyList.add("203.171.230.230 80");
		proxyList.add("221.226.3.141 3128");
		proxyList.add("210.74.130.34 8080");
		proxyList.add("60.28.196.27 80");
	}
	
	public static String getRandomProxyServer() {
		int size = proxyList.size();
		Random random = new Random();
		return proxyList.get(random.nextInt(size));
		
	}
	
	public static void main(String[] args) {
		String s = getRandomProxyServer();
		System.out.println(s);
	}

}
