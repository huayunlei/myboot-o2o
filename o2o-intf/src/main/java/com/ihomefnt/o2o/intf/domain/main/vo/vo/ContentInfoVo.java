package com.ihomefnt.o2o.intf.domain.main.vo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("首页核心操作区信息详情")
@Data
public class ContentInfoVo {

    @ApiModelProperty("核心区状态名称")
    private String mainCoreName;

    @ApiModelProperty("核心区状态编号")
    private String mainCoreNo;

    @ApiModelProperty("内容区样式：1单图；2主副标题+单图；3图片+角标；4施工进行中样式")
    private Integer contentType;

    @ApiModelProperty("图片的地址")
    private String pictureUrl;

    @ApiModelProperty("图片宽度")
    private Integer pictureWidth;

    @ApiModelProperty("图片高度")
    private Integer pictureHeight;

    @ApiModelProperty("当内容区样式为3图片+角标的形式时，图片的跳转地址")
    private String pictureOpenUrl;

    @ApiModelProperty("标题格式：1左对齐，2居中，3右对齐")
    private Integer titleStyle;

    @ApiModelProperty("主标题")
    private String title;

    @ApiModelProperty("副标题")
    private String subTitle;

    @ApiModelProperty("图片角标信息")
    private List<CornerMarkerVo> cornerMarkers;

    @ApiModelProperty("附属区样式-目前仅施工进行中状态使用")
    private List<ExtraInfoVo> subItems;

    @ApiModelProperty("视频-目前仅施工进行中状态使用")
    private ExtraInfoVo videoInfo;

}
