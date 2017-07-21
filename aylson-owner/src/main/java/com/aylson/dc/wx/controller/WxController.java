package com.aylson.dc.wx.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.base.GeneralConstant.QRCodeChannel;
import com.aylson.dc.mem.search.MemAccountSearch;
import com.aylson.dc.mem.search.WxShareSearch;
import com.aylson.dc.mem.service.MemAccountService;
import com.aylson.dc.mem.service.WxShareService;
import com.aylson.dc.mem.vo.MemAccountVo;
import com.aylson.dc.mem.vo.WxShareVo;
import com.aylson.dc.sys.common.SystemConfig;
import com.aylson.dc.sys.service.AgentUserService;
import com.aylson.dc.sys.vo.AgentUserVo;
import com.aylson.utils.BillNumUtils;
import com.aylson.utils.StringUtil;
import com.fastweixin.api.CustomAPI;
import com.fastweixin.api.JsAPI;
import com.fastweixin.api.OauthAPI;
import com.fastweixin.api.QrcodeAPI;
import com.fastweixin.api.UserAPI;
import com.fastweixin.api.config.ApiConfig;
import com.fastweixin.api.enums.OauthScope;
import com.fastweixin.api.enums.QrcodeType;
import com.fastweixin.api.response.GetSignatureResponse;
import com.fastweixin.api.response.GetUserInfoResponse;
import com.fastweixin.api.response.OauthGetTokenResponse;
import com.fastweixin.api.response.QrcodeResponse;
import com.fastweixin.message.Article;
import com.fastweixin.message.BaseMsg;
import com.fastweixin.message.CustomMsg;
import com.fastweixin.message.NewsMsg;
import com.fastweixin.message.TextMsg;
import com.fastweixin.message.req.BaseEvent;
import com.fastweixin.message.req.QrCodeEvent;
import com.fastweixin.message.req.TextReqMsg;
import com.fastweixin.servlet.WeixinControllerSupport;

