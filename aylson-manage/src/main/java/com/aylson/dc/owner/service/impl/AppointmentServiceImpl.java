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
import com.aylson.dc.base.OwnerGeneralConstant.AppointmentState;
import com.aylson.dc.base.OwnerGeneralConstant.DesignState;
import com.aylson.dc.base.OwnerGeneralConstant.DesignType;
import com.aylson.dc.owner.dao.AppointmentDao;
import com.aylson.dc.owner.po.Appointment;
import com.aylson.dc.owner.search.AppointmentSearch;
import com.aylson.dc.owner.search.DesignSearch;
import com.aylson.dc.owner.service.AppointmentService;
import com.aylson.dc.owner.service.DesignDetailDWService;
import com.aylson.dc.owner.service.DesignDetailSRService;
import com.aylson.dc.owner.service.DesignService;
import com.aylson.dc.owner.service.QuotationDetailDWService;
import com.aylson.dc.owner.service.QuotationDetailSRService;
import com.aylson.dc.owner.service.QuotationService;
import com.aylson.dc.owner.vo.AppointmentVo;
import com.aylson.dc.owner.vo.DesignDetailDWVo;
import com.aylson.dc.owner.vo.DesignDetailSRVo;
import com.aylson.dc.owner.vo.DesignVo;
import com.aylson.dc.owner.vo.QuotationDetailDWVo;
import com.aylson.dc.owner.vo.QuotationDetailSRVo;
import com.aylson.dc.owner.vo.QuotationVo;
import com.aylson.utils.DateUtil;


@Service
public class AppointmentServiceImpl extends BaseServiceImpl<Appointment,AppointmentSearch> implements AppointmentService {

	@Autowired
	private AppointmentDao appointmentDao;
	@Autowired
	private DesignService designService;
	@Autowired
	private DesignDetailDWService designDetailDWService;
	@Autowired
	private DesignDetailSRService designDetailSRService;
	@Autowired
	private QuotationService quotationService;                      //订货报价单服务
	@Autowired
	private QuotationDetailDWService quotationDetailDWService;
	@Autowired
	private QuotationDetailSRService quotationDetailSRService;

	@Override
	protected BaseDao<Appointment,AppointmentSearch> getBaseDao() {
		return appointmentDao;
	}

