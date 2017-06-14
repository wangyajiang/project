package com.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.tool.comm.BaseUtils;
import com.tool.constant.EnumConfig;
import com.tool.utils.BaiDuMapUtils;
import com.tool.utils.ConvertUtils;
import com.tool.utils.DBHelper;
import com.tool.utils.DTUtils;

/**
 * 51服务器插入测试数据
 * @author Administrator
 *
 */
public class TestData {
	private static final String String = null;
	public static final String xing = "李王张刘陈杨赵黄周吴徐孙胡朱高林何郭马罗梁宋郑谢韩唐冯于董萧程曹袁邓许傅沈曾彭吕苏卢蒋蔡贾丁魏薛叶阎余潘杜戴夏钟汪田任姜范方石姚谭廖邹熊金陆郝孔白崔康毛邱秦江史顾侯邵孟龙万段漕钱汤尹黎易常武乔贺赖龚文庞樊兰殷施陶洪翟安颜倪严牛温芦季俞章鲁葛伍韦申尤毕聂丛焦向柳邢路岳齐沿梅莫庄辛管祝左涂谷祁时舒耿牟卜路詹关苗凌费纪靳盛童欧甄项曲成游阳裴席卫查屈鲍位覃霍翁隋植甘景薄单包司柏宁柯阮桂闵欧解强柴华车冉房边辜吉饶刁瞿戚丘古米池滕晋苑邬臧畅宫来嵺苟全褚廉简娄盖符奚木穆党燕郎邸冀谈姬屠连郜晏栾郁商蒙计喻揭窦迟宇敖糜鄢冷卓花仇艾蓝都巩稽井练仲乐虞卞封竺冼原官衣楚佟栗匡宗应台巫鞠僧桑荆谌银扬明沙薄伏岑习胥保和蔺";
	public static final String ming = "盼灿亚旭江展梅萍元雪晓莉明燕敏俊飞文斌卫国伟九庆以莲留强晓敏汝彬台铭帆伟东明骏世鹏仕辉丹慧乔昆仑万里雪中锋绍雄秋萍丽娟发前云霞自伟依林旭虎玮柏组红心林鹏海涛醒绍平玉洁方雄志伟双丽明汉琴珍美明君海英涛文武艳艳小刚霞高强浩志豪军林小敏叶东自富喜喜少云忠义小燕自坤静雯文建石开勇海林轲仕茂秀莲仁贵远青开凤玲伟军存瑞明浩文昌大伟岸英德琴光国佑军盛露志伟少青祖美发祥钟森永发运瑞丽敏自珍家宝建联迪慧章盛锦素素巨建生平明霞孟刚丽娟海峰松伟秋林军少中明琴枝山晓岚为军瑞云昭美永丽玉红淇喧喧克军明静军太佐凤萍羽小亚伟启林仕雁";
	public static DBHelper db = null;
	
	public static List<Map<String, Object>> orgList = null;
	public static Map<Integer, List<Map<String, Object>>> orgThreeParam = new HashMap<Integer, List<Map<String, Object>>>();
	public static List<List<Map<String, Object>>> orgThreeList = new ArrayList<List<Map<String, Object>>>();
	public static List<String> prifxx = new ArrayList<String>();
	public static List<String> sufixx = new ArrayList<String>();
	
	public static List<Integer> industryIds = new ArrayList<Integer>();
	
	public static Random random = new Random();
	
	
	private TestData() {}
	
