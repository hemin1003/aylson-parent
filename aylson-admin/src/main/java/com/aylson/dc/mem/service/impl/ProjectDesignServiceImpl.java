package com.aylson.dc.mem.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.exception.ServiceException;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.GeneralConstant.AttachmentType;
import com.aylson.dc.base.GeneralConstant.GoldType;
import com.aylson.dc.base.GeneralConstant.ProjectStatus;
import com.aylson.dc.base.GeneralConstant.SourceType;
import com.aylson.dc.mem.dao.ProjectDesignDao;
import com.aylson.dc.mem.po.ProjectDesign;
import com.aylson.dc.mem.search.ProjectDesignSearch;
import com.aylson.dc.mem.service.MemAccountService;
import com.aylson.dc.mem.service.ProjectDesignService;
import com.aylson.dc.mem.service.ProjectInfoService;
import com.aylson.dc.mem.vo.MemGoldDetailVo;
import com.aylson.dc.mem.vo.ProjectDesignVo;
import com.aylson.dc.mem.vo.ProjectInfoVo;
import com.aylson.dc.sys.common.SystemConfig;
import com.aylson.dc.sys.po.Attachment;
import com.aylson.dc.sys.search.AttachmentSearch;
import com.aylson.dc.sys.service.AttachmentService;
import com.aylson.dc.sys.vo.AttachmentVo;
import com.aylson.utils.StringUtil;

