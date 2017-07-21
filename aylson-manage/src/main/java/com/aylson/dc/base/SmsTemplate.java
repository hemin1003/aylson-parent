package com.aylson.dc.base;

import java.util.Date;

import com.aylson.utils.DateUtil;

/**
 * 
 * @author wwx
 *
 */

public class SmsTemplate {
	/**
	 * 短信模板
	 * 1：艾臣俱乐部会员【变量】，通过【变量】，于【变量】提交了【变量】设计需求，请及时跟进处理。
	 * 2：艾臣俱乐部会员【变量】，通过【变量】，于【变量】提交了【变量】客户信息，请及时跟进处理。
	 * 3：艾臣俱乐部会员【变量】，通过【变量】，于【变量】确认了【变量】设计方案，请及时跟进处理。
	 * 4：尊敬的会员，恭喜您。您已成功兑换礼品【变量】，一共消耗【变量】积分。您的礼品兑换码是【变量】，请于【变量】前联系我司工作人员领取奖品。联系电话：400-816-2882
	 * 5：您提交的【变量】设计已结算，您本次获得【变量】积分。再次感谢您对艾臣的信任与支持。
	 * 6：您提交的【变量】设计需求资料不完善，详情请登录平台查看并按要求补充相关资料。
	 * 7：您提交的【变量】设计方案已完成，收到后请尽快登录平台查阅并反馈。感谢您对艾臣的信任与支持。
	 * 8：您提交的【变量】客户信息不属实，审核没通过，收到后请尽快登录平台并处理。
	 * 9：您提交的【变量】客户信息审核已通过，收到后请尽快登录平台并提交设计相关需求资料（包括设计需求、平面图、设计信息表和草图等文件或照片）。
	 * 10：您的验证码是：【变量】
	 * 11：您提交的【变量】业主信息审核已通过，【变量】积分已到账。感谢您对艾臣的信任与支持。
	 * 12:艾臣开拓者联盟会员[会员名称变量(会员名称+手机号码)],于[日期变量]提交了[工程名称变量]客户信息，请及时跟进处理。
	 * 13:您提交的[变量(工程名称)]客户信息审核已通过，本次获得**积分，请登录艾臣开拓者联盟俱乐部查看积分详情。
	 * 14:您提交的[变量(工程名称)]客户信息不属实，审核不通过，收到后请尽快登录平台并处理。
	 * 15:艾臣开拓者联盟会员[会员名称变量(会员名称+手机号码)],于[日期变量]提交了[代理商名称变量]代理商信息，请及时跟进处理。
	 * 16:您提交的[代理商名称变量]代理商信息审核已通过，本次获得**积分，请登录艾臣开拓者联盟俱乐部查看积分详情。
	 * 17:您提交的[代理商名称变量]代理商信息不属实，审核不通过，收到后请尽快登录平台并处理。
	 * 18:您提交的[代理商名称变量]代理商已经签约合同，您本次获得**积分，**现金，请登录艾臣开拓者联盟俱乐部查看积分现金详情。
	 * 19:您提交的[代理商名称变量]代理商已经开业，您本次获得**积分，**现金，请登录艾臣开拓者联盟俱乐部查看积分现金详情。
	 * 20:您的转账申请已经处理，人民币****元款项已经转至您尾号****的银行卡，请注意查收！再次感谢你对艾臣的信任与支持！
	 * 21:您提交的[代理商名称变量]代理商[日期变量]年销售额人民币***元，您本次获得回扣人民币***元，请登录艾臣开拓者联盟俱乐部查看钱包详情
	 * 22:尊敬的会员，恭喜您。您已成功兑换礼品[礼品名称变量]，一共消耗***积分，请于[日期变量]前联系我司工作人员领取奖品。联系电话：400-816-2882
	 * 23:您的转账申请处理失败，不能将钱转入您尾号****的银行卡，请核对您的卡号信息；已经将钱退回您的钱包，请登录艾臣开拓者联盟俱乐部查看钱包详情！
	 */
	
