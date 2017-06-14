package com.tool.utils;

import java.security.MessageDigest;

import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class AlgorithmUtils {
	
	public static String EncoderByMd5(String str) {
		try {
			MessageDigest md5=MessageDigest.getInstance("MD5");
			BASE64Encoder base64en = new BASE64Encoder();
			return base64en.encode(md5.digest(str.getBytes("utf-8")));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception {
		String key = "asdasdasd";
		System.out.println(EncoderByMd5(key));
		System.out.println(EncoderByMd5(key));
	}

}
