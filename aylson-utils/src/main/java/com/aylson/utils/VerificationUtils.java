package com.aylson.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 信息有效性校验工具类
 * @author wwx
 * @since  2016-08
 * @version v1.0
 *
 */
public class VerificationUtils {
	
	//正则规则
	/**
	 * 手机号码
	 * 有效手机号码：13开头，145,147开头，150-159开头，18开头的
	 */
	public static String MOBILE = "^(13[0-9]|14[5|7]|15[0-9]|17[0-9]|18[0-9])\\d{8}$";
	/**
	 * 邮箱
	 */
	public static String EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	/**
	 * 
	 * @param str         需要验证的内容
	 * @param regularity  验证规则
	 * @return
	 */
	public static Boolean isValid(String str, String regularity){
		if(str == null || regularity == null) return false;
		Pattern p = Pattern.compile(regularity);  
		Matcher m = p.matcher(str);  
		return m.matches();
	}
}
