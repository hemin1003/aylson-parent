package com.aylson.dc.mem.service;

import java.util.List;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.mem.po.MemAccount;
import com.aylson.dc.mem.po.MemGoldDetail;
import com.aylson.dc.mem.po.MemIntegralDetail;
import com.aylson.dc.mem.po.MemWalletDetail;
import com.aylson.dc.mem.search.MemAccountSearch;
import com.aylson.dc.mem.search.MemGoldDetailSearch;
import com.aylson.dc.mem.search.MemIntegralDetailSearch;
import com.aylson.dc.mem.search.MemWalletDetailSearch;
import com.aylson.dc.mem.vo.MemAccountVo;

public interface MemAccountService extends BaseService<MemAccount,MemAccountSearch> {
	
	/**
	 * 更新会员积分值，并添加相应的流水
	 * @param memIntegralDetail
	 * @return
	 */
	public Result updateIntegral(MemIntegralDetail memIntegralDetail);
	
	/**
	 * 更新会员金币值，并添加相应的流水
	 * @param memGoldDetail
	 * @return
	 */
	public Result updateGold(MemGoldDetail memGoldDetail);
	
	/**
	 * 更新会员钱包值，并添加相应的流水
	 * @param memWalletDetail
	 * @return
	 */
	public Result updateWallet(MemWalletDetail memWalletDetail);
	
	/**
	 * 通过手机号码获取会员信息
	 * @param mobilePhone
	 * @return
	 */
	public MemAccountVo getByPhone(String mobilePhone);
	
	/**
	 * 通过手机号码和账号类型获取会员信息
	 * @param mobilePhone
	 * @return
	 */
	public MemAccountVo getByPhoneAndMemberType(String mobilePhone, Integer memberType);
	
	/**
	 * 注册
	 * @param memAccountVo
	 * @return
	 */
	public Result register(MemAccountVo memAccountVo);
	
	/**
	 * 会员登录
	 * @param memAccountVo
	 * @return
	 */
	public Result login(MemAccountVo memAccountVo);
	
	/**
	 * 获取我的个人信息
	 * @return
	 */
	public Result getMyPersonalInfo(String memberId);
	
	/**
	 * 修改会员个人信息
	 * @param memAccountVo
	 * @return
	 */
	public Result modifyMemPersonalInfo(MemAccountVo memAccountVo);
	
	/**
	 * 根据会员ID获取会员积分流水明细信息
	 * @param memberId
	 * @return
	 */
	public Result getMemIntegralDetail(MemIntegralDetailSearch memIntegralDetailSearch);
	
	/**
	 * 根据会员ID获取会员金币流水明细信息
	 * @param memberId
	 * @return
	 */
	public Result getMemGoldDetail(MemGoldDetailSearch memGoldDetailSearch);
	
	/**
	 * 获取会员信息列表
	 * @param memAccountSearch
	 * @return
	 */
	public Result getMemAccounts(MemAccountSearch memAccountSearch);
	
	/**
	 * 修改会员密码
	 * @param mobilePhone
	 * @param validCode
	 * @param newPwd
	 * @param confirmPwd
	 * @return
	 */
	public Result modifyPwd(String mobilePhone, String validCode, String newPwd, String confirmPwd, Integer memberType);
	
	/**
	 * 发送验证码前校验信息
	 * @param phone
	 * @param type
	 * @return
	 */
	public Result beforeSendValidCode(String phone, Integer type, Integer memberType);
	
	/**
	 * 根据wxOpenId会员
	 * @param wxOpenId
	 * @return
	 */
	public MemAccount getByWxOpenId(String wxOpenId);
	
	/**
	 * 根据wxOpenId查询微信用户-推荐人绑定关系
	 * @param wxOpenId
	 * @return
	 */
	public List<MemAccountVo> getRelationByWxOpenId(String wxOpenId);
	
	/**
	 * 根据组合条件查询微信用户-推荐人绑定关系
	 * @param wxOpenId
	 * @return
	 */
	public MemAccountVo getRelation(Integer id, Integer referralId, String wxOpenId, String channel);
	
	/**
	 * 根据wxOpenId和渠道查询微信用户-推荐人绑定关系
	 * @param wxOpenId
	 * @return
	 */
	public MemAccountVo getRelationByWxOpenIdAndChannel(String wxOpenId, String channel);
	
	/**
	 * 添加微信用户-推荐人绑定关系
	 * @param memAccount
	 * @return
	 */
	public Boolean addReferralWxUser(MemAccount memAccount);
	
	/**
	 * 删除微信用户-推荐人绑定关系
	 * @param id
	 * @return
	 */
	public Boolean deleteRelationById(Integer id);
	
	/**
	 * 我邀请的人
	 * @param memberId
	 * @return
	 */
	public List<MemAccountVo> getMyInvitesMem(Integer memberId, Integer page, Integer rows);
	
	/**
	 * 校验注册信息
	 * @param memAccountVo
	 * @return
	 */
	public Result validRegisterInfo(MemAccountVo memAccountVo);
	
	/**
	 * 根据会员ID获取会员钱包流水明细信息
	 * @param memberId
	 * @return
	 */
	public Result getMemWalletDetail(MemWalletDetailSearch memWalletDetailSearch);
	
	
	
}
