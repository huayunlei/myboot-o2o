package com.ihomefnt.o2o.intf.domain.product.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import lombok.Data;

/**
 * Created by wangxiao on 15-12-17.
 */
@Data
public class HttpAddUserConsultRequest  extends HttpBaseRequest{

	private Long productId;    //待评价ID
	private Long type;         //1:套装2：空间3：单品
    private String content;    //咨询内容
    private String phoneNumber;//手机号
}
