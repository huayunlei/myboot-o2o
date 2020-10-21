/**
 * 
 */
package com.ihomefnt.o2o.intf.domain.suit.dto;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * 
 * @author weitichao
 *
 */
@Data
public class HttpWpfAppointmentRequest extends HttpBaseRequest {
	
	private Integer wpfSuitId;//全品家套装id
	
	private String suit_name;//套装名称
	
	private String name;//用户称谓
	
	private String city;//用户所在城市
	
	private String phoneNum;//用户手机号码
	
	private String ip; //IP地址
}
