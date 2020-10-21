package com.ihomefnt.o2o.intf.domain.customer.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

@Data
public class CustomerDetailRequestVo extends HttpBaseRequest{
	private Long customerId;//客户ID

}
