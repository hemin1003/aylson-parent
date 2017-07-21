package com.aylson.dc.partner.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.partner.po.PartnerAccount;
import com.aylson.dc.partner.search.PartnerAccountSearch;
import com.aylson.dc.partner.vo.PartnerAccountVo;

public interface PartnerAccountService extends BaseService<PartnerAccount,PartnerAccountSearch> {
	
	
	/**
	 * 注册
	 * @param partnerAccountVo
	 * @return
	 */
	public Result register(PartnerAccountVo partnerAccountVo);
	
	/**
	 * 登录
	 * @param partnerAccountVo
	 * @return
	 */
	public Result login(PartnerAccountVo partnerAccountVo);
	
	/**
	 * 修改密码
	 * @param partnerAccountVo
	 * @return
	 */
	public Result modifyPwd(PartnerAccountVo partnerAccountVo);
	
	/**
	 * 获取统计数据
	 * @param accountId
	 * @return
	 */
	public Result getSumResult(Integer accountId);
	
	/**
	 * 获取我的个人信息
	 * @param accountId
	 * @return
	 */
	public Result getMyPersonalInfo(String accountId);
	
	/**
	 * 修改我的个人信息
	 * @param partnerAccountVo
	 * @return
	 */
	public Result modifyPersonalInfo(PartnerAccountVo partnerAccountVo);
	
	/**
	 * 获取个人中心统计数据：申诉未读数，公告未读数
	 * @param accountId
	 * @return
	 */
	public Result getPCSummaryInfo(Integer accountId);
	
	/**
	 * 获取我的合伙人二维码
	 * 说明：根据合伙人id查找信息，
	 * 如果存在二维码地址，直接返回
	 * 如果不存在：请求微信服务器返回二维码，转化成图片上传到七牛，
	 * 成功上传后保存到微信分享关系表（mem_wx_share）里，渠道为：myPartnerQrCode，同时更新合伙人的二维码地址字段
	 * @param accountId
	 * @return
	 */
	public Result getMyOwnerQrCode(Integer accountId);
	
	
}
