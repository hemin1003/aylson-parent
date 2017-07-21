package com.aylson.dc.owner.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class OwnerInfo implements Serializable{

	
	private static final long serialVersionUID = 2986252095158012010L;
	
	private Integer id;                      //主键
	private String name;                     //客户姓名
	private String mobilePhone;              //客户电话
	private String decorateProject;          //装修项目，以，隔开
	private String province;                 //省会
	private Integer provinceId;              //省会id
	private String city;                     //城市
	private Integer cityId;                  //城市id
	private String area;                     //区域
	private Integer areaId;                  //区域id
	private String address;                  //详细地址
	private String remark;                   //备注
	private String belongIds;                //客户归属id集合，以，隔开
	private String belongNames;              //客户归属集合，以，隔开
	private Integer type;                    //用户类型
	private Integer createrId;               //创建人用户id
	private Date createTime;                 //创建时间
	private Date updateTime;                 //更新时间
	private Integer sourceType;              //客户来源：1 预约订单  2 后台新增
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	public String getDecorateProject() {
		return decorateProject;
	}
	public void setDecorateProject(String decorateProject) {
		this.decorateProject = decorateProject;
	}
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getBelongIds() {
		return belongIds;
	}
	public void setBelongIds(String belongIds) {
		this.belongIds = belongIds;
	}
	
	public String getBelongNames() {
		return belongNames;
	}
	public void setBelongNames(String belongNames) {
		this.belongNames = belongNames;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Integer getSourceType() {
		return sourceType;
	}
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	
	
}
