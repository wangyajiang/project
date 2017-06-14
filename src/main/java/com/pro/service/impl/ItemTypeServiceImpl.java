package com.pro.service.impl;

import java.util.List;
 import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pro.service.IItemTypeService;
import com.pro.dao.IItemTypeDao;
import com.pro.entity.ItemType;
import com.tool.bean.Paging;
import com.tool.exception.ServiceException;

public class ItemTypeServiceImpl implements IItemTypeService {

	@Autowired
	private IItemTypeDao itemTypeDao;
	
	public List<ItemType> queryItemTypeAll() throws ServiceException {
		return itemTypeDao.queryAll();
	}

	public List<ItemType> queryItemTypeListByConditions(Map<String, Object> param) throws ServiceException {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		return itemTypeDao.queryByConditions(param);
	}

	public Paging<ItemType> queryItemTypePaging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		Paging<ItemType> paging = new Paging<ItemType>();
		paging.setPage(page);
		paging.setPageSize(pageSize);
		paging.setTota(itemTypeDao.queryCount(param));
		if (paging.getTotal() > 0L) {
			paging.setList(itemTypeDao.queryList(param, page, pageSize, sort, desc));
		}
		return paging;
	}

}
