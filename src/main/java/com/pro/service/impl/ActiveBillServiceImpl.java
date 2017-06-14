package com.pro.service.impl;

import java.util.List;
 import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.pro.service.IActiveBillService;
import com.pro.dao.IActiveBillDao;
import com.pro.entity.ActiveBill;
import com.tool.bean.Paging;
import com.tool.exception.ServiceException;

public class ActiveBillServiceImpl implements IActiveBillService {

	@Autowired
	private IActiveBillDao activeBillDao;

	public List<ActiveBill> queryActiveBillListByConditions(Map<String, Object> param) throws ServiceException {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		return activeBillDao.queryByConditions(param);
	}

	public Paging<ActiveBill> queryActiveBillPaging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException {
		if (param == null) {
			param = new HashMap<String, Object>();
		}
		Paging<ActiveBill> paging = new Paging<ActiveBill>();
		paging.setPage(page);
		paging.setPageSize(pageSize);
		paging.setTota(activeBillDao.queryCount(param));
		if (paging.getTotal() > 0L) {
			paging.setList(activeBillDao.queryList(param, page, pageSize, sort, desc));
		}
		return paging;
	}

}
