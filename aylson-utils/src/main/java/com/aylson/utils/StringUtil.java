package com.aylson.utils;

/**
 * 字符串工具类
 * @author wwx
 * @since  2016-05
 * @version v1.0
 *
 */
public class StringUtil {

	/**
	 * 判断字符串是否为空：
	 * true: null 或 ''
	 * @param str
	 * @return
	 */
	public static Boolean isEmpty(String str){
		if(str == null || "".equals(str)){
			return true;
		}
		return false;
	}
	
	/**
	 * 判断字符串是否不为空：
	 * @param str
	 * @return
	 */
	public static Boolean isNotEmpty(String str){
		if(str != null && !"".equals(str)){
			return true;
		}
		return false;
	}
	
	/**
	 * 解析富文本内容
	 * @param content
	 * @return
	 */
	public static String parseRichContent(String content){
		if(content != null && !"".equals(content)){
			return content.replaceAll("</?[^>]+>", "").replaceAll("<a>\\s*|\t|\r|\n</a>", "");
		}
		return null;
	}
	
	public static void main(String[] args) {
		String content = "<img src=\"http://obo6bj7wm.bkt.clouddn.com/dc-publish-Penguins.jpg-20160810095733\" alt=\"\" width=\"512\" height=\"384\" title=\"\" align=\"\" /><img src=\"http://obo6bj7wm.bkt.clouddn.com/dc-publish-Hydrangeas.jpg-20160810095557523018100\" alt=\"\" width=\"120\" height=\"90\" title=\"\" align=\"\" />" 
		+"<p>"
		+"	<img src=\"http://obo6bj7wm.bkt.clouddn.com/dc-publish20160810094253101516932\" alt=\"\" width=\"112\" height=\"84\" title=\"\" align=\"\" />33333333333333333"
		+"</p>"
		+"<p>"
			+"<img src=\"http://obo6bj7wm.bkt.clouddn.com/dc-publish-imgFile-2016081009541620334047\" alt=\"\" width=\"300\" height=\"225\" title=\"\" align=\"\" /> "
		+"</p>";
		System.out.println(content);
		System.out.println(StringUtil.parseRichContent(content));
		String text = StringUtil.parseRichContent(content);
		if(text.length() > 100){
			StringUtil.parseRichContent(content).substring(0, 100);
		}else{
			StringUtil.parseRichContent(content);
		}
	}

	/**
	 * 如果为.00，则处理成0.00：
	 * @param str
	 * @return
	 */
	public static String zero2Str(String str){
		return (null2Str(str).equals(".00")?"0.00":str);
	}
	
	/**
	 * 1.将null对象返回空字符串-“""”<br>
	 * 2.若非null对象返回toString()及trim字符串<br>
	 * 
	 * @param origin
	 *            String
	 * @return String
	 */
	public static String null2Str(Object origin) {
		return (origin == null ? "" : origin.toString().trim()).replace("null", "");
	}
}
