package com.aylson.dc.cfdb.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.TaskDetailDao;
import com.aylson.dc.cfdb.dao.TasklistDao;
import com.aylson.dc.cfdb.po.Tasklist;
import com.aylson.dc.cfdb.search.TasklistSearch;
import com.aylson.dc.cfdb.service.TasklistService;
import com.aylson.dc.cfdb.vo.TaskDetailVo;
import com.aylson.dc.cfdb.vo.TasklistVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.utils.DateUtil2;
import com.aylson.utils.UUIDUtils;

@Service
public class TasklistServiceImpl  extends BaseServiceImpl<Tasklist, TasklistSearch> implements TasklistService {

	protected static final Logger logger = Logger.getLogger(TasklistServiceImpl.class);
	
	@Autowired
	private TasklistDao tasklistDao;
	
	@Autowired
	private TaskDetailDao taskDetailDao;
	
	@Override
	protected BaseDao<Tasklist, TasklistSearch> getBaseDao() {
		return tasklistDao;
	}

	@Override
	@Transactional
	public Result addListAndDetail(TasklistVo tasklistVo, HttpServletRequest request) {
		Result result = new Result();
		try{
			//1. 任务表
			String taskId = UUIDUtils.create();
			tasklistVo.setTaskId(taskId);
			SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute("sessionInfo");
			String cTime = DateUtil2.getCurrentLongDateTime();
			tasklistVo.setCreateDate(cTime);
			tasklistVo.setUpdateDate(cTime);
			tasklistVo.setCreatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			boolean flag1 = this.tasklistDao.insert(tasklistVo);
			
			//2. 详情表
			TaskDetailVo taskDetailVo = new TaskDetailVo();
			taskDetailVo.setTaskId(taskId);
			taskDetailVo.setCreateDate(cTime);
			taskDetailVo.setUpdateDate(cTime);
			taskDetailVo.setCreatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
			boolean flag2 = this.taskDetailDao.insert(taskDetailVo);
			
			if(flag1 && flag2){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
				//新增失败时则清除
				this.tasklistDao.deleteById(taskId);
				this.taskDetailDao.deleteById(taskId);
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}

	@Override
	@Transactional
	public Result deleteListAndDetail(String taskId) {
		Result result = new Result();
		try{
			//1. 任务表
			boolean flag1 = this.tasklistDao.deleteById(taskId);
			//2. 详情表
			boolean flag2 = this.taskDetailDao.deleteById(taskId);
			if(flag1 && flag2){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
}