package com.aylson.dc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.frame.base.Page;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.base.GeneralConstant.GiftConfigStatus;
import com.aylson.dc.base.GeneralConstant.MemberType;
import com.aylson.dc.base.GeneralConstant.PublishStatus;
import com.aylson.dc.base.GeneralConstant.PublishType;
import com.aylson.dc.base.OwnerGeneralConstant.DesignType;
import com.aylson.dc.base.OwnerGeneralConstant.FeedBackState;
import com.aylson.dc.mem.po.MemAccount;
import com.aylson.dc.mem.search.FeedBackReplySearch;
import com.aylson.dc.mem.search.FeedBackSearch;
import com.aylson.dc.mem.search.GiftConfigSearch;
import com.aylson.dc.mem.search.GiftSendDetailSearch;
import com.aylson.dc.mem.search.MemAccountSearch;
import com.aylson.dc.mem.search.PublishSearch;
import com.aylson.dc.mem.search.WxShareSearch;
import com.aylson.dc.mem.service.FeedBackReplyService;
import com.aylson.dc.mem.service.FeedBackService;
import com.aylson.dc.mem.service.GiftConfigService;
import com.aylson.dc.mem.service.GiftSendDetailService;
import com.aylson.dc.mem.service.GiftSendService;
import com.aylson.dc.mem.service.MemAccountService;
import com.aylson.dc.mem.service.PublishService;
import com.aylson.dc.mem.service.WxShareService;
import com.aylson.dc.mem.vo.FeedBackReplyVo;
import com.aylson.dc.mem.vo.FeedBackVo;
import com.aylson.dc.mem.vo.GiftConfigVo;
import com.aylson.dc.mem.vo.GiftSendDetailVo;
import com.aylson.dc.mem.vo.GiftSendVo;
import com.aylson.dc.mem.vo.MemAccountVo;
import com.aylson.dc.mem.vo.PublishVo;
import com.aylson.dc.mem.vo.WxShareVo;
import com.aylson.dc.owner.search.AppointmentSearch;
import com.aylson.dc.owner.search.BuyerShowPraiseSearch;
import com.aylson.dc.owner.search.BuyerShowSearch;
import com.aylson.dc.owner.search.DesignSearch;
import com.aylson.dc.owner.search.OrderScheduleSearch;
import com.aylson.dc.owner.search.OrderSearch;
import com.aylson.dc.owner.search.QuotationSearch;
import com.aylson.dc.owner.service.AppointmentService;
import com.aylson.dc.owner.service.BuyerShowPraiseService;
import com.aylson.dc.owner.service.BuyerShowService;
import com.aylson.dc.owner.service.DesignService;
import com.aylson.dc.owner.service.OrderScheduleService;
import com.aylson.dc.owner.service.OrderService;
import com.aylson.dc.owner.service.QuotationDetailDWService;
import com.aylson.dc.owner.service.QuotationDetailSRService;
import com.aylson.dc.owner.service.QuotationService;
import com.aylson.dc.owner.vo.AppointmentVo;
import com.aylson.dc.owner.vo.BuyerShowPraiseVo;
import com.aylson.dc.owner.vo.BuyerShowVo;
import com.aylson.dc.owner.vo.DesignVo;
import com.aylson.dc.owner.vo.OrderScheduleVo;
import com.aylson.dc.owner.vo.OrderVo;
import com.aylson.dc.owner.vo.QuotationDetailDWVo;
import com.aylson.dc.owner.vo.QuotationDetailSRVo;
import com.aylson.dc.owner.vo.QuotationVo;
import com.aylson.dc.sys.common.SystemConfig;
import com.aylson.dc.sys.po.CouponActivity;
import com.aylson.dc.sys.po.CouponUserRelations;
import com.aylson.dc.sys.search.CouponActivitySearch;
import com.aylson.dc.sys.search.CouponDetailSearch;
import com.aylson.dc.sys.search.CouponUserRelationsSearch;
import com.aylson.dc.sys.search.DictionarySearch;
import com.aylson.dc.sys.search.HelpSearch;
import com.aylson.dc.sys.search.ProductIntentSearch;
import com.aylson.dc.sys.search.ProductSearch;
import com.aylson.dc.sys.search.RegionSearch;
import com.aylson.dc.sys.search.StoreSearch;
import com.aylson.dc.sys.service.CouponActivityService;
import com.aylson.dc.sys.service.CouponDetailService;
import com.aylson.dc.sys.service.CouponUserRelationsService;
import com.aylson.dc.sys.service.DictionaryService;
import com.aylson.dc.sys.service.HelpService;
import com.aylson.dc.sys.service.ProductIntentService;
import com.aylson.dc.sys.service.ProductService;
import com.aylson.dc.sys.service.RegionService;
import com.aylson.dc.sys.service.StoreService;
import com.aylson.dc.sys.vo.CouponActivityVo;
import com.aylson.dc.sys.vo.CouponDetailVo;
import com.aylson.dc.sys.vo.CouponUserRelationsVo;
import com.aylson.dc.sys.vo.DictionaryVo;
import com.aylson.dc.sys.vo.HelpVo;
import com.aylson.dc.sys.vo.ProductIntentVo;
import com.aylson.dc.sys.vo.ProductVo;
import com.aylson.dc.sys.vo.RegionVo;
import com.aylson.dc.sys.vo.StoreVo;
import com.aylson.utils.DateUtil;
import com.aylson.utils.MD5Encoder;
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
 * 业主公众号控制器
 * 说明：
 * 	  1、存放开拓者联盟客户端的所有控制器接口，便于管理跟踪
 *    2、控制层尽量只负责控制跳转，业务逻辑在相关服务层实现
 * @author wwx
 * @since  2016-09
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/owner")
public class OwnerController extends BaseController {
	
	//服务类
	@Autowired
	private MemAccountService memAccountService;                //会员账号服务
	@Autowired
	private ProductService productService;                      //产品展示服务
	@Autowired
	private StoreService storeService;                          //门店展示服务
	@Autowired
	private PublishService publishService;                      //发布管理服务-艾臣资讯
	@Autowired
	private HelpService helpService;                            //常见问题服务
	@Autowired
	private FeedBackService feedBackService;                    //反馈管理服务
	@Autowired
	private FeedBackReplyService feedBackReplyService;          //反馈回复管理服务
	@Autowired
	private DictionaryService dictionaryService;                //系统-数据字典服务
	@Autowired
	private AppointmentService appointmentService;              //在线预约服务
	@Autowired
	private RegionService regionService;                        //系统-区域服务
	@Autowired
	private DesignService designService;                        //在线预约-设计信息表服务
	@Autowired
	private QuotationService quotationService;                  //在线预约-报价订货单服务
	@Autowired
	private QuotationDetailDWService quotationDetailDWService;  //门窗报价详情服务
	@Autowired
	private QuotationDetailSRService quotationDetailSRService;	//阳光房报价详情服务
	@Autowired
	private CouponActivityService couponActivityService;	    //优惠券活动配置
	@Autowired
	private CouponDetailService couponDetailService;	        //优惠券活动明细配置
	@Autowired
	private CouponUserRelationsService couponUserRelationsService;	//优惠券会员关系配置
	@Autowired
	private GiftSendService giftSendService;	               //礼品发货
	@Autowired
	private GiftSendDetailService giftSendDetailService;	   //礼品发货详情
	@Autowired
	private WxShareService wxShareService;	                   //微信分享关系
	@Autowired
	private GiftConfigService giftConfigService;	           //礼品配置服务
	@Autowired
	private OrderService orderService;	                       //订单跟踪服务
	@Autowired
	private OrderScheduleService orderScheduleService;	       //订单进度服务
	@Autowired
	private BuyerShowService buyerShowService;	               //买家秀服务
	@Autowired
	private BuyerShowPraiseService buyerShowPraiseService;	   //买家秀点赞服务
	@Autowired
	private ProductIntentService productIntentService;	       //产品意向登记服务
	/**
	 * 控制器接口代理
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/agency", method = RequestMethod.GET)
	public Result agency(){
		Result result = new Result();
		return result;
	}
	
	/************************************* 个人中心   开始  ***************************************************/
	
