package com.ihomefnt.o2o.intf.domain.program.vo.response;

import lombok.Data;

/**
 * 基础信息返回数据
 * @author ZHAO
 */
@Data
public class DicResponse {
	private String keyDesc;

	private String valueDesc;

	public DicResponse() {
		this.keyDesc = "";
		this.valueDesc = "";
	}

}
