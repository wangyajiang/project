package com.lnglat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.tool.comm.BaseUtils;
import com.tool.constant.EnumConfig;
import com.tool.utils.BaiDuMapUtils;
import com.tool.utils.ConvertUtils;
import com.tool.utils.DBHelper;

public class SetLngAndLat {

	public void set() {
		DBHelper db  = new DBHelper();
		StringBuilder sql = new StringBuilder("SELECT c.id,CONCAT(p.name, ci.name, r.name, s.name, co.name, c.address) address ");
		sql.append("FROM company c ");
		sql.append("LEFT JOIN organization p on c.province_id = p.id ");
		sql.append("LEFT JOIN organization ci on c.city_id = ci.id ");
		sql.append("LEFT JOIN organization r on c.region_id = r.id ");
		sql.append("LEFT JOIN organization s on c.street_id = s.id ");
		sql.append("LEFT JOIN organization co on c.community_id = co.id ");
		List<Map<String, Object>> list = null;
		try {
			list = db.queryList(Map.class, sql.toString(), null);
			List<Object[]> params = new ArrayList<Object[]>();
			int len = list.size();
			for (Map<String, Object> param : list) {
				Object[] obj = new Object[3];
				Integer id = ConvertUtils.formatToInt(param.get("id"));
				String address = ConvertUtils.formatToStr(param.get("address"));
				Map<String, String> lngAndLat = BaiDuMapUtils.getLatitude(address); 
				obj[0] = lngAndLat.get("lng");
				obj[1] = lngAndLat.get("lat");
				obj[2] = id;
				len --;
				System.out.println("剩余：" + len + "\t" + Arrays.toString(obj));
				params.add(obj);
				if (params.size() % 100 == 0 && params.size() >0) {
					db.executeBatch("update company set lng = ?, lat = ? where id = ?", params);
					params.clear();
				}
			}
			db.executeBatch("update company set lng = ?, lat = ? where id = ?", params);
			System.out.println("success : " + params.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			db.close();
		}
		
		
				
	}
	
	public static void main(String[] args) {
		BaseUtils.newInstance();
		new SetLngAndLat().set();
	}
}
