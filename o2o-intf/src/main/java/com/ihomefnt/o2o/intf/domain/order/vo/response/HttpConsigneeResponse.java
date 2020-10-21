package com.ihomefnt.o2o.intf.domain.order.vo.response;

import com.ihomefnt.o2o.intf.domain.order.dto.Consignee;
import lombok.Data;

import java.util.List;

@Data
public class HttpConsigneeResponse {

	private List<Consignee> consigneeList;

}
