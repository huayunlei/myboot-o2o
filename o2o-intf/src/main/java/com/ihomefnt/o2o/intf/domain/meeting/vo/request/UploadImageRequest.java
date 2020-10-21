package com.ihomefnt.o2o.intf.domain.meeting.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 上传图片
 * @author ZHAO
 */
@Data
@ApiModel("上传图片请求参数")
public class UploadImageRequest {
	@ApiModelProperty("图片")
    private String imageBase64;
}
