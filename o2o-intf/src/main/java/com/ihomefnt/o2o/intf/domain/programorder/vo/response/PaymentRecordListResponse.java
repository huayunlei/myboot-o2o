package com.ihomefnt.o2o.intf.domain.programorder.vo.response;

import java.util.List;

import com.ihomefnt.o2o.intf.domain.programorder.dto.TransactionListVo;
import lombok.Data;

/**
 * 方案支付明细记录返回数据
 * @author ZHAO
 */
@Data
public class PaymentRecordListResponse {
	private String payTimeGroup;//时间分组
	
	private List<TransactionListVo> recordList;
}
