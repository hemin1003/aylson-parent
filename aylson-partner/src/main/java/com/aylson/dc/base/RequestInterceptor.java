package com.aylson.dc.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.utils.StringUtil;

public class RequestInterceptor implements HandlerInterceptor{

	private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);
	
	// 这些请求不拦截
    private static final Set<String> UNFILTER_URI = new HashSet<>();
    // 这些开头的请求不拦截
    private static final Set<String> UNFILTER_URI_STARTWITH = new HashSet<>();
    
    static {
    	 //不拦截：完全匹配的请求
    	 UNFILTER_URI.add("/common/getPhoneVerifyCode");            //获取手机验证码
    	 UNFILTER_URI.add("/common/beforeSendValidCode");           //获取手机验证码前校验
    	 UNFILTER_URI.add("/sys/fileHandle/editorUploadImg");		//获取用户列表
    	 UNFILTER_URI.add("/sys/fileHandle/upload");		        //上传图片和文件
    	 UNFILTER_URI.add("/common/sendWxMessageResponse");         //响应其他服务器的请求发送微信信息
    	 //不拦截：开头匹配的请求
    	 UNFILTER_URI_STARTWITH.add("/resources");//资源文件
    	 UNFILTER_URI_STARTWITH.add("/static");//资源文件
    	 UNFILTER_URI_STARTWITH.add("/wx/frame");//资源文件
    	 UNFILTER_URI_STARTWITH.add("/pay");//资源文件
    	 UNFILTER_URI_STARTWITH.add("/inner/test");//资源文件
    	 UNFILTER_URI_STARTWITH.add("/general");//资源文件
    	 UNFILTER_URI_STARTWITH.add("/partner/visitor");//业主游客访问
    	 //UNFILTER_URI_STARTWITH.add("/wx/busi");//测试用
    }
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

    /**
     * 拦截所有请求
     */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		String servletPath = request.getServletPath();  //获取访问链接
		System.out.println("================"+servletPath+"================");
		if (unsign(servletPath)){ return true;}   //开始匹配
		if (unfilter(servletPath)){ return true;} //完全匹配
		String clientId = request.getParameter("clientId"); //前端请求标识
		if(StringUtil.isNotEmpty(clientId)){//来自客户端的判断
			if("-1".equals(clientId)){
				this.loginOutResponse(response);
			}else{
				return true;
			}
		}else{
			SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute("sessionInfo");
			if(sessionInfo == null){
				//来自后台的判断
	        	response.sendRedirect(request.getContextPath() + "/login.jsp");
				return false;
			}
		}
        return true;
	}

	/**
	 * 
	 * @param msgCode   对应消息码
	 * @param msg       返回消息
	 * @param request   请求对象
	 * @param response  响应对象
	 * @throws ServletException  
	 * @throws IOException
	 */
	 private void forward(Integer msgCode, String msg, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        request.setAttribute("msg", msg);
	        if (msgCode == ResultCode.CODE_STATE_4002) {
	            request.getRequestDispatcher("/login.jsp").forward(request, response);
	        } else {
	            request.getRequestDispatcher("/jsp/error/authMsg.jsp").forward(request, response);
	        }
	 }
	 
	 /**
     * 这些不拦截
     *
     * @param servletPath
     * @return
     */
    private boolean unfilter(String servletPath) {
        if (UNFILTER_URI.contains(servletPath)) {
            return true;
        }
        return false;
    }

    private boolean unsign(String servletPath) {
        for (String path : UNFILTER_URI_STARTWITH) {
            if (servletPath.startsWith(path)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 前端页面登陆失效响应
     * @param response
     * @throws Exception
     */
    private void loginOutResponse(HttpServletResponse response) throws Exception{
    	//客户端无法跳转，返回json信息，由前端页面请求控制
    	response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/json"); 
		PrintWriter out =response.getWriter();//提取出来以后报错，所以每次使用都生成一个
		try{
		JSONObject obj = new JSONObject();
		obj.put("success", false);
		obj.put("logout", true);
		obj.put("message", "登录超时，请重新登录");
		out.println(obj.toJSONString());
		out.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			out.close();
		}
    }

    
}
