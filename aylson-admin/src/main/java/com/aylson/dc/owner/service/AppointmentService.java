package com.aylson.dc.owner.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.owner.po.Appointment;
import com.aylson.dc.owner.search.AppointmentSearch;
import com.aylson.dc.owner.vo.AppointmentVo;

public interface AppointmentService extends BaseService<Appointment,AppointmentSearch> {
	
	/**
	 * 根据预约id获取预约详情
	 * @param appointId
	 * @return
	 */
	public Result getAppointInfo(Integer appointId);
	
	/**
	 * 保存/提交
	 * @param appointmentVo
	 * @return
	 */
	public Result save(AppointmentVo appointmentVo);
	
	/**
	 * 提交下一步
	 * @param id
	 * @return
	 */
	public Result next(AppointmentVo appointmentVo);
	
	/**
	 * 添加预约
	 * @param appointmentVo
	 * @return
	 */
	public Result addAppoint(AppointmentVo appointmentVo);
	

}
