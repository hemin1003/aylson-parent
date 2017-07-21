package com.aylson.dc.base;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 * @author wwx
 * @since  2016-12
 * @version v1.0
 * 用于存放web-业主模块常用变量，便于管理
 *
 */
public interface OwnerGeneralConstant {
	
	/**
	 * 预约状态
	 * @author Administrator
	 *
	 */
	public abstract class AppointmentState {
		/*** 新建 */
		public static final int NEW = 1;
		/*** 已指派*/
		public static final int HAD_ASSIGN = 2;
		/*** 待添加设计信息表*/
		public static final int WAIT_DESIGN = 3;
		/*** 待设计大样图*/
		public static final int WAIT_DRAW = 4;
		/*** 待重新设计大样图*/
		public static final int WAIT_DRAW_AGAIN = 41;
		/*** 待确认大样图*/
		public static final int WAIT_CONFIRM_DRAW = 5;
		/*** 待重新确认大样图*/
		public static final int WAIT_CONFIRM_DRAW_AGAIN = 51;
		/*** 待aylson报价*/
		public static final int WAIT_AYLSON_QUOTE = 6;
		/*** aylson 重新报价中*/
		public static final int WAIT_AYLSON_QUOTE_AGAIN = 61;
		/*** 代理商报价中*/
		public static final int WAIT_AGENT_QUOTE = 7;
		/*** 代理商重新报价中*/
		public static final int WAIT_AGENT_QUOTE_AGAIN = 71;
		/*** 客户确认中*/
		public static final int WAIT_CLIENT_CONFIRM = 8;
		/*** 满意报价*/
		public static final int SATISFY_QUOTE = 9;
		/*** 不满意报价*/
		public static final int NOTSATISFY_QUOTE = 91;
		/*** 确认订单*/
		public static final int CONFIRM_ORDER = 10;
		
		public static Map<Integer, String> AppointmentStateMap = new HashMap<Integer, String>();
		static {
			AppointmentStateMap.put(NEW, "新建 ");
			AppointmentStateMap.put(HAD_ASSIGN, "已指派");
			AppointmentStateMap.put(WAIT_DESIGN, "待添加设计信息表 ");
			AppointmentStateMap.put(WAIT_DRAW, "待设计大样图 ");
			AppointmentStateMap.put(WAIT_DRAW_AGAIN, "待重新设计大样图 ");
			AppointmentStateMap.put(WAIT_CONFIRM_DRAW, "待确认大样图 ");
			AppointmentStateMap.put(WAIT_CONFIRM_DRAW_AGAIN, "待重新确认大样图 ");
			AppointmentStateMap.put(WAIT_AYLSON_QUOTE, "待aylson报价");
			AppointmentStateMap.put(WAIT_AYLSON_QUOTE_AGAIN, "aylson 重新报价中 ");
			AppointmentStateMap.put(WAIT_AGENT_QUOTE, "代理商报价中 ");
			AppointmentStateMap.put(WAIT_AGENT_QUOTE_AGAIN, "代理商重新报价中 ");
			AppointmentStateMap.put(WAIT_CLIENT_CONFIRM, "客户确认中 ");
			AppointmentStateMap.put(SATISFY_QUOTE, "满意报价 ");
			AppointmentStateMap.put(NOTSATISFY_QUOTE, "不满意报价 ");
			AppointmentStateMap.put(CONFIRM_ORDER, "确认订单");
		}
		
	}
	
