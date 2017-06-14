package com.pro.service;

import java.util.List;
import java.util.Map;

import com.pro.entity.ActiveBill;
import com.tool.bean.Paging;
import com.tool.exception.ServiceException;

public interface IActiveBillService {

	List<ActiveBill> queryActiveBillListByConditions(Map<String, Object> param) throws ServiceException;

	Paging<ActiveBill> queryActiveBillPaging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException;

}
