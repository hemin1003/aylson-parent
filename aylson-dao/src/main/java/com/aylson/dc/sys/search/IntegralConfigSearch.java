package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class IntegralConfigSearch extends BaseSearch {

	private static final long serialVersionUID = 8632083413806125191L;

	private Integer id;                             //主键
	/**
	 * 类型：
	 * 1：注册赠送[我的推荐人]积分：
	 * 2：注册赠送[我的推荐人的推荐人]积分：
	 * 3：提交方案赠送[提交人]积分：
	 * 4：提交方案赠送[推荐人]积分：
	 * 5：结算方案赠送[提交人]积分：
	 * 6：结算方案赠送[推荐人]积分
	 */
	private Integer type;                            //类型：
	private Integer rangeBegin;                      //范围开始
	private Integer rangeEnd;                        //范围结束
	private Boolean isIntegralLevelConfig;           //是否积分等级
	private Integer bigType;                         //大类：0 产业工人  1设计师  2开拓者 3积分等级
	
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
	
	public Boolean getIsIntegralLevelConfig() {
		return isIntegralLevelConfig;
	}
	public void setIsIntegralLevelConfig(Boolean isIntegralLevelConfig) {
		this.isIntegralLevelConfig = isIntegralLevelConfig;
	}
	
	public Integer getBigType() {
		return bigType;
	}
	public void setBigType(Integer bigType) {
		this.bigType = bigType;
	}
	
	
}
