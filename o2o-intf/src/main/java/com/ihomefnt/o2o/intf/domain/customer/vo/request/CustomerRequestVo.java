package com.ihomefnt.o2o.intf.domain.customer.vo.request;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
@Data
public class CustomerRequestVo extends HttpBaseRequest{
	private String name;//姓名
	private String mobile;//手机号码
	private int gender;//性别
	private String customerBuilding;//客户楼盘
	private String remark;//描述
	@JsonIgnore
    private Long adviser; //置家顾问id
	@JsonIgnore
    private String adviserName;//置家顾问中文名
}
