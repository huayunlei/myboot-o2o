package com.ihomefnt.o2o.intf.domain.meeting.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 发布照片
 * @author ZHAO
 */
@Data
@ApiModel("发布照片请求参数")
public class PublishPicRequest {
	@ApiModelProperty("家庭ID")
    private Integer familyId;
	
	@ApiModelProperty("成员ID")
    private Integer memberId;

	@ApiModelProperty("图片地址集合")
    private String picUrls;
}
