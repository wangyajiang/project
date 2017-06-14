package com.tool.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.pachong.MainRun;

public class FileUtils {

	public static BufferedWriter getBufferedWriter(String url) {
		return getBufferedWriter(url, "utf8");
	}
	
	public static BufferedWriter getBufferedWriter(String url, String chartset) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(url)), chartset));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bw;
	}
	
	public static void writeLine(BufferedWriter bw, String str) {
		try {
			bw.write(str);
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(BufferedWriter bw) {
		try {
			if (bw != null) {
				bw.close();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedReader getBufferedReader(String url, String chartset) {
		if (chartset == null) {
			chartset = "utf8";
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(url), chartset));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return br;
	}
	
	public static void close(BufferedReader br) {
		try {
			if (br != null) {
				br.close();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MainRun.getDocumentByProxy("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2013/13/08/21/130821215.html");
	}
}
