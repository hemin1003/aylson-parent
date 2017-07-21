package com.aylson.dc.base;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.aylson.dc.base.common.SystemInitializeJob;
import com.aylson.dc.sys.common.SessionInfo;

public class RequestInterceptor implements HandlerInterceptor{

	private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);
	
	// 这些请求不拦截
    private static final Set<String> UNFILTER_URI = new HashSet<>();
    // 这些开头的请求不拦截
    private static final Set<String> UNFILTER_URI_STARTWITH = new HashSet<>();
    
    static {
    	 //不拦截：完全匹配的请求
    	 UNFILTER_URI.add("/web/captchaImage");				        //获取验证码
    	 UNFILTER_URI.add("/sys/user/login");       				//登录
    	 UNFILTER_URI.add("/sys/user/loginOut");       				//注销登录
    	 //不拦截：开头匹配的请求
    	 UNFILTER_URI_STARTWITH.add("/visitor");//游客
    	
    }
    
    /**
     * 拦截所有请求
     */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		//系统初始化
		SystemInitializeJob.start();
		String servletPath = request.getServletPath();  //获取访问链接
		//System.out.println("================"+servletPath+"================");
		if (unsign(servletPath)){ return true;}   //开始匹配
		if (unfilter(servletPath)){ return true;} //完全匹配
		SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute("sessionInfo");
		if(sessionInfo == null){
			accessResult(response,"未登录或者登录超时，请先登录!");
			return false;
		}
        return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		//System.out.println("afterCompletion");
		response.addHeader("Access-Control-Allow-Origin", "*");
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
		//        System.out.println("postHandle");
		response.addHeader("Access-Control-Allow-Origin", "*");
		
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

    private void accessResult(HttpServletResponse response, String message) throws Exception{
    	//客户端无法跳转，返回json信息，由前端页面请求控制
    	response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/json"); 
		PrintWriter out =response.getWriter();//提取出来以后报错，所以每次使用都生成一个
		try{
			JSONObject obj = new JSONObject();
			obj.put("success", false);
			obj.put("message",message);
			out.println(obj.toJSONString());
			out.flush();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			out.close();
		}
    }
    
}
