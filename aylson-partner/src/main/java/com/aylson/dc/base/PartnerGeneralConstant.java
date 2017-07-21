package com.aylson.dc.base;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author wwx
 * @since  2016-12
 * @version v1.0
 * 艾臣合伙人变量管理
 *
 */
public interface PartnerGeneralConstant {
	
	/**
	 * 行业类型
	 * @author Administrator
	 *
	 */
	public abstract class IndustryType {
		/*** 其他   */
		public static final int OTHER = 10;
		/*** 设计师   */
		public static final int DESIGN = 1;
		/*** 工长 */
		public static final int PIONEER = 2;
		/*** 建筑 */
		public static final int ARCHITECTURE = 3;
		/*** 房产中介 */
		public static final int ROOMAGENCY = 4;
		/*** 售楼员 */
		public static final int ROOMSALES = 5;
		
		public static Map<Integer, String> IndustryTypeMap = new HashMap<Integer, String>();
		static {
			IndustryTypeMap.put(DESIGN, "设计师");
			IndustryTypeMap.put(PIONEER, "工长 ");
			IndustryTypeMap.put(ARCHITECTURE, "建筑");
			IndustryTypeMap.put(ROOMAGENCY, "房产中介");
			IndustryTypeMap.put(ROOMSALES, "售楼员 ");
			IndustryTypeMap.put(OTHER, "其他 ");
		}
		
	}
	
	/**
	 * 合伙人账号状态
	 * @author Administrator
	 *
	 */
	public abstract class AccountState {
		/*** 申请中...   */
		public static final int APPLING = 0;
		/*** 已通过   */
		public static final int PASS = 1;
		/*** 未通过 */
		public static final int NOTPASS = 2;
		
		public static Map<Integer, String> AccountStateMap = new HashMap<Integer, String>();
		static {
			AccountStateMap.put(APPLING, "申请中...");
			AccountStateMap.put(PASS, "已通过 ");
			AccountStateMap.put(NOTPASS, "未通过");
		}
		
	}
	
	/**
	 * 优惠券申请状态
	 * @author Administrator
	 *
	 */
	public abstract class CouponApplyState {
		/*** 申请中...   */
		public static final int APPLING = 0;
		/*** 已通过   */
		public static final int PASS = 1;
		/*** 未通过 */
		public static final int NOTPASS = 2;
		
		public static Map<Integer, String> CouponApplyStateMap = new HashMap<Integer, String>();
		static {
			CouponApplyStateMap.put(APPLING, "申请中...");
			CouponApplyStateMap.put(PASS, "已通过 ");
			CouponApplyStateMap.put(NOTPASS, "未通过");
		}
		
	}
	
	/**
	 * 反馈状态
	 * @author Administrator
	 *
	 */
	public abstract class FeedBackState {
		/*** 待回复  */
		public static final int WAIT_REPLY = 1;
		/*** 已回答*/
		public static final int HAD_REPLY = 2;
		/*** 已查看*/
		public static final int HAD_VIEW = 3;
		/*** 反馈人已查看*/
		public static final int FEEDBACKER_HAD_VIEW = 4;
		
		public static Map<Integer, String> DesignTypeMap = new HashMap<Integer, String>();
		static {
			DesignTypeMap.put(WAIT_REPLY, "待回复 ");
			DesignTypeMap.put(HAD_REPLY, "已回答");
			DesignTypeMap.put(HAD_VIEW, "已查看 ");
			DesignTypeMap.put(FEEDBACKER_HAD_VIEW, "反馈人已查看 ");
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
	
	
}
