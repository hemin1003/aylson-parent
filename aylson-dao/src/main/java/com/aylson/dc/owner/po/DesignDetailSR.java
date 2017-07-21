package com.aylson.dc.owner.po;

import java.io.Serializable;

public class DesignDetailSR implements Serializable{

	private static final long serialVersionUID = -1851928577782522103L;
	
	
	private Integer id;                      //主键  
	private Integer appointId;               //预约id 
	private Integer designId;                //设计信息id  
	private String type;                     //类型  
	private String colorIn;                  //室内颜色  
	private String colorOut;                 //室外颜色 
	private Integer length1;                 //L1
	private Integer length2;                 //L2
	private Integer length3;                 //L3 
	private Integer length4;                 //L4 
	private Integer length5;                 //L5
	private Integer length6;                 //L6 
	private Integer width1;                  //W1
	private Integer width2;                  //W2  
	private Float partial;                   //∂角
	private Integer height;                  //H 
	private String roof;                     //房顶 
	private Boolean rootIsHollow;            //房顶是否中空  
	private String real;                     //房身 
	private Boolean realIsHollow;            //房身是否中空 
	private String rearGlass;                //三角玻璃  
	private Boolean rearGlassIsHollow;       //三角玻璃是否中空
	private String roofGlassColor;           //房顶玻璃颜色
	private String realGlassColor;           //房身玻璃颜色
	private String rearGlassColor;           //三角玻璃颜色
	private String styleSettingUrl;          //风格定型附件
	private String hollowShutterConMode;     //中空百叶控制方式 
	private Boolean isHaveSunVisorSys;       //是否有遮阳系统
	private Boolean isHaveSafeSys;           //是否有安全系统
	private String otherRequire;             //其他要求
	private String attachUrls;               //设计图附件
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getAppointId() {
		return appointId;
	}
	public void setAppointId(Integer appointId) {
		this.appointId = appointId;
	}
	
	public Integer getDesignId() {
		return designId;
	}
	public void setDesignId(Integer designId) {
		this.designId = designId;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getColorIn() {
		return colorIn;
	}
	public void setColorIn(String colorIn) {
		this.colorIn = colorIn;
	}
	
	public String getColorOut() {
		return colorOut;
	}
	public void setColorOut(String colorOut) {
		this.colorOut = colorOut;
	}
	
	public Integer getLength1() {
		return length1;
	}
	public void setLength1(Integer length1) {
		this.length1 = length1;
	}
	
	public Integer getLength2() {
		return length2;
	}
	public void setLength2(Integer length2) {
		this.length2 = length2;
	}
	
	public Integer getLength3() {
		return length3;
	}
	public void setLength3(Integer length3) {
		this.length3 = length3;
	}
	
	public Integer getLength4() {
		return length4;
	}
	public void setLength4(Integer length4) {
		this.length4 = length4;
	}
	
	public Integer getLength5() {
		return length5;
	}
	public void setLength5(Integer length5) {
		this.length5 = length5;
	}
	
	public Integer getLength6() {
		return length6;
	}
	public void setLength6(Integer length6) {
		this.length6 = length6;
	}
	
	public Integer getWidth1() {
		return width1;
	}
	public void setWidth1(Integer width1) {
		this.width1 = width1;
	}
	
	public Integer getWidth2() {
		return width2;
	}
	public void setWidth2(Integer width2) {
		this.width2 = width2;
	}
	
	public Float getPartial() {
		return partial;
	}
	public void setPartial(Float partial) {
		this.partial = partial;
	}
	
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	
	public String getRoof() {
		return roof;
	}
	public void setRoof(String roof) {
		this.roof = roof;
	}
	
	public Boolean getRootIsHollow() {
		return rootIsHollow;
	}
	public void setRootIsHollow(Boolean rootIsHollow) {
		this.rootIsHollow = rootIsHollow;
	}
	
	public String getReal() {
		return real;
	}
	public void setReal(String real) {
		this.real = real;
	}
	
	public Boolean getRealIsHollow() {
		return realIsHollow;
	}
	public void setRealIsHollow(Boolean realIsHollow) {
		this.realIsHollow = realIsHollow;
	}
	
	public String getRearGlass() {
		return rearGlass;
	}
	public void setRearGlass(String rearGlass) {
		this.rearGlass = rearGlass;
	}
	
	public Boolean getRearGlassIsHollow() {
		return rearGlassIsHollow;
	}
	public void setRearGlassIsHollow(Boolean rearGlassIsHollow) {
		this.rearGlassIsHollow = rearGlassIsHollow;
	}
	
	public String getRoofGlassColor() {
		return roofGlassColor;
	}
	public void setRoofGlassColor(String roofGlassColor) {
		this.roofGlassColor = roofGlassColor;
	}
	
	public String getRealGlassColor() {
		return realGlassColor;
	}
	public void setRealGlassColor(String realGlassColor) {
		this.realGlassColor = realGlassColor;
	}
	
	public String getRearGlassColor() {
		return rearGlassColor;
	}
	public void setRearGlassColor(String rearGlassColor) {
		this.rearGlassColor = rearGlassColor;
	}
	
	public String getStyleSettingUrl() {
		return styleSettingUrl;
	}
	public void setStyleSettingUrl(String styleSettingUrl) {
		this.styleSettingUrl = styleSettingUrl;
	}
	
	public String getHollowShutterConMode() {
		return hollowShutterConMode;
	}
	public void setHollowShutterConMode(String hollowShutterConMode) {
		this.hollowShutterConMode = hollowShutterConMode;
	}
	
	public Boolean getIsHaveSunVisorSys() {
		return isHaveSunVisorSys;
	}
	public void setIsHaveSunVisorSys(Boolean isHaveSunVisorSys) {
		this.isHaveSunVisorSys = isHaveSunVisorSys;
	}
	
	public Boolean getIsHaveSafeSys() {
		return isHaveSafeSys;
	}
	public void setIsHaveSafeSys(Boolean isHaveSafeSys) {
		this.isHaveSafeSys = isHaveSafeSys;
	}
	
	public String getOtherRequire() {
		return otherRequire;
	}
	public void setOtherRequire(String otherRequire) {
		this.otherRequire = otherRequire;
	}
	
	public String getAttachUrls() {
		return attachUrls;
	}
	public void setAttachUrls(String attachUrls) {
		this.attachUrls = attachUrls;
	}
	

}
