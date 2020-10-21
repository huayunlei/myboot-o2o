package com.ihomefnt.o2o.intf.domain.staticdata.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-11-21 18:56
 */
@Data
@ApiModel("关于艾佳")
public class AboutAiJiaDataResponse {

    @ApiModelProperty("图片集合")
    private List<SimpleImageResponse> imageList;
}
