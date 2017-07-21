package com.aylson.dc.mem.service;

import java.util.List;
import java.util.Map;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.mem.po.ProjectClient;
import com.aylson.dc.mem.search.ProjectClientSearch;
import com.aylson.dc.mem.vo.ClientInfoVo;
import com.aylson.dc.mem.vo.MemAccountVo;

public interface ProjectClientService extends BaseService<ProjectClient,ProjectClientSearch> {
	
	/**
	 * 查询待分配业主资料的会员列表
	 * @return
	 */
	public List<MemAccountVo> getDistributionMember();
	
	/**
	 * 查询可以分配的业主资料
	 * @return
	 */
	public List<ClientInfoVo> getDistributionClient();
	
	/**
	 * 获取分配列表
	 * @param memAccountVoList
	 * @param clientInfoVoList
	 * @return
	 */
	public Map<Integer,List<ClientInfoVo>> getDistributionList(List<MemAccountVo> memAccountVoList,List<ClientInfoVo> clientInfoVoList);
	
	/**
	 * 保存分配的会员业主资料
	 * @param memClientInfo
	 * @return
	 */
	public Result distribuClientInfo(Map<Integer,List<ClientInfoVo>> memClientInfo);
	
	/**
	 * 保存分配的会员业主资料
	 * @param memClientInfo
	 * @return
	 */
	public Result distribuClientInfoJob();
	
	/**
	 * 批量忽略过期的客户资料
	 * @return
	 */
	public Result ignoreClientInfoJob();
	
	
}
