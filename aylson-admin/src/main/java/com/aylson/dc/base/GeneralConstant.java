package com.aylson.dc.base;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author wwx
 * @since  2016-08
 * @version v1.0
 * 用于存放web模块常用变量，便于管理
 *
 */
public interface GeneralConstant {
	
	/**
	 * 方案设计项目状态
	 * @author wwx
	 *
	 */
	public abstract class ProjectStatus {
		/*** 待提交资料*/
		public static final int WAIT_SUMBIT_CLIENTINFO = 1;
		/*** 待审核 */
		public static final int WAIT_VERIFY = 2;
		/*** 已审核，待提交方案 */
		public static final int HAD_VERIFY = 3;
		/*** 已提交方案 ，待设计方案*/
		public static final int HAD_SUMBIT_PROGRAM = 4;
		/*** 已设计 ，待确认 */
		public static final int HAD_SUMBIT_DESIGN = 5;
		/*** 已确认 ，待结算 */
		public static final int HAD_SUMBIT_CONFIRM = 6;
		/*** 已评价*/
		public static final int HAD_EVALUATE  = 7;
		/*** 已结算*/
		public static final int HAD_SETTLE  = 8;
		/*** 审核不通过，待重新提交资料*/
		public static final int WAIT_RESUMBIT_CLIENTINFO  = 11;
		/*** 客户未确认，待重新提交方案*/
		public static final int WAIT_RESUMBIT_PROGRAM  = 31;
		/*** 方案需求审核不通过，待重新提交方案*/
		public static final int VERIFY_RESUMBIT_PROGRAM  = 32;
		/*** 设计不满意，待重新设计 */
		public static final int WAIT_RESUMBIT_DESIGN  = 51;
		
		public static Map<Integer, String> ProjectStatusMap = new HashMap<Integer, String>();
		static {
			ProjectStatusMap.put(WAIT_SUMBIT_CLIENTINFO, "待提交资料");
			ProjectStatusMap.put(WAIT_VERIFY, "待审核");
			ProjectStatusMap.put(HAD_VERIFY, "已审核，待提交方案");
			ProjectStatusMap.put(HAD_SUMBIT_PROGRAM, "已提交方案 ，待设计");
			ProjectStatusMap.put(HAD_SUMBIT_DESIGN, "已设计 ，待确认 ");
			ProjectStatusMap.put(HAD_SUMBIT_CONFIRM, "已确认 ，待结算");
			ProjectStatusMap.put(HAD_SETTLE, "已结算");
			ProjectStatusMap.put(WAIT_RESUMBIT_CLIENTINFO, "审核不通过，待重新提交资料");
			ProjectStatusMap.put(WAIT_RESUMBIT_PROGRAM, "客户未确认，待重新提交方案");
			ProjectStatusMap.put(VERIFY_RESUMBIT_PROGRAM, "方案需求审核不通过，待重新提交方案");
			ProjectStatusMap.put(WAIT_RESUMBIT_DESIGN, "设计不满意，待重新设计");
		}
	}
	
	/**
	 * 方案设计项目状态(合并)
	 * @author wwx
	 *
	 */
	public abstract class ProjectStatusMerge {
		/*** 待审核 */
		public static final String S1 = "s1";
		public static final String S2 = "s2";
		public static final String S3 = "s3";
		public static final String S4 = "s4";
		public static final String S5 = "s5";
		public static final String S6 = "s6";
		
		public static Map<String, String> ProjectStatusMergeMap = new HashMap<String, String>();
		static {
			ProjectStatusMergeMap.put(S1, "待审核");//1,2,11
			ProjectStatusMergeMap.put(S2, "待提交");//3,31,32
			ProjectStatusMergeMap.put(S3, "待设计");//4,51
			ProjectStatusMergeMap.put(S4, "待确认");//5
			ProjectStatusMergeMap.put(S5, "待结算");//6,7
			ProjectStatusMergeMap.put(S6, "已结算");//8
		}
	}
	
