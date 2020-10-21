package com.ihomefnt.o2o.intf.domain.activity.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xiamingyu
 * @date 2018/11/26
 */
@Data
@ApiModel("画屏分享图片返回")
public class BoeShareResponse {

    @ApiModelProperty("分享图片url")
    private String sharePicUrl;
}
