package com.aylson.dc.sys.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.utils.QiniuUtils;
import com.aylson.utils.QiniuUtils.DomainToBucket;
import com.aylson.utils.StringUtil;


/**
 * 文件上传下载Controller 
 * @author wwx
 *
 */
@Controller
@RequestMapping("/sys/fileHandle")
public class FileHandleController extends BaseController{
	
	/**
	 * 跳转到文件上传界面
	 * @return
	 */
	@RequestMapping(value = "/toFileUpload", method = RequestMethod.GET)
	public String toFileUpload()  {
		String fileUpload = "/jsp/sys/admin/file/fileUpload";//文件上传页面
		return fileUpload;
	}
	
	/**
	 * 跳转到图片上传界面
	 * @return
	 */
	@RequestMapping(value = "/toImgUpload", method = RequestMethod.GET)
	public String toImgUpload()  {
		String fileUpload = "/jsp/sys/admin/file/imgUpload";//文件上传页面
		return fileUpload;
	}
	
	/**
	 * 单个图片上传处理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/imgUpload", method = RequestMethod.POST)
	@ResponseBody
	public Result imgUpload(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Result result = new Result();
		String bucket =  request.getParameter("bucket");
//		JSONObject obj = new JSONObject();
		if(!DomainToBucket.DomainToBucketMap.containsKey(bucket)){
//			obj.put("error", 0);
//			response.getWriter().println(obj.toJSONString());
			result.setError(ResultCode.CODE_STATE_4006, "找不到对应的资源空间，请联系管理");
			return result;
		}
		byte[] files = null; //上传的文件
		int statusCode = 0;  //上传结果code
		String fileName = null;//七牛云存放文件名
		//1、获取上传的文件
		long startTime=System.currentTimeMillis();   //获取开始时间
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){ //判断request是否有文件上传
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
			Iterator<String> ite = multiRequest.getFileNames();
			while(ite.hasNext()){
				MultipartFile file = multiRequest.getFile(ite.next());
				if(file!=null){
					try {
						 files = file.getBytes();
						 fileName = bucket +"-"+ new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"@|@"+ file.getOriginalFilename();
						 statusCode = QiniuUtils.uploadFile(files, fileName,bucket,QiniuUtils.UPLOAD_SIMPLE);
						 break;
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
		}
		long endTime=System.currentTimeMillis(); //获取结束时间
		System.out.println("上传文件共使用时间："+(endTime-startTime));
		if(statusCode == 200){//成功：返回图片存放地址
			result.setData(DomainToBucket.DomainToBucketMap.get(bucket)+fileName);
			result.setSuccess(true);
			result.setOK(ResultCode.CODE_STATE_200, "上传成功");
		}else{//失败：返回失败信息
			result.setError(ResultCode.CODE_STATE_4006, "上传失败");
		}
		return result;
	}
	
	/**
	 * 单个图片上传处理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/imgBatchUpload", method = RequestMethod.POST)
	@ResponseBody
	public Result imgBatchUpload(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Result result = new Result();
		String bucket =  request.getParameter("bucket");
		Map<String,String> uploadResult = new HashMap<String, String>();
		if(!DomainToBucket.DomainToBucketMap.containsKey(bucket)){
			result.setError(ResultCode.CODE_STATE_4006, "找不到对应的资源空间，请联系管理");
			return result;
		}
		byte[] files = null; //上传的文件
		int statusCode = 0;  //上传结果code
		String fileName = null;//七牛云存放文件名
		StringBuffer fileNames = new StringBuffer();
		//1、获取上传的文件
		long startTime=System.currentTimeMillis();   //获取开始时间
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){ //判断request是否有文件上传
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
			Iterator<String> ite = multiRequest.getFileNames();
			String domain = DomainToBucket.DomainToBucketMap.get(bucket);
			while(ite.hasNext()){
				MultipartFile file = multiRequest.getFile(ite.next());
				if(file!=null){
					try {
						 files = file.getBytes();
						 fileName = bucket + new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+(int)(Math.random()*1000000000);
						 statusCode = QiniuUtils.uploadFile(files, fileName,bucket,QiniuUtils.UPLOAD_SIMPLE);
						 if(statusCode==200){
							//fileNames.append(domain+fileName + ",");
							String uploadName = file.getOriginalFilename();//上传文件名
							if(!StringUtil.isEmpty(uploadName)){
								String key = uploadName.substring(0, uploadName.lastIndexOf("."));
								String newKey = request.getParameter(key);
								uploadResult.put(newKey,domain+fileName);
							}
						 }
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
		}
		long endTime=System.currentTimeMillis(); //获取结束时间
		System.out.println("上传文件共使用时间："+(endTime-startTime));
		if(statusCode ==200){//成功：返回图片存放地址
			result.setData(uploadResult);
			result.setSuccess(true);
			result.setOK(ResultCode.CODE_STATE_200, "上传成功");
		}else{//失败：返回失败信息
			result.setError(ResultCode.CODE_STATE_4006, "上传失败");
		}
		return result;
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/kindeditorUpload", method = RequestMethod.POST)
	@ResponseBody
	public void kindeditorUpload(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String bucket =  request.getParameter("bucket");
		byte[] files = null; //上传的文件
		int statusCode = 0;  //上传结果code
		String fileName = null;//七牛云存放文件名
		JSONObject obj = new JSONObject();
		if(!DomainToBucket.DomainToBucketMap.containsKey(bucket)){
			obj.put("error", 0);
			response.getWriter().println(obj.toJSONString());
			return;
		}
		//1、获取上传的文件
		long startTime=System.currentTimeMillis();   //获取开始时间
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){ //判断request是否有文件上传
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
			Iterator<String> ite = multiRequest.getFileNames();
			while(ite.hasNext()){
				MultipartFile file = multiRequest.getFile(ite.next());
				if(file!=null){
					try {
						 files = file.getBytes();
						 fileName = bucket +"-"+ new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"@|@"+ file.getOriginalFilename();
						 statusCode = QiniuUtils.uploadFile(files, fileName,bucket,QiniuUtils.UPLOAD_SIMPLE);
						 break;
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
		}
		long endTime=System.currentTimeMillis(); //获取结束时间
		System.out.println("上传文件共使用时间："+(endTime-startTime));
		if(statusCode ==200){//成功：返回图片存放地址
			obj.put("error", 0);
			obj.put("url", DomainToBucket.DomainToBucketMap.get(bucket)+fileName);
		}else{//失败：返回失败信息
			obj.put("error", 0);
		}
		response.getWriter().println(obj.toJSONString());
	}

	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	@ResponseBody
	public Result fileUpload(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Result result = new Result();
		String bucket =  request.getParameter("bucket");
		if(!DomainToBucket.DomainToBucketMap.containsKey(bucket)){
			result.setError(ResultCode.CODE_STATE_4006, "找不到对应的资源空间，请联系管理员");
			return result;
		}
		byte[] files = null; //上传的文件
		int statusCode = 0;  //上传结果code
		String fileName = null;//七牛云存放文件名
		//1、获取上传的文件
		long startTime=System.currentTimeMillis();   //获取开始时间
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){ //判断request是否有文件上传
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
			Iterator<String> ite = multiRequest.getFileNames();
			while(ite.hasNext()){
				MultipartFile file = multiRequest.getFile(ite.next());
				if(file!=null){
					try {
						 files = file.getBytes();
						 fileName = bucket +"-"+ new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"@|@"+ file.getOriginalFilename();
						 statusCode = QiniuUtils.uploadFile(files, fileName,bucket,QiniuUtils.UPLOAD_SIMPLE);
						 break;
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
		}
		long endTime=System.currentTimeMillis(); //获取结束时间
		System.out.println("上传文件共使用时间："+(endTime-startTime));
		if(statusCode == 200){//成功：返回图片存放地址
			result.setData(DomainToBucket.DomainToBucketMap.get(bucket)+fileName);
			result.setSuccess(true);
			result.setOK(ResultCode.CODE_STATE_200, "上传成功");
		}else{//失败：返回失败信息
			result.setError(ResultCode.CODE_STATE_4006, "上传失败");
		}
		return result;
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public Result upload(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Result result = new Result();
		String bucket =  request.getParameter("bucket");
		if(!DomainToBucket.DomainToBucketMap.containsKey(bucket)){
			result.setError(ResultCode.CODE_STATE_4006, "找不到对应的资源空间，请联系管理");
			return result;
		}
		byte[] files = null; //上传的文件
		int statusCode = 0;  //上传结果code
		String fileName = null;//七牛云存放文件名
		//1、获取上传的文件
		long startTime=System.currentTimeMillis();   //获取开始时间
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)){ //判断request是否有文件上传
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
			Iterator<String> ite = multiRequest.getFileNames();
			while(ite.hasNext()){
				MultipartFile file = multiRequest.getFile(ite.next());
				if(file!=null){
					try {
						 files = file.getBytes();
						 fileName = bucket +"-"+ new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"@|@"+ file.getOriginalFilename();
						 statusCode = QiniuUtils.uploadFile(files, fileName,bucket,QiniuUtils.UPLOAD_SIMPLE);
						 break;
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
		}
		long endTime=System.currentTimeMillis(); //获取结束时间
		System.out.println("上传文件共使用时间："+(endTime-startTime));
		if(statusCode == 200){//成功：返回图片存放地址
			result.setData(DomainToBucket.DomainToBucketMap.get(bucket)+fileName);
			result.setSuccess(true);
			result.setOK(ResultCode.CODE_STATE_200, "上传成功");
		}else{//失败：返回失败信息
			result.setError(ResultCode.CODE_STATE_4006, "上传失败");
		}
		return result;
	}
	
   @RequestMapping(value = "/editorUploadImg", produces = "text/plain;charset=UTF-8", method = { RequestMethod.POST })
   @ResponseBody
   public void editorUploadImg(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception {
	   	JSONObject obj = new JSONObject();
	   	PrintWriter pw = null;
	   	String testPath = "http://obo6bj7wm.bkt.clouddn.com/dc-publish-20160831182401@|@广东企业真实性核验单 (2).png";
	   	obj.put("error", 0);
		obj.put("url", testPath);
		response.getWriter().println(obj.toJSONString());
   }
   
    @RequestMapping(value = "/uploadTest", method = RequestMethod.POST)
	@ResponseBody
	public Result uploadTest(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Result result = new Result();
		String bucket =  request.getParameter("bucket");
		if(!DomainToBucket.DomainToBucketMap.containsKey(bucket)){
			result.setError(ResultCode.CODE_STATE_4006, "找不到对应的资源空间，请联系管理员");
			return result;
		}
		result.setData("http://oc51dfei2.bkt.clouddn.com/dc-requirement-20160922153022@|@YvCrvRIFuAv9jKNW3z3H_4xP49WSJhSOvXOACRTH9_p2bH4S3B-Ntpx-Mlt9rNN_.jpg");
		result.setSuccess(true);
		result.setOK(ResultCode.CODE_STATE_200, "上传成功");
		return result;
		
	}
	
	
}