	/**
	 * 附件类型
	 * @author wwx
	 *
	 */
	public abstract class AttachmentType {
		/*** 客户信息附件  */
		public static final int OWNERINFO = 1;
		/*** 经销商附件  */
		public static final int AGENT = 2;
		/*** 总部附件  */
		public static final int ORG = 3;
		
		public static Map<Integer, String> AttachmentTypeMap = new HashMap<Integer, String>();
		static {
			AttachmentTypeMap.put(OWNERINFO, "客户信息附件");
			AttachmentTypeMap.put(AGENT, "经销商附件");
			AttachmentTypeMap.put(ORG, "总部附件");
		}
	}
	
	/**
	 * 来源表
	 * 注意：为了不影响旧数据，一但确定了值就不要轻易修改
	 * @author wwx
	 *
	 */
	public abstract class SourceTable {
		/*** 预约信息表  */
		public static final int OWNER_APPOINT = 1;
		/*** 订单信息表  */
		public static final int OWNER_ORDER = 2;
		/*** 客户信息表  */
		public static final int OWNER_INFO = 3;
		/*** 用户信息表  */
		public static final int SYS_USER = 4;
		
		public static Map<Integer, String> SourceTableMap = new HashMap<Integer, String>();
		static {
			SourceTableMap.put(OWNER_APPOINT, "来源预约信息表");
			SourceTableMap.put(OWNER_ORDER, "来源订单信息表");
			SourceTableMap.put(OWNER_INFO, "来源客户信息表");
			SourceTableMap.put(SYS_USER, "来源用户信息表");
		}
	}
	
	/**
	 * 发布管理类别
	 * @author wwx
	 *
	 */
	public abstract class PublishType {
		/*** 通知公告 */
		public static final int NOTICE = 1;
		/*** 新闻动态  */
		public static final int NEWS = 2;
		/*** 艾臣资讯  */
		public static final int AYLSONINFO = 3;
		
		public static Map<Integer, String> PublishTypeMap = new HashMap<Integer, String>();
		static {
			PublishTypeMap.put(NOTICE, "通知公告");
			PublishTypeMap.put(NEWS, "新闻动态");
			PublishTypeMap.put(AYLSONINFO, "艾臣资讯");
		}
	}
	
	/**
	 * 发布管理状态
	 * @author Administrator
	 *
	 */
	public abstract class PublishStatus {
		/*** 草稿 */
		public static final int DRAFT = 1;
		/*** 发布  */
		public static final int PUBLISH = 2;
		/*** 结束发布  */
		public static final int FINISH_PUBLISH = 3;
		
		public static Map<Integer, String> PublishStatusMap = new HashMap<Integer, String>();
		static {
			PublishStatusMap.put(DRAFT, "草稿");
			PublishStatusMap.put(PUBLISH, "发布");
			PublishStatusMap.put(FINISH_PUBLISH, "结束发布");
		}
	}
	
	/**
	 * 系统用户类型
	 * @author Administrator
	 *
	 */
	public abstract class UserType {
		/*** 机构用户 */
		public static final int ORG_USER = 1;
		/*** 代理商用户  */
		public static final int AGENT_USER = 2;
		
		public static Map<Integer, String> UserTypeMap = new HashMap<Integer, String>();
		static {
			UserTypeMap.put(ORG_USER, "机构用户");
			UserTypeMap.put(AGENT_USER, "代理商用户");
		}
	}
	
	/**
	 * 系统用户类型
	 * @author Administrator
	 *
	 */
	public abstract class UserStatus {
		/*** 禁用  */
		public static final int DISABLE = 0;
		/*** 启用 */
		public static final int ACTIVITY = 1;
		
		
		public static Map<Integer, String> UserStatusMap = new HashMap<Integer, String>();
		static {
			UserStatusMap.put(ACTIVITY, "启用");
			UserStatusMap.put(DISABLE, "禁用");
		}
	}
	
