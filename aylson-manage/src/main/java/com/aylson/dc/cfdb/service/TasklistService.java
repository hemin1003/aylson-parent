package com.aylson.dc.cfdb.service;

import javax.servlet.http.HttpServletRequest;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.cfdb.po.Tasklist;
import com.aylson.dc.cfdb.search.TasklistSearch;
import com.aylson.dc.cfdb.vo.TasklistVo;

public interface TasklistService  extends BaseService<Tasklist, TasklistSearch> {
	
	/**
	 * 新增任务，同时新增详情表记录
	 * @param tasklistVo
	 * @return
	 */
	public Result addListAndDetail(TasklistVo tasklistVo, HttpServletRequest request);
	
	/**
	 * 删除任务，同时删除详情表记录
	 * @param taskId
	 * @return
	 */
	public Result deleteListAndDetail(String taskId);
}
