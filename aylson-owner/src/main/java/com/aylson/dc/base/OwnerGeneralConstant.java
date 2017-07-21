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
			AppointmentStateMap.put(CONFIRM_ORDER, "确认订单 ");
		}
		
	}
	
	/**
	 * 预约状态
	 * @author Administrator
	 *
	 */
	public abstract class AppointState {
		/*** 取消作废 */
		public static final int CANCEL = 0;
		/*** 预约处理 */
		public static final int APPOINT = 1;
		/*** 待设计报价*/
		public static final int WAIT_OFFER = 2;
		/*** 待确认*/
		public static final int WAIT_CONFIRM = 3;
		/*** 待重新设计报价*/
		public static final int WAIT_OFFER_AGAIN = 31;
		/*** 待下单*/
		public static final int WAIT_ORDER = 4;
		/*** 待总部确认*/
		public static final int WAIT_AYLSON_CONFIRM = 5;
		/*** 总部确认*/
		public static final int AYLSON_CONFIRM = 6;
		
		public static Map<Integer, String> AppointStateMap = new HashMap<Integer, String>();
		static {
			AppointStateMap.put(CANCEL, "作废");
			AppointStateMap.put(APPOINT, "预约处理");
			AppointStateMap.put(WAIT_OFFER, "待设计报价 ");
			AppointStateMap.put(WAIT_CONFIRM, "待客户确认 ");
			AppointStateMap.put(WAIT_OFFER_AGAIN, "待重新设计报价 ");
			AppointStateMap.put(WAIT_ORDER, "待下单 ");
			AppointStateMap.put(WAIT_AYLSON_CONFIRM, "待总部确认 ");
			AppointStateMap.put(AYLSON_CONFIRM, "总部已确认 ");
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
	 * 预约下单业务流程状态
	 * @author Administrator
	 *
	 */
	/*public abstract class AppointNodeState {
		*//*** 预约处理 *//*
		public static final int APPOINT = 1;
		*//*** 待设计报价*//*
		public static final int WAIT_OFFER = 2;
		*//*** 待确认*//*
		public static final int WAIT_CONFIRM = 3;
		*//*** 待重新设计报价*//*
		public static final int WAIT_OFFER_AGAIN = 31;
		*//*** 待下单*//*
		public static final int WAIT_ORDER = 4;
		*//*** 待总部确认*//*
		public static final int WAIT_AYLSON_CONFIRM = 5;
		*//*** 总部确认*//*
		public static final int AYLSON_CONFIRM = 6;
		
		public static Map<Integer, String> AppointNodeStateMap = new HashMap<Integer, String>();
		static {
			AppointNodeStateMap.put(APPOINT, "预约处理  ");
			AppointNodeStateMap.put(WAIT_OFFER, "待设计报价 ");
			AppointNodeStateMap.put(WAIT_CONFIRM, "待客户确认 ");
			AppointNodeStateMap.put(WAIT_OFFER_AGAIN, "待重新设计报价 ");
			AppointNodeStateMap.put(WAIT_ORDER, "待下单 ");
			AppointNodeStateMap.put(WAIT_AYLSON_CONFIRM, "待总部确认 ");
			AppointNodeStateMap.put(AYLSON_CONFIRM, "总部已确认 ");
		}
	}*/
	
	/**
	 * 流程节点来源类型
	 * @author Administrator
	 *
	 */
	/*public abstract class FlowNodeSourceType {
		*//*** 预约 *//*
		public static final int APPOINT = 1;
		*//*** 订单*//*
		public static final int ORDER = 2;
		
		public static Map<Integer, String> FlowNodeSourceTypeMap = new HashMap<Integer, String>();
		static {
			FlowNodeSourceTypeMap.put(APPOINT, "预约 ");
			FlowNodeSourceTypeMap.put(ORDER, "订单");
			
		}
	}*/
	
	/**
	 * 业务流程类型
	 * @author Administrator
	 *
	 */
	public abstract class FlowNodeType {
		/*** 预约下单流程 */
		public static final int APPOINT = 1;
		public static Map<Integer, String> FlowNodeTypeMap = new HashMap<Integer, String>();
		static {
			FlowNodeTypeMap.put(APPOINT, "预约下单流程 ");
		}
		
	}
	
	/**
	 * 费用类型
	 * @author Administrator
	 *
	 */
	public abstract class CostType {
		/*************** 经销商报价区 ********************/
		/*** 产品费 */
		public static final int PRODUCTCOST = 1;
		/*** 运输费 */
		public static final int FREIGHT = 2;
		/*** 安装费 */
		public static final int INSTALLATION_FEE = 3;
		/*** 现金券抵扣 */
		public static final int CASH_COUPON = 4;
		/*** 折扣 */
		public static final int DISCOUNT = 5;
		/*** 优惠券抵扣（公众号） */
		public static final int ACTIVITY_COUPON = 6;
		/**************** 总部报价区 *******************/
		/*** 出厂金额 */
		public static final int FACTORY_AMOUNT = 7;
		/*** 折扣 */
		public static final int ORG_DISCOUNT = 8;
		public static Map<Integer, String> CostTypeMap = new HashMap<Integer, String>();
		static {
			CostTypeMap.put(PRODUCTCOST, "产品费 ");
			CostTypeMap.put(FREIGHT, "运输费 ");
			CostTypeMap.put(INSTALLATION_FEE, "安装费 ");
			CostTypeMap.put(CASH_COUPON, "现金券抵扣");
			CostTypeMap.put(DISCOUNT, "折扣 ");
			CostTypeMap.put(ACTIVITY_COUPON, "优惠券抵扣（公众号） ");
			CostTypeMap.put(FACTORY_AMOUNT, "出厂金额 ");
			CostTypeMap.put(ORG_DISCOUNT, "折扣 ");
		}
		
	}
	
	/**
	 * 流程节点来源类型
	 * @author Administrator
	 *
	 */
	public abstract class CostSourceType {
		/*** 预约 */
		public static final int APPOINT = 1;
		
		public static Map<Integer, String> CostSourceTypeMap = new HashMap<Integer, String>();
		static {
			CostSourceTypeMap.put(APPOINT, "预约 ");
		}
	}
	
}
