package com.aylson.core.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author wwx
 * @since  2016-05
 * @version v1.0
 * 用于存放系统常用变量，便于管理
 *
 */
public interface SysConstant {
	
	/**
	 * 角色类型
	 * 
	 */
	public abstract class RoleType {
		/**
		 * 通用
		 */
		public static final int ROLETYPE_COMMON = 0;
		/**
		 * 匹配
		 */
		public static final int ROLETYPE_MATCH = 1;
		
		public static Map<Integer, String> RoleTypeMap = new HashMap<Integer, String>();
		static {
			RoleTypeMap.put(ROLETYPE_MATCH, "匹配");
			RoleTypeMap.put(ROLETYPE_COMMON, "通用");
		}
	}
	
	/**
	 * 流水单号前缀
	 * @author Administrator
	 *
	 */
	public abstract class BillCodePrefix {
		/*** 预约单*/
		public static final String APPOINTMENT = "AD";
		/*** 预约设计单 */
		public static final String DESIGN = "ADD";
		/*** 预约报价订货单 */
		public static final String QUOTATION = "AQD";
		/*** 订货单 */
		public static final String ORDER = "DD";
		/*** 兑换礼品 */
		public static final String EXCHANGEGIFT = "EG";
		
	}
	
	
}