/**
 * 微信框架支持控制器
 * 说明：
 * 	  1、维护与微信平台框架支持，如关注事件之类的处理
 * @author wwx
 * @since  2016-05
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/wx/frame")
public class WxController extends WeixinControllerSupport {

	private static final Logger log = LoggerFactory.getLogger(WxController.class);
	
	private ApiConfig apiConfig;
	
	//服务类
	@Autowired
	private MemAccountService memAccountService;                   //系统-组织机构服务
	@Autowired
	private AgentUserService agentUserService;                     //系统-代理商服务
	@Autowired
	private WxShareService wxShareService;                     //系统-代理商服务
	
	/**
	 * 获取微信Token
	 */
	@Override
	protected String getToken() {
		if(SystemConfig.isLiveMode()){
			return SystemConfig.getConfigValueByKey("Formal_Token");
		}else{
			return SystemConfig.getConfigValueByKey("Test_Token");
		}
	}
	
	/**
	 * 获取微信AppId
	 */
	@Override
	protected String getAppId(){
		if(SystemConfig.isLiveMode()){
			return SystemConfig.getConfigValueByKey("Formal_AppID");
		}else{
			return SystemConfig.getConfigValueByKey("Test_AppID");
		}
	}
	
	/**
	 * 获取微信AppSecret
	 * @return
	 */
	private String getAppSecret(){
		if(SystemConfig.isLiveMode()){
			return SystemConfig.getConfigValueByKey("Formal_AppSecret");
		}else{
			return SystemConfig.getConfigValueByKey("Test_AppSecret");
		}
	}
	
	/**
	 * 获取ApiConfig对象
	 * @return
	 */
	private ApiConfig getApiConfig(){
		if(SystemConfig.isLiveMode()){
			this.apiConfig = new ApiConfig(SystemConfig.getConfigValueByKey("Formal_AppID"), SystemConfig.getConfigValueByKey("Formal_AppSecret"));
		}else{
			this.apiConfig = new ApiConfig(SystemConfig.getConfigValueByKey("Test_AppID"), SystemConfig.getConfigValueByKey("Test_AppSecret"));
		}
		return apiConfig;
	}
	
	/**
	 * 获取微信配置对象
	 * @param enableJsApi
	 * @return
	 */
	private ApiConfig getApiConfig(boolean enableJsApi){
		if(SystemConfig.isLiveMode()){
			this.apiConfig = new ApiConfig(SystemConfig.getConfigValueByKey("Formal_AppID"), SystemConfig.getConfigValueByKey("Formal_AppSecret"),enableJsApi);
		}else{
			this.apiConfig = new ApiConfig(SystemConfig.getConfigValueByKey("Test_AppID"), SystemConfig.getConfigValueByKey("Test_AppSecret"),enableJsApi);
		}
		return apiConfig;
	}
	
	  /**
     * 重写父类方法，处理对应的微信消息
     */
    @Override
    protected BaseMsg handleTextMsg(TextReqMsg msg) {
        String content = msg.getContent();
        log.debug("用户发送到服务器的内容:{}", content);
        if(content.equals("aylson")){
        	return new TextMsg("http://m.aylson.cn");
        }
        return new CustomMsg("davidzhaop");
    }

    /**
     * 处理添加关注事件，有需要时子类重写
     * @param event 添加关注事件对象
     * @return 响应消息对象
     */
    @Override
	protected BaseMsg handleSubscribe(BaseEvent event) {
	  	  this.apiConfig = new ApiConfig(this.getAppId(), this.getAppSecret());
	  	  //关注推送信息处理
	  	  String host = null;
	  	  if(SystemConfig.isLiveMode()){//测试模式
	  		host = SystemConfig.getConfigValueByKey("server_host_live");
	  	  }else{
	  		host = SystemConfig.getConfigValueByKey("server_host_test");  
	  	  }
	      //处理活动扫描
	  	  //处理扫描事件
	      //this.addReferralWxUser(event);
	     // this.addWxShare(event);
	     // String handleTxt = "感谢您关注艾臣安全门窗公众号，我们将竭诚为您服务！来自澳洲品牌，专业研发、设计、生产安全门窗系统、高档贵族阳光房、建筑幕墙、无锁孔安全门、安全静音木门等高技术产品，籍此提高人们的生活品质，是所有高档住宅，大中型别墅、商业办公楼安全门窗的首选配套产品。新人福利猛戳<a href='http://"+ host +"/welfare'>这里</a>！";
	     // String handleTxt = "新人福利猛戳<a href='http://"+ host +"/powercharge'>这里</a>！";
	     /* Boolean isShareSuccess = this.addWxShare(event);
    	  String shareTxt = "";
    	  if(isShareSuccess != null){
    		  if(isShareSuccess){
    			  shareTxt = "助力成功。";
    		  }else{
    			  shareTxt = "您已经助力过了。";
    		  }
    	  }*/
	  	  String shareTxt = this.addWxShare(event);
    	  String handleTxt = shareTxt + "新人福利猛戳<a href='http://"+ host +"/assicharge'>这里</a>！";
	      return new TextMsg(handleTxt);
	}
    
    /**
     * 网页授权
     */
    @RequestMapping(value = "/pageGrant", method = RequestMethod.GET)
    public String pageGrant(String url) {
    	ApiConfig apiConfig = this.getApiConfig();
        OauthAPI oauthAPI = new OauthAPI(apiConfig);
        String pageUrl = oauthAPI.getOauthPageUrl(url, OauthScope.SNSAPI_USERINFO, "STATE");
        return "redirect:"+pageUrl;
    }
    
    /**
     * 获取js-sdk所需的签名
     * @param url 当前网页的URL，不包含#及其后面部分
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getWxSignature", method = RequestMethod.GET)
    public Result getSignature(String url) {
    	Result result = new Result();
    	try{
    		ApiConfig apiConfig = this.getApiConfig(true);
	    	JsAPI jsAPI = new JsAPI(apiConfig);
	    	GetSignatureResponse sign = jsAPI.getSignature(url);
	    	sign.setAppId(this.getAppId());
	    	result.setOK(ResultCode.CODE_STATE_200, "", sign);
    	}catch(Exception e){
    		e.printStackTrace();
    		result.setError(ResultCode.CODE_STATE_4008, "系统繁忙，请稍候重试");
    	}
    	return result;
    }
    
    /**
     * 绑定微信服务器
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/bind", method = RequestMethod.GET)
    @ResponseBody
    protected final void bind(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	PrintWriter writer = response.getWriter();
        if (isLegal(request)) {
            //绑定微信服务器成功
        	 writer.print(request.getParameter("echostr"));
        } else {
            //绑定微信服务器失败
        	//System.out.println("==============================>>绑定微信服务器失败");
        	writer.print("error");
        }
        writer.flush();
        writer.close();
    }
    
    /**
     * 
     * @param response
     * @param memberId
     */
    @ResponseBody
    @RequestMapping(value = "/getMyQrCodeUrl", method = RequestMethod.GET)
    public Result myQrCodeUrl(String channel, Integer memberId){
    	System.out.println("【分享流程】1、获取二维码进行分享");
    	Result result = new Result();
		if(memberId == null){
    		result.setError(ResultCode.CODE_STATE_4008, "分享人id不能为空");
    		return result;
    	}
		if(StringUtil.isEmpty(channel)){
			//channel = QRCodeChannel.MEMBER;//默认：会员分享
			result.setError(ResultCode.CODE_STATE_4008, "分享渠道不能为空");
    		return result;
		}
		//如果已经存在二维码的ticket,直接返回二维码即可
		AgentUserVo agentUserVo = null;
		if(QRCodeChannel.AGENT_OWNER.equals(channel)){//代理商分享业主渠道:agent_owner
			agentUserVo = this.agentUserService.getByUserId(memberId);
			if(agentUserVo == null){
    			result.setError(ResultCode.CODE_STATE_4008, "不存在该分享人的账号信息");
        		return result;
    		}
			if(StringUtil.isNotEmpty(agentUserVo.getWxOwnQrcodeTicket())){
				result.setOK(ResultCode.CODE_STATE_200, "获取成功","https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + agentUserVo.getWxOwnQrcodeTicket());
	    	    return result;
			}
		}
    	try{
			//生成二维码
			ApiConfig apiConfig = new ApiConfig(this.getAppId(),this.getAppSecret());
			String sceneId = channel + "_" + memberId; //场景参数：分享二维码
			QrcodeType qrcodeType = QrcodeType.QR_LIMIT_STR_SCENE;
			QrcodeAPI qrcodeAPI = new QrcodeAPI(apiConfig);
			QrcodeResponse qrcodeResponse = qrcodeAPI.createQrcode(qrcodeType, sceneId, sceneId, 0);
			String ticket = qrcodeResponse.getTicket();
			//保存会员二维码ticket
			if(StringUtil.isEmpty(ticket)){
				result.setError(ResultCode.CODE_STATE_4008, "获取会员二维码失败");
        		return result;
			}
			result.setOK(ResultCode.CODE_STATE_200, "获取成功","https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket);
			if(QRCodeChannel.AGENT_OWNER.equals(channel)){//代理商分享业主渠道:agent_owner
				agentUserVo.setWxOwnQrcodeTicket(ticket);
				this.agentUserService.edit(agentUserVo);
			}
    	}catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_4008, "系统繁忙，请稍候重试");
		}
        return result;
    }
	
	/**
     * 跳转到授权页面，该页面已获取openid,orderno
     * @param code
     * @param model
     * @param toUrl  需要授权的页面(相对路径)
     * @return
     * 暂时不用,由前端控制
     */
    @RequestMapping(value = "/toPage", method = RequestMethod.GET)
    public String toPage(String code, Model model, String toUrl) {
    	if(!StringUtil.isEmpty(toUrl)){
    		toUrl = toUrl.split("&")[0];
    	}
    	//获取微信用户信息
    	OauthAPI oauthAPI = new OauthAPI(this.apiConfig);
        OauthGetTokenResponse response = oauthAPI.getToken(code);
        if(response!=null && response.getOpenid()!=null){
        	UserAPI userAPI = new UserAPI(apiConfig);
            GetUserInfoResponse userInfo = userAPI.getUserInfo(response.getOpenid());
            
            model.addAttribute("wechat_user_info", userInfo);
            model.addAttribute("openid", response.getOpenid());
            model.addAttribute("orderno",BillNumUtils.getBillNum(0));
        }
        return toUrl;
    }
    
    /**
     * 网友授权接收code校验
     * @param code
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getByCode", method = RequestMethod.GET)
    public Result getByCode(String code) {
    	Result result = new Result();
    	Map<String, Object> info = new HashMap<String, Object>();
    	try{
    		//获取微信用户信息
        	OauthAPI oauthAPI = new OauthAPI( this.getApiConfig());
            OauthGetTokenResponse response = oauthAPI.getToken(code);
            if(response!=null && response.getOpenid()!=null){
            	UserAPI userAPI = new UserAPI( this.getApiConfig());
                GetUserInfoResponse userInfo = userAPI.getUserInfo(response.getOpenid());
                info.put("wechat_user_info", userInfo);
                info.put("openid", response.getOpenid());
                info.put("orderno", BillNumUtils.getBillCode());
            }
	    	result.setOK(ResultCode.CODE_STATE_200, "", info);
    	}catch(Exception e){
    		e.printStackTrace();
    		result.setError(ResultCode.CODE_STATE_4008, "系统繁忙，请稍候重试");
    	}
    	return result;
    }
    
    /**
     * 获取活动临时二维码
     * @param channel
     * @param inviterWxOpenId
     * @param inviterWxUnionId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getActivityQrCodeUrl", method = RequestMethod.GET)
    public Result getActivityQrCodeUrl(String channel, String inviterWxOpenId, String inviterWxUnionId){
    	System.out.println("【分享流程】1、获取活动二维码进行分享");
    	Result result = new Result();
		if(StringUtil.isEmpty(channel)){
			result.setError(ResultCode.CODE_STATE_4008, "活动渠道不能为空");
    		return result;
		}
		if(StringUtil.isEmpty(inviterWxOpenId)){
			result.setError(ResultCode.CODE_STATE_4008, "分享人openId不能为空");
    		return result;
		}
		if(StringUtil.isEmpty(inviterWxUnionId)){
			result.setError(ResultCode.CODE_STATE_4008, "分享人unionId不能为空");
    		return result;
		}
    	try{
			//生成二维码
			ApiConfig apiConfig = new ApiConfig(this.getAppId(),this.getAppSecret());
			//String sceneId = channel + "_" + inviterWxOpenId + "_" + inviterWxUnionId; //场景参数：分享二维码
			String sceneId = channel + "_" + inviterWxOpenId; //场景参数：分享二维码
			QrcodeType qrcodeType = QrcodeType.QR_LIMIT_STR_SCENE;
			QrcodeAPI qrcodeAPI = new QrcodeAPI(apiConfig);
			//sceneId = "123";
			//QrcodeResponse qrcodeResponse = qrcodeAPI.createQrcode(qrcodeType, sceneId, sceneId, null);
			QrcodeResponse qrcodeResponse = qrcodeAPI.createQrcode(qrcodeType, sceneId, sceneId, 0);
			String ticket = qrcodeResponse.getTicket();
			//保存会员二维码ticket
			if(StringUtil.isEmpty(ticket)){
				result.setError(ResultCode.CODE_STATE_4008, "获取活动二维码失败");
        		return result;
			}
			result.setOK(ResultCode.CODE_STATE_200, "获取成功","https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket);
    	}catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_4008, "系统繁忙，请稍候重试");
		}
        return result;
    }
    
    /**
     * 处理扫描二维码事件，有需要时子类重写
     *
     * @param event 扫描二维码事件对象
     * @return 响应消息对象
     */
    /*private Boolean addWxShare(BaseEvent event) {
    	System.out.println("【分享流程】2、扫描二维码绑定分享人与微信用户关系");
    	Boolean isShareSuccess = null;
    	if(StringUtil.isNotEmpty(event.getTicket()) && StringUtil.isNotEmpty(event.getEventKey())){
    		String shareId = event.getEventKey();//获取参数：分享人信息id
    		if(StringUtil.isNotEmpty(shareId)){
    			WxShareVo sharer = this.wxShareService.getById(Integer.parseInt(shareId));//获取分享人信息
    			if(sharer != null){
    				if(StringUtil.isNotEmpty(sharer.getWxOpenId())){//分享人的信息
    					if(sharer.getWxOpenId().equals(event.getFromUserName())){
    						isShareSuccess = true;
    						return isShareSuccess;
    					}
    	    			//2.3、如果还没有助力过，那么添加一条助力记录
    	    			Result shareResult = this.wxShareService.isShared(sharer.getChannel(), sharer.getWxOpenId(), event.getFromUserName());
    	    			if(shareResult.isSuccess()){
    	    				UserAPI userAPI = new UserAPI(apiConfig);
    	    				GetUserInfoResponse userInfo = userAPI.getUserInfo(event.getFromUserName());
    	    				//微信绑定对象
    	    				WxShareVo wxShareVo = new WxShareVo();
    	    				wxShareVo.setChannel(sharer.getChannel());
    	    				wxShareVo.setInviterWxOpenId(sharer.getWxOpenId());
    	    				wxShareVo.setInviterWxUnionId(sharer.getWxUnionId());
    	    				wxShareVo.setWxOpenId(event.getFromUserName());
    	    				wxShareVo.setWxUnionId(userInfo.getUnionid());
    	    				wxShareVo.setWxHeadPhoto(userInfo.getHeadimgurl());
    	    				wxShareVo.setWxNickName(userInfo.getNickname());
    	    				wxShareVo.setCreateTime(new Date());
    	    				wxShareVo.setAssistValue(this.wxShareService.getAssistValue(100, sharer.getSharesHad(), 800, sharer.getAssistValueHad()));
    	    				//助力数
    	    				isShareSuccess = this.wxShareService.add(wxShareVo);
    	    			}else{
    	    				isShareSuccess = false;
    	    			}
    	    		}
    			}
    		}
    	}
    	return isShareSuccess;
    }*/
    
    /**
     * 处理二维码扫描关注
     * @param event
     * @param type
     */
    private String addWxShare(BaseEvent event){
    	String tip = "";
    	if(StringUtil.isNotEmpty(event.getTicket()) && StringUtil.isNotEmpty(event.getEventKey())){
    		String shareId = event.getEventKey();//获取二维码携带参数：mem_wx_share  微信分享表id
    		if(StringUtil.isNotEmpty(shareId)){
    			if(shareId.indexOf("qrscene") == 0){
    				   shareId = shareId.split("_")[1];
    			}
    			WxShareVo sharer = this.wxShareService.getById(Integer.parseInt(shareId));//获取分享人信息
    			if(sharer != null){
    				//信息通用校验
    				if(sharer.getWxOpenId().equals(event.getFromUserName())){//邀请人和分享人不能一样
    					/*if("AssiCharge".equals(sharer.getChannel())){         //好友助力渠道
    	    				tip = "邀请人和分享人不能是同一个";
	    				}*/
    					return tip;
					}
    				if(StringUtil.isEmpty(sharer.getChannel())){
    					return tip;
    				}
    				//是否已经存在
	    			Result shareResult = this.wxShareService.isShared(sharer.getChannel(), sharer.getWxOpenId(), event.getFromUserName());
    				if(shareResult.isSuccess()){//没有存在，添加一条记录
    					UserAPI userAPI = new UserAPI(apiConfig);
	    				GetUserInfoResponse userInfo = userAPI.getUserInfo(event.getFromUserName());
	    				//微信绑定对象
	    				WxShareVo wxShareVo = new WxShareVo();
	    				wxShareVo.setChannel(sharer.getChannel());
	    				wxShareVo.setInviterWxOpenId(sharer.getWxOpenId());
	    				wxShareVo.setInviterWxUnionId(sharer.getWxUnionId());
	    				wxShareVo.setWxOpenId(event.getFromUserName());
	    				wxShareVo.setWxUnionId(userInfo.getUnionid());
	    				wxShareVo.setWxHeadPhoto(userInfo.getHeadimgurl());
	    				wxShareVo.setWxNickName(userInfo.getNickname());
	    				wxShareVo.setCreateTime(new Date());
	    				//根据不同的渠道做处理
	    				if("AssiCharge".equals(sharer.getChannel())){         //好友助力渠道
	    					tip = "助力成功。";
    	    				//wxShareVo.setAssistValue(this.wxShareService.getAssistValue(100, sharer.getSharesHad(), 800, sharer.getAssistValueHad()));
    	    				wxShareVo.setAssistValue(this.wxShareService.getAssistValue(120, sharer.getSharesHad(), 800, sharer.getAssistValueHad()));
	    				}else if("myOwnerQrCode".equals(sharer.getChannel())){//业主公众号我的二维码分享扫描
	    					
	    				}
	    				Boolean flag = false;
	    				try{
	    					flag = this.wxShareService.add(wxShareVo);
	    				}catch(Exception e){
	    					//处理一次特殊字符
	    					if(StringUtil.isNotEmpty(wxShareVo.getWxNickName())){
	    						wxShareVo.setWxNickName(wxShareVo.getWxNickName().replaceAll("[^\\u0000-\\uFFFF]", ""));
	    						flag = this.wxShareService.add(wxShareVo);
	    					}
	    				}
	    				if(!flag){
	    					if("AssiCharge".equals(sharer.getChannel())){         //好友助力渠道
		    					tip = "助力失败。";
		    				}
	    				}
    				}else{
    					if("AssiCharge".equals(sharer.getChannel())){         //好友助力渠道
	    					tip = "您已经助力过了。";
	    				}
    				}
    			}
    		}
    	}
    	return tip;
    }
    
    @Override
    protected BaseMsg handleQrCodeEvent(QrCodeEvent event) {
    	this.apiConfig = new ApiConfig(this.getAppId(), this.getAppSecret());
	  	  //关注推送信息处理
	  	  String host = null;
	  	  if(SystemConfig.isLiveMode()){//测试模式
	  		host = SystemConfig.getConfigValueByKey("server_host_live");
	  	  }else{
	  		host = SystemConfig.getConfigValueByKey("server_host_test");  
	  	  }
    	/*Boolean isShareSuccess = this.addWxShare(event);
    	String shareTxt = "";
	  	  if(isShareSuccess != null){
	  		  if(isShareSuccess){
	  			  shareTxt = "助力成功。";
	  		  }else{
	  			  shareTxt = "您已经助力过了。";
	  		  }
	  	  }*/
	  	String shareTxt = this.addWxShare(event);
    	String handleTxt = shareTxt + "新人福利猛戳<a href='http://"+ host +"/assicharge'>这里</a>！";
    	//String handleTxt = "感谢您关注艾臣安全门窗公众号，我们将竭诚为您服务！来自澳洲品牌，专业研发、设计、生产安全门窗系统、高档贵族阳光房、建筑幕墙、无锁孔安全门、安全静音木门等高技术产品，籍此提高人们的生活品质，是所有高档住宅，大中型别墅、商业办公楼安全门窗的首选配套产品。新人福利猛戳<a href='http://"+ host +"/welfare'>这里</a>！";
	    return new TextMsg(handleTxt);
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/getMySjsQrCodeUrl", method = RequestMethod.GET)
    public Result mySjsQrCodeUrl(String channel, Integer memberId){
    	System.out.println("【分享流程】1、获取二维码进行分享");
    	Result result = new Result();
		if(memberId == null){
    		result.setError(ResultCode.CODE_STATE_4008, "分享人id不能为空");
    		return result;
    	}
		if(StringUtil.isEmpty(channel)){
			//channel = QRCodeChannel.MEMBER;//默认：会员分享
			result.setError(ResultCode.CODE_STATE_4008, "分享渠道不能为空");
    		return result;
		}
		//如果已经存在二维码的ticket,直接返回二维码即可
		AgentUserVo agentUserVo = null;
		MemAccountVo memAccountVo = null;
		if(QRCodeChannel.AGENT.equals(channel)){//代理商分享渠道:agent
			agentUserVo = this.agentUserService.getByUserId(memberId);
			if(agentUserVo == null){
    			result.setError(ResultCode.CODE_STATE_4008, "不存在该分享人的账号信息");
        		return result;
    		}
			if(StringUtil.isNotEmpty(agentUserVo.getWxQrcodeTicket())){
				result.setOK(ResultCode.CODE_STATE_200, "获取成功","https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + agentUserVo.getWxQrcodeTicket());
	    	    return result;
			}
		}else{//会员分享渠道:member
			memAccountVo = this.memAccountService.getById(memberId);
			if(memAccountVo == null){
    			result.setError(ResultCode.CODE_STATE_4008, "不存在该分享人的账号信息");
        		return result;
    		}
			if(StringUtil.isNotEmpty(memAccountVo.getWxQrcodeTicket())){
				result.setOK(ResultCode.CODE_STATE_200, "获取成功","https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + memAccountVo.getWxQrcodeTicket());
	    	    return result;
			}
		}
    	try{
			//生成二维码
			ApiConfig apiConfig = new ApiConfig(this.getAppId(),this.getAppSecret());
			String sceneId = channel + "_" + memberId; //场景参数：分享二维码
			QrcodeType qrcodeType = QrcodeType.QR_LIMIT_STR_SCENE;
			QrcodeAPI qrcodeAPI = new QrcodeAPI(apiConfig);
			QrcodeResponse qrcodeResponse = qrcodeAPI.createQrcode(qrcodeType, sceneId, sceneId, 0);
			String ticket = qrcodeResponse.getTicket();
			//保存会员二维码ticket
			if(StringUtil.isEmpty(ticket)){
				result.setError(ResultCode.CODE_STATE_4008, "获取会员二维码失败");
        		return result;
			}
			result.setOK(ResultCode.CODE_STATE_200, "获取成功","https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket);
			if(QRCodeChannel.AGENT.equals(channel)){//代理商分享渠道:agent
				agentUserVo.setWxQrcodeTicket(ticket);
				this.agentUserService.edit(agentUserVo);
			}else{
				memAccountVo.setWxQrcodeTicket(ticket);
				this.memAccountService.edit(memAccountVo);
			}
    	}catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_4008, "系统繁忙，请稍候重试");
		}
        return result;
    }
    
    /**
     * 通过活动分享人获取临时二维码
     * @param shareId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getActivityQrCode", method = RequestMethod.GET)
    public Result getActivityQrCode(Integer shareId){
    	System.out.println("【分享流程】获取临时二维码");
    	Result result = new Result();
		if(shareId == null){
			result.setError(ResultCode.CODE_STATE_4008, "获取活动二维码参数失败");
    		return result;
		}
		WxShareVo wxShareVo = this.wxShareService.getById(shareId);
		if(wxShareVo == null){
			result.setError(ResultCode.CODE_STATE_4008, "获取活动二维码信息失败");
    		return result;
		}
    	try{
			//生成临时二维码
			ApiConfig apiConfig = new ApiConfig(this.getAppId(),this.getAppSecret());
			QrcodeType qrcodeType = QrcodeType.QR_SCENE;   //临时二维码类型
			String sceneId = shareId+"";                   //二维码携带参数
			String sceneStr = "";                          
			QrcodeAPI qrcodeAPI = new QrcodeAPI(apiConfig);
			QrcodeResponse qrcodeResponse = qrcodeAPI.createQrcode(qrcodeType, sceneId, sceneStr, 2592000);
			String ticket = qrcodeResponse.getTicket();
			if(StringUtil.isEmpty(ticket)){
				result.setError(ResultCode.CODE_STATE_4008, "获取活动二维码失败");
        		return result;
			}
			result.setOK(ResultCode.CODE_STATE_200, "获取成功","https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket);
    	}catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_4008, "系统繁忙，请稍候重试");
		}
        return result;
    }
    
    /**
     * 获取我的二维码-业主公众号-个人中心
     * @param memberId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMyOwnerQrCode", method = RequestMethod.GET)
    public Result getMyOwnerQrCode(Integer memberId){
    	Result result = new Result();
    	//信息校验
    	if(memberId == null){
    		result.setError(ResultCode.CODE_STATE_4008, "获取会员信息失败");
    		return result;
    	}
    	MemAccountVo memAccountVo = this.memAccountService.getById(memberId);
    	if(memAccountVo == null){
    		result.setError(ResultCode.CODE_STATE_4008, "获取会员信息失败");
    		return result;
    	}
    	//查看分享关系表是否已经有记录
    	WxShareSearch wxShareSearch =  new WxShareSearch();
    	wxShareSearch.setChannel("myOwnerQrCode");             //我的二维码：相当于一个渠道   
    	wxShareSearch.setSourceId(memberId);
    	wxShareSearch.setType(1);//1:表示来源为会员账号
    	List<WxShareVo> list = this.wxShareService.getList(wxShareSearch);
    	WxShareVo sharer = null;
    	if(list != null && list.size() > 0){//如果已经存在，直接获取
    		sharer = list.get(0);
    	}else{//如果不存在，新增一条
    		sharer = new WxShareVo();
    		sharer.setChannel("myOwnerQrCode");             //我的二维码：相当于一个渠道   
    		sharer.setSourceId(memberId);                   //来源id
    		sharer.setType(1);                              //1:表示来源为会员账号
    		sharer.setWxOpenId(memAccountVo.getWxOpenId());
    		sharer.setWxUnionId(memAccountVo.getWxUnionId());
    		sharer.setWxHeadPhoto(memAccountVo.getWxHeadPhoto());
    		sharer.setWxNickName(memAccountVo.getWxNickName());
    		sharer.setRemark("业主公众号我的二维码分享");
    		sharer.setCreateTime(new Date());
    		Boolean flag = this.wxShareService.add(sharer);
    		if(!flag){
    			result.setError(ResultCode.CODE_STATE_4008, "保存会员二维码信息失败");
        		return result;
    		}
    	}
    	//调用微信接口获取临时二维码
    	try{
			//生成临时二维码
			ApiConfig apiConfig = new ApiConfig(this.getAppId(),this.getAppSecret());
			QrcodeType qrcodeType = QrcodeType.QR_SCENE;          //临时二维码类型
			String sceneId = sharer.getId()+"";                   //二维码携带参数
			String sceneStr = "";                          
			QrcodeAPI qrcodeAPI = new QrcodeAPI(apiConfig);
			QrcodeResponse qrcodeResponse = qrcodeAPI.createQrcode(qrcodeType, sceneId, sceneStr, 2592000);
			String ticket = qrcodeResponse.getTicket();
			if(StringUtil.isEmpty(ticket)){
				result.setError(ResultCode.CODE_STATE_4008, "获取活动二维码失败");
        		return result;
			}
			result.setOK(ResultCode.CODE_STATE_200, "获取成功","https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket);
    	}catch (Exception e) {
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_4008, "系统繁忙，请稍候重试");
		}
    	return result;
    }
    
    /**
	 * 发送微信文章
	 * @param address
	 * @return
	 */
	@RequestMapping(value = "/sendWxArticle", method = RequestMethod.GET)
	@ResponseBody
	public Result sendWxArticle(String address) {
		//获取微信token
		Result result = new Result();
		String appid = "wx5afd32ac14c76152";
		String secret = "3ab5befd3132dca2c5ac0e87badb296d";
		ApiConfig config = new ApiConfig(appid, secret);
	    Article article1 = new Article("艾臣门窗全景展厅全新上线，邀您体验", "", "http://ktz.aylsonclub.com/static/imgs/1.jpg", "http://www.hzzx3d.com/news/aylson/index.html");
	    NewsMsg newMsg = new NewsMsg();
	    newMsg.add(article1);
	    try{
	    	List<String> list = this.memAccountService.getWxOpenIdList();
	    	/*for(String wxOpenId:list){
	    		System.out.println(wxOpenId);
	    		customAPI.sendCustomMessage("oZvguvxhdKPSUnZHep0LtFpnEbnk", newMsg);
	    	}*/
	    	System.out.println("========================发送微信开始================================");
	        for(int i=0;i<list.size();i++){
	    	    CustomAPI customAPI = new CustomAPI(config);
	    		String wxOpenId = list.get(i);
	    		/*if(i != 0 && i%8==0){
	    			Thread.sleep(1000*60*1);//延迟三分钟发送
	    			System.out.println("延迟1分钟发送");
	    		}*/
	    		customAPI.sendCustomMessage(wxOpenId, newMsg);
//	    		customAPI.sendCustomMessage("oZvguvxhdKPSUnZHep0LtFpnEbnk", newMsg);
	    		System.out.println(wxOpenId);
	    	}
//	    	 CustomAPI customAPI = new CustomAPI(config);
//	    	 customAPI.sendCustomMessage("oZvguvxhdKPSUnZHep0LtFpnEbnk", newMsg);
	    	System.out.println("========================发送微信结束================================");
			result.setOK(ResultCode.CODE_STATE_200, "获取成功");

	      }catch(Exception e){
	    	  e.printStackTrace();
	    	  result.setError(ResultCode.CODE_STATE_4008, "系统繁忙，请稍候重试");
	    }
	    
		return result;
	}
    
    
}