	/**
	 * 会员注册
	 * @param memAccountVo
	 * @return
	 */
	@RequestMapping(value = "/visitor/register", method = RequestMethod.POST)
	@ResponseBody
	public Result register(MemAccountVo memAccountVo) {
		Result result = new Result();
		try{
			/*if(StringUtil.isNotEmpty(code)){
				//获取微信用户信息
				ApiConfig apiConfig = this.getWxApiConfig();
		    	OauthAPI oauthAPI = new OauthAPI(apiConfig);
		        OauthGetTokenResponse response = oauthAPI.getToken(code);
		        if(response != null && StringUtil.isNotEmpty(response.getOpenid())){
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
			}*/
			result = this.memAccountService.ownerResister(memAccountVo);
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
	@RequestMapping(value = "/visitor/login", method = RequestMethod.POST)
	@ResponseBody
	public Result login(MemAccountVo memAccountVo) {
		Result result = new Result();
		try{
			if(StringUtil.isEmpty(memAccountVo.getAccountName())){
				result.setError(ResultCode.CODE_STATE_4006, "账号名不能为空");
				return result;
			}
			MemAccountSearch memAccountSearch = new MemAccountSearch();
			memAccountSearch.setAccountName(memAccountVo.getAccountName().trim());
			memAccountSearch.setMemberType(MemberType.OWNER);
			List<MemAccountVo> memAccountVoList = this.memAccountService.getList(memAccountSearch);
			if(memAccountVoList == null || memAccountVoList.size() == 0){
				result.setError(ResultCode.CODE_STATE_4006, "不存在该账号，请确认是否信息有误或联系相关人员");
				return result;
			}
			if(memAccountVoList.size() > 1){
				result.setError(ResultCode.CODE_STATE_4006, "存在两个账号，请确认是否信息有误或联系相关人员");
				return result;
			}
			MemAccountVo loginMem = memAccountVoList.get(0);
			if(!loginMem.getPwd().trim().equals(MD5Encoder.encodeByMD5(memAccountVo.getPwd().trim()))){
				result.setError(ResultCode.CODE_STATE_4006, "密码有误，请重新输入");
				return result;
			}
			loginMem.setPwd(null);
			result.setOK(ResultCode.CODE_STATE_200, "登录成功",loginMem);
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
		Result result = new Result();
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			MemAccountVo memAccountVo = this.memAccountService.getById(Integer.parseInt(clientId));
			if(memAccountVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			if(SystemConfig.isLiveMode()){
				memAccountVo.setAppId(SystemConfig.getConfigValueByKey("Formal_AppID"));
			}else{
				memAccountVo.setAppId(SystemConfig.getConfigValueByKey("Test_AppID"));
			}
			result.setOK(ResultCode.CODE_STATE_200, "查询成功",memAccountVo);
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
		Result result = new Result();
		try{
			if(StringUtil.isNotEmpty(mediaIds)){
				memAccountVo.setPhoto(this.dealWxPhoto(mediaIds, DomainToBucket.DOMAIN_MEMBER));
			}
			if(memAccountVo.getId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取账号信息失败！请重新登录再修改");
				return result;
			}
			if(StringUtil.isNotEmpty(memAccountVo.getBirthdayStr())){
				memAccountVo.setBirthday(DateUtil.format(memAccountVo.getBirthdayStr(), "yyyy-MM-dd"));
			}
			Boolean flag = this.memAccountService.edit(memAccountVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "修改成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "修改失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
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
	@RequestMapping(value = "/visitor/modifyPwd", method = RequestMethod.POST)
	@ResponseBody
	public Result modifyPwd(String mobilePhone, String validCode, String newPwd, String confirmPwd) {
		Result result = null;
		try{
			result = this.memAccountService.modifyPwd(mobilePhone, validCode, newPwd, confirmPwd, MemberType.OWNER);
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
	 * 通过授权码获取用户信息
	 * @param code
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/getByCode", method = RequestMethod.GET)
    public Result getByCode(String code) {
    	Result result = new Result();
    	if(StringUtil.isEmpty(code)){
    		result.setError(ResultCode.CODE_STATE_500, "授权码不能为空");
    	}
    	MemAccount memAccount = null;
    	try{
    		//获取微信用户信息
        	OauthAPI oauthAPI = new OauthAPI( this.getWxApiConfig());
            OauthGetTokenResponse response = oauthAPI.getToken(code);
            if(response!=null && response.getOpenid()!=null){
            	UserAPI userAPI = new UserAPI( this.getWxApiConfig());
                GetUserInfoResponse userInfo = userAPI.getUserInfo(response.getOpenid());
                if(StringUtil.isNotEmpty(response.getOpenid())){
                	MemAccountSearch memAccountSearch = new MemAccountSearch();
                	memAccountSearch.setWxOpenId(response.getOpenid());
            		memAccountSearch.setMemberType(MemberType.OWNER);
            		List<MemAccountVo> list = this.memAccountService.getList(memAccountSearch);
            		if(list != null && list.size() > 0){
            			memAccount = list.get(0);
            		}else{
            			memAccount = new MemAccount();
                		memAccount.setWxHeadPhoto(userInfo.getHeadimgurl());
                		memAccount.setWxOpenId(userInfo.getOpenid());
                		memAccount.setWxUnionId(userInfo.getUnionid());
                		memAccount.setWxNickName(userInfo.getNickname());
            		}
                }
            }
	    	result.setOK(ResultCode.CODE_STATE_200, "", memAccount);
    	}catch(Exception e){
    		e.printStackTrace();
    		result.setError(ResultCode.CODE_STATE_4008, "系统繁忙，请稍候重试");
    	}
    	return result;
    }
	/************************************* 个人中心   结束  ***************************************************/
	/************************************* 产品介绍   开始  ***************************************************/
	/**
	 * 获取产品介绍种类
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getProductCategory", method = RequestMethod.GET)
	public Result getProductCategory() {
		Result result = new Result();
		try{
			DictionarySearch dictionarySearch = new DictionarySearch();
			dictionarySearch.setDicType("ProductIntroduceCategory_Item");
			List<DictionaryVo> list = this.dictionaryService.getList(dictionarySearch);
			result.setOK(ResultCode.CODE_STATE_200, "", list);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取产品分页列表
	 * @param productSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getProductList", method = RequestMethod.GET)
	public Result getProductList(ProductSearch productSearch) {
		Result result = new Result();
		try{
			if(productSearch.getCategory() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取产品分类失败");
				return result;
			}
			Page<ProductVo> page = this.productService.getPage(productSearch);
			result.setOK(ResultCode.CODE_STATE_200, "", page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 根据产品id获取产品详情
	 * @param productId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getProductDetail", method = RequestMethod.GET)
	public Result getProductDetail(Integer productId) {
		Result result = new Result();
		try{
			if(productId == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取产品资料信息失败");
				return result;
			}
			ProductVo productVo = this.productService.getById(productId);
			result.setOK(ResultCode.CODE_STATE_200, "", productVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	
	/************************************* 产品介绍   结束  ***************************************************/
	
	/************************************* 门店展示   开始  ***************************************************/
	/**
	 * 获取门店信息分页列表
	 * @param storeSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getStoreList", method = RequestMethod.GET)
	public Result getStoreList(StoreSearch storeSearch) {
		Result result = new Result();
		try{
			Page<StoreVo> page = this.storeService.getPage(storeSearch);
			result.setOK(ResultCode.CODE_STATE_200, "", page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 根据门店id获取门店详情
	 * @param storeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getStoreDetail", method = RequestMethod.GET)
	public Result getStoreDetail(Integer storeId) {
		Result result = new Result();
		try{
			if(storeId == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取门店详情信息失败");
				return result;
			}
			StoreVo storeVo = this.storeService.getById(storeId);
			if(StringUtil.isNotEmpty(storeVo.getStoreImgs())){
				String[] storeImgItem = storeVo.getStoreImgs().split("<;>");
				if(storeImgItem != null && storeImgItem.length > 0){
					List<String> storeImgList = new ArrayList<String>();
					for(int i=0; i<storeImgItem.length; i++){
						storeImgList.add(storeImgItem[i]);
					}
					storeVo.setStoreImgList(storeImgList);
				}
			}
			result.setOK(ResultCode.CODE_STATE_200, "", storeVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/************************************* 门店展示   结束  ***************************************************/
	/************************************* 艾臣资讯   开始  ***************************************************/

	/**
	 * 获取发布信息列表：
	 * @param publishSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getPublishList", method = RequestMethod.GET)
	public Result getPublishList(PublishSearch publishSearch) {
		Result result = new Result();
		try{
			publishSearch.setStatus(PublishStatus.PUBLISH);
			publishSearch.setType(PublishType.AYLSONINFO);//艾臣资讯
			Page<PublishVo> page = this.publishService.getPage(publishSearch);
			result.setOK(ResultCode.CODE_STATE_200, "", page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 根据发布ID查看发布详情
	 * @param publishId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getPublishDetail", method = RequestMethod.GET)
	public Result getPublishDetail(Integer publishId) {
		Result result = new Result();
		try{
			if(publishId == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取详情信息失败");
				return result;
			}
			PublishVo publishVo = this.publishService.getById(publishId);
			result.setOK(ResultCode.CODE_STATE_200, "", publishVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	/************************************* 门艾臣资讯  结束  ***************************************************/
	/************************************* 问题反馈  开始  ***************************************************/
	/**
	 * 获取常见问题列表
	 * @param helpSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getHelpList", method = RequestMethod.GET)
	public Result getHelpList(HelpSearch helpSearch) {
		Result result = new Result();
		try{
			Page<HelpVo> page = this.helpService.getPage(helpSearch);
			result.setOK(ResultCode.CODE_STATE_200, "", page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取常见问题详情
	 * @param helpId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getHelpDetail", method = RequestMethod.GET)
	public Result getHelpDetail(Integer helpId) {
		Result result = new Result();
		try{
			if(helpId == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取详情信息失败");
				return result;
			}
			HelpVo helpVo = this.helpService.getById(helpId);
			result.setOK(ResultCode.CODE_STATE_200, "", helpVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取我的反馈列表
	 * @param feedBackSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMyFeedBackList", method = RequestMethod.GET)
	public Result getMyFeedBackList(FeedBackSearch feedBackSearch) {
		Result result = new Result();
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取反馈人信息失败");
				return result;
			}
			feedBackSearch.setFeedbackerType(MemberType.OWNER);
			feedBackSearch.setFeedbackerId(Integer.parseInt(clientId));
			Page<FeedBackVo> page = this.feedBackService.getPage(feedBackSearch);
			result.setOK(ResultCode.CODE_STATE_200, "", page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取我的反馈详情
	 * @param feedBackId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMyFeedBackDetail", method = RequestMethod.GET)
	public Result getMyFeedBackDetail(Integer feedBackId, Integer page, Integer rows) {
		Result result = new Result();
		try{
			if(feedBackId == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取详情信息失败");
				return result;
			}
			FeedBackVo feedBackVo = this.feedBackService.getById(feedBackId);
			if(feedBackVo != null){
				feedBackVo.setState(FeedBackState.FEEDBACKER_HAD_VIEW);
				Boolean flag = this.feedBackService.edit(feedBackVo);
				if(!flag){
					result.setError(ResultCode.CODE_STATE_4006, "更新详情信息失败");
					return result;
				}
			}
			FeedBackReplySearch feedBackReplySearch = new FeedBackReplySearch();
			feedBackReplySearch.setFeedBackId(feedBackId);
			if(page != null){feedBackReplySearch.setPage(page);}
			if(rows != null){feedBackReplySearch.setRows(rows);}
			Page<FeedBackReplyVo> feedBackReplyPage = this.feedBackReplyService.getPage(feedBackReplySearch);
			feedBackVo.setFeedBackReplyPage(feedBackReplyPage);
			result.setOK(ResultCode.CODE_STATE_200, "", feedBackVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取我的反馈回复详情
	 * @param feedBackId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getMyFeedBackReply", method = RequestMethod.GET)
	public Result getMyFeedBackReply(Integer feedBackId, Integer page, Integer rows) {
		Result result = new Result();
		try{
			if(feedBackId == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取详情信息失败");
				return result;
			}
			FeedBackReplySearch feedBackReplySearch = new FeedBackReplySearch();
			feedBackReplySearch.setFeedBackId(feedBackId);
			if(page != null){feedBackReplySearch.setPage(page);}
			if(rows != null){feedBackReplySearch.setRows(rows);}
			Page<FeedBackReplyVo> feedBackReplyPage = this.feedBackReplyService.getPage(feedBackReplySearch);
			result.setOK(ResultCode.CODE_STATE_200, "", feedBackReplyPage);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 会员添加反馈
	 * @param feedBackVo
	 * @return
	 */
	@RequestMapping(value = "/addFeedBack", method = RequestMethod.POST)
	@ResponseBody
	public Result addFeedBack(FeedBackVo feedBackVo) {
		Result result = new Result();
		try{
			if(StringUtil.isEmpty(feedBackVo.getTitle())){
				result.setError(ResultCode.CODE_STATE_4006, "请输入意见反馈的主题");
				return result;
			}
			if(StringUtil.isEmpty(feedBackVo.getContent())){
				result.setError(ResultCode.CODE_STATE_4006, "请对问题进行详细描述，方便客服人员了解问题，谢谢！！");
				return result;
			}
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取反馈人信息失败");
				return result;
			}
			Date curDate = new Date();
			feedBackVo.setFeedbackTime(curDate);
			feedBackVo.setReplyTime(curDate);
			feedBackVo.setFeedbackerType(MemberType.OWNER);
			feedBackVo.setFeedbackerId(Integer.parseInt(clientId));
			feedBackVo.setState(FeedBackState.WAIT_REPLY);//待回复
			Boolean flag = this.feedBackService.add(feedBackVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 反馈已读
	 * @param feedBackVo
	 * @return
	 */
	@RequestMapping(value = "/editFeedBack", method = RequestMethod.POST)
	@ResponseBody
	public Result editFeedBack(FeedBackVo feedBackVo) {
		Result result = new Result();
		try{
			if(feedBackVo.getId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取反馈信息主键失败");
				return result;
			}
			feedBackVo.setState(FeedBackState.FEEDBACKER_HAD_VIEW);
			Boolean flag = this.feedBackService.edit(feedBackVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 回复反馈
	 * @param feedBackReplyVo
	 * @return
	 */
	@RequestMapping(value = "/replyFeedBack", method = RequestMethod.POST)
	@ResponseBody
	public Result replyFeedBack(FeedBackReplyVo feedBackReplyVo) {
		Result result = new Result();
		try{
			feedBackReplyVo.setReplyType(1);//提问人
			result = this.feedBackReplyService.reply(feedBackReplyVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	/************************************* 问题反馈  结束  ***************************************************/
	/************************************* 在线预约  开始  ***************************************************/
	/**
	 * 获取预约列表
	 * @param appointmentSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getAppointList", method = RequestMethod.GET)
	public Result getAppointList(AppointmentSearch appointmentSearch) {
		Result result = new Result();
		try{
			Page<AppointmentVo> page = this.appointmentService.getPage(appointmentSearch);
			result.setOK(ResultCode.CODE_STATE_200, "", page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取位置信息
	 * @param regionSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getLocation", method = RequestMethod.GET)
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
	 * 获取所有位置位置信息
	 * @param regionSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getAllLocation", method = RequestMethod.GET)
	public List<Map<String, String>> getAllLocation(RegionSearch regionSearch) {
		List<Map<String, String>> all = new ArrayList<Map<String, String>>();
		try{
			List<RegionVo> list = this.regionService.getList(regionSearch);
			if(list != null && list.size() > 0){
				for(RegionVo RegionVo: list ){
					if("0".equals(RegionVo.getRegionLevel())){
						continue;
					}
					Map<String, String> locationObj = new HashMap<String, String>();
					locationObj.put("name", RegionVo.getRegionName());
					locationObj.put("value", RegionVo.getRegionId()+"");
					if(!"1".equals(RegionVo.getRegionLevel()) && RegionVo.getParentId() != null){
						locationObj.put("parent", RegionVo.getParentId()+"");
					}
					all.add(locationObj);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return all;
	}
	
	/**
	 * 添加预约
	 * @param appointmentVo
	 * @return
	 */
	@RequestMapping(value = "/visitor/addAppoint", method = RequestMethod.POST)
	@ResponseBody
	public Result addAppoint(AppointmentVo appointmentVo) {
		Result result = new Result();
		try{
			/*if(StringUtil.isEmpty(appointmentVo.getName())){
				result.setError(ResultCode.CODE_STATE_4006, "姓名不能为空");
				return result;
			}
			if(StringUtil.isEmpty(appointmentVo.getMobilePhone())){
				result.setError(ResultCode.CODE_STATE_4006, "电话不能为空");
				return result;
			}
			if(!VerificationUtils.isValid(appointmentVo.getMobilePhone(), VerificationUtils.MOBILE)){
				result.setError(ResultCode.CODE_STATE_4006, "手机号码格式有误");
				return result;
			}
			if(StringUtil.isEmpty(appointmentVo.getProvince()) || appointmentVo.getProvinceId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "所在城市不能为空");
				return result;
			}
			if(StringUtil.isEmpty(appointmentVo.getCity()) || appointmentVo.getCityId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "所在城市不能为空");
				return result;
			}
			if(StringUtil.isEmpty(appointmentVo.getArea()) || appointmentVo.getAreaId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "所在城市不能为空");
				return result;
			}
			appointmentVo.setAppointDate(new Date());
			appointmentVo.setSource("wx"); //添加来源：wx公众号
			appointmentVo.setBillCode(BillNumUtils.getBillCode(BillCodePrefix.APPOINTMENT,appointmentVo.getMobilePhone()));
			Boolean flag = this.appointmentService.add(appointmentVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功",appointmentVo);
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}*/
			result = this.appointmentService.addAppointMent(appointmentVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 预约订单确认
	 * @return
	 */
	@RequestMapping(value = "/visitor/appointConfirm", method = RequestMethod.POST)
	@ResponseBody
	public Result appointConfirm(Integer appointId, Boolean isComfirm, String opinion, Integer couponId, Integer couponUserId){
		Result result = null;
		try{
			result = this.appointmentService.appointConfirm(appointId, isComfirm, opinion, couponId, couponUserId);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请您稍后再试或者联系工作人员");
		}
		return result;
	}
	
	/**
	 * 获取预约的设计信息表
	 * @param appointId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getDesign", method = RequestMethod.GET)
	public Result getDesign(Integer appointId) {
		Result result = new Result();
		try{
			if(appointId == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取不到预约信息");
				return result;
			}
			DesignSearch search = new DesignSearch();
			search.setAppointId(appointId);
			List<DesignVo> list = this.designService.getList(search);
			result.setOK(ResultCode.CODE_STATE_200, "", list);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 根据预约id获取报价订货单列表
	 * @param appointId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getQuotation", method = RequestMethod.GET)
	public Result getQuotation(Integer appointId) {
		Result result = new Result();
		try{
			if(appointId == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取不到预约信息");
				return result;
			}
			QuotationSearch search = new QuotationSearch();
			search.setAppointId(appointId);
			List<QuotationVo> list = this.quotationService.getList(search);
			result.setOK(ResultCode.CODE_STATE_200, "", list);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 根据订货单id获取详情
	 * @param quotationId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getQuotationDetail", method = RequestMethod.GET)
	public Result getQuotationDetail(Integer quotationId) {
		Result result = new Result();
		try{
			if(quotationId == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取不到报价单信息");
				return result;
			}
			QuotationVo quotationVo = this.quotationService.getById(quotationId);
			if(quotationVo != null){
				if(DesignType.SUMROOM == quotationVo.getDesignType().intValue()){
					List<QuotationDetailSRVo> detailSRVoList = this.quotationDetailSRService.getByQuotationId(quotationId); 
					quotationVo.setDetailSRVoList(detailSRVoList);
				}else{
					List<QuotationDetailDWVo> detailDWVoList = this.quotationDetailDWService.getByQuotationId(quotationId);   
					quotationVo.setDetailDWVoList(detailDWVoList);
				}
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "查询不到报价单信息");
				return result;
			}
			result.setOK(ResultCode.CODE_STATE_200, "", quotationVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 确认报价
	 * @param designId
	 * @param isSatisfy
	 * @return
	 */
	@RequestMapping(value = "/confirmQuotation", method = RequestMethod.POST)
	@ResponseBody
	public Result confirmQuotation(Integer designId, Boolean isSatisfy) {
		Result result = null;
		try{
			result = this.designService.confirmQuotation(designId, isSatisfy);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 根据预约id获取预约所有相关信息
	 * @param appointId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getAppointDetail", method = RequestMethod.GET)
	public Result getAppointDetail(Integer appointId) {
		Result result = new Result();
		try{
			//result = this.appointmentService.getAppointDetail(appointId);
			result = this.appointmentService.getAppointInfo(appointId);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 确认订单
	 * @param appointId     预约id
	 * @param isSatisfy     是否满意报价并下单
	 * @param couponId      优惠券明细id，isSatisfy = true时会做判断
	 * @param couponUserId  优惠券用户id，isSatisfy = true时会做判断
	 * @return
	 */
	@RequestMapping(value = "/visitor/confirmAllQuotation", method = RequestMethod.POST)
	@ResponseBody
	public Result confirmAllQuotation(Integer appointId, Integer couponId, Integer couponUserId, Boolean isSatisfy) {
		Result result = null;
		try{
			result = this.designService.confirmAllQuotation(appointId,couponId,couponUserId, isSatisfy);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	/************************************* 在线预约  结束  ***************************************************/
	
	/************************************* 优惠券活动配置  开始  ***************************************************/
	
	/**
	 * 获取优惠券活动配置
	 * @param couponActivitySearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getCouponActivity", method = RequestMethod.GET)
	public Result getCouponActivity(CouponActivitySearch couponActivitySearch) {
		Result result = new Result();
		try{
			couponActivitySearch.setState(1);
			Page<CouponActivityVo> page = this.couponActivityService.getPage(couponActivitySearch);
			result.setOK(ResultCode.CODE_STATE_200, "", page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取优惠券明细配置
	 * @param couponDetailSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getCouponDetail", method = RequestMethod.GET)
	public Result getCouponDetail(CouponDetailSearch couponDetailSearch) {
		Result result = new Result();
		try{
			couponDetailSearch.setIsEnabled(1);
			Page<CouponDetailVo> page = this.couponDetailService.getPage(couponDetailSearch);
			result.setOK(ResultCode.CODE_STATE_200, "", page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 通过会员关系的优惠券明细数据
	 * @param couponUserRelationsSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getCouponUserRelations", method = RequestMethod.GET)
	public Result getCouponUserRelations(CouponUserRelationsSearch couponUserRelationsSearch) {
		Result result = new Result();
		try{
			//优惠券，0=未使用，默认值；1=已使用；-1=已过期
			String isUsed = this.request.getParameter("isUsed");
			if(!isUsed.equals("-1")){
				couponUserRelationsSearch.setIsUsed(Integer.valueOf(isUsed));
			}else{
				couponUserRelationsSearch.setIsUsed(Integer.valueOf(0));
			}
			List<CouponUserRelations> list = this.couponUserRelationsService.getList(couponUserRelationsSearch);
			Map<String, Object> params = new HashMap<>();
			List<Integer> ids = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				CouponUserRelations cur = list.get(i);
				ids.add(cur.getCouponFkid());
			}
			List page = null;
			if(ids.size()>0){
				params.put("ids", ids);
				params.put("isUsed", isUsed);
				params.put("phoneNum", couponUserRelationsSearch.getPhoneNum());
				page = this.couponDetailService.selectCouponDetails(params);
			}
			result.setOK(ResultCode.CODE_STATE_200, "", page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 新增绑定优惠券会员关系数据
	 * @param couponUserRelationsVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/addCouponUserRelations", method = RequestMethod.POST)
	public Result addCouponUserRelations(CouponUserRelationsVo couponUserRelationsVo) {
		Result result = new Result();
		try{
			if(null==couponUserRelationsVo.getPhoneNum() || 
				null==couponUserRelationsVo.getActivityFkid() || 
						null==couponUserRelationsVo.getCouponFkid()){
				result.setOK(5005, "传值参数有误");
				
			}else{
				CouponActivitySearch couponActivitySearch = new CouponActivitySearch();
				couponActivitySearch.setId(couponUserRelationsVo.getActivityFkid());;
				List<CouponActivity> listActivity = this.couponActivityService.getList(couponActivitySearch);
				//活动是否存在
				if(listActivity.size()>0){
					//判断活动有效期
					CouponActivity activity = listActivity.get(0);
					Date c = new Date();
					if(activity.getEndTime().before(c)){
						result.setOK(5003, "该活动已过期");
						
					}else{
						int limitNum = activity.getLimitNum();
						CouponUserRelationsSearch search = new CouponUserRelationsSearch();
						search.setActivityFkid(couponUserRelationsVo.getActivityFkid());
						List<CouponUserRelations> list = this.couponUserRelationsService.getList(search);
						//判断是否超出活动人数限制
						if(list.size()>=limitNum){
							result.setOK(5004, "活动参与人数已达上限");
							
						}else{
							search.setPhoneNum(couponUserRelationsVo.getPhoneNum());
							search.setCouponFkid(couponUserRelationsVo.getCouponFkid());
							list = this.couponUserRelationsService.getList(search);
							if(list.size()>0){
								result.setOK(5001, "你已领取过了");
								
							}else{
								couponUserRelationsVo.setCreatedTime(new Date());
								Boolean flag = this.couponUserRelationsService.add(couponUserRelationsVo);
								if(flag){
									result.setOK(ResultCode.CODE_STATE_200, "操作成功");
								}else{
									result.setError(ResultCode.CODE_STATE_4006, "操作失败");
								}
							}
						}
					}
					
				}else{
					result.setOK(5002, "活动数据不存在或异常.");
				}
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 使用优惠券会员关系数据
	 * @param couponUserRelationsVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/updateCouponUserRelations", method = RequestMethod.POST)
	public Result updateCouponUserRelations(CouponUserRelationsVo couponUserRelationsVo) {
		Result result = new Result();
		try{
			couponUserRelationsVo.setIsUsed(1);	//已使用
			couponUserRelationsVo.setUsedTime(new Date());
			Boolean flag = this.couponUserRelationsService.edit(couponUserRelationsVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/************************************* 优惠券活动配置  结束  ***************************************************/
	/************************************* 新人福利  开始  ***************************************************/
	/**
	 * 获取获奖名单
	 * @param giftSendDetailSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getWinningGiftList", method = RequestMethod.GET)
	public Result getWinningGiftList(GiftSendDetailSearch giftSendDetailSearch) {
		Result result = new Result();
		try{
			if(StringUtil.isEmpty(giftSendDetailSearch.getChannel())){
				result.setError(ResultCode.CODE_STATE_4006, "获奖渠道不能为空");
				return result;
			}
			Page<GiftSendDetailVo> page = this.giftSendDetailService.getPage(giftSendDetailSearch);
			for(GiftSendDetailVo GiftSendDetailVo : page.getRowsObject()){
				GiftSendDetailVo.setConsigneePhone(GiftSendDetailVo.getConsigneePhone().substring(0, 6)+"****");
			}
			result.setOK(ResultCode.CODE_STATE_200, "", page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 参加新人福利活动
	 * @param wxShareVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/addShare", method = RequestMethod.POST)
	public Result addShare(WxShareVo wxShareVo) {
		Result result = new Result();
		try{
			if(StringUtil.isEmpty(wxShareVo.getChannel())){
				result.setError(ResultCode.CODE_STATE_4006, "获取不到活动渠道");
				return result;
			}
			if(StringUtil.isEmpty(wxShareVo.getWxOpenId())){
				result.setError(ResultCode.CODE_STATE_4006, "获取不到wxOpernId");
				return result;
			}
			if(StringUtil.isEmpty(wxShareVo.getWxUnionId())){
				result.setError(ResultCode.CODE_STATE_4006, "获取不到wxUnionId");
				return result;
			}
			if(StringUtil.isEmpty(wxShareVo.getWxHeadPhoto())){
				result.setError(ResultCode.CODE_STATE_4006, "获取不到微信头像");
				return result;
			}
			WxShareSearch wxShareSearch = new WxShareSearch();
			wxShareSearch.setChannel(wxShareVo.getChannel());
			wxShareSearch.setWxOpenId(wxShareVo.getWxOpenId());
			List<WxShareVo> list = this.wxShareService.getList(wxShareSearch);
			Boolean flag = true;
			if(list == null || list.size() <= 0){
				Date curDate = new Date();
				wxShareVo.setAssistValue(200);
				wxShareVo.setCreateTime(curDate);
				wxShareVo.setRemark(DateUtil.format(curDate)+"参加新人福利活动,活动渠道为："+wxShareVo.getChannel());
				flag = this.wxShareService.add(wxShareVo);
			}
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "", wxShareVo);
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "分享失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取活动分享信息
	 * @param wxShareSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getShareList", method = RequestMethod.GET)
	public Result getShareList(WxShareSearch wxShareSearch) {
		Result result = new Result();
		Map<String, Object> dateMap = new HashMap<String, Object>();
		try{
			if(StringUtil.isEmpty(wxShareSearch.getChannel())){
				result.setError(ResultCode.CODE_STATE_4006, "活动渠道不能为空");
				return result;
			}
			if(StringUtil.isEmpty(wxShareSearch.getInviterWxOpenId())){
				result.setError(ResultCode.CODE_STATE_4006, "分享人wxOpernId不能为空");
				return result;
			}
			WxShareVo sharer = null;  //分享人信息
			Page<WxShareVo> page = this.wxShareService.getPage(wxShareSearch);
			WxShareSearch search2 =  new WxShareSearch();
			search2.setWxOpenId(wxShareSearch.getInviterWxOpenId());
			List<WxShareVo> list = this.wxShareService.getList(search2);
			if(list != null && list.size() > 0){
				sharer = list.get(0);
			}
			dateMap.put("shareList", page);
			dateMap.put("sharer", sharer);
			result.setOK(ResultCode.CODE_STATE_200, "", dateMap);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取活动礼品列表
	 * @param giftConfigSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getGiftList", method = RequestMethod.GET)
	public Result getGiftList(GiftConfigSearch giftConfigSearch, String wxOpenId) {
		Result result = new Result();
		try{
			if(StringUtil.isEmpty(giftConfigSearch.getMatchActivityCode())){
				result.setError(ResultCode.CODE_STATE_4006, "获取礼品活动编码失败");
				return result;
			}
			if(StringUtil.isEmpty(wxOpenId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前用户wxOpenId失败");
				return result;
			}
			giftConfigSearch.setStatus(GiftConfigStatus.UP);
			List<GiftConfigVo> list = this.giftConfigService.getList(giftConfigSearch);
			if(list != null && list.size() > 0){
				//判断是否已经领取过
				GiftSendDetailSearch giftSendDetailSearch = new GiftSendDetailSearch();
			    giftSendDetailSearch.setChannel(giftConfigSearch.getMatchActivityCode());
			    giftSendDetailSearch.setAccountPk(wxOpenId);
			    List<GiftSendDetailVo> giftSendDetailList = this.giftSendDetailService.getList(giftSendDetailSearch);
				//判断是否可以领取
				WxShareSearch wxShareSearch = new WxShareSearch();
				wxShareSearch.setChannel(giftConfigSearch.getMatchActivityCode());
				wxShareSearch.setInviterWxOpenId(wxOpenId);
			    long curShareNum = this.wxShareService.getRowCount(wxShareSearch);
			    for(GiftConfigVo giftConfigVo:list){
			    	if(giftSendDetailList != null && giftSendDetailList.size() > 0){
			    		for(GiftSendDetailVo giftSendDetailVo:giftSendDetailList){
			    			if(giftConfigVo.getId().intValue() == giftSendDetailVo.getGiftId().intValue()){
			    				giftConfigVo.setIsExchanged(true);
			    				break;
			    			}
			    		}
			    	}
			    	if(giftConfigVo.getIntegral().longValue() <= curShareNum){//如果礼品配置积分数小于或者等于助力人数
			    		giftConfigVo.setWhetherExchange(true);//可以领取
			    	}
			    }
			}
			result.setOK(ResultCode.CODE_STATE_200, "", list);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 添加礼品送货单
	 * @param giftSendVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/addGiftSend", method = RequestMethod.POST)
	public Result addGiftSend(GiftSendVo giftSendVo) {
		Result result = null;
		try{
			result = this.giftSendService.addGiftSend(giftSendVo);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/************************************* 新人福利  结束  ***************************************************/
	/************************************* 订单跟踪  开始  ***************************************************/
	/**
	 * 获取订单列表
	 * @param orderSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getOrderList", method = RequestMethod.GET)
	public Result getOrderList(OrderSearch orderSearch) {
		Result result = new Result();
		try{
			if(StringUtil.isEmpty(orderSearch.getPhoneOrNo())){
				result.setError(ResultCode.CODE_STATE_4006, "请输入订单号或者手机号");
				return result;
			}
			//按状态倒叙
			orderSearch.setSort("updateTime");
			orderSearch.setOrder("desc");
			Page<OrderVo> page = this.orderService.getPage(orderSearch);
			result.setOK(ResultCode.CODE_STATE_200, "", page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 根据订单id获取订单详情
	 * @param orderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getOrderDetail", method = RequestMethod.GET)
	public Result getOrderDetail(Integer orderId) {
		Result result = new Result();
		try{
			if(orderId == null){
				result.setError(ResultCode.CODE_STATE_4006, "订单id不能为空");
				return result;
			}
			OrderVo orderVo = this.orderService.getById(orderId);
			if(orderVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "订单信息不存在");
				return result;
			}
			OrderScheduleSearch orderScheduleSearch = new OrderScheduleSearch();
			orderScheduleSearch.setOrderId(orderId);
			List<OrderScheduleVo> scheduleVoList = this.orderScheduleService.getList(orderScheduleSearch);
			orderVo.setScheduleVoList(scheduleVoList);
			result.setOK(ResultCode.CODE_STATE_200, "", orderVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 确认收货
	 * @param orderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/receipt", method = RequestMethod.POST)
	public Result receipt(Integer orderId) {
		Result result = new Result();
		try{
			if(orderId == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取订单信息失败");
				return result;
			}
			OrderVo orderVo = this.orderService.getById(orderId);
			if(orderVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取订单信息失败");
				return result;
			}
			if(orderVo.getState() != null && orderVo.getState().intValue() == 5){
				result.setError(ResultCode.CODE_STATE_4006, "已经收货过了，不用重复操作");
				return result;
			}
			OrderVo updateInfo = new OrderVo();
			updateInfo.setId(orderId);
			updateInfo.setState(5);//确认收货
			updateInfo.setScheduleRemark("确认收货");
			result = this.orderService.editOrder(updateInfo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 添加买家秀
	 * @param buyerShowVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addBuyerShow", method = RequestMethod.POST)
	public Result addBuyerShow(BuyerShowVo buyerShowVo, String mediaIds) {
		Result result = new Result();
		try{
			//校验：是否发表过买家秀
			if(buyerShowVo.getOrderId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取订单信息失败");
				return result;
			}
			if(StringUtil.isEmpty(mediaIds)){
				result.setError(ResultCode.CODE_STATE_4006, "至少上传一张图片");
				return result;
			}
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前登录人信息失败");
				return result;
			}
			OrderVo orderVo = this.orderService.getById(buyerShowVo.getOrderId());
			if(orderVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取订单信息失败");
				return result;
			}
			if(orderVo.getHasBuyShow()){
				result.setError(ResultCode.CODE_STATE_4006, "您已经发布过该订单的买家秀了，不能重复发布");
				return result;
			}
			//可以发表
			if(orderVo.getProvinceId() != null){
				RegionVo provice = this.regionService.getById(orderVo.getProvinceId());
				if(provice != null){
					buyerShowVo.setProvince(provice.getRegionName());
					buyerShowVo.setProvinceCode(provice.getRegionCode());
					buyerShowVo.setProvinceId(provice.getRegionId());
				}
			}
			if(orderVo.getCityId() != null){
				RegionVo city = this.regionService.getById(orderVo.getCityId());
				if(city != null){
					buyerShowVo.setCity(city.getRegionName());
					buyerShowVo.setCityCode(city.getRegionCode());
					buyerShowVo.setCityId(city.getRegionId());
				}
			}
			if(orderVo.getAreaId() != null){
				RegionVo area = this.regionService.getById(orderVo.getAreaId());
				if(area != null){
					buyerShowVo.setArea(area.getRegionName());
					buyerShowVo.setAreaCode(area.getRegionCode());
					buyerShowVo.setAreaId(area.getRegionId());
				}
			}
			buyerShowVo.setImgUrls(this.dealWxPhoto(mediaIds, "aylson-buyershow"));//获取微信上传的图片
			buyerShowVo.setAppointId(orderVo.getAppointId());
			buyerShowVo.setCreateTime(new Date());
			buyerShowVo.setDesignId(orderVo.getDesignId());
			buyerShowVo.setDesignType(orderVo.getDesignType());
			buyerShowVo.setMemberId(Integer.parseInt(clientId));
			Boolean flag = this.buyerShowService.add(buyerShowVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "操作失败");
		}
		return result;
	}
	
	/**
	 * 获取买家秀列表
	 * @param buyerShowSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBuyerShowList", method = RequestMethod.GET)
	public Result getBuyerShowList(BuyerShowSearch buyerShowSearch) {
		Result result = new Result();
		try{
			Page<BuyerShowVo> page = this.buyerShowService.getPage(buyerShowSearch);
			result.setOK(ResultCode.CODE_STATE_200, "", page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "操作失败");
		}
		return result;
	}
	
	/**
	 * 获取买家秀详情
	 * @param buyerShowId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBuyerShowDetail", method = RequestMethod.GET)
	public Result getBuyerShowDetail(Integer buyerShowId, String operater) {
		Result result = new Result();
		try{
			if(buyerShowId == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取买家秀信息失败");
				return result;
			}
			BuyerShowVo buyerShowVo = this.buyerShowService.getById(buyerShowId);
			if(buyerShowVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取买家秀信息失败");
				return result;
			}
			if(StringUtil.isEmpty(operater)){
				result.setError(ResultCode.CODE_STATE_4006, "获取操作人信息失败");
				return result;
			}
			//添加一次阅读数
			BuyerShowVo read = new BuyerShowVo();
			read.setId(buyerShowId);
			read.setReadNum(buyerShowVo.getReadNum()+1);
			this.buyerShowService.edit(read);
			//获取会员信息
			if(buyerShowVo.getMemberId() != null){
				MemAccountVo memAccountVo = this.memAccountService.getById(buyerShowVo.getMemberId());
				if(memAccountVo != null){
					buyerShowVo.setWxHeadPhoto(memAccountVo.getWxHeadPhoto());
					buyerShowVo.setWxNickName(memAccountVo.getWxNickName());
				}
			}
			//获取当前人点赞情况
			BuyerShowPraiseSearch search = new BuyerShowPraiseSearch();
			search.setBuyershowId(buyerShowId);
			search.setOperater(operater);
			List<BuyerShowPraiseVo> praiseInfo = this.buyerShowPraiseService.getList(search);
			if(praiseInfo != null && praiseInfo.size() > 0 ){
				buyerShowVo.setIsPraise(praiseInfo.get(0).getIsPraise());
			}
			result.setOK(ResultCode.CODE_STATE_200, "", buyerShowVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "操作失败");
		}
		return result;
	}
	
	/**
	 * 点赞/取消点赞
	 * @param buyerShowPraiseVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/praiseBuyerShow", method = RequestMethod.POST)
	public Result praiseBuyerShow(BuyerShowPraiseVo buyerShowPraiseVo) {
		Result result = new Result();
		Boolean flag = false;
		try{
			if(buyerShowPraiseVo.getBuyershowId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取买家秀信息失败");
				return result;
			}
			if(StringUtil.isEmpty(buyerShowPraiseVo.getOperater())){
				result.setError(ResultCode.CODE_STATE_4006, "获取操作人信息失败");
				return result;
			}
			if(buyerShowPraiseVo.getIsPraise() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取状态失败");
				return result;
			}
			BuyerShowPraiseSearch search = new BuyerShowPraiseSearch();
			search.setBuyershowId(buyerShowPraiseVo.getBuyershowId());
			search.setOperater(buyerShowPraiseVo.getOperater());
			List<BuyerShowPraiseVo> praiseInfo = this.buyerShowPraiseService.getList(search);
			if(praiseInfo != null && praiseInfo.size() > 0 ){//已经有记录
				BuyerShowPraiseVo existPraise = praiseInfo.get(0);
				existPraise.setIsPraise(buyerShowPraiseVo.getIsPraise());;
				flag = this.buyerShowPraiseService.edit(existPraise);
			}else{//没有记录
				buyerShowPraiseVo.setCreateTime(new Date());
				buyerShowPraiseVo.setIsPraise(true);
				flag = this.buyerShowPraiseService.add(buyerShowPraiseVo);
			}
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "操作失败");
		}
		return result;
	}
	
	/************************************* 好友助力充电领取商务四件套活动  开始  ***************************************************/
	/**
	 * 获取活动参与人情况
	 * @param wxShareSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getParticipantInfo", method = RequestMethod.GET)
	public Result getParticipantInfo(WxShareSearch wxShareSearch) {
		Result result = new Result();
		Map<String, Object> dateMap = new HashMap<String, Object>();
		try{
			if(StringUtil.isEmpty(wxShareSearch.getChannel())){
				result.setError(ResultCode.CODE_STATE_4006, "活动渠道不能为空");
				return result;
			}
			if(StringUtil.isEmpty(wxShareSearch.getWxOpenId())){
				result.setError(ResultCode.CODE_STATE_4006, "当前人wxOpernId不能为空");
				return result;
			}
			wxShareSearch.setInviterWxOpenId("join");
			//获取当前人参与活动信息
			WxShareVo sharer = null;      //分享人信息
			List<WxShareVo> list = this.wxShareService.getList(wxShareSearch);
			if(list != null && list.size() > 0){
				sharer = list.get(0);
			}
			//获取当前人的助力情况
			WxShareSearch search =  new WxShareSearch();
			search.setInviterWxOpenId(wxShareSearch.getWxOpenId());
			search.setChannel(wxShareSearch.getChannel());
			search.setPage(wxShareSearch.getPage());
			search.setRows(wxShareSearch.getRows());
			Page<WxShareVo> page = this.wxShareService.getPage(search);
			dateMap.put("shareList", page);
			dateMap.put("sharer", sharer);
			result.setOK(ResultCode.CODE_STATE_200, "", dateMap);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 参加活动
	 * @param wxShareVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/joinActivity", method = RequestMethod.POST)
	public Result joinActivity(WxShareVo wxShareVo) {
		Result result = new Result();
		try{
			//信息校验
			if(StringUtil.isEmpty(wxShareVo.getChannel())){
				result.setError(ResultCode.CODE_STATE_4006, "获取不到活动渠道");
				return result;
			}
			if(StringUtil.isEmpty(wxShareVo.getWxOpenId())){
				result.setError(ResultCode.CODE_STATE_4006, "获取不到wxOpernId");
				return result;
			}
			if(StringUtil.isEmpty(wxShareVo.getWxUnionId())){
				result.setError(ResultCode.CODE_STATE_4006, "获取不到wxUnionId");
				return result;
			}
			if(StringUtil.isEmpty(wxShareVo.getWxHeadPhoto())){
				result.setError(ResultCode.CODE_STATE_4006, "获取不到微信头像");
				return result;
			}
			WxShareSearch wxShareSearch = new WxShareSearch();
			wxShareSearch.setChannel(wxShareVo.getChannel());
			wxShareSearch.setWxOpenId(wxShareVo.getWxOpenId());
			wxShareSearch.setInviterWxOpenId("join");
			List<WxShareVo> list = this.wxShareService.getList(wxShareSearch);
			Boolean flag = true;
			if(list == null || list.size() <= 0){
				Date curDate = new Date();
				wxShareVo.setAssistValue(200);
				wxShareVo.setCreateTime(curDate);
				wxShareVo.setRemark(DateUtil.format(curDate)+"参加活动渠道为["+wxShareVo.getChannel()+"]的活动");
				flag = this.wxShareService.add(wxShareVo);
			}else{
				wxShareVo = list.get(0);
			}
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "", wxShareVo);
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "分享失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 兑换活动奖品
	 * @param giftSendVo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/exchangeActGift", method = RequestMethod.POST)
	public Result exchangeActGift(GiftSendVo giftSendVo){
		Result result = new Result();
		try{
			result = this.giftSendService.exchangeActGift(giftSendVo);
		}catch(Exception e){
			result.setError(ResultCode.CODE_STATE_500, e.getMessage());
		}
		return result;
	}
	
	/**
	 * 获取所有礼品列表
	 * @param giftSendDetailSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getGiftSendList", method = RequestMethod.GET)
	public Result getGiftSendList(GiftSendDetailSearch giftSendDetailSearch) {
		Result result = new Result();
		try{
			Page<GiftSendDetailVo> page = this.giftSendDetailService.getPage(giftSendDetailSearch);
			for(GiftSendDetailVo GiftSendDetailVo : page.getRowsObject()){
				GiftSendDetailVo.setConsigneePhone(GiftSendDetailVo.getConsigneePhone().substring(0, 6)+"****");
			}
			result.setOK(ResultCode.CODE_STATE_200, "", page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/************************************* 好友助力充电领取商务四件套活动  结束  ***************************************************/
	@ResponseBody
	@RequestMapping(value = "/visitor/test", method = RequestMethod.GET)
 	public Result test() {
		Result result = new Result();
		try{
			List<WxShareVo> list = this.wxShareService.getList(null);
			result.setOK(ResultCode.CODE_STATE_200, "",list);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	@ResponseBody
    @RequestMapping(value = "/visitor/getAddress", method = RequestMethod.GET)
    public Result getAddress(String latitude, String longitude, String key){
    	Result result = new Result();
    	if(StringUtil.isEmpty(latitude) || StringUtil.isEmpty(longitude) || StringUtil.isEmpty(key)){
    		result.setError(ResultCode.CODE_STATE_4006, "参数不全");
			return result;
    	}
    	String urlNameString = "http://apis.map.qq.com/ws/geocoder/v1/?location="+latitude+","+longitude+"&key="+key;
		System.out.println(urlNameString);
    	try{
			// 根据地址获取请求
            HttpGet request = new HttpGet(urlNameString);//这里发送get请求
            // 获取当前客户端对象
            DefaultHttpClient httpClient = new DefaultHttpClient();
            // 通过请求对象获取响应对象
            HttpResponse response = httpClient.execute(request);
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result.setOK(ResultCode.CODE_STATE_200, "获取成功",EntityUtils.toString(response.getEntity(),"utf-8"));
            } 
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_4006, "获取二维码失败");
		}
    	return result;
    }
	
	/**
	 * 获取所有位置位置信息
	 * @param regionSearch
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/visitor/getAllLocation2", method = RequestMethod.GET)
	public List<Map<String, Object>> getAllLocation2(RegionSearch regionSearch) {
		List<Map<String, Object>> proviceData = new ArrayList<Map<String, Object>>();
		try{
			regionSearch.setRegionLevel("1");
			List<RegionVo> provinceList = this.regionService.getList(regionSearch);
			for(RegionVo province: provinceList ){//循环：省份
				Map<String, Object> provinceObj = new HashMap<String, Object>();
				provinceObj.put("value", province.getRegionId());
				provinceObj.put("label", province.getRegionName());
				provinceObj.put("code", province.getRegionCode());
				Integer provinceId = province.getRegionId();
				RegionSearch citySearch = new RegionSearch();
				citySearch.setParentId(provinceId);
				List<RegionVo> cityList = this.regionService.getList(citySearch);
				List<Map<String, Object>> cityData = new ArrayList<Map<String, Object>>();
				for(RegionVo city: cityList){//循环：城市
					Map<String, Object> cityObj = new HashMap<String, Object>();
					cityObj.put("value", city.getRegionId());
					cityObj.put("label", city.getRegionName());
					cityObj.put("code", city.getRegionCode());
					//cityObj.put("children", "");
					Integer cityId = city.getRegionId();
					RegionSearch areaSearch = new RegionSearch();
					areaSearch.setParentId(cityId);
					List<RegionVo> areaList = this.regionService.getList(areaSearch);
					List<Map<String, Object>> areaData = new ArrayList<Map<String, Object>>();
					for(RegionVo area: areaList){//循环：区域
						Map<String, Object> areaObj = new HashMap<String, Object>();
						areaObj.put("value", area.getRegionId());
						areaObj.put("label", area.getRegionName());
						areaObj.put("code", area.getRegionCode());
						areaData.add(areaObj);
					}
					if(areaData.size()>0){
						cityObj.put("children", areaData);
					}
					cityData.add(cityObj);
				}
				if(cityData.size()>0){
					provinceObj.put("children", cityData);
				}
				proviceData.add(provinceObj);
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return proviceData;
	}
	
	/************************************* 微信端产品意向登记  结束  ***************************************************/
	/**
	 * 根据产品类型统计访问数
	 * @param productType
	 */
	@RequestMapping(value = "/visitor/accessCount", method = RequestMethod.POST)
	@ResponseBody
	public void accessCount(Integer productType) {
		if(productType != null){
			if(1 == productType.intValue()){//安全门窗
				this.productIntentService.accessCountDW();
			}
			if(2 == productType.intValue()){//健康厨房
				this.productIntentService.accessCountHK();
			}
		}
		
	}
	
	/**
	 * 添加产品意向
	 * @param productIntentVo
	 * @return
	 */
	@RequestMapping(value = "/visitor/addProductIntent", method = RequestMethod.POST)
	@ResponseBody
	public Result addProductIntent(ProductIntentVo productIntentVo) {
		Result result = new Result();
		//一、信息校验
		if(productIntentVo.getProductType() == null){
			result.setError(ResultCode.CODE_STATE_4006, "产品类型不能为空！"); return result;
		}
		if(StringUtil.isEmpty(productIntentVo.getName())){
			result.setError(ResultCode.CODE_STATE_4006, "请输入您的姓名！"); return result;
		}
		if(StringUtil.isEmpty(productIntentVo.getMobilePhone())){
			result.setError(ResultCode.CODE_STATE_4006, "请输入您的手机号码！"); return result;
		}
		if(!VerificationUtils.isValid(productIntentVo.getMobilePhone(), VerificationUtils.MOBILE)){
			result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码！");
			return result;
		}
		ProductIntentSearch search = new ProductIntentSearch();
		search.setMobilePhoneLike(productIntentVo.getMobilePhone());
		search.setProductType(productIntentVo.getProductType());
		search.setState(0);
		List<ProductIntentVo> list = this.productIntentService.getList(search);
		if(list != null && list.size() > 0){
			result.setError(ResultCode.CODE_STATE_4006, "您已经登记过了，请静候客服人员对您的电话回访，感谢您对本公司产品的认可！");
			return result;
		}
		//信息有效
		productIntentVo.setCreateTime(new Date());
		Boolean flag = this.productIntentService.add(productIntentVo);
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "登记成功，请静候客服人员对您的电话回访，感谢您对本公司产品的认可！",productIntentVo);
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "登记失败，请您稍后再试，或者联系客服人员！");
		}
		return result;
	}
	/************************************* 微信端产品意向登记  结束  ***************************************************/

}
