package com.aylson.dc.base.controller;

import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.base.SmsTemplate;
import com.aylson.dc.base.cache.CacheEntity;
import com.aylson.dc.base.cache.CacheTimerHandler;
import com.aylson.dc.mem.service.MemAccountService;
import com.aylson.dc.sys.common.SystemConfig;
import com.aylson.utils.MapUtil;
import com.aylson.utils.QiniuUtils;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;
import com.aylson.utils.QiniuUtils.DomainToBucket;
import com.fastweixin.api.CustomAPI;
import com.fastweixin.api.config.ApiConfig;
import com.fastweixin.message.TextMsg;

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
	
	@Autowired
	private MemAccountService memAccountService;                   //系统-组织机构服务
	
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
        String content = SmsTemplate.getSmsWhenValidCode(validCode+"");
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
     * 发送验证码之前校验
     * @param phone
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/beforeSendValidCode", method = RequestMethod.GET)
    public Result beforeSendValidCode(String phone, Integer type, Integer memberType){
    	Result result = null;
		try{
			result = this.memAccountService.beforeSendValidCode(phone, type, memberType );
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
    }
    
    
    /**
     * 响应其他服务器的请求发送微信信息
     * @param httpUrl
     * @param wxMessage
     */
    @RequestMapping(value = "/sendWxMessageResponse", method = RequestMethod.POST)
    public void sendWxMessageResponse(String wxOpenId, String wxMessage){
    	System.out.println("sendWxMessageResponse==>wxOpenId:"+wxOpenId);
    	System.out.println("sendWxMessageResponse==>wxMessage:"+wxMessage);
    	if(StringUtil.isNotEmpty(wxOpenId) && StringUtil.isNotEmpty(wxMessage)){
    		try {
	    		String appId = null;
				String appSecret = null;
				if(SystemConfig.isLiveMode()){
					appId = SystemConfig.getConfigValueByKey("Formal_AppID");
					appSecret = SystemConfig.getConfigValueByKey("Formal_AppSecret");
				}else{
					appId = SystemConfig.getConfigValueByKey("Test_AppID");
					appSecret = SystemConfig.getConfigValueByKey("Test_AppSecret");
				}
				System.out.println("sendWxMessageResponse==>appId:"+appId);
				System.out.println("sendWxMessageResponse==>appSecret:"+appSecret);
				ApiConfig apiConfig = new ApiConfig(appId,appSecret);
				CustomAPI customAPI = new CustomAPI(apiConfig);
				customAPI.sendCustomMessage(wxOpenId,	new TextMsg(wxMessage));
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}
    }
    
    /**
	 * 根据地址获取经纬度
	 * @param address
	 * @return
	 */
	@RequestMapping(value = "/getGpsByAddress", method = RequestMethod.POST)
	@ResponseBody
	public Result getGpsByAddress(String address) {
		Result result = new Result();
		try{
			if(StringUtil.isEmpty(address)){
				result.setError(ResultCode.CODE_STATE_4006, "地址不能为空");
				return result;
			}
			float[] gps = MapUtil.addressToGPS(address);
			if(gps == null){
				result.setError(ResultCode.CODE_STATE_4006, "获取不到经纬度，请输入其他地址试试");
				return result;
			}
			result.setOK(ResultCode.CODE_STATE_200, "获取成功",gps);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "系统繁忙，请稍后再试或者联系管理员");
		}
		return result;
	}
	
	/**
	 * 上传图片
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/imgUpload", method = RequestMethod.POST)
	@ResponseBody
	public Result imgUpload(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Result result = new Result();
		String bucket =  request.getParameter("bucket");
		if(!DomainToBucket.DomainToBucketMap.containsKey(bucket)){
			result.setError(ResultCode.CODE_STATE_4006, "找不到对应的资源空间，请联系管理");
			return result;
		}
		if (SystemConfig.isDebugMode()) {//debug模式统一上传到测试空间
			bucket = "dc-test";
		}
		byte[] files = null; //上传的文件
		int statusCode = 0;  //上传结果code
		String fileName = null;//七牛云存放文件名
		//1、获取上传的文件
		long startTime=System.currentTimeMillis();   //获取开始时间
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){ //判断request是否有文件上传
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
			Iterator<String> ite = multiRequest.getFileNames();
			while(ite.hasNext()){
				MultipartFile file = multiRequest.getFile(ite.next());
				if(file!=null){
					try {
						 files = file.getBytes();
						 fileName = bucket +"-"+ new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"@|@"+ file.getOriginalFilename();
						 statusCode = QiniuUtils.uploadFile(files, fileName,bucket,QiniuUtils.UPLOAD_SIMPLE);
						 break;
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
		}
		long endTime=System.currentTimeMillis(); //获取结束时间
		System.out.println("上传文件共使用时间："+(endTime-startTime));
		if(statusCode == 200){//成功：返回图片存放地址
			result.setData(DomainToBucket.DomainToBucketMap.get(bucket)+fileName);
			result.setSuccess(true);
			result.setOK(ResultCode.CODE_STATE_200, "上传成功");
		}else{//失败：返回失败信息
			result.setError(ResultCode.CODE_STATE_4006, "上传失败");
		}
		return result;
	}
    
	
}