	/**
	 * 1:艾臣俱乐部会员【变量】，通过【变量】，于【变量】提交了【变量】设计需求，请及时跟进处理。
	 * @param memberName
	 * @param channel
	 * @param createTime
	 * @param projectNaem
	 * @return
	 */
	public static String getSmsWhenSubmitRequire(String memberName, String channel, Date createTime, String projectName){
		String content = "艾臣俱乐部会员" + memberName + "，通过" + channel + "，于" + DateUtil.format(createTime, "yyyy年MM月dd日 hh时ss分mm秒") + "提交了" + projectName + "设计需求，请及时跟进处理。";
		return content;
	}
	
	/**
	 * 2:艾臣俱乐部会员【变量】，通过【变量】，于【变量】提交了【变量】客户信息，请及时跟进处理。
	 * @param memberName
	 * @param channel
	 * @param createTime
	 * @param projectNaem
	 * @return
	 */
	public static String getSmsWhenSubmitClientInfo(String memberName, String channel, Date createTime, String projectName){
		String content = "艾臣俱乐部会员" + memberName + "，通过" + channel + "，于" + DateUtil.format(createTime, "yyyy年MM月dd日 hh时ss分mm秒") + "提交了" + projectName + "客户信息，请及时跟进处理。";
		return content;
	}
	
	/**
	 * 3:艾臣俱乐部会员【变量】，通过【变量】，于【变量】确认了【变量】设计方案，请及时跟进处理。
	 * @param memberName
	 * @param channel
	 * @param createTime
	 * @param projectNaem
	 * @return
	 */
	public static String getSmsWhenConfirmDesign(String memberName, String channel, Date createTime, String projectName){
		String content = "艾臣俱乐部会员" + memberName + "，通过" + channel + "，于" + DateUtil.format(createTime, "yyyy年MM月dd日 hh时ss分mm秒") + "提交了" + projectName + "设计方案，请及时跟进处理。";
		return content;
	}
	
	/**
	 * 4:尊敬的会员，恭喜您。您已成功兑换礼品【变量】，一共消耗【变量】积分。您的礼品兑换码是【变量】，请于【变量】前联系我司工作人员领取奖品。联系电话：400-816-2882
	 * @param giftName
	 * @param exchangeIntegral
	 * @param exchangeCode
	 * @param exchangeTime
	 * @return
	 */
	public static String getSmsWhenExchangeGift(String giftName, Integer exchangeIntegral, String exchangeCode, Date exchangeTime){
		String content = "尊敬的会员，恭喜您。您已成功兑换礼品" + giftName + "，一共消耗" + exchangeIntegral + "积分。您的礼品兑换码是" + exchangeCode + "，请于" + DateUtil.format(exchangeTime, "yyyy年MM月dd日") + "前联系我司工作人员领取奖品。联系电话：400-816-2882";
		return content;
	}
	
	/**
	 * 5:您提交的【变量】设计已结算，您本次获得【变量】积分。再次感谢您对艾臣的信任与支持。
	 * @param projectName
	 * @param integral
	 * @return
	 */
	public static String getSmsWhenSettle(String projectName, Integer integral){
		String content = "您提交的" + projectName + "设计已结算，您本次获得" + integral + "积分。再次感谢您对艾臣的信任与支持。";
		return content;
	}
	
	/**
	 * 6:您提交的【变量】设计需求资料不完善，详情请登录平台查看并按要求补充相关资料。
	 * @param projectName
	 * @return
	 */
	public static String getSmsWhenRequireImperfect(String projectName){
		String content = "您提交的" + projectName + "设计需求资料不完善，详情请登录平台查看并按要求补充相关资料。";
		return content;
	}
	
	/**
	 * 7：您提交的【变量】设计方案已完成，收到后请尽快登录平台查阅并反馈。感谢您对艾臣的信任与支持。
	 * @param projectName
	 * @return
	 */
	public static String getSmsWhenFinishDesign(String projectName){
		String content = "您提交的" + projectName + "设计方案已完成，收到后请尽快登录平台查阅并反馈。感谢您对艾臣的信任与支持。";
		return content;
	}
	
	/**
	 * 8：您提交的【变量】客户信息不属实，审核没通过，收到后请尽快登录平台并处理。
	 * @param projectName
	 * @return
	 */
	public static String getSmsWhenClientInfoImperfect(String projectName){
		String content = "您提交的" + projectName + "客户信息不属实，审核没通过，收到后请尽快登录平台并处理。";
		return content;
	}
	
