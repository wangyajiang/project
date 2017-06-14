package com.pro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pro.dao.IFocusMapDao;
import com.pro.entity.FocusMap;
import com.pro.service.IFocusMapService;
import com.tool.bean.Paging;
import com.tool.constant.EnumConfig;
import com.tool.exception.ServiceException;
import com.tool.utils.ConvertUtils;

public class FocusMapServiceImpl implements IFocusMapService {

	@Autowired
	private IFocusMapDao focusMapDao;

	public List<FocusMap> queryFocusMapListByConditions(Map<String, Object> param) throws ServiceException {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		param.put("status", EnumConfig.STATUS.OK.getCode());
		String sort = null;
		boolean desc = false;
		int pageSize = 100;
		if (param.containsKey("sort")) {
			sort = param.get("sort").toString();
			param.remove("sort");
		}
		if (param.containsKey("desc")) {
			desc = ConvertUtils.formatToBoolean(param.get("desc"), false);
			param.remove("desc");
		}
		if (param.containsKey("pageSize")) {
			pageSize = ConvertUtils.getInt(param.get("pageSize"), pageSize);
			param.remove("pageSize");
		}
		return focusMapDao.queryList(param, 1, pageSize, sort, desc);
	}

	public Paging<FocusMap> queryFocusMapPaging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		param.put("status", EnumConfig.STATUS.OK.getCode());
		Paging<FocusMap> paging = new Paging<FocusMap>();
		paging.setPage(page);
		paging.setPageSize(pageSize);
		paging.setTota(focusMapDao.queryCount(param));
		if (paging.getTotal() > 0L) {
			paging.setList(focusMapDao.queryList(param, page, pageSize, sort, desc));
		}
		return paging;
	}

}
