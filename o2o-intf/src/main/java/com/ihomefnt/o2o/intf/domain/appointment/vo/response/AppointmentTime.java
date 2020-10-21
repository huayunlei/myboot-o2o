package com.ihomefnt.o2o.intf.domain.appointment.vo.response;

import lombok.Data;

import java.util.List;

@Data
public class AppointmentTime {
	private String weekStr;//今天、明天、星期几
	private List<String> timeSlotList;//时间段列表
}
