package com.aylson.dc.base;

import java.io.IOException;
import java.net.URLDecoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.sys.common.SystemConfig;
import com.fastweixin.api.CustomAPI;
import com.fastweixin.api.config.ApiConfig;
import com.fastweixin.message.TextMsg;

/**
 * 
 * @author wwx
 *
 */
public class WxMessageTemplate {

	public static String getPioneerHost(){
		String serverHost = "#";
		if(SystemConfig.isLiveMode()){
			serverHost = SystemConfig.getConfigValueByKey("ktz_service");
		}else{
			serverHost = SystemConfig.getConfigValueByKey("test_service")+"/ktz";
		}
		return serverHost;
	}
	
	/**
	 * 您提交的[变量(工程名称)]客户信息审核已通过，本次获得**积分，点击 这里 查看积分详情。
	 */
	public static String whenPioneerVerifyClientInfo(String projectName, Integer integral, String url){
		String content = "您提交的" + projectName + "客户信息审核已通过，本次获得" + integral + "积分。点击 "
				+ "<a href='"+ url +"'>这里</a> 查看积分详情。";
		return content;
	}
	
	/**
	 * 您提交的[变量(工程名称)]客户信息不属实，审核不通过，点击 这里 处理。
	 */
	public static String whenPioneerVerifyClientInfoImperfect(String projectName, Integer integral, String url){
		String content = "您提交的" + projectName + "客户信息不属实，审核不通过，点击 "
				+ "<a href='"+ url +"'>这里</a> 处理。";
		return content;
	}
	
	/**
	 * 您提交的[代理商名称变量]代理商信息审核已通过，本次获得**积分，点击 这里 查看积分详情。
	 */
	public static String whenPioneerVerifyAgentInfo(String agentName, Integer integral, String url){
		String content = "您提交的" + agentName + "代理商信息审核已通过，本次获得" + integral + "积分，点击 "
				+ "<a href='"+ url +"'>这里</a> 查看积分详情。";
		return content;
	}
	
	/**
	 * 您提交的[代理商名称变量]代理商信息不属实，审核不通过，点击 这里 处理。
	 */
	public static String whenPioneerVerifyAgentInfoImperfect(String agentName, String url){
		String content = "您提交的" + agentName + "不属实，审核不通过，点击 "
				+ "<a href='"+ url +"'>这里</a>处理。";
		return content;
	}
	
	/**
	 * 您提交的[代理商名称变量]代理商已经签约合同，您本次获得**积分，**现金，点击 这里 查看积分现金详情。
	 */
	public static String whenPioneerAgentSign(String agentName, Integer integral,Float cash, String url){
		String content = "您提交的" + agentName + "已经签约合同，您本次获得" + integral + "积分，" + cash + "现金，点击 "
				+ "<a href='"+ url +"'>这里</a>查看积分现金详情。";
		return content;
	}
	
	/**
	 * 您提交的[代理商名称变量]代理商已经签约开业，您本次获得**积分，**现金，点击 这里 查看积分现金详情。
	 */
	public static String whenPioneerAgentOpen(String agentName, Integer integral,Float cash, String url){
		String content = "您提交的" + agentName + "已经签约开业，您本次获得" + integral + "积分，" + cash + "现金，点击 "
				+ "<a href='"+ url +"'>这里</a>查看积分现金详情。";
		return content;
	}
	
	/**
	 * 您的转账申请已经处理，人民币****元款项已经转至您尾号****的银行卡，请注意查收！点击 这里 查看钱包详情。
	 */
	public static String whenPioneerTransferMoney(Float money, String bankNum, String url){
		String content = "您的转账申请已经处理，人民币" + money + "元款项已经转至您尾号" + bankNum + "的银行卡，请注意查收！点击 "
				+ "<a href='"+ url +"'>这里</a>查看钱包详情。";
		return content;
	}
	
	/**
	 * 您的转账申请处理失败，不能将钱转入您尾号****的银行卡，请核对您的卡号信息；已经将钱退回您的钱包，点击 这里 查看钱包详情。
	 */
	public static String whenPioneerTransferFail(String bankNum, String url){
		String content = "您的转账申请处理失败，不能将钱转入您尾号" + bankNum + "的银行卡，请核对您的卡号信息；已经将钱退回您的钱包，点击 "
				+ "<a href='"+ url +"'>这里</a>查看钱包详情。";
		return content;
	}
	
	/**
	 * 您提交的[代理商名称变量]代理商[日期变量]年销售额人民币***元，您本次获得回扣人民币***元，点击 这里 查看钱包详情
	 */
	public static String whenPioneerAgentRebate(String agentName, String year, Float money, Float rebate,String url){
		String content = "您提交的" + agentName + "代理商" + year + "年销售额人民币" + money + "元，"
				+ "您本次获得回扣人民币" + rebate + "元，点击 "
				+ "<a href='"+ url +"'>这里</a>查看钱包详情。";
		return content;
	}
	
	public static Result sendWeixinMesage(String openId, String message) {
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
     * 请求其他服务器发送微信信息
     * @param httpUrl
     * @param wxMessage
     */
	public static void sendWxMessageRequest(String httpUrl, String wxOpenId, String wxMessage){
		try {
			HttpClient client = new HttpClient();
			PostMethod method = new PostMethod(httpUrl);
			client.getParams().setContentCharset("UTF-8");
			method.setRequestHeader("ContentType",
					"application/x-www-form-urlencoded;charset=UTF-8");
			NameValuePair[] data = {
					new NameValuePair("wxOpenId", wxOpenId),
					new NameValuePair("wxMessage", wxMessage), };
			method.setRequestBody(data);
			client.executeMethod(method);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }
	
	
	
}
