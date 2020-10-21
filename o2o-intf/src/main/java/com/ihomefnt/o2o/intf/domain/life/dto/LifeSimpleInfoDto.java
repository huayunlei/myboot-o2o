package com.ihomefnt.o2o.intf.domain.life.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liyonggang
 * @create 2018-11-02 16:39
 */
@Data
@ApiModel("文章简单信息")
public class LifeSimpleInfoDto implements Serializable {

    private static final long serialVersionUID = -3130419987467206090L;

    @ApiModelProperty("文章id")
    private Long articleId;
    @ApiModelProperty("文章标题")
    private String title;
    @ApiModelProperty("文章大图")
    private String imageUrl;
}
