package com.aylson.dc.mem.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.exception.ServiceException;
import com.aylson.core.frame.base.Page;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.GeneralConstant.GoldType;
import com.aylson.dc.base.GeneralConstant.IntegeralType;
import com.aylson.dc.base.GeneralConstant.IntegralConfigType;
import com.aylson.dc.base.GeneralConstant.MemberType;
import com.aylson.dc.base.GeneralConstant.SourceType;
import com.aylson.dc.base.cache.CacheEntity;
import com.aylson.dc.base.cache.CacheTimerHandler;
import com.aylson.dc.mem.dao.MemAccountDao;
import com.aylson.dc.mem.po.MemAccount;
import com.aylson.dc.mem.po.MemGoldDetail;
import com.aylson.dc.mem.po.MemIntegralDetail;
import com.aylson.dc.mem.po.MemWalletDetail;
import com.aylson.dc.mem.search.MemAccountSearch;
import com.aylson.dc.mem.search.MemGoldDetailSearch;
import com.aylson.dc.mem.search.MemIntegralDetailSearch;
import com.aylson.dc.mem.search.MemWalletDetailSearch;
import com.aylson.dc.mem.service.MemAccountService;
import com.aylson.dc.mem.service.MemGoldDetailService;
import com.aylson.dc.mem.service.MemIntegralDetailService;
import com.aylson.dc.mem.service.MemWalletDetailService;
import com.aylson.dc.mem.service.WithdrawalsApplyService;
import com.aylson.dc.mem.vo.MemAccountVo;
import com.aylson.dc.mem.vo.MemGoldDetailVo;
import com.aylson.dc.mem.vo.MemIntegralDetailVo;
import com.aylson.dc.mem.vo.MemWalletDetailVo;
import com.aylson.dc.sys.common.SystemConfig;
import com.aylson.dc.sys.service.IntegralConfigService;
import com.aylson.dc.sys.vo.IntegralConfigVo;
import com.aylson.utils.DateUtil;
import com.aylson.utils.MD5Encoder;
import com.aylson.utils.StringUtil;
import com.aylson.utils.VerificationUtils;

@Service
public class MemAccountServiceImpl extends BaseServiceImpl<MemAccount,MemAccountSearch> implements MemAccountService {
	
	private static final Logger logger = LoggerFactory.getLogger(MemAccountServiceImpl.class);
	
	@Autowired
	private MemAccountDao memAccountDao;                            //会员信息dao
	@Autowired
	private MemIntegralDetailService memIntegralDetailService;      //会员积分明细服务
	@Autowired
	private MemGoldDetailService memGoldDetailService;              //会员金币明细服务
	@Autowired
	private IntegralConfigService integralConfigService;            //积分配置服务
	@Autowired
	private MemWalletDetailService memWalletDetailService;          //会员钱包明细服务
	@Autowired
	private WithdrawalsApplyService withdrawalsApplyService;        //会员-开拓者提现申请服务
	
	@Override
	protected BaseDao<MemAccount,MemAccountSearch> getBaseDao() {
		return this.memAccountDao;
	}

