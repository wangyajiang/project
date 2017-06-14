package com.pro.conntroller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pro.service.IFocusMapService;
import com.tool.bean.Result;
import com.tool.constant.EnumConfig;
import com.tool.exception.ServiceException;
import com.tool.utils.ConvertUtils;


@Controller
@RequestMapping("/home")
public class HomeController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IFocusMapService focusMapService;
	
	@RequestMapping("/focusMaps")
	@ResponseBody
	public Result getFocusMaps(HttpServletRequest request, HttpServletResponse response) {
		Result rt = new Result();
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sort", "sort");
			param.put("desc", ConvertUtils.formatToBoolean(request.getParameter("desc"), false));
			param.put("pageSize", ConvertUtils.getInt(request.getParameter("pageSize"), 100));
			rt.setData(focusMapService.queryFocusMapListByConditions(param));
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
