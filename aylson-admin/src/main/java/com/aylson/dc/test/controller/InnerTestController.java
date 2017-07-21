package com.aylson.dc.test.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.mem.service.ProjectClientService;

/**
 * 微信框架支持控制器
 * 说明：
 * 	  1、维护与微信平台框架支持，如关注事件之类的处理
 * @author wwx
 * @since  2016-05
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/inner/test")
public class InnerTestController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(InnerTestController.class);
	
	@Autowired
	private ProjectClientService projectClientService;
	
	
	/**
	 * 分配设计师客户资料
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/distribuClientInfo", method = RequestMethod.GET)
	public Result distribuClientInfo() {
		Result result = null;
		try{
			 result = this.projectClientService.distribuClientInfoJob();
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	/**
	 * 忽略设计师客户资料
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ignoreClientInfo", method = RequestMethod.GET)
	public Result ignoreClientInfo() {
		Result result = null;
		try{
			 result = this.projectClientService.ignoreClientInfoJob();
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
	}
	
	public static void main(String[] args) {
		//100个人获取到的概率和是一千，每个人至少获得的概率为1
	    int personCount = 100;            //总参与人数
	    int probabilityTotal = 1000;      //总的概率和
	    int probabilityRange = probabilityTotal/personCount*2;
	    int probabilityHad = 200;           //已经获取的概率和                                //数据库获取
	    int personHad = 0;                //已经获取概率的人数  每次累加                //数据库获取
	    
	    int probabilityGet = 0;           //每次获取的概率数
	    
	    for(int i=0; i<personCount; i++){
	    	//System.out.println(Math.round(Math.random()*20));
	    	probabilityGet = (int)Math.round(Math.random()*20);
	    	//probabilityGet = (int)Math.round(Math.random()*(probabilityTotal-probabilityHad-(personCount-i-1)));
	    	if(probabilityGet < 1){
	    		probabilityGet = 1;
	    	}
	    	System.out.println(probabilityGet);
	    	if(i== personCount-1){//最后一个人，获取剩余的概率数
	    		probabilityGet = probabilityTotal - probabilityHad;
	    	}
	    	personHad += 1;
	    	probabilityHad += probabilityGet;
	    	//保持人数，每次拿剩余的去随机数
	    	
	    }
	    System.out.println("已经获取概率的人数:"+personHad);
	    System.out.println("已经获取的概率和:"+probabilityHad);
	    
	    //主要判断人数，必须达到100个人：1   19
	    int a = 1000;
	    int b = 100;
	    int d = 0;
	    for(int i=0;i<100;i++){
	    	int c = a-(b-1)-d;
	    	//System.out.println(c);
	    }
	    
	    
	    
		
		
	}
	
	
    
	
}