	/**
	 * 积分类型
	 * @author Administrator
	 *
	 */
	public abstract class IntegeralType {
		//出
		/*** 兑换礼品 */
		public static final int EXCHANGEGIFT = -1;
		//进
		/*** 注册赠送 */
		public static final int SEND_BY_REGISTER = 1;
		/*** 注册赠送我的推荐人 */
		public static final int SEND_BY_REGISTER_REFERRAL = 2;
		/*** 注册赠送我的推荐人的推荐人 */
		public static final int SEND_BY_REGISTER_REFERRAL_PARENT = 3;
		/*** 设计方案的客户资料审核通过赠送 */
		public static final int SEND_BY_VERIFY_PROJECTINFO = 4;
		/*** 设计方案的客户资料审核通过赠送給推荐人 */
		public static final int SEND_BY_VERIFY_PROJECTINFO_REFERRAL = 5;
		/*** 完成设计方案赠送会员 */
		public static final int SEND_BY_SETTLE = 6;
		/*** 完成设计方案赠送会员的推荐人 */
		public static final int SEND_BY_SETTLE_REFERRAL = 7;
		/*** 代理商资料审核通过赠送 */
		public static final int SEND_BY_VERIFY_AGENTINFO = 8;
		/*** 代理商资料审核通过赠送推荐人 */
		public static final int SEND_BY_VERIFY_AGENTINFO_REFERRAL = 9;
		/*** 代理商签约成功赠送 */
		public static final int SEND_BY_SIGN_AGENTINFO = 10;
		/*** 代理商开业成功赠送 */
		public static final int SEND_BY_OPEN_AGENTINFO = 11;
		/*** 代理商签约成功赠送推荐人 */
		public static final int SEND_BY_SIGN_REFERRAL = 12;
		/*** 代理商开业成功赠送推荐人 */
		public static final int SEND_BY_OPEN_REFERRAL = 13;
		
		
		public static Map<Integer, String> IntegeralTypeMap = new HashMap<Integer, String>();
		static {
			//-
			IntegeralTypeMap.put(EXCHANGEGIFT, "兑换礼品");
			//+
			IntegeralTypeMap.put(SEND_BY_REGISTER, "注册赠送");
			IntegeralTypeMap.put(SEND_BY_REGISTER_REFERRAL, "注册赠送我的推荐人");
			IntegeralTypeMap.put(SEND_BY_REGISTER_REFERRAL_PARENT, "注册赠送我的推荐人的推荐人");
			IntegeralTypeMap.put(SEND_BY_VERIFY_PROJECTINFO, "设计方案的客户资料审核通过赠送");
			IntegeralTypeMap.put(SEND_BY_VERIFY_PROJECTINFO_REFERRAL, "设计方案的客户资料审核通过赠送");
			IntegeralTypeMap.put(SEND_BY_SETTLE, "完成设计方案赠送会员");
			IntegeralTypeMap.put(SEND_BY_SETTLE_REFERRAL, "完成设计方案赠送会员的推荐人");
			IntegeralTypeMap.put(SEND_BY_VERIFY_AGENTINFO, "代理商资料审核通过赠送");
			IntegeralTypeMap.put(SEND_BY_VERIFY_AGENTINFO_REFERRAL, "代理商资料审核通过赠送推荐人");
			IntegeralTypeMap.put(SEND_BY_SIGN_AGENTINFO, "代理商签约成功赠送");
			IntegeralTypeMap.put(SEND_BY_OPEN_AGENTINFO, "代理商开业成功赠送");
			IntegeralTypeMap.put(SEND_BY_SIGN_REFERRAL, "代理商签约成功赠送推荐人");
			IntegeralTypeMap.put(SEND_BY_OPEN_REFERRAL, "代理商开业成功赠送推荐人");
		}
	}
	
