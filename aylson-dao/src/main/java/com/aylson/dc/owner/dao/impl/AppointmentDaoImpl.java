package com.aylson.dc.owner.dao.impl;

import org.springframework.stereotype.Repository;

import com.aylson.core.frame.dao.impl.BaseDaoImpl;
import com.aylson.dc.owner.dao.AppointmentDao;
import com.aylson.dc.owner.po.Appointment;
import com.aylson.dc.owner.search.AppointmentSearch;

@Repository
public class AppointmentDaoImpl extends BaseDaoImpl<Appointment,AppointmentSearch> implements AppointmentDao {

	
}
