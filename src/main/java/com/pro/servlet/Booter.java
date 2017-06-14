package com.pro.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.pro.common.Contents;
import com.pro.entity.ItemType;
import com.tool.cache.interceptor.MethodCacheInterceptor;
import com.tool.comm.BaseUtils;
import com.tool.utils.DBHelper;


public class Booter extends HttpServlet {
	
	private static final long serialVersionUID = 1L; 
	 
    public void init(ServletConfig config) {
    	System.out.println("================>[Servlet]自动加载启动开始.");  
    	try {  
           super.init();
           BaseUtils.newInstance();//读取配置文件
//           LoadPropertyConfigurer.newInstance();
           initItemType();
           System.out.println(">>>>>>[初始化数据库连接成功！]");
        } catch (ServletException e) {  
           e.printStackTrace();  
        }
    	System.out.println(MethodCacheInterceptor.cache);
        System.out.println("================>[Servlet]自动加载启动结束.");  
    }
    
    private static void initItemType() {
    	DBHelper db = new DBHelper();
    	try {
    		Contents.itemTypes = db.queryList(ItemType.class, "select * from item_type order by sort asc", null);
		} catch (Exception e) {
			 System.out.println("================>初始化参数失败！");  
			e.printStackTrace();
		} finally {
			db.close();
		}
    }
}
