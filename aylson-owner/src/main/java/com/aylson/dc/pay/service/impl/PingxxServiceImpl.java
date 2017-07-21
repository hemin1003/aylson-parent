//package com.aylson.dc.pay.service.impl;
//
//
//import java.math.BigDecimal;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.aylson.core.frame.domain.Result;
//import com.aylson.core.frame.domain.ResultCode;
//import com.aylson.dc.base.GeneralConstant.GoldType;
//import com.aylson.dc.base.GeneralConstant.PayBusiType;
//import com.aylson.dc.base.GeneralConstant.PayStatus;
//import com.aylson.dc.base.GeneralConstant.SourceType;
//import com.aylson.dc.mem.service.MemAccountService;
//import com.aylson.dc.mem.service.RechargeConfigService;
//import com.aylson.dc.mem.service.RechargeRecordsService;
//import com.aylson.dc.mem.vo.MemGoldDetailVo;
//import com.aylson.dc.mem.vo.RechargeConfigVo;
//import com.aylson.dc.mem.vo.RechargeRecordsVo;
//import com.aylson.dc.pay.service.PingxxService;
//import com.aylson.dc.sys.common.SystemConfig;
//import com.aylson.utils.BillNumUtils;
//import com.aylson.utils.StringUtil;
//import com.pingplusplus.model.Charge;
//
//
//@Service
//public class PingxxServiceImpl  implements PingxxService {
//
//	@Autowired
//	private RechargeRecordsService rechargeRecordsService;
//	@Autowired
//	private MemAccountService memAccountService;
//	@Autowired
//	private RechargeConfigService rechargeConfigService;
//	
//	/**
//	 * 封装请求参数
//	 * order_no:    required  商户订单号，适配每个渠道对此参数的要求，必须在商户系统内唯一。(alipay: 1-64 位， wx: 2-32 位，bfb: 1-20 位，upacp: 8-40 位，yeepay_wap:1-50 位，jdpay_wap:1-30 位，cnp_u:8-20 位，cnp_f:8-20 位，cmb_wallet:10 位纯数字字符串。注：除cmb_wallet外的其他渠道推荐使用 8-20 位，要求数字或字母，不允许特殊字符)。
//	 * app[id]:     required  支付使用的 app 对象的 id
//	 * channel:     required  支付使用的第三方支付渠道
//	 * amount:      required  订单总金额, 单位为对应币种的最小货币单位，例如：人民币为分（如订单总金额为 1 元，此处请填 100）
//	 * client_ip:   required  发起支付请求客户端的 IP 地址，格式为 IPV4，如: 127.0.0.1。
//	 * currency:    required  三位 ISO 货币代码，目前仅支持人民币 cny。
//	 * subject:     required  商品的标题，该参数最长为 32 个 Unicode 字符，银联全渠道（upacp/upacp_wap）限制在 32 个字节。
//	 * body:        required  商品的描述信息，该参数最长为 128 个 Unicode 字符，yeepay_wap 对于该参数长度限制为 100 个 Unicode 字符。
//	 * extra:       optional  特定渠道发起交易时需要的额外参数以及部分渠道支付成功返回的额外参数。
//	 * time_expire: optional  订单失效时间，用 Unix 时间戳表示。时间范围在订单创建后的 1 分钟到 15 天，默认为 1 天，创建时间以 Ping++ 服务器时间为准。 微信对该参数的有效值限制为 2 小时内；银联对该参数的有效值限制为 1 小时内。
//	 * metadata:    optional  参考 Metadata 元数据。
//	 * description: optional  订单附加说明，最多 255 个 Unicode 字符。
//	 */
//	@Override
//	public Map<String, Object> getChargeParamsMap(String orderNo, String appId, String channel, Integer amount, String clientIp, String subject, String body, String openId) {
//		  Map<String, Object> chargeParams = new HashMap<String, Object>();
//		  Map<String, String> extra = new HashMap<String, String>();
//		  if(StringUtil.isEmpty(channel)){
//			  channel = "wx_pub";
//			  extra.put("open_id",openId);
//		  };
//		  if(StringUtil.isNotEmpty(channel) && channel.equals("wx_pub_qr")){
//	        	extra.put("product_id", orderNo);
//	      }
//		  chargeParams.put("extra", extra);
//		  chargeParams.put("order_no",  orderNo);
//		  chargeParams.put("amount", amount);
//		  Map<String, String> app = new HashMap<String, String>();
//		  app.put("id", appId);
//		  chargeParams.put("app", app);
//		  chargeParams.put("channel",  channel);
//		  chargeParams.put("currency", "cny");
//		  chargeParams.put("client_ip",  clientIp);
//		  chargeParams.put("subject",  subject);
//		  chargeParams.put("body",  body);
//		  return chargeParams;
//	}
//
//	/**
//	 * 获取app_id
//	 * @return
//	 */
//	@Override
//	public String getAppId() {
//		if(SystemConfig.isLiveMode()){
//			return  SystemConfig.getConfigValueByKey("PINGXXLive_APP_ID");
//		}else{
//			return SystemConfig.getConfigValueByKey("PINGXXTest_APP_ID");
//		}
//	}
//
//	/**
//	 * 获取app_key
//	 * @return
//	 */
//	@Override
//	public String getKey() {
//		if(SystemConfig.isLiveMode()){
//			if(SystemConfig.isDebugMode()){
//				return SystemConfig.getConfigValueByKey("PINGXXLive_TEST_KEY");
//			}else{
//				return SystemConfig.getConfigValueByKey("PINGXXLive_LIVE_KEY");
//			}
//		}else{
//			if(SystemConfig.isDebugMode()){
//				return SystemConfig.getConfigValueByKey("PINGXXTest_TEST_KEY");
//			}else{
//				return SystemConfig.getConfigValueByKey("PINGXXTest_LIVE_KEY");
//			}
//		}
//	}
//
//	/**
//	 * 处理支付结果
//	 * @param charge
//	 * @return
//	 */
//	@Override
//	public Result processRecharge(Charge charge) {
//		Result result = new Result();
//		if(charge == null){
//			result.setError(ResultCode.CODE_STATE_4006, "业务类型不明");return result;
//		}
//		try{
//			RechargeRecordsVo record = this.rechargeRecordsService.getByOrderNo(charge.getOrderNo());
//			if(record == null){
//				result.setError(ResultCode.CODE_STATE_4006, "处理充值发生异常：根据支付订单号无法获取订单。订单号:"+charge.getOrderNo());return result;
//			}
//			if(PayBusiType.CHARGE == record.getBusiType()){//充值送金币
//				MemGoldDetailVo memGoldDetailVo = new MemGoldDetailVo();
//				memGoldDetailVo.setGold(record.getGold());
//				memGoldDetailVo.setCreateTime(new Date());
//				memGoldDetailVo.setDescription(record.getDescription());
//				memGoldDetailVo.setMemberId(record.getMemberId());
//				memGoldDetailVo.setType(GoldType.BUY);
//				memGoldDetailVo.setSourceType(SourceType.EXCHANGEGOLD);
//				memGoldDetailVo.setSourceId(record.getId());
//				result  = this.memAccountService.updateGold(memGoldDetailVo);
//			}
//			if(result.isSuccess()){//更新支付记录
//				record.setStatus(PayStatus.CHARGE_FINISH);
//				this.rechargeRecordsService.edit(record);
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return result;
//	}
//
//	/**
//	 * 处理支付请求信息
//	 * @param records
//	 * @return
//	 */
//	@Override
//	public Result processPayRequest(RechargeRecordsVo records) {
//		Result result = new Result();
//		if(records.getAmount() == null ){
//			result.setError(ResultCode.CODE_STATE_4006, "金额不能为空");return result;
//		}
//		if(records.getAmount() <= 0.0f){
//			result.setError(ResultCode.CODE_STATE_4006, "金额不能小于0");return result;
//		}
//		BigDecimal payAmount = new BigDecimal(records.getAmount());
//	    payAmount = payAmount.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
//	    Integer pingxxAmount = payAmount.intValue();
//	    if(pingxxAmount <= 0){
//	    	result.setError(ResultCode.CODE_STATE_4006, "金额不能小于一分钱");return result;
//	    }
//	    records.setPingxxAmount(pingxxAmount); //请求ping++的支付值
//	    if(records.getMemberId() == null){
//	    	result.setError(ResultCode.CODE_STATE_4006, "获取会员信息失败");return result;
//	    }
//	    if(records.getBusiType() == null || records.getBusiId() == null){
//	    	result.setError(ResultCode.CODE_STATE_4006, "业务类型不明");return result;
//	    }
//	    if(PayBusiType.CHARGE == records.getBusiType().intValue()){
//	    	RechargeConfigVo rechargeConfigVo = this.rechargeConfigService.getById(records.getBusiId());
//	    	if(rechargeConfigVo == null){
//	    		result.setError(ResultCode.CODE_STATE_4006, "获取充值配置信息失败");return result;
//	    	}
//	    	BigDecimal configAmount = new BigDecimal(rechargeConfigVo.getAmount());
//	    	configAmount = configAmount.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
//		    if(configAmount.intValue() != records.getPingxxAmount().intValue()){
//			   result.setError(ResultCode.CODE_STATE_4006, "充值金额与配置信息不一致");return result;
//		    };
//		    //订单号
//		    records.setOrderNo("RD" + BillNumUtils.getBillNum(records.getMemberId()));
//		    records.setSubject(rechargeConfigVo.getTitle());
//		    records.setGold(rechargeConfigVo.getBuyGold()+rechargeConfigVo.getSendGold());
//		    //body:会员id#订单号#金额#ping++金额#金币#业务类型#业务id
//		    records.setBody(records.getMemberId()+"#"+records.getOrderNo()+"#"+records.getAmount()+"#"+records.getPingxxAmount()+"#"+records.getGold()+"#"+records.getBusiType()+"#"+records.getBusiId());
//	    }else{
//	    	result.setError(ResultCode.CODE_STATE_4006, "业务类型不明");return result;
//	    }
//	    result.setOK(ResultCode.CODE_STATE_200, "可以充值");
//		return result;
//	}
//	
//
//}
