package com.aylson.dc;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.sys.common.SystemConfig;
import com.fastweixin.api.JsAPI;
import com.fastweixin.api.OauthAPI;
import com.fastweixin.api.config.ApiConfig;
import com.fastweixin.api.enums.OauthScope;
import com.fastweixin.api.response.GetSignatureResponse;
import com.fastweixin.message.BaseMsg;
import com.fastweixin.message.CustomMsg;
import com.fastweixin.message.TextMsg;
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
    
	
}