@Service
public class ProjectDesignServiceImpl extends BaseServiceImpl<ProjectDesign,ProjectDesignSearch> implements ProjectDesignService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectDesignServiceImpl.class);
	
	@Autowired
	private ProjectDesignDao projectDesignDao;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private MemAccountService memAccountService;
	@Autowired
	private ProjectInfoService projectInfoService;
	
	@Override
	protected BaseDao<ProjectDesign,ProjectDesignSearch> getBaseDao() {
		return this.projectDesignDao;
	}

	@Override
	public ProjectDesignVo getByProjectId(Integer projectId) {
		return this.projectDesignDao.selectByProjectId(projectId);
	}

	@Override
	@Transactional
	public Result saveProjectDesign(ProjectDesignVo projectDesignVo, Integer memberId) {
		Result result = new Result();
		Boolean flag = false;
		//保存（提交）设计方案需求需要做的事，扣除一定金币，修改设计状态，同时保存设计方案需求的附件
		//信息校验
		if(projectDesignVo.getProjectId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取工程客户资料失败");
			return result;
		}
		if(StringUtil.isEmpty(projectDesignVo.getAttachments())){
			result.setError(ResultCode.CODE_STATE_4006, "请上传方案需求附件");
			return result;
		}
		if(memberId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取当前会员信息失败");
			return result;
		}
		Date createTime = new Date();
		if(projectDesignVo.getId() != null){//修改
			//如果有旧的附件，先作废
			AttachmentSearch attachmentSearch = new AttachmentSearch();
			//attachmentSearch.setType(AttachmentType.PROJECT_PROGRAM);
			attachmentSearch.setSourceId(projectDesignVo.getProjectId());
			List<Attachment> requirementListBefore = this.attachmentService.getList(attachmentSearch);
			if(requirementListBefore != null && requirementListBefore.size() > 0){
				for(Attachment temp : requirementListBefore){
					temp.setStatus(0);//作废
				}
				flag = this.attachmentService.batchUpdate(requirementListBefore);
				if(!flag){
					result.setError(ResultCode.CODE_STATE_4006, "处理旧的方案需求附件失败，请稍后再试");
					throw new ServiceException("处理旧的方案需求附件失败");
				}
			}
		}else{//新增
			Integer gold = Integer.parseInt(SystemConfig.getConfigValueByKey("payGoldByProject"));//需要消费金币，验证是否有足够的金币
			MemGoldDetailVo memGoldDetailVo = new MemGoldDetailVo();
			memGoldDetailVo.setCreateTime(createTime);
			memGoldDetailVo.setGold(-gold);
			memGoldDetailVo.setMemberId(memberId);
			memGoldDetailVo.setSourceId(projectDesignVo.getProjectId());
			memGoldDetailVo.setSourceType(SourceType.PROGRAMDESIGN);
			memGoldDetailVo.setDescription(GoldType.GoldTypeMap.get(GoldType.SUBMIT_PROJECT_REQUIREMENT));
			memGoldDetailVo.setType(GoldType.SUBMIT_PROJECT_REQUIREMENT);
			result = this.memAccountService.updateGold(memGoldDetailVo);
			if(!result.isSuccess()){
				return result;
			}
			projectDesignVo.setGold(gold);
			projectDesignVo.setIsPay(true);
		}
		//保存新的附件列表
		String[] requireAttachmentArray = projectDesignVo.getAttachments().split(",");
		if(requireAttachmentArray != null && requireAttachmentArray.length > 0){
			List<Attachment> requirementList = new ArrayList<Attachment>();
			for(int i=0; i<requireAttachmentArray.length; i++){
				if(StringUtil.isEmpty(requireAttachmentArray[i])){continue;}
				AttachmentVo attachmentVo = new AttachmentVo();
				attachmentVo.setCreateTime(createTime);
				attachmentVo.setAttachAddress(requireAttachmentArray[i]);
				attachmentVo.setSourceId(projectDesignVo.getProjectId());
				//attachmentVo.setType(AttachmentType.PROJECT_PROGRAM);
				//attachmentVo.setRemark(AttachmentType.AttachmentTypeMap.get(AttachmentType.PROJECT_PROGRAM));
				requirementList.add(attachmentVo);
			}
			flag = this.attachmentService.batchAdd(requirementList);
			if(flag){//保存方案需求附件成功，
				if(projectDesignVo.getId() != null){//修改
					flag = this.edit(projectDesignVo);
				}else{//新增
					flag = this.add(projectDesignVo);
				}
				if(flag){//如果成功，修改状态为
					ProjectInfoVo projectInfoVo = this.projectInfoService.getById(projectDesignVo.getProjectId());
					if(projectInfoVo != null){
						projectInfoVo.setStatus(ProjectStatus.HAD_SUMBIT_PROGRAM);
						flag = this.projectInfoService.edit(projectInfoVo);
						if(flag){
							result.setOK(ResultCode.CODE_STATE_200, "保存成功",projectDesignVo);
						}else{
							result.setError(ResultCode.CODE_STATE_4006, "更新工程状态失败，请稍后再试");
							throw new ServiceException("更新工程状态失败");
						}
					}else{
						result.setError(ResultCode.CODE_STATE_4006, "获取工程客户资料失败，请稍后再试");
						throw new ServiceException("获取工程客户资料失败");
					}
				}else{
					result.setError(ResultCode.CODE_STATE_4006, "保存方案需求失败，请稍后再试");
					throw new ServiceException("保存方案需求失败");
				}
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "保存方案需求附件失败，请稍后再试");
				throw new ServiceException("保存方案需求附件失败");
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "上传方案需求附件有误");
			return result;
		}
		return result;
	}

	@Override
	public Result commonValid(ProjectDesignVo projectDesignVo) {
		Result result = new Result();
		//信息校验，说明：修改和新增的差异性信息校验在控制器层处理
		if(projectDesignVo.getProjectId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取工程的客户资料失败");
			return result;
		}
		if(StringUtil.isEmpty(projectDesignVo.getProductTypes()) || StringUtil.isEmpty(projectDesignVo.getProductTypeIds())){
			result.setError(ResultCode.CODE_STATE_4006, "产品设计内容不能为空");
			return result;
		}
		if(StringUtil.isEmpty(projectDesignVo.getAttachments())){
			result.setError(ResultCode.CODE_STATE_4006, "方案需求附件不能为空");
			return result;
		}
		result.setOK(ResultCode.CODE_STATE_200, "信息有效");
		return result;
	}

	
}
