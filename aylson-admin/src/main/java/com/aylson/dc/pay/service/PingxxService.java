//package com.aylson.dc.pay.service;
//
//import java.util.Map;
//
//import com.aylson.core.frame.domain.Result;
//import com.aylson.dc.mem.vo.RechargeRecordsVo;
//import com.pingplusplus.model.Charge;

///**
// * 处理ping++业务
// * @author wwx
// * @since 2016-08-27
// *
// */
//public interface PingxxService {
//	
//	public Map<String, Object> getChargeParamsMap(String orderNo, String appId, String channel, Integer amount, String clientIp, String subject, String body, String openId);
//	
//	/**
//	 * 获取app_id
//	 * @return
//	 */
//	public String getAppId();
//	
//	/**
//	 * 获取app_key
//	 * @return
//	 */
//	public String getKey();
//	
//	/**
//	 * 处理支付结果
//	 * @param charge
//	 * @return
//	 */
//	public Result processRecharge(Charge charge);
//	
//	/**
//	 * 处理支付请求信息
//	 * @param records
//	 * @return
//	 */
//	public Result processPayRequest(RechargeRecordsVo records);
//	
//}