	/**
	 * 更新会员积分信息，以及添加明细
	 */
	@Override
	@Transactional
	public synchronized Result updateIntegral(MemIntegralDetail memIntegralDetail) {
		Result result = new Result();
		if(memIntegralDetail.getMemberId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取会员信息失败");
			return result;
		}
		if(memIntegralDetail.getIntegral() == null){
			result.setError(ResultCode.CODE_STATE_4006, "积分值不能为空");
			return result;
		}
		if(memIntegralDetail.getType() == null){
			result.setError(ResultCode.CODE_STATE_4006, "未知积分类型");
			return result;
		}
		//获取当前会员信息
		MemAccountVo memAccountVo = this.getById(memIntegralDetail.getMemberId());
		if(memAccountVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取会员信息失败");
			return result;
		}
		//之后积分值
		Integer afterIntegral = memIntegralDetail.getIntegral() + memAccountVo.getIntegral();
		Integer afterTotalIntegral = memAccountVo.getTotalIntegral();
		if(memIntegralDetail.getType() > 0){//获取积分累加
			afterTotalIntegral = afterTotalIntegral + memIntegralDetail.getIntegral();
			Integer integralLevel = this.integralConfigService.getIntegralLevel(afterTotalIntegral);
			if(integralLevel != null ){//更新会员等级
				if((memAccountVo.getIntegralLevel() != null 
						&& integralLevel.intValue() > memAccountVo.getIntegralLevel().intValue())
						  || memAccountVo.getIntegralLevel() == null){
					memAccountVo.setIntegralLevel(integralLevel);
				}
			}
		}
		if(afterIntegral < 0 ){
			result.setError(ResultCode.CODE_STATE_4006, "积分值不足");
			return result;
		}
		memAccountVo.setIntegral(afterIntegral);
		memAccountVo.setTotalIntegral(afterTotalIntegral);//保存累加的积分
		Boolean flag = this.edit(memAccountVo);
		if(flag){
			flag = this.memIntegralDetailService.add(memIntegralDetail);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "更新会员积分值成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "更新会员积分值失败");
				throw new ServiceException("更新会员积分值失败");
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "更新会员积分值失败");
		}
		return result;
	}

	/**
	 * 更新会员金币信息，以及添加明细
	 */
	@Override
	@Transactional
	public synchronized Result updateGold(MemGoldDetail memGoldDetail) {
		Result result = new Result();
		if(memGoldDetail.getMemberId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取会员信息失败");
			return result;
		}
		if(memGoldDetail.getGold() == null){
			result.setError(ResultCode.CODE_STATE_4006, "金币值不能为空");
			return result;
		}
		if(memGoldDetail.getType() == null){
			result.setError(ResultCode.CODE_STATE_4006, "未知金币类型");
			return result;
		}
		//获取当前会员信息
		MemAccountVo memAccountVo = this.getById(memGoldDetail.getMemberId());
		if(memAccountVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取会员信息失败");
			return result;
		}
		//之后积分值
		Integer afterGold = memGoldDetail.getGold() + memAccountVo.getGold();
		if(afterGold < 0 ){
			result.setError(ResultCode.CODE_STATE_4006, "金币值不足");
			return result;
		}
		memAccountVo.setGold(afterGold);
		Boolean flag = this.edit(memAccountVo);
		if(flag){
			flag = this.memGoldDetailService.add(memGoldDetail);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "更新会员金币值成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "更新会员金币值失败");
				throw new ServiceException("更新会员积分值失败");
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "更新会员金币值失败");
		}
		return result;
	}
	
	/**
	 * 更新会员金币信息，以及添加明细
	 */
	@Override
	@Transactional
	public synchronized Result updateWallet(MemWalletDetail memWalletDetail) {
		Result result = new Result();
		if(memWalletDetail.getMemberId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取会员信息失败");
			return result;
		}
		if(memWalletDetail.getWallet() == null){
			result.setError(ResultCode.CODE_STATE_4006, "钱包值不能为空");
			return result;
		}
		if(memWalletDetail.getType() == null){
			result.setError(ResultCode.CODE_STATE_4006, "未知类型");
			return result;
		}
		//获取当前会员信息
		MemAccountVo memAccountVo = this.getById(memWalletDetail.getMemberId());
		if(memAccountVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取会员信息失败");
			return result;
		}
		//之后积分值
		Float afterWallet = memWalletDetail.getWallet() + memAccountVo.getWallet();
		if(afterWallet < 0.0f ){
			result.setError(ResultCode.CODE_STATE_4006, "钱包值不足");
			return result;
		}
		memAccountVo.setWallet(afterWallet);
		Boolean flag = this.edit(memAccountVo);
		if(flag){
			flag = this.memWalletDetailService.add(memWalletDetail);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "更新会员金币值成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "更新会员金币值失败");
				throw new ServiceException("更新会员积分值失败");
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "更新会员金币值失败");
		}
		return result;
	}

	/**
	 * 根据电话号码查看用户信息
	 */
	@Override
	public MemAccountVo getByPhone(String mobilePhone) {
		if(VerificationUtils.isValid(mobilePhone, VerificationUtils.MOBILE)){
			MemAccountSearch memAccountSearch = new MemAccountSearch();
			memAccountSearch.setMobilePhone(mobilePhone);
			List<MemAccountVo> list = this.getList(memAccountSearch);
			if(list != null && list.size() > 0){
				return list.get(0);
			}
		}
		return null;
	}
	
	/**
	 * 通过手机号码和账号类型获取会员信息
	 * @param mobilePhone
	 * @param memberType
	 * @return
	 */
	@Override
	public MemAccountVo getByPhoneAndMemberType(String mobilePhone, Integer memberType) {
		//手机号码有效和账号类型有效
		if(VerificationUtils.isValid(mobilePhone, VerificationUtils.MOBILE) && (memberType != null || MemberType.DESIGN == memberType
				|| MemberType.WORK == memberType)){
			MemAccountSearch memAccountSearch = new MemAccountSearch();
			memAccountSearch.setMobilePhone(mobilePhone);
			memAccountSearch.setMemberType(memberType);
			List<MemAccountVo> list = this.getList(memAccountSearch);
			if(list != null && list.size() > 0){
				return list.get(0);
			}
		}
		return null;
	}

	/**
	 * 会员注册
	 */
	@Override
	@Transactional
	public Result register(MemAccountVo memAccountVo) {
		//1、校验注册信息
		Result result = this.validRegisterInfo(memAccountVo);
		if(!result.isSuccess()) return result;            //注册信息有误，返回错误信息
		int memberType = memAccountVo.getMemberType();    //获取会员类型，方便判断
		//2、处理业务逻辑：不同类型的会员注册赠送推荐人不同的积分
		MemAccountVo referraler = null;//我的推荐人
		//设计师联盟通过【官网】输入手机号码获取推荐人信息
		if(memAccountVo.getReferralId() == null && StringUtil.isNotEmpty(memAccountVo.getReferralPhone())){
			referraler = this.getReferralerByPhoneAndMemberType(memAccountVo.getMobilePhone(),memAccountVo.getMemberType());
			if(referraler == null){
				result.setError(ResultCode.CODE_STATE_4006, "找不到推荐人电话号码的会员信息");
				return result;
			}
			memAccountVo.setReferralId(referraler.getId());
			memAccountVo.setReferralName(referraler.getAccountName());
			memAccountVo.setByAgent(referraler.getByAgent());
			memAccountVo.setByAgentUserId(referraler.getByAgentUserId());
		}
		if(MemberType.DESIGN != memberType){//如果注册的不是设计师，那么不归属与某个代理商
			memAccountVo.setByAgent(null);
			memAccountVo.setByAgentUserId(null);
		}
		if(MemberType.WORK == memberType || MemberType.PIONEER == memberType){//如果是产业工人、开拓者注册，会员账号与电话号码一样
			memAccountVo.setAccountName(memAccountVo.getMobilePhone());
		}
		Date curDate = new Date(); //当前时间
		memAccountVo.setRegisterTime(curDate);
		memAccountVo.setAccountName(memAccountVo.getAccountName().trim());
		memAccountVo.setPwd(MD5Encoder.encodeByMD5(memAccountVo.getPwd()));
		Boolean flag = this.add(memAccountVo);//添加新会员
		if(flag){//处理注册赠送
			if(MemberType.DESIGN == memberType){//注册为设计师，需要赠送相应的金币
				Integer gold = SystemConfig.sendGoldForRegister();
				if(gold != 0){
					MemGoldDetail memGoldDetail = new MemGoldDetail(null, memAccountVo.getId(), GoldType.REGISTER, gold, curDate, 
								GoldType.GoldTypeMap.get(GoldType.REGISTER), SourceType.MEMREGISTER, memAccountVo.getId());
					result = this.updateGold(memGoldDetail);
					if(!result.isSuccess()){throw new ServiceException("注册赠送会员金币失败");}
				}
			}
			//注册不同类型的会员赠送不同的积分
			if(memAccountVo.getReferralId() != null){
				//赠送我的推荐人一定的积分
				IntegralConfigVo myReferralerIntegral = null;
				if(MemberType.DESIGN == memberType){
					myReferralerIntegral = this.integralConfigService.getIntegralConfig(IntegralConfigType.D_REGISTER_TO_REFERRAL, null);
				}else if(MemberType.WORK == memberType){
					myReferralerIntegral = this.integralConfigService.getIntegralConfig(IntegralConfigType.W_REGISTER_TO_REFERRAL, null);
				}else if(MemberType.PIONEER == memberType){
					myReferralerIntegral = this.integralConfigService.getIntegralConfig(IntegralConfigType.P_REGISTER_TO_REFERRAL, null);
				}
				//赠送新用户的推荐人积分
				if(myReferralerIntegral != null && myReferralerIntegral.getIntegral().intValue() != 0){
					MemIntegralDetail memIntegralDetail = new MemIntegralDetail(null, memAccountVo.getReferralId(), IntegeralType.SEND_BY_REGISTER_REFERRAL,
							myReferralerIntegral.getIntegral(), curDate, "推荐会员注册成功赠送", SourceType.MEMREGISTER, memAccountVo.getId());
					result = this.updateIntegral(memIntegralDetail);//赠送我的推荐人积分
					if(!result.isSuccess()){
						result.setError(ResultCode.CODE_STATE_4006, "注册通过赠送推荐人积分失败");
						throw new ServiceException("注册通过赠送推荐人积分失败");
					}
					if(referraler == null){
						referraler = this.getById(memAccountVo.getReferralId());
					}
					//赠送新用户的推荐人的推荐人一定积分
					if(referraler != null && referraler.getReferralId() != null){
						//获取应该赠送我的推荐人的推荐人的积分
						//Integer myReferralerIntegralParent = 0;  
						IntegralConfigVo myReferralerIntegralParent = null;
						if(MemberType.DESIGN == memberType){
							myReferralerIntegralParent = this.integralConfigService.getIntegralConfig(IntegralConfigType.D_REGISTER_TO_REFERRAL_PARENT, null);
						}else if(MemberType.WORK == memberType){
							myReferralerIntegralParent = this.integralConfigService.getIntegralConfig(IntegralConfigType.W_REGISTER_TO_REFERRAL_PARENT, null);
						}else if(MemberType.PIONEER == memberType){
							myReferralerIntegralParent = this.integralConfigService.getIntegralConfig(IntegralConfigType.P_REGISTER_TO_REFERRAL_PARENT, null);
						}
						if(myReferralerIntegralParent != null && myReferralerIntegralParent.getIntegral().intValue() != 0){
							MemIntegralDetail memIntegralDetail2 = new MemIntegralDetail(null, referraler.getReferralId(), IntegeralType.SEND_BY_REGISTER_REFERRAL_PARENT,
									myReferralerIntegralParent.getIntegral(), curDate, "推荐会员推荐其他会员注册成功赠送", SourceType.MEMREGISTER, memAccountVo.getId());
							result = this.updateIntegral(memIntegralDetail2);
							if(!result.isSuccess()){
								result.setError(ResultCode.CODE_STATE_4006, "注册通过赠送推荐人积分失败");
								throw new ServiceException("注册通过赠送推荐人积分失败");
							}
						}
					}
				}
			}
			result.setOK(ResultCode.CODE_STATE_200, "注册成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "注册失败");
		}
		return result;
	}

	/**
	 * 会员登录
	 * @param memAccountVo
	 * @return
	 */
	@Override
	public Result login(MemAccountVo memAccountVo) {
		Result result = new Result();
		//1、信息有效性校验      
		if(StringUtil.isEmpty(memAccountVo.getAccountName())){
			result.setError(ResultCode.CODE_STATE_4006, "账号名不能为空");
			return result;
		}
		if(memAccountVo.getMemberType() == null || (MemberType.DESIGN != memAccountVo.getMemberType().intValue()
				&& MemberType.WORK != memAccountVo.getMemberType().intValue()
					&& MemberType.PIONEER != memAccountVo.getMemberType().intValue())){
			result.setError(ResultCode.CODE_STATE_4006, "登录账号类型未知");
			return result;
		}
		MemAccountSearch memAccountSearch = new MemAccountSearch();
		memAccountSearch.setAccountName(memAccountVo.getAccountName().trim());
		memAccountSearch.setMemberType(memAccountVo.getMemberType());
		List<MemAccountVo> memAccountVoList = this.getList(memAccountSearch);
		if(memAccountVoList == null || memAccountVoList.size() == 0){
			result.setError(ResultCode.CODE_STATE_4006, "不存在该账号，请确认是否信息有误或联系相关人员");
			return result;
		}
		if(memAccountVoList.size() > 1){
			result.setError(ResultCode.CODE_STATE_4006, "存在两个账号，请确认是否信息有误或联系相关人员");
			return result;
		}
		MemAccountVo loginMem = memAccountVoList.get(0);
		if(!loginMem.getPwd().trim().equals(MD5Encoder.encodeByMD5(memAccountVo.getPwd().trim()))){
			result.setError(ResultCode.CODE_STATE_4006, "密码有误，请重新输入");
			return result;
		}
		loginMem.setPwd(null);
		result.setOK(ResultCode.CODE_STATE_200, "登录成功",loginMem);
		return result;
	}
	
	/**
	 * 获取我的个人信息
	 * @return
	 */

	@Override
	public Result getMyPersonalInfo(String memberId) {
		Result result = new Result();
		if(StringUtil.isEmpty(memberId)){
			result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
			return result;
		}
		MemAccountVo memAccountVo = this.getById(Integer.parseInt(memberId));
		if(memAccountVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
			return result;
		}
		if(memAccountVo.getBirthday() != null){
			memAccountVo.setBirthdayStr(DateUtil.format(memAccountVo.getBirthday(), true));
		}
		if(SystemConfig.isLiveMode()){
			memAccountVo.setAppId(SystemConfig.getConfigValueByKey("Formal_AppID"));
		}else{
			memAccountVo.setAppId(SystemConfig.getConfigValueByKey("Test_AppID"));
		}
		if(memAccountVo.getMemberType() != null && MemberType.PIONEER == memAccountVo.getMemberType().intValue()){
			memAccountVo.setHadWithdrawlasAmout(this.withdrawalsApplyService.sumByApplierId(memAccountVo.getId()));
		}

		result.setOK(ResultCode.CODE_STATE_200, "查询成功",memAccountVo);
		return result;
	}
	
	/**
	 * 修改会员个人信息
	 * @param memAccountVo
	 * @return
	 */
	@Override
	public Result modifyMemPersonalInfo(MemAccountVo memAccountVo) {
		Result result = new Result();
		//1、信息有效性校验  
		if(memAccountVo.getId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取账号信息失败！请重新登录再修改");
			return result;
		}
		/*if(StringUtil.isEmpty(memAccountVo.getCompanyName())){
			result.setError(ResultCode.CODE_STATE_4006, "工作单位不能为空");
			return result;
		}*/
		if(StringUtil.isEmpty(memAccountVo.getProvince())){
			result.setError(ResultCode.CODE_STATE_4006, "省会不能为空");
			return result;
		}
		if(StringUtil.isEmpty(memAccountVo.getCity())){
			result.setError(ResultCode.CODE_STATE_4006, "城市不能为空");
			return result;
		}
		if(StringUtil.isEmpty(memAccountVo.getArea())){
			result.setError(ResultCode.CODE_STATE_4006, "区域不能为空");
			return result;
		}
		if(StringUtil.isEmpty(memAccountVo.getAddress())){
			result.setError(ResultCode.CODE_STATE_4006, "地址不能为空");
			return result;
		}
		memAccountVo.setAccountName(null);
		memAccountVo.setReferralId(null);
		memAccountVo.setReferralName(null);
		memAccountVo.setRegisterTime(null);
		memAccountVo.setIntegral(null);
		memAccountVo.setGold(null);
		memAccountVo.setPwd(null);
		memAccountVo.setStatus(null);
		if(StringUtil.isNotEmpty(memAccountVo.getBirthdayStr())){
			memAccountVo.setBirthday(DateUtil.format(memAccountVo.getBirthdayStr(), "yyyy-MM-dd"));
		}
		Boolean flag = this.edit(memAccountVo);
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "修改成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "修改失败");
		}
		return result;
	}
	
	/**
	 * 根据会员ID获取会员积分流水明细信息
	 * @param memberId
	 * @return
	 */
	@Override
	public Result getMemIntegralDetail(MemIntegralDetailSearch memIntegralDetailSearch) {
		Result result = new Result();
		if(memIntegralDetailSearch.getMemberId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "会员账号ID不能为空");
			return result;
		}
		Page<MemIntegralDetailVo> page = this.memIntegralDetailService.getPage(memIntegralDetailSearch);
		result.setOK(ResultCode.CODE_STATE_200, "", page);
		return result;
	}
	
	/**
	 * 根据会员ID获取会员积分流水明细信息
	 * @param memberId
	 * @return
	 */
	@Override
	public Result getMemGoldDetail(MemGoldDetailSearch memGoldDetailSearch) {
		Result result = new Result();
		if(memGoldDetailSearch.getMemberId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "会员账号ID不能为空");
			return result;
		}
		Page<MemGoldDetailVo> page = this.memGoldDetailService.getPage(memGoldDetailSearch);
		result.setOK(ResultCode.CODE_STATE_200, "", page);
		return result;
	}
	
	/**
	 * 获取会员信息列表
	 * @param memAccountSearch
	 * @return
	 */
	@Override
	public Result getMemAccounts(MemAccountSearch memAccountSearch) {
		Result result = new Result();
		memAccountSearch.setStatus(1);   //有效账号：已审核
		Page<MemAccountVo> page = this.getPage(memAccountSearch);
		for(MemAccountVo temp : page.getRowsObject()){
			temp.setPwd(null);
			temp.setMobilePhone(null);
			temp.setIntegral(null);
			temp.setGold(null);
		}
		result.setOK(ResultCode.CODE_STATE_200, "", page);
		return result;
	}
	
	/**
	 * 修改会员密码
	 * @param mobilePhone
	 * @param validCode
	 * @param newPwd
	 * @param confirmPwd
	 * @return
	 */
	@Override
	public Result modifyPwd(String mobilePhone, String validCode, String newPwd, String confirmPwd,
			Integer memberType) {
		Result result = new Result();
		Boolean flag = false;
		//1、信息有效性校验  
//		if(memberType == null || MemberType.DESIGN != memberType.intValue()
//				|| MemberType.WORK != memberType.intValue()){
//			result.setError(ResultCode.CODE_STATE_4006, "注册账号类型未知");
//			return result;
//		}
		if(StringUtil.isEmpty(mobilePhone)){
			result.setError(ResultCode.CODE_STATE_4006, "手机号码不能为空");
			return result;
		}
		if(!VerificationUtils.isValid(mobilePhone, VerificationUtils.MOBILE)){
			result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
			return result;
		}
		if(StringUtil.isEmpty(newPwd)){
			result.setError(ResultCode.CODE_STATE_4006, "密码不能为空");
			return result;
		}
		if(StringUtil.isEmpty(confirmPwd)){
			result.setError(ResultCode.CODE_STATE_4006, "确认密码不能为空");
			return result;
		}
		if(!newPwd.trim().equals(confirmPwd.trim())){
			result.setError(ResultCode.CODE_STATE_4006, "密码和确认密码不一致");
			return result;
		}
		if(StringUtil.isEmpty(validCode)){
			result.setError(ResultCode.CODE_STATE_4006, "手机验证码不能为空");
			return result;
		}
	    CacheEntity cacheEntity = CacheTimerHandler.getCache(mobilePhone);
	    String validCodeSys = "";
	    if (cacheEntity != null) {
			validCodeSys =  cacheEntity.getCacheContext()+"";//发送的验证码
		}
		if(StringUtil.isEmpty(validCodeSys) || !validCodeSys.equals(validCode)){
			result.setError(ResultCode.CODE_STATE_4006, "验证码有误！");
			return result;
		}
		//信息有效，持久化新密码
		MemAccountVo memAccountVo = this.getByPhone(mobilePhone);
		if(memAccountVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "找不到该手机号码的会员信息");
			return result;
		}
		memAccountVo.setPwd(MD5Encoder.encodeByMD5(newPwd));
		flag = this.edit(memAccountVo);
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
		}
		return result;
	}

	/**
     * 发送验证码之前校验
     * @param phone
     * @param type
     * @return
     */
	@Override
	public Result beforeSendValidCode(String phone, Integer type, Integer memberType) {
		Result result = new Result();
	    if(type == null){
        	result.setError(ResultCode.CODE_STATE_4006, "类型未知，无法发送");
			return result;
        }
	    if(StringUtil.isEmpty(phone)){
			result.setError(ResultCode.CODE_STATE_4006, "手机号码不能为空");
			return result;
		}
		if(!VerificationUtils.isValid(phone, VerificationUtils.MOBILE)){
			result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
			return result;
		}
		if(memberType == null){
			result.setError(ResultCode.CODE_STATE_4006, "用户类型未知");
			return result;
		}
		if(MemberType.DESIGN != memberType.intValue() && MemberType.WORK != memberType.intValue()
				&& MemberType.PIONEER != memberType.intValue()){
			result.setError(ResultCode.CODE_STATE_4006, "用户类型未知");
			return result;
		}
		// 判断手机号码是否已经注册
		MemAccountSearch memAccountSearch = new MemAccountSearch();
		memAccountSearch.setMobilePhone(phone);
		memAccountSearch.setMemberType(memberType);
		long rows = this.getRowCount(memAccountSearch);
		if(rows > 0 && type == 0){//注册校验，已经注册，不能重复注册
			result.setError(ResultCode.CODE_STATE_4006, "该手机号码已经存在，不能重复注册");
			return result;
		}
		if(rows <=0 && type == 1){
			result.setError(ResultCode.CODE_STATE_4006, "该手机号码的用户不存在，不能发送");
			return result;
		}
		result.setOK(ResultCode.CODE_STATE_200, "信息有效");
		return result;
	}

	/**
	 * 根据wxOpenId会员
	 * @param wxOpenId
	 * @return
	 */
	@Override
	public MemAccount getByWxOpenId(String wxOpenId) {
		if(StringUtil.isNotEmpty(wxOpenId)){
			MemAccountSearch memAccountSearch = new MemAccountSearch();
			memAccountSearch.setWxOpenId(wxOpenId);
			List<MemAccountVo> list = this.getList(memAccountSearch);
			if(list != null && list.size() > 0){
				return list.get(0);
			}
		}
		return null;
	}
	
	/**
	 * 根据wxOpenId查询微信用户-推荐人绑定关系
	 * @param wxOpenId
	 * @return
	 */
	@Override
	public List<MemAccountVo> getRelationByWxOpenId(String wxOpenId) {
		if(StringUtil.isNotEmpty(wxOpenId)){
			return this.memAccountDao.selectRelationByWxOpenId(wxOpenId);
		}
		return null;
	}
	
	/**
	 * 根据组合条件查询微信用户-推荐人绑定关系
	 * @param wxOpenId
	 * @return
	 */
	@Override
	public MemAccountVo getRelation(Integer id, Integer referralId, String wxOpenId, String channel){
		MemAccountVo memAccountVo = null;
		Map<String, Object> params = new HashMap<String, Object>();
		if(id != null){
			params.put("id", id);
		}
		if(referralId != null){
			params.put("referralId", referralId);
		}
		if(StringUtil.isNotEmpty(wxOpenId)){
			params.put("wxOpenId", wxOpenId);
		}
		if(StringUtil.isNotEmpty(channel)){
			params.put("channel", channel);
		}
		if(!params.isEmpty()){
			List<MemAccountVo> relation = this.memAccountDao.selectRelation(params);
			if(relation != null && relation.size() > 0){
				memAccountVo =  relation.get(0);
			}
		}
		return memAccountVo;
	}
	
	/**
	 * 根据wxOpenId和渠道查询微信用户-推荐人绑定关系
	 * @param wxOpenId
	 * @return
	 */
	public MemAccountVo getRelationByWxOpenIdAndChannel(String wxOpenId, String channel){
		return null;
	}

	/**
	 * 添加微信用户-推荐人绑定关系
	 * @param memAccount
	 * @return
	 */
	@Override
	public Boolean addReferralWxUser(MemAccount memAccount) {
		return this.memAccountDao.insertReferralWxUser(memAccount);
	}
	
	/**
	 * 删除微信用户-推荐人绑定关系
	 * @param id
	 * @return
	 */
	@Override
	public Boolean deleteRelationById(Integer id){
		if(id == null) return false;
		return this.memAccountDao.deleteRelationById(id);
	};

	/**
	 * 我邀请的人
	 * @param memberId
	 * @return
	 */
	@Override
	public List<MemAccountVo> getMyInvitesMem(Integer memberId, Integer page, Integer rows) {
		if(memberId != null){
			MemAccountSearch search = new MemAccountSearch();
			if(page != null){search.setPage(page);}
			if(rows != null){search.setRows(rows);}
			search.setIsPage(true);
			search.setReferralId(memberId);
			List<MemAccountVo> myInvitesMem = this.getList(search);
		    if(myInvitesMem != null && myInvitesMem.size() > 0){
		    	for(MemAccountVo temp : myInvitesMem){
		    		temp.setPwd(null);
		    		temp.setGold(null);
		    		temp.setIntegral(null);
		    		temp.setTotalIntegral(null);
		    		temp.setWxOpenid(null);
		    		temp.setWxUnionId(null);
		    	}
		    }
			return myInvitesMem;
		}
		return null;
	}

	/**
	 * 校验注册信息
	 * @param memAccountVo
	 * 信息有效性校验   
	 * 账户类型，校验不同：
	 * 共同校验：手机号码，验证码，密码
	 * 设计师校验：省会、城市、区域、地址、账号名
	 * 产业工人校验：省会、城市、区域、地址
	 * @return
	 */
	@Override
	public Result validRegisterInfo(MemAccountVo memAccountVo) {
		Result result = new Result();
		//1、共性校验
		//1.1、判断会员类型
		if(memAccountVo.getMemberType() == null){
			result.setError(ResultCode.CODE_STATE_4006, "注册账号类型不能为空");
			return result;
		}
		if(MemberType.DESIGN != memAccountVo.getMemberType().intValue()
				&& MemberType.WORK != memAccountVo.getMemberType().intValue()
					&& MemberType.PIONEER != memAccountVo.getMemberType().intValue()){
			result.setError(ResultCode.CODE_STATE_4006, "注册账号类型未知");
			return result;
		}
		//1.2、密码校验
		if(StringUtil.isEmpty(memAccountVo.getPwd())){
			result.setError(ResultCode.CODE_STATE_4006, "密码不能为空");
			return result;
		}
		if(StringUtil.isEmpty(memAccountVo.getConfirmPwd())){
			result.setError(ResultCode.CODE_STATE_4006, "确认密码不能为空");
			return result;
		}
		if(!memAccountVo.getPwd().trim().equals(memAccountVo.getConfirmPwd().trim())){
			result.setError(ResultCode.CODE_STATE_4006, "密码和确认密码不一致");
			return result;
		}
		//1.3、手机号码校验
		 if(StringUtil.isEmpty(memAccountVo.getMobilePhone())){
			result.setError(ResultCode.CODE_STATE_4006, "手机号码不能为空");
			return result;
		}
		if(!VerificationUtils.isValid(memAccountVo.getMobilePhone(), VerificationUtils.MOBILE)){
			result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
			return result;
		}
		//1.4、手机验证码校验
		/*
		if(StringUtil.isEmpty(memAccountVo.getValidCode())){
			result.setError(ResultCode.CODE_STATE_4006, "手机验证码不能为空");
			return result;
		}
		CacheEntity cacheEntity = CacheTimerHandler.getCache(memAccountVo.getMobilePhone());
	    String validCodeSys = "";
	    if (cacheEntity != null) {
			validCodeSys =  cacheEntity.getCacheContext()+"";//发送的验证码
		}
		if(StringUtil.isEmpty(validCodeSys) || !validCodeSys.equals(memAccountVo.getValidCode())){
			result.setError(ResultCode.CODE_STATE_4006, "验证码有误！");
			return result;
		}*/
		//2、差异性校验
		MemAccountSearch memAccountSearch = new MemAccountSearch();
		String message = null;
		if(MemberType.DESIGN == memAccountVo.getMemberType().intValue()){       //设计师校验
			//2.1、账号名校验
			if(StringUtil.isEmpty(memAccountVo.getAccountName())){
				result.setError(ResultCode.CODE_STATE_4006, "账号名不能为空");
				return result;
			}
			//2.2、省会校验
			if(StringUtil.isEmpty(memAccountVo.getProvince()) || memAccountVo.getProvinceId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "省会不能为空");
				return result;
			}
			//2.3、城市校验
			if(StringUtil.isEmpty(memAccountVo.getCity()) || memAccountVo.getCityId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "城市不能为空");
				return result;
			}
			//2.4、区域校验
			if(StringUtil.isEmpty(memAccountVo.getArea()) || memAccountVo.getAreaId() == null){
				result.setError(ResultCode.CODE_STATE_4006, "区域不能为空");
				return result;
			}
			//2.5、地址校验
			if(StringUtil.isEmpty(memAccountVo.getAddress())){
				result.setError(ResultCode.CODE_STATE_4006, "地址不能为空");
				return result;
			}
			memAccountSearch.setAccountName(memAccountVo.getAccountName().trim());
			memAccountSearch.setMemberType(MemberType.DESIGN);
			message = "该账号名已经存在，不能重复注册";
		}else if(MemberType.WORK == memAccountVo.getMemberType().intValue()){   //产业工人校验
			memAccountSearch.setAccountName(memAccountVo.getMobilePhone().trim());
			memAccountSearch.setMemberType(MemberType.WORK);
			message = "该手机号码已经存在，不能重复注册";
		}else if(MemberType.PIONEER == memAccountVo.getMemberType().intValue()){//开拓者校验
			memAccountSearch.setAccountName(memAccountVo.getMobilePhone().trim());
			memAccountSearch.setMemberType(MemberType.PIONEER);
			message = "该手机号码已经存在，不能重复注册";
		}
		//账号名唯一性校验
		long rows = this.getRowCount(memAccountSearch);
		if(rows > 0){
			result.setError(ResultCode.CODE_STATE_4006, message);//账号名重复
			return result;
		}
		//手机号码唯一性校验
		memAccountSearch.setAccountName(null);
		memAccountSearch.setMobilePhone(memAccountVo.getMobilePhone().trim());
		rows = this.getRowCount(memAccountSearch);
		if(rows > 0){
			result.setError(ResultCode.CODE_STATE_4006, "该手机号码已经存在，不能重复注册");
			return result;
		}
		result.setOK(ResultCode.CODE_STATE_200, "信息有效");
		return result;
	}
	
	/**
	 * 根据电话号码和会员类型获取推荐人信息
	 * @param mobilePhone
	 * @param memberType
	 * @return
	 */
	public MemAccountVo getReferralerByPhoneAndMemberType(String mobilePhone, Integer memberType){
		MemAccountVo referraler = null;
		if(StringUtil.isNotEmpty(mobilePhone) && memberType != null){
			MemAccountSearch memAccountSearch = new MemAccountSearch();
			memAccountSearch.setMobilePhone(mobilePhone.trim());
			memAccountSearch.setMemberType(memberType);
			List<MemAccountVo> list = this.getList(memAccountSearch);
			if(list != null && list.size() > 0){
				referraler = list.get(0);
			}
		}
		return referraler;
	}
	
	public Result register1(MemAccountVo memAccountVo) {
		//1、校验注册信息
		Result result = this.validRegisterInfo(memAccountVo);
		if(!result.isSuccess()) return result;            //注册信息有误，返回错误信息
		int memberType = memAccountVo.getMemberType();    //获取会员类型，方便判断
		//2、处理业务逻辑：不同类型的会员注册赠送推荐人不同的积分
		MemAccountVo referraler = null;//我的推荐人
		//设计师联盟通过【官网】输入手机号码获取推荐人信息
		if(memAccountVo.getReferralId() == null && StringUtil.isNotEmpty(memAccountVo.getReferralPhone())){
			referraler = this.getReferralerByPhoneAndMemberType(memAccountVo.getMobilePhone(),memAccountVo.getMemberType());
			if(referraler == null){
				result.setError(ResultCode.CODE_STATE_4006, "找不到推荐人电话号码的会员信息");
				return result;
			}
			memAccountVo.setReferralId(referraler.getId());
			memAccountVo.setReferralName(referraler.getAccountName());
			memAccountVo.setByAgent(referraler.getByAgent());
			memAccountVo.setByAgentUserId(referraler.getByAgentUserId());
		}
		if(MemberType.DESIGN != memberType){//如果注册的不是设计师，那么不归属与某个代理商
			memAccountVo.setByAgent(null);
			memAccountVo.setByAgentUserId(null);
		}
		if(MemberType.WORK == memberType || MemberType.WORK == memberType){//如果是产业工人、开拓者注册，会员账号与电话号码一样
			memAccountVo.setAccountName(memAccountVo.getMobilePhone());
		}
		Date curDate = new Date(); //当前时间
		memAccountVo.setRegisterTime(curDate);
		memAccountVo.setAccountName(memAccountVo.getAccountName().trim());
		memAccountVo.setPwd(MD5Encoder.encodeByMD5(memAccountVo.getPwd()));
		Boolean flag = this.add(memAccountVo);//添加新会员
		if(flag){//处理注册赠送
			if(MemberType.DESIGN == memberType){//注册为设计师，需要赠送相应的金币
				Integer gold = SystemConfig.sendGoldForRegister();
				if(gold != 0){
					MemGoldDetail memGoldDetail = new MemGoldDetail(null, memAccountVo.getId(), GoldType.REGISTER, gold, curDate, 
								GoldType.GoldTypeMap.get(GoldType.REGISTER), SourceType.MEMREGISTER, memAccountVo.getId());
					result = this.updateGold(memGoldDetail);
					if(!result.isSuccess()){throw new ServiceException("注册赠送会员金币失败");}
				}
			}
			//注册不同类型的会员赠送不同的积分
			if(memAccountVo.getReferralId() != null){
				//赠送我的推荐人一定的积分
				IntegralConfigVo myReferralerIntegral = null;
				if(MemberType.DESIGN == memberType){
					myReferralerIntegral = this.integralConfigService.getIntegralConfig(IntegralConfigType.D_REGISTER_TO_REFERRAL, null);
				}else if(MemberType.WORK == memberType){
					myReferralerIntegral = this.integralConfigService.getIntegralConfig(IntegralConfigType.W_REGISTER_TO_REFERRAL, null);
				}else if(MemberType.PIONEER == memberType){
					myReferralerIntegral = this.integralConfigService.getIntegralConfig(IntegralConfigType.P_REGISTER_TO_REFERRAL, null);
				}
				//赠送新用户的推荐人积分
				if(myReferralerIntegral != null && myReferralerIntegral.getIntegral().intValue() != 0){
					MemIntegralDetail memIntegralDetail = new MemIntegralDetail(null, memAccountVo.getReferralId(), IntegeralType.SEND_BY_REGISTER_REFERRAL,
							myReferralerIntegral.getIntegral(), curDate, "推荐会员注册成功赠送", SourceType.MEMREGISTER, memAccountVo.getId());
					result = this.updateIntegral(memIntegralDetail);//赠送我的推荐人积分
					if(!result.isSuccess()){
						result.setError(ResultCode.CODE_STATE_4006, "注册通过赠送推荐人积分失败");
						throw new ServiceException("注册通过赠送推荐人积分失败");
					}
					if(referraler == null){
						referraler = this.getById(memAccountVo.getReferralId());
					}
					//赠送新用户的推荐人的推荐人一定积分
					if(referraler != null && referraler.getReferralId() != null){
						//获取应该赠送我的推荐人的推荐人的积分
						//Integer myReferralerIntegralParent = 0;  
						IntegralConfigVo myReferralerIntegralParent = null;
						if(MemberType.DESIGN == memberType){
							myReferralerIntegralParent = this.integralConfigService.getIntegralConfig(IntegralConfigType.D_REGISTER_TO_REFERRAL_PARENT, null);
						}else if(MemberType.WORK == memberType){
							myReferralerIntegralParent = this.integralConfigService.getIntegralConfig(IntegralConfigType.W_REGISTER_TO_REFERRAL_PARENT, null);
						}else if(MemberType.PIONEER == memberType){
							myReferralerIntegralParent = this.integralConfigService.getIntegralConfig(IntegralConfigType.P_REGISTER_TO_REFERRAL_PARENT, null);
						}
						if(myReferralerIntegralParent != null && myReferralerIntegralParent.getIntegral().intValue() != 0){
							MemIntegralDetail memIntegralDetail2 = new MemIntegralDetail(null, referraler.getReferralId(), IntegeralType.SEND_BY_REGISTER_REFERRAL_PARENT,
									myReferralerIntegralParent.getIntegral(), curDate, "推荐会员推荐其他会员注册成功赠送", SourceType.MEMREGISTER, memAccountVo.getId());
							result = this.updateIntegral(memIntegralDetail2);
							if(!result.isSuccess()){
								result.setError(ResultCode.CODE_STATE_4006, "注册通过赠送推荐人积分失败");
								throw new ServiceException("注册通过赠送推荐人积分失败");
							}
						}
					}
				}
			}
			result.setOK(ResultCode.CODE_STATE_200, "注册成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "注册失败");
		}
		return result;
	}

	@Override
	public Result getMemWalletDetail(MemWalletDetailSearch memWalletDetailSearch) {
		Result result = new Result();
		if(memWalletDetailSearch.getMemberId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "会员账号ID不能为空");
			return result;
		}
		Page<MemWalletDetailVo> page = this.memWalletDetailService.getPage(memWalletDetailSearch);
		result.setOK(ResultCode.CODE_STATE_200, "", page);
		return result;
	}

	@Override
	public Result ownerResister(MemAccountVo memAccountVo) {
		Result result = new Result();
		//一、注册信息校验
		if(StringUtil.isEmpty(memAccountVo.getPwd())){
			result.setError(ResultCode.CODE_STATE_4006, "密码不能为空");
			return result;
		}
		if(StringUtil.isEmpty(memAccountVo.getConfirmPwd())){
			result.setError(ResultCode.CODE_STATE_4006, "确认密码不能为空");
			return result;
		}
		if(!memAccountVo.getPwd().trim().equals(memAccountVo.getConfirmPwd().trim())){
			result.setError(ResultCode.CODE_STATE_4006, "密码和确认密码不一致");
			return result;
		}
		if(StringUtil.isEmpty(memAccountVo.getMobilePhone())){
			result.setError(ResultCode.CODE_STATE_4006, "手机号码不能为空");
			return result;
		}
		if(!VerificationUtils.isValid(memAccountVo.getMobilePhone(), VerificationUtils.MOBILE)){
			result.setError(ResultCode.CODE_STATE_4006, "请输入有效的手机号码");
			return result;
		}
		if(StringUtil.isEmpty(memAccountVo.getValidCode())){
			result.setError(ResultCode.CODE_STATE_4006, "手机验证码不能为空");
			return result;
		}
		if(StringUtil.isEmpty(memAccountVo.getWxOpenId())){
			result.setError(ResultCode.CODE_STATE_4006, "请先授权再注册");
			return result;
		}
		CacheEntity cacheEntity = CacheTimerHandler.getCache(memAccountVo.getMobilePhone());
	    String validCodeSys = "";
	    if (cacheEntity != null) {
			validCodeSys =  cacheEntity.getCacheContext()+"";//发送的验证码
		}
		if(StringUtil.isEmpty(validCodeSys) || !validCodeSys.equals(memAccountVo.getValidCode())){
			result.setError(ResultCode.CODE_STATE_4006, "验证码有误！");
			return result;
		}
		MemAccountSearch memAccountSearch = new MemAccountSearch();
		memAccountSearch.setMobilePhone(memAccountVo.getMobilePhone());
		memAccountSearch.setMemberType(MemberType.OWNER);
		//账号名唯一性校验
		long rows = this.getRowCount(memAccountSearch);
		if(rows > 0){
			result.setError(ResultCode.CODE_STATE_4006, "该手机号的账号已经存在");//账号名重复
			return result;
		}
		//二、信息有效
		memAccountVo.setMemberType(MemberType.OWNER);//会员类型为：业主
		Date curDate = new Date(); //当前时间
		memAccountVo.setRegisterTime(curDate);
		memAccountVo.setAccountName(memAccountVo.getMobilePhone().trim());//账户名和手机号一致
		memAccountVo.setPwd(MD5Encoder.encodeByMD5(memAccountVo.getPwd()));
		Boolean flag = this.add(memAccountVo);//添加新会员
		if(flag){		
			result.setOK(ResultCode.CODE_STATE_200, "注册成功",memAccountVo);
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "注册失败");
		}
		return result;
	}

	@Override
	public List<String> getWxOpenIdList() {
		return this.memAccountDao.selectWxOpenId();
	}
	
	
}