	public static void newInstance() {
		BaseUtils.newInstance();
		if (db == null) {
			db = new DBHelper(EnumConfig.DB_NAME.GOLD);
		}
		prifxx.add("");
		prifxx.add("浙江");
		prifxx.add("湖州");
		sufixx.add("");
		sufixx.add("企业");
		sufixx.add("公司");
		try {
			System.out.println("初始化>>>>>>>>>>>>>");
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ");
			sql.append("CONCAT(org1.name,org2.name,org3.name,org4.name,org.name) address,SUBSTR(org.name, 1, 2) companyName,");
			sql.append("org1.id province_id,org2.id city_id,org3.id region_id,org4.id street_id,org.id community_id ");
			sql.append("FROM organization org ");
			sql.append("LEFT JOIN organization org1 on org1.level = 1 ");
			sql.append("LEFT JOIN organization org2 on org2.level = 2 and org2.parent_id = org1.id ");
			sql.append("LEFT JOIN organization org3 on org3.level = 3 and org3.parent_id = org2.id ");
			sql.append("LEFT JOIN organization org4 on org4.level = 4 and org4.parent_id = org3.id ");
			sql.append("where org.level = 5 and org.parent_id = org4.id and org2.id = 104 and org3.id <> 158");
			orgList = db.queryListBySelf(Map.class, sql.toString(), null, "address", "companyName", "province_id", "city_id", "region_id", "street_id", "community_id");
			if (orgList == null || orgList.isEmpty()) {
				System.out.println("区域数据>>>>>>>>>：0条！");
				return;
			}
			int nun = 0; 
			for (Map<String, Object> item : orgList) {
				Integer regionId = ConvertUtils.getInt(item.get("region_id"), 0);
				if (regionId == null || regionId == 0) {
					continue;
				}
				if (!orgThreeParam.containsKey(regionId)) {
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					orgThreeParam.put(regionId, list);
				}
				orgThreeParam.get(regionId).add(item);
				nun ++;
			}
			for (Integer regionId : orgThreeParam.keySet()) {
				orgThreeList.add(orgThreeParam.get(regionId));
			}
			System.out.println("区域数据>>>>>>>>>：" + orgList.size() + "条！\t级数据：" + nun + "\t" + orgThreeList.size());
			List<Map<String, Object>> l = db.queryList(Map.class, "select id from industry", null);
			for (Map<String, Object> param : l) {
				industryIds.add(Integer.parseInt(String.valueOf(param.get("id"))));
			}
			System.out.println("行业>>>>>>>>>：" + industryIds.size() + "条！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<Map<String, Object>> getLevelOrgList() {
		int size = orgThreeList.size();
		int index = random.nextInt(size);
		return orgThreeList.get(index);
	}
	
	public static Map<String, Object> getOrg(List<Map<String, Object>> list) {
		int size = list.size();
		int index = random.nextInt(size);
		return list.get(index);
	}
	
	public static String getPrifxx() {
		
		return null;
	}
	
	public static String getCompanyName(String middel) {
		StringBuilder name = new StringBuilder();
		name.append(prifxx.get(random.nextInt(prifxx.size())));
		name.append(middel);
		name.append(sufixx.get(random.nextInt(sufixx.size())));
		return name.toString();
	}
	
	public static int getNatures() {
		return random.nextInt(11) + 1;
	}
	
	public static Integer getindustryId() {
		return industryIds.get(random.nextInt(industryIds.size()));
	}
	
	public static boolean isBig() {
		if (random.nextInt(100) < 80) {
			return true;
		}
		return false;
	}
	
	public static int getXing() {
		int len = xing.length();
		int n = random.nextInt(len);
		double p = n / (double)len;
		if (p < 0.7) {
			if (isBig()) {
				if (isBig()) {
					return random.nextInt(10);
				} else {
					if (isBig()) {
						return random.nextInt(30);
					} else {
						if (isBig()) {
							return random.nextInt(50);
						} else {
							return random.nextInt(70);
						}
					}
				}
			} else {
				if (isBig()) {
					return random.nextInt(100);
				} else {
					if (isBig()) {
						return random.nextInt(150);
					} else {
						if (isBig()) {
							return random.nextInt(200);
						}
					}
				}
			}
			
		}
		return random.nextInt(len);
	}
	
	public static String getName() {
		StringBuilder name = new StringBuilder();
		name.append(xing.charAt(getXing()));
		name.append(ming.charAt(random.nextInt(ming.length())));
		if (isBig()) {
			name.append(ming.charAt(random.nextInt(ming.length())));
		}
		return name.toString();
	}
	public static String getPhone() {
		StringBuilder name = new StringBuilder("1");
		int n = random.nextInt(4);
		if (n == 0) {
			name.append("3");
		} else if (n == 1) {
			if (isBig()) {
				name.append("3");
			} else {
				name.append("4");
			}
		} else if (n == 2) {
			name.append("5");
		} else if (n == 3) {
			name.append("8");
		}
		for (int i = 0; i < 8; i ++) {
			name.append(random.nextInt(10));
		}
		name.append(random.nextInt(5) + 4);
		return name.toString();
	}
	public static Date getCreateTiem() {
		Date date = DTUtils.getZeroDate(DTUtils.getNowDate());
		date = DTUtils.addDay(date, - random.nextInt(30) - 1);
		return DTUtils.getDate(date.getTime() + random.nextInt(86400) * 1000L);
	}
	
	public static void insertCompany() {
		Integer startId = 43641;
		int dataLen = 4891 - 3020;
		String sql = "insert into company (id,company_name,province_id,city_id,region_id,street_id,community_id, natures,address,industry_id,legal_person,phone,distribution_box_num,device_num,company_type,create_time, lat, lng) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		List<Object[]> dataList = new ArrayList<Object[]>();
		int index = 0;
		for (int i = 0; i < dataLen; i ++) {
			System.out.println("正在初始化第 " + (i + 1) + " 条数据，剩余：" + (dataLen - i - 1));
			startId ++;
			Object[] obj = new Object[18];
			Map<String, Object> item = getOrg(getLevelOrgList());
			Map<String, String> map = BaiDuMapUtils.getLatitude(item.get("address").toString());
			index = 0;
			obj[index ++] = startId;
			obj[index ++] = getCompanyName(item.get("companyName").toString());
			obj[index ++] = item.get("province_id");
			obj[index ++] = item.get("city_id");
			obj[index ++] = item.get("region_id");
			obj[index ++] = item.get("street_id");
			obj[index ++] = item.get("community_id");
			obj[index ++] = getNatures();
			obj[index ++] = item.get("address");
			obj[index ++] = getindustryId();
			obj[index ++] = getName();
			obj[index ++] = getPhone();
			obj[index ++] = random.nextInt(10) + 1;//distribution_box_num
			obj[index ++] = random.nextInt(10) + 1;//device_num
			obj[index ++] = random.nextInt(3);//company_type
			obj[index ++] = getCreateTiem();//create_time
			obj[index ++] = map.get("lat");
			obj[index ++] = map.get("lng");
			dataList.add(obj);
		}
		System.out.println("数据初始化完毕！");
		System.out.println("需要插入的数据量：" + dataList.size());
//		try {
//			db.executeBatch(sql, dataList);
//			System.out.println("新增成功！");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	
	public static void main(String[] args) {
		newInstance();
		insertCompany();
//		for (int i = 0; i < 10000; i++) {
//			System.out.print(xing.charAt(getXing()));
//			if (i % 100 == 0) {
//				System.out.println();
//			}
//		}
		
	}

}
