//package com.aylson.dc.pay.controller;
//
//import java.io.BufferedReader;
//import java.io.PrintWriter;
//import java.util.Date;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.aylson.core.frame.controller.BaseController;
//import com.aylson.core.frame.domain.Result;
//import com.aylson.core.frame.domain.ResultCode;
//import com.aylson.dc.base.GeneralConstant.PayStatus;
//import com.aylson.dc.mem.service.RechargeRecordsService;
//import com.aylson.dc.mem.vo.RechargeRecordsVo;
//import com.aylson.dc.pay.service.PingxxService;
//import com.aylson.utils.BillNumUtils;
//import com.aylson.utils.StringUtil;
//import com.pingplusplus.Pingpp;
//import com.pingplusplus.exception.PingppException;
//import com.pingplusplus.model.Charge;
//import com.pingplusplus.model.Webhooks;
//
//@Controller
//@RequestMapping("/pay")
//public class PayController extends BaseController {
//
//	@Autowired
//	private PingxxService pingxxService;
//	@Autowired
//	private RechargeRecordsService rechargeRecordsService;
//	
//	
//	/**
//	 * 客户端发起ping++支付请求
//	 * @param orderNo  订单号
//	 * @param amount   应付金额
//	 * @param channel  渠道
//	 * @param memberId 会员id
//	 * @param busiType 业务类型
//	 * @param busiId   业务id
//	 * @return
//	 */
//	@RequestMapping(value = "/requestPingxxPay", method = RequestMethod.POST)
//	@ResponseBody
//	public Result requestPingxxPay(RechargeRecordsVo records){
//		//1、服务端接接收客户端请求参数:params,
//		Result result = new Result();
//		if(StringUtil.isEmpty(records.getOrderNo())){
//			records.setOrderNo(BillNumUtils.getBillCode());
//		}
//		//支付前处理信息
////	    result = this.pingxxService.busiDeal(records);
//		result = this.pingxxService.processPayRequest(records);
//		if(!result.isSuccess()) return result;
//		//获取ping++配置信息
//		String appId = this.pingxxService.getAppId();
//		String aipKey = this.pingxxService.getKey();
//		if(StringUtil.isEmpty(aipKey) || StringUtil.isEmpty(appId)){
//			result.setError(ResultCode.CODE_STATE_4006, "获取ping++配置信息失败");return result;
//		}
//		Pingpp.apiKey = aipKey;
//		//2封装向ping++请求需要的参数
//		Map<String, Object> chargeParamsMap = this.pingxxService.getChargeParamsMap(records.getOrderNo(),appId,records.getChannel(),records.getPingxxAmount(),clientIp,records.getSubject(),records.getBody(),records.getOpenId());
//		try{
//			//3、调用 Server-SDK封装的创建支付 Charge 的方法请求 Ping++
//			Charge ch= Charge.create(chargeParamsMap);
//			//4、Ping++ 响应服务请求，返回的Charge(支付凭据），也是系统服务端需要返回给客户端的
//			if(ch != null){
//				//5、添加充值记录
//				// 插入充值记录
//	            records.setDescription("会员充值");
////	            records.setDescription(records.getSubject()+":"+records.getBody());
//	            records.setChargeId(ch.getId());
//	            records.setCreateTime(new Date());
//	            records.setStatus(PayStatus.HAD_SUMBIT);
//	            Boolean flag = rechargeRecordsService.add(records);
//	            if (!flag) {
//	                logger.error("无法插入支付订单。");
//	                result.setError(ResultCode.CODE_STATE_4006, "无法插入支付订单。");
//	                return result;
//	            }
//				result.setOK(ResultCode.CODE_STATE_200, "", ch);
//			}else{
//				result.setError(ResultCode.CODE_STATE_4006, "支付失败");
//			}
//		}catch(PingppException e){
//			e.printStackTrace();
//            result.setError(ResultCode.CODE_STATE_4006, "支付失败:" + e.getMessage());
//		}catch(Exception e){
//			e.printStackTrace();
//			result.setError(ResultCode.CODE_STATE_4006, "支付失败:" + e.getMessage());
//		}
//		return result;
//	}
//	
//	/**
//	 * ping++采用Webhooks将结果通知系统服务端
//	 * @return
//	 */
//	@RequestMapping(value = "/pingxx/whnotify", method = RequestMethod.POST)
//	public void pingxxPayNotify(HttpServletRequest req, HttpServletResponse resp)  throws Exception{
//		StringBuffer stringBuffer = new StringBuffer();
//        String line;
//        try {
//            BufferedReader reader = req.getReader();
//            while ((line = reader.readLine()) != null)
//                stringBuffer.append(line);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return;
//        }
//        String result;
//        Object obj = Webhooks.getObject(stringBuffer.toString());
//        if (obj == null) {
//            result = "fail";
//            resp.setStatus(405);
//            logger.error("*********************>充值失败回调。Notify.parseNotify转换后为null");
//        }else{
//        	result = "success";
//            resp.setStatus(200);//通知ping++成功获取结果
//            if (obj instanceof Charge) {
//                logger.info("====================>充值成功回调：" + obj.toString());
//                Charge charge = (Charge) obj;
//                Result chargeResult = this.pingxxService.processRecharge(charge);
//                if(!chargeResult.isSuccess()){
//                	 logger.error(chargeResult.getMessage());
//                	 result = "fail";
//                     resp.setStatus(405);
//                }
//            }
//            
//        }
//        resp.setContentType("text/plain; charset=utf-8");
//        PrintWriter out = resp.getWriter();
//        out.print(result);
//        out.close();
//	}
//	
//	/**
//	 * ping++采用Webhooks将结果通知系统服务端（用于测试）
//	 * @return
//	 */
//	@RequestMapping(value = "/pingxx/testWhnotify", method = RequestMethod.POST)
//	public void testWhnotify(HttpServletRequest req, HttpServletResponse resp)  throws Exception{
//		StringBuffer stringBuffer = new StringBuffer();
//        String line;
//        try {
//            BufferedReader reader = req.getReader();
//            while ((line = reader.readLine()) != null)
//                stringBuffer.append(line);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return;
//        }
//        String result;
//        Object obj = Webhooks.getObject(stringBuffer.toString());
//        if (obj == null) {
//            result = "fail";
//            resp.setStatus(405);
//            logger.error("*********************>充值失败回调。Notify.parseNotify转换后为null");
//        }else{
//        	result = "success";
//            resp.setStatus(200);//通知ping++成功获取结果
//            if (obj instanceof Charge) {
//                logger.info("====================>充值成功回调：" + obj.toString());
//                Charge charge = (Charge) obj;
//                Result chargeResult = this.pingxxService.processRecharge(charge);
//                if(!chargeResult.isSuccess()){
//                	 logger.error(chargeResult.getMessage());
//                	 result = "fail";
//                     resp.setStatus(405);
//                }
//            }
//            
//        }
//        resp.setContentType("text/plain; charset=utf-8");
//        PrintWriter out = resp.getWriter();
//        out.print(result);
//        out.close();
//	}
//	
//	
//}
