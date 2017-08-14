package com.aylson.dc.cfdb.service.impl;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.ImUsersDao;
import com.aylson.dc.cfdb.dao.IncomeHisDao;
import com.aylson.dc.cfdb.dao.TasklistDao;
import com.aylson.dc.cfdb.dao.UserTasklistDao;
import com.aylson.dc.cfdb.po.IncomeHis;
import com.aylson.dc.cfdb.po.UserTasklist;
import com.aylson.dc.cfdb.search.UserTasklistSearch;
import com.aylson.dc.cfdb.service.UserTasklistService;
import com.aylson.dc.cfdb.vo.ImUsersVo;
import com.aylson.dc.cfdb.vo.TasklistVo;
import com.aylson.dc.cfdb.vo.UserTasklistVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.utils.DateUtil2;
import com.aylson.utils.StringUtil;
import com.aylson.utils.UUIDUtils;

@Service
public class UserTasklistServiceImpl  extends BaseServiceImpl<UserTasklist, UserTasklistSearch> implements UserTasklistService {
	
	protected static final Logger logger = Logger.getLogger(UserTasklistServiceImpl.class);

	@Autowired
	private UserTasklistDao userTasklistDao;
	
	@Autowired
	private ImUsersDao imUsersDao;
	
	@Autowired
	private IncomeHisDao incomeHisDao;
	
	@Autowired
	private TasklistDao tasklistDao;

	@Override
	protected BaseDao<UserTasklist, UserTasklistSearch> getBaseDao() {
		return userTasklistDao;
	}

	@Override
	@Transactional
	public Result updateUserTaskInfo(UserTasklistVo userTasklistVo, HttpServletRequest request) {
		Result result = new Result();
		String cTime = DateUtil2.getCurrentLongDateTime();
		try{
			//1. 更新任务审批状态
			userTasklistVo.setIsChecked(1);
			userTasklistVo.setUpdateDate(cTime);
			userTasklistVo.setProveDate(cTime);
			
			//2. 如果审批完成，则需要增加或扣减用户收益金额
			ImUsersVo imUsersVo = this.imUsersDao.selectById(userTasklistVo.getPhoneId());
			//更新数据
			double balance = Double.valueOf(imUsersVo.getBalance());	//原已有余额
			double totalIncome = Double.valueOf(imUsersVo.getTotalIncome());	//原累积收入余额
			double earn = Double.valueOf(userTasklistVo.getIncome());	//任务收益金额
			imUsersVo.setUpdateDate(cTime);
			//操作标识位，1=加钱，2=扣钱
			int actionFlag = 0;
			//格式化，保留两位小数，四舍五入
			DecimalFormat dFormat = new DecimalFormat("#0.000"); 
			//审核完成，增加用户金额
			if(userTasklistVo.getStatusFlag() == 3) {
				actionFlag = 1;
				imUsersVo.setBalance(StringUtil.zero2Str(dFormat.format(balance+earn)));
				imUsersVo.setTotalIncome(StringUtil.zero2Str(dFormat.format(totalIncome+earn)));
				logger.info("用户加钱后余额=" + StringUtil.zero2Str(dFormat.format(balance+earn)) 
					+ "。balance=" + balance + ", earn=" + earn);
				userTasklistVo.setIsFirstSuc(2);		//成功审核标识
				
			//审核失败，且有成功审核后才扣减用户金额
			}else if(userTasklistVo.getStatusFlag() == 4) {
				if(null!=userTasklistVo.getIsFirstSuc() && userTasklistVo.getIsFirstSuc()==2) {
					actionFlag = 2;
					imUsersVo.setBalance(StringUtil.zero2Str(dFormat.format(balance-earn)));
					imUsersVo.setTotalIncome(StringUtil.zero2Str(dFormat.format(totalIncome-earn)));
					logger.info("用户扣钱后余额=" + StringUtil.zero2Str(dFormat.format(balance-earn)) 
						+ "。balance=" + balance + ", earn=" + earn);
				}else {
					actionFlag = 3;	//未扣钱，直接审批失败，不记录收益
				}
				//审批失败时，需把相应的任务数加1
				this.increaseTaskNum(userTasklistVo.getTaskId(), request, cTime);
			}
			
			boolean flag1 = this.userTasklistDao.updateById(userTasklistVo);	//更新任务审批状态
			boolean flag2 = this.imUsersDao.updateById(imUsersVo);			//增加或扣减用户收益金额
			if(flag1 && flag2) {
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else {
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
			
			if(actionFlag != 3) {
				//3. 记录用户收益记录情况
				this.recordIncomeHis(userTasklistVo, request, actionFlag, cTime);;
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 审批失败时，需把相应的任务数加1
	 * @param taskId
	 */
	private void increaseTaskNum(String taskId, HttpServletRequest request, String cTime) {
		SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute("sessionInfo");
		//审批失败时，需把任务数加1
		TasklistVo taskListVo =  this.tasklistDao.selectById(taskId);
		taskListVo.setAmount(taskListVo.getAmount()+1);	//任务当前剩余数量加1
		taskListVo.setUpdateDate(cTime);
		taskListVo.setUpdatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
		boolean flag = this.tasklistDao.updateById(taskListVo);
		if (!flag) {
			logger.warn("审批失败时，任务数回退加1失败，请查核。taskId=" + taskId);
		}
	}
	
	/**
	 * 记录用户收益记录情况
	 * @param userTasklistVo 用户任务
	 * @param request 当前请求
	 * @param actionFlag 操作标识位，1=加钱，2=扣钱
	 * @param cTime 当前时间
	 */
	private void recordIncomeHis(UserTasklistVo userTasklistVo, HttpServletRequest request, int actionFlag, String cTime) {
		SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute("sessionInfo");
		IncomeHis incomeHis = new IncomeHis();
		incomeHis.setId(UUIDUtils.create());
		incomeHis.setPhoneId(userTasklistVo.getPhoneId());
		incomeHis.setTaskId(userTasklistVo.getTaskId());
		incomeHis.setLogoUrl(userTasklistVo.getLogoUrl());
		incomeHis.setTaskName(userTasklistVo.getTaskName());
		incomeHis.setIncomeTime(cTime);
		incomeHis.setIncome(userTasklistVo.getIncome());
		incomeHis.setCreateDate(cTime);
		incomeHis.setCreatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getUser().getRoleName());
		incomeHis.setUpdateDate(cTime);
		incomeHis.setFlag(actionFlag);	//1=加钱；2=扣钱
		incomeHis.setChannel(1);			//1=后台系统广告；2=SDK平台广告
		boolean flag = this.incomeHisDao.insert(incomeHis);				//记录用户收益记录情况
		if(!flag) {
			logger.warn("记录用户收益记录失败，请查核。phoneId=" + userTasklistVo.getPhoneId() 
					+ ", taskId=" + userTasklistVo.getTaskId());
		}
	}
}
