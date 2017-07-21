package com.aylson.dc.mem.dao;

import java.util.List;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.dc.mem.po.ProjectClient;
import com.aylson.dc.mem.search.ProjectClientSearch;
import com.aylson.dc.mem.vo.ClientInfoVo;
import com.aylson.dc.mem.vo.MemAccountVo;

public interface ProjectClientDao extends BaseDao<ProjectClient,ProjectClientSearch> {
	
	/**
	 * 查询待分配业主资料的会员列表
	 * @return
	 */
	public List<MemAccountVo> selectDistributionMember();
	
	/**
	 * 查询可以分配的业主资料
	 * @return
	 */
	public List<ClientInfoVo> selectDistributionClient();
	
}
