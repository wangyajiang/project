package com.pro.service;

import java.util.List;
import java.util.Map;

import com.pro.entity.ItemsActivePics;
import com.tool.bean.Paging;
import com.tool.exception.ServiceException;

public interface IItemsActivePicsService {

	List<ItemsActivePics> queryItemsActivePicsListByConditions(Map<String, Object> param) throws ServiceException;

	Paging<ItemsActivePics> queryItemsActivePicsPaging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException;

}
