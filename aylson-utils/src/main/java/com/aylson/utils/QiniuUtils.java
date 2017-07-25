package com.aylson.utils;

import java.util.HashMap;
import java.util.Map;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;


/**
 * 七牛云储存工具类
 * @author wwx
 *
 */
public class QiniuUtils {
	
	//七牛云账号的AK和SK，以及配置域名
//	private static final String  AK = "Qs-_4ZUu6Jt6KZ4jyGN_T4Ypqox0EY3W8rYzVP8V";
//	private static final String  SK = "7FXGxk1ZuB2gUE-NbjurKBYskdmgyYTonZgW-aOO";
	
	private static final String  AK = "MFv1TjQdSjRBiaysUu7MjXOI1GxQPCnZJRFvmcsd";
	private static final String  SK = "s6NV2UFhPtY0TvBPcHVdujwPxTYSZlDjRefZRi9m";
	
	public static final int UPLOAD_SIMPLE = 1;           //简单上传
	public static final int UPLOAD_OVER = 2;             //上传覆盖
	
	/**
	 * 获取七牛云简单上传凭证
	 */
	public static String getUploadToken(String bucket){
		if(bucket == null || "".equals(bucket))bucket = DomainToBucket.DOMAIN_DEFAULT;
		Auth auth = Auth.create(AK, SK);
		String uploadToken = auth.uploadToken(bucket);
		return uploadToken;
	}
	
	/**
	 * 获取七牛云覆盖上传凭证
	 */
	public static String getUploadToken(String bucket, String key){
		if("".equals(key)) return "";
		if(bucket == null || "".equals(bucket))bucket = DomainToBucket.DOMAIN_DEFAULT;
		Auth auth = Auth.create(AK, SK);
		String uploadToken = auth.uploadToken(bucket, key, 3600, new StringMap().put("insertOnly", 1 ));
		return uploadToken;
	}
	
	/**
	 * 上传文件到七牛云
	 * @param file
	 * @param fileName
	 * @param bucket
	 * @param type : 1 简单上传 2 覆盖上传
	 * @return
	 */
	public static int uploadFile(byte[] file, String fileName, String bucket, Integer type){
		UploadManager uploadManager = new UploadManager();//七牛上传管理器
		String uploadToken = null;
		if(type == QiniuUtils.UPLOAD_SIMPLE){
			uploadToken = getUploadToken(bucket);
		}else if(type == QiniuUtils.UPLOAD_OVER){
			uploadToken = getUploadToken(bucket, fileName);
		}
		Response res = null;
		int statusCode = 0 ;
		try {
			res = uploadManager.put(file, fileName,uploadToken);//fileName是key,在7牛相当于文件名
			statusCode = res.statusCode;
		} catch (QiniuException e) {
			Response r = e.response;
	        // 请求失败时打印的异常的信息
	        System.out.println(r.toString());
		}
		return statusCode;
	}
	
	public static String getFileName(){
		String fileName = "";
		
		return fileName;
	}
	
	/**
	 * 角色类型
	 * 
	 */
	public static class DomainToBucket {
		/**
		 * 默认资源空间
		 */
		public static String  DOMAIN_DEFAULT = "yfax-test";
		
//		public static String  DOMAIN_DEFAULT = "dc-test";
		/**
		 * 发布管理资源空间
		 */
		public static String  DOMAIN_PUBLISH = "dc-publish";
		/**
		 * 礼品兑换图片资源空间
		 */
		public static String  DOMAIN_GIFT = "dc-gift";
		/**
		 * 方案设计-需求附件
		 */
		public static String  DOMAIN_REQUIREMENT = "dc-requirement";
		/**
		 * 方案设计-设计附件
		 */
		public static String  DOMAIN_DESIGN = "dc-design";
		/**
		 * 会员附件
		 */
		public static String  DOMAIN_MEMBER = "dc-member";
		/**
		 * 文章附件
		 */
		public static String  DOMAIN_ARTICLE = "dc-article";
		/**
		 * 提现转账凭证附件
		 */
		public static String  DOMAIN_TRANSFERPROOF = "dc-transferproof";
		/**
		 * 代理商开业图片附件
		 */
		public static String  DOMAIN_AGENTOPEN = "dc-agentopen";
		/**
		 * 合伙人
		 */
		public static String  DOMAIN_PARTNER = "aylson-partner";
		/**
		 * 买家秀
		 */
		public static String  DOMAIN_BUYERSHOW = "aylson-buyershow";
		
		public static Map<String, String> DomainToBucketMap = new HashMap<String, String>();
		
		static {
			DomainToBucketMap.put(DOMAIN_DEFAULT, "http://otml7va16.bkt.clouddn.com/");
			
//			DomainToBucketMap.put(DOMAIN_DEFAULT, "http://obo5ony4m.bkt.clouddn.com/");
			DomainToBucketMap.put(DOMAIN_PUBLISH, "http://obo6bj7wm.bkt.clouddn.com/");
			DomainToBucketMap.put(DOMAIN_GIFT, "http://obw71n53a.bkt.clouddn.com/");
			DomainToBucketMap.put(DOMAIN_REQUIREMENT, "http://oc51dfei2.bkt.clouddn.com/");
			DomainToBucketMap.put(DOMAIN_DESIGN, "http://oc51kn5ac.bkt.clouddn.com/");
			DomainToBucketMap.put(DOMAIN_MEMBER, "http://oc5104fa9.bkt.clouddn.com/");
			DomainToBucketMap.put(DOMAIN_ARTICLE, "http://ocnwkw9ho.bkt.clouddn.com/");
			DomainToBucketMap.put(DOMAIN_TRANSFERPROOF, "http://oe3ohnsw3.bkt.clouddn.com/");
			DomainToBucketMap.put(DOMAIN_AGENTOPEN, "http://oe3ocgn89.bkt.clouddn.com/");
			DomainToBucketMap.put(DOMAIN_PARTNER, "http://omhhlosqw.bkt.clouddn.com/");
			DomainToBucketMap.put(DOMAIN_BUYERSHOW, "http://omsok361e.bkt.clouddn.com/");
		}
	}
	
	
}
