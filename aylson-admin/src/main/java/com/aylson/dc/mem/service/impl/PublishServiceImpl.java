package com.aylson.dc.mem.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.base.Page;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.GeneralConstant.PublishStatus;
import com.aylson.dc.mem.dao.PublishDao;
import com.aylson.dc.mem.po.Publish;
import com.aylson.dc.mem.po.PublishRead;
import com.aylson.dc.mem.search.PublishSearch;
import com.aylson.dc.mem.service.PublishService;
import com.aylson.dc.mem.vo.PublishVo;
import com.aylson.utils.StringUtil;

@Service
public class PublishServiceImpl extends BaseServiceImpl<Publish,PublishSearch> implements PublishService {
	
	private static final Logger logger = LoggerFactory.getLogger(PublishServiceImpl.class);
	
	@Autowired
	private PublishDao publishDao;
	
	@Override
	protected BaseDao<Publish,PublishSearch> getBaseDao() {
		return this.publishDao;
	}

	@Override
	public Boolean addReadInfo(PublishRead publishRead) {
		return this.publishDao.insertReadInfo(publishRead);
	}
	
	/**
	 * 获取发布信息列表：
	 * @param type 1：通知公告 2：新闻动态
	 * @return
	 */
	@Override
	public Result getPublishList(PublishSearch publishSearch, String memberId) {
		Result result = new Result();
		try{
			if(publishSearch.getType() == null){
				result.setError(ResultCode.CODE_STATE_4006, "发布类型未知");
				return result;
			}
			if(StringUtil.isNotEmpty(memberId)){
				publishSearch.setReaderId(Integer.parseInt(memberId));
			}
			publishSearch.setStatus(PublishStatus.PUBLISH);
			Page<PublishVo> page = this.getPage(publishSearch);
			result.setOK(ResultCode.CODE_STATE_200, "", page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}

	/**
	 * 根据发布ID查看发布详情
	 * 目前有：1：通知公告 2：新闻动态
	 * @param publishId
	 * @return
	 */
	@Override
	public Result getPublishInfo(Integer publishId, String memberId) {
		Result result = new Result();
		if(publishId == null){
			result.setError(ResultCode.CODE_STATE_4006, "找不到该条发布");
			return result;
		}
		PublishVo publishVo = this.getById(publishId);
		if(!publishVo.getIsRead()){//如果未读，添加到已读列表中去
			if(StringUtil.isEmpty(memberId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			PublishRead publishRead = new PublishRead();
			publishRead.setPublishId(publishId);
			publishRead.setReadTime(new Date());
			publishRead.setReaderId(Integer.parseInt(memberId));
			Boolean flag = this.addReadInfo(publishRead);
			if(flag){
				publishVo.setIsRead(true);
			}
		}
		result.setOK(ResultCode.CODE_STATE_200, "", publishVo);
		return result;
	}
	
	
}
