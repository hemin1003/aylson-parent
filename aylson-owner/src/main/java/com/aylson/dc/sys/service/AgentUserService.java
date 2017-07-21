package com.aylson.dc.sys.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.sys.po.AgentUser;
import com.aylson.dc.sys.search.AgentUserSearch;
import com.aylson.dc.sys.vo.AgentUserVo;

public interface AgentUserService extends BaseService<AgentUser,AgentUserSearch> {
	
	/**
	 * 添加代理商用户信息
	 * @param agentUserVo
	 * @return
	 */
	public Result addAgentUser(AgentUserVo agentUserVo);
	
	/**
	 * 修改代理商用户信息
	 * @param orgUserVo
	 * @return
	 */
	public Result editAgentUser(AgentUserVo agentUserVo);
	
	/**
	 * 根据用户id查询
	 * @param userId
	 * @return
	 */
	public AgentUserVo getByUserId(Integer userId);
	
}
