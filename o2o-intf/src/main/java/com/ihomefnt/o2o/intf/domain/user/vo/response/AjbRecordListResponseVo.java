package com.ihomefnt.o2o.intf.domain.user.vo.response;

import lombok.Data;

import java.util.List;

/**
 * 艾积分充值记录集合
 * @author ZHAO
 */
@Data
public class AjbRecordListResponseVo {
	private List<AjbRecordResponseVo> recordList;
}
