package com.pro.service;

import java.util.List;
import java.util.Map;

import com.pro.entity.ItemsActive;
import com.tool.bean.Paging;
import com.tool.exception.ServiceException;

public interface IItemsActiveService {

	List<ItemsActive> queryItemsActiveListByConditions(Map<String, Object> param) throws ServiceException;

	Paging<ItemsActive> queryItemsActivePaging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException;

}
