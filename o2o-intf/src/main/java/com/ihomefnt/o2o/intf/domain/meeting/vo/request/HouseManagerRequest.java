package com.ihomefnt.o2o.intf.domain.meeting.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询艾管家信息
 * @author ZHAO
 */
@Data
@ApiModel("查询艾管家信息请求参数")
public class HouseManagerRequest {
	@ApiModelProperty("家庭ID")
    private Integer familyId;
}
