package com.aylson.core.frame.domain;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * 处理结果对象
 */
public class Result implements Serializable {
	
	private static final long serialVersionUID = 5905715228490291386L;

	private boolean success;
	/**
	 * @fields record 消息对象
	 */
	private Object message;

	private Object data;

	private int resultCode;

	public Result() {
		super();
	}

	/**
	 * @description
	 * @param status
	 *            状态
	 * @param message
	 *            消息
	 */
	public Result(boolean success, Object message) {
		this.success = success;
		this.message = message;
	}

	/**
	 * 添加成功结果信息
	 * 
	 * @param record
	 */
	public void setOK(int resultCode, Object message, Object data) {
		this.resultCode = resultCode;
		this.message = message;
		this.data = data;
		this.success = true;
	}

	/**
	 * 添加成功结果信息
	 * 
	 * @param record
	 */
	public void setOK(int resultCode, Object message) {
		this.resultCode = resultCode;
		this.message = message;
		this.success = true;
	}

	/**
	 * 添加错误消息
	 * 
	 * @param message
	 */
	public void setError(int resultCode, Object message) {
		this.resultCode = resultCode;
		this.message = message;
		this.success = false;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
	
	public String toJsonString(){
		JSONObject json = JSONObject.fromObject(this);
		return json.toString();
	}
}
