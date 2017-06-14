package com.tool.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * sql过滤器 第一部分为保留关键词，关键词必须与空格组合才生效，如无空格可以认为为有, 第二部分为保留符号
 * 请全部通过spring注入方式，如果不方便注入的，也请对参数进行严格的过滤
 * @author wyj
 */
public class SqlFilter {
	private static String[] injs_words = ("alter ,create ,truncate ,drop ,add ,lock ,insert ,update ,delete ,select ,grant ,exec ,use ,chr ,mid ,master ,optimize ,show ,analyze ,"
			+ " column , table , database , index , trigger , count , order , group , and , or , from , where , like , union , global, exists , if , not ,updatexml,extractvalue")
			.split(",");

	private static String[] injs_syms = ("',`,;,--,%,|,*").split(",");

	public static String filter(String str) {
		if(StringUtils.isEmpty(str)){
			return str;
		}
		for (String word : injs_words) {
			Pattern p = Pattern.compile(word, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(str);
			str = m.replaceAll(" ");
		}

		for (String sym : injs_syms) {
			str = str.replace(sym, " ");
		}

		return str;
	}

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		for (String word : injs_words) {
			sb.append(word);
		}

		for (String sym : injs_syms) {
			sb.append(sym);
		}
		System.out.println(sb.toString());
		System.out.println(filter(sb.toString()));

		String name = filter("';drop table abc;--");
		String sql = "select * from abc where name ='" + name + "'";
		System.out.println(sql);
	}
}
