package com.ihomefnt.o2o.intf.domain.art.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wanyunxin
 * @create 2019-08-07 11:30
 */
@ApiModel("艺术品列表")
@Data
public class ArtistInfoResponse {

    @ApiModelProperty("艺术家id")
    private String artistId;

    @ApiModelProperty("艺术家昵称")
    private String artistName;

    @ApiModelProperty("性别 -1 未知 0女 1男")
    private Integer sex;

    @ApiModelProperty("文字简介")
    private String introduction;

    @ApiModelProperty("图文简介")
    private String introductionHtml;

    @ApiModelProperty("艺术家头像")
    private String personPicUrl;

    @ApiModelProperty("艺术品总数")
    private Integer totalWorksNum;


}