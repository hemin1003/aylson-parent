package com.aylson.dc.base.controller;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aylson.core.frame.base.Page;
import com.aylson.core.frame.controller.BaseController;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.dc.base.GeneralConstant.UserType;
import com.aylson.dc.owner.search.AppointmentSearch;
import com.aylson.dc.owner.search.OrderSearch;
import com.aylson.dc.owner.search.OwnerInfoSearch;
import com.aylson.dc.owner.service.AppointmentService;
import com.aylson.dc.owner.service.OrderService;
import com.aylson.dc.owner.service.OwnerInfoService;
import com.aylson.dc.owner.vo.AppointmentVo;
import com.aylson.dc.owner.vo.OrderVo;
import com.aylson.dc.sys.common.SessionInfo;
import com.aylson.dc.sys.common.SystemConfig;
import com.aylson.dc.sys.search.MemoSearch;
import com.aylson.dc.sys.service.MemoService;
import com.aylson.dc.sys.vo.MemoVo;
import com.aylson.dc.sys.vo.UserVo;
import com.aylson.utils.DateUtil;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

/**
 * 首页控制器
 * 说明：
 * 	  1、
 *    2、
 * @author wwx
 * @since  2017-04
 * @version v1.0
 *
 */
@Controller
@RequestMapping("/web")
public class WebController extends BaseController {
	

	@Autowired
	private AppointmentService appointmentService;                 //在线预约服务
	@Autowired
	private MemoService memoService;                               //我的备忘服务
	@Autowired
	private OrderService orderService;                             //订单服务
	@Autowired
	private OwnerInfoService ownerInfoService;                     //客户信息服务
	@Autowired
	private Producer captchaProducer;
	
    
    /**
     * 总部用户：    【待办件】【订单总数】【客户数】【我的预约待办列表】【我的备忘录】                              【订单预警】
     * 经销商首页：【待办件】【订单总数】【客户数】【我的预约待办列表】【我的备忘录】                              【成交总金额】【安居艾臣推广二维码】
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getIndex", method = RequestMethod.GET)
    public Result getIndex(){
    	Result result = new Result();
    	long orderNum = 0;                                //订单总数
    	long waitDoNum = 0;                               //待办件数量
    	long clientNum = 0;                               //客户数
    	Float totalAmount = 0.0f;                         //成交总金额
    	String myQrCodeUrl = null;                        //经销商推广二维码
    	Page<AppointmentVo> appointPage = null;           //【我的预约待办列表】
    	Page<MemoVo> memoPage = null;                     //【我的备忘录】
    	Page<OrderVo> orderPage = null;                   //【订单预警】
    	Page<OrderVo> orderWaitDoPage = null;             //【待办订单】
    	Map<String, Object> indexData = new HashMap<String, Object>();
		try{
			SessionInfo sessionInfo = (SessionInfo)this.request.getSession().getAttribute("sessionInfo");
			if(sessionInfo == null){
				result.setError(ResultCode.CODE_STATE_4006, "未登录或者登陆过期，请重新登录再试！");
				return result;
			}
			UserVo userInfo = sessionInfo.getUser();
			//用户类型
			indexData.put("userType", userInfo.getType());
			//我的预约待办列表
			AppointmentSearch appointmentSearch = new AppointmentSearch();
			appointmentSearch.setUserType(userInfo.getType().intValue());
			if(userInfo.getType().intValue() == UserType.AGENT_USER){
				appointmentSearch.setByAgentUserId(userInfo.getId());
			}
			appointmentSearch.setStateType(1);
			appointPage = this.appointmentService.getPage(appointmentSearch);
			waitDoNum = appointPage.getTotal();
			indexData.put("appointPage", appointPage);
			indexData.put("waitDoNum", waitDoNum);
			//客户数
			OwnerInfoSearch ownerInfoSearch = new OwnerInfoSearch();
			if(userInfo.getType().intValue() == UserType.AGENT_USER){
				ownerInfoSearch.setCreaterId(userInfo.getId());
			}
			ownerInfoSearch.setSourceType(1);//预约下单
			clientNum = this.ownerInfoService.getRowCount(ownerInfoSearch);
			indexData.put("clientNum", clientNum);
			//我的备忘录列表
			MemoSearch memoSearch = new MemoSearch();
			memoSearch.setUserId(sessionInfo.getUser().getId());
			memoSearch.setMemoTimeGt(DateUtil.format(new Date()));
			memoPage = this.memoService.getPage(memoSearch);
			indexData.put("memoPage", memoPage);
			//订单总数
			OrderSearch orderSearch2 = new OrderSearch();
			if(userInfo.getType().intValue() == UserType.AGENT_USER){
				orderSearch2.setByAgentUserId(userInfo.getId());
			}
			orderSearch2.setFlowStateType(1);
			List<OrderVo> orderList = this.orderService.getList(orderSearch2);
			orderNum = orderList.size();
			indexData.put("orderNum", orderNum);
			if( UserType.ORG_USER == userInfo.getType()){         //总部用户
				//订单预警
				OrderSearch orderSearch = new OrderSearch();
				orderSearch.setIsWarn(true);
				orderSearch.setSort("warnDays");
				orderSearch.setOrder("desc");
				orderPage = this.orderService.getPage(orderSearch);
				indexData.put("orderPage", orderPage);
				//订单待办
				OrderSearch orderWaitDo = new OrderSearch();
				orderWaitDo.setFlowState(1);
				orderWaitDoPage = this.orderService.getPage(orderWaitDo);
				indexData.put("orderWaitDoPage", orderWaitDoPage);
				
			}else if( UserType.AGENT_USER == userInfo.getType()){ //经销商用户
				if(orderList != null){
					for(OrderVo orderVo:orderList){
						totalAmount += orderVo.getTurnoverAmount();
					}
				}
				indexData.put("totalAmount", totalAmount);
				if(SystemConfig.isLiveMode()){
					myQrCodeUrl = "http://ktz.aylsonclub.com/service/wx/frame/getMyQrCodeUrl?channel=agentOwner&memberId="+userInfo.getId();
				}else{
					myQrCodeUrl = "http://test.aylsonclub.com/dc-web/wx/frame/getMyQrCodeUrl?channel=agentOwner&memberId="+userInfo.getId();
				}
				indexData.put("myQrCodeUrl", myQrCodeUrl);
			}
			result.setOK(ResultCode.CODE_STATE_200, "查询成功",indexData);
		}catch(Exception e){
			e.printStackTrace();
			result.setError(ResultCode.CODE_STATE_500, "查询失败");
		}
		return result;
    }
    
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
	
}
