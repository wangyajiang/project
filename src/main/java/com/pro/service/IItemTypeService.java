package com.pro.service;

import java.util.List;
import java.util.Map;

import com.pro.entity.ItemType;
import com.tool.bean.Paging;
import com.tool.exception.ServiceException;

public interface IItemTypeService {
	
	List<ItemType> queryItemTypeAll() throws ServiceException;
	
	List<ItemType> queryItemTypeListByConditions(Map<String, Object> param) throws ServiceException;

	Paging<ItemType> queryItemTypePaging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException;

}