	/**
	 * 钱包进出类型
	 * @author Administrator
	 *
	 */
	public abstract class WalletType {
		//出
		/*** 提现 */
		public static final int WITHDRAWALS = -1;
		//进
		/*** 签约赠送 */
		public static final int SEND_BY_SIGN = 1;
		/*** 提现失败返还 */
		public static final int WITHDRAWALS_FAIL = 2;
		/*** 开业赠送 */
		public static final int SEND_BY_OPEN = 3;
		/*** 代理商年终销售额回扣 */
		public static final int SEND_BY_SALE = 4;
		
		public static Map<Integer, String> WalletTypeMap = new HashMap<Integer, String>();
		static {
			//-
			WalletTypeMap.put(WITHDRAWALS, "提现");
			//+
			WalletTypeMap.put(SEND_BY_SIGN, "签约赠送");
			WalletTypeMap.put(WITHDRAWALS_FAIL, "提现失败返还");
			WalletTypeMap.put(SEND_BY_OPEN, "开业赠送");
			WalletTypeMap.put(SEND_BY_SALE, "代理商年终销售额回扣");
			
		}
	}
	
	public abstract class GoldType {
		//出
		/*** 提交工程方案需求 */
		public static final int SUBMIT_PROJECT_REQUIREMENT = -1;
		//进
		/*** 购买金币 */
		public static final int BUY = 1;
		/*** 注册赠送 */
		public static final int REGISTER = 2;
		
		public static Map<Integer, String> GoldTypeMap = new HashMap<Integer, String>();
		static {
			//-
			GoldTypeMap.put(SUBMIT_PROJECT_REQUIREMENT, "提交工程方案需求");
			//+
			GoldTypeMap.put(BUY, "购买金币");
			GoldTypeMap.put(REGISTER, "注册赠送");
		}
	}
	
	/**
	 * 积分或金币来源类型
	 * @author Administrator
	 *
	 */
	public abstract class SourceType {
		/*** 方案设计 mem_project_info */
		public static final int PROGRAMDESIGN = 1;
		/*** 礼品兑换 mem_exchange_gift_records */
		public static final int EXCHANGEGIFT = 2;
		/*** 会员注册 mem_account */
		public static final int MEMREGISTER = 3;
		/*** 充值金币 mem_exchange_records */
		public static final int EXCHANGEGOLD = 4;
		/*** 提交代理商资料  pioneer_agent */
		public static final int PIONEERAGENT = 5;
		/*** 钱包提现  mem_withdrawals_apply */
		public static final int WITHDRAWALS = 6;
		/*** 提交代理商资料  pioneer_agent_saleinfo */
		public static final int PIONEERAGENTSALES = 7;
		
		public static Map<Integer, String> SourceTypeMap = new HashMap<Integer, String>();
		static {
			SourceTypeMap.put(PROGRAMDESIGN, "方案设计");
			SourceTypeMap.put(EXCHANGEGIFT, "礼品兑换");
			SourceTypeMap.put(MEMREGISTER, "会员注册 ");
			SourceTypeMap.put(EXCHANGEGOLD, "充值金币 ");
			SourceTypeMap.put(PIONEERAGENT, "提交代理商资料 ");
			SourceTypeMap.put(WITHDRAWALS, "钱包提现 ");
			SourceTypeMap.put(PIONEERAGENTSALES, "销售明细");
		}
	}
	
	/**
	 * 礼品配置状态
	 * @author Administrator
	 *
	 */
	public abstract class GiftConfigStatus {
		/*** 新建 */
		public static final int NEW = 1;
		/*** 上架  */
		public static final int UP = 2;
		/*** 下架  */
		public static final int DOWN = 3;
		
		public static Map<Integer, String> GiftConfigStatusMap = new HashMap<Integer, String>();
		static {
			GiftConfigStatusMap.put(NEW, "新建");
			GiftConfigStatusMap.put(UP, "上架");
			GiftConfigStatusMap.put(DOWN, "下架");
		}
	}
	
