package com.aylson.dc.owner.po;

import java.io.Serializable;

public class DesignDetailDW implements Serializable{

	private static final long serialVersionUID = 6942773339415976287L;
	
	private Integer id;                           //主键
	private Integer appointId;                    //预约id
	private Integer designId;                     //设计信息id
	private String productNo;                     //产品编号
	private String productName;                   //产品名称
	private String installLocation;               //安装位置
	private Integer frameNum;                     //樘数
	private String metalParts;                    //五金配件
	private String railsTypeD;                    //下轨类型
	private Integer wallThick;                    //墙厚
	private Integer holeSizeW;                    //洞口尺寸：宽
	private Integer holeSizeH;                    //洞口尺寸：高
	private Integer productSizeW;                 //产品尺寸：宽
	private Integer productSizeH;                 //产品尺寸：高
	private String colorIn;                       //颜色：内
	private String colorOut;                      //颜色：外
	private String glass;                         //玻璃
	private String shutter;                       //百叶：电动/手动
	private String pack;                          //包套：无/单包/双包
	private Boolean isHaveSafeSys;                //是否有安全系统
	private String attachUrls;                    //附件地址集合
	private Integer openFanNumW;                  //开启扇数量
	private Integer sillHeightW;                  //窗台高度
	
	
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
	
	public String getProductNo() {
		return productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getInstallLocation() {
		return installLocation;
	}
	public void setInstallLocation(String installLocation) {
		this.installLocation = installLocation;
	}
	
	public Integer getFrameNum() {
		return frameNum;
	}
	public void setFrameNum(Integer frameNum) {
		this.frameNum = frameNum;
	}
	
	public String getMetalParts() {
		return metalParts;
	}
	public void setMetalParts(String metalParts) {
		this.metalParts = metalParts;
	}
	
	public String getRailsTypeD() {
		return railsTypeD;
	}
	public void setRailsTypeD(String railsTypeD) {
		this.railsTypeD = railsTypeD;
	}
	
	public Integer getWallThick() {
		return wallThick;
	}
	public void setWallThick(Integer wallThick) {
		this.wallThick = wallThick;
	}
	
	public Integer getHoleSizeW() {
		return holeSizeW;
	}
	public void setHoleSizeW(Integer holeSizeW) {
		this.holeSizeW = holeSizeW;
	}
	
	public Integer getHoleSizeH() {
		return holeSizeH;
	}
	public void setHoleSizeH(Integer holeSizeH) {
		this.holeSizeH = holeSizeH;
	}
	
	public Integer getProductSizeW() {
		return productSizeW;
	}
	public void setProductSizeW(Integer productSizeW) {
		this.productSizeW = productSizeW;
	}
	
	public Integer getProductSizeH() {
		return productSizeH;
	}
	public void setProductSizeH(Integer productSizeH) {
		this.productSizeH = productSizeH;
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
	
	public String getGlass() {
		return glass;
	}
	public void setGlass(String glass) {
		this.glass = glass;
	}
	
	public String getShutter() {
		return shutter;
	}
	public void setShutter(String shutter) {
		this.shutter = shutter;
	}
	
	public String getPack() {
		return pack;
	}
	public void setPack(String pack) {
		this.pack = pack;
	}
	
	public Boolean getIsHaveSafeSys() {
		return isHaveSafeSys;
	}
	public void setIsHaveSafeSys(Boolean isHaveSafeSys) {
		this.isHaveSafeSys = isHaveSafeSys;
	}
	
	public String getAttachUrls() {
		return attachUrls;
	}
	public void setAttachUrls(String attachUrls) {
		this.attachUrls = attachUrls;
	}
	
	public Integer getOpenFanNumW() {
		return openFanNumW;
	}
	public void setOpenFanNumW(Integer openFanNumW) {
		this.openFanNumW = openFanNumW;
	}
	
	public Integer getSillHeightW() {
		return sillHeightW;
	}
	public void setSillHeightW(Integer sillHeightW) {
		this.sillHeightW = sillHeightW;
	}
	
	
}
