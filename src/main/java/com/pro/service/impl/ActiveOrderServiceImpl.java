package com.pro.service.impl;

import java.util.List;
 import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pro.service.IActiveOrderService;
import com.pro.dao.IActiveOrderDao;
import com.pro.entity.ActiveOrder;
import com.tool.bean.Paging;
import com.tool.exception.ServiceException;

public class ActiveOrderServiceImpl implements IActiveOrderService {

	@Autowired
	private IActiveOrderDao activeOrderDao;

	public List<ActiveOrder> queryActiveOrderListByConditions(Map<String, Object> param) throws ServiceException {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		return activeOrderDao.queryByConditions(param);
	}

	public Paging<ActiveOrder> queryActiveOrderPaging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		Paging<ActiveOrder> paging = new Paging<ActiveOrder>();
		paging.setPage(page);
		paging.setPageSize(pageSize);
		paging.setTota(activeOrderDao.queryCount(param));
		if (paging.getTotal() > 0L) {
			paging.setList(activeOrderDao.queryList(param, page, pageSize, sort, desc));
		}
		return paging;
	}

}
