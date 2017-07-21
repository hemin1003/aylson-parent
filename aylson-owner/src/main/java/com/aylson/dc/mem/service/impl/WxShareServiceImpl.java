package com.aylson.dc.mem.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.mem.dao.WxShareDao;
import com.aylson.dc.mem.po.WxShare;
import com.aylson.dc.mem.search.WxShareSearch;
import com.aylson.dc.mem.service.WxShareService;
import com.aylson.utils.StringUtil;

@Service
public class WxShareServiceImpl extends BaseServiceImpl<WxShare,WxShareSearch> implements WxShareService {
	
	private static final Logger logger = LoggerFactory.getLogger(WxShareServiceImpl.class);
	
	@Autowired
	private WxShareDao wxShareDao;
	
	@Override
	protected BaseDao<WxShare,WxShareSearch> getBaseDao() {
		return this.wxShareDao;
	}

	@Override
	public Result isShared(String channel, String inviterWxOpenId, String wxOpenId) {
		Result result = new Result();
		if(StringUtil.isEmpty(channel)){
			result.setError(ResultCode.CODE_STATE_4006, "渠道不能为空");
			return result;
		}
		if(StringUtil.isEmpty(inviterWxOpenId)){
			result.setError(ResultCode.CODE_STATE_4006, "分享人wxOpenId不能为空");
			return result;
		}
		if(StringUtil.isEmpty(wxOpenId)){
			result.setError(ResultCode.CODE_STATE_4006, "wxOpenId不能为空");
			return result;
		}
		WxShareSearch wxShareSearch =  new WxShareSearch();
		wxShareSearch.setChannel(channel);
		wxShareSearch.setInviterWxOpenId(inviterWxOpenId);
		wxShareSearch.setWxOpenId(wxOpenId);
		long rows = this.wxShareDao.getRowCount(wxShareSearch);
		if(rows > 0){//已经存在
			result.setError(ResultCode.CODE_STATE_4006, "已经分享过了");
			return result;
		}
		result.setOK(ResultCode.CODE_STATE_200, "分享有效");
		return result;
	}

	@Override
	public int getAssistValue(int sharersTotal, int sharersHad, int assistValueTotal, int assistValueHad) {
//		int assistValue = (int)Math.round(Math.random()*8);
		int assistValue = (int)Math.round(Math.random()*6.5);
		if(assistValue < 1){
			assistValue = 1;
    	}
    	//System.out.println(assistValue);
    	if(sharersHad == sharersTotal-1){//最后一个人，获取剩余的概率数
    		assistValue = assistValueTotal - assistValueHad;
    	}
		return assistValue;
	}

	
}
