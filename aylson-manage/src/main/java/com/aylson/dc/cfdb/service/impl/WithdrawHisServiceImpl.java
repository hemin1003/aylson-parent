package com.aylson.dc.cfdb.service.impl;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.cfdb.dao.ImUsersDao;
import com.aylson.dc.cfdb.dao.WithdrawHisDao;
import com.aylson.dc.cfdb.po.WithdrawHis;
import com.aylson.dc.cfdb.search.WithdrawHisSearch;
import com.aylson.dc.cfdb.service.WithdrawHisService;
import com.aylson.dc.cfdb.vo.ImUsersVo;
import com.aylson.dc.cfdb.vo.WithdrawHisVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.utils.DateUtil2;
import com.aylson.utils.StringUtil;


@Service
public class WithdrawHisServiceImpl extends BaseServiceImpl<WithdrawHis, WithdrawHisSearch> implements WithdrawHisService {
	
	protected static final Logger logger = Logger.getLogger(WithdrawHisServiceImpl.class);

	@Autowired
	private WithdrawHisDao withdrawHisDao;
	
	@Autowired
	private ImUsersDao imUsersDao;

	@Override
	protected BaseDao<WithdrawHis, WithdrawHisSearch> getBaseDao() {
		return withdrawHisDao;
	}

	@Override
	public Result updateWithdrawHisInfo(WithdrawHisVo withdrawHisVo, HttpServletRequest request) {
		Result result = new Result();
		String cTime = DateUtil2.getCurrentLongDateTime();
		try{
			//1. 更新提现状态
			SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute("sessionInfo");
			withdrawHisVo.setUpdatedBy(sessionInfo.getUser().getUserName() + "/" + sessionInfo.getRole().getRoleName());
			withdrawHisVo.setUpdateDate(cTime);
			
			//2. 如果失败，则需要增加用户收益金额
			ImUsersVo imUsersVo = this.imUsersDao.selectById(withdrawHisVo.getPhoneId());
			//更新数据
			double balance = Double.valueOf(imUsersVo.getBalance());	//原已有余额
			//2=支付成功；3=充值成功；；
			if(withdrawHisVo.getStatusType() == 2 || withdrawHisVo.getStatusType() == 3) {
				logger.info("用户提现成功，余额=" + (balance));
				
			//4=失败则回退用户金额
			}else if(withdrawHisVo.getStatusType() == 4) {
				//格式化，保留两位小数，四舍五入
				DecimalFormat dFormat = new DecimalFormat("#0.000"); 
				double earn = Double.valueOf(withdrawHisVo.getIncome());	//提现金额
				imUsersVo.setUpdateDate(cTime);
				imUsersVo.setBalance(StringUtil.zero2Str(dFormat.format(balance+earn)));
				logger.info("用户提现失败，回滚后余额=" + StringUtil.zero2Str(dFormat.format(balance+earn)) 
					+ "。balance=" + balance + ", earn=" + earn);
				
			}
			
			boolean flag1 = this.withdrawHisDao.updateById(withdrawHisVo);	//更新提现状态
			boolean flag2 = this.imUsersDao.updateById(imUsersVo);			//更新用户收益金额
			if(flag1 && flag2) {
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else {
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
}
