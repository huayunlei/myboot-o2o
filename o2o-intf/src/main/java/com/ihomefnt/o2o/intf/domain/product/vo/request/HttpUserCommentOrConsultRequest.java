package com.ihomefnt.o2o.intf.domain.product.vo.request;

import lombok.Data;

/**
 * Created by wangxiao on 15-12-17.
 */
@Data
public class HttpUserCommentOrConsultRequest {

	private Long productId;    //待评价ID
	
	private Long type;         //1:套装2：空间3：单品
	
	private int pageNo;

    private int pageSize;
}
