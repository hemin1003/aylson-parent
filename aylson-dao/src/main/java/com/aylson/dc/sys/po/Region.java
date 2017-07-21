package com.aylson.dc.sys.po;

import java.io.Serializable;

/**
 * 全国省市区数据
 * @author wwx
 *
 */
public class Region implements Serializable{

	private static final long serialVersionUID = -6358350407401881315L;
	
	private Integer regionId;       //地区ID
	private String regionCode;      //地区code代号
	private String regionName;      //区域名称
	private String regionLevel;     //区域等级
	private Integer parentId;       //父类ID
	private String languge;         //语言
	
	
	public Integer getRegionId() {
		return regionId;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	
	public String getRegionLevel() {
		return regionLevel;
	}
	public void setRegionLevel(String regionLevel) {
		this.regionLevel = regionLevel;
	}
	
	public String getLanguge() {
		return languge;
	}
	public void setLanguge(String languge) {
		this.languge = languge;
	}
	
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	
}
