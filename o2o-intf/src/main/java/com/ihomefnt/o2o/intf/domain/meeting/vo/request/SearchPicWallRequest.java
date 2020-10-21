package com.ihomefnt.o2o.intf.domain.meeting.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询照片墙
 * @author ZHAO
 */
@Data
@ApiModel("查询照片墙请求参数")
public class SearchPicWallRequest {
	@ApiModelProperty("家庭ID")
    private Integer familyId;
	
	@ApiModelProperty("屏幕宽度")
    private Integer width;
	
	@ApiModelProperty("当前第几页")
    private Integer pageNo;
	
	@ApiModelProperty("每页显示条数")
    private Integer pageSize;
}
