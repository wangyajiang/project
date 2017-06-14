package com.pro.service.impl;

import java.util.List;
 import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pro.service.IItemsActivePicsService;
import com.pro.dao.IItemsActivePicsDao;
import com.pro.entity.ItemsActivePics;
import com.tool.bean.Paging;
import com.tool.exception.ServiceException;

public class ItemsActivePicsServiceImpl implements IItemsActivePicsService {

	@Autowired
	private IItemsActivePicsDao itemsActivePicsDao;

	public List<ItemsActivePics> queryItemsActivePicsListByConditions(Map<String, Object> param) throws ServiceException {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		return itemsActivePicsDao.queryByConditions(param);
	}

	public Paging<ItemsActivePics> queryItemsActivePicsPaging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		Paging<ItemsActivePics> paging = new Paging<ItemsActivePics>();
		paging.setPage(page);
		paging.setPageSize(pageSize);
		paging.setTota(itemsActivePicsDao.queryCount(param));
		if (paging.getTotal() > 0L) {
			paging.setList(itemsActivePicsDao.queryList(param, page, pageSize, sort, desc));
		}
		return paging;
	}

}
