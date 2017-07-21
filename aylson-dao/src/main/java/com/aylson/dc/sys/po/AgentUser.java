package com.aylson.dc.sys.po;

import java.io.Serializable;
import java.util.Date;

import com.aylson.utils.JsonDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class AgentUser implements Serializable {

	private static final long serialVersionUID = 3834330871259331694L;
	
	private Integer id;                //主键
	private Integer userId;            //用户id
	private String agentName;          //代理商名称
	private String agentCode;          //代理商编号
	private String province;           //省会
	private Integer provinceId;        //省会id
	private String city;               //城市
	private Integer cityId;            //城市id
	private String area;               //区域
	private Integer areaId;            //区域id
	private String contacter;          //联系人
	private String contactPhone;       //联系电话
	private Boolean sex;               //性别
	private Date birthday;             //出生日期
	private String certificateNo;      //证件号
	private String address;            //地址
	private String products;           //代理产品
	private String productIds;         //代理产品ids
	private Date createTime;           //创建时间
	private String wxQrcodeTicket;     //设计师推广二维码
	private Boolean isAgent;           //是否代理商：false:直营    true:非直营
	private String wxOwnQrcodeTicket;  //业主公众号推广二维码
	private String agentDesc;          //代理商信息描述
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
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
	
	public String getContacter() {
		return contacter;
	}
	public void setContacter(String contacter) {
		this.contacter = contacter;
	}
	
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	public Boolean getSex() {
		return sex;
	}
	public void setSex(Boolean sex) {
		this.sex = sex;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getProducts() {
		return products;
	}
	public void setProducts(String products) {
		this.products = products;
	}
	
	public String getProductIds() {
		return productIds;
	}
	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}
	
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getWxQrcodeTicket() {
		return wxQrcodeTicket;
	}
	public void setWxQrcodeTicket(String wxQrcodeTicket) {
		this.wxQrcodeTicket = wxQrcodeTicket;
	}
	
	public Boolean getIsAgent() {
		return isAgent;
	}
	public void setIsAgent(Boolean isAgent) {
		this.isAgent = isAgent;
	}
	
	public String getWxOwnQrcodeTicket() {
		return wxOwnQrcodeTicket;
	}
	public void setWxOwnQrcodeTicket(String wxOwnQrcodeTicket) {
		this.wxOwnQrcodeTicket = wxOwnQrcodeTicket;
	}
	
	public String getAgentDesc() {
		String agentNameShow = this.agentName == null?"代理商名称无":this.agentName;
		String provinceShow = this.province == null?"省份无":this.province;
		String cityShow = this.city == null?"城市无":this.city;
		String areaShow = this.area == null?"区域无":this.area;
		String addressShow = this.address == null?"详细地址无":this.address;
		String contacterShow = this.contacter == null?"联系人无":this.contacter;
		return agentNameShow +"（"+ provinceShow+ " " + cityShow+ " " + areaShow+ " " + addressShow + " "+ contacterShow + "）";
	}
	
	
}
