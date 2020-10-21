package com.ihomefnt.o2o.intf.domain.customer.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

@Data
public class CustomerQueryRequestVo extends HttpBaseRequest{
	private int customerType;//0:全部 1:已邀请 2:已到店 3:交易中 4:已结佣 5:到店过期 6:交易过期

}
