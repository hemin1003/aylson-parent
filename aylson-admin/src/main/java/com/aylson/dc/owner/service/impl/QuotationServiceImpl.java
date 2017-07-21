package com.aylson.dc.owner.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.constants.SysConstant.BillCodePrefix;
import com.aylson.core.exception.ServiceException;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.OwnerGeneralConstant.DesignState;
import com.aylson.dc.base.OwnerGeneralConstant.DesignType;
import com.aylson.dc.owner.dao.QuotationDao;
import com.aylson.dc.owner.po.Quotation;
import com.aylson.dc.owner.search.QuotationDetailDWSearch;
import com.aylson.dc.owner.search.QuotationSearch;
import com.aylson.dc.owner.service.DesignDetailDWService;
import com.aylson.dc.owner.service.DesignService;
import com.aylson.dc.owner.service.QuotationDetailDWService;
import com.aylson.dc.owner.service.QuotationDetailSRService;
import com.aylson.dc.owner.service.QuotationService;
import com.aylson.dc.owner.vo.DesignDetailDWVo;
import com.aylson.dc.owner.vo.DesignVo;
import com.aylson.dc.owner.vo.QuotationDetailDWVo;
import com.aylson.dc.owner.vo.QuotationDetailSRVo;
import com.aylson.dc.owner.vo.QuotationVo;
import com.aylson.utils.BillNumUtils;
import com.aylson.utils.DateUtil;
import com.aylson.utils.StringUtil;

import net.sf.json.JSONArray;


@Service
public class QuotationServiceImpl extends BaseServiceImpl<Quotation,QuotationSearch> implements QuotationService {

	@Autowired
	private QuotationDao quotationDao;
	@Autowired
	private QuotationDetailDWService quotationDetailDWService;
	@Autowired
	private QuotationDetailSRService quotationDetailSRService;
	@Autowired
	private DesignService designService;
	@Autowired
	private DesignDetailDWService designDetailDWService;

	@Override
	protected BaseDao<Quotation,QuotationSearch> getBaseDao() {
		return quotationDao;
	}
	
	@Override
	public QuotationVo toAddQuotation(Integer designId, Integer designType) {
		QuotationVo quotationVo = null;
		if(designId != null){
			//设计信息表信息
			DesignVo designVo = this.designService.getById(designId);
			if(designVo != null){
				quotationVo = new QuotationVo();
				quotationVo.setAppointId(designVo.getAppointId());
				quotationVo.setDesignId(designId);
				quotationVo.setClientName(designVo.getClientName());
				quotationVo.setClientPhone(designVo.getClientPhone());
				quotationVo.setClientAddress(designVo.getClientAddress());
				quotationVo.setOrderTimeStr(DateUtil.format(new Date()));
				quotationVo.setBillCode(BillNumUtils.getBillCode(BillCodePrefix.QUOTATION, designVo.getClientPhone()));
				if(DesignType.DOOR == designType || DesignType.WINDOW == designType){
					List<DesignDetailDWVo> designDetailList = this.designDetailDWService.getByDesignId(designId);
					if(designDetailList != null && designDetailList.size() > 0){
						List<QuotationDetailDWVo> detailDWVoList = new ArrayList<QuotationDetailDWVo>();
						for(DesignDetailDWVo temp:designDetailList ){
							QuotationDetailDWVo quotationDetailDWVo = new QuotationDetailDWVo();
							quotationDetailDWVo.setProductName(temp.getProductName());
							quotationDetailDWVo.setProductNo(temp.getProductNo());
							quotationDetailDWVo.setProductSizeH(temp.getProductSizeH());
							quotationDetailDWVo.setProductSizeW(temp.getProductSizeW());
							quotationDetailDWVo.setColorIn(temp.getColorIn());
							quotationDetailDWVo.setColorOut(temp.getColorOut());
							quotationDetailDWVo.setWallThick(temp.getWallThick());
							quotationDetailDWVo.setPruductNum(temp.getFrameNum());
							quotationDetailDWVo.setGoodsAmount(0.0f);
							detailDWVoList.add(quotationDetailDWVo);
						}
						quotationVo.setDetailDWVoList(detailDWVoList);
					}
				}
			}
		}
		return quotationVo;
	}
	
