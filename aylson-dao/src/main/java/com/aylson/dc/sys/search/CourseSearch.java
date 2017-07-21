package com.aylson.dc.sys.search;

import com.aylson.core.frame.search.BaseSearch;

public class CourseSearch extends BaseSearch{

	private static final long serialVersionUID = -5492369098434657574L;
	
	//匹配查询
	private Integer id;             		//主键
	private Integer state;                  //状态：1 草稿 2发布 3结束
	private String createTime;              //创建时间
	private Integer createrId;              //创建人id
	private Integer type;                   //课程类型：1产品卖点2系统介绍3安装售后4运营体系
	//模糊查询
	private String courseNameLike;          //课程名称
	private String lecturerLike;            //讲师
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public Integer getCreaterId() {
		return createrId;
	}
	public void setCreaterId(Integer createrId) {
		this.createrId = createrId;
	}
	
	public String getCourseNameLike() {
		return courseNameLike;
	}
	public void setCourseNameLike(String courseNameLike) {
		this.courseNameLike = courseNameLike;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getLecturerLike() {
		return lecturerLike;
	}
	public void setLecturerLike(String lecturerLike) {
		this.lecturerLike = lecturerLike;
	}
	
	
}