	/**
	 * 积分配置类型
	 * @author Administrator
	 *
	 */
	public abstract class IntegralConfigType {
		/*** （设计师）注册赠送[我的推荐人]积分 */
		public static final int D_REGISTER_TO_REFERRAL = 1;
		/*** （设计师）注册赠送[我的推荐人的推荐人]积分  */
		public static final int D_REGISTER_TO_REFERRAL_PARENT = 2;
		/*** （设计师）提交方案赠送[提交人]积分  */
		public static final int D_SUBMIT = 3;
		/*** （设计师）提交方案赠送[推荐人]积分  */
		public static final int D_SUBMIT_TO_REFERRAL = 4;
		/*** （设计师）结算方案赠送[提交人]积分  */
		public static final int D_SETTLE = 5;
		/*** （设计师）结算方案赠送[推荐人]积分  */
		public static final int D_SETTLE_TO_REFERRAL = 6;
		
		/*** （产业工人）注册赠送[我的推荐人]积分 */
		public static final int W_REGISTER_TO_REFERRAL = 7;
		/*** （产业工人）注册赠送[我的推荐人的推荐人]积分  */
		public static final int W_REGISTER_TO_REFERRAL_PARENT = 8;
		/*** （产业工人）提交方案赠送[提交人]积分  */
		public static final int W_SUBMIT = 9;
		/*** （产业工人）提交方案赠送[推荐人]积分  */
		public static final int W_SUBMIT_TO_REFERRAL = 10;
		
		/**用于区分配置会员的积分等级范围以及分配的业主资料数*/
		public static final int INTEGRAL_LEVEL = 11;
		/**业主资料最多分配给几个设计师配置类型*/
		public static final int CLIENTINFO_LIMIT = 12;
		
		/*** （开拓者）注册赠送[我的推荐人]积分 */
		public static final int P_REGISTER_TO_REFERRAL = 13;
		/*** （开拓者）注册赠送[我的推荐人的推荐人]积分  */
		public static final int P_REGISTER_TO_REFERRAL_PARENT = 14;
		/*** （开拓者）提交客户资料赠送[提交人]积分  */
		public static final int P_SUBMITCLIENTINFO = 15;
		/*** （开拓者）提交客户资料赠送[推荐人]积分  */
		public static final int P_SUBMITCLIENTINFO_TO_REFERRAL = 16;
		/*** （开拓者）提交代理商资料赠送[提交人]积分  */
		public static final int P_SUBMITAGENTINFO = 17;
		/*** （开拓者）提交代理商资料赠送[推荐人]积分  */
		public static final int P_SUBMITAGENTINFO_TO_REFERRAL = 18;
		/*** （开拓者）代理商签约赠送[邀请人]积分和现金-独立介绍方式  */
		public static final int P_SIGN_TO_REFERRAL = 19;
		/*** （开拓者）代理商开业赠送[邀请人]积分和现金  */
		public static final int P_OPEN_TO_REFERRAL = 20;
		/*** （开拓者）已加盟代理商年销售额给[邀请人]回扣  */
		public static final int P_REBATE_TO_REFERRAL = 21;
		/*** （开拓者）代理商签约赠送[邀请人]积分和现金-非独立介绍方式  */
		public static final int P_SIGN_NOTSINGLE_TO_REFERRAL = 22;
		/*** （开拓者）代理商签约赠送[邀请人的推荐人]积分 */
		public static final int P_SIGN_TO_REFERRAL_PARENT = 23;
		/*** （开拓者）代理商开业赠送[邀请人的推荐人]积分  */
		public static final int P_OPEN_TO_REFERRAL_PARENT = 24;
		/*** （开拓者）代理商开业赠送[邀请人]积分和现金-非独立介绍方式  */
		public static final int P_OPEN_NOTSINGLE_TO_REFERRAL = 25;
		
	}
	
