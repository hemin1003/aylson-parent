package com.aylson.dc.mem.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.mem.po.Publish;
import com.aylson.dc.mem.po.PublishRead;
import com.aylson.dc.mem.search.PublishSearch;

public interface PublishService extends BaseService<Publish,PublishSearch> {
	
	/**
	 * 添加读取详情
	 * @param publishRead
	 * @return
	 */
	public Boolean addReadInfo(PublishRead publishRead);
	
	/**
	 * 获取发布信息列表：
	 * @param type 1：通知公告 2：新闻动态
	 * @return
	 */
	public Result getPublishList(PublishSearch publishSearch, String memberId);
	
	/**
	 * 根据发布ID查看发布详情
	 * 目前有：1：通知公告 2：新闻动态
	 * @param publishId
	 * @return
	 */
	public Result getPublishInfo(Integer publishId, String memberId);
	
}
