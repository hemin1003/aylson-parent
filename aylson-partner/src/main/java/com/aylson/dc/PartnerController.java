package com.aylson.dc;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.frame.base.Page;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.base.PartnerGeneralConstant.CouponApplyState;
import com.aylson.dc.base.PartnerGeneralConstant.FeedBackState;
import com.aylson.dc.base.PartnerGeneralConstant.MemberType;
import com.aylson.dc.base.cache.CacheEntity;
import com.aylson.dc.base.cache.CacheTimerHandler;
import com.aylson.dc.mem.FeedBackReplyService;
import com.aylson.dc.mem.FeedBackService;
import com.aylson.dc.mem.search.FeedBackReplySearch;
import com.aylson.dc.mem.search.FeedBackSearch;
import com.aylson.dc.mem.vo.FeedBackReplyVo;
import com.aylson.dc.mem.vo.FeedBackVo;
import com.aylson.dc.owner.search.OrderSearch;
import com.aylson.dc.owner.service.OrderService;
import com.aylson.dc.owner.service.QuotationService;
import com.aylson.dc.owner.vo.OrderVo;
import com.aylson.dc.owner.vo.QuotationVo;
import com.aylson.dc.partner.search.CouponApplySearch;
import com.aylson.dc.partner.search.CouponConfigSearch;
import com.aylson.dc.partner.search.CouponSearch;
import com.aylson.dc.partner.search.PartnerAccountSearch;
import com.aylson.dc.partner.search.PartnerWalletBillSearch;
import com.aylson.dc.partner.service.CouponApplyService;
import com.aylson.dc.partner.service.CouponConfigService;
import com.aylson.dc.partner.service.CouponService;
import com.aylson.dc.partner.service.PartnerAccountService;
import com.aylson.dc.partner.service.PartnerWalletBillService;
import com.aylson.dc.partner.vo.CouponApplyVo;
import com.aylson.dc.partner.vo.CouponConfigVo;
import com.aylson.dc.partner.vo.CouponVo;
import com.aylson.dc.partner.vo.PartnerAccountVo;
import com.aylson.dc.partner.vo.PartnerWalletBillVo;
import com.aylson.dc.sys.common.SystemConfig;
import com.aylson.dc.sys.search.NoticeReadSearch;
import com.aylson.dc.sys.search.NoticeSearch;
import com.aylson.dc.sys.search.RegionSearch;
import com.aylson.dc.sys.service.NoticeReadService;
import com.aylson.dc.sys.service.NoticeService;
import com.aylson.dc.sys.service.RegionService;
import com.aylson.dc.sys.vo.NoticeReadVo;
import com.aylson.dc.sys.vo.NoticeVo;
import com.aylson.utils.MD5Encoder;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;
import com.fastweixin.api.OauthAPI;
import com.fastweixin.api.UserAPI;
import com.fastweixin.api.config.ApiConfig;
import com.fastweixin.api.response.GetUserInfoResponse;
import com.fastweixin.api.response.OauthGetTokenResponse;

