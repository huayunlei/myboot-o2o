package com.ihomefnt.o2o.intf.domain.customer.doo;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Data
public class CustomerItemDo{
	private long customerId;//客户ID
	
	private String name;//姓名
	
	private String mobile;//手机号码
	
	private int gender;//性别
	
	private int customerType;//1:已邀请 2:已到店 3:交易中 4:已结佣 5:到店过期 6:交易过期
	
	@JsonIgnore(value = true)
	private Timestamp appointmentTime;//预约时间
	private String inviteTime;//预约时间
	
	public String getInviteTime() {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(appointmentTime!=null){
			return sdf.format(appointmentTime);
		}
		return null;
	}
}
