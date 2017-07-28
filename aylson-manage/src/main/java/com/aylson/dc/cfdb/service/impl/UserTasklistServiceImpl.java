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
import com.aylson.dc.cfdb.dao.ImUsersDao;
import com.aylson.dc.cfdb.dao.IncomeHisDao;
import com.aylson.dc.cfdb.dao.UserTasklistDao;
import com.aylson.dc.cfdb.po.IncomeHis;
import com.aylson.dc.cfdb.po.UserTasklist;
import com.aylson.dc.cfdb.search.UserTasklistSearch;
import com.aylson.dc.cfdb.service.UserTasklistService;
import com.aylson.dc.cfdb.vo.ImUsersVo;
import com.aylson.dc.cfdb.vo.UserTasklistVo;
import com.aylson.utils.DateUtil2;
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
			userTasklistVo.setUpdateDate(DateUtil2.getCurrentLongDateTime());
			
			//2. 如果审批完成，则需要增加或扣减用户收益金额
			ImUsersVo imUsersVo = this.imUsersDao.selectById(userTasklistVo.getPhoneId());
			//更新数据
			int balance = Integer.valueOf(imUsersVo.getBalance());	//原已有余额
			int earn = Integer.valueOf(userTasklistVo.getIncome());	//任务收益金额
			imUsersVo.setUpdateDate(cTime);
			//操作标识位
			int actionFlag = 0;
			//审核完成
			if(userTasklistVo.getStatusFlag() == 3) {
				actionFlag = 1;
				imUsersVo.setBalance(String.valueOf(balance+earn));
				logger.info("用户加钱后余额=" + (balance+earn) + "。balance=" + balance + ", earn=" + earn);
				
			//审核失败
			}else if(userTasklistVo.getStatusFlag() == 4) {
				actionFlag = 2;
				imUsersVo.setBalance(String.valueOf(balance-earn));
				logger.info("用户扣钱后余额=" + (balance-earn) + "。balance=" + balance + ", earn=" + earn);
				
			}
			
			boolean flag1 = this.userTasklistDao.updateById(userTasklistVo);	//更新任务审批状态
			boolean flag2 = this.imUsersDao.updateById(imUsersVo);			//增加或扣减用户收益金额
			if(flag1 && flag2) {
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else {
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
			
			//3. 记录用户收益记录情况
			IncomeHis incomeHis = new IncomeHis();
			incomeHis.setId(UUIDUtils.create());
			incomeHis.setPhoneId(userTasklistVo.getPhoneId());
			incomeHis.setTaskId(userTasklistVo.getTaskId());
			incomeHis.setLogoUrl(userTasklistVo.getLogoUrl());
			incomeHis.setTaskName(userTasklistVo.getTaskName());
			incomeHis.setIncomeTime(cTime);
			incomeHis.setIncome(userTasklistVo.getIncome());
			incomeHis.setCreateDate(cTime);
			incomeHis.setUpdateDate(cTime);
			incomeHis.setFlag(actionFlag);	//1=加钱；2=扣钱
			incomeHis.setChannel(1);			//1=后台系统广告；2=SDK平台广告
			boolean flag3 = this.incomeHisDao.insert(incomeHis);				//记录用户收益记录情况
			if(!flag3) {
				logger.warn("记录用户收益记录失败，请查核。phoneId=" + userTasklistVo.getPhoneId() 
						+ ", taskId=" + userTasklistVo.getTaskId());
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
}
