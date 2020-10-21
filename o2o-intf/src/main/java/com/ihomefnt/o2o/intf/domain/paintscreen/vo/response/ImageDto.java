package com.ihomefnt.o2o.intf.domain.paintscreen.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liyonggang
 * @create 2018-12-14 15:09
 */
@Data
@ApiModel("图片信息")
public class ImageDto implements Serializable {

    private static final long serialVersionUID = -533389052378397735L;

    @ApiModelProperty("图片id")
    private String imgId;
    @ApiModelProperty("图片类型 0正常  1广告")
    private Integer imgType;
    @ApiModelProperty("图片url")
    private String imgUrl;

    @ApiModelProperty("宽度")
    private Integer width;

    @ApiModelProperty("高度")
    private Integer height;

}
