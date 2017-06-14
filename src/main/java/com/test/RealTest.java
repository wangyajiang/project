package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import com.tool.utils.CheckUtils;
import com.tool.utils.ConvertUtils;
import com.tool.utils.FileUtils;

public class RealTest {

	public String readyLine() {
		String str = null;
		String path = "F://1/";
		try {
			BufferedReader br = FileUtils.getBufferedReader(path + "112827.txt", "gbk");
			File tempFile = new File(path + "_temp.txt");
			long index = 1;
			if (!tempFile.exists()) {
				index = 1;
			} else {
				BufferedReader tempBr = FileUtils.getBufferedReader(path + "_temp.txt", "utf-8");
				index = ConvertUtils.getLong(tempBr.readLine(), 1L);
				FileUtils.close(tempBr);
			}
			for (long i = 0; i < index - 1; i ++) {
				br.readLine();
			}
			while (true) {
				index ++;
				str = br.readLine();
				if (CheckUtils.isNotBlank(str)) {
					break;
				}
			}
			FileUtils.close(br);
			BufferedWriter bw = FileUtils.getBufferedWriter(path + "_temp.txt", "utf-8");
			FileUtils.writeLine(bw, ConvertUtils.formatToStr(index));
			FileUtils.close(bw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	} 
	
	public static void main(String[] args) {
		
	}

}