	/**
	 * 设计信息表状态
	 * @author Administrator
	 *
	 */
	public abstract class DesignState {
		/*** 新建 */
		public static final int NEW = 1;
		/*** 已确定*/
		public static final int CONFRIMED = 2;
		/*** 待上传大样图*/
		public static final int WAIT_DRAW = 3;
		/*** 待重新上传大样图*/
		public static final int WAIT_DRAW_AGAIN = 31;
		/*** 已上传大样图*/
		public static final int HAD_DRAW = 4;
		/*** 已重新上传大样图*/
		public static final int HAD_DRAW_AGAIN = 41;
		/*** 待确认大样图*/
		public static final int WAIT_CONFIRM_DRAW = 5;
		/*** 待重新确认大样图*/
		public static final int WAIT_CONFIRM_DRAW_AGAIN = 51;
		/*** 满意大样图*/
		public static final int SATISFY_DRAW = 6;
		/*** 不满意大样图*/
		public static final int NOTSATISFY_DRAW = 61;
		/*** aylson已报价*/
		public static final int AYLSON_HAD_QUOTE = 7;
		/*** aylson已重新报价*/
		public static final int AYLSON_HAD_QUOTE_AGAIN = 71;
		/*** 代理商已报价*/
		public static final int AGENT_HAD_QUOTE = 8;
		/*** 代理商已重新报价*/
		public static final int AGENT_HAD_QUOTE_AGAIN = 81;
		/*** 满意报价*/
		public static final int SATISFY_QUOTE = 9;
		/*** 不满意报价*/
		public static final int NOTSATISFY_QUOTE = 91;
		/*** 确认订单*/
		public static final int CONFIRM_ORDER = 10;
		
		public static Map<Integer, String> DesignStateMap = new HashMap<Integer, String>();
		static {
			DesignStateMap.put(NEW, "新建 ");
			DesignStateMap.put(CONFRIMED, "已确定");
			DesignStateMap.put(WAIT_DRAW, "待上传大样图 ");
			DesignStateMap.put(HAD_DRAW, "已上传大样图 ");
			DesignStateMap.put(HAD_DRAW_AGAIN, "已重新上传大样图 ");
			DesignStateMap.put(WAIT_CONFIRM_DRAW, "待确认大样图 ");
			DesignStateMap.put(WAIT_CONFIRM_DRAW_AGAIN, "待重新确认大样图 ");
			DesignStateMap.put(SATISFY_DRAW, "满意大样图 ");
			DesignStateMap.put(NOTSATISFY_DRAW, "不满意大样图 ");
			DesignStateMap.put(AYLSON_HAD_QUOTE, " aylson已报价 ");
			DesignStateMap.put(AYLSON_HAD_QUOTE_AGAIN, " aylson已重新报价 ");
			DesignStateMap.put(AGENT_HAD_QUOTE, "代理商已报价 ");
			DesignStateMap.put(AGENT_HAD_QUOTE_AGAIN, "代理商已重新报价 ");
			DesignStateMap.put(SATISFY_QUOTE, "满意报价 ");
			DesignStateMap.put(NOTSATISFY_QUOTE, "不满意报价 ");
			DesignStateMap.put(CONFIRM_ORDER, "确认订单 ");
		}
		
	}
	
	/**
	 * 设计类型
	 * @author Administrator
	 *
	 */
	public abstract class DesignType {
		/*** 门 */
		public static final int DOOR = 1;
		/*** 窗*/
		public static final int WINDOW = 2;
		/*** 阳光房*/
		public static final int SUMROOM = 3;
		
		public static Map<Integer, String> DesignTypeMap = new HashMap<Integer, String>();
		static {
			DesignTypeMap.put(DOOR, "门 ");
			DesignTypeMap.put(WINDOW, "窗");
			DesignTypeMap.put(SUMROOM, "阳光房 ");
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
	 * 反馈状态
	 * @author Administrator
	 *
	 */
	public abstract class OrderScheduleState {
		/*** 确认订单   */
		public static final int CONFIRM = 1;
		/*** 生产中*/
		public static final int PRODUCTIONING = 2;
		/*** 产品入库*/
		public static final int STOREING = 3;
		/*** 已发货*/
		public static final int SEND = 4;
		/*** 已收货*/
		public static final int RECEIPT = 5;
		
		public static Map<Integer, String> OrderScheduleStateMap = new HashMap<Integer, String>();
		static {
			OrderScheduleStateMap.put(CONFIRM, "确认订单 ");
			OrderScheduleStateMap.put(PRODUCTIONING, "生产中");
			OrderScheduleStateMap.put(STOREING, "产品入库 ");
			OrderScheduleStateMap.put(SEND, "已发货 ");
			OrderScheduleStateMap.put(RECEIPT, "已收货 ");
		}
		
	}
	
	
}
