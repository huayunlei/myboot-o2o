package com.ihomefnt.o2o.intf.domain.appointment.vo.response;

import lombok.Data;

import java.util.List;

@Data
public class HttpAppointmentTimeResponse {

	private List<AppointmentTime> appointmentTimeList;
}