	/**
	 * 文章发布渠道
	 * @author Administrator
	 *
	 */
	public abstract class ArticlePublishChannel {
		/*** 官网 */
		public static final int WEB = 1;
		/*** 微信公众号 */
		public static final int WX = 2;
		
	}
	
	/**
	 * 支付的业务类型
	 * @author Administrator
	 *
	 */
	public abstract class PayBusiType {
		/*** 充值 */
		public static final int CHARGE = 1;
		/*** 退款 */
		public static final int REFUND = 2;
		
		public static Map<Integer, String> PayBusiTypeMap = new HashMap<Integer, String>();
		static {
			PayBusiTypeMap.put(CHARGE, "充值");
			PayBusiTypeMap.put(REFUND, "退款");
		}
		
	}
	
	/**
	 * 支付的状态
	 * @author Administrator
	 *
	 */
	public abstract class PayStatus {
		/*** 已提交 */
		public static final int HAD_SUMBIT = 0;
		/*** 支付完成 */
		public static final int CHARGE_FINISH = 1;
		/*** 支付失败 */
		public static final int CHARGE_FAIL = 2;
		/*** 已退款 */
		public static final int REFUND = 3;
		
		public static Map<Integer, String> PayStatusMap = new HashMap<Integer, String>();
		static {
			PayStatusMap.put(HAD_SUMBIT, "已提交");
			PayStatusMap.put(CHARGE_FINISH, "支付完成");
			PayStatusMap.put(CHARGE_FAIL, "支付失败");
			PayStatusMap.put(REFUND, "已退款");
		}
		
	}
	
	
	/**
	 * 充值配置状态
	 * @author Administrator
	 *
	 */
	public abstract class RechargeConfigStatus {
		/*** 新建 */
		public static final int NEW = 1;
		/*** 上架  */
		public static final int UP = 2;
		/*** 下架  */
		public static final int DOWN = 3;
		
		public static Map<Integer, String> RechargeConfigStatusMap = new HashMap<Integer, String>();
		static {
			RechargeConfigStatusMap.put(NEW, "新建");
			RechargeConfigStatusMap.put(UP, "上架");
			RechargeConfigStatusMap.put(DOWN, "下架");
		}
	}
	
	/**
	 * 会员类型
	 * @author Administrator
	 *
	 */
	public abstract class MemberType {
		/*** 设计师 */
		public static final int DESIGN = 1;
		/*** 产业工人  */
		public static final int WORK = 2;
		/*** 开拓者  */
		public static final int PIONEER = 3;
		/*** 业主客户  */
		public static final int OWNER = 4;
		/*** 艾臣合伙人  */
		public static final int PARTNER = 5;
		
		public static Map<Integer, String> MemberTypeMap = new HashMap<Integer, String>();
		static {
			MemberTypeMap.put(DESIGN, "设计师");
			MemberTypeMap.put(WORK, "产业工人");
			MemberTypeMap.put(PIONEER, "开拓者");
			MemberTypeMap.put(OWNER, "业主客户");
			MemberTypeMap.put(PARTNER, "艾臣合伙人");
		}
	}
	
	public abstract class DeviceType {
		/*** 服务端 */
		public static final int SERVER = 1;
		/*** web端  */
		public static final int WEB = 2;
		/*** 微信端  */
		public static final int WEIXIN = 3;
		
		public static Map<Integer, String> DeviceTypeMap = new HashMap<Integer, String>();
		static {
			DeviceTypeMap.put(SERVER, "服务端 ");
			DeviceTypeMap.put(WEB, "web端");
			DeviceTypeMap.put(WEIXIN, "微信端 ");
		}
	}
	
	/**
	 * 会员资料分配状态
	 * @author Administrator
	 *
	 */
	public abstract class MemClientStatus {
		/*** 已分配 */
		public static final int ASSIGNED = 1;
		/*** 已添加  */
		public static final int ADDED = 2;
		/*** 已忽略  */
		public static final int IGNORED = 3;
		
