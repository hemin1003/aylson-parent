package com.aylson.core.frame.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 基础控制器，其他控制器需extends此控制器获得initBinder自动转换的功能
 * 
 * 
 */
@Controller
public class BaseController {

	protected static final Logger logger = Logger
			.getLogger(BaseController.class);

	protected HttpServletRequest request;

	protected ApplicationContext context;
	
	protected String clientIp = null;


	/**
	 * 自动注入当前Request对象
	 * 
	 * @param request
	 */
	@ModelAttribute
	public void setReq(HttpServletRequest request) {
		this.request = request;
		clientIp = request.getRemoteAddr();
	}

	/**
	 * 获得Spring容器对象
	 * 
	 * @return
	 */
	protected ApplicationContext getContext() {
		return context;
	}

	/**
	 * 获取HttpServletRequest对象
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return request;
	}

}
