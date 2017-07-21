package com.aylson.dc.owner.service;

import com.aylson.core.frame.domain.Result;
import com.aylson.core.frame.service.BaseService;
import com.aylson.dc.owner.po.Appointment;
import com.aylson.dc.owner.search.AppointmentSearch;
import com.aylson.dc.owner.vo.AppointmentVo;

public interface AppointmentService extends BaseService<Appointment,AppointmentSearch> {
	
	/**
	 * 通知代理商报价
	 * @param id
	 * @return
	 */
	public Result noticeAgentQuote(Integer appointId);
	
	/**
	 * 通知客户确认报价
	 * @param id
	 * @return
	 */
	public Result noticeConfirmQuote(Integer appointId);
	
	/**
	 * 提交下一步确认大样图
	 * @param appointId
	 * @return
	 */
	public Result sumConfrimDraw(Integer appointId);
	
	/**
	 * 根据预约信息id获取预约所有详情
	 * @param appointId
	 * @return
	 */
	public AppointmentVo getAppointDetail(Integer appointId);

}
