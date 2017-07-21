package com.aylson.dc.owner.po;

import java.io.Serializable;

public class QuotationDetailSR implements Serializable{

	
	
	private static final long serialVersionUID = -2469198188918885062L;
	
	private Integer id;                      //主键  
	private Integer appointId;               //预约id 
	private Integer quotationId;             //报价单id
	private Integer seq;                     //序号  
	private String productNo;                //产品编号
	private Integer category;                //结构类型  
	private Integer structure;               //结构
	private String colorIn;                  //颜色-内  
	private String colorOut;                 //颜色-外
	private String glass;                    //玻璃
	private String shutter;                  //百叶
	private Integer pruductNum;              //产品数量
	private Float areas;                     //面积 
	private Float unitPrice;                 //单价 
	private Float salesUnitPrice;            //销售单价 
	private Float salesUnitAmount;           //销售金额 
	private Float amount;                    //出厂金额
	private String handColor;                //X手颜色 
	private Integer length;                  //长 
	private Integer width;                   //宽
	private Integer height;                  //柱高
	
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
	
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	
	public Integer getStructure() {
		return structure;
	}
	public void setStructure(Integer structure) {
		this.structure = structure;
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
	
	public String getHandColor() {
		return handColor;
	}
	public void setHandColor(String handColor) {
		this.handColor = handColor;
	}
	
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	

}
