package com.pro.conntroller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pro.common.Contents;
import com.pro.entity.Items;
import com.pro.entity.ItemsActive;
import com.pro.service.IItemsActiveService;
import com.tool.bean.Paging;
import com.tool.bean.Result;
import com.tool.constant.EnumConfig;
import com.tool.exception.ServiceException;
import com.tool.utils.ConvertUtils;
import com.tool.utils.WebUtils;


@Controller
@RequestMapping("/active")
public class ItemActiveController {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IItemsActiveService itemsActiveService;
	
	
	@RequestMapping("/getItemsActives")
	@ResponseBody
	public Result getFocusMaps(HttpServletRequest request, HttpServletResponse response) {
		Result rt = new Result();
		try {
			int page = ConvertUtils.getInt(request.getParameter("page"), 1);
			int pageSize = ConvertUtils.getInt(request.getParameter("pageSize"), Contents.PAGE_SIZE);
			String sort = ConvertUtils.getStr(request.getParameter("sort"), null);
			boolean desc = ConvertUtils.getBoolean(request.getParameter("desc"), true);
			
			Map<String, Object> param = WebUtils.requestToMap(request, Items.class);
			Paging<ItemsActive> paging = itemsActiveService.queryItemsActivePaging(param, page, pageSize, sort, desc);
			rt.setData(paging);
		} catch (ServiceException e) {
			rt.setCode(e.getCode());
			logger.error("【ServiceException】", e);
		} catch (Exception e) {
			rt.setCode(EnumConfig.S_CODE.FAIL);
			logger.error("【Exception】", e);
		}
		return rt;
	}
}