	/**
	 * 9：您提交的【变量】客户信息审核已通过，收到后请尽快登录平台并提交设计相关需求资料（包括设计需求、平面图、设计信息表和草图等文件或照片）。
	 * @param projectName
	 * @return
	 */
	public static String getSmsWhenVerifyClientInfo(String projectName){
		String content = "您提交的" + projectName + "客户信息审核已通过，收到后请尽快登录平台并提交设计相关需求资料（包括设计需求、平面图、设计信息表和草图等文件或照片）。";
		return content;
	}
	
	/**
	 * 10：您的验证码是：【变量】
	 * @param validCode
	 * @return
	 */
	public static String getSmsWhenValidCode(String validCode){
		String content = "您的验证码是："+validCode;
		return content;
	}
	
	/**
	 * 11：您提交的【变量】业主信息审核已通过，【变量】积分已到账。感谢您对艾臣的信任与支持。
	 * @param projectName
	 * @return
	 */
	public static String getSmsWhenVerifyClientInfo(String projectName, Integer integral){
		String content = "您提交的" + projectName + "业主信息审核已通过，" + integral + "积分已到账。感谢您对艾臣的信任与支持。";
		return content;
	}
	
	/**
	 * 艾臣俱乐部会员【变量】对【变量】工程的设计方案不满意，请及时跟进处理。
	 * @param projectName
	 * @param memberName
	 * @return
	 */
	public static String getSmsWhenReturnDesign(String projectName, String memberName){
		String content = "艾臣俱乐部会员" + memberName + "对"+ projectName +"工程的设计方案不满意，请及时跟进处理。";
		return content;
	}
	
	/**
	 * 12:艾臣开拓者联盟会员[会员名称变量(会员名称+手机号码)],于[日期变量]提交了[工程名称变量]客户信息，请及时跟进处理。
	 */
	public static String getSmsWhenPioneerSubmitClientInfo(String memberName, Date createTime, String projectName){
		String content = "艾臣开拓者联盟会员" + memberName + "，于" + DateUtil.format(createTime, "yyyy年MM月dd日 hh时ss分mm秒") + "提交了" + projectName + "客户信息，请及时跟进处理。";
		return content;
	}
	
	/**
	 * 13:您提交的[变量(工程名称)]客户信息审核已通过，本次获得**积分，请登录艾臣开拓者联盟俱乐部查看积分详情。
	 * @param projectName
	 * @param integral
	 * @return
	 */
	public static String getSmsWhenPioneerVerifyClientInfo(String projectName, Integer integral){
		String content = "您提交的" + projectName + "客户信息审核已通过，本次获得" + integral + "积分。请登录艾臣开拓者联盟俱乐部查看积分详情。";
		return content;
	}
	
	/**
	 * 14:您提交的[变量(工程名称)]客户信息不属实，审核不通过，收到后请尽快登录平台并处理。
	 * @param args
	 */
	public static String getSmsWhenPioneerClientInfoImperfect(String projectName){
		String content = "您提交的" + projectName + "客户信息不属实，审核不通过，收到后请尽快登录平台并处理。";
		return content;
	}
	
	/**
	 * 15:艾臣开拓者联盟会员[会员名称变量(会员名称+手机号码)],于[日期变量]提交了[代理商名称变量]代理商信息，请及时跟进处理。
	 * @param args
	 */
	public static String getSmsWhenPioneerSubmitAgentInfo(String memberName, Date createTime, String agentName){
		String content = "艾臣开拓者联盟会员" + memberName + "，于" + DateUtil.format(createTime, "yyyy年MM月dd日 hh时ss分mm秒") + "提交了" + agentName + "代理商信息，请及时跟进处理。";
		return content;
	}
	
	/**
	 * 16:您提交的[代理商名称变量]代理商信息审核已通过，本次获得**积分，请登录艾臣开拓者联盟俱乐部查看积分详情。
	 * @param projectName
	 * @param integral
	 * @return
	 */
	public static String getSmsWhenPioneerVerifyAgentInfo(String agentName, Integer integral){
		String content = "您提交的" + agentName + "代理商信息审核已通过，本次获得" + integral + "积分。请登录艾臣开拓者联盟俱乐部查看积分详情。";
		return content;
	}
	