	@Override
	@Transactional
	public Result addQuotation(QuotationVo quotationVo,String quotationDetailDWVoListJson) {
		Result result = new Result();
		if(StringUtil.isNotEmpty(quotationVo.getOrderTimeStr())){
			quotationVo.setOrderTime(DateUtil.format(quotationVo.getOrderTimeStr(),"yyyy-MM-dd"));
		}
		if(StringUtil.isNotEmpty(quotationVo.getDeliveryTimeStr())){
			quotationVo.setDeliveryTime(DateUtil.format(quotationVo.getDeliveryTimeStr(),"yyyy-MM-dd"));
		}
		Boolean flag = this.quotationDao.insert(quotationVo);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
		}
		//保存明细
		if(StringUtil.isNotEmpty(quotationDetailDWVoListJson)){//门窗
			JSONArray data = JSONArray.fromObject(quotationDetailDWVoListJson);
			List<QuotationDetailDWVo> quotationDetailList = (List) JSONArray.toCollection(data, QuotationDetailDWVo.class); 
			for(QuotationDetailDWVo temp:quotationDetailList){
				temp.setQuotationId(quotationVo.getId());
				temp.setAppointId(quotationVo.getAppointId());
			}
			flag = this.quotationDetailDWService.batchAdd(quotationDetailList);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "批量添加门窗订货单明细失败");
				throw new ServiceException("批量添加门窗订货单明细失败");
			}
		}
		if(quotationVo.getDetailSRVoList() != null && quotationVo.getDetailSRVoList().size() > 0){
			for(QuotationDetailSRVo temp:quotationVo.getDetailSRVoList()){
				temp.setQuotationId(quotationVo.getId());
				temp.setAppointId(quotationVo.getAppointId());
			}
			flag = this.quotationDetailSRService.batchAdd(quotationVo.getDetailSRVoList());
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "批量添加阳光房订货单明细失败");
				throw new ServiceException("批量添加阳光房订货单明细失败");
			}
		}
		if(flag){//保存成功后将对应的设计信息表状态修改为：【已添加设计订货单:aylson已报价】
			DesignVo designVo = new DesignVo();
			designVo.setId(quotationVo.getDesignId());
			designVo.setState(DesignState.AYLSON_HAD_QUOTE);//已添加设计订货单:aylson已报价
			flag = this.designService.edit(designVo);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "更新设计信息表的状态失败");
				throw new ServiceException("更新设计信息表的状态失败");
			}
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
		}
		return result;
	}

	@Override
	public QuotationVo getByDesignId(Integer designId) {
		if(designId != null){
			return this.quotationDao.selectByDesignId(designId);
		}
		return null;
	}

	@Override
	public QuotationVo getQuotationVo(Integer designId, Integer designType) {
		QuotationVo quotationVo = null;
		if(designId != null){
			quotationVo  = this.quotationDao.selectByDesignId(designId);
			if(quotationVo != null){
				if(quotationVo.getOrderTime() != null){
					quotationVo.setOrderTimeStr(DateUtil.format(quotationVo.getOrderTime(), true));
				}
				if(quotationVo.getDeliveryTime() != null){
					quotationVo.setDeliveryTimeStr(DateUtil.format(quotationVo.getDeliveryTime(), true));
				}
				if(designType != null){
					if(DesignType.SUMROOM == designType.intValue()){
						List<QuotationDetailSRVo> detailSRVoList = this.quotationDetailSRService.getByQuotationId(quotationVo.getId());
						quotationVo.setDetailSRVoList(detailSRVoList);
					}else{
						List<QuotationDetailDWVo> detailDWVoList = this.quotationDetailDWService.getByQuotationId(quotationVo.getId());
						quotationVo.setDetailDWVoList(detailDWVoList);
					}
				}
			}
		}
		return quotationVo;
	}

	@Override
	@Transactional
	public Result editQuotation(QuotationVo quotationVo, String quotationDetailDWVoListJson) {
		Result result = new Result();
		if(StringUtil.isNotEmpty(quotationVo.getOrderTimeStr())){
			quotationVo.setOrderTime(DateUtil.format(quotationVo.getOrderTimeStr(),"yyyy-MM-dd"));
		}
		if(StringUtil.isNotEmpty(quotationVo.getDeliveryTimeStr())){
			quotationVo.setDeliveryTime(DateUtil.format(quotationVo.getDeliveryTimeStr(),"yyyy-MM-dd"));
		}
		Boolean flag = this.quotationDao.updateById(quotationVo);//更新报价订货单头信息
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "修改报价订货单头信息失败");
		}
		//保存明细
		if(StringUtil.isNotEmpty(quotationDetailDWVoListJson)){//门窗
			//删除旧的订货明细
			QuotationDetailDWSearch quotationDetailDWSearch = new QuotationDetailDWSearch();
			quotationDetailDWSearch.setQuotationId(quotationVo.getId());
			flag = this.quotationDetailDWService.delete(quotationDetailDWSearch);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "删除报价订货单明细信息失败");
				throw new ServiceException("删除报价订货单明细信息失败");
			}
			JSONArray data = JSONArray.fromObject(quotationDetailDWVoListJson);
			List<QuotationDetailDWVo> quotationDetailList = (List) JSONArray.toCollection(data, QuotationDetailDWVo.class); 
			for(QuotationDetailDWVo temp:quotationDetailList){
				temp.setQuotationId(quotationVo.getId());
				temp.setAppointId(quotationVo.getAppointId());
			}
			flag = this.quotationDetailDWService.batchAdd(quotationDetailList);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "批量添加门窗订货单明细失败");
				throw new ServiceException("批量添加门窗订货单明细失败");
			}
		}
		if(quotationVo.getDetailSRVoList() != null && quotationVo.getDetailSRVoList().size() > 0){
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "删除报价订货单明细信息失败");
				throw new ServiceException("删除报价订货单明细信息失败");
			}
			for(QuotationDetailSRVo temp:quotationVo.getDetailSRVoList()){
				temp.setQuotationId(quotationVo.getId());
				temp.setAppointId(quotationVo.getAppointId());
			}
			flag = this.quotationDetailSRService.batchUpdate(quotationVo.getDetailSRVoList());
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "批量添加阳光房订货单明细失败");
				throw new ServiceException("批量添加阳光房订货单明细失败");
			}
		}
		if(flag){//保存成功后将对应的设计信息表状态修改为：【已添加设计订货单:aylson已重新报价】
			DesignVo designVo = new DesignVo();
			designVo.setId(quotationVo.getDesignId());
			if(DesignState.AYLSON_HAD_QUOTE == quotationVo.getDesignState().intValue()){
				designVo.setState(DesignState.AYLSON_HAD_QUOTE);//已添加设计订货单:aylson已报价
			}else{
				designVo.setState(DesignState.AYLSON_HAD_QUOTE_AGAIN);//已添加设计订货单:aylson已重新报价
			}
			flag = this.designService.edit(designVo);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "更新设计信息表的状态失败");
				throw new ServiceException("更新设计信息表的状态失败");
			}
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
		}
		return result;
	}

	@Override
	@Transactional
	public Result agentQuote(QuotationVo quotationVo) {
		Result result = new Result();
		//信息校验
		if(quotationVo.getId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取报价订货单信息失败");
			return result;
		}
		if(quotationVo.getDesignId() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取设计表信息失败");
			return result;
		}
		if(quotationVo.getSalesAmount() == null || quotationVo.getSalesAmount() < 0.0f){
			result.setError(ResultCode.CODE_STATE_4006, "报价金额不能小于0");
			return result;
		}
		//信息有效，更新相应的信息
		Boolean flag = this.quotationDao.updateById(quotationVo);
		if(flag){//如果成功，更新设计信息表的状态
			DesignVo designVo = this.designService.getById(quotationVo.getDesignId());
			if(DesignState.NOTSATISFY_QUOTE == designVo.getState().intValue()){
				designVo.setState(DesignState.AGENT_HAD_QUOTE_AGAIN);
			}else{
				designVo.setState(DesignState.AGENT_HAD_QUOTE);
			}
			flag = this.designService.edit(designVo);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "更新报价状态失败");
				throw new ServiceException("更新报价状态失败");
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "保存报价失败");
		}
		
		return result;
	}

	@Override
	public Result agentQuote(QuotationVo quotationVo, String quotationDetailDWVoListJson) {
		Result result = new Result();
		if(StringUtil.isNotEmpty(quotationVo.getOrderTimeStr())){
			quotationVo.setOrderTime(DateUtil.format(quotationVo.getOrderTimeStr(),"yyyy-MM-dd"));
		}
		Boolean flag = this.quotationDao.updateById(quotationVo);//更新报价订货单头信息
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "修改报价订货单头信息失败");
		}
		//保存明细
		if(StringUtil.isNotEmpty(quotationDetailDWVoListJson)){//门窗
			//删除旧的订货明细
			QuotationDetailDWSearch quotationDetailDWSearch = new QuotationDetailDWSearch();
			quotationDetailDWSearch.setQuotationId(quotationVo.getId());
			flag = this.quotationDetailDWService.delete(quotationDetailDWSearch);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "删除报价订货单明细信息失败");
				throw new ServiceException("删除报价订货单明细信息失败");
			}
			JSONArray data = JSONArray.fromObject(quotationDetailDWVoListJson);
			List<QuotationDetailDWVo> quotationDetailList = (List) JSONArray.toCollection(data, QuotationDetailDWVo.class); 
			for(QuotationDetailDWVo temp:quotationDetailList){
				temp.setQuotationId(quotationVo.getId());
				temp.setAppointId(quotationVo.getAppointId());
			}
			flag = this.quotationDetailDWService.batchAdd(quotationDetailList);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "批量添加门窗订货单明细失败");
				throw new ServiceException("批量添加门窗订货单明细失败");
			}
		}
		if(quotationVo.getDetailSRVoList() != null && quotationVo.getDetailSRVoList().size() > 0){
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "删除报价订货单明细信息失败");
				throw new ServiceException("删除报价订货单明细信息失败");
			}
			for(QuotationDetailSRVo temp:quotationVo.getDetailSRVoList()){
				temp.setQuotationId(quotationVo.getId());
				temp.setAppointId(quotationVo.getAppointId());
			}
			flag = this.quotationDetailSRService.batchUpdate(quotationVo.getDetailSRVoList());
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "批量添加阳光房订货单明细失败");
				throw new ServiceException("批量添加阳光房订货单明细失败");
			}
		}
		if(flag){//保存成功后将对应的设计信息表状态修改为：【已添加设计订货单:aylson已重新报价】
			DesignVo designVo = new DesignVo();
			designVo.setId(quotationVo.getDesignId());
			if(DesignState.AYLSON_HAD_QUOTE_AGAIN == quotationVo.getDesignState().intValue() ){
				designVo.setState(DesignState.AGENT_HAD_QUOTE_AGAIN);//已添加设计订货单:代理商已重新报价
			}else{
				designVo.setState(DesignState.AGENT_HAD_QUOTE);//已添加设计订货单:代理商报价
			}
			flag = this.designService.edit(designVo);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "更新设计信息表的状态失败");
				throw new ServiceException("更新设计信息表的状态失败");
			}
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
		}
		return result;
	}


}
