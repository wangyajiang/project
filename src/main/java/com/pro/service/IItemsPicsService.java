package com.pro.service;

import java.util.List;
import java.util.Map;

import com.pro.entity.ItemsPics;
import com.tool.bean.Paging;
import com.tool.exception.ServiceException;

public interface IItemsPicsService {

	List<ItemsPics> queryItemsPicsListByConditions(Map<String, Object> param) throws ServiceException;

	Paging<ItemsPics> queryItemsPicsPaging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException;

}
