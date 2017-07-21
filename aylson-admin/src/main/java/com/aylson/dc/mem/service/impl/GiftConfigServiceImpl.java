package com.aylson.dc.mem.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aylson.core.frame.base.Page;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.GeneralConstant.GiftConfigStatus;
import com.aylson.dc.mem.dao.GiftConfigDao;
import com.aylson.dc.mem.po.GiftConfig;
import com.aylson.dc.mem.search.GiftConfigSearch;
import com.aylson.dc.mem.service.GiftConfigService;
import com.aylson.dc.mem.service.MemAccountService;
import com.aylson.dc.mem.vo.GiftConfigVo;
import com.aylson.dc.mem.vo.MemAccountVo;
import com.aylson.utils.StringUtil;

@Service
public class GiftConfigServiceImpl extends BaseServiceImpl<GiftConfig,GiftConfigSearch> implements GiftConfigService {
	
	private static final Logger logger = LoggerFactory.getLogger(GiftConfigServiceImpl.class);
	
	@Autowired
	private GiftConfigDao giftConfigDao;
	@Autowired
	private MemAccountService memAccountService;
	
	@Override
	protected BaseDao<GiftConfig,GiftConfigSearch> getBaseDao() {
		return this.giftConfigDao;
	}

	/**
	 * 获取积分礼品兑换列表
	 * @param giftConfigSearch
	 * @return
	 */
	@Override
	public Result getIntegralGiftList(GiftConfigSearch giftConfigSearch, String memberId) {
		Result result = new Result();
		//校验用户信息
		if(StringUtil.isEmpty(memberId)){
			result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
			return result;
		}
		MemAccountVo memAccountVo = this.memAccountService.getById(Integer.parseInt(memberId));
		if(memAccountVo == null ){
			result.setError(ResultCode.CODE_STATE_4006, "获取当前人登录信息失败");
			return result;
		}
		if(memAccountVo.getMemberType() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取登录人账号类型失败");
			return result;
		}
		//获取当前会员类型的礼品兑换列表
		giftConfigSearch.setStatus(GiftConfigStatus.UP);
		giftConfigSearch.setMatchObj(memAccountVo.getMemberType());
		giftConfigSearch.setContainAll(true);
		Page<GiftConfigVo> page = this.getPage(giftConfigSearch);
		Integer currentMemInteger = memAccountVo.getIntegral()==null?0:memAccountVo.getIntegral();
		for(GiftConfigVo temp:page.getRowsObject()){
			if(temp.getIntegral() >= currentMemInteger){
				temp.setWhetherExchange(true);
			}
		}
		result.setOK(ResultCode.CODE_STATE_200, "", page);
		return result;
	}
	
	/**
	 * 获取礼品详情
	 * @param giftId
	 * @return
	 */
	@Override
	public Result getGiftDetail(Integer giftId) {
		Result result = new Result();
		if(giftId == null){
			result.setError(ResultCode.CODE_STATE_4006, "查看礼品详情失败");
			return result;
		}
		GiftConfigVo giftConfigVo = this.getById(giftId);
		if(giftConfigVo != null){
			//参数
			if(StringUtil.isNotEmpty(giftConfigVo.getParameters())){
				String[] params = giftConfigVo.getParameters().split("<;>");
				if(params != null && params.length > 0){
					Map<String,String> parameterMap = new HashMap<String,String>();
					for(int i=0; i<params.length; i++){
						String[] param = params[i].split("<:>");
						parameterMap.put(param[0], param[1]);
					}
					giftConfigVo.setParameterMap(parameterMap);
				}
			}
			//处理礼品导航图片
			if(StringUtil.isNotEmpty(giftConfigVo.getImgNavigation())){
				String[] imgNavigationItem = giftConfigVo.getImgNavigation().split("<;>");
				if(imgNavigationItem != null && imgNavigationItem.length > 0){
					List<String> imgNavigationAddress = new ArrayList<String>();
					for(int i=0; i<imgNavigationItem.length; i++){
						imgNavigationAddress.add(imgNavigationItem[i]);
					}
					giftConfigVo.setImgNavigationAddress(imgNavigationAddress);
				}
			}
			result.setOK(ResultCode.CODE_STATE_200, "", giftConfigVo);
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "查看礼品详情失败");
			return result;
		}
		return result;
	}

	
}
