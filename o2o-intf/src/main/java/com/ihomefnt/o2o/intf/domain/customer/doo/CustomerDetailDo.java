package com.ihomefnt.o2o.intf.domain.customer.doo;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Data
public class CustomerDetailDo{
	private String name;//姓名
	private String mobile;//手机号码
	private int gender;//性别
	private String customerBuilding;//客户楼盘
	private String remark;//描述
	private int customerType;//1:已邀请 2:已到店 3:交易中 4:已结佣 5:到店过期 6:交易过期
	private String appointmentTime;//预约时间
	
	@JsonIgnore(value = true)
	private Timestamp appointmentTimestamp;//预约时间
	
	private int daysRemaining;//剩余天数
	public String getAppointmentTime() {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(appointmentTimestamp!=null){
			return sdf.format(appointmentTimestamp);
		}
		return null;
	}
}
