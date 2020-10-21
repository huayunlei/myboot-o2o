package com.ihomefnt.o2o.intf.domain.customer.doo;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 佣金清单
 * 
 * @author Ivan
 * @date 2016年5月18日 上午11:13:39
 */
@Data
public class CommissionInventoryDo {
	@JsonIgnore(value = true)
	private Long customerId;//客户Id
	private String name;//客户姓名
	private String mobile;//手机号码
	private int gender;//性别
	@JsonIgnore(value = true)
	private Timestamp appointmentTime;//预约时间
	private String inviteTime;//邀请时间
	private double storeMoney;//到店金额
	private double orderMoney;//交易金额
	public String getInviteTime() {
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(appointmentTime!=null){
			return sdf.format(appointmentTime);
		}
		return null;
	}
}
