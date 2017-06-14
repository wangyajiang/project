package com.pro.conntroller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pro.common.Contents;
import com.pro.entity.ItemType;
import com.pro.service.IItemTypeService;
import com.pro.service.IUserService;
import com.pro.utils.WebClient;
import com.tool.bean.Result;
import com.tool.constant.EnumConfig;
import com.tool.exception.ServiceException;
import com.tool.utils.CheckUtils;


@Controller
public class SysController {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IItemTypeService itemTypeService;
	@Autowired
	private IUserService userService;
	
	@RequestMapping("/home.do")
	public String logins() {
		return "redirect:/pages/home.html";
	}
	
	@RequestMapping("/getPage")
	@ResponseBody
	public Result getPage(HttpServletRequest request, HttpServletResponse response) {
		Result rt = new Result();
		try {
			String page = request.getParameter("page");
			String url = request.getScheme()+"://"+ request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			if (CheckUtils.isBlank(page)) {
				rt.setCode(EnumConfig.S_CODE.NO_PAGE);
				return rt;
			}
			url += page;
			String content = WebClient.request(url, null);
			rt.setData(content);
		} catch (ServiceException e) {
			rt.setCode(e.getCode());
			logger.error("【ServiceException】", e);
		} catch (Exception e) {
			rt.setCode(EnumConfig.S_CODE.FAIL);
			logger.error("【Exception】", e);
		}
		return rt;
	}
	
	@RequestMapping("/getItemTypes")
	@ResponseBody
	public Result getItemTypes(HttpServletRequest request, HttpServletResponse response) {
		Result rt = new Result();
		try {
			List<ItemType> list = Contents.itemTypes;
			if (list == null) {
				list = itemTypeService.queryItemTypeAll();
				Contents.itemTypes = list;
			}
			rt.setData(list);
		} catch (ServiceException e) {
			rt.setCode(e.getCode());
			logger.error("【ServiceException】", e);
		} catch (Exception e) {
			rt.setCode(EnumConfig.S_CODE.FAIL);
			logger.error("【Exception】", e);
		}
		return rt;
	}
	
	@RequestMapping("/login")
	@ResponseBody
	public Result login(HttpServletRequest request, HttpServletResponse response) {
		Result rt = new Result();
		try {
			rt.setData(userService.login(request.getParameter("mobile"), request.getParameter("password")));
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
