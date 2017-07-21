package com.aylson.dc.wx.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.frame.base.Page;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.base.GeneralConstant.AttachmentType;
import com.aylson.dc.base.GeneralConstant.GiftConfigStatus;
import com.aylson.dc.base.GeneralConstant.MemClientStatus;
import com.aylson.dc.base.GeneralConstant.QRCodeChannel;
import com.aylson.dc.base.GeneralConstant.UserStatus;
import com.aylson.dc.mem.po.ArticleReply;
import com.aylson.dc.mem.po.MemAccount;
import com.aylson.dc.mem.search.ArticleSearch;
import com.aylson.dc.mem.search.GiftConfigSearch;
import com.aylson.dc.mem.search.MemAccountSearch;
import com.aylson.dc.mem.search.MemGoldDetailSearch;
import com.aylson.dc.mem.search.MemIntegralDetailSearch;
import com.aylson.dc.mem.search.ProjectClientSearch;
import com.aylson.dc.mem.search.ProjectDesignSearch;
import com.aylson.dc.mem.search.ProjectInfoSearch;
import com.aylson.dc.mem.search.PublishSearch;
import com.aylson.dc.mem.search.RechargeConfigSearch;
import com.aylson.dc.mem.service.ArticleService;
import com.aylson.dc.mem.service.GiftConfigService;
import com.aylson.dc.mem.service.GiftExchangeRecordsService;
import com.aylson.dc.mem.service.MemAccountService;
import com.aylson.dc.mem.service.ProjectClientService;
import com.aylson.dc.mem.service.ProjectDesignService;
import com.aylson.dc.mem.service.ProjectInfoService;
import com.aylson.dc.mem.service.PublishService;
import com.aylson.dc.mem.service.RechargeConfigService;
import com.aylson.dc.mem.vo.ArticleReplyVo;
import com.aylson.dc.mem.vo.ArticleVo;
import com.aylson.dc.mem.vo.MemAccountVo;
import com.aylson.dc.mem.vo.ProjectClientVo;
import com.aylson.dc.mem.vo.ProjectDesignVo;
import com.aylson.dc.mem.vo.ProjectInfoVo;
import com.aylson.dc.mem.vo.RechargeConfigVo;
import com.aylson.dc.sys.common.SystemConfig;
import com.aylson.dc.sys.po.Attachment;
import com.aylson.dc.sys.search.AgentUserSearch;
import com.aylson.dc.sys.search.AttachmentSearch;
import com.aylson.dc.sys.search.DictionarySearch;
import com.aylson.dc.sys.search.FeedbackSearch;
import com.aylson.dc.sys.search.RegionSearch;
import com.aylson.dc.sys.service.AgentUserService;
import com.aylson.dc.sys.service.AttachmentService;
import com.aylson.dc.sys.service.DictionaryService;
import com.aylson.dc.sys.service.FeedbackService;
import com.aylson.dc.sys.service.IntegralConfigService;
import com.aylson.dc.sys.service.RegionService;
import com.aylson.dc.sys.vo.AgentUserVo;
import com.aylson.dc.sys.vo.DictionaryVo;
import com.aylson.dc.sys.vo.FeedbackVo;
import com.aylson.utils.QiniuUtils;
import com.aylson.utils.QiniuUtils.DomainToBucket;
import com.aylson.utils.StringUtil;
import com.fastweixin.api.MediaAPI;
import com.fastweixin.api.OauthAPI;
import com.fastweixin.api.UserAPI;
import com.fastweixin.api.config.ApiConfig;
import com.fastweixin.api.response.DownloadMediaResponse;
import com.fastweixin.api.response.GetUserInfoResponse;
import com.fastweixin.api.response.OauthGetTokenResponse;

