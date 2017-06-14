package com.pro.service;

import java.util.List;
import java.util.Map;

import com.pro.entity.Items;
import com.tool.bean.Paging;
import com.tool.exception.ServiceException;

public interface IItemsService {

	List<Items> queryItemsListByConditions(Map<String, Object> param) throws ServiceException;

	Paging<Items> queryItemsPaging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException;

}
