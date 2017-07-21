package com.aylson.dc.base.controller;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.base.cache.CacheEntity;
import com.aylson.dc.base.cache.CacheTimerHandler;
import com.aylson.dc.sys.common.SystemConfig;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;

/**
 * 通用控制器
 * 说明：
 * 	  1、
 *    2、
 * @author wwx
 * @since  2016-05
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {
	
	
	/**
     * 获取手机验证码
     *
     * @param phone
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getPhoneVerifyCode", method = RequestMethod.GET)
    public  Result getPhoneVerifyCode(String phone) throws Exception {
    	//返送前校验信息
        Result result = new Result();
        if(StringUtil.isEmpty(phone)){
			result.setError(ResultCode.CODE_STATE_4006, "手机号码不能为空");
			return result;
		}
		if(!VerificationUtils.isValid(phone, VerificationUtils.MOBILE)){
			result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
			return result;
		}
        int sendQuantity = 0;
        // 生成验证码
        int validCode = (int) ((Math.random() * 9 + 1) * 1000);
        if(SystemConfig.isDebugMode()){
        }
        System.out.println(phone+"生成的验证码为："+validCode);
        // 判断缓存中的验证码是否已经存在和过期
        CacheEntity cacheEntity = CacheTimerHandler.getCache(phone);
        if (cacheEntity != null) {
            validCode = (int) cacheEntity.getCacheContext();
            long interval = cacheEntity.getTimeoutStamp()
                    - System.currentTimeMillis();
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
        String content = "您的验证码是："+ validCode;
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
    
   
    
    
    
	
}
