package com.ihomefnt.o2o.intf.domain.activity.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询活动信息请求参数
 * @author ZHAO
 */
@Data
@ApiModel("查询活动信息请求参数")
public class ActivityInfoRequest {
	@ApiModelProperty("微信openId")
    private String openId;
}
