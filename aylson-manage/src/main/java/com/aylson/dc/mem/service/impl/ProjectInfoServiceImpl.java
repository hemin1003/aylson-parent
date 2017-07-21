package com.aylson.dc.mem.service.impl;

import java.net.URLDecoder;
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
import com.aylson.dc.base.GeneralConstant.IntegeralType;
import com.aylson.dc.base.GeneralConstant.IntegralConfigType;
import com.aylson.dc.base.GeneralConstant.MemClientStatus;
import com.aylson.dc.base.GeneralConstant.MemberType;
import com.aylson.dc.base.GeneralConstant.ProjectStatus;
import com.aylson.dc.base.GeneralConstant.SourceType;
import com.aylson.dc.base.SmsTemplate;
import com.aylson.dc.mem.dao.ProjectInfoDao;
import com.aylson.dc.mem.po.MemIntegralDetail;
import com.aylson.dc.mem.po.ProjectFlowNode;
import com.aylson.dc.mem.po.ProjectInfo;
import com.aylson.dc.mem.search.ProjectFlowNodeSearch;
import com.aylson.dc.mem.search.ProjectInfoSearch;
import com.aylson.dc.mem.service.MemAccountService;
import com.aylson.dc.mem.service.ProjectClientService;
import com.aylson.dc.mem.service.ProjectDesignService;
import com.aylson.dc.mem.service.ProjectFlowNodeService;
import com.aylson.dc.mem.service.ProjectInfoService;
import com.aylson.dc.mem.vo.MemAccountVo;
import com.aylson.dc.mem.vo.MemGoldDetailVo;
import com.aylson.dc.mem.vo.MemIntegralDetailVo;
import com.aylson.dc.mem.vo.ProjectClientVo;
import com.aylson.dc.mem.vo.ProjectDesignVo;
import com.aylson.dc.mem.vo.ProjectInfoVo;
import com.aylson.dc.sys.common.SystemConfig;
import com.aylson.dc.sys.po.Attachment;
import com.aylson.dc.sys.search.AttachmentSearch;
import com.aylson.dc.sys.search.DictionarySearch;
import com.aylson.dc.sys.service.AttachmentService;
import com.aylson.dc.sys.service.DictionaryService;
import com.aylson.dc.sys.service.IntegralConfigService;
import com.aylson.dc.sys.vo.AttachmentVo;
import com.aylson.dc.sys.vo.DictionaryVo;
import com.aylson.dc.sys.vo.IntegralConfigVo;
import com.aylson.utils.IHuiYiUtils;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;
import com.fastweixin.api.CustomAPI;
import com.fastweixin.api.config.ApiConfig;
import com.fastweixin.message.TextMsg;

