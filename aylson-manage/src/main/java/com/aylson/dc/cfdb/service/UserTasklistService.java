package com.aylson.dc.cfdb.service;

import javax.servlet.http.HttpServletRequest;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.cfdb.po.UserTasklist;
import com.aylson.dc.cfdb.search.UserTasklistSearch;
import com.aylson.dc.cfdb.vo.UserTasklistVo;

public interface UserTasklistService  extends BaseService<UserTasklist, UserTasklistSearch> {

	/**
	 * 更新审批信息，如果审批完成，则更新用户余额
	 * @param userTasklist
	 * @return
	 */
	public Result updateUserTaskInfo(UserTasklistVo userTasklistVo, HttpServletRequest request);
}
