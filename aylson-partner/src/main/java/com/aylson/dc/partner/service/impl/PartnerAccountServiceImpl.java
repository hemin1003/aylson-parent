package com.aylson.dc.partner.service.impl;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.PartnerGeneralConstant.AccountState;
import com.aylson.dc.base.cache.CacheEntity;
import com.aylson.dc.base.cache.CacheTimerHandler;
import com.aylson.dc.partner.dao.PartnerAccountDao;
import com.aylson.dc.partner.po.PartnerAccount;
import com.aylson.dc.partner.search.PartnerAccountSearch;
import com.aylson.dc.partner.service.PartnerAccountService;
import com.aylson.dc.partner.vo.PartnerAccountVo;
import com.aylson.utils.MD5Encoder;
import com.aylson.utils.QiniuUtils;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;
import com.fastweixin.api.config.ApiConfig;


@Service
public class PartnerAccountServiceImpl extends BaseServiceImpl<PartnerAccount,PartnerAccountSearch> implements PartnerAccountService {

	@Autowired
	private PartnerAccountDao partnerAccountDao;

	@Override
	protected BaseDao<PartnerAccount,PartnerAccountSearch> getBaseDao() {
		return partnerAccountDao;
	}

	@Override
	public Result register(PartnerAccountVo partnerAccountVo) {
		Result result = new Result();
		//信息校验
		if(StringUtil.isEmpty(partnerAccountVo.getPartnerName())){
			result.setError(ResultCode.CODE_STATE_4006, "姓名不能为空");
			return result;
		}
		if(StringUtil.isEmpty(partnerAccountVo.getPwd())){
			result.setError(ResultCode.CODE_STATE_4006, "密码不能为空");
			return result;
		}
		if(StringUtil.isEmpty(partnerAccountVo.getConfirmPwd())){
			result.setError(ResultCode.CODE_STATE_4006, "确认密码不能为空");
			return result;
		}
		if(!partnerAccountVo.getPwd().trim().equals(partnerAccountVo.getConfirmPwd().trim())){
			result.setError(ResultCode.CODE_STATE_4006, "密码和确认密码不一致");
			return result;
		}
		if(StringUtil.isEmpty(partnerAccountVo.getMobilePhone())){
			result.setError(ResultCode.CODE_STATE_4006, "手机号码不能为空");
			return result;
		}
		if(!VerificationUtils.isValid(partnerAccountVo.getMobilePhone(), VerificationUtils.MOBILE)){
			result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
			return result;
		}
		if(StringUtil.isEmpty(partnerAccountVo.getProvince()) || partnerAccountVo.getProvinceId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "省会不能为空");
			return result;
		}
		if(StringUtil.isEmpty(partnerAccountVo.getCity()) || partnerAccountVo.getCityId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "城市不能为空");
			return result;
		}
		if(StringUtil.isEmpty(partnerAccountVo.getReferralName())){
			result.setError(ResultCode.CODE_STATE_4006, "拓展经理姓名不能为空");
			return result;
		}
		if(StringUtil.isEmpty(partnerAccountVo.getReferralPhone())){
			result.setError(ResultCode.CODE_STATE_4006, "拓展经理手机号不能为空");
			return result;
		}
		if(StringUtil.isEmpty(partnerAccountVo.getAgentName())){
			result.setError(ResultCode.CODE_STATE_4006, "推荐代理商不能为空");
			return result;
		}
		if(StringUtil.isEmpty(partnerAccountVo.getAgentPhone())){
			result.setError(ResultCode.CODE_STATE_4006, "推荐代理商电话不能为空");
			return result;
		}
		if(partnerAccountVo.getMobilePhone().equals(partnerAccountVo.getReferralPhone())){
			result.setError(ResultCode.CODE_STATE_4006, "注册人与拓展经理的手机号码不能一样");
			return result;
		}
		if(partnerAccountVo.getMobilePhone().equals(partnerAccountVo.getAgentPhone())){
			result.setError(ResultCode.CODE_STATE_4006, "注册人与代理商的手机号码不能一样");
			return result;
		}
		if(StringUtil.isEmpty(partnerAccountVo.getValidCode())){
			result.setError(ResultCode.CODE_STATE_4006, "手机验证码不能为空");
			return result;
		}
		CacheEntity cacheEntity = CacheTimerHandler.getCache(partnerAccountVo.getMobilePhone());
	    String validCodeSys = "";
	    if (cacheEntity != null) {
			validCodeSys =  cacheEntity.getCacheContext()+"";//发送的验证码
		}
		if(StringUtil.isEmpty(validCodeSys) || !validCodeSys.equals(partnerAccountVo.getValidCode())){
			result.setError(ResultCode.CODE_STATE_4006, "验证码有误！");
			return result;
		}
		PartnerAccountSearch search = new PartnerAccountSearch();
		search.setMobilePhoneLike(partnerAccountVo.getMobilePhone());
		long rows = this.getRowCount(search);
		if(rows > 0){
			result.setError(ResultCode.CODE_STATE_4006, "该手机号的账号已经存在");//账号名重复
			return result;
		}
		//信息有效
		Date curDate = new Date(); //当前时间
		partnerAccountVo.setRegisterTime(curDate);
		partnerAccountVo.setPwd(MD5Encoder.encodeByMD5(partnerAccountVo.getPwd()));
		Boolean flag = this.add(partnerAccountVo);//保存注册申请
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "注册提交成功，请耐心等候审核",partnerAccountVo);
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "注册提交失败，请稍后再试");
		}
		return result;
	}
	
	@Override
	public Result login(PartnerAccountVo partnerAccountVo) {
		Result result = new Result();
		//信息校验
		if(StringUtil.isEmpty(partnerAccountVo.getMobilePhone())){
			result.setError(ResultCode.CODE_STATE_4006, "手机号不能为空");
			return result;
		}
		if(!VerificationUtils.isValid(partnerAccountVo.getMobilePhone(), VerificationUtils.MOBILE)){
			result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
			return result;
		}
		PartnerAccountSearch search = new PartnerAccountSearch();
		search.setMobilePhoneLike(partnerAccountVo.getMobilePhone());
		List<PartnerAccountVo> list = this.getList(search);
		if(list == null || list.size() == 0){
			result.setError(ResultCode.CODE_STATE_4006, "不存在该手机账号，请确认是否信息有误或联系相关人员");
			return result;
		}
		if(list.size() > 1){
			result.setError(ResultCode.CODE_STATE_4006, "存在两个账号，请确认是否信息有误或联系相关人员");
			return result;
		}
		PartnerAccountVo loginer = list.get(0);
		if(AccountState.PASS  != loginer.getState().intValue()){
			result.setError(ResultCode.CODE_STATE_4006, "该手机账号还未通过审核");
			return result;
		}
		if(!loginer.getPwd().trim().equals(MD5Encoder.encodeByMD5(partnerAccountVo.getPwd().trim()))){
			result.setError(ResultCode.CODE_STATE_4006, "密码有误，请重新输入");
			return result;
		}
		loginer.setPwd(null);
		result.setOK(ResultCode.CODE_STATE_200, "登录成功",loginer);
		return result;
	}
	
	@Override
	public Result modifyPwd(PartnerAccountVo partnerAccountVo) {
		Result result = new Result();
		if(partnerAccountVo.getId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取账号信息失败");
			return result;
		}
		if(StringUtil.isEmpty(partnerAccountVo.getPwd())){
			result.setError(ResultCode.CODE_STATE_4006, "现在密码不能为空");
			return result;
		}
		if(StringUtil.isEmpty(partnerAccountVo.getNewPwd())){
			result.setError(ResultCode.CODE_STATE_4006, "新密码不能为空");
			return result;
		}
		if(StringUtil.isEmpty(partnerAccountVo.getConfirmPwd())){
			result.setError(ResultCode.CODE_STATE_4006, "确认密码不能为空");
			return result;
		}
		if(!partnerAccountVo.getNewPwd().trim().equals(partnerAccountVo.getConfirmPwd().trim())){
			result.setError(ResultCode.CODE_STATE_4006, "新密码和确认密码不一致");
			return result;
		}
		PartnerAccountVo loginer = this.getById(partnerAccountVo.getId());
		if(loginer == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取账号信息失败");
			return result;
		}
		if(!loginer.getPwd().trim().equals(MD5Encoder.encodeByMD5(partnerAccountVo.getPwd().trim()))){
			result.setError(ResultCode.CODE_STATE_4006, "现在密码有误，请重新输入");
			return result;
		}
		//信息有效，更新信息密码
		PartnerAccountVo modifyPwd = new PartnerAccountVo();
		modifyPwd.setId(partnerAccountVo.getId());
		modifyPwd.setPwd(MD5Encoder.encodeByMD5(partnerAccountVo.getNewPwd()));
		Boolean flag = this.edit(modifyPwd);
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "操作成功，下次登录请使用新密码");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "操作失败，请稍后再试或联系工作人员");
		}
		return result;
	}

	@Override
	public Result getSumResult(Integer accountId) {
		Result result = new Result();
		Map<String, Object> data = new HashMap<String,Object>();
		if(accountId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取账号信息失败");
			return result;
		}
		List<Float> sumResult = this.partnerAccountDao.selectSumResult(accountId);
		data.put("bonusSum", sumResult.get(0));                        //已获得总分红
		data.put("bonusHadSum", sumResult.get(1));                     //已转账分红
		data.put("orderNum", sumResult.get(2).intValue());             //成单数量
		data.put("couponTotalNum", sumResult.get(3).intValue());       //申请现金券的总数量
		data.put("couponUseNum", sumResult.get(4).intValue());         //已使用的现金券数量 
		data.put("clientNum", sumResult.get(5).intValue());            //我的客户数量
		data.put("notActiveClientNum", sumResult.get(6).intValue());   //未激活的客户数量
		data.put("orderClientNum", sumResult.get(7).intValue());       //使用过优惠券下单的客户数量
		data.put("couponNotUseNum", sumResult.get(8).intValue());      //未使用的现金券数量 
		data.put("couponInvalidNum", sumResult.get(9).intValue());     //已失效的现金券数量 
		result.setOK(ResultCode.CODE_STATE_200, "查询成功",data);
		return result;
	}

	@Override
	public Result getMyPersonalInfo(String accountId) {
		Result result = new Result();
		if(StringUtil.isEmpty(accountId)){
			result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
			return result;
		}
		PartnerAccountVo partnerAccountVo = this.getById(Integer.parseInt(accountId));
		result.setOK(ResultCode.CODE_STATE_200, "查询成功",partnerAccountVo);
		return result;
	}

	@Override
	public Result modifyPersonalInfo(PartnerAccountVo partnerAccountVo) {
		Result result = new Result();
		//1、信息有效性校验  
		if(partnerAccountVo.getId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取账号信息失败！请重新登录再修改");
			return result;
		}
		if(StringUtil.isEmpty(partnerAccountVo.getProvince())){
			result.setError(ResultCode.CODE_STATE_4006, "省会不能为空");
			return result;
		}
		if(StringUtil.isEmpty(partnerAccountVo.getCity())){
			result.setError(ResultCode.CODE_STATE_4006, "城市不能为空");
			return result;
		}
		Boolean flag = this.edit(partnerAccountVo);
		if(flag){
			PartnerAccountVo newPartnerAccountVo = this.getById(partnerAccountVo.getId());
			result.setOK(ResultCode.CODE_STATE_200, "修改成功",newPartnerAccountVo);
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "修改失败");
		}
		return result;
	}

	@Override
	public Result getPCSummaryInfo(Integer accountId) {
		Result result = new Result();
		Map<String, Object> data = new HashMap<String,Object>();
		if(accountId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取账号信息失败");
			return result;
		}
		List<Integer> summaryInfo = this.partnerAccountDao.selectPCSummaryInfo(accountId);
		data.put("noticeUnRead", summaryInfo.get(0));                     //公告未读数
		data.put("feedbackUnRead", summaryInfo.get(1));                   //申诉未读数
		result.setOK(ResultCode.CODE_STATE_200, "查询成功",data);
		return result;
	}

	/**
	 * 获取我的合伙人二维码
	 * 说明：根据合伙人id查找信息，
	 * 如果存在二维码地址，直接返回
	 * 如果不存在：请求微信服务器返回二维码，转化成图片上传到七牛，
	 * 成功上传后保存到微信分享关系表（mem_wx_share）里，渠道为：myPartnerQrCode，同时更新合伙人的二维码地址字段
	 */
	@Override
	public Result getMyOwnerQrCode(Integer accountId) {
		Result result = new Result();
		//根据会员id查询合伙人注册信息
		PartnerAccountVo pa = this.getById(accountId);
		if(pa == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
			return result;
		}
		if(StringUtil.isEmpty(pa.getQrCode())){//如果不存在
			InputStream instreams = this.getWxQrCodeInputStream(accountId);//获取小程序二维码二进制输入流
			if(instreams == null){
				result.setError(ResultCode.CODE_STATE_4006, "请求微信获取我的二维码失败");
				return result;
			}
			//获取小程序二维码成功，上传到七牛
			String fileName = "qrcode_M_"+accountId;
            try {
				int statusCode = QiniuUtils.uploadFile(InputStreamTOByte(instreams), fileName,"aylson-partner",QiniuUtils.UPLOAD_SIMPLE);
				if(statusCode == 200){//成功：返回图片存放地址
					System.out.println("上传成功");
					//更新合伙人的二维码信息
					pa.setQrCode("http://omhhlosqw.bkt.clouddn.com/"+fileName);
					Boolean flag = this.edit(pa);
					if(flag){
						result.setOK(ResultCode.CODE_STATE_200, "获取二维码成功",pa);
					}else{
						result.setError(ResultCode.CODE_STATE_4006, "保存我的二维码失败");
						return result;
					}
					
				}else{//失败：返回失败信息
					result.setError(ResultCode.CODE_STATE_4006, "保存我的二维码失败");
					return result;
				}
			} catch (IOException e) {
				e.printStackTrace();
				result.setError(ResultCode.CODE_STATE_4006, "保存我的二维码失败");
				return result;
			}
		}else{//如果已经存在合伙人二维码地址
			result.setOK(ResultCode.CODE_STATE_200, "获取二维码成功",pa);
		}
		//小程序信息
    	return result;
	}
	
	/**
	 * 获取小程序返回的二维码输入流
	 * @param accountId
	 * @return
	 */
	public InputStream getWxQrCodeInputStream(Integer accountId){
		InputStream instreams = null;
		String appid = "wx174b4388f848e611";
		String secret = "f646c66028959e348c3f03996eb4ff4d";
		ApiConfig wxConfig = new ApiConfig(appid,secret);
		String token = wxConfig.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token="+token;
		String params = "{\"path\": \"pages/register/index?accountId="+accountId+"\", \"width\": 320}";
        // 将JSON进行UTF-8编码,以便传输中文
		try{
			String encoderJson = URLEncoder.encode(params, HTTP.UTF_8);
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	        HttpPost httpPost = new HttpPost(url);
	        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
	        StringEntity se = new StringEntity(params);
	        se.setContentType("text/html");
	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json"));
	        httpPost.setEntity(se);
	        HttpResponse response = httpClient.execute(httpPost);
	        if (response != null) {
	            HttpEntity resEntity = response.getEntity();
	            if (resEntity != null) {
	                instreams = resEntity.getContent(); 
	               
	            }
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
		return instreams;
	}
	
	/**
	 * 将输入流转化成字节数组
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static byte[] InputStreamTOByte(InputStream in) throws IOException{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] data = new byte[1024];  
        int count = -1;  
        while((count = in.read(data,0,1024)) != -1)  
            outStream.write(data, 0, count);  
          
        data = null;  
        return outStream.toByteArray();  
    }  
	

}
