package com.aylson.dc.sys.po;

import java.io.Serializable;

public class IntegralConfig implements Serializable{

	private static final long serialVersionUID = -5361621437519119573L;
	
	private Integer id;               //主键
	/**
	 * 类型：
	 * 设计师
	 * 1：注册赠送[我的推荐人]积分：
	 * 2：注册赠送[我的推荐人的推荐人]积分：
	 * 3：提交方案赠送[提交人]积分：
	 * 4：提交方案赠送[推荐人]积分：
	 * 5：结算方案赠送[提交人]积分：
	 * 6：结算方案赠送[推荐人]积分
	 * 产业工人
	 * 7：注册赠送[我的推荐人]积分：
	 * 8：注册赠送[我的推荐人的推荐人]积分：
	 * 9：提交方案赠送[提交人]积分：
	 * 10：提交方案赠送[推荐人]积分：
	 * 11:用于区分配置会员的积分等级范围以及分配的业主资料数
	 */
	private Integer type;             //类型：
	private Integer rangeBegin;       //范围开始        当type=11时表示：会员的积分等级范围开始
	private Integer rangeEnd;         //范围结束        当type=11时表示：会员的积分等级范围结束
	private Float rate;               //比率                当type=11时表示：分配的业主资料数
	private Integer integral;         //赠送积分         当type=11时表示：会员对应的积分
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getRangeBegin() {
		return rangeBegin;
	}
	public void setRangeBegin(Integer rangeBegin) {
		this.rangeBegin = rangeBegin;
	}
	
	public Integer getRangeEnd() {
		return rangeEnd;
	}
	public void setRangeEnd(Integer rangeEnd) {
		this.rangeEnd = rangeEnd;
	}
	
	public Float getRate() {
		return rate;
	}
	public void setRate(Float rate) {
		this.rate = rate;
	}
	
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	
	

}
