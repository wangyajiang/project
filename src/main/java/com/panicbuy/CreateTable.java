package com.panicbuy;
/**
 * 
 */

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.tool.comm.BaseUtils;
import com.tool.constant.EnumConfig;
import com.tool.utils.ConvertUtils;
import com.tool.utils.DBHelper;
import com.tool.utils.DTUtils;
import com.tool.utils.FileUtils;

public class CreateTable {
	private CreateTable(){}
	public static DBHelper db = null;
	private static Map<String, Boolean> tabs = new HashMap<String, Boolean>();
	public static Random random = new Random();
	
	public  static void newInstance() {
		if (db == null) {
			BaseUtils.newInstance();
			db = new DBHelper(EnumConfig.DB_NAME.PANICBUY);
		}
		try {
			List<Map<String, String>> list = db.queryList(Map.class, "show tables", null);
			if (list != null && !list.isEmpty()) {
				for (Map<String, String> param : list) {
					for (String key : param.keySet()) {
						tabs.put(param.get(key), true);
					}
				}
			}
		} catch (Exception e) {}
	}
	
	/**
	 * 焦点图
	 * 表名称：focus_map
	 */
	public static void createFocusMap() {
		String tabName = "focus_map";
		if (tabs.containsKey(tabName)) {
			System.out.println("【焦点图】已经存在！");
			return;
		}
		StringBuilder sql = new StringBuilder("create table if not exists ");
		sql.append(tabName);
		sql.append("(");
		sql.append("id INT(3) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '焦点图唯一标识',");
		sql.append("name varchar(100) DEFAULT NULL COMMENT '名称',");
		sql.append("url varchar(300) DEFAULT NULL COMMENT '触发',");
		sql.append("status int(1) NOT NULL DEFAULT '0' COMMENT '0：正常，1：关闭',");
		sql.append("sort int(3) DEFAULT '0' COMMENT '排序',");
		sql.append("type int(1) NOT NULL DEFAULT '0' COMMENT '0：外部连接，1：内部函数，3：无连接',");
		sql.append("create_time datetime NOT NULL");
		sql.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='焦点图';");
		System.out.print(">>>>>>>>>>>:");
		try {
			db.executeSingle(sql.toString(), new Object[]{});
			System.out.println("【焦点图】创建成功！");
		} catch (Exception e) {
			System.out.println("【焦点图】创建失败！");
		}
	}
	/**
	 * 商品
	 * 表名称：items
	 */
	public static void createItems() {
		String tabName = "items";
		if (tabs.containsKey(tabName)) {
			System.out.println("【商品】已经存在！");
			return;
		}
		StringBuilder sql = new StringBuilder("create table if not exists ");
		sql.append(tabName);
		sql.append("(");
		sql.append("item_id INT(16) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '商品唯一标识',");
		sql.append("user_id int(11) DEFAULT NULL COMMENT '用户唯一标识',");
		sql.append("title varchar(60) NOT NULL COMMENT '商品名称',");
		sql.append("pic_url varchar(300) NOT NULL COMMENT '商品主图',");
		sql.append("price decimal(10,2) NOT NULL DEFAULT '0' COMMENT '商品价格',");
		sql.append("status int(1) NOT NULL DEFAULT '0' COMMENT '商品状态，0正常，1关闭',");
		sql.append("type_id int(11) NOT NULL COMMENT '商品类型',");
		sql.append("create_time datetime NOT NULL COMMENT '创建时间'");
		sql.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='商品';");
		System.out.print(">>>>>>>>>>>:");
		try {
			db.executeSingle(sql.toString(), new Object[]{});
			System.out.println("【商品】创建成功！");
		} catch (Exception e) {
			System.out.println("【商品】创建失败！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 商品图片列表（焦点图）
	 * 表名称：items_pics
	 */
	public static void createItemsPics() {
		String tabName = "items_pics";
		if (tabs.containsKey(tabName)) {
			System.out.println("【 商品图片列表（焦点图）】已经存在！");
			return;
		}
		StringBuilder sql = new StringBuilder("create table if not exists ");
		sql.append(tabName);
		sql.append("(");
		sql.append("id INT(18) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '商品图片列表唯一标识',");
		sql.append("item_id INT(16) NOT NULL COMMENT '商品一标识',");
		sql.append("pic_url varchar(300) NOT NULL COMMENT '商品图片地址',");
		sql.append("sort int(2) NOT NULL DEFAULT '0' COMMENT '排序'");
		sql.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='商品图片列表';");
		System.out.print(">>>>>>>>>>>:");
		try {
			db.executeSingle(sql.toString(), new Object[]{});
			System.out.println("【商品活动图片列表（焦点图）】创建成功！");
		} catch (Exception e) {
			System.out.println("【商品活动图片列表（焦点图）】创建失败！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 商品类型
	 * 表名称：item_type
	 */
	public static void createItemsType() {
		String tabName = "item_type";
		if (tabs.containsKey(tabName)) {
			System.out.println("【 商品类型】已经存在！");
			return;
		}
		StringBuilder sql = new StringBuilder("create table if not exists ");
		sql.append(tabName);
		sql.append("(");
		sql.append("type_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '商品类型唯一标识',");
		sql.append("type_name varchar(20) DEFAULT NULL COMMENT '商品类型名称',");
		sql.append("sort int(5) NOT NULL DEFAULT '0' COMMENT '排序'");
		sql.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='商品类型';");
		System.out.print(">>>>>>>>>>>:");
		try {
			db.executeSingle(sql.toString(), new Object[]{});
			System.out.println("【商品类型】创建成功！");
		} catch (Exception e) {
			System.out.println("【商品类型】创建失败！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 商品活动
	 * 表名称：items_active
	 */
	public static void createItemsActive() {
		String tabName = "items_active";
		if (tabs.containsKey(tabName)) {
			System.out.println("【 商品活动】已经存在！");
			return;
		}
		StringBuilder sql = new StringBuilder("create table if not exists ");
		sql.append(tabName);
		sql.append("(");
		sql.append("active_id INT(16) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '活动唯一标识',");
		sql.append("item_id INT(16) NOT NULL COMMENT '商品唯一标识',");
		sql.append("active_name varchar(60) DEFAULT NULL COMMENT '活动名称',");
		sql.append("type_id int(11) NOT NULL COMMENT '商品类型',");
		sql.append("item_pic_url varchar(300) DEFAULT NULL COMMENT '活动商品主图',");
		sql.append("price decimal(10,2) NOT NULL DEFAULT '0' COMMENT '活动价格(元)',");
		sql.append("amnt decimal(10,2) NOT NULL DEFAULT '1' COMMENT '活动每份价格(元)',");
		sql.append("total_num int(11) NOT NULL DEFAULT '1' COMMENT '活动总份数',");
		sql.append("current_num int(11) NOT NULL DEFAULT '1' COMMENT '当前参与份数',");
		sql.append("surplus_num int(11) NOT NULL DEFAULT '1' COMMENT '剩余份数',");
		sql.append("status int(11) NOT NULL DEFAULT '0' COMMENT '活动状态（0：活动未开启，1：关闭活动，2：活动等待开始，3：开启活动，4：活动进行中，5：活动已结束，6：公布结果）',");
		sql.append("start_time datetime DEFAULT NULL COMMENT '活动开始时间',");
		sql.append("publish_time datetime DEFAULT NULL COMMENT '公布时间',");
		sql.append("end_time datetime DEFAULT NULL COMMENT '活动结束时间',");
		sql.append("create_time datetime NOT NULL COMMENT '创建时间'");
		sql.append(") ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=utf8 COMMENT='商品活动';");
		System.out.print(">>>>>>>>>>>:");
		try {
			db.executeSingle(sql.toString(), new Object[]{});
			System.out.println("【商品活动】创建成功！");
		} catch (Exception e) {
			System.out.println("【商品活动】创建失败！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 商品活动图片列表（焦点图）
	 * 表名称：items_active_pics
	 */
	public static void createItemsActivePics() {
		String tabName = "items_active_pics";
		if (tabs.containsKey(tabName)) {
			System.out.println("【商品活动图片列表（焦点图）】已经存在！");
			return;
		}
		StringBuilder sql = new StringBuilder("create table if not exists ");
		sql.append(tabName);
		sql.append("(");
		sql.append("id INT(18) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '商品活动图片列表唯一标识',");
		sql.append("active_id INT(16) NOT NULL COMMENT '活动唯一标识',");
		sql.append("pic_url varchar(300) NOT NULL COMMENT '活动图片列表',");
		sql.append("sort int(2) NOT NULL DEFAULT '0' COMMENT '排序'");
		sql.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='商品活动图片列表';");
		System.out.print(">>>>>>>>>>>:");
		try {
			db.executeSingle(sql.toString(), new Object[]{});
			System.out.println("【商品活动图片列表（焦点图）】创建成功！");
		} catch (Exception e) {
			System.out.println("【商品活动图片列表（焦点图）】创建失败！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 活动订单（订单快照）
	 * 表名称：active_order
	 */
	public static void createActiveOrder() {
		String tabName = "active_order";
		if (tabs.containsKey(tabName)) {
			System.out.println("【活动订单（订单快照）】已经存在！");
			return;
		}
		StringBuilder sql = new StringBuilder("create table if not exists ");
		sql.append(tabName);
		sql.append("(");
		sql.append("order_id INT(18) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '订单唯一标识',");
		sql.append("user_id int(11) DEFAULT NULL COMMENT '用户唯一标识',");
		sql.append("active_id INT(16) NOT NULL COMMENT '活动唯一标识',");
		sql.append("item_id INT(16) NOT NULL COMMENT '商品唯一标识',");
		sql.append("active_name varchar(60) DEFAULT NULL COMMENT '活动名称',");
		sql.append("type_id int(11) NOT NULL COMMENT '商品类型',");
		sql.append("item_pic_url varchar(300) DEFAULT NULL COMMENT '活动商品主图',");
		sql.append("price decimal(10,2) NOT NULL DEFAULT '0' COMMENT '订单总额(元)',");
		sql.append("amnt decimal(10,2) NOT NULL DEFAULT '1' COMMENT '活动每份价格(元)',");
		sql.append("num int(11) NOT NULL DEFAULT '1' COMMENT '参与份数(参与次数)',");
		sql.append("status int(1) NOT NULL DEFAULT '0' COMMENT '订单状态（0：未支付，1：支付中，2：支付成功）',");
		sql.append("nickname varchar(15) DEFAULT NULL COMMENT '用户昵称',");
		sql.append("user_pic varchar(15) DEFAULT NULL COMMENT '用户头像',");
		sql.append("order_ip varchar(15) DEFAULT NULL COMMENT '用户订单IP',");
		sql.append("ip_name varchar(15) DEFAULT NULL COMMENT '用户订单IP地址',");
		sql.append("order_end_time datetime DEFAULT NULL COMMENT '订单结束时间',");
		sql.append("create_time datetime NOT NULL COMMENT '创建时间'");
		sql.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='活动订单';");
		System.out.print(">>>>>>>>>>>:");
		try {
			db.executeSingle(sql.toString(), new Object[]{});
			System.out.println("【活动订单（订单快照）】创建成功！");
		} catch (Exception e) {
			System.out.println("【活动订单（订单快照）】创建失败！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 活动账单
	 * 表名称：active_bill
	 */
	public static void createActiveBill() {
		String tabName = "active_bill";
		if (tabs.containsKey(tabName)) {
			System.out.println("【活动账单】已经存在！");
			return;
		}
		StringBuilder sql = new StringBuilder("create table if not exists ");
		sql.append(tabName);
		sql.append("(");
		sql.append("bill_id INT(18) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '账单唯一标识',");
		sql.append("order_id int(18) DEFAULT NULL COMMENT '订单唯一标识',");
		sql.append("user_id int(11) DEFAULT NULL COMMENT '用户唯一标识',");
		sql.append("active_name varchar(60) DEFAULT NULL COMMENT '活动名称',");
		sql.append("price decimal(10,2) NOT NULL DEFAULT '0' COMMENT '账单金额',");
		sql.append("amnt decimal(10,2) NOT NULL DEFAULT '1' COMMENT '活动每份价格(元)',");
		sql.append("num int(11) NOT NULL DEFAULT '1' COMMENT '参与份数(参与次数)',");
		sql.append("create_time datetime NOT NULL COMMENT '创建时间'");
		sql.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='活动账单';");
		System.out.print(">>>>>>>>>>>:");
		try {
			db.executeSingle(sql.toString(), new Object[]{});
			System.out.println("【活动账单】创建成功！");
		} catch (Exception e) {
			System.out.println("【活动账单】创建失败！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 用户信息表
	 * 表名称：user
	 */
	public static void createUser() {
		String tabName = "user";
		if (tabs.containsKey(tabName)) {
			System.out.println("【用户信息表】已经存在！");
			return;
		}
		StringBuilder sql = new StringBuilder("create table if not exists ");
		sql.append(tabName);
		sql.append("(");
		sql.append("user_id INT(15) PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT '用户唯一标识',");
		sql.append("nickname varchar(20) DEFAULT NULL COMMENT '昵称',");
		sql.append("pic_head varchar(300) DEFAULT NULL COMMENT '用户头像',");
		sql.append("mobile varchar(14) DEFAULT NULL COMMENT '手机',");
		sql.append("password varchar(30) DEFAULT NULL COMMENT '密码',");
		sql.append("real_name varchar(20) DEFAULT NULL COMMENT '用户真实姓名',");
		sql.append("ctno varchar(20) DEFAULT NULL COMMENT '身份证号',");
		sql.append("status int(1) NOT NULL DEFAULT '0' COMMENT '状态（0：正常，1：关闭）',");
		sql.append("province_id INT(15) DEFAULT NULL COMMENT '省',");
		sql.append("city_id INT(15) DEFAULT NULL COMMENT '市',");
		sql.append("county_id INT(15) DEFAULT NULL COMMENT '县/区',");
		sql.append("address varchar(200) DEFAULT NULL COMMENT '详细地址',");
		sql.append("create_time datetime NOT NULL COMMENT '创建时间'");
		sql.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户信息表';");
		System.out.print(">>>>>>>>>>>:");
		try {
			db.executeSingle(sql.toString(), new Object[]{});
			System.out.println("【用户信息表】创建成功！");
		} catch (Exception e) {
			System.out.println("【用户信息表】创建失败！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 用户积分表
	 * 表名称：user_point
	 */
	public static void createUserPoint() {
		String tabName = "user_point";
		if (tabs.containsKey(tabName)) {
			System.out.println("【用户信息表】已经存在！");
			return;
		}
		StringBuilder sql = new StringBuilder("create table if not exists ");
		sql.append(tabName);
		sql.append("(");
		sql.append("user_id INT(15) PRIMARY KEY NOT NULL COMMENT '用户唯一标识',");
		sql.append("point int(11) NOT NULL DEFAULT '0' COMMENT '可用积分',");
		sql.append("modify_time datetime NOT NULL COMMENT '修改时间',");
		sql.append("create_time datetime NOT NULL COMMENT '创建时间'");
		sql.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户积分表';");
		System.out.print(">>>>>>>>>>>:");
		try {
			db.executeSingle(sql.toString(), new Object[]{});
			System.out.println("【用户积分表】创建成功！");
		} catch (Exception e) {
			System.out.println("【用户积分表】创建失败！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 积分增减表
	 * 表名称：user_point_log
	 */
	public static void createUserPointLog() {
		String tabName = "user_point_log";
		if (tabs.containsKey(tabName)) {
			System.out.println("【用户信息表】已经存在！");
			return;
		}
		StringBuilder sql = new StringBuilder("create table if not exists ");
		sql.append(tabName);
		sql.append("(");
		sql.append("user_id INT(15) PRIMARY KEY NOT NULL COMMENT '用户唯一标识',");
		sql.append("name varchar(20) DEFAULT NULL COMMENT '积分获取或者消费名称',");
		sql.append("point int(11) NOT NULL DEFAULT '1' COMMENT '积分',");
		sql.append("status int(1) NOT NULL DEFAULT '0' COMMENT '0：获得积分，1：消费积分',");
		sql.append("create_time datetime NOT NULL COMMENT '创建时间'");
		sql.append(") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='积分增减表';");
		System.out.print(">>>>>>>>>>>:");
		try {
			db.executeSingle(sql.toString(), new Object[]{});
			System.out.println("【用户信息表】创建成功！");
		} catch (Exception e) {
			System.out.println("【用户信息表】创建失败！");
			e.printStackTrace();
		}
	}
	
	private static void setItemTypeDataList(List<Object[]> list, String name) {
		Object[] obj = new Object[3];
		obj[0] = list.size() + 1;
		obj[1] = name;
		obj[2] = list.size() + 1;
		list.add(obj);
	}
	public static void insertItemType() {
		String sql = "insert into item_type(type_id, type_name, sort) values (?, ?, ?)";
		List<Object[]> list = new ArrayList<Object[]>();
		setItemTypeDataList(list, "手机数码");
		setItemTypeDataList(list, "电脑办公");
		setItemTypeDataList(list, "家具家电");
		setItemTypeDataList(list, "个护化装");
		setItemTypeDataList(list, "豪华汽车");
		setItemTypeDataList(list, "箱包钟表");
		setItemTypeDataList(list, "运动户外");
		setItemTypeDataList(list, "母婴玩具");
		setItemTypeDataList(list, "食品酒类");
		setItemTypeDataList(list, "充值卡类");
		setItemTypeDataList(list, "其它商品");
		try {
			db.executeBatch(sql, list);
			System.out.println("商品类型======数据初始化成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	
	private static void setFocusMapDataList(List<Object[]> list, String name, String url) {
		Object[] obj = new Object[7];
		obj[0] = list.size() + 1;
		obj[1] = name;
		obj[2] = url;
		obj[3] = 0;
		obj[4] = list.size() + 1;
		obj[5] = 0;
		obj[6] = DTUtils.getNowDate();
		list.add(obj);
	}
	public static void insertIFocusMap() {
		String sql = "insert into focus_map(id, name, url, status, sort, type, create_time) values (?, ?, ?, ?, ?, ?, ?)";
		List<Object[]> list = new ArrayList<Object[]>();
		setFocusMapDataList(list, "景甜", "http://a.hiphotos.baidu.com/image/h%3D200/sign=6426f574dc88d43fefa996f24d1ed2aa/728da9773912b31b28749f288c18367adab4e193.jpg");
		setFocusMapDataList(list, "郑爽", "http://img0.imgtn.bdimg.com/it/u=703037305,2281337801&fm=23&gp=0.jpg");
		setFocusMapDataList(list, "热巴", "http://img3.imgtn.bdimg.com/it/u=603551034,3407470893&fm=23&gp=0.jpg");
		setFocusMapDataList(list, "杨幂", "http://img1.imgtn.bdimg.com/it/u=730885429,2207682282&fm=23&gp=0.jpg");
		setFocusMapDataList(list, "天外飞仙", "http://img3.imgtn.bdimg.com/it/u=457872269,1685321314&fm=23&gp=0.jpg");
		setFocusMapDataList(list, "刘涛", "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=739123753,2628538089&fm=23&gp=0.jpg");
		setFocusMapDataList(list, "刘亦菲", "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1584057823,2775876300&fm=23&gp=0.jpg");
		
		
		try {
			db.executeBatch(sql, list);
			System.out.println("焦点图======数据初始化成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	
	private static void setItemsDataList(List<Object[]> list, String title, String url, double price, int typeId) {
		if (title.length() > 60) {
			title = title.substring(0, 60);
		}
		Object[] obj = new Object[6];
		obj[0] = list.size() + 1;
		obj[1] = title;
		obj[2] = url;
		obj[3] = price;
		obj[4] = typeId;
		obj[5] = DTUtils.getNowDate();
		System.out.println(Arrays.toString(obj));
		list.add(obj);
	}
	public static void insertItems() {
		String sql = "insert into items(item_id, title, pic_url, price, type_id, create_time) values (?, ?, ?, ?, ?, ?)";
		List<Object[]> list = new ArrayList<Object[]>();
		String path = "D:/workspace/initdata/";
		String[] files = {"手机数码","电脑办公","家具家电","个护化装","豪华汽车","箱包钟表","运动户外","母婴玩具","食品酒类","充值卡类","其它商品"};
		try {
			BufferedReader br = null;
			String str = "";
			String[] arr = null;
			for (int i = 0; i < files.length; i ++) {
				br = FileUtils.getBufferedReader(path + files[i] + ".txt", null);
				System.out.println();
				System.out.println("==========" + files[i] + "==========");
				while ((str = br.readLine()) != null) {
					if (str.trim().compareTo("") == 0) {
						continue;
					}
					arr = str.split(",");
					System.out.println();
					setItemsDataList(list, arr[0], arr[1], Double.parseDouble(arr[2]), i + 1);
				}
				br.close();
			}
			db.executeBatch(sql, list);
			System.out.println("商品======数据初始化成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	
	private static void setItemActiveDataList(List<Object[]> list, Integer itemId, String title, String url, double price, int typeId) {
		int n = (int) Math.ceil(price);
		Object[] obj = new Object[13];
		obj[0] = list.size() + 1;
		obj[1] = itemId;
		obj[2] = title;
		obj[3] = typeId;
		obj[4] = url;
		obj[5] = n;
		obj[6] = 1;
		obj[7] = obj[5];
		obj[8] = random.nextInt(n);
		obj[9] = n - (int)obj[8];
		obj[10] = 4;
		obj[11] = DTUtils.addDay(DTUtils.getNowDate(), -random.nextInt(3) - 1);
		obj[12] = DTUtils.getNowDate();
		System.out.println(Arrays.toString(obj));
		list.add(obj);
	}
	public static void insertItemActive() {
		String sql = "insert into items_active(active_id, item_id, active_name, type_id, item_pic_url, price, amnt, total_num, current_num, surplus_num, status, start_time, create_time) values (?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
		List<Object[]> list = new ArrayList<Object[]>();
//		setItemActiveDataList(list, "小米 红米4X 高画质相机， 隐私双系统， 18天", "https://g-search3.alicdn.com/img/bao/uploaded/i4/TB1kttxPVXXXXbAXFXXXXXXXXXX.jpg_180x180.jpg", 683);
//		setItemActiveDataList(list, "华为 P10 徕卡人像摄影， 多彩外观设计 ， 3D", "https://g-search2.alicdn.com/img/bao/uploaded/i4/TB1vRiVPVXXXXbrXVXXXXXXXXXX.jpg_180x180.jpg", 3421);
//		setItemActiveDataList(list, "华为 P10 Plus 每一拍都是大片， 金属钻雕工艺", "https://g-search2.alicdn.com/img/bao/uploaded/i4/TB11euUPVXXXXc6XVXXXXXXXXXX.jpg_180x180.jpg", 3886);
//		setItemActiveDataList(list, "小米 小米手机6 1nit超暗夜光屏， 四轴光学防", "https://g-search2.alicdn.com/img/bao/uploaded/i4/TB1dn5CQVXXXXaTXpXXXXXXXXXX.jpg_180x180.jpg", 2636);
//		setItemActiveDataList(list, "锤子 坚果Pro 自定义来电语音， 智能语义拖拽", "https://g-search2.alicdn.com/img/bao/uploaded/i4/TB1R1NqRXXXXXXuaXXXXXXXXXXX.jpg_180x180.jpg", 1643);
//		setItemActiveDataList(list, "Samsung/三星 UA55KUC31SJXXZ 55吋屏超高清4K智能液晶曲面电视机", "https://g-search1.alicdn.com/img/bao/uploaded/i4/imgextra/i3/1544405013787059691/TB2kpuUfl0kpuFjSsppXXcGTXXa_!!0-saturn_solar.jpg_250x250.jpg", 4999);
//		setItemActiveDataList(list, "现货Samsung/三星 UA55KS8800JXXZ 55寸LED液晶电视4K曲面智能", "https://g-search3.alicdn.com/img/bao/uploaded/i4/i1/91131528/TB27nVTiYXlpuFjSszfXXcSGXXa_!!91131528.jpg_250x250.jpg", 6750);
//		setItemActiveDataList(list, "VR眼镜虚拟现实智能手机游戏影院头戴式全景头盔3D眼镜安卓iOS", "https://g-search2.alicdn.com/img/bao/uploaded/i4/i3/TB1dcy9RpXXXXXvXVXXXXXXXXXX_!!0-item_pic.jpg_250x250.jpg", 59);
//		setItemActiveDataList(list, "ugp智能vr虚拟现实3d眼镜 手机头戴式影院游戏机头盔一体机ar眼睛", "https://g-search3.alicdn.com/img/bao/uploaded/i4/i2/TB122B.QpXXXXaraXXXXXXXXXXX_!!0-item_pic.jpg_250x250.jpg", 46);
//		setItemActiveDataList(list, "VR眼镜虚拟现实3D影院智能手机游戏一体机BOX头戴式ar眼睛头盔", "https://g-search3.alicdn.com/img/bao/uploaded/i4/i2/TB1xo_sQFXXXXckXFXXXXXXXXXX_!!0-item_pic.jpg_250x250.jpg", 39.9);
//		setItemActiveDataList(list, "4代vr眼镜眼睛头戴式智能手机专用3d虚拟现实电影游戏一体机苹果", "https://g-search1.alicdn.com/img/bao/uploaded/i4/i1/TB1awK7QFXXXXatXVXXXXXXXXXX_!!0-item_pic.jpg_250x250.jpg", 39.9);
//		setItemActiveDataList(list, "正品阿尔郎 电动平衡车双轮儿童成人智能代步车两轮体感车漂移车", "https://g-search1.alicdn.com/img/bao/uploaded/i4/i3/TB1eZGvPXXXXXXYapXXXXXXXXXX_!!0-item_pic.jpg_250x250.jpg", 759);
//		setItemActiveDataList(list, "圣卡洛成人滑板车上班代步工具大轮两轮二轮可折叠城市校园代步车", "https://g-search1.alicdn.com/img/bao/uploaded/i4/i1/TB1jkGrQFXXXXcNaXXXXXXXXXXX_!!0-item_pic.jpg_250x250.jpg", 288);
		
		
		try {
			List<Map<String, Object>> items = db.queryList(Map.class, "select * from items", null);
			for (Map<String, Object> item : items) {
				setItemActiveDataList(list, ConvertUtils.getInt(item.get("item_id"), 0),ConvertUtils.getStr(item.get("title"), null), ConvertUtils.getStr(item.get("pic_url"), null), Double.parseDouble(ConvertUtils.getStr(item.get("price"), "0")), ConvertUtils.getInt(item.get("type_id"), 0));
			}
			
			
			db.executeBatch(sql, list);
			System.out.println("商品======数据初始化成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
	
	
	
	public static void main(String[] args) {
		newInstance();
		/**/
		//焦点图
		createFocusMap();
		//商品
		createItems();
		//商品图片列表（焦点图）
		createItemsPics();
		//商品类型
		createItemsType();
		//商品活动
		createItemsActive();
		//商品活动图片列表（焦点图）
		createItemsActivePics();
		//活动订单（订单快照）
		createActiveOrder();
		//活动账单
		createActiveBill();
		//用户信息表
		createUser();
		//用户积分表
		createUserPoint();
		//createUserPointLog
		createUserPointLog();
		
		//商品类型-----初始化
//		insertItemType();
//		insertIFocusMap();
//		insertItems();
//		insertItemActive();
	}
}
