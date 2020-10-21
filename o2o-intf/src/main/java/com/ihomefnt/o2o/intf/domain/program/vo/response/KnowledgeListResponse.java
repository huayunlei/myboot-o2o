package com.ihomefnt.o2o.intf.domain.program.vo.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 方案小贴士集合
 * @author ZHAO
 */
@Data
public class KnowledgeListResponse implements Serializable {
	private List<KonwledgeResponse> styleDescList;//风格说明集合
	
	private List<KonwledgeResponse> brandDescList;//品牌说明集合
}