/**
 * 微信业务处理控制器
 * 说明：
 * 	   1、维护与微信平台与设计师联盟业务的控制，如会员列表等接口
 * @author wwx
 * @since  2016-05
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/wx/busi")
public class WxBusiController extends BaseController {
	
	//服务类
	@Autowired
	private MemAccountService memAccountService;                   //系统-组织机构服务
	@Autowired
	private DictionaryService dictionaryService;                   //系统-数据字典服务
	@Autowired
	private RegionService regionService;                           //系统-区域服务
	@Autowired
	private FeedbackService feedbackService;                       //系统-反馈服务
	@Autowired
	private ProjectInfoService projectInfoService;                 //会员-方案项目信息服务
	@Autowired
	private ProjectDesignService projectDesignService;             //会员-方案项目设计服务
	@Autowired
	private PublishService publishService;                         //会员-发布管理服务
	@Autowired
	private GiftConfigService giftConfigService;                   //会员-礼品兑换配置服务
	@Autowired
	private ArticleService articleService;                         //会员-交流社区服务
	@Autowired
	private GiftExchangeRecordsService giftExchangeRecordsService; //会员-礼品兑换服务
	@Autowired
	private RechargeConfigService rechargeConfigService;           //会员-充值金币配置服务
	@Autowired
	private AgentUserService agentUserService;                     //代理商服务
	@Autowired
	private AttachmentService attachmentService;                   //附件服务
	@Autowired
	private ProjectClientService projectClientService;             //会员分配客户资料服务
	
	/**
	 * 获取位置信息
	 * @param regionSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getLocation", method = RequestMethod.GET)
	public Result getLocation(RegionSearch regionSearch) {
		Result result = null;
		try{
			result = this.regionService.getLocation(regionSearch);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 会员注册
	 * @param memAccountVo
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public Result register(MemAccountVo memAccountVo, String code) {
		Result result = new Result();
		try{
			if(memAccountVo.getMemberType() == null){
				result.setError(ResultCode.CODE_STATE_4006, "注册账号类型不能为空");
				return result;
			}
			if(StringUtil.isNotEmpty(code)){
				//获取微信用户信息
				ApiConfig apiConfig = this.getWxApiConfig();
		    	OauthAPI oauthAPI = new OauthAPI(apiConfig);
		        OauthGetTokenResponse response = oauthAPI.getToken(code);
		        if(response != null && StringUtil.isNotEmpty(response.getOpenid())){
		        	List<MemAccountVo> referralWxUserList = this.memAccountService.getRelationByWxOpenId(response.getOpenid());//推荐人微信关系
		        	if(referralWxUserList != null && referralWxUserList.size() > 0){
		        		for(MemAccountVo referralWxUser: referralWxUserList){
		        			if(QRCodeChannel.AGENT.equals(referralWxUser.getChannel())){//代理商分享渠道:agent
			        			AgentUserVo agentUserVo = this.agentUserService.getByUserId(referralWxUser.getReferralId());
			        			memAccountVo.setByAgent(agentUserVo.getAgentName());
			        			memAccountVo.setByAgentUserId(agentUserVo.getUserId());
			        			break;
			        		}else if(QRCodeChannel.MEMBER.equals(referralWxUser.getChannel())){//会员渠道分享：设计师、工长
			        			MemAccount referralMem = this.memAccountService.getById(referralWxUser.getReferralId());
			        			//如果两个电话一样，那么将绑定的关系去除，自己分享自己注册不对
			        			if(StringUtil.isNotEmpty(referralMem.getMobilePhone()) && referralMem.getMobilePhone().equals(memAccountVo.getMobilePhone())){
			        				this.memAccountService.deleteRelationById(referralWxUser.getId());
			        			}else{
			        				memAccountVo.setReferralId(referralMem.getId());
			        				memAccountVo.setReferralName(referralMem.getAccountName());
			        				memAccountVo.setReferralPhone(referralMem.getMobilePhone());
			        				memAccountVo.setByAgent(referralMem.getByAgent());
				        			memAccountVo.setByAgentUserId(referralMem.getByAgentUserId());
			        			}
			        			break;
			        		}
		        		}
		        	}
		        	//获取新注册会员的微信信息
		        	UserAPI userAPI = new UserAPI(apiConfig);
		            GetUserInfoResponse userInfo = userAPI.getUserInfo(response.getOpenid());
		            memAccountVo.setWxHeadPhoto( userInfo.getHeadimgurl());
		            memAccountVo.setWxNickName(userInfo.getNickname());
		            memAccountVo.setWxOpenid(userInfo.getOpenid());
		            memAccountVo.setWxUnionid(userInfo.getUnionid());
		           //System.out.println("==============微信用户信息："+ userInfo.toJsonString());
		        }else{ 
		        	result.setError(ResultCode.CODE_STATE_4006, "微信授权失败，请重新进入页面进行操作");
					return result;
		        }
			}
			result = this.memAccountService.register(memAccountVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试");
		}
		return result;
	}
	
	/**
	 * 会员登录
	 * @param memAccountVo
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Result login(MemAccountVo memAccountVo) {
		Result result = new Result();
		try{
			if(memAccountVo.getMemberType() == null){
				result.setError(ResultCode.CODE_STATE_4006, "账号类型不能为空");
				return result;
			}
			result = this.memAccountService.login(memAccountVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 注销登录
	 * @return
	 */
	@RequestMapping(value = "/loginOut", method = RequestMethod.POST)
	@ResponseBody
	public Result loginOut() {
		Result result = new Result();
		try{
			if(this.request.getSession().getAttribute("sessionInfo") != null){
				this.request.getSession().removeAttribute("sessionInfo");
			}
			result.setOK(ResultCode.CODE_STATE_200, "注销成功");
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 获取我的个人信息
	 * @return
	 */
	@RequestMapping(value = "/getMyPersonalInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result getMyPersonalInfo() {
		Result result = null;
		try{
			String clientId = request.getParameter("clientId");
			result = this.memAccountService.getMyPersonalInfo(clientId);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 修改会员个人信息
	 * @param memAccountVo
	 * @return
	 */
	@RequestMapping(value = "/modifyMemPersonalInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result modifyMemPersonalInfo(MemAccountVo memAccountVo,String mediaIds) {
		Result result = null;
		try{
			if(StringUtil.isNotEmpty(mediaIds)){
				memAccountVo.setPhoto(this.dealWxPhoto(mediaIds, DomainToBucket.DOMAIN_MEMBER));
			}
			result = this.memAccountService.modifyMemPersonalInfo(memAccountVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 根据会员ID获取会员积分流水明细信息
	 * @param memberId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMemIntegralDetail", method = RequestMethod.GET)
	public Result getMemIntegralDetail(MemIntegralDetailSearch memIntegralDetailSearch) {
		Result result = null;
		try{
			result = this.memAccountService.getMemIntegralDetail(memIntegralDetailSearch);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 根据会员ID获取会员金币流水明细信息
	 * @param memberId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMemGoldDetail", method = RequestMethod.GET)
	public Result getMemGoldDetail(MemGoldDetailSearch memGoldDetailSearch) {
		Result result = null;
		try{
			result = this.memAccountService.getMemGoldDetail(memGoldDetailSearch);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取会员信息列表
	 * @param memAccountSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMemAccounts", method = RequestMethod.GET)
	public Result getMemAccounts(MemAccountSearch memAccountSearch) {
		Result result = null;
		try{
			result = this.memAccountService.getMemAccounts(memAccountSearch);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 根据会员ID获取方案设计列表
	 * @param projectInfoSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getProjectList", method = RequestMethod.GET)
	public Result getProjectList(ProjectInfoSearch projectInfoSearch) {
		Result result = new Result();
		try{
			if(projectInfoSearch.getMemberId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取不到会员id信息，请重新登录再试");
				return result;
			}
			Page<ProjectInfoVo> page = this.projectInfoService.getPage(projectInfoSearch);
			result.setOK(ResultCode.CODE_STATE_200, "", page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取工程预算范围
	 * @param dictionarySearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBudgetRange", method = RequestMethod.GET)
	public Result getBudgetRange(DictionarySearch dictionarySearch) {
		Result result = new Result();
		try{
			if(StringUtil.isEmpty(dictionarySearch.getDicType())){
				dictionarySearch.setDicType("budgetRange_item");
			}
			List<DictionaryVo> list = this.dictionaryService.getList(dictionarySearch);
			result.setOK(ResultCode.CODE_STATE_200, "", list);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 添加方案设计的客户资料
	 * @param projectInfoVo
	 * @return
	 */
//	@RequestMapping(value = "/addProjectInfo", method = RequestMethod.POST)
//	@ResponseBody
//	public Result addProjectInfo(ProjectInfoVo projectInfoVo) {
//		Result result = null;
//		try{
//			projectInfoVo.setChannel("微信公众号");
//			result = this.projectInfoService.addProjectInfo(projectInfoVo);
//		}catch(Exception e){
//			e.printStackTrace();
//			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
//		}
//		return result;
//	}
	
	/**
	 * 提交方案设计的客户资料
	 * @param projectId
	 * @return
	 */
//	@RequestMapping(value = "/confirmProjectInfo", method = RequestMethod.POST)
//	@ResponseBody
//	public Result confirmProjectInfo(Integer projectId) {
//		Result result = null;
//		try{
//			result = this.projectInfoService.confirmProjectInfo(projectId,"微信公众号");
//		}catch(Exception e){
//			e.printStackTrace();
//			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
//		}
//		return result;
//	}
	
	/**
	 * 修改方案设计的客户资料
	 * @param projectInfoVo
	 * @return
	 */
//	@RequestMapping(value = "/modifyProjectInfo", method = RequestMethod.POST)
//	@ResponseBody
//	public Result modifyProjectInfo(ProjectInfoVo projectInfoVo) {
//		Result result = null;
//		try{
//			projectInfoVo.setChannel("微信公众号");
//			result = this.projectInfoService.modifyProjectInfo(projectInfoVo);
//		}catch(Exception e){
//			e.printStackTrace();
//			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
//		}
//		return result;
//	}
	
	/**
	 * 保存方案设计的客户资料
	 * @param projectInfoVo
	 * @return
	 */
	@RequestMapping(value = "/saveProjectInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result saveProjectInfo(ProjectInfoVo projectInfoVo){
		Result result = null;
		try{
			String clientId = request.getParameter("clientId");
			projectInfoVo.setChannel("微信公众号");
			result = this.projectInfoService.saveProjectInfo(projectInfoVo, clientId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 跳转到提交方案页面
	 * @param projectDesignVo
	 * @return
	 */
	@RequestMapping(value = "/toProjectDesign", method = RequestMethod.GET)
	@ResponseBody
	public Result toProjectDesign(ProjectDesignVo projectDesignVo) {
		Result result = null;
		try{
			result = this.projectInfoService.toProjectDesign(projectDesignVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 保存或提交设计方案需求
	 * @param projectDesignVo
	 * @return
	 */
//	@RequestMapping(value = "/saveProjectDesign", method = RequestMethod.POST)
//	@ResponseBody
//	public Result saveProjectDesign(ProjectDesignVo projectDesignVo) {
//		Result result = new Result();
//		try{
//			String clientId = request.getParameter("clientId");
//			if(StringUtil.isEmpty(clientId)){
//				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
//				return result;
//			}
//			result = this.projectDesignService.saveProjectDesign(projectDesignVo,Integer.parseInt(clientId));
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return result;
//		
//	}
	
	/**
	 * 保存设计方案需求
	 * @param projectDesignVo
	 * @return
	 */
	@RequestMapping(value = "/saveRequirement", method = RequestMethod.POST)
	@ResponseBody
	public Result saveRequirement(ProjectDesignVo projectDesignVo,String mediaIds){
		Result result = new Result();
		try{
			projectDesignVo.setChannel("微信公众号");
			if(StringUtil.isNotEmpty(mediaIds)){
				String imageUrls = this.dealWxPhoto(mediaIds, DomainToBucket.DOMAIN_REQUIREMENT);
				projectDesignVo.setAttachments(imageUrls);
			}
			if(projectDesignVo.getProjectId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取工程的客户资料失败");
				return result;
			}
			ProjectDesignSearch projectDesignSearch = new ProjectDesignSearch();
			projectDesignSearch.setProjectId(projectDesignVo.getProjectId());
			long rows = this.projectDesignService.getRowCount(projectDesignSearch);
			if(rows == 1 && projectDesignVo.getId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "方案需求已经存在");
				return result;
			}
		    result = this.projectInfoService.saveRequirement(projectDesignVo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 确认方案设计
	 * @return
	 */
	@RequestMapping(value = "/confirmDesign", method = RequestMethod.POST)
	@ResponseBody
	public Result confirmDesign(Integer projectId){
		Result result = new Result();
		try{
		   if(projectId == null){
			   result.setError(ResultCode.CODE_STATE_4006, "获取方案信息失败");
			   return result;
		   }
		   ProjectInfoVo projectInfoVo = this.projectInfoService.getById(projectId);
		   result = this.projectInfoService.confirmDesign(projectInfoVo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 退回方案设计
	 * @return
	 */
	@RequestMapping(value = "/returnDesign", method = RequestMethod.POST)
	@ResponseBody
	public Result returnDesign(Integer projectId, String auditOpition){
		Result result = new Result();
		try{
		   if(projectId == null){
			   result.setError(ResultCode.CODE_STATE_4006, "获取方案信息失败");
			   return result;
		   }
		   ProjectInfoVo projectInfoVo = this.projectInfoService.getById(projectId);
		   projectInfoVo.setAuditOpition(auditOpition);
		   result = this.projectInfoService.returnDesign(projectInfoVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 评价方案设计
	 * @param projectDesignVo
	 * @return
	 */
	@RequestMapping(value = "/evaluate", method = RequestMethod.POST)
	@ResponseBody
	public Result evaluate(ProjectDesignVo projectDesignVo) {
		Result result = null;
		try{
			result =this.projectInfoService.evaluate(projectDesignVo);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据项目ID获取方案项目的所有信息
	 * @param projectId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getProjectInfoDetail", method = RequestMethod.GET)
	public Result getProjectInfoDetail(Integer projectId) {
		Result result = new Result();
		try{
			if(projectId == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取工程客户资料失败");
				return result;
			}
			ProjectInfoVo projectInfoVo = this.projectInfoService.getProjectInfoDetail(projectId);
			projectInfoVo.setFlowNodeList(null);
			result.setOK(ResultCode.CODE_STATE_200, "", projectInfoVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取发布信息列表：
	 * @param type 1：通知公告 2：新闻动态
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPublishList", method = RequestMethod.GET)
	public Result getPublishList(PublishSearch publishSearch) {
		Result result = null;
		try{
			String clientId = request.getParameter("clientId");
			result = this.publishService.getPublishList(publishSearch, clientId);
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
	@ResponseBody
	@RequestMapping(value = "/getPublishInfo", method = RequestMethod.GET)
	public Result getPublishInfo(Integer publishId) {
		Result result = null;
		try{
			String clientId = request.getParameter("clientId");
			result = this.publishService.getPublishInfo(publishId, clientId);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取积分礼品兑换列表
	 * @param giftConfigSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getIntegralGiftList", method = RequestMethod.GET)
	public Result getIntegralGiftList(GiftConfigSearch giftConfigSearch) {
		Result result = null;
		try{
			String clientId = request.getParameter("clientId");
			result = this.giftConfigService.getIntegralGiftList(giftConfigSearch, clientId);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取礼品详情
	 * @param giftId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGiftDetail", method = RequestMethod.GET)
	public Result getGiftDetail(Integer giftId) {
		Result result = null;
		try{
			result = this.giftConfigService.getGiftDetail(giftId);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 兑换礼品
	 * @param giftId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/exchangeGift", method = RequestMethod.POST)
	public Result exchangeGift(Integer giftId){
		Result result = null;
		try{
			String clientId = request.getParameter("clientId");
			result = this.giftExchangeRecordsService.exchangeGift(giftId, clientId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 发表文章
	 * @param articleVo
	 * @return
	 */
	@RequestMapping(value = "/publishArticle", method = RequestMethod.POST)
	@ResponseBody
	public Result publishArticle(ArticleVo articleVo,String mediaIds) {
		Result result = new Result();
		try{
			//1、信息有效性校验  
			String clientId = request.getParameter("clientId");
			if(StringUtil.isNotEmpty(mediaIds)){
				articleVo.setThumb(this.dealWxPhoto(mediaIds, DomainToBucket.DOMAIN_ARTICLE));
			}
			result = this.articleService.publishArticle(articleVo, clientId);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 获取分页文章列表
	 * @param articleSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getArticleList", method = RequestMethod.GET)
	public Result getArticleList(ArticleSearch articleSearch) {
		Result result = new Result();
		try{
			Page<ArticleVo> page = this.articleService.getPage(articleSearch);
			result.setOK(ResultCode.CODE_STATE_200, "", page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取文章详情
	 * @param articleId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getArticle", method = RequestMethod.GET)
	public Result getArticle(Integer articleId) {
		Result result = new Result();
		try{
			String clientId = request.getParameter("clientId");
			result = this.articleService.getArticle(articleId, clientId);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取文章回复列表
	 * @param articleSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getArticleReplyList", method = RequestMethod.GET)
	public Result getArticleReplyList(ArticleSearch articleSearch) {
		Result result = new Result();
		try{
			if(articleSearch.getArticleId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取文章信息失败");
				return result;
			}
			//获取读取列表
			Page<ArticleReplyVo> articleReadList = this.articleService.getReplyPage(articleSearch);
			result.setOK(ResultCode.CODE_STATE_200, "", articleReadList);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 回复文章
	 * @param articleReply
	 * @return
	 */
	@RequestMapping(value = "/replyArticle", method = RequestMethod.POST)
	@ResponseBody
	public Result replyArticle(ArticleReply articleReply) {
		Result result = null;
		try{
			String clientId = request.getParameter("clientId");
			result = this.articleService.replyArticle(articleReply, clientId);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 更新点赞数
	 * @param articleId
	 * @return
	 */
	@RequestMapping(value = "/praise", method = RequestMethod.POST)
	@ResponseBody
	public Result praise(Integer articleId){
		return this.articleService.praise(articleId);
	}
	
	/**
	 * 更新分享数
	 * @param articleId
	 * @return
	 */
	@RequestMapping(value = "/share", method = RequestMethod.POST)
	@ResponseBody
	public Result share(Integer articleId){
		return this.articleService.share(articleId);
	}
	
	/**
	 * 添加反馈
	 * @param feedbackVo
	 * @return
	 */
	@RequestMapping(value = "/addFeedBack", method = RequestMethod.POST)
	@ResponseBody
	public Result addFeedBack(FeedbackVo feedbackVo) {
		Result result = new Result();
		try{
			String clientId = request.getParameter("clientId");
			result = this.feedbackService.addFeedBack(feedbackVo, clientId);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 获取分页反馈列表
	 * @param feedbackSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getFeekbackList", method = RequestMethod.GET)
	public Result getFeekbackList(FeedbackSearch feedbackSearch) {
		Result result = new Result();
		try{
			//获取读取列表
			Page<FeedbackVo> feedbackList = this.feedbackService.getPage(feedbackSearch);
			result.setOK(ResultCode.CODE_STATE_200, "", feedbackList);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取反馈详情
	 * @param feedbackId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getFeekback", method = RequestMethod.GET)
	public Result getFeekback(Integer feedbackId) {
		Result result = new Result();
		try{
			if(feedbackId == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取反馈信息失败");
				return result;
			}
			FeedbackVo feedbackVo = this.feedbackService.getById(feedbackId);
			result.setOK(ResultCode.CODE_STATE_200, "", feedbackVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 修改会员密码
	 * @param mobilePhone
	 * @param validCode
	 * @param newPwd
	 * @param confirmPwd
	 * @return
	 */
	@RequestMapping(value = "/modifyPwd", method = RequestMethod.POST)
	@ResponseBody
	public Result modifyPwd(String mobilePhone, String validCode, String newPwd, String confirmPwd, Integer memberType) {
		Result result = null;
		try{
			result = this.memAccountService.modifyPwd(mobilePhone, validCode, newPwd, confirmPwd, memberType);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 获取充值配置列表
	 * @param rechargeConfigSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getRechargeList", method = RequestMethod.GET)
	public Result getRechargeList(RechargeConfigSearch rechargeConfigSearch) {
		Result result = new Result();
		try{
			rechargeConfigSearch.setStatus(GiftConfigStatus.UP);
			Page<RechargeConfigVo> rechargeList = this.rechargeConfigService.getPage(rechargeConfigSearch);
			result.setOK(ResultCode.CODE_STATE_200, "", rechargeList);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 处理微信上传图片
	 * @param mediaIds
	 * @param qiniuDomain
	 * @return
	 */
	private String dealWxPhoto(String mediaIds, String qiniuDomain){
		StringBuffer newImages = null;
		if(StringUtil.isNotEmpty(mediaIds) && StringUtil.isNotEmpty(qiniuDomain)){
			ApiConfig apiConfig = this.getWxApiConfig();
			String[] mediaIdArray = mediaIds.split(","); 
	        for(String mediaId : mediaIdArray){
	        	if(mediaId.indexOf(DomainToBucket.DomainToBucketMap.get(qiniuDomain)) >= 0){//已经上传过的图片
					if(newImages == null){
						newImages = new StringBuffer(mediaId);
					}else{
						newImages.append(",").append(mediaId);
					}
				}else{
					MediaAPI mediaAPI = new MediaAPI(apiConfig);
			        DownloadMediaResponse dmr = mediaAPI.downloadMedia(mediaId);
			        if(dmr!=null){
			        	String fileName = qiniuDomain +"-"+ new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"@|@"+ dmr.getFileName(); //产生一个新的名称
			        	int statusCode = QiniuUtils.uploadFile(dmr.getContent(), fileName, qiniuDomain, QiniuUtils.UPLOAD_SIMPLE);
						if(statusCode==200){
							String domainUrl = DomainToBucket.DomainToBucketMap.get(qiniuDomain);
							if (newImages == null){
								newImages = new StringBuffer(domainUrl+fileName);
							}else{
								newImages.append(",").append(domainUrl+fileName);
							}
						}
			        }
				}
	        }
	        return newImages.toString();
		}
		return null;
	}
	
	/**
	 * 获取成功案例列表
	 * @param projectInfoSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getSuccessfulCase", method = RequestMethod.GET)
	public Result getSuccessfulCase(ProjectInfoSearch projectInfoSearch) {
		Result result = new Result();
		try{
			projectInfoSearch.setIsSuccessfulCase(true);
			Page<ProjectInfoVo> page = this.projectInfoService.getPage(projectInfoSearch);
			result.setOK(ResultCode.CODE_STATE_200, "", page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取成功案例详情
	 * @param projectId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getSuccessfulCaseDetail", method = RequestMethod.GET)
	public Result getSuccessfulCaseDetail(Integer projectId) {
		Result result = new Result();
		try{
			if(projectId == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取工程客户资料失败");
				return result;
			}
			ProjectInfoVo projectInfoVo = this.projectInfoService.getProjectInfoDetail(projectId);
			if(projectInfoVo.getIsSuccessfulCase() != null && projectInfoVo.getIsSuccessfulCase()){
				AttachmentSearch attachmentSearch = new AttachmentSearch();
				attachmentSearch.setSourceId(projectId);
//				attachmentSearch.setType(AttachmentType.PROJECT_SUCCESSCASE);
				attachmentSearch.setStatus(1);
				List<Attachment> successCaseList = this.attachmentService.getList(attachmentSearch);
				projectInfoVo.setSuccessCaseList(successCaseList);
			}
			projectInfoVo.setFlowNodeList(null);
			result.setOK(ResultCode.CODE_STATE_200, "", projectInfoVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取代理商列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAgentUser", method = RequestMethod.GET)
	public Result getAgentUser(){
		Result result = new Result();
		try{
			AgentUserSearch agentUserSearch = new AgentUserSearch();
			agentUserSearch.setStatus(UserStatus.ACTIVITY);
			List<AgentUserVo> agentUserList = this.agentUserService.getList(agentUserSearch);
			result.setOK(ResultCode.CODE_STATE_200, "", agentUserList);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取我邀请的人
	 * @param memberId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMyInvitesMem", method = RequestMethod.GET)
	public Result getMyInvitesMem(Integer memberId, Integer page, Integer rows){
		Result result = new Result();
		try{
			if(memberId == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前登录人信息失败");
				return result;
			}
			MemAccountSearch search = new MemAccountSearch();
			search.setReferralId(memberId);
			if(page != null){search.setPage(page);}
			if(rows != null){search.setRows(rows);}
			Page<MemAccountVo> myInvitesMem = this.memAccountService.getPage(search);
			for(MemAccountVo temp:myInvitesMem.getRowsObject()){
				temp.setPwd(null);
	    		temp.setGold(null);
	    		temp.setIntegral(null);
	    		temp.setTotalIntegral(null);
	    		temp.setWxOpenid(null);
	    		temp.setWxUnionId(null);
			}
			result.setOK(ResultCode.CODE_STATE_200, "", myInvitesMem);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取我的用户列表
	 * @param memberId
	 * @param status  1 已分配 2 已添加 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMyCustomer", method = RequestMethod.GET)
	public Result getMyCustomer(ProjectClientSearch projectClientSearch){
		Result result = new Result();
		try{
			if(projectClientSearch.getMemberId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前登录人信息失败");
				return result;
			}
			if(projectClientSearch.getStatus() == null){
				result.setError(ResultCode.CODE_STATE_4006, "未知状态");
				return result;
			}
			if(MemClientStatus.ASSIGNED != projectClientSearch.getStatus() && 
					MemClientStatus.ADDED != projectClientSearch.getStatus()){
				result.setError(ResultCode.CODE_STATE_4006, "未知状态");
				return result;
			}
			projectClientSearch.setClientId(null);
			Page<ProjectClientVo> MyCustomer = this.projectClientService.getPage(projectClientSearch);
			result.setOK(ResultCode.CODE_STATE_200, "", MyCustomer);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 添加为我的客户
	 * @param projectClientId
	 * @return
	 */
	@RequestMapping(value = "/addMyCustomer", method = RequestMethod.POST)
	@ResponseBody
	public Result addMyCustomer(Integer projectClientId) {
		Result result = new Result();
		try{
			if(projectClientId == null){
				result.setError(ResultCode.CODE_STATE_4006, "业主资料id不能为空");
				return result;
			}
			ProjectClientVo projectClientVo = this.projectClientService.getById(projectClientId);
			if(projectClientVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取业主资料信息失败");
				return result;
			}
			projectClientVo.setStatus(MemClientStatus.ADDED);
			Boolean flag = this.projectClientService.edit(projectClientVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 业主资料提交客户需求前
	 * @param projectClientId
	 * @return
	 */
	@RequestMapping(value = "/clientToRequirement", method = RequestMethod.POST)
	@ResponseBody
	public Result clientToRequirement(Integer projectClientId) {
		Result result = new Result();
		try{
			result = this.projectInfoService.clientToRequirement(projectClientId);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 忽略客户资料
	 * @param projectClientId
	 * @return
	 */
	@RequestMapping(value = "/ignoreClientInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result ignoreClientInfo(Integer projectClientId) {
		Result result = new Result();
		try{
			if(projectClientId == null){
				result.setError(ResultCode.CODE_STATE_4006, "业主资料id不能为空");
				return result;
			}
			ProjectClientVo projectClientVo = this.projectClientService.getById(projectClientId);
			if(projectClientVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取业主资料信息失败");
				return result;
			}
			projectClientVo.setStatus(MemClientStatus.IGNORED);
			Boolean flag = this.projectClientService.edit(projectClientVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 获取微信配置对象
	 */
	private ApiConfig getWxApiConfig(){
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
		return apiConfig;
	}
	
	
}
