package com.pro.service;

import java.util.List;
import java.util.Map;

import com.pro.entity.FocusMap;
import com.tool.bean.Paging;
import com.tool.exception.ServiceException;

public interface IFocusMapService {

	List<FocusMap> queryFocusMapListByConditions(Map<String, Object> param) throws ServiceException;

	Paging<FocusMap> queryFocusMapPaging(Map<String, Object> param, int page, int pageSize, String sort, boolean desc) throws ServiceException;

}
