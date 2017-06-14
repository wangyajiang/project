package com.pro.service.impl;

import java.util.List;
 import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pro.service.IItemsActiveService;
import com.pro.dao.IItemsActiveDao;
import com.pro.entity.ItemsActive;
import com.tool.bean.Paging;
import com.tool.exception.ServiceException;

public class ItemsActiveServiceImpl implements IItemsActiveService {

	@Autowired
	private IItemsActiveDao itemsActiveDao;

	public List<ItemsActive> queryItemsActiveListByConditions(Map<String, Object> param) throws ServiceException {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		return itemsActiveDao.queryByConditions(param);
	}

	public Paging<ItemsActive> queryItemsActivePaging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		Paging<ItemsActive> paging = new Paging<ItemsActive>();
		paging.setPage(page);
		paging.setPageSize(pageSize);
		paging.setTota(itemsActiveDao.queryCount(param));
		if (paging.getTotal() > 0L) {
			paging.setList(itemsActiveDao.queryList(param, page, pageSize, sort, desc));
		}
		return paging;
	}

}
