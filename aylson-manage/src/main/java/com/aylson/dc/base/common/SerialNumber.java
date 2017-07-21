package com.aylson.dc.base.common;

/**
 * 获取序列号模板方法
 * @author Administrator
 *
 */
public abstract class SerialNumber {
	
	/**
	 * 执行序列号方法获取
	 * @param start
	 * @return
	 */
	protected abstract String process(int start);
	
	/**
	 * 获取序列号
	 * @param start 开始值
	 * @return
	 */
	public synchronized String getSerialNumber(int start) {
		return process(start);
	}
	
}
