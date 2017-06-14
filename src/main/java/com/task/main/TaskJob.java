package com.task.main;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.task.domain.TableRule;
import com.task.service.TaskService;
import com.tool.comm.BaseUtils;
import com.tool.utils.DTUtils;


public class TaskJob {
	public static final ConcurrentLinkedQueue<TableRule> queue = new ConcurrentLinkedQueue<TableRule>();
	public static ExecutorService fixedThreadPool = null;
	
    public static void main(String[] args) {
    	BaseUtils.newInstance();
    	String[] sourceTableNames = BaseUtils.getVal("source.tableNames").split("\\|");
    	String[] sourceColumns = BaseUtils.getVal("source.columns").split("\\|");
    	String[] goldTableNames = BaseUtils.getVal("gold.tableNames").split("\\|");
    	String[] goldColumns = BaseUtils.getVal("gold.columns").split("\\|");
//    	String[] formats = BaseUtils.getVal("formats").split("\\|");
    	if (sourceTableNames.length != sourceColumns.length) {
    		System.out.println("规则错误");
    		return;
    	}
    	int len = sourceTableNames.length;
    	for (int i = 0; i < len; i ++) {
    		TableRule item = new TableRule();
    		item.setSourceTableName(sourceTableNames[i]);
    		item.setSourceColumns(sourceColumns[i]);
    		item.setGoldTableName(goldTableNames[i]);
    		item.setGoldColumns(goldColumns[i]);
//    		item.setFormats(formats[i]);
    		
    		queue.add(item);
    	}
        try {
        	System.out.println(DTUtils.getNow() + "：=============系统启动=============");
        	fixedThreadPool = Executors.newFixedThreadPool(1);
        	for (int i = 0; i < len; i ++) {
        		fixedThreadPool.execute(new Thread(new TaskService(queue.poll())));
        	}
        	fixedThreadPool.shutdown();
        }  catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
}
