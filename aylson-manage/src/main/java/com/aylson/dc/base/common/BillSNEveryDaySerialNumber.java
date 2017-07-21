package com.aylson.dc.base.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Administrator
 *
 */
public class BillSNEveryDaySerialNumber extends SerialNumber{

	protected final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	protected DecimalFormat df = null;
	
	/**
	 * 构造方法
	 * @param length
	 */
	public BillSNEveryDaySerialNumber(int length){
		if(length < 1) {
			throw new IllegalArgumentException("参数的长度必须大于1!");
		}
		char[] chs = new char[length];
		for(int i = 0; i < length; i++) {
			chs[i] = '0';
		}
		df = new DecimalFormat(new String(chs));
	}
	
	/**
	 * 获取流水单号的实现
	 */
	@Override
	protected String process(int start) {
		Date curDate = new Date();
		String serialNumber = sdf.format(curDate)+df.format(start);
		return serialNumber;
	}

	
}
