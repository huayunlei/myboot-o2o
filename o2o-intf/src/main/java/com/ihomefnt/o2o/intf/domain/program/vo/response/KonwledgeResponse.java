package com.ihomefnt.o2o.intf.domain.program.vo.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 方案小贴士
 * @author ZHAO
 */
@Data
public class KonwledgeResponse implements Serializable {
	private String title;//标题
	
	private String desc;//描述
	
	private String logImgUrl;//LOG图片

	public KonwledgeResponse() {
		this.title = "";
		this.desc = "";
		this.logImgUrl = "";
	}

}
