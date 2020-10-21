package com.ihomefnt.o2o.intf.domain.meeting.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增家庭成员响应体
 * @author czx
 */
@Data
@ApiModel("新增家庭成员响应体")
public class AddMemberResponse{
	
	@ApiModelProperty("成员 ID")
    private String memberId;

	@ApiModelProperty("家庭 ID")
    private String familyId;
}
