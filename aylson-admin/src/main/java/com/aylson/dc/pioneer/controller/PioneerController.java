package com.aylson.dc.pioneer.controller;

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
import com.aylson.dc.base.SmsTemplate;
import com.aylson.dc.base.GeneralConstant.MemberType;
import com.aylson.dc.base.GeneralConstant.PioneerAgentStatus;
import com.aylson.dc.base.GeneralConstant.ProjectStatus;
import com.aylson.dc.base.GeneralConstant.QRCodeChannel;
import com.aylson.dc.mem.po.MemAccount;
import com.aylson.dc.mem.search.BankSearch;
import com.aylson.dc.mem.search.GiftConfigSearch;
import com.aylson.dc.mem.search.MemAccountSearch;
import com.aylson.dc.mem.search.MemGoldDetailSearch;
import com.aylson.dc.mem.search.MemIntegralDetailSearch;
import com.aylson.dc.mem.search.MemWalletDetailSearch;
import com.aylson.dc.mem.search.ProjectInfoSearch;
import com.aylson.dc.mem.search.WithdrawalsApplySearch;
import com.aylson.dc.mem.service.BankService;
import com.aylson.dc.mem.service.GiftConfigService;
import com.aylson.dc.mem.service.GiftExchangeRecordsService;
import com.aylson.dc.mem.service.MemAccountService;
import com.aylson.dc.mem.service.ProjectInfoService;
import com.aylson.dc.mem.service.WithdrawalsApplyService;
import com.aylson.dc.mem.vo.BankVo;
import com.aylson.dc.mem.vo.MemAccountVo;
import com.aylson.dc.mem.vo.ProjectInfoVo;
import com.aylson.dc.mem.vo.WithdrawalsApplyVo;
import com.aylson.dc.pioneer.search.PioneerAgentSaleInfoSearch;
import com.aylson.dc.pioneer.search.PioneerAgentSearch;
import com.aylson.dc.pioneer.service.PioneerAgentSaleInfoService;
import com.aylson.dc.pioneer.service.PioneerAgentService;
import com.aylson.dc.pioneer.vo.PioneerAgentSaleInfoVo;
import com.aylson.dc.pioneer.vo.PioneerAgentVo;
import com.aylson.dc.sys.common.SystemConfig;
import com.aylson.dc.sys.search.DictionarySearch;
import com.aylson.dc.sys.search.FeedbackSearch;
import com.aylson.dc.sys.search.RegionSearch;
import com.aylson.dc.sys.service.DictionaryService;
import com.aylson.dc.sys.service.FeedbackService;
import com.aylson.dc.sys.service.RegionService;
import com.aylson.dc.sys.vo.DictionaryVo;
import com.aylson.dc.sys.vo.FeedbackVo;
import com.aylson.utils.DateUtil;
import com.aylson.utils.IHuiYiUtils;
import com.aylson.utils.QiniuUtils;
import com.aylson.utils.QiniuUtils.DomainToBucket;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;
import com.fastweixin.api.MediaAPI;
import com.fastweixin.api.OauthAPI;
import com.fastweixin.api.UserAPI;
import com.fastweixin.api.config.ApiConfig;
import com.fastweixin.api.response.DownloadMediaResponse;
import com.fastweixin.api.response.GetUserInfoResponse;
import com.fastweixin.api.response.OauthGetTokenResponse;

