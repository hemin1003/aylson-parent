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
	 * 合伙人账号状态
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
	 * 钱包账单明细类型
	 * @author Administrator
	 *
	 */
	public abstract class WalletBillType {
		/*** 奖励分红  */
		public static final int SEND_BONUS = 1;
		/*** 提取分红 */
		public static final int EXCHANGE_BONUS = -1;
		
		public static Map<Integer, String> WalletBillTypeMap = new HashMap<Integer, String>();
		static {
			WalletBillTypeMap.put(SEND_BONUS, "奖励分红");
			WalletBillTypeMap.put(EXCHANGE_BONUS, "提取分红 ");
		}
		
	}
	
	/**
	 * 钱包账单明细来源类型
	 * @author Administrator
	 *
	 */
	/*public abstract class WalletBillSourceType {
		*//*** 订单  *//*
		public static final int ORDER = 1;
		*//*** 订单  *//*
		public static final int USER = 2;
		
		public static Map<Integer, String> WalletBillSourceTypeMap = new HashMap<Integer, String>();
		static {
			WalletBillSourceTypeMap.put(ORDER, "订单");
			WalletBillSourceTypeMap.put(USER, "用户");
		}
		
	}*/
	
	/**
	 * 钱包账单明细来源类型
	 * @author Administrator
	 *
	 */
	public abstract class CouponState {
		/*** 待使用  */
		public static final int WAIT_USE = 0;
		/*** 已使用  */
		public static final int HAD_USE = 1;
		/*** 已失效  */
		public static final int CANT_USE = 2;
		
		public static Map<Integer, String> CouponStateMap = new HashMap<Integer, String>();
		static {
			CouponStateMap.put(WAIT_USE, "待使用");
			CouponStateMap.put(HAD_USE, "已使用");
			CouponStateMap.put(CANT_USE, "已失效");
		}
		
	}
	
	
}
