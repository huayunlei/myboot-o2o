package com.ihomefnt.o2o.intf.domain.comment.vo.request;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by wangxiao on 15-12-17.
 */

@Data
@Accessors(chain = true)
public class UserCommentRequestVo {

	private Long productId;    //待评价ID
	private Long type;         //1:套装2：空间3：单品4:攻略5：案例
	private int pageNo;
    private int pageSize;
}
