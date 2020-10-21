package com.ihomefnt.o2o.intf.domain.staticdata.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author liyonggang
 * @create 2019-11-22 10:31
 */
@Data
@ApiModel("图片简单新")
public class SimpleImageResponse {

    @ApiModelProperty("链接")
    private String url;

    @ApiModelProperty("比例")
    private Double proportion;

}
