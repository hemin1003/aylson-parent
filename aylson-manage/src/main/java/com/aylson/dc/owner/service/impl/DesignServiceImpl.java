package com.aylson.dc.owner.service.impl;


import java.util.Date;
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
import com.aylson.dc.owner.dao.DesignDao;
import com.aylson.dc.owner.po.Appointment;
import com.aylson.dc.owner.po.Design;
import com.aylson.dc.owner.search.DesignDetailDWSearch;
import com.aylson.dc.owner.search.DesignDetailSRSearch;
import com.aylson.dc.owner.search.DesignSearch;
import com.aylson.dc.owner.service.AppointmentService;
import com.aylson.dc.owner.service.DesignDetailDWService;
import com.aylson.dc.owner.service.DesignDetailSRService;
import com.aylson.dc.owner.service.DesignService;
import com.aylson.dc.owner.vo.AppointmentVo;
import com.aylson.dc.owner.vo.DesignDetailDWVo;
import com.aylson.dc.owner.vo.DesignDetailSRVo;
import com.aylson.dc.owner.vo.DesignVo;
import com.aylson.utils.DateUtil;
import com.aylson.utils.StringUtil;

import net.sf.json.JSONArray;


@Service
public class DesignServiceImpl extends BaseServiceImpl<Design,DesignSearch> implements DesignService {

	@Autowired
	private DesignDao designDao;
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private DesignDetailDWService designDetailDWService;
	@Autowired
	private DesignDetailSRService designDetailSRService;

	@Override
	protected BaseDao<Design,DesignSearch> getBaseDao() {
		return designDao;
	}

