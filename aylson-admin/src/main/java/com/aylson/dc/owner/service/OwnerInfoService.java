package com.aylson.dc.owner.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.owner.po.OwnerInfo;
import com.aylson.dc.owner.search.OwnerInfoSearch;
import com.aylson.dc.owner.vo.OwnerInfoVo;

public interface OwnerInfoService extends BaseService<OwnerInfo,OwnerInfoSearch> {
	
	/**
	 * 添加客户信息以及附件
	 * @param ownerInfoVo
	 * @return
	 */
	public Result addInfo(OwnerInfoVo ownerInfoVo);
	
	/**
	 * 修改客户信息以及附件
	 * @param ownerInfoVo
	 * @return
	 */
	public Result updateInfo(OwnerInfoVo ownerInfoVo);
	
	/**
	 * 删除客户信息以及附件
	 * @param ownerInfoVo
	 * @return
	 */
	public Result delInfo(Integer id);
	
}
