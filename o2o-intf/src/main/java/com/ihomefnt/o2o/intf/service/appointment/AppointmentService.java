package com.ihomefnt.o2o.intf.service.appointment;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.appointment.vo.response.AppointmentTime;

public interface AppointmentService {

	List<AppointmentTime> getDateTime();
}