	@Override
	@Transactional
	public Result delDesign(Integer designId, Integer designType) {
		Result result = new Result();
		if(designId == null || designType == null ){
			result.setError(ResultCode.CODE_STATE_4006, "获取设计表信息失败");
			return result;
		}
		Boolean flag = false;
		//先删除设计明细
		if(3 == designType.intValue()){//删除阳光房明细
			DesignDetailSRSearch designDetailSRSearch = new DesignDetailSRSearch();
			designDetailSRSearch.setDesignId(designId);
			flag = this.designDetailSRService.delete(designDetailSRSearch);
		}else{//删除门窗明细
			DesignDetailDWSearch designDetailDWSearch =  new DesignDetailDWSearch();
			designDetailDWSearch.setDesignId(designId);
			flag = this.designDetailDWService.delete(designDetailDWSearch);
		}
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "删除设计信息表的明细失败");
			return result;
		}
		//再删除设计信息表头信息
		flag = this.designDao.deleteById(designId);
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "删除成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "删除失败");
			throw new ServiceException("删除失败");
		}
			
		return result;
	}

	@Override
	@Transactional
	public Result applyDraw(Integer appointId) {
		Result result = new Result();
		if(appointId == null ){
			result.setError(ResultCode.CODE_STATE_4006, "获取预约信息失败");
			return result;
		}
		//更新设计信息表状态为：待上传大样图
		DesignSearch designSearch = new DesignSearch();
		designSearch.setAppointId(appointId);;
		List<DesignVo> list = this.designDao.select(designSearch);
		if(list != null && list.size() > 0){
			for(DesignVo temp : list){
				temp.setState(DesignState.WAIT_DRAW);
			}
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "找不到相关设计信息表信息，不能申请大样图");
			return result;
		}
		Boolean flag = this.designDao.batchUpdate(list);
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "更新设计信息失败");
			return result;
		}
		//更新预约信息表状态为：待上传大样图
		Appointment appointment = new Appointment();
		appointment.setId(appointId);
		appointment.setState(AppointmentState.WAIT_DRAW);
		flag = this.appointmentService.edit(appointment);
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			throw new ServiceException("修改预约状态失败");
		}
		return result;
	}

	@Override
	@Transactional
	public Result addDesign(DesignVo designVo, String designDetailDWVoListJson) {
		Result result = new Result();
		if(StringUtil.isNotEmpty(designVo.getOrderTimeStr())){
			designVo.setOrderTime(DateUtil.format(designVo.getOrderTimeStr(),"yyyy-MM-dd"));
		}
		designVo.setCreateTime(new Date());
		Boolean flag = this.designDao.insert(designVo);//保存设计信息表的表头信息
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
		}
		//根据不同的类型，保存设计信息表的明细的明细到对应的数据表里
		if(designVo.getDesignType() != null && designVo.getDesignType().intValue() == 3){//阳光房明细
			DesignDetailSRVo designDetailSRVo = designVo.getDesignDetailSRVo();
			designDetailSRVo.setDesignId(designVo.getId());
			designDetailSRVo.setAppointId(designVo.getAppointId());
			flag = this.designDetailSRService.add(designDetailSRVo);
		}else{//门窗明细
			JSONArray data = JSONArray.fromObject(designDetailDWVoListJson);
			List<DesignDetailDWVo> designDetailList = (List) JSONArray.toCollection(data, DesignDetailDWVo.class); 
			for(DesignDetailDWVo temp:designDetailList){
				temp.setDesignId(designVo.getId());
				temp.setAppointId(designVo.getAppointId());
			}
			flag = this.designDetailDWService.batchAdd(designDetailList);
		}
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			throw new ServiceException("保存设计信息表的明细失败");
		}
		return result;
	}
	
	@Override
	@Transactional
	public Result editDesign(DesignVo designVo, String designDetailDWVoListJson) {
		Result result = new Result();
		if(StringUtil.isNotEmpty(designVo.getOrderTimeStr())){
			designVo.setOrderTime(DateUtil.format(designVo.getOrderTimeStr(),"yyyy-MM-dd"));
		}
		Boolean flag = this.designDao.updateById(designVo);  //更新设计信息表的头信息
		if(!flag){
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
		}
		//根据不同的类型，保存设计信息表的明细的明细到对应的数据表里
		if(designVo.getDesignType() != null && designVo.getDesignType().intValue() == DesignType.SUMROOM){//阳光房明细
			//先删除所有明细
			DesignDetailSRSearch designDetailSRSearch = new DesignDetailSRSearch();
			designDetailSRSearch.setDesignId(designVo.getId());
			flag = this.designDetailSRService.delete(designDetailSRSearch);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "删除设计信息表明细信息失败");
				throw new ServiceException("删除设计信息表明细信息失败");
			}
			DesignDetailSRVo designDetailSRVo = designVo.getDesignDetailSRVo();
			designDetailSRVo.setDesignId(designVo.getId());
			designDetailSRVo.setAppointId(designVo.getAppointId());
			flag = this.designDetailSRService.add(designDetailSRVo);
		}else{//门窗明细
			DesignDetailDWSearch designDetailDWSearch = new DesignDetailDWSearch();
			designDetailDWSearch.setDesignId(designVo.getId());
			flag = this.designDetailDWService.delete(designDetailDWSearch);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "删除设计信息表明细信息失败");
				throw new ServiceException("删除设计信息表明细信息失败");
			}
			JSONArray data = JSONArray.fromObject(designDetailDWVoListJson);
			List<DesignDetailDWVo> designDetailList = (List) JSONArray.toCollection(data, DesignDetailDWVo.class); 
			for(DesignDetailDWVo temp:designDetailList){
				temp.setDesignId(designVo.getId());
				temp.setAppointId(designVo.getAppointId());
			}
			flag = this.designDetailDWService.batchAdd(designDetailList);
		}
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
			throw new ServiceException("保存设计信息表的明细失败");
		}
		return result;
	}
	
	
	/**
	 * 确认大样图
	 */
	public Result confirmDraw(DesignVo designVo) {
		Result result = new Result();
		if(designVo.getState() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取设计信息当前状态失败");
			return result;
		}
		if(designVo.getAppointState() == null){
			result.setError(ResultCode.CODE_STATE_4006, "获取预约信息当前状态失败");
			return result;
		}
		if(StringUtil.isNotEmpty(designVo.getDrawOpition()) 
				&& StringUtil.isNotEmpty(designVo.getDrawOpitionHistory())){
			designVo.setDrawOpition(designVo.getDrawOpitionHistory()+";"+designVo.getDrawOpition());
		}
		Boolean flag = this.edit(designVo);//更新设计信息
		AppointmentVo appointmentVo = null;
		Boolean isConfirmAll = true;       //是否所有大样图已经确认
		//是否需要更新预约信息的状态：如果不满意大样图，预约状态还没有修改为【待确认大样图】状态，更新预约信息的状态为【待确认大样图】状态
		if(flag && (DesignState.NOTSATISFY_DRAW == designVo.getState().intValue() 
				|| AppointmentState.WAIT_DRAW_AGAIN == designVo.getAppointState().intValue())){
			appointmentVo = new AppointmentVo();
			appointmentVo.setId(designVo.getAppointId());
			appointmentVo.setState(AppointmentState.WAIT_DRAW_AGAIN);//待重新设计大样图
			flag = this.appointmentService.edit(appointmentVo);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "更新预约信息状态失败");
				throw new ServiceException("更新预约信息状态失败");
			}
			isConfirmAll = false;
		}
		if(flag && DesignState.SATISFY_DRAW == designVo.getState()){//判断是否都已经确认，如果已经确认更新预约信息为待报价
			DesignSearch designSearch = new DesignSearch();
			designSearch.setAppointId(designVo.getAppointId());;
			List<DesignVo> list = this.getList(designSearch);
			if(list != null && list.size() > 0){
				for(DesignVo temp : list){
					if(DesignState.SATISFY_DRAW != temp.getState()){//如果存在没有不满意的
						isConfirmAll = false;
						break;
					}
				}
			}
		}
		if(isConfirmAll){//如果一个预约的所有设计信息表的大样图都已经确认，更新雨夜信息的状态为【待报价】
			appointmentVo = new AppointmentVo();
			appointmentVo.setId(designVo.getAppointId());
			appointmentVo.setState(AppointmentState.WAIT_AYLSON_QUOTE);//aylson报价中
			flag = this.appointmentService.edit(appointmentVo);
			if(!flag){
				result.setError(ResultCode.CODE_STATE_4006, "更新预约信息【待报价】状态失败");
				throw new ServiceException("更新预约信息【待报价】状态失败");
			}
		}
		if(flag){
			result.setOK(ResultCode.CODE_STATE_200, "操作成功");
		}else{
			result.setError(ResultCode.CODE_STATE_4006, "操作失败");
		}
		return result;
	}

	@Override
	public DesignVo getDesign(Integer designId, Integer designType) {
		DesignVo designVo = null;
		if(designId != null){
			designVo =  this.designDao.selectById(designId);
			if(designVo != null){
				if(designVo.getOrderTime() != null){
					designVo.setOrderTimeStr(DateUtil.format(designVo.getOrderTime(), true));
				}
				if(DesignType.SUMROOM == designType.intValue()){
					DesignDetailSRVo designDetailSRVo = this.designDetailSRService.getByDesignId(designId);
					designVo.setDesignDetailSRVo(designDetailSRVo);
				}else{
					List<DesignDetailDWVo> designDetailDWList = this.designDetailDWService.getByDesignId(designId);
					designVo.setDesignDetailDWList(designDetailDWList);
				}
			}else{
				throw new ServiceException("根据设计id找不到相关设计信息");
			}
		}
		return designVo;
	}


}
