package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class RegionSearch extends BaseSearch{

	private static final long serialVersionUID = 3709522526123493237L;
	
	//匹配查询
	private Integer regionId;       //地区ID
	private String regionLevel;     //区域等级
	private String regionName;      //区域名称
	private Integer parentId;       //父类ID
	//模糊查询
	private String regionNameLike;  //区域名称
	
	public Integer getRegionId() {
		return regionId;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	
	public String getRegionLevel() {
		return regionLevel;
	}
	public void setRegionLevel(String regionLevel) {
		this.regionLevel = regionLevel;
	}
	
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public String getRegionNameLike() {
		return regionNameLike;
	}
	public void setRegionNameLike(String regionNameLike) {
		this.regionNameLike = regionNameLike;
	}
	
	
}
