package com.aylson.dc.mem.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.mem.po.ProjectInfo;
import com.aylson.dc.mem.search.ProjectInfoSearch;
import com.aylson.dc.mem.vo.ProjectDesignVo;
import com.aylson.dc.mem.vo.ProjectInfoVo;

public interface ProjectInfoService extends BaseService<ProjectInfo,ProjectInfoSearch> {
	
	/**
	 * 通用有效信息校验
	 * @param projectInfoVo
	 * @return
	 */
	public Result commonValid(ProjectInfoVo projectInfoVo);
	
	/**
	 * （设计师会员）保存方案的客户资料
	 * @param projectInfoVo
	 * @return
	 */
	public Result savePorjectInfo(ProjectInfoVo projectInfoVo);
	
	
	/**
	 * (aylson)审核方案的客户资料
	 * @param projectInfoVo
	 * @return
	 */
	public Result verifyPorjectInfo(ProjectInfoVo projectInfoVo);
	
	/**
	 * （设计师会员）保存方案的设计需求
	 * @param projectDesignVo
	 * @return
	 */
	public Result saveRequirement(ProjectDesignVo projectDesignVo);
	
	/**
	 * (aylson)退回设计需求
	 * @param projectInfoVo
	 * @return
	 */
	public Result returnRequirement(ProjectInfoVo projectInfoVo);
	
	/**
	 * (aylson)保存方案设计内容
	 * @param projectInfoVo
	 * @return
	 */
	public Result saveDesign(ProjectInfoVo projectInfoVo);
	
	/**
	 * （设计师会员）确认方案设计结果
	 * @param projectInfoVo
	 * @return
	 */
	public Result confirmDesign(ProjectInfoVo projectInfoVo);
	
	/**
	 * （设计师会员）不满意设计结果
	 * @param projectInfoVo
	 * @return
	 */
	public Result returnDesign(ProjectInfoVo projectInfoVo);
	
	/**
	 * 评价
	 * @param projectDesignVo
	 * @return
	 */
	public Result evaluate(ProjectDesignVo projectDesignVo);
	
	/**
	 * (aylson)结算方案
	 * @param projectInfoVo
	 * @return
	 */
	public Result settleProject(ProjectInfoVo projectInfoVo);
	
	
	/**
	 * 根据项目id查看工程详情
	 * @param projectId
	 * @return
	 */
	public ProjectInfoVo getProjectInfoDetail(Integer projectId);
	
	/**
	 * 审核产业工人提交的客户资料
	 * @param projectInfoVo
	 * @return
	 */
	public Result verifyWork(ProjectInfoVo projectInfoVo);
	
	/**
	 * 审核开拓者提交的客户资料
	 * @param projectInfoVo
	 * @return
	 */
	public Result verifyPioneer(ProjectInfoVo projectInfoVo);
	
	/**
	 * 保存方案设计的客户资料
	 * @param projectInfoVo
	 * @return
	 */
	public Result saveProjectInfo(ProjectInfoVo projectInfoVo, String memberId);
	
	/**
	 * 跳转到提交方案页面
	 * @param projectDesignVo
	 * @return
	 */
	public Result toProjectDesign(ProjectDesignVo projectDesignVo);
	
	/**
	 * 保存成功案例
	 * @param imgUrl
	 * @param projectId
	 * @param isSuccessfulCase
	 * @param successCaseImg
	 * @return
	 */
	public Result successCase(String[] imgUrl, Integer projectId, Boolean isSuccessfulCase, String successCaseImg);
	
	/**
	 * 业主提交客户资料前
	 * @param projectClientId
	 * @return
	 */
	public Result clientToRequirement(Integer projectClientId);
	
}
