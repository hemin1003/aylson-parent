package com.aylson.dc.sys.controller;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aylson.core.frame.controller.BaseController;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;


@Controller
@RequestMapping("/")
public class IndexController extends BaseController {
	
	@Autowired
	private Producer captchaProducer;
	
	/**
	 * 获取验证码
	 * @param response
	 */
	@RequestMapping(value = "/captchaImage", method = RequestMethod.GET)
	public void getCaptchaImage(HttpServletResponse response){
		try{
			System.out.println("================================>start vaildateCode");
			response.setDateHeader("Expires", 0);  
	        // Set standard HTTP/1.1 no-cache headers.  
	        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
	        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).  
	        response.addHeader("Cache-Control", "post-check=0, pre-check=0");  
	        // Set standard HTTP/1.0 no-cache header.  
	        response.setHeader("Pragma", "no-cache");  
	        // return a jpeg  
	        response.setContentType("image/jpeg");  
	        // create the text for the image  
	        String capText = captchaProducer.createText();  
	        // store the text in the session  
	        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);  
	        
	        System.out.println("================================>vaildateCode==" +capText);
	        // create the image with the text  
	        BufferedImage bi = captchaProducer.createImage(capText);  
	        ServletOutputStream out = response.getOutputStream();  
	        // write the data out  
	        ImageIO.write(bi, "jpg", out);  
	        try {  
	            out.flush();  
	        } finally {  
	            out.close();  
	        }   		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转到首页
	 * @return
	 */
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	public String toIndex() {
		return "index";
	}
	
	@RequestMapping(value = "/north", method = RequestMethod.GET)
	public String north() {
		return "/jsp/layout/north";
	}

	@RequestMapping(value = "/west", method = RequestMethod.GET)
	public String west() {
		return "/jsp/layout/west";
	}

	@RequestMapping(value = "/center", method = RequestMethod.GET)
	public String center() {
		return "/jsp/layout/center";
	}

	@RequestMapping(value = "/south", method = RequestMethod.GET)
	public String south() {
		return "/jsp/layout/south";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		return "/jsp/layout/home";
	}
	
	
	
	
}
