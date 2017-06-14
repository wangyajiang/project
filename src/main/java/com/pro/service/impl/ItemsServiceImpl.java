package com.pro.service.impl;

import java.util.List;
 import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pro.service.IItemsService;
import com.pro.dao.IItemsDao;
import com.pro.entity.Items;
import com.tool.bean.Paging;
import com.tool.exception.ServiceException;

public class ItemsServiceImpl implements IItemsService {

	@Autowired
	private IItemsDao itemsDao;

	public List<Items> queryItemsListByConditions(Map<String, Object> param) throws ServiceException {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		return itemsDao.queryByConditions(param);
	}

	public Paging<Items> queryItemsPaging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		Paging<Items> paging = new Paging<Items>();
		paging.setPage(page);
		paging.setPageSize(pageSize);
		paging.setTota(itemsDao.queryCount(param));
		if (paging.getTotal() > 0L) {
			paging.setList(itemsDao.queryList(param, page, pageSize, sort, desc));
		}
		return paging;
	}

}