	@Override
	public Result noticeAgentQuote(Integer appointId) {
		Result result = new Result();
		//判断是否可以通知代理商报价
		if(appointId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取预约信息id失败");
			return result;
		}
		AppointmentVo appointmentVo = this.appointmentDao.selectById(appointId);
		if(appointmentVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取预约信息失败");
			return result;
		}
		DesignSearch designSearch = new DesignSearch();
		designSearch.setAppointId(appointId);
		List<DesignVo> designList = this.designService.getList(designSearch);
		if(designList != null && designList.size() > 0){
			for(DesignVo designVo: designList){
				if(DesignState.AYLSON_HAD_QUOTE != designVo.getState()
						&& DesignState.AYLSON_HAD_QUOTE_AGAIN != designVo.getState()){//如果存在没有报价订货单的设计信息表，不允许通知
					result.setError(ResultCode.CODE_STATE_4006, "请完成所有设计信息表的订货报价单再通知代理商报价");
					return result;
				}
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "找不到任何设计信息表的信息");
			return result;
		}
		//资料齐全，通知代理商进行报价
		appointmentVo.setState(AppointmentState.WAIT_AGENT_QUOTE);
		Boolean flag = this.appointmentDao.updateById(appointmentVo);
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
		}
		return result;
	}

	@Override
	public Result noticeConfirmQuote(Integer appointId) {
		Result result = new Result();
		//判断是否可以通知客户确认报价
		if(appointId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取预约信息id失败");
			return result;
		}
		AppointmentVo appointmentVo = this.appointmentDao.selectById(appointId);
		if(appointmentVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取预约信息失败");
			return result;
		}
		DesignSearch designSearch = new DesignSearch();
		designSearch.setAppointId(appointId);
		List<DesignVo> designList = this.designService.getList(designSearch);
		if(designList != null && designList.size() > 0){
			for(DesignVo designVo: designList){
				if(DesignState.AGENT_HAD_QUOTE != designVo.getState()
						&& DesignState.AGENT_HAD_QUOTE_AGAIN != designVo.getState()){
					result.setError(ResultCode.CODE_STATE_4006, "请完成所有设计信息表的报价再给客户确认报价");
					return result;
				}
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "找不到任何设计信息表的信息");
			return result;
		}
		//资料齐全，通知客户对报价进行确认
		appointmentVo.setState(AppointmentState.WAIT_CLIENT_CONFIRM);
		Boolean flag = this.appointmentDao.updateById(appointmentVo);
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
		}
		result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		return result;
	}
	
	@Override
	@Transactional
	public Result sumConfrimDraw(Integer appointId) {
		Result result = new Result();
		//校验是否可以提交下一步进行大样图的确认
		if(appointId == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取预约信息id失败");
			return result;
		}
		AppointmentVo appointmentVo = this.appointmentDao.selectById(appointId);
		if(appointmentVo == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取预约信息id失败");
			return result;
		}
		DesignSearch designSearch = new DesignSearch();
		designSearch.setAppointId(appointId);;
		List<DesignVo> list = this.designService.getList(designSearch);
		Boolean isFirstConfirm = true;
		if(AppointmentState.WAIT_DRAW_AGAIN == appointmentVo.getState().intValue())isFirstConfirm = false;
		if(list != null && list.size() > 0){
			for(DesignVo temp : list){
				if(isFirstConfirm){//第一次确认
					if( DesignState.HAD_DRAW != temp.getState().intValue()){//如果存在没有上传大样图的设计信息，不能进入下一步
						result.setError(ResultCode.CODE_STATE_4006, "请先上传所有设计信息表的大样图再进行确认提交");
						return result;
					}
					temp.setState(DesignState.WAIT_CONFIRM_DRAW);//待确认大样图
				}else{
					if(DesignState.HAD_DRAW_AGAIN != temp.getState().intValue() && DesignState.SATISFY_DRAW != temp.getState().intValue()){//如果存在没有上传大样图的设计信息，不能进入下一步
						result.setError(ResultCode.CODE_STATE_4006, "请先上传所有设计信息表的大样图再进行确认提交");
						return result;
					}
					if(DesignState.HAD_DRAW_AGAIN == temp.getState().intValue()){
						temp.setState(DesignState.WAIT_CONFIRM_DRAW_AGAIN);//待重新确认大样图
					}
				}
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "找不到该预约的任何设计信息");
			return result;
		}
		if(isFirstConfirm){
			appointmentVo.setState(AppointmentState.WAIT_CONFIRM_DRAW);//预约信息：待确认大样图
		}else{
			appointmentVo.setState(AppointmentState.WAIT_CONFIRM_DRAW_AGAIN);//预约信息：重新待确认大样图
		}
		Boolean flag = this.appointmentDao.updateById(appointmentVo);
		if(flag){
			flag = this.designService.batchUpdate(list);
			if(flag){
				result.setOK(ResultCode.CODE_STATE_200, "操作成功");
			}else{
				result.setError(ResultCode.CODE_STATE_4006, "更改设计信息状态失败");
				throw new ServiceException("更改设计信息状态失败");
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "更改预约信息状态失败");
		}
		return result;
	}

	@Override
	public AppointmentVo getAppointDetail(Integer appointId) {
		if(appointId == null) return null;
		AppointmentVo appointmentVo = this.appointmentDao.selectById(appointId);
		if(appointmentVo != null){
			if(appointmentVo.getHomeTime() != null){
				appointmentVo.setHomeTimeStr(DateUtil.format(appointmentVo.getHomeTime()));
			}
			if(appointmentVo.getDecoratingTime() != null){
				appointmentVo.setDecoratingTimeStr(DateUtil.format(appointmentVo.getDecoratingTime(),true));
			}
			if(appointmentVo.getAppointDate() != null){
				appointmentVo.setAppointDateStr(DateUtil.format(appointmentVo.getAppointDate(),true));
			}
			DesignSearch designSearch = new DesignSearch();
			designSearch.setAppointId(appointId);
			List<DesignVo> designVoList = this.designService.getList(designSearch);
			if(designVoList != null && designVoList.size() > 0){
				for(DesignVo designVo : designVoList){//获取设计信息表信息
					QuotationVo quotationVo = this.quotationService.getByDesignId(designVo.getId());//设计信息对应的报价单
					if(DesignType.SUMROOM == designVo.getDesignType().intValue()){//如果是阳光房
						DesignDetailSRVo designDetailSRVo = this.designDetailSRService.getByDesignId(designVo.getId());
						designVo.setDesignDetailSRVo(designDetailSRVo);//阳光房设计信息
						if(quotationVo != null){//阳光房-报价单详情
							List<QuotationDetailSRVo> detailSRVoList = this.quotationDetailSRService.getByQuotationId(quotationVo.getId());
							quotationVo.setDetailSRVoList(detailSRVoList);
						}
					}else{//如果是门、窗
						List<DesignDetailDWVo> designDetailDWList = this.designDetailDWService.getByDesignId(designVo.getId());
						designVo.setDesignDetailDWList(designDetailDWList);//门窗设计信息详情
						if(quotationVo != null){//门窗-报检单详情
							List<QuotationDetailDWVo> detailDWVoList = this.quotationDetailDWService.getByQuotationId(quotationVo.getId());
							quotationVo.setDetailDWVoList(detailDWVoList);
						}
					}
					designVo.setQuotationVo(quotationVo);
				}
				appointmentVo.setDesignVoList(designVoList);
			}
		}
		return appointmentVo;
	}
	

}