	/**
	 * 17:您提交的[代理商名称变量]代理商信息不属实，审核不通过，收到后请尽快登录平台并处理。
	 * @param args
	 */
	public static String getSmsWhenPioneerAgentInfoImperfect(String agentName){
		String content = "您提交的" + agentName + "代理商信息不属实，审核不通过，收到后请尽快登录平台并处理。";
		return content;
	}
	
	/**
	 * 18:您提交的[代理商名称变量]代理商已经签约合同，您本次获得**积分，**现金，请登录艾臣开拓者联盟俱乐部查看积分现金详情。
	 * @param args
	 */
	public static String getSmsWhenPioneerSignSuccess(String agentName, Integer integral, Float cash){
		String content = "您提交的" + agentName + "代理商已经签约合同，您本次获得" + integral + "积分，"+ cash +"现金。请登录艾臣开拓者联盟俱乐部查看积分详情。";
		return content;
	}
	
	/**
	 * 19:您提交的[代理商名称变量]代理商已经开业，您本次获得**积分，**现金，请登录艾臣开拓者联盟俱乐部查看积分现金详情。
	 * @param args
	 */
	public static String getSmsWhenPioneerOpenSuccess(String agentName, Integer integral, Float cash){
		String content = "您提交的" + agentName + "代理商已经开业，您本次获得" + integral + "积分，"+ cash +"现金。请登录艾臣开拓者联盟俱乐部查看积分详情。";
		return content;
	}
	
	/**
	 * 20:您的转账申请已经处理，人民币****元款项已经转至您尾号****的银行卡，请注意查收！再次感谢你对艾臣的信任与支持！
	 * @param args
	 */
	public static String getSmsWhenPioneerTransferSuccess(Float transferAmount, String bankNum){
		String content = "您的转账申请已经处理，人民币" + transferAmount + "元款项已经转至您尾号" + bankNum + "的银行卡，请注意查收！再次感谢你对艾臣的信任与支持！";
		return content;
	}
	
	/**
	 * 23:您的转账申请处理失败，不能将钱转入您尾号****的银行卡，请核对您的卡号信息；已经将钱退回您的钱包，请登录艾臣开拓者联盟俱乐部查看钱包详情！
	 * @param args
	 */
	public static String getSmsWhenPioneerTransferFail(String bankNum){
		String content = "您的转账申请处理失败，不能将钱转入您尾号" + bankNum + "的银行卡，请核对您的卡号信息；已经将钱退回您的钱包，请登录艾臣开拓者联盟俱乐部查看钱包详情！";
		return content;
	}
	
	/**
	 * 21:您提交的[代理商名称变量]代理商[日期变量]年销售额人民币***元，您本次获得回扣人民币***元，请登录艾臣开拓者联盟俱乐部查看钱包详情
	 * @param args
	 */
	public static String getSmsWhenPioneerRebates (String agentName, String year, Float salesAmount, Float rebatesAmount){
		String content = "您提交的" + agentName + "代理商" + year + "年销售额人民币" + salesAmount + "元，您本次获得回扣人民币" + rebatesAmount + "元，请登录艾臣开拓者联盟俱乐部查看钱包详情。";
		return content;
	}
	

	public static void main(String[] args) {
		System.out.println(SmsTemplate.getSmsWhenSubmitRequire("张三","官网",new Date(),"天朗明居"));
		System.out.println(SmsTemplate.getSmsWhenSubmitClientInfo("张三","官网",new Date(),"天朗明居"));
		System.out.println(SmsTemplate.getSmsWhenConfirmDesign("张三","官网",new Date(),"天朗明居"));
		System.out.println(SmsTemplate.getSmsWhenExchangeGift("奥迪A4",10000,"SBASDFADAF",new Date()));
		System.out.println(SmsTemplate.getSmsWhenSettle("天朗明居",100));
		System.out.println(SmsTemplate.getSmsWhenRequireImperfect("天朗明居"));
		System.out.println(SmsTemplate.getSmsWhenFinishDesign("天朗明居"));
		System.out.println(SmsTemplate.getSmsWhenClientInfoImperfect("天朗明居"));
		System.out.println(SmsTemplate.getSmsWhenVerifyClientInfo("天朗明居"));
		System.out.println(SmsTemplate.getSmsWhenValidCode("2234"));
		System.out.println(SmsTemplate.getSmsWhenVerifyClientInfo("天朗明居",20));
		
	}
	
}
