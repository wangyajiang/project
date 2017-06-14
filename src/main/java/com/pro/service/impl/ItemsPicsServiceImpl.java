package com.pro.service.impl;

import java.util.List;
 import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pro.service.IItemsPicsService;
import com.pro.dao.IItemsPicsDao;
import com.pro.entity.ItemsPics;
import com.tool.bean.Paging;
import com.tool.exception.ServiceException;

public class ItemsPicsServiceImpl implements IItemsPicsService {

	@Autowired
	private IItemsPicsDao itemsPicsDao;

	public List<ItemsPics> queryItemsPicsListByConditions(Map<String, Object> param) throws ServiceException {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		return itemsPicsDao.queryByConditions(param);
	}

	public Paging<ItemsPics> queryItemsPicsPaging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		Paging<ItemsPics> paging = new Paging<ItemsPics>();
		paging.setPage(page);
		paging.setPageSize(pageSize);
		paging.setTota(itemsPicsDao.queryCount(param));
		if (paging.getTotal() > 0L) {
			paging.setList(itemsPicsDao.queryList(param, page, pageSize, sort, desc));
		}
		return paging;
	}

}
