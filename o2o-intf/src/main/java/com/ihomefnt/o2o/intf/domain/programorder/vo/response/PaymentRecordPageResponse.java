package com.ihomefnt.o2o.intf.domain.programorder.vo.response;

import lombok.Data;

import java.util.List;

/**
 * 方案支付明细记录(分页)返回数据
 * @author ZHAO
 */
@Data
public class PaymentRecordPageResponse {
	private List<PaymentRecordListResponse> list;// 支付明细记录
	
	private Integer pageNo;// 当前第几页
	
	private Integer pageSize;// 每页显示条数
	
	private Integer totalPage;// 总页数
	
	private Integer totalCount;// 总条数
}
