package com.aylson.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


/**
 * 短信平台 互亿无线工具类
 * @author wwx
 *
 */
public class IHuiYiUtils {
	
	//账号信息
	private final static String IHUIYI_SMS_HOST = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
	private final static String IHUIYI_USERNAME = "cf_aylson";
	private final static String IHUIYI_PASSWORD = "aylson_zp";
	
	public static Map<String,String> sentSms(String mobilePhone, String content){
	  System.out.println("开始发送短信到"+mobilePhone+",内容为："+content);
	  Map<String,String> result = new HashMap<String, String>();
	  HttpClient client = new HttpClient();
      PostMethod method = new PostMethod(IHuiYiUtils.IHUIYI_SMS_HOST);
      client.getParams().setContentCharset("UTF-8");
      method.setRequestHeader("ContentType",
                "application/x-www-form-urlencoded;charset=UTF-8");
      NameValuePair[] data = {
                new NameValuePair("account", IHuiYiUtils.IHUIYI_USERNAME),
                new NameValuePair("password",IHuiYiUtils.IHUIYI_PASSWORD), // 密码可以使用明文密码或使用32位MD5加密
                new NameValuePair("mobile", mobilePhone),
                new NameValuePair("content", content),};

        method.setRequestBody(data);
        try {
            client.executeMethod(method);
            String submitResult = method.getResponseBodyAsString();
            Document doc = DocumentHelper.parseText(submitResult);
            Element root = doc.getRootElement();
            String returnCode = root.elementText("code");
            result.put("returnCode", returnCode);
            result.put("msg", root.elementText("msg"));
            result.put("smsid", root.elementText("smsid"));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
	
}