/**
 * 开拓者控制器
 * 说明：
 * 	  1、存放开拓者联盟客户端的所有控制器接口，便于管理跟踪
 *    2、控制层尽量只负责控制跳转，业务逻辑在相关服务层实现
 * @author wwx
 * @since  2016-09
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/pioneer")
public class PioneerController extends BaseController {
	
	//服务类
	@Autowired
	private MemAccountService memAccountService;                    //会员-会员账号服务
	@Autowired
	private GiftConfigService giftConfigService;                    //会员-礼品兑换配置服务
	@Autowired
	private GiftExchangeRecordsService giftExchangeRecordsService;  //会员-礼品兑换服务
	@Autowired
	private FeedbackService feedbackService;                        //系统-反馈服务
	@Autowired
	private ProjectInfoService projectInfoService;                  //会员-方案项目信息服务
	@Autowired
	private PioneerAgentService pioneerAgentService;                //会员-开拓者代理商服务
	@Autowired
	private PioneerAgentSaleInfoService pioneerAgentSaleInfoService;//会员-开拓者代理商服务
	@Autowired
	private WithdrawalsApplyService withdrawalsApplyService;        //会员-开拓者提现申请服务
	@Autowired
	private RegionService regionService;                            //系统-区域服务
	@Autowired
	private DictionaryService dictionaryService;                   //系统-数据字典服务
	@Autowired
	private BankService bankService;                               //系统-会员银行信息服务

		
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
			if(memAccountVo.getMemberType() == null){memAccountVo.setMemberType(MemberType.PIONEER);}
			if(StringUtil.isNotEmpty(code)){
				//获取微信用户信息
				ApiConfig apiConfig = this.getWxApiConfig();
		    	OauthAPI oauthAPI = new OauthAPI(apiConfig);
		        OauthGetTokenResponse response = oauthAPI.getToken(code);
		        if(response != null && StringUtil.isNotEmpty(response.getOpenid())){
		        	//查看通过分享注册的
		        	MemAccountVo referralWxUser = this.memAccountService.getRelation(null, null, response.getOpenid(), QRCodeChannel.PIONEER);
		        	if(referralWxUser != null && referralWxUser.getReferralId() != null){//如果是通过分享注册
		        		//获取分享人的信息
	        			MemAccount referralMem = this.memAccountService.getById(referralWxUser.getReferralId());
	        			//如果两个电话一样，那么将绑定的关系去除，自己分享自己注册不对
	        			if(StringUtil.isNotEmpty(referralMem.getMobilePhone()) && referralMem.getMobilePhone().equals(memAccountVo.getMobilePhone())){
	        				this.memAccountService.deleteRelationById(referralWxUser.getId());
	        			}else{//获取新注册会员的推荐人信息
	        				memAccountVo.setReferralId(referralMem.getId());
	        				memAccountVo.setReferralName(referralMem.getAccountName());
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
		Result result = null;
		try{
			if(memAccountVo.getMemberType() == null){memAccountVo.setMemberType(MemberType.PIONEER);}
			result = this.memAccountService.login(memAccountVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
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
	 * 业主资料-添加业主资料
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
	 * 个人中心-根据会员ID获取会员积分流水明细信息
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
	 * 个人中心-根据会员ID获取会员金币流水明细信息
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
	 * 个人中心-根据会员ID获取会员钱包流水明细信息
	 * @param memberId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMemWalletDetail", method = RequestMethod.GET)
	public Result getMemWalletDetail(MemWalletDetailSearch memWalletDetailSearch) {
		Result result = null;
		try{
			result = this.memAccountService.getMemWalletDetail(memWalletDetailSearch);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 个人中心-获取我的个人信息
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
	 * 个人中心-修改会员个人信息
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
	 * 个人中心-获取我邀请的人
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
	 * 个人中心-添加反馈
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
	 * 个人中心-获取分页反馈列表
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
	 * 个人中心-获取反馈详情
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
	 * 个人中心-修改会员密码
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
	 * 我的代理商-添加代理商资料
	 * @param pioneerAgentVo
	 * @return
	 */
	@RequestMapping(value = "/addAgentInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result addAgentInfo(PioneerAgentVo pioneerAgentVo) {
		Result result = new Result();
		Boolean flag = false;
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			MemAccountVo memAccountVo = this.memAccountService.getById(Integer.parseInt(clientId));
			if(memAccountVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "不存在当前登录人信息");
				return result;
			}
			if(StringUtil.isEmpty(pioneerAgentVo.getAgentName())){
				result.setError(ResultCode.CODE_STATE_4006, "代理商名称不能为空");
				return result;
			}
			if(StringUtil.isEmpty(pioneerAgentVo.getContactPhone())){
				result.setError(ResultCode.CODE_STATE_4006, "代理商电话不能为空");
				return result;
			}
			if(!VerificationUtils.isValid(pioneerAgentVo.getContactPhone(), VerificationUtils.MOBILE)){
				result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
				return result;
			}
			if(StringUtil.isEmpty(pioneerAgentVo.getProvince()) || pioneerAgentVo.getProvinceId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "省会不能为空");
				return result;
			}
			if(StringUtil.isEmpty(pioneerAgentVo.getCity()) || pioneerAgentVo.getCityId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "城市不能为空");
				return result;
			}
			if(StringUtil.isEmpty(pioneerAgentVo.getArea()) || pioneerAgentVo.getAreaId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "区域不能为空");
				return result;
			}
			if(StringUtil.isEmpty(pioneerAgentVo.getAddress())){
				result.setError(ResultCode.CODE_STATE_4006, "代理商地址不能为空");
				return result;
			}
			pioneerAgentVo.setSubmitterId(Integer.parseInt(clientId));
			pioneerAgentVo.setCreateTime(new Date());
			pioneerAgentVo.setUdpateTime(new Date());
			pioneerAgentVo.setStatus(PioneerAgentStatus.WAIT_AUDIT);
			pioneerAgentVo.setRemark("");
			pioneerAgentVo.setHistoryRemark("");
			flag = this.pioneerAgentService.add(pioneerAgentVo);
			if(flag){
				//发送通知短信
				if(!SystemConfig.isDebugMode() ){//非调试模式才发送短信
					String smsContent = SmsTemplate.getSmsWhenPioneerSubmitAgentInfo(memAccountVo.getAccountName(), new Date(), pioneerAgentVo.getAgentName());
					DictionarySearch noticePhoneSearch = new DictionarySearch();
					noticePhoneSearch.setDicType("SmsNoticePioneerPhone_type");
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
				result.setOK(ResultCode.CODE_STATE_200, "添加成功",pioneerAgentVo);
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "添加失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 我的代理商-添加代理商资料
	 * @param pioneerAgentVo
	 * @return
	 */
	@RequestMapping(value = "/modifyAgentInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result modifyAgentInfo(PioneerAgentVo pioneerAgentVo) {
		Result result = new Result();
		Boolean flag = false;
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			if(pioneerAgentVo.getId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取代理商信息失败");
				return result;
			}
			if(pioneerAgentVo.getSubmitterId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取提交人信息失败");
				return result;
			}
			if(StringUtil.isEmpty(pioneerAgentVo.getAgentName())){
				result.setError(ResultCode.CODE_STATE_4006, "代理商名称不能为空");
				return result;
			}
			if(StringUtil.isEmpty(pioneerAgentVo.getContactPhone())){
				result.setError(ResultCode.CODE_STATE_4006, "代理商电话不能为空");
				return result;
			}
			if(!VerificationUtils.isValid(pioneerAgentVo.getContactPhone(), VerificationUtils.MOBILE)){
				result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
				return result;
			}
			if(StringUtil.isEmpty(pioneerAgentVo.getProvince()) || pioneerAgentVo.getProvinceId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "省会不能为空");
				return result;
			}
			if(StringUtil.isEmpty(pioneerAgentVo.getCity()) || pioneerAgentVo.getCityId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "城市不能为空");
				return result;
			}
			if(StringUtil.isEmpty(pioneerAgentVo.getArea()) || pioneerAgentVo.getAreaId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "区域不能为空");
				return result;
			}
			if(StringUtil.isEmpty(pioneerAgentVo.getAddress())){
				result.setError(ResultCode.CODE_STATE_4006, "代理商地址不能为空");
				return result;
			}
			pioneerAgentVo.setUdpateTime(new Date());
			pioneerAgentVo.setStatus(PioneerAgentStatus.WAIT_AUDIT);
			pioneerAgentVo.setHistoryRemark(pioneerAgentVo.getHistoryRemark() + DateUtil.format(new Date())+":重新提交;<br>");
			flag = this.pioneerAgentService.edit(pioneerAgentVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功",pioneerAgentVo);
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
	 * 根据id获取我提交的代理商信息
	 * @param 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAgentInfo", method = RequestMethod.GET)
	public Result getMyAgent(Integer agentId) {
		Result result = new Result();
		try{
			if(agentId == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取代理商信息失败");
				return result;
			}
			PioneerAgentVo pioneerAgentVo = this.pioneerAgentService.getById(agentId);
			result.setOK(ResultCode.CODE_STATE_200, "", pioneerAgentVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取我的代理商
	 * @param pioneerAgentSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMyAgent", method = RequestMethod.GET)
	public Result getMyAgent(PioneerAgentSearch pioneerAgentSearch) {
		Result result = new Result();
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			if(StringUtil.isEmpty(pioneerAgentSearch.getStatusMerge())){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前列表状态失败");
				return result;
			}
			if(!"s1".equals(pioneerAgentSearch.getStatusMerge()) && 
					!"s2".equals(pioneerAgentSearch.getStatusMerge()) && 
						!"s3".equals(pioneerAgentSearch.getStatusMerge())){
				result.setError(ResultCode.CODE_STATE_4006, "未知列表状态");
				return result;
			}
			pioneerAgentSearch.setSubmitterId(Integer.parseInt(clientId));
			Page<PioneerAgentVo> myAgent = this.pioneerAgentService.getPage(pioneerAgentSearch);
			result.setOK(ResultCode.CODE_STATE_200, "", myAgent);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取加盟代理商的销售明细
	 * @param pioneerAgentSaleInfoSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMyAgentSalesInfo", method = RequestMethod.GET)
	public Result getMyAgentSalesInfo(PioneerAgentSaleInfoSearch pioneerAgentSaleInfoSearch) {
		Result result = new Result();
		try{
			if(pioneerAgentSaleInfoSearch.getAgentId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取加盟代理商信息失败");
				return result;
			}
			Page<PioneerAgentSaleInfoVo> agentSalesInfo = this.pioneerAgentSaleInfoService.getPage(pioneerAgentSaleInfoSearch);
			result.setOK(ResultCode.CODE_STATE_200, "", agentSalesInfo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取我的提现申请列表
	 * @param withdrawalsApplySearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMyWithdrawalsApply", method = RequestMethod.GET)
	public Result getMyWithdrawalsApply(WithdrawalsApplySearch withdrawalsApplySearch) {
		Result result = new Result();
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			withdrawalsApplySearch.setApplierId(Integer.parseInt(clientId));
			Page<WithdrawalsApplyVo> myWithdrawalsApply = this.withdrawalsApplyService.getPage(withdrawalsApplySearch);
			result.setOK(ResultCode.CODE_STATE_200, "", myWithdrawalsApply);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 添加提现申请
	 * @param withdrawalsApplyVo
	 * @return
	 */
	@RequestMapping(value = "/addWithdrawalsApply", method = RequestMethod.POST)
	@ResponseBody
	public Result addWithdrawalsApply(WithdrawalsApplyVo withdrawalsApplyVo) {
		Result result = new Result();
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			withdrawalsApplyVo.setApplierId(Integer.parseInt(clientId));
			result = this.withdrawalsApplyService.addWithdrawalsApply(withdrawalsApplyVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 获取银行配置列表
	 * @param dictionarySearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBanKConfigList", method = RequestMethod.GET)
	public Result getBanKList(DictionarySearch dictionarySearch) {
		Result result = new Result();
		try{
			if(StringUtil.isEmpty(dictionarySearch.getDicType())){
				dictionarySearch.setDicType("bank_item");
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
	 * 获取我的银行卡信息
	 * @param bankSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMyBankInfo", method = RequestMethod.GET)
	public Result getMyBankInfo(BankSearch bankSearch) {
		Result result = new Result();
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			bankSearch.setMemberId(Integer.parseInt(clientId));
			List<BankVo> list = this.bankService.getList(bankSearch);
			result.setOK(ResultCode.CODE_STATE_200, "", list);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 添加我的银行信息
	 * @param bankVo
	 * @return
	 */
	@RequestMapping(value = "/addMyBank", method = RequestMethod.POST)
	@ResponseBody
	public Result addMyBank(BankVo bankVo) {
		Result result = new Result();
		Boolean flag = false;
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			bankVo.setMemberId(Integer.parseInt(clientId));
			if(StringUtil.isEmpty(bankVo.getBankholder())){
				result.setError(ResultCode.CODE_STATE_4006, "持卡人姓名不能为空");
				return result;
			}
			if(StringUtil.isEmpty(bankVo.getBankName()) || bankVo.getDicId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "持卡人银行不能为空");
				return result;
			}
			if(StringUtil.isEmpty(bankVo.getBankNum())){
				result.setError(ResultCode.CODE_STATE_4006, "银行卡号不能为空");
				return result;
			}
			flag = this.bankService.add(bankVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "添加成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "添加失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 删除我的银行信息
	 * @param bankVo
	 * @return
	 */
	@RequestMapping(value = "/delMyBank", method = RequestMethod.POST)
	@ResponseBody
	public Result delMyBank(Integer bankId) {
		Result result = new Result();
		Boolean flag = false;
		try{
			if(bankId == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取银行卡信息失败");
				return result;
			}
			flag = this.bankService.deleteById(bankId);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "删除成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "删除失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	
}
