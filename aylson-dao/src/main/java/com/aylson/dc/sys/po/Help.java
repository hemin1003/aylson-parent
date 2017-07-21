package com.aylson.dc.sys.po;

import java.io.Serializable;

public class Help implements Serializable{

	private static final long serialVersionUID = -3227529424790180618L;
	
	private Integer id;             		//主键  
	private Integer seq;             		//序号  
	private String question;             	//问题  
	private String answer;             		//回答内容  
	private Integer type;             		//类型：1 设计师 2开拓者 3业主
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	

}
