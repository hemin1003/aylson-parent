package com.aylson.dc.owner.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.owner.po.Appointment;
import com.aylson.dc.owner.search.AppointmentSearch;
import com.aylson.dc.owner.vo.AppointmentVo;

public interface AppointmentService extends BaseService<Appointment,AppointmentSearch> {
	
	/**
	 * 根据预约信息id获取预约所有详情
	 * @param appointId
	 * @return
	 */
	public Result getAppointDetail(Integer appointId);
	
	/**
	 * 添加预约单
	 * @param appointmentVo
	 * @return
	 */
	public Result addAppointMent(AppointmentVo appointmentVo);
	
	/**
	 * 
	 * @param appointId 预约id
	 * @param isComfirm 是否确认：true 确认  false 不满意
	 * @param opinion 不满意意见，isComfirm = false时必填
	 * @param couponId 活动优惠券id
	 * @param couponUserId 优惠券用户id
	 * @return
	 */
	public Result appointConfirm(Integer appointId, Boolean isComfirm, String opinion, Integer couponId, Integer couponUserId);

	/**
	 * 根据预约id获取预约详情
	 * @param appointId
	 * @return
	 */
	public Result getAppointInfo(Integer appointId);
}
