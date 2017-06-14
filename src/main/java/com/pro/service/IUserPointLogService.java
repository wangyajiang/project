package com.pro.service;

import com.pro.common.SessionUser;
import com.tool.exception.ServiceException;

public interface IUserPointLogService {

	void sign(SessionUser key) throws ServiceException;

}
