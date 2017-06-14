package com.pro.service;

import java.util.List;
import java.util.Map;

import com.pro.entity.ActiveOrder;
import com.tool.bean.Paging;
import com.tool.exception.ServiceException;

public interface IActiveOrderService {

	List<ActiveOrder> queryActiveOrderListByConditions(Map<String, Object> param) throws ServiceException;

	Paging<ActiveOrder> queryActiveOrderPaging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException;

}
