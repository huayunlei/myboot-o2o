package com.ihomefnt.o2o.intf.domain.product.doo;

import lombok.Data;

@Data
public class UserConsult {
	
    private String consultId;//id
    private String userId;//用户ID
    private String userName;//用户名
    private String content;//咨询内容
    private String createDate ;//咨询时间
    private String replyContent;//回复内容
    private String replyDate ;//回复时间
    private String status;//状态
    
    private Long productType;//商品类型1:套装2：空间3：单品
    
    private Long productId;
    
    private String customerNickName;//昵称

	public String getCreateDate() {
		if(null != createDate){
			return createDate.substring(0, 10);
		}
		return createDate;
	}

	public String getReplyDate() {
		if(null != replyDate){
			return replyDate.substring(0, 10);
		}
		return replyDate;
	}
}