		public static Map<Integer, String> MemClientStatusMap = new HashMap<Integer, String>();
		static {
			MemClientStatusMap.put(ASSIGNED, "已分配  ");
			MemClientStatusMap.put(ADDED, "已添加");
			MemClientStatusMap.put(IGNORED, "已忽略 ");
		}
	}
	
	/**
	 * 分享二维码的渠道
	 * @author Administrator
	 *
	 */
	public abstract class QRCodeChannel {
		/*** 代理商分享 */
		public static final String AGENT = "agent";
		/*** 会员分享 */
		public static final String MEMBER = "member";
		/*** 开拓者分享 */
		public static final String PIONEER = "pioneer";
		
	}
	
	/**
	 * 代理商资料状态
	 * @author Administrator
	 *
	 */
	public abstract class PioneerAgentStatus {
		/*** 待审核 */
		public static final int WAIT_AUDIT = 1;
		/*** 已审核 */
		public static final int HAD_AUDIT = 2;
		/*** 审核不通过*/
		public static final int FAIL_AUDIT = 21;
		/*** 已签约  */
		public static final int HAD_SIGN = 3;
		/*** 签约失败  */
		public static final int FAIL_SIGN = 31;
		/*** 已开业  */
		public static final int HAD_OPEN = 4;
		/*** 开业失败  */
		public static final int FAIL_OPEN = 41;
		
		public static Map<Integer, String> PioneerAgentStatusMap = new HashMap<Integer, String>();
		static {
			PioneerAgentStatusMap.put(WAIT_AUDIT, "待审核  ");
			PioneerAgentStatusMap.put(HAD_AUDIT, "已审核");
			PioneerAgentStatusMap.put(HAD_SIGN, "已签约 ");
			PioneerAgentStatusMap.put(HAD_OPEN, "已开业 ");
			PioneerAgentStatusMap.put(FAIL_AUDIT, "审核不通过 ");
			PioneerAgentStatusMap.put(FAIL_SIGN, "签约失败 ");
			PioneerAgentStatusMap.put(FAIL_OPEN, "开业失败 ");
		}
		
	}
	
	/**
	 * 提现申请状态
	 * @author Administrator
	 *
	 */
	public abstract class WithdrawalsApplyStatus {
		/*** 处理中 */
		public static final int DEALING = 1;
		/*** 已转账*/
		public static final int HAD_DEAL = 2;
		/*** 转账失败*/
		public static final int FAIL_DEAL = 3;
		
		public static Map<Integer, String> WithdrawalsApplyStatusMap = new HashMap<Integer, String>();
		static {
			WithdrawalsApplyStatusMap.put(DEALING, "处理中  ");
			WithdrawalsApplyStatusMap.put(HAD_DEAL, "已转账");
			WithdrawalsApplyStatusMap.put(FAIL_DEAL, "转账失败 ");
		}
		
	}
	
	/**
	 * 礼品适用对象类型
	 * @author Administrator
	 *
	 */
	public abstract class MatchObjType {
		/*** 设计师 */
		public static final int DESIGN = 1;
		/*** 产业工人  */
		public static final int WORK = 2;
		/*** 开拓者  */
		public static final int PIONEER = 3;
		/*** 业主客户  */
		public static final int OWNER = 4;
		/*** 活动参与人  */
		public static final int ACTIVITY = 10;
		
		public static Map<Integer, String> MatchObjTypeMap = new HashMap<Integer, String>();
		static {
			MatchObjTypeMap.put(DESIGN, "设计师");
			MatchObjTypeMap.put(WORK, "产业工人");
			MatchObjTypeMap.put(PIONEER, "开拓者");
			MatchObjTypeMap.put(OWNER, "业主客户");
			MatchObjTypeMap.put(ACTIVITY, "活动参与人");
		}
	}
	
	
}