@Service
@Transactional
public class ProjectInfoServiceImpl extends BaseServiceImpl<ProjectInfo,ProjectInfoSearch> implements ProjectInfoService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectInfoServiceImpl.class);
	
	@Autowired
	private ProjectInfoDao projectInfoDao;
	@Autowired
	private ProjectFlowNodeService projectFlowNodeService;
	@Autowired
	private ProjectDesignService projectDesignService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private MemAccountService memAccountService;
	@Autowired
	private IntegralConfigService integralConfigService;
	@Autowired
	private ProjectClientService projectClientService;             //会员分配客户资料服务
	
	@Override
	protected BaseDao<ProjectInfo,ProjectInfoSearch> getBaseDao() {
		return this.projectInfoDao;
	}

	/**
	 * 通用有效信息校验
	 * @param projectInfoVo
	 * @return
	 */
	@Override
	public Result commonValid(ProjectInfoVo projectInfoVo) {
		Result result = new Result();
		//信息校验，说明：修改和新增的差异性信息校验在控制器层处理
		if(StringUtil.isEmpty(projectInfoVo.getClientName())){
			result.setError(ResultCode.CODE_STATE_4006, "客户名称不能为空");
			return result;
		}
		if(StringUtil.isEmpty(projectInfoVo.getProjectName())){
			result.setError(ResultCode.CODE_STATE_4006, "工程名称不能为空");
			return result;
		}
		if(StringUtil.isEmpty(projectInfoVo.getProvince()) || projectInfoVo.getProvinceId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "请选择省份");
			return result;
		}
		if(StringUtil.isEmpty(projectInfoVo.getCity()) || projectInfoVo.getCityId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "请选择城市");
			return result;
		}
		if(StringUtil.isEmpty(projectInfoVo.getArea()) || projectInfoVo.getAreaId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "请选择城市区域");
			return result;
		}
		if(StringUtil.isEmpty(projectInfoVo.getClientAddress())){
			result.setError(ResultCode.CODE_STATE_4006, "客户地址不能为空");
			return result;
		}
		//客户手机号码
		if(StringUtil.isEmpty(projectInfoVo.getMobilePhone())){
			result.setError(ResultCode.CODE_STATE_4006, "手机号码不能为空");
			return result;
		}
		if(!VerificationUtils.isValid(projectInfoVo.getMobilePhone(), VerificationUtils.MOBILE)){
			result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
			return result;
		}
		if(StringUtil.isEmpty(projectInfoVo.getBudgetRange()) || projectInfoVo.getBudgetRangeId() == null || projectInfoVo.getBudgetRangeValue() == null){
			result.setError(ResultCode.CODE_STATE_4006, "请选择工程运算");
			return result;
		}
		result.setOK(ResultCode.CODE_STATE_200, "信息有效");
		return result;
	}
	
	/**
	 * （设计师会员）保存方案的客户资料
	 * @param projectInfoVo
	 * @return
	 */
	@Override
	@Transactional
	public Result savePorjectInfo(ProjectInfoVo projectInfoVo) {
		//信息校验，说明：修改和新增的差异性信息校验在控制器层处理
		Result result = this.commonValid(projectInfoVo);
		Boolean flag = false;
		Date curDate = new Date();
		if(!result.isSuccess()) return result;
		if(projectInfoVo.getMemberId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取当前登录人信息失败");
			return result;
		}
		if(projectInfoVo.getStatus() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取方案的状态失败");
			return result;
		}
		if(ProjectStatus.WAIT_SUMBIT_CLIENTINFO != projectInfoVo.getStatus().intValue()
				&& ProjectStatus.WAIT_VERIFY != projectInfoVo.getStatus().intValue()
				 && ProjectStatus.WAIT_RESUMBIT_CLIENTINFO != projectInfoVo.getStatus().intValue()){
			result.setError(ResultCode.CODE_STATE_4006, "无效的方案状态");
			return result;
		}
		//业务处理：保存或更新客户资料；添加流程节点，发送提示短信
		if(projectInfoVo.getId() != null){//本次操作：修改（状态为1,2,11）
			flag = this.edit(projectInfoVo);
		}else{//本次操作：新增
			projectInfoVo.setCreateTime(curDate);
			flag = this.add(projectInfoVo);
		}
		if(flag){//添加流程节点
			ProjectFlowNode projectFlowNode = new ProjectFlowNode();
			projectFlowNode.setCreateTime(curDate);
			projectFlowNode.setOper(projectInfoVo.getOper());
			projectFlowNode.setOperId(projectInfoVo.getOperId());
			projectFlowNode.setProjectId(projectInfoVo.getId());
			projectFlowNode.setStatus(projectInfoVo.getStatus());
			projectFlowNode.setRemark(ProjectStatus.ProjectStatusMap.get(projectInfoVo.getStatus()));
			flag = this.projectFlowNodeService.add(projectFlowNode);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "保存流程节点信息失败");
				throw new ServiceException("保存流程节点信息失败");
			}
			//发送通知短信
			if(!SystemConfig.isDebugMode() && ProjectStatus.WAIT_VERIFY == projectInfoVo.getStatus().intValue()){//非调试模式并且保存并提交才发送短信
				String smsContent = SmsTemplate.getSmsWhenSubmitClientInfo(projectInfoVo.getMemberName()+"("+projectInfoVo.getMemberPhone()+")", projectInfoVo.getChannel(), new Date(), projectInfoVo.getProjectName());
				DictionarySearch noticePhoneSearch = new DictionarySearch();
				noticePhoneSearch.setDicType("SmsNoticePhone_type");
				List<DictionaryVo> noticePhoneList = this.dictionaryService.getList(noticePhoneSearch);
				for(DictionaryVo temp : noticePhoneList){
					if(VerificationUtils.isValid(temp.getDicValue(), VerificationUtils.MOBILE)){
						//有效的电话号码才发短信
						IHuiYiUtils.sentSms(temp.getDicValue(), smsContent);
					}else{
						System.out.println("======短信通知电话无效："+temp.getDicValue()+"==========");
					}
				}
			}
			result.setOK(ResultCode.CODE_STATE_200, "保存成功",projectInfoVo);
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "保存工程客户资料信息失败");
			throw new ServiceException("保存工程客户资料信息失败");
		}
		return result;
	}
	
	/**
	 * (aylson)审核方案的客户资料
	 * @param projectInfoVo
	 * @return
	 */
	@Override
	@Transactional
	public Result verifyPorjectInfo(ProjectInfoVo projectInfoVo) {
		Result result = new Result();
		//信息校验
		if(projectInfoVo.getStatus() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取方案的状态失败");
			return result;
		}
		if(ProjectStatus.HAD_VERIFY != projectInfoVo.getStatus().intValue()
				&& ProjectStatus.WAIT_RESUMBIT_CLIENTINFO != projectInfoVo.getStatus().intValue()){
			result.setError(ResultCode.CODE_STATE_4006, "无效的方案状态");
			return result;
		}
		//业务逻辑处理：
		//保存工程资料，添加流程节点，
		//如果审核通过，发送通知短信，同时如果还没有赠送过积分，赠送积分
		//如果审核不通过，发送通知短信
		if(ProjectStatus.HAD_VERIFY == projectInfoVo.getStatus() && !projectInfoVo.getIsSent()){//审核通过并且没有赠送积分
			//标记赠送过积分
			projectInfoVo.setIsSent(true);
			//提交客户资料赠送会员的积分值
			Integer submiter = this.integralConfigService.getIntegral(IntegralConfigType.D_SUBMIT, null);  
			//赠送积分
			if(submiter != 0){
				MemIntegralDetailVo memIntegralDetailVo = new MemIntegralDetailVo();
				memIntegralDetailVo.setIntegral(submiter);
				memIntegralDetailVo.setCreateTime(new Date());
				memIntegralDetailVo.setSourceType(SourceType.PROGRAMDESIGN);
				memIntegralDetailVo.setSourceId(projectInfoVo.getId());
				memIntegralDetailVo.setMemberId(projectInfoVo.getMemberId());
				memIntegralDetailVo.setType(IntegeralType.SEND_BY_VERIFY_PROJECTINFO);
				memIntegralDetailVo.setDescription("方案客户资料审核通过赠送");
				projectInfoVo.setIntegral(projectInfoVo.getIntegral() + submiter);//添加工程积分
				result = this.memAccountService.updateIntegral(memIntegralDetailVo);
				if(result.isSuccess()){
					if(projectInfoVo.getReferralId() != null){//存在我的推荐人
						//提交客户资料赠送会员推荐人的积分值
						Integer myReferral = this.integralConfigService.getIntegral(IntegralConfigType.D_SUBMIT_TO_REFERRAL, null);
						if(myReferral != 0){//积分配置不为0，需要赠送
							memIntegralDetailVo.setIntegral(myReferral);
							memIntegralDetailVo.setMemberId(projectInfoVo.getReferralId());
							memIntegralDetailVo.setType(IntegeralType.SEND_BY_VERIFY_PROJECTINFO_REFERRAL);
							result = this.memAccountService.updateIntegral(memIntegralDetailVo);
						    if(!result.isSuccess()){
						    	result.setError(ResultCode.CODE_STATE_4006, "审核通过赠送会员的推荐人积分失败");
								throw new ServiceException("审核通过赠送会员的推荐人积分失败");
						    }
						}
					}
				}else{
					result.setError(ResultCode.CODE_STATE_4006, "审核通过赠送会员积分失败");
					throw new ServiceException("审核通过赠送会员积分失败");
				}
			}
		}
		//赠送积分成功,更新工程资料
		Boolean flag = this.edit(projectInfoVo);
		if(flag){//添加流程节点
			ProjectFlowNode projectFlowNode = new ProjectFlowNode();
			projectFlowNode.setCreateTime(new Date());
			projectFlowNode.setOper(projectInfoVo.getOper());
			projectFlowNode.setOperId(projectInfoVo.getOperId());
			projectFlowNode.setProjectId(projectInfoVo.getId());
			projectFlowNode.setStatus(projectInfoVo.getStatus());
			projectFlowNode.setRemark(projectInfoVo.getAuditOpition());//审核意见
			flag = this.projectFlowNodeService.add(projectFlowNode);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "保存流程节点信息失败");
				throw new ServiceException("保存流程节点信息失败");
			}
			String wxMessage = null;
			if(!SystemConfig.isDebugMode()){//非调试模式才发送短信
				String smsContent = null;
				if(ProjectStatus.HAD_VERIFY == projectInfoVo.getStatus().intValue()){//审核通过
					smsContent = SmsTemplate.getSmsWhenVerifyClientInfo(projectInfoVo.getProjectName());
					wxMessage = this.getWxMessage(projectInfoVo.getProjectName(), projectInfoVo.getId(), null,4,projectInfoVo.getMemberType());
				}else if(ProjectStatus.WAIT_RESUMBIT_CLIENTINFO == projectInfoVo.getStatus().intValue()){//审核不通过
					smsContent = SmsTemplate.getSmsWhenClientInfoImperfect(projectInfoVo.getProjectName());
					wxMessage = this.getWxMessage(projectInfoVo.getProjectName(), projectInfoVo.getId(),null, 3,projectInfoVo.getMemberType());
				}
				if(StringUtil.isNotEmpty(smsContent) && VerificationUtils.isValid(projectInfoVo.getMemberPhone(), VerificationUtils.MOBILE)){
					//有效的电话号码才发短信
					IHuiYiUtils.sentSms(projectInfoVo.getMemberPhone(), smsContent);
				}else{
					System.out.println("======短信通知电话无效或内容为空："+projectInfoVo.getMemberPhone()+":"+smsContent+"==========");
				}
			}
			//发送微信信息
			if(StringUtil.isNotEmpty(projectInfoVo.getWxOpenId()) && wxMessage != null){
				this.sendWeixinMesage(projectInfoVo.getWxOpenId(), wxMessage);
			}
			result.setOK(ResultCode.CODE_STATE_200, "保存成功",projectInfoVo);
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "更新工程客户资料失败");
			throw new ServiceException("更新工程客户资料失败");
		}
		return result;
	}
	
	/**
	 * （设计师会员）保存方案的设计需求
	 * @param projectDesignVo
	 * @return
	 */
	@Override
	@Transactional
	public Result saveRequirement(ProjectDesignVo projectDesignVo) {
		//信息校验，说明：修改和新增的差异性信息校验在控制器层处理
		Result result = this.projectDesignService.commonValid(projectDesignVo);
		Boolean flag = false;
		if(!result.isSuccess()) return result;
		/*ProjectDesignSearch projectDesignSearch = new ProjectDesignSearch();
		projectDesignSearch.setProjectId(projectDesignVo.getProjectId());
		long rows = this.projectDesignService.getRowCount(projectDesignSearch);
		if(rows == 1){
			result.setError(ResultCode.CODE_STATE_4006, "方案需求已经存在");
			return result;
		}*/
		//业务逻辑处理：
		//新增：状态为提交 ，如果未扣除金币，需要扣除     修改：需求审核不通过/确认不通过
		//添加流程节点、发送通知短信，修改方案状态
		ProjectInfoVo projectInfoVo = this.getById(projectDesignVo.getProjectId());
		if(projectInfoVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取方案信息失败");
			return result;
		}
		Date createTime = new Date(); //当前时间
		if(projectDesignVo.getId() != null){//修改
			//如果有旧的附件，先作废
			AttachmentSearch attachmentSearch = new AttachmentSearch();
			attachmentSearch.setType(AttachmentType.PROJECT_PROGRAM);
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
			Integer gold = SystemConfig.payGoldForPorject();
			if(gold != 0){
				MemGoldDetailVo memGoldDetailVo = new MemGoldDetailVo();
				memGoldDetailVo.setCreateTime(createTime);
				memGoldDetailVo.setGold(-gold);
				memGoldDetailVo.setMemberId(projectInfoVo.getMemberId());
				memGoldDetailVo.setSourceId(projectDesignVo.getProjectId());
				memGoldDetailVo.setSourceType(SourceType.PROGRAMDESIGN);
				memGoldDetailVo.setDescription(GoldType.GoldTypeMap.get(GoldType.SUBMIT_PROJECT_REQUIREMENT));
				memGoldDetailVo.setType(GoldType.SUBMIT_PROJECT_REQUIREMENT);
				result = this.memAccountService.updateGold(memGoldDetailVo);
				if(!result.isSuccess()){
					return result;
				}
				projectDesignVo.setGold(gold);
			}
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
				attachmentVo.setType(AttachmentType.PROJECT_PROGRAM);
				attachmentVo.setRemark(AttachmentType.AttachmentTypeMap.get(AttachmentType.PROJECT_PROGRAM));
				requirementList.add(attachmentVo);
			}
			flag = this.attachmentService.batchAdd(requirementList);
			if(flag){//保存方案需求附件成功，
				if(projectDesignVo.getId() != null){
					flag = this.projectDesignService.edit(projectDesignVo);
				}else{
					flag = this.projectDesignService.add(projectDesignVo);
				}
				if(!flag){
					result.setError(ResultCode.CODE_STATE_4006, "保存方案信息失败");
					throw new ServiceException("保存方案信息失败");
				}
				projectInfoVo.setStatus(ProjectStatus.HAD_SUMBIT_PROGRAM);
				flag = this.edit(projectInfoVo);//更新方案的状态
				if(flag){//添加流程节点
					ProjectFlowNode projectFlowNode = new ProjectFlowNode();
					projectFlowNode.setCreateTime(createTime);
					projectFlowNode.setOper(projectInfoVo.getAccountName());
					projectFlowNode.setOperId(projectInfoVo.getMemberId());
					projectFlowNode.setProjectId(projectInfoVo.getId());
					projectFlowNode.setStatus(projectInfoVo.getStatus());
					projectFlowNode.setRemark(ProjectStatus.ProjectStatusMap.get(projectInfoVo.getStatus()));
					flag = this.projectFlowNodeService.add(projectFlowNode);
					if(!flag){
						result.setError(ResultCode.CODE_STATE_4006, "保存流程节点信息失败");
						throw new ServiceException("保存流程节点信息失败");
					}
					//发送通知短信
					if(!SystemConfig.isDebugMode()){//非调试模式才发送短信
						String memberName = projectInfoVo.getRealName();
						if(StringUtil.isEmpty(memberName)){
							memberName = projectInfoVo.getAccountName();
						}
						String smsContent = SmsTemplate.getSmsWhenSubmitRequire(memberName+"("+projectInfoVo.getMemberPhone()+")", projectDesignVo.getChannel(), new Date(), projectInfoVo.getProjectName());
						DictionarySearch noticePhoneSearch = new DictionarySearch();
						noticePhoneSearch.setDicType("SmsNoticePhone_type");
						List<DictionaryVo> noticePhoneList = this.dictionaryService.getList(noticePhoneSearch);
						for(DictionaryVo temp : noticePhoneList){
							if(VerificationUtils.isValid(temp.getDicValue(), VerificationUtils.MOBILE)){
								//有效的电话号码才发短信
								IHuiYiUtils.sentSms(temp.getDicValue(), smsContent);
							}else{
								System.out.println("======短信通知电话无效："+temp.getDicValue()+"==========");
							}
						}
					}
					projectInfoVo.setProjectDesignVo(projectDesignVo);
					result.setOK(ResultCode.CODE_STATE_200, "保存成功",projectInfoVo);
				}
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "保存方案需求附件失败，请稍后再试");
				throw new ServiceException("保存方案需求附件失败");
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "保存方案需求附件失败，请稍后再试");
			throw new ServiceException("保存方案需求附件失败");
		}
		return result;
	}
	
	/**
	 * (aylson)退回设计需求
	 * @return
	 */
	@Override
	@Transactional
	public Result returnRequirement(ProjectInfoVo projectInfoVo){
		Result result = new Result();
		Boolean flag = false;
		//信息校验
		if(projectInfoVo.getId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取工程信息失败");
			return result;
		}
		if(projectInfoVo.getMemberId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取会员信息失败");
			return result;
		}
		if(projectInfoVo.getStatus() == null && ProjectStatus.HAD_SUMBIT_PROGRAM != projectInfoVo.getStatus()){
			result.setError(ResultCode.CODE_STATE_4006, "无效的方案状态");
			return result;
		}
		if(StringUtil.isEmpty(projectInfoVo.getOper()) || projectInfoVo.getOperId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取当前操作人信息失败");
			return result;
		}
		if(StringUtil.isEmpty(projectInfoVo.getMemberPhone())){
			result.setError(ResultCode.CODE_STATE_4006, "获取会员手机信息失败");
			return result;
		}
		if(StringUtil.isEmpty(projectInfoVo.getAuditOpition())){
			result.setError(ResultCode.CODE_STATE_4006, "退回原因不能为空");
			return result;
		}
		
		//业务处理：退回方案需求（修改方案状态，添加流程节点，短信通知会员）
		projectInfoVo.setStatus(ProjectStatus.VERIFY_RESUMBIT_PROGRAM);
		flag = this.edit(projectInfoVo);
		if(flag){//添加流程节点
			ProjectFlowNode projectFlowNode = new ProjectFlowNode();
			projectFlowNode.setCreateTime(new Date());
			projectFlowNode.setOper(projectInfoVo.getOper());
			projectFlowNode.setOperId(projectInfoVo.getOperId());
			projectFlowNode.setProjectId(projectInfoVo.getId());
			projectFlowNode.setStatus(projectInfoVo.getStatus());
			projectFlowNode.setRemark(projectInfoVo.getAuditOpition());
			flag = this.projectFlowNodeService.add(projectFlowNode);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "保存流程节点信息失败");
				throw new ServiceException("保存流程节点信息失败");
			}
			String smsContent = SmsTemplate.getSmsWhenRequireImperfect(projectInfoVo.getProjectName());
			if(!SystemConfig.isDebugMode()){//非调试模式才发送短信
				if(StringUtil.isNotEmpty(smsContent) && VerificationUtils.isValid(projectInfoVo.getMemberPhone(), VerificationUtils.MOBILE)){
					//有效的电话号码才发短信
					IHuiYiUtils.sentSms(projectInfoVo.getMemberPhone(), smsContent);
				}else{
					System.out.println("======短信通知电话无效或内容为空："+projectInfoVo.getMemberPhone()+":"+smsContent+"==========");
				}
			}
			//发送微信信息
			if(StringUtil.isNotEmpty(projectInfoVo.getWxOpenId())){
				String wxMessage = this.getWxMessage(projectInfoVo.getProjectName(), projectInfoVo.getId(),null, 1,projectInfoVo.getMemberType());
				if(wxMessage != null){
					this.sendWeixinMesage(projectInfoVo.getWxOpenId(), wxMessage);
				}
			}
			result.setOK(ResultCode.CODE_STATE_200, "保存成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "更新工程客户资料失败");
			throw new ServiceException("更新工程客户资料失败");
		}
		return result;
	}
	
	/**
	 * (aylson)保存方案设计内容
	 * @param projectDesignVo
	 * @return
	 */
	@Override
	@Transactional
	public Result saveDesign(ProjectInfoVo projectInfoVo){
		Result result = new Result();
		Boolean flag = false;
		//有效性校验
		if(StringUtil.isEmpty(projectInfoVo.getAttachments())){
			result.setError(ResultCode.CODE_STATE_4006, "请上传设计好的附件");
			return result;
		}
		if(projectInfoVo.getId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取工程信息失败");
			return result;
		}
		if(projectInfoVo.getMemberId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取会员信息失败");
			return result;
		}
		if(projectInfoVo.getStatus() == null && ProjectStatus.HAD_SUMBIT_PROGRAM != projectInfoVo.getStatus()){
			result.setError(ResultCode.CODE_STATE_4006, "无效的方案状态");
			return result;
		}
		if(StringUtil.isEmpty(projectInfoVo.getOper()) || projectInfoVo.getOperId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取当前操作人信息失败");
			return result;
		}
		if(StringUtil.isEmpty(projectInfoVo.getMemberPhone())){
			result.setError(ResultCode.CODE_STATE_4006, "获取会员手机信息失败");
			return result;
		}
		//业务处理：保存上传的设计附件，更新方案状态，短信通知会员，添加流程节点
		//如果有旧的附件，先作废
		AttachmentSearch attachmentSearch = new AttachmentSearch();
		attachmentSearch.setType(AttachmentType.PROJECT_DESIGN);
		attachmentSearch.setSourceId(projectInfoVo.getId());
		List<Attachment> requirementListBefore = this.attachmentService.getList(attachmentSearch);
		if(requirementListBefore != null && requirementListBefore.size() > 0){
			for(Attachment temp : requirementListBefore){
				temp.setStatus(0);//作废
			}
			flag = this.attachmentService.batchUpdate(requirementListBefore);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "处理旧的方案设计附件失败，请稍后再试");
				throw new ServiceException("处理旧的方案设计附件失败");
			}
		}
		String[] attachmentArray =  projectInfoVo.getAttachments().split(",");
		if(attachmentArray != null && attachmentArray.length > 0){
			List<AttachmentVo> attachmentList = new ArrayList<AttachmentVo>();
			Date createTime = new Date();
			for(int i=0; i<attachmentArray.length; i++){
				if(StringUtil.isEmpty(attachmentArray[i])){continue;}
				AttachmentVo attachmentVo = new AttachmentVo();
				attachmentVo.setAttachAddress(attachmentArray[i]);
				attachmentVo.setCreateTime(createTime);
				attachmentVo.setType(AttachmentType.PROJECT_DESIGN);
				attachmentVo.setRemark(AttachmentType.AttachmentTypeMap.get(AttachmentType.PROJECT_DESIGN));
				attachmentVo.setSourceId(projectInfoVo.getId());
				attachmentList.add(attachmentVo);
			}
			flag = this.attachmentService.batchAdd(attachmentList);
			if(flag){
				projectInfoVo.setStatus(ProjectStatus.HAD_SUMBIT_DESIGN);
				flag = this.edit(projectInfoVo);
				if(flag){
					ProjectFlowNode projectFlowNode = new ProjectFlowNode();
					projectFlowNode.setCreateTime(new Date());
					projectFlowNode.setOper(projectInfoVo.getOper());
					projectFlowNode.setOperId(projectInfoVo.getOperId());
					projectFlowNode.setProjectId(projectInfoVo.getId());
					projectFlowNode.setStatus(projectInfoVo.getStatus());
					projectFlowNode.setRemark(ProjectStatus.ProjectStatusMap.get(projectInfoVo.getStatus()));
					flag = this.projectFlowNodeService.add(projectFlowNode);
					if(!flag){
						result.setError(ResultCode.CODE_STATE_4006, "保存流程节点信息失败");
						throw new ServiceException("保存流程节点信息失败");
					}
					String smsContent = SmsTemplate.getSmsWhenFinishDesign(projectInfoVo.getProjectName());
					if(!SystemConfig.isDebugMode()){//非调试模式才发送短信
						if(StringUtil.isNotEmpty(smsContent) && VerificationUtils.isValid(projectInfoVo.getMemberPhone(), VerificationUtils.MOBILE)){
							//有效的电话号码才发短信
							IHuiYiUtils.sentSms(projectInfoVo.getMemberPhone(), smsContent);
						}else{
							System.out.println("======短信通知电话无效或内容为空："+projectInfoVo.getMemberPhone()+":"+smsContent+"==========");
						}
					}
					//发送微信信息
					if(StringUtil.isNotEmpty(projectInfoVo.getWxOpenId())){
						String wxMessage = this.getWxMessage(projectInfoVo.getProjectName(), projectInfoVo.getId(),null, 2,projectInfoVo.getMemberType());
						if(wxMessage != null){
							this.sendWeixinMesage(projectInfoVo.getWxOpenId(), wxMessage);
						}
					}
					result.setOK(ResultCode.CODE_STATE_200, "保存成功");
				}else{
					result.setError(ResultCode.CODE_STATE_4006, "更新方案资料失败");
					throw new ServiceException("更新方案资料失败");
				}
			}else{
				
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "保存上传的设计附件失败");
			return result;
		}
		return result;
	}

	/**
	 * （设计师会员）确认方案设计
	 * @param projectDesignVo
	 * @return
	 */
	@Override
	@Transactional
	public Result confirmDesign(ProjectInfoVo projectInfoVo){
		Result result = new Result();
		Boolean flag = false;
		//信息校验
		//业务处理：添加流程节点，更新状态，发送通知短信
		projectInfoVo.setStatus(ProjectStatus.HAD_SUMBIT_CONFIRM);
		flag = this.edit(projectInfoVo);
		if(flag){//添加流程节点
			ProjectFlowNode projectFlowNode = new ProjectFlowNode();
			projectFlowNode.setCreateTime(new Date());
			projectFlowNode.setOper(projectInfoVo.getAccountName());
			projectFlowNode.setOperId(projectInfoVo.getMemberId());
			projectFlowNode.setProjectId(projectInfoVo.getId());
			projectFlowNode.setStatus(projectInfoVo.getStatus());
			projectFlowNode.setRemark(ProjectStatus.ProjectStatusMap.get(projectInfoVo.getStatus()));
			flag = this.projectFlowNodeService.add(projectFlowNode);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "保存流程节点信息失败");
				throw new ServiceException("保存流程节点信息失败");
			}
			//发送通知短信
			/*if(!SystemConfig.isDebugMode()){//非调试模式才发送短信
				String smsContent = SmsTemplate.getSmsWhenSubmitClientInfo(projectInfoVo.getMemberName(), "官网", new Date(), projectInfoVo.getProjectName());
				DictionarySearch noticePhoneSearch = new DictionarySearch();
				noticePhoneSearch.setDicType("SmsNoticePhone_type");
				List<DictionaryVo> noticePhoneList = this.dictionaryService.getList(noticePhoneSearch);
				for(DictionaryVo temp : noticePhoneList){
					if(VerificationUtils.isValid(temp.getDicValue(), VerificationUtils.MOBILE)){
						//有效的电话号码才发短信
						IHuiYiUtils.sentSms(temp.getDicValue(), smsContent);
					}else{
						System.out.println("======短信通知电话无效："+temp.getDicValue()+"==========");
					}
				}
			}*/
			result.setOK(ResultCode.CODE_STATE_200, "保存成功",projectInfoVo);
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "保存工程客户资料信息失败");
			throw new ServiceException("保存工程客户资料信息失败");
		}
		return result;
	}
	
	/**
	 * （设计师会员）不满意设计结果
	 * @param projectInfoVo
	 * @return
	 */
	@Override
	@Transactional
	public Result returnDesign(ProjectInfoVo projectInfoVo){
		Result result = new Result();
		//1、信息校验
		if(projectInfoVo.getId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取工程信息失败");
			return result;
		}
		if(StringUtil.isEmpty(projectInfoVo.getAuditOpition())){
			result.setError(ResultCode.CODE_STATE_4006, "请输入退回原因，让我们能更好的完善设计！谢谢");
			return result;
		}
		//2、业务处理：更新工程状态，保存退回原因，添加节点，发送短信
		projectInfoVo.setStatus(ProjectStatus.WAIT_RESUMBIT_DESIGN);
		if(StringUtil.isNotEmpty(projectInfoVo.getAuditOpition())){
			projectInfoVo.setRemark(projectInfoVo.getAuditOpition());
		}
		Boolean flag = this.edit(projectInfoVo);
		if(flag){
			ProjectFlowNode projectFlowNode = new ProjectFlowNode();
			projectFlowNode.setCreateTime(new Date());
			projectFlowNode.setOper(projectInfoVo.getOper());
			projectFlowNode.setOperId(projectInfoVo.getOperId());
			projectFlowNode.setProjectId(projectInfoVo.getId());
			projectFlowNode.setStatus(projectInfoVo.getStatus());
			projectFlowNode.setRemark(projectInfoVo.getAuditOpition());
			flag = this.projectFlowNodeService.add(projectFlowNode);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "保存流程节点信息失败");
				throw new ServiceException("保存流程节点信息失败");
			}
			//发送通知短信
			if(!SystemConfig.isDebugMode()){//非调试模式才发送短信
				String memberName = projectInfoVo.getRealName();
				if(StringUtil.isEmpty(memberName)){
					memberName = projectInfoVo.getAccountName();
				}
				String smsContent = SmsTemplate.getSmsWhenReturnDesign(projectInfoVo.getProjectName(),memberName+"("+projectInfoVo.getMemberPhone()+")");
				DictionarySearch noticePhoneSearch = new DictionarySearch();
				noticePhoneSearch.setDicType("SmsNoticePhone_type");
				List<DictionaryVo> noticePhoneList = this.dictionaryService.getList(noticePhoneSearch);
				for(DictionaryVo temp : noticePhoneList){
					if(VerificationUtils.isValid(temp.getDicValue(), VerificationUtils.MOBILE)){
						//有效的电话号码才发短信
						IHuiYiUtils.sentSms(temp.getDicValue(), smsContent);
					}else{
						System.out.println("======短信通知电话无效："+temp.getDicValue()+"==========");
					}
				}
			}
			result.setOK(ResultCode.CODE_STATE_200, "保存成功",projectInfoVo);
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "保存工程客户资料信息失败");
			throw new ServiceException("保存工程客户资料信息失败");
		}
		return result;
	}
	
	/**
	 * 评价方案设计
	 * @param projectDesignVo
	 */
	@Override
	@Transactional
	public Result evaluate(ProjectDesignVo projectDesignVo) {
		Result result = new Result();
		//1、信息校验
		if(projectDesignVo.getId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取工程客户资料失败");
			return result;
		}
		if(projectDesignVo.getEvalScore() == null){
			result.setError(ResultCode.CODE_STATE_4006, "请选择一个评价分数");
			return result;
		}
		if(projectDesignVo.getProjectId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取工程信息失败");
			return result;
		}
		//2、保存评价信息
		ProjectDesignVo evaluateInfo = new ProjectDesignVo();
		evaluateInfo.setId(projectDesignVo.getId());
		evaluateInfo.setEvalScore(projectDesignVo.getEvalScore());
		evaluateInfo.setEvalContent(projectDesignVo.getEvalContent());
		Boolean flag = this.projectDesignService.edit(evaluateInfo);
		if(flag){
			ProjectInfoVo projectInfoVo = new ProjectInfoVo();
			projectInfoVo.setId(projectDesignVo.getProjectId());
			projectInfoVo.setStatus(ProjectStatus.HAD_EVALUATE);
			flag = this.edit(projectInfoVo);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "更新工程状态失败");
				throw new ServiceException("更新工程状态失败");
			}
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
		}
		return result;
	}
	
	/**
	 * (aylson)结算方案
	 * @param projectInfoVo
	 * @return
	 */
	@Transactional
	@Override
	public Result settleProject(ProjectInfoVo projectInfoVo){
		Result result = new Result();
		//信息校验
		if(projectInfoVo.getId() == null || projectInfoVo.getProjectDesignId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取工程信息失败");
			return result;
		}
		if(projectInfoVo.getAmount() == null){
			result.setError(ResultCode.CODE_STATE_4006, "结算金额不能为空");
			return result;
		}
		if(projectInfoVo.getAmount() < 0.0f ){
			result.setError(ResultCode.CODE_STATE_4006, "结算金额不能小于0");
			return result;
		}
		if(projectInfoVo.getStatus() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取工程状态失败");
			return result;
		}
		if(ProjectStatus.HAD_SUMBIT_CONFIRM != projectInfoVo.getStatus().intValue() && ProjectStatus.HAD_EVALUATE != projectInfoVo.getStatus().intValue()){
			result.setError(ResultCode.CODE_STATE_4006, "客户还没有确认不能结算");
			return result;
		}
		if(ProjectStatus.HAD_SETTLE == projectInfoVo.getStatus().intValue()){
			result.setError(ResultCode.CODE_STATE_4006, "已经结算过了");
			return result;
		}
		//业务处理：更新方案状态，更新设计金额，赠送积分，发送短信通知，添加流程节点
		//
		Integer submiter = this.integralConfigService.getIntegral(IntegralConfigType.D_SETTLE, projectInfoVo.getAmount()/10000);
		if(submiter != 0){
			Date curTime = new Date();
			MemIntegralDetailVo memIntegralDetailVo = new MemIntegralDetailVo();
			memIntegralDetailVo.setCreateTime(curTime);
			memIntegralDetailVo.setSourceType(SourceType.PROGRAMDESIGN);
			memIntegralDetailVo.setSourceId(projectInfoVo.getId());
			memIntegralDetailVo.setIntegral(submiter);
			memIntegralDetailVo.setMemberId(projectInfoVo.getMemberId());
			memIntegralDetailVo.setDescription("方案完成结算赠送");
			memIntegralDetailVo.setType(IntegeralType.SEND_BY_SETTLE);
			projectInfoVo.setIntegral(projectInfoVo.getIntegral() + submiter);//添加工程积分
			result = this.memAccountService.updateIntegral(memIntegralDetailVo);
			if(result.isSuccess()){
				if(projectInfoVo.getReferralId() != null){//存在我的推荐人
					//提交客户资料赠送会员推荐人的积分值
					Integer myReferral = this.integralConfigService.getIntegral(IntegralConfigType.D_SETTLE_TO_REFERRAL, projectInfoVo.getAmount()/10000);
					if(myReferral != 0){//积分配置不为0，需要赠送
						memIntegralDetailVo.setCreateTime(curTime);
						memIntegralDetailVo.setSourceType(SourceType.PROGRAMDESIGN);
						memIntegralDetailVo.setSourceId(projectInfoVo.getId());
						memIntegralDetailVo.setIntegral(myReferral);
						memIntegralDetailVo.setMemberId(projectInfoVo.getReferralId());
						memIntegralDetailVo.setType(IntegeralType.SEND_BY_SETTLE_REFERRAL);
						memIntegralDetailVo.setDescription("方案完成结算赠送给推荐人");
						result = this.memAccountService.updateIntegral(memIntegralDetailVo);
					    if(!result.isSuccess()){
					    	result.setError(ResultCode.CODE_STATE_4006, "结算赠送会员的推荐人积分失败");
							throw new ServiceException("结算赠送会员的推荐人积分失败");
					    }
					}
				}
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "结算赠送会员积分失败");
				throw new ServiceException("结算赠送会员积分失败");
			}
		}
		projectInfoVo.setStatus(ProjectStatus.HAD_SETTLE);
		Boolean flag = this.edit(projectInfoVo);//更新状态
		if(flag){
			ProjectDesignVo projectDesignVo = new ProjectDesignVo();
			projectDesignVo.setId(projectInfoVo.getProjectDesignId());
			projectDesignVo.setAmount(projectInfoVo.getAmount());
			projectDesignVo.setRemark(projectInfoVo.getRemark());
			flag = this.projectDesignService.edit(projectDesignVo);
			if(flag){//添加流程节点
				ProjectFlowNode projectFlowNode = new ProjectFlowNode();
				projectFlowNode.setCreateTime(new Date());
				projectFlowNode.setOper(projectInfoVo.getOper());
				projectFlowNode.setOperId(projectInfoVo.getOperId());
				projectFlowNode.setProjectId(projectInfoVo.getId());
				projectFlowNode.setStatus(projectInfoVo.getStatus());
				projectFlowNode.setRemark(ProjectStatus.ProjectStatusMap.get(projectInfoVo.getStatus()));
				flag = this.projectFlowNodeService.add(projectFlowNode);
				if(!flag){
					result.setError(ResultCode.CODE_STATE_4006, "保存流程节点信息失败");
					throw new ServiceException("保存流程节点信息失败");
				}
				String smsContent = SmsTemplate.getSmsWhenSettle(projectInfoVo.getProjectName(),submiter);
				if(!SystemConfig.isDebugMode()){//非调试模式才发送短信
					if(StringUtil.isNotEmpty(smsContent) && VerificationUtils.isValid(projectInfoVo.getMemberPhone(), VerificationUtils.MOBILE)){
						//有效的电话号码才发短信
						IHuiYiUtils.sentSms(projectInfoVo.getMemberPhone(), smsContent);
					}else{
						System.out.println("======短信通知电话无效或内容为空："+projectInfoVo.getMemberPhone()+":"+smsContent+"==========");
					}
				}
				//发送微信信息
				if(StringUtil.isNotEmpty(projectInfoVo.getWxOpenId())){
					this.sendWeixinMesage(projectInfoVo.getWxOpenId(), smsContent);
				}
				result.setOK(ResultCode.CODE_STATE_200, "保存成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "更新金额信息失败");
				throw new ServiceException("更新金额信息失败");
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "更新方案信息失败");
			throw new ServiceException("更新方案信息失败");
		}
		return result;
	}
	
	/**
	 * 审核产业工人提交的客户资料
	 * @param projectInfoVo
	 * @return
	 */
	@Override
	@Transactional
	public Result verifyWork(ProjectInfoVo projectInfoVo) {
		Result result = new Result();
		//1、信息校验
		if(projectInfoVo.getStatus() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取状态失败");
			return result;
		}
		if(ProjectStatus.HAD_VERIFY != projectInfoVo.getStatus().intValue()
				&& ProjectStatus.WAIT_RESUMBIT_CLIENTINFO != projectInfoVo.getStatus().intValue()){
			result.setError(ResultCode.CODE_STATE_4006, "未知的状态，不能审核");
			return result;
		}
		//2业务逻辑处理：
		//保存工程资料
		//如果审核通过，发送通知短信，同时如果还没有赠送过积分，赠送积分
		//如果审核不通过，发送通知短信
		Integer submiter = 0;
		if(ProjectStatus.HAD_VERIFY == projectInfoVo.getStatus() && !projectInfoVo.getIsSent()){//审核通过并且没有赠送积分
			//标记赠送过积分
			projectInfoVo.setIsSent(true);
			//提交客户资料赠送会员的积分值
			submiter = this.integralConfigService.getIntegral(IntegralConfigType.W_SUBMIT, null);  
			//赠送积分
			if(submiter != 0){
				MemIntegralDetailVo memIntegralDetailVo = new MemIntegralDetailVo();
				memIntegralDetailVo.setIntegral(submiter);
				memIntegralDetailVo.setCreateTime(new Date());
				memIntegralDetailVo.setSourceType(SourceType.PROGRAMDESIGN);
				memIntegralDetailVo.setSourceId(projectInfoVo.getId());
				memIntegralDetailVo.setMemberId(projectInfoVo.getMemberId());
				memIntegralDetailVo.setType(IntegeralType.SEND_BY_VERIFY_PROJECTINFO);
				memIntegralDetailVo.setDescription("客户资料审核通过赠送");
				projectInfoVo.setIntegral(projectInfoVo.getIntegral() + submiter);//添加工程积分
				result = this.memAccountService.updateIntegral(memIntegralDetailVo);
				if(result.isSuccess()){
					if(projectInfoVo.getReferralId() != null){//存在我的推荐人
						//提交客户资料赠送会员推荐人的积分值
						Integer myReferral = this.integralConfigService.getIntegral(IntegralConfigType.W_SUBMIT_TO_REFERRAL, null);
						if(myReferral != 0){//积分配置不为0，需要赠送
							memIntegralDetailVo.setIntegral(myReferral);
							memIntegralDetailVo.setMemberId(projectInfoVo.getReferralId());
							memIntegralDetailVo.setType(IntegeralType.SEND_BY_VERIFY_PROJECTINFO_REFERRAL);
							result = this.memAccountService.updateIntegral(memIntegralDetailVo);
						    if(!result.isSuccess()){
						    	result.setError(ResultCode.CODE_STATE_4006, "审核通过赠送会员的推荐人积分失败");
								throw new ServiceException("审核通过赠送会员的推荐人积分失败");
						    }
						}
					}
				}else{
					result.setError(ResultCode.CODE_STATE_4006, "审核通过赠送会员积分失败");
					throw new ServiceException("审核通过赠送会员积分失败");
				}
			}
		}
		//赠送积分成功,更新工程资料
		Boolean flag = this.edit(projectInfoVo);
		if(flag){
			ProjectFlowNode projectFlowNode = new ProjectFlowNode();
			projectFlowNode.setCreateTime(new Date());
			projectFlowNode.setOper(projectInfoVo.getOper());
			projectFlowNode.setOperId(projectInfoVo.getOperId());
			projectFlowNode.setProjectId(projectInfoVo.getId());
			projectFlowNode.setStatus(projectInfoVo.getStatus());
			projectFlowNode.setRemark(projectInfoVo.getAuditOpition());//审核意见
			flag = this.projectFlowNodeService.add(projectFlowNode);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "保存流程节点信息失败");
				throw new ServiceException("保存流程节点信息失败");
			}
			String wxMessage = null;
			if(!SystemConfig.isDebugMode()){//非调试模式才发送短信
				String smsContent = null;
				if(ProjectStatus.HAD_VERIFY == projectInfoVo.getStatus().intValue()){//审核通过
					smsContent = SmsTemplate.getSmsWhenVerifyClientInfo(projectInfoVo.getProjectName(),submiter);
					wxMessage = this.getWxMessage(projectInfoVo.getProjectName(), projectInfoVo.getId(),projectInfoVo.getIntegral(), 5,projectInfoVo.getMemberType());
				}else if(ProjectStatus.WAIT_RESUMBIT_CLIENTINFO == projectInfoVo.getStatus().intValue()){//审核不通过
					smsContent = SmsTemplate.getSmsWhenClientInfoImperfect(projectInfoVo.getProjectName());
					wxMessage = this.getWxMessage(projectInfoVo.getProjectName(), projectInfoVo.getId(),null, 6,projectInfoVo.getMemberType());
				}
				if(StringUtil.isNotEmpty(smsContent) && VerificationUtils.isValid(projectInfoVo.getMemberPhone(), VerificationUtils.MOBILE)){
					//有效的电话号码才发短信
					IHuiYiUtils.sentSms(projectInfoVo.getMemberPhone(), smsContent);
				}else{
					System.out.println("======短信通知电话无效或内容为空："+projectInfoVo.getMemberPhone()+":"+smsContent+"==========");
				}
			}
			//发送微信信息
			if(StringUtil.isNotEmpty(projectInfoVo.getWxOpenId()) && wxMessage != null){
				this.sendWeixinMesage(projectInfoVo.getWxOpenId(), wxMessage);
			}
			result.setOK(ResultCode.CODE_STATE_200, "保存成功",projectInfoVo);
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "更新客户资料失败");
			throw new ServiceException("更新客户资料失败");
		}
		return result;
	}
	
	/**
	 * 审核开拓者提交的客户资料
	 * @param projectInfoVo
	 * @return
	 */
	@Override
	@Transactional
	public Result verifyPioneer(ProjectInfoVo projectInfoVo) {
		Result result = new Result();
		//1、信息校验
		if(projectInfoVo.getStatus() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取状态失败");
			return result;
		}
		if(ProjectStatus.HAD_VERIFY != projectInfoVo.getStatus().intValue()
				&& ProjectStatus.WAIT_RESUMBIT_CLIENTINFO != projectInfoVo.getStatus().intValue()){
			result.setError(ResultCode.CODE_STATE_4006, "未知的状态，不能审核");
			return result;
		}
		//2业务逻辑处理：
		//保存工程资料
		//如果审核通过，发送通知短信，同时如果还没有赠送过积分，赠送积分
		//如果审核不通过，发送通知短信
		//Integer submiter = 0;
		IntegralConfigVo submiter = null;
		if(ProjectStatus.HAD_VERIFY == projectInfoVo.getStatus() && !projectInfoVo.getIsSent()){//审核通过并且没有赠送积分
			//标记赠送过积分
			projectInfoVo.setIsSent(true);
			//提交客户资料赠送会员的积分值
			submiter = this.integralConfigService.getIntegralConfig(IntegralConfigType.P_SUBMITCLIENTINFO, null);
			//赠送积分
			if(submiter != null && submiter.getIntegral().intValue() != 0){
				MemIntegralDetail memIntegralDetail = new MemIntegralDetail(null, projectInfoVo.getMemberId(), IntegeralType.SEND_BY_VERIFY_PROJECTINFO
						, submiter.getIntegral(), new Date(), "客户资料审核通过赠送", SourceType.PROGRAMDESIGN, projectInfoVo.getId());
				projectInfoVo.setIntegral(projectInfoVo.getIntegral() + submiter.getIntegral());//添加工程积分
				result = this.memAccountService.updateIntegral(memIntegralDetail);
				if(result.isSuccess()){
					if(projectInfoVo.getReferralId() != null){//存在我的推荐人
						//提交客户资料赠送会员推荐人的积分值
						IntegralConfigVo myReferral = this.integralConfigService.getIntegralConfig(IntegralConfigType.P_SUBMITCLIENTINFO_TO_REFERRAL, null);
						if(myReferral != null && myReferral.getIntegral() != null && myReferral.getIntegral().intValue() != 0){//积分配置不为0，需要赠送
							MemIntegralDetail memIntegralDetailReferral = new MemIntegralDetail(null, projectInfoVo.getReferralId(), IntegeralType.SEND_BY_VERIFY_PROJECTINFO_REFERRAL
									, myReferral.getIntegral(), new Date(), "业主资料审核通过赠送", SourceType.PROGRAMDESIGN, projectInfoVo.getId());
							result = this.memAccountService.updateIntegral(memIntegralDetailReferral);
						    if(!result.isSuccess()){
						    	result.setError(ResultCode.CODE_STATE_4006, "审核通过赠送会员的推荐人积分失败");
								throw new ServiceException("审核通过赠送会员的推荐人积分失败");
						    }
						}
					}
				}else{
					result.setError(ResultCode.CODE_STATE_4006, "审核通过赠送会员积分失败");
					throw new ServiceException("审核通过赠送会员积分失败");
				}
			}
		}
		//赠送积分成功,更新工程资料
		Boolean flag = this.edit(projectInfoVo);
		if(flag){
			ProjectFlowNode projectFlowNode = new ProjectFlowNode();
			projectFlowNode.setCreateTime(new Date());
			projectFlowNode.setOper(projectInfoVo.getOper());
			projectFlowNode.setOperId(projectInfoVo.getOperId());
			projectFlowNode.setProjectId(projectInfoVo.getId());
			projectFlowNode.setStatus(projectInfoVo.getStatus());
			projectFlowNode.setRemark(projectInfoVo.getAuditOpition());//审核意见
			flag = this.projectFlowNodeService.add(projectFlowNode);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "保存流程节点信息失败");
				throw new ServiceException("保存流程节点信息失败");
			}
			String wxMessage = null;
			if(!SystemConfig.isDebugMode()){//非调试模式才发送短信
				String smsContent = null;
				if(ProjectStatus.HAD_VERIFY == projectInfoVo.getStatus().intValue()){//审核通过
					smsContent = SmsTemplate.getSmsWhenVerifyClientInfo(projectInfoVo.getProjectName(),submiter.getIntegral());
					wxMessage = this.getWxMessage(projectInfoVo.getProjectName(), projectInfoVo.getId(),projectInfoVo.getIntegral(), 5,projectInfoVo.getMemberType());
				}else if(ProjectStatus.WAIT_RESUMBIT_CLIENTINFO == projectInfoVo.getStatus().intValue()){//审核不通过
					smsContent = SmsTemplate.getSmsWhenClientInfoImperfect(projectInfoVo.getProjectName());
					wxMessage = this.getWxMessage(projectInfoVo.getProjectName(), projectInfoVo.getId(),null, 6,projectInfoVo.getMemberType());
				}
				if(StringUtil.isNotEmpty(smsContent) && VerificationUtils.isValid(projectInfoVo.getMemberPhone(), VerificationUtils.MOBILE)){
					//有效的电话号码才发短信
					IHuiYiUtils.sentSms(projectInfoVo.getMemberPhone(), smsContent);
				}else{
					System.out.println("======短信通知电话无效或内容为空："+projectInfoVo.getMemberPhone()+":"+smsContent+"==========");
				}
			}
			//发送微信信息
			if(StringUtil.isNotEmpty(projectInfoVo.getWxOpenId()) && wxMessage != null){
				this.sendWeixinMesage(projectInfoVo.getWxOpenId(), wxMessage);
			}
			result.setOK(ResultCode.CODE_STATE_200, "保存成功",projectInfoVo);
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "更新客户资料失败");
			throw new ServiceException("更新客户资料失败");
		}
		return result;
	}
	
	/**
	 * 根据项目id查看工程详情
	 * @param projectId
	 * @return
	 */
	@Override
	public ProjectInfoVo getProjectInfoDetail(Integer projectId) {
		ProjectInfoVo projectInfoVo = new ProjectInfoVo();
		if(projectId != null){
			//获取客户资料详情
			projectInfoVo = this.getById(projectId);
			//获取设计详情
			ProjectDesignVo projectDesignVo = this.projectDesignService.getByProjectId(projectId);
			if(projectDesignVo != null){
				//处理产品设计内容
				DictionarySearch dictionarySearch = new DictionarySearch();
				dictionarySearch.setDicType("ProductCategory_bigType");
				List<DictionaryVo> productTypesList = this.dictionaryService.getList(dictionarySearch);//获取产品类型列表
				if(!StringUtil.isEmpty(projectDesignVo.getProductTypeIds())){
					String[] productIdsArray = projectDesignVo.getProductTypeIds().split(",");
					for(int i=0; i<productIdsArray.length; i++){
						for(DictionaryVo dic : productTypesList){
							int productId = Integer.parseInt(productIdsArray[i]);
							if(dic.getId() == productId){
								dic.setCk(true);
								break;
							}
						}
					}
				}
				projectDesignVo.setProductTypesList(productTypesList);
				//处理欧玛尼系统内容
				dictionarySearch.setDicType("AutoSystem_type");
				List<DictionaryVo> autoSysList = this.dictionaryService.getList(dictionarySearch);     //获取智能系统列表
				if(!StringUtil.isEmpty(projectDesignVo.getAutoSysIds())){
					String[] autoSysIdsArray = projectDesignVo.getAutoSysIds().split(",");
					for(int i=0; i<autoSysIdsArray.length; i++){
						for(DictionaryVo dic : autoSysList){
							int autoSysId = Integer.parseInt(autoSysIdsArray[i]);
							if(dic.getId() == autoSysId){
								dic.setCk(true);
								break;
							}
						}
					}
				}
				projectDesignVo.setAutoSysList(autoSysList);
				//获取工程方案需求附件
				AttachmentSearch attachmentSearch = new AttachmentSearch();
				attachmentSearch.setSourceId(projectDesignVo.getProjectId());
				attachmentSearch.setStatus(1);//有效附件
				attachmentSearch.setType(AttachmentType.PROJECT_PROGRAM);
				List<Attachment> requirementList = this.attachmentService.getList(attachmentSearch);
				projectDesignVo.setRequirementList(requirementList);
				//获取工程设计附件
				attachmentSearch.setType(AttachmentType.PROJECT_DESIGN);
				List<Attachment> designList = this.attachmentService.getList(attachmentSearch);
				projectDesignVo.setDesignList(designList);
				projectInfoVo.setProjectDesignVo(projectDesignVo);
			}
			Integer gold = SystemConfig.payGoldForPorject();//需要扣除的金币
			projectInfoVo.setGold(gold);
			//ProjectFlowNode ProjectFlowNode = this.projectFlowNodeService.getProjectFlowNode(projectId, ProjectStatus.VERIFY_RESUMBIT_PROGRAM);
			//if(ProjectFlowNode != null)projectInfoVo.setRemark(ProjectFlowNode.getRemark());
			ProjectFlowNodeSearch projectFlowNodeSearch = new ProjectFlowNodeSearch();
			projectFlowNodeSearch.setProjectId(projectId);
			List<ProjectFlowNode> flowNodeList = this.projectFlowNodeService.getList(projectFlowNodeSearch);
			projectInfoVo.setFlowNodeList(flowNodeList);//流程节点信息列表
		}
		return projectInfoVo;
	}

	/**
	 * 保存方案设计的客户资料
	 * @param projectInfoVo
	 * @return
	 */
	@Override
	public Result saveProjectInfo(ProjectInfoVo projectInfoVo, String memberId) {
		Result result = new Result();
		if(StringUtil.isEmpty(memberId)){
			result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
			return result;
		}
		MemAccountVo memAccountVo = this.memAccountService.getById(Integer.parseInt(memberId));
		if(memAccountVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
			return result;
		}
		projectInfoVo.setMemberId(memAccountVo.getId());
		projectInfoVo.setOper(memAccountVo.getAccountName());
		projectInfoVo.setOperId(memAccountVo.getId());
//		projectInfoVo.setChannel("微信公众号");
		String memberName = memAccountVo.getRealName();
		if(StringUtil.isEmpty(memberName)){
			memberName =memAccountVo.getAccountName();
		}
		projectInfoVo.setMemberName(memberName);
		projectInfoVo.setMemberPhone(memAccountVo.getMobilePhone());
		result = this.savePorjectInfo(projectInfoVo);
		return result;
	}
	
	/**
	 * 跳转到提交方案页面
	 * @param projectDesignVo
	 * @return
	 */
	@Override
	public Result toProjectDesign(ProjectDesignVo projectDesignVo) {
		Result result = new Result();
		if(projectDesignVo.getProjectId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取工程客户资料失败");
			return result;
		}
		//获取产品设计内容
		DictionarySearch dictionarySearch = new DictionarySearch();
		dictionarySearch.setDicType("ProductCategory_bigType");
		List<DictionaryVo> productTypesList = this.dictionaryService.getList(dictionarySearch);//获取产品类型列表
		projectDesignVo.setProductTypesList(productTypesList);
		//获取哈玛尼智能系统内容
		dictionarySearch.setDicType("AutoSystem_type");
		List<DictionaryVo> autoSysList = this.dictionaryService.getList(dictionarySearch);     //获取智能系统列表
		projectDesignVo.setAutoSysList(autoSysList);
		result.setOK(ResultCode.CODE_STATE_200, "操作成功",projectDesignVo);
		return result;
	}
	
	/**
	 * 发送微信信息
	 * @param openId
	 * @param message
	 * @return
	 */
	private Result sendWeixinMesage(String openId, String message) {
		Result result = new Result();
		try {
			if(message != null && !message.equals("")){
				message = URLDecoder.decode(message, "UTF-8");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "内容不能为空");
				return result;
			}
			if (openId != null && !openId.equals("")) {
				String appId = null;
				String appSecret = null;
				if(SystemConfig.isLiveMode()){
					appId = SystemConfig.getConfigValueByKey("Formal_AppID");
					appSecret = SystemConfig.getConfigValueByKey("Formal_AppSecret");
				}else{
					appId = SystemConfig.getConfigValueByKey("Test_AppID");
					appSecret = SystemConfig.getConfigValueByKey("Test_AppSecret");
				}
				ApiConfig apiConfig = new ApiConfig(appId,appSecret);
				CustomAPI customAPI = new CustomAPI(apiConfig);
				customAPI.sendCustomMessage(openId,
						new TextMsg(message));
				result.setOK(ResultCode.CODE_STATE_200, "发送信息成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_4008, "系统繁忙，请稍候重试");
			return result;
		}
		return result;
	}
	
	/**
	 * * 6：您提交的【变量】设计需求资料不完善，请点击[这里]查看并按要求补充相关资料。
	 * 7：您提交的【变量】设计方案已完成，请点击[这里]查阅并反馈。感谢您对艾臣的信任与支持。
	
	 * 8：您提交的【变量】客户信息不属实，审核没通过，请点击[这里]处理。
	 * 9：您提交的【变量】客户信息审核已通过，请点击[这里]提交设计相关需求资料（包括设计需求、平面图、设计信息表和草图等文件或照片）。
	 * @param projectName
	 * @param projectId
	 * @param type
	 * @return
	 */
	private String getWxMessage(String projectName,Integer projectId,Integer integral, Integer type, Integer memberType){
		String wxMessage = null;
		if(StringUtil.isNotEmpty(projectName) && projectId != null && type != null){
			String serverHost = null;
			if(SystemConfig.isLiveMode()){
				serverHost = SystemConfig.getConfigValueByKey("server_host_live");
			}else{
				serverHost = SystemConfig.getConfigValueByKey("server_host_test");
				if(memberType != null && MemberType.PIONEER == memberType){
					serverHost += serverHost+"/ktz";
				}
			}
			if(type == 1){
				String url = "<a href='http://"+ serverHost +"/pages/projects-details.jsp?id="+ projectId +"'>这里</a>";
				wxMessage = "您提交的" + projectName + "设计需求资料不完善，请点击"+ url +"查看并按要求补充相关资料。";
			}else if(type == 2){
				String url = "<a href='http://"+ serverHost +"/pages/projects-details.jsp?id="+ projectId +"'>这里</a>";
				wxMessage = "您提交的" + projectName + "设计方案已完成，请点击"+ url +"查阅并反馈。感谢您对艾臣的信任与支持。";
			}else if(type == 3){
				String url = "<a href='http://"+ serverHost +"/pages/projects-customer.jsp?id="+ projectId +"'>这里</a>";
				wxMessage = "您提交的" + projectName + "客户信息不属实，审核没通过，请点击"+ url +"处理。";
			}else if(type == 4){
				String url = "<a href='http://"+ serverHost +"/pages/projects-details.jsp?id="+ projectId +"'>这里</a>";
				wxMessage = "您提交的" + projectName + "客户信息审核已通过，请点击"+ url +"提交设计相关需求资料（包括设计需求、平面图、设计信息表和草图等文件或照片）。";
			}else if(type == 5){
				String url = "<a href='http://"+ serverHost +"/pages2/projects-customer.jsp?id="+ projectId +"'>这里</a>";
				wxMessage = "您提交的" + projectName + "客户信息审核已通过，本次获得" + integral + "积分，点击"+ url +"查看积分详情。";
			}else if(type == 6){
				String url = "<a href='http://"+ serverHost +"/pages2/projects-customer.jsp?id="+ projectId +"'>这里</a>";
				wxMessage = "您提交的" + projectName + "客户信息不属实，审核没通过，请点击"+ url +"处理。";
			}
		}
		return wxMessage;
	}

	/**
	 * 成功案例处理
	 */
	@Override
	@Transactional
	public Result successCase(String[] imgUrl, Integer projectId, Boolean isSuccessfulCase, String successCaseImg) {
		Result result = new Result();
		Boolean flag = false;
		//保存附件
		if(projectId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取工程详情失败");
			return result;
		}
		if(StringUtil.isEmpty(successCaseImg)){
			result.setError(ResultCode.CODE_STATE_4006, "请上传案例主题图片");
			return result;
		}
		if(isSuccessfulCase == null){
			isSuccessfulCase = false;
		}
		//如果为成功案例，那么需要上传案例图片
		if(isSuccessfulCase && (imgUrl == null || imgUrl.length <= 0) ){
			result.setError(ResultCode.CODE_STATE_4006, "请上传案例明细图片");
			return result;
		}
		if(isSuccessfulCase){//处理附件
			AttachmentSearch attachmentSearch = new AttachmentSearch();
			attachmentSearch.setSourceId(projectId);
			attachmentSearch.setType(AttachmentType.PROJECT_SUCCESSCASE);
			attachmentSearch.setStatus(1);
			List<Attachment> successCaseList = this.attachmentService.getList(attachmentSearch);
			if(successCaseList != null && successCaseList.size() > 0){
				for(Attachment temp: successCaseList){
					temp.setStatus(0);//作废
				}
				flag = this.attachmentService.batchUpdate(successCaseList);
				if(!flag){
					result.setError(ResultCode.CODE_STATE_4006, "处理旧的方案需求附件失败，请稍后再试");
					throw new ServiceException("处理旧的方案需求附件失败");
				}
			}
			Date createTime = new Date();
			List<Attachment> list = new ArrayList<Attachment>();
			for(int i=0; i<imgUrl.length; i++){
				Attachment attachment = new Attachment();
				attachment.setAttachAddress(imgUrl[i]);
				attachment.setCreateTime(createTime);
				attachment.setSourceId(projectId);
				attachment.setType(AttachmentType.PROJECT_SUCCESSCASE);
				attachment.setStatus(1);
				attachment.setRemark(AttachmentType.AttachmentTypeMap.get(AttachmentType.PROJECT_SUCCESSCASE));
				list.add(attachment);
			}
			flag = this.attachmentService.batchAdd(list);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "保存案例图片失败");
				throw new ServiceException("保存案例图片失败");
			}
		}else{
			flag = true;
		}
		ProjectInfoVo projectInfoVo = new ProjectInfoVo();
		projectInfoVo.setId(projectId);
		projectInfoVo.setIsSuccessfulCase(isSuccessfulCase);
		projectInfoVo.setSuccessCaseImg(successCaseImg);
		flag = this.edit(projectInfoVo);
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
		}
		return result;
	}

	/**
	 * 业主提交客户资料前
	 * @param projectClientId
	 * @return
	 */
	@Override
	@Transactional
	public Result clientToRequirement(Integer projectClientId) {
		Result result = new Result();
		//信息有效性校验
		if(projectClientId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取业主资料信息失败");
			return result;
		}
		ProjectClientVo projectClientVo = this.projectClientService.getById(projectClientId);
		if(projectClientVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取业主资料信息失败");
			return result;
		}
		if(projectClientVo.getMemberId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取会员信息失败");
			return result;
		}
		if(MemClientStatus.ADDED != projectClientVo.getStatus()){
			result.setError(ResultCode.CODE_STATE_4006, "该业主资料还不是你的客户");
			return result;
		}
		//业务处理：新增一条工程资料，将id保存在会员业主资料关系表中
		Date curDate = new Date();
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setMemberId(projectClientVo.getMemberId());
		projectInfo.setProjectName(projectClientVo.getProjectName());
		projectInfo.setClientName(projectClientVo.getClientName());
		projectInfo.setMobilePhone(projectClientVo.getMobilePhone());
		projectInfo.setBudgetRange(projectClientVo.getBudgetRange());
		projectInfo.setBudgetRangeId(projectClientVo.getBudgetRangeId());
		projectInfo.setBudgetRangeValue(projectClientVo.getBudgetRangeValue());
		projectInfo.setByAgent(projectClientVo.getByAgent());
		projectInfo.setByAgentId(projectClientVo.getByAgentId());
		projectInfo.setProvince(projectClientVo.getProvince());
		projectInfo.setProvinceId(projectClientVo.getProvinceId());
		projectInfo.setCity(projectClientVo.getCity());
		projectInfo.setCityId(projectClientVo.getCityId());
		projectInfo.setArea(projectClientVo.getArea());
		projectInfo.setAreaId(projectClientVo.getAreaId());
		projectInfo.setClientAddress(projectClientVo.getClientAddress());
		projectInfo.setIsSent(true);
		projectInfo.setCreateTime(curDate);
		projectInfo.setUpdateTime(curDate);
		projectInfo.setStatus(ProjectStatus.HAD_VERIFY);//已审核
		projectInfo.setIntegral(0);
		Boolean flag = this.add(projectInfo);
		if(flag){
			projectClientVo.setProjectId(projectInfo.getId());
			flag = this.projectClientService.edit(projectClientVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功",projectInfo.getId());
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "更新会员业主资料失败");
				throw new ServiceException("更新会员业主资料失败");
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "保存客户资料失败失败");
		}
		return result;
	}
	
	
}