/**
 * 艾臣合伙人客户端接口控制器
 * 说明：
 * 	  1、【艾臣合伙人客户端】接口统一入口，便于管理跟踪
 *    2、控制层尽量只负责控制跳转，业务逻辑在相关服务层实现
 *    3、/visitor/*  为游客接口，不拦截
 * @author wwx
 * @since  2017-01
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/partner")
public class PartnerController extends BaseController {
	
	
	//服务类
	@Autowired
	private RegionService regionService;                                //系统-区域服务
	@Autowired
	private PartnerAccountService partnerAccountService;                //合伙人账号服务
	@Autowired
	private CouponConfigService couponConfigService;                    //优惠券配置服务
	@Autowired
	private CouponApplyService couponApplyService;                      //优惠券申请服务
	@Autowired
	private CouponService couponService;                                //优惠券服务
	@Autowired
	private NoticeService noticeService;                                //公告服务
	@Autowired
	private NoticeReadService noticeReadService;                        //公告阅读服务
	@Autowired
	private FeedBackService feedBackService;                            //反馈管理服务
	@Autowired
	private FeedBackReplyService feedBackReplyService;                  //反馈回复管理服务
	@Autowired
	private OrderService orderService;                                  //订单服务
	@Autowired
	private PartnerWalletBillService partnerWalletBillService;          //钱包流水明细服务
	@Autowired
	private QuotationService quotationService;                          //报价服务
	
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
	 * 注册
	 * @param partnerAccountVo
	 * @return
	 */
	@RequestMapping(value = "/visitor/register", method = RequestMethod.POST)
	@ResponseBody
	public Result register(PartnerAccountVo partnerAccountVo) {
		Result result = null;
		try{
			result = this.partnerAccountService.register(partnerAccountVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试");
		}
		return result;
	}
	
	/**
	 * 登录
	 * @param partnerAccountVo
	 * @return
	 */
	@RequestMapping(value = "/visitor/login", method = RequestMethod.POST)
	@ResponseBody
	public Result login(PartnerAccountVo partnerAccountVo) {
		Result result = null;
		try{
			result = this.partnerAccountService.login(partnerAccountVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 找回密码
	 * @param partnerAccountVo
	 * @return
	 */
	@RequestMapping(value = "/visitor/findPassword", method = RequestMethod.GET)
	@ResponseBody
	public Result findPassword(String mobilePhone) {
		Result result = new Result();
		try{
			if(StringUtil.isEmpty(mobilePhone)){
				result.setError(ResultCode.CODE_STATE_4006, "手机号码不能为空");
				return result;
			}
			if(!VerificationUtils.isValid(mobilePhone, VerificationUtils.MOBILE)){
				result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
				return result;
			}
			PartnerAccountSearch search = new PartnerAccountSearch();
			search.setMobilePhoneLike(mobilePhone);
			List<PartnerAccountVo> list = this.partnerAccountService.getList(search);
			if(list != null && list.size() > 0){
				//信息有效，发送验证码
				result = this.getPhoneVerifyCode(mobilePhone);
				if(result.isSuccess()){
					result.setOK(ResultCode.CODE_STATE_200, "信息有效，验证码已经发出");
				}
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "未找到该手机号的账号信息");
				return result;
			}
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 找回密码
	 * @param partnerAccountVo
	 * @return
	 */
	@RequestMapping(value = "/visitor/findPasswordNext", method = RequestMethod.GET)
	@ResponseBody
	public Result findPasswordNext(String mobilePhone, String validCode, String newPwd) {
		Result result = new Result();
		try{
			if(StringUtil.isEmpty(mobilePhone)){
				result.setError(ResultCode.CODE_STATE_4006, "手机号码不能为空");
				return result;
			}
			if(!VerificationUtils.isValid(mobilePhone, VerificationUtils.MOBILE)){
				result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
				return result;
			}
			if(StringUtil.isEmpty(newPwd)){
				result.setError(ResultCode.CODE_STATE_4006, "新密码不能为空");
				return result;
			}
			if(StringUtil.isEmpty(validCode)){
				result.setError(ResultCode.CODE_STATE_4006, "手机验证码不能为空");
				return result;
			}
			CacheEntity cacheEntity = CacheTimerHandler.getCache(mobilePhone);
		    String validCodeSys = "";
		    if (cacheEntity != null) {
				validCodeSys =  cacheEntity.getCacheContext()+"";//发送的验证码
			}
			if(StringUtil.isEmpty(validCodeSys) || !validCodeSys.equals(validCode)){
				result.setError(ResultCode.CODE_STATE_4006, "验证码有误！");
				return result;
			}
			Boolean flag = false;
			PartnerAccountSearch search = new PartnerAccountSearch();
			search.setMobilePhoneLike(mobilePhone);
			List<PartnerAccountVo> list = this.partnerAccountService.getList(search);
			if(list != null && list.size() > 0){
				//信息有效，保存新密码
				PartnerAccountVo pa = new PartnerAccountVo();
				pa.setId(list.get(0).getId());
				pa.setPwd(MD5Encoder.encodeByMD5(newPwd));
				flag = this.partnerAccountService.edit(pa);
			}
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "修改成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "操作失败，请稍后再试或联系工作人员");
			}
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
			result = this.partnerAccountService.getMyPersonalInfo(clientId);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 修改个人信息
	 * @return
	 */
	@RequestMapping(value = "/modifyPersonalInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result modifyPersonalInfo(PartnerAccountVo partnerAccountVo) {
		Result result = new Result();
		try{
			result = this.partnerAccountService.modifyPersonalInfo(partnerAccountVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 首页账号统计数据
	 * @return
	 */
	@RequestMapping(value = "/getSumResult", method = RequestMethod.GET)
	@ResponseBody
	public Result getSumResult() {
		Result result = new Result();
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			result = this.partnerAccountService.getSumResult(Integer.parseInt(clientId));
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 修改密码
	 * @param partnerAccountVo
	 * @return
	 */
	@RequestMapping(value = "/modifyPwd", method = RequestMethod.POST)
	@ResponseBody
	public Result modifyPwd(PartnerAccountVo partnerAccountVo) {
		Result result = new Result();
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			partnerAccountVo.setId(Integer.parseInt(clientId));
			result = this.partnerAccountService.modifyPwd(partnerAccountVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 获取已上架优惠券配置列表
	 * @param couponConfigSearch
	 * @return
	 */
	@RequestMapping(value = "/getCouponConfig", method = RequestMethod.GET)
	@ResponseBody
	public Result getCouponConfig(CouponConfigSearch couponConfigSearch) {
		Result result = new Result();
		try{
			couponConfigSearch.setState(1);//已上架
			List<CouponConfigVo> list = this.couponConfigService.getList(couponConfigSearch);
			result.setOK(ResultCode.CODE_STATE_200, "",list);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 保存优惠券申请信息
	 * @param couponApplyVo
	 * @return
	 */
	@RequestMapping(value = "/applyCoupon", method = RequestMethod.POST)
	@ResponseBody
	public Result applyCoupon(CouponApplyVo couponApplyVo) {
		Result result = null;
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result = new Result();
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			couponApplyVo.setApplierId(Integer.parseInt(clientId));
			result = this.couponApplyService.applyCoupon(couponApplyVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 获取现金券列表
	 * @param couponApplySearch
	 * @return
	 */
	@RequestMapping(value = "/getCouponApplyList", method = RequestMethod.GET)
	@ResponseBody
	public Result getCouponApplyList(CouponApplySearch couponApplySearch) {
		Result result = new Result();
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			if(couponApplySearch.getState() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取状态失败");
				return result;
			}
			if(couponApplySearch.getState().intValue() != CouponApplyState.APPLING
					&& couponApplySearch.getState().intValue() != CouponApplyState.PASS
						&& couponApplySearch.getState().intValue() != CouponApplyState.NOTPASS){
				result.setError(ResultCode.CODE_STATE_4006, "未定义状态");
				return result;
			}
			couponApplySearch.setApplierId(Integer.parseInt(clientId));
			Page<CouponApplyVo> page = this.couponApplyService.getPage(couponApplySearch);
			result.setOK(ResultCode.CODE_STATE_200, "查询成功",page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 获取优惠券列表
	 * @param couponSearch
	 * @return
	 */
	@RequestMapping(value = "/getCouponList", method = RequestMethod.GET)
	@ResponseBody
	public Result getCouponList(CouponSearch couponSearch) {
		Result result = new Result();
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			if(couponSearch.getApplyId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取优惠券申请id失败");
				return result;
			}
			Page<CouponVo> page = this.couponService.getPage(couponSearch);
			result.setOK(ResultCode.CODE_STATE_200, "查询成功",page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 获取公告列表
	 * @param noticeSearch
	 * @return
	 */
	@RequestMapping(value = "/getNoticeList", method = RequestMethod.GET)
	@ResponseBody
	public Result getNoticeList(NoticeSearch noticeSearch) {
		Result result = new Result();
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			noticeSearch.setReaderId(Integer.parseInt(clientId));
			noticeSearch.setState(1);//已发布
			Page<NoticeVo> page = this.noticeService.getPage(noticeSearch);
			result.setOK(ResultCode.CODE_STATE_200, "查询成功",page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 获取公告详情
	 * @param noticeId
	 * @return
	 */
	@RequestMapping(value = "/getNoticeDetail", method = RequestMethod.GET)
	@ResponseBody
	public Result getNoticeDetail(Integer noticeId) {
		Result result = new Result();
		try{
			if(noticeId == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取公告信息失败");
				return result;
			}
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			//判断是否已读
			NoticeReadSearch search = new NoticeReadSearch();
			search.setNoticeId(noticeId);
			search.setReaderId(Integer.parseInt(clientId));
			long rows = this.noticeReadService.getRowCount(search);
			if(rows == 0L){
				NoticeReadVo noticeReadVo = new NoticeReadVo();
				noticeReadVo.setNoticeId(noticeId);
				noticeReadVo.setReaderId(Integer.parseInt(clientId));
				noticeReadVo.setReadTime(new Date());
				this.noticeReadService.add(noticeReadVo);
			}
			NoticeVo noticeVo = this.noticeService.getById(noticeId);
			if(noticeVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取不到对应的公告信息");
				return result;
			}
			result.setOK(ResultCode.CODE_STATE_200, "查询成功",noticeVo);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 获取我的客户
	 * @param noticeId
	 * @return
	 */
	@RequestMapping(value = "/getClientList", method = RequestMethod.GET)
	@ResponseBody
	public Result getClientList(CouponApplySearch couponApplySearch) {
		Result result = new Result();
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			couponApplySearch.setApplierId(Integer.parseInt(clientId));
			couponApplySearch.setState(CouponApplyState.PASS);
			couponApplySearch.setIsClient(true);
			Page<CouponApplyVo> page = this.couponApplyService.getPage(couponApplySearch);
			result.setOK(ResultCode.CODE_STATE_200, "查询成功",page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 获取客户订单
	 * @param orderSearch
	 * @return
	 */
	@RequestMapping(value = "/getClientOrderList", method = RequestMethod.GET)
	@ResponseBody
	public Result getClientOrderList(OrderSearch orderSearch) {
		Result result = new Result();
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			if(StringUtil.isEmpty(orderSearch.getMobilePhone())){
				result.setError(ResultCode.CODE_STATE_4006, "获取客户手机信息失败");
				return result;
			}
			orderSearch.setPartnerId(Integer.parseInt(clientId));
			Page<OrderVo> page = this.orderService.getPage(orderSearch);
			result.setOK(ResultCode.CODE_STATE_200, "查询成功",page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 获取我的客户的优惠券情况
	 * @param couponSearch
	 * @return
	 */
	@RequestMapping(value = "/getClientCouponList", method = RequestMethod.GET)
	@ResponseBody
	public Result getClientCouponList(CouponSearch couponSearch) {
		Result result = new Result();
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			if(StringUtil.isEmpty(couponSearch.getOwnerPhone())){
				result.setError(ResultCode.CODE_STATE_4006, "获取客户手机信息失败");
				return result;
			}
			couponSearch.setIsClientCoupon(true);
			couponSearch.setApplierId(Integer.parseInt(clientId));
			Page<CouponVo> page = this.couponService.getPage(couponSearch);
			result.setOK(ResultCode.CODE_STATE_200, "查询成功",page);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 获取我的申述列表
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
			feedBackSearch.setFeedbackerType(MemberType.PARTNER);
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
	 * 获取申诉详情
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
	 * 获取我的申诉回复详情
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
	 * 添加申诉
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
			feedBackVo.setFeedbackerType(MemberType.PARTNER);
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
	
	/**
	 * 获取分红列表
	 * @param partnerWalletBillSearch
	 * @return
	 */
	@RequestMapping(value = "/getBonusList", method = RequestMethod.GET)
	@ResponseBody
	public Result getBonusList(Integer page, Integer rows){
		Result result = new Result();
		Map<String, Object> data = new HashMap<String, Object>();
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前登录人信息失败");
				return result;
			}
			PartnerWalletBillSearch partnerWalletBillSearch = new PartnerWalletBillSearch();
			partnerWalletBillSearch.setAccountId(Integer.parseInt(clientId));
			partnerWalletBillSearch.setSourceType(2);//订单
			partnerWalletBillSearch.setType(1);//奖励分红
			if(page != null){partnerWalletBillSearch.setPage(page);}
			if(rows != null){partnerWalletBillSearch.setRows(rows);}
			Page<PartnerWalletBillVo> billDetail = this.partnerWalletBillService.getPage(partnerWalletBillSearch);
			data.put("billDetail", billDetail);
			Float billTotal = this.partnerWalletBillService.getBillSum(partnerWalletBillSearch);
			if(billTotal == null) billTotal = 0.0f;
			data.put("billTotal", billTotal);
			result.setOK(ResultCode.CODE_STATE_200, "查询成功",data);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取已得分红列表
	 * @param partnerWalletBillSearch
	 * @return
	 */
	@RequestMapping(value = "/getBonusHadList", method = RequestMethod.GET)
	@ResponseBody
	public Result getBonusHadList(Integer page, Integer rows){
		Result result = new Result();
		Map<String, Object> data = new HashMap<String, Object>();
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前登录人信息失败");
				return result;
			}
			PartnerWalletBillSearch partnerWalletBillSearch = new PartnerWalletBillSearch();
			partnerWalletBillSearch.setAccountId(Integer.parseInt(clientId));
			partnerWalletBillSearch.setType(-1);
			if(page != null){partnerWalletBillSearch.setPage(page);}
			if(rows != null){partnerWalletBillSearch.setRows(rows);}
			Page<PartnerWalletBillVo> billDetail = this.partnerWalletBillService.getPage(partnerWalletBillSearch);
			data.put("billDetail", billDetail);
			Float billTotal = this.partnerWalletBillService.getBillSum(partnerWalletBillSearch);
			if(billTotal == null) billTotal = 0.0f;
			data.put("billTotal", billTotal);
			result.setOK(ResultCode.CODE_STATE_200, "查询成功",data);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 获取分红明细
	 * @param partnerWalletBillSearch
	 * @return
	 */
	@RequestMapping(value = "/getBonusDetail", method = RequestMethod.GET)
	@ResponseBody
	public Result getBonusDetail(Integer billId){
		Result result = new Result();
		Map<String, Object> data = new HashMap<String, Object>();
		PartnerWalletBillVo partnerWalletBillVo = null;
		OrderVo orderVo = null;
		List<CouponVo> couponList = null;
		//QuotationVo quotationVo = null;
		try{
			if(billId == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取分红信息失败");
				return result;
			}
			partnerWalletBillVo = this.partnerWalletBillService.getById(billId);
			if(partnerWalletBillVo == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取分红信息失败");
				return result;
			}
			data.put("partnerWalletBillVo", partnerWalletBillVo);
			if(2 == partnerWalletBillVo.getSourceType().intValue()){
				orderVo = this.orderService.getById(partnerWalletBillVo.getSourceId());
				CouponSearch couponSearch = new CouponSearch();
				if(orderVo.getSourceType() != null && orderVo.getSourceType().intValue() == 1){//
					couponSearch.setOrderId(orderVo.getAppointId());
					couponSearch.setOrderCode(orderVo.getAppointNo());
				}
				if(orderVo.getSourceType() != null && orderVo.getSourceType().intValue() == 2){//
					couponSearch.setOrderId(orderVo.getId());
					couponSearch.setOrderCode(orderVo.getOrderNo());
				}
				couponList = this.couponService.getList(couponSearch);
			}
			data.put("orderVo", orderVo);
//			if(orderVo.getSourceType() != null && orderVo.getSourceType().intValue() == 1){
//				quotationVo = this.quotationService.getQuotationVo(orderVo.getDesignId(), orderVo.getDesignType());
//			}
//			data.put("quotationVo", quotationVo);
			data.put("couponList", couponList);
			result.setOK(ResultCode.CODE_STATE_200, "查询成功",data);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
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
    	PartnerAccountVo partnerAccountVo = null;
    	try{
    		//获取微信用户信息
        	OauthAPI oauthAPI = new OauthAPI( this.getWxApiConfig());
            OauthGetTokenResponse response = oauthAPI.getToken(code);
            if(response!=null && response.getOpenid()!=null){
            	UserAPI userAPI = new UserAPI( this.getWxApiConfig());
                GetUserInfoResponse userInfo = userAPI.getUserInfo(response.getOpenid());
                if(StringUtil.isNotEmpty(response.getOpenid())){
                	PartnerAccountSearch partnerAccountSearch = new PartnerAccountSearch();
                	partnerAccountSearch.setWxOpenId(response.getOpenid());
            		List<PartnerAccountVo> list = this.partnerAccountService.getList(partnerAccountSearch);
            		if(list != null && list.size() > 0){
            			partnerAccountVo = list.get(0);
            		}else{
            			partnerAccountVo = new PartnerAccountVo();
            			partnerAccountVo.setWxHeadPhoto(userInfo.getHeadimgurl());
            			partnerAccountVo.setWxOpenId(userInfo.getOpenid());
            			partnerAccountVo.setWxUnionId(userInfo.getUnionid());
            			partnerAccountVo.setWxNickName(userInfo.getNickname());
            		}
                }
            }
	    	result.setOK(ResultCode.CODE_STATE_200, "", partnerAccountVo);
    	}catch(Exception e){
    		e.printStackTrace();
    		result.setError(ResultCode.CODE_STATE_4008, "系统繁忙，请稍候重试");
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
	 * 获取手机验证码
	 * @param phone
	 * @return
	 * @throws Exception
	 */
    public  Result getPhoneVerifyCode(String phone) throws Exception {
    	//返送前校验信息
        Result result = new Result();
        int sendQuantity = 0;
        // 生成验证码
        int validCode = (int) ((Math.random() * 9 + 1) * 1000);
        System.out.println(phone+"生成的验证码为："+validCode);
        // 判断缓存中的验证码是否已经存在和过期
        CacheEntity cacheEntity = CacheTimerHandler.getCache(phone);
        if (cacheEntity != null) {
            validCode = (int) cacheEntity.getCacheContext();
            long interval = cacheEntity.getTimeoutStamp() - System.currentTimeMillis();
            // 一分钟内不会重新发送验证码，超过一分钟才生成新的验证码，也不会重复发送验证码
            if (interval >= (cacheEntity.getValidityTime() - 60) * 1000) {
                result.setError(ResultCode.CODE_STATE_4006, "操作太频繁，请稍后重试");
                return result;
            }
        }
        // 校验已发送条数
        CacheEntity cEntity = CacheTimerHandler.getCache("sendQuantity_" + phone);
        if(cEntity != null){
        	sendQuantity = (int) cEntity.getCacheContext();
        	if(sendQuantity>10){
        		result.setError(ResultCode.CODE_STATE_4006, "您的手机短信验证码发送条数已超过限制条数， 同一个手机号码24小时之内只能发送10条验证码");
                return result;
        	}
        }
        sendQuantity = sendQuantity + 1;
        // 调试模式
        if (SystemConfig.isDebugMode()) {
            cacheEntity = new CacheEntity(phone, validCode);
            CacheTimerHandler.addCache(phone, cacheEntity);
            result.setOK(ResultCode.CODE_STATE_200, "", validCode);
            return result;
        }
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(SystemConfig.getConfigValueByKey("ihuiyi_sms_host"));
        client.getParams().setContentCharset("UTF-8");
        method.setRequestHeader("ContentType",
                "application/x-www-form-urlencoded;charset=UTF-8");
        String content = "您的验证码是：" + validCode;
        NameValuePair[] data = {
                new NameValuePair("account", SystemConfig.getConfigValueByKey("ihuiyi_username")),
                new NameValuePair("password", SystemConfig.getConfigValueByKey("ihuiyi_password")), // 密码可以使用明文密码或使用32位MD5加密
                new NameValuePair("mobile", phone),
                new NameValuePair("content", content),};
        method.setRequestBody(data);
        try {
            client.executeMethod(method);
            String submitResult = method.getResponseBodyAsString();
            Document doc = DocumentHelper.parseText(submitResult);
            Element root = doc.getRootElement();
            String returnCode = root.elementText("code");
            String msg = root.elementText("msg");
            //String smsid = root.elementText("smsid");
            // 短信提交成功
            if ("2".equals(returnCode)) {
                System.out.println(phone+"=====================>>验证码已成功发生：" + validCode);
                cacheEntity = new CacheEntity(phone, validCode);
                CacheTimerHandler.addCache(phone, cacheEntity);
                cEntity = new CacheEntity("sendQuantity_" + phone, sendQuantity, 86400);  //一天24小时等于86400秒
                CacheTimerHandler.addCache("sendQuantity_" + phone, cEntity, 86400);
                result.setOK(ResultCode.CODE_STATE_200, "验证码已成功发送");
            } else {
                result.setError(ResultCode.CODE_STATE_4006, "验证码发送失败:" + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(ResultCode.CODE_STATE_4008, "系统繁忙，请稍候重试");
        }
        return result;
    }
	
    /**
	 * 发送优惠券号给客户
	 * @return
	 */
	@RequestMapping(value = "/sentCouponInfo", method = RequestMethod.POST)
	@ResponseBody
	public Result sentCouponInfo(String couponCodes) {
		Result result = new Result();
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			if(StringUtil.isEmpty(couponCodes)){
				result.setError(ResultCode.CODE_STATE_4006, "至少选择一个优惠券");
				return result;
			}
			PartnerAccountVo pa = this.partnerAccountService.getById(Integer.parseInt(clientId));
			if(pa == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			String content = "您申请的优惠券号如下："+couponCodes;
			HttpClient client = new HttpClient();
	        PostMethod method = new PostMethod(SystemConfig.getConfigValueByKey("ihuiyi_sms_host"));
	        client.getParams().setContentCharset("UTF-8");
	        method.setRequestHeader("ContentType",
	                "application/x-www-form-urlencoded;charset=UTF-8");
	        NameValuePair[] data = {
	                new NameValuePair("account", SystemConfig.getConfigValueByKey("ihuiyi_username")),
	                new NameValuePair("password", SystemConfig.getConfigValueByKey("ihuiyi_password")), // 密码可以使用明文密码或使用32位MD5加密
	                new NameValuePair("mobile", pa.getMobilePhone()),
	                new NameValuePair("content", content),};
	        method.setRequestBody(data);
	        try {
	            client.executeMethod(method);
	            String submitResult = method.getResponseBodyAsString();
	            Document doc = DocumentHelper.parseText(submitResult);
	            Element root = doc.getRootElement();
	            String returnCode = root.elementText("code");
	            String msg = root.elementText("msg");
	            //String smsid = root.elementText("smsid");
	            // 短信提交成功
	            if ("2".equals(returnCode)) {
	                result.setOK(ResultCode.CODE_STATE_200, "已成功发送");
	            } else {
	                result.setError(ResultCode.CODE_STATE_4006, "发送失败:" +msg);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            result.setError(ResultCode.CODE_STATE_4008, "系统繁忙，请稍候重试");
	        }
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 个人中心统计情况
	 * @return
	 */
	@RequestMapping(value = "/getPCSummaryInfo", method = RequestMethod.GET)
	@ResponseBody
	public Result getPCSummaryInfo(){
		Result result = new Result();
		try{
			String clientId = request.getParameter("clientId");
			if(StringUtil.isEmpty(clientId)){
				result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
				return result;
			}
			result = this.partnerAccountService.getPCSummaryInfo(Integer.parseInt(clientId));
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍候再试或联系管理员");
		}
		return result;
	}
	
	/**
	 * 获取我的合伙人二维码
	 * 说明：根据合伙人id查找信息，
	 * 如果存在二维码地址，直接返回
	 * 如果不存在：请求微信服务器返回二维码，转化成图片上传到七牛，
	 * 成功上传后保存到微信分享关系表（mem_wx_share）里，渠道为：myPartnerQrCode，同时更新合伙人的二维码地址字段
	 * @param memberId
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/getMyPartnerQrCode", method = RequestMethod.GET)
    public Result getMyOwnerQrCode(){
    	Result result = new Result();
    	String clientId = request.getParameter("clientId");
		if(StringUtil.isEmpty(clientId)){
			result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
			return result;
		}
		try{
			result = this.partnerAccountService.getMyOwnerQrCode(Integer.parseInt(clientId));
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_4006, "获取二维码失败");
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
	
}
