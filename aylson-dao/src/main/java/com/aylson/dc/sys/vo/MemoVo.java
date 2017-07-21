package com.aylson.dc.sys.vo;

import com.aylson.dc.sys.po.Memo;

public class MemoVo extends Memo {

	private static final long serialVersionUID = 6492964548820761771L;

	private String memoTimeStr;             	//备忘时间
	private String createTimeStr;             	//创建时间  
	
	public String getMemoTimeStr() {
		return memoTimeStr;
	}
	public void setMemoTimeStr(String memoTimeStr) {
		this.memoTimeStr = memoTimeStr;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	

}
