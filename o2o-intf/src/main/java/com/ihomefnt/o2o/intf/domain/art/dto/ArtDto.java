package com.ihomefnt.o2o.intf.domain.art.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wanyunxin
 * @create 2019-08-06 20:16
 */

@Data
@ApiModel(value="新版艾商城艺术品")
public class ArtDto {

    @ApiModelProperty(value = "艺术作品编号")
    private String worksId;

    @ApiModelProperty(value = "艺术作品名称")
    private String worksName;

    @ApiModelProperty(value = "作品图片")
    private String worksPicUrl;

    @ApiModelProperty(value = "作者id")
    private String artistId;

    @ApiModelProperty(value = "作者名称")
    private String artistName;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty("规格ID")
    private String specificationId;
}
