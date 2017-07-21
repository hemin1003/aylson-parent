package com.aylson.dc.owner.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aylson.core.exception.ServiceException;
import com.aylson.core.frame.dao.BaseDao;
import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.domain.ResultCode;
import com.aylson.core.frame.service.impl.BaseServiceImpl;
import com.aylson.dc.base.OwnerGeneralConstant.DesignType;
import com.aylson.dc.owner.dao.QuotationDao;
import com.aylson.dc.owner.po.Quotation;
import com.aylson.dc.owner.search.QuotationDetailDWSearch;
import com.aylson.dc.owner.search.QuotationDetailSRSearch;
import com.aylson.dc.owner.search.QuotationSearch;
import com.aylson.dc.owner.service.DesignService;
import com.aylson.dc.owner.service.QuotationDetailDWService;
import com.aylson.dc.owner.service.QuotationDetailSRService;
import com.aylson.dc.owner.service.QuotationService;
import com.aylson.dc.owner.vo.DesignVo;
import com.aylson.dc.owner.vo.QuotationDetailDWVo;
import com.aylson.dc.owner.vo.QuotationDetailSRVo;
import com.aylson.dc.owner.vo.QuotationVo;
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

	@Override
	protected BaseDao<Quotation,QuotationSearch> getBaseDao() {
		return quotationDao;
	}
	
	@Override
	@Transactional
	public Result addQuotation(QuotationVo quotationVo,String quotationDetailDWVoListJson) {
		Result result = new Result();
		if(StringUtil.isNotEmpty(quotationVo.getOrderTimeStr())){
			quotationVo.setOrderTime(DateUtil.format(quotationVo.getOrderTimeStr(),"yyyy-MM-dd"));
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
			designVo.setState(6);//已添加设计订货单:aylson已报价
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
		if(flag){//保存成功后将对应的设计信息表状态修改为：【已添加设计订货单:aylson已报价】
			DesignVo designVo = new DesignVo();
			designVo.setId(quotationVo.getDesignId());
			designVo.setState(6);//已添加设计订货单:aylson已报价
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
