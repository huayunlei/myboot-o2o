package com.ihomefnt.o2o.intf.domain.homecard.vo.request;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * APP3.0新版首页视频版块请求参数
 * @author ZHAO
 */
@Data
@ApiModel(value="VideoBoardRequest",description="APP3.0新版首页视频版块块请求参数")
public class VideoBoardRequest extends HttpBaseRequest{
	@ApiModelProperty("视频类型")
	private Integer typeId;
	
	@ApiModelProperty("当前第几页")
	private Integer pageNo;
	
	@ApiModelProperty("每页显示多少条")
	private Integer pageSize;
}
