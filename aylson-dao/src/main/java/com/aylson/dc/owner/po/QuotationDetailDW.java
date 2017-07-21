package com.aylson.dc.owner.po;

import java.io.Serializable;

public class QuotationDetailDW implements Serializable{

	private static final long serialVersionUID = 556505736710838262L;
	
	
	private Integer id;                           //主键
	private Integer appointId;                    //预约id
	private Integer quotationId;                  //报价单id
	private Integer seq;                          //设计信息id
	private String productNo;                     //产品编号
	private String productName;                   //产品名称
	private Integer holeSizeW;                    //洞口尺寸：宽
	private Integer holeSizeH;                    //洞口尺寸：高
	private Integer productSizeW;                 //产品尺寸：宽
	private Integer productSizeH;                 //产品尺寸：高
	private Integer wallThick;                    //墙厚
	private String colorIn;                       //颜色：内
	private String colorOut;                      //颜色：外
	private String glass;                         //玻璃
	private String shutter;                       //百叶：电动/手动
	private Integer pruductNum;                   //产品数量
	private Float areas;                          //面积
	private Float unitPrice;                      //单价
	private Float amount;                         //金额
	private String rails;                         //下轨道
	private String handColor;                     //X手颜色
	private Float salesUnitPrice;                 //销售单价
	private Float salesUnitAmount;                //销售金额
	private Float goodsAmount;                    //配件金额
	private String remark;                        //备注
	private String goodNames;                     //备注
	private String goodAmounts;                   //备注
	
	
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
	
	public Integer getQuotationId() {
		return quotationId;
	}
	public void setQuotationId(Integer quotationId) {
		this.quotationId = quotationId;
	}
	
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
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
	
	public Integer getWallThick() {
		return wallThick;
	}
	public void setWallThick(Integer wallThick) {
		this.wallThick = wallThick;
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
	
	public Integer getPruductNum() {
		return pruductNum;
	}
	public void setPruductNum(Integer pruductNum) {
		this.pruductNum = pruductNum;
	}
	
	public Float getAreas() {
		return areas;
	}
	public void setAreas(Float areas) {
		this.areas = areas;
	}
	
	public Float getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Float unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	
	public String getRails() {
		return rails;
	}
	public void setRails(String rails) {
		this.rails = rails;
	}
	
	public String getHandColor() {
		return handColor;
	}
	public void setHandColor(String handColor) {
		this.handColor = handColor;
	}
	
	public Float getSalesUnitPrice() {
		return salesUnitPrice;
	}
	public void setSalesUnitPrice(Float salesUnitPrice) {
		this.salesUnitPrice = salesUnitPrice;
	}
	
	public Float getSalesUnitAmount() {
		return salesUnitAmount;
	}
	public void setSalesUnitAmount(Float salesUnitAmount) {
		this.salesUnitAmount = salesUnitAmount;
	}
	
	public Float getGoodsAmount() {
		return goodsAmount;
	}
	public void setGoodsAmount(Float goodsAmount) {
		this.goodsAmount = goodsAmount;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getGoodNames() {
		return goodNames;
	}
	public void setGoodNames(String goodNames) {
		this.goodNames = goodNames;
	}
	
	public String getGoodAmounts() {
		return goodAmounts;
	}
	public void setGoodAmounts(String goodAmounts) {
		this.goodAmounts = goodAmounts;
	}
	
	
}
