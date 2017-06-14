
package com.pachong;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import com.tool.comm.QuartzManager;
import com.tool.constant.Constants;
import com.tool.utils.DTUtils;

public class MyJob implements Job {
	public static final ConcurrentLinkedQueue<Map<String, String>> queue = new ConcurrentLinkedQueue<Map<String, String>>();
	public static ExecutorService fixedThreadPool = null;
	
	public static void initQueue() {
		List<String> list = new ArrayList<String>();
		list.add("11 北京市");
		list.add("12 天津市");
		list.add("13 河北省");
		list.add("14 山西省");
		
//		list.add("15 内蒙古自治区");
//		list.add("21 辽宁省");
//		list.add("22 吉林省");
//		list.add("23 黑龙江省");
//		list.add("31 上海市");
//		list.add("32 江苏省");
//		list.add("33 浙江省");
//		list.add("34 安徽省");
//		list.add("35 福建省");
//		list.add("36 江西省");
//		list.add("37 山东省");
//		list.add("41 河南省");
//		list.add("42 湖北省");
//		list.add("43 湖南省");
//		list.add("44 广东省");
//		list.add("45 广西壮族自治区");
//		list.add("46 海南省");
//		list.add("50 重庆市");
//		list.add("51 四川省");
//		list.add("52 贵州省");
//		list.add("53 云南省");
//		list.add("54 西藏自治区");
//		list.add("61 陕西省");
//		list.add("62 甘肃省");
//		list.add("63 青海省");
//		list.add("64 宁夏回族自治区");
//		list.add("65 新疆维吾尔自治区");
		for (String str : list) {
			Map<String, String> param = new HashMap<String, String>();
			param.put("url", "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/" + str.split(" ")[0] + ".html");
			param.put("name", str.split(" ")[1]);
			queue.add(param);
		}
		
	}
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		 try {
         	System.out.println(DTUtils.getNow() + "：=============run=============");
        	fixedThreadPool = Executors.newFixedThreadPool(4);
        	int len = queue.size();
        	len = len > 4 ? 4 : len;
        	for (int i = 0; i < len; i ++) {
        		Map<String, String> param = queue.poll();
        		Thread thread = new Thread(new PaChongTaskRun(param.get("url"), param.get("name")));
        		thread.setName(thread.getName() + "【" + param.get("name") + "】");
        		Thread.sleep(200);
        		fixedThreadPool.execute(thread);
        	}
        	if (queue.isEmpty()) {
        		QuartzManager.removeJob(Constants.JOB_NAME);
        	}
        	fixedThreadPool.shutdown();
         }  catch (Exception e) {  
            e.printStackTrace();  
         }  
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		initQueue();
		MyJob job = new MyJob();
		try {
			System.out.println(DTUtils.getNow() + "：=============系统启动=============");
			QuartzManager.addJob(Constants.JOB_NAME, job, "0 44 * * * ?");
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
}
