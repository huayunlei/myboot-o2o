package com.ihomefnt.o2o.intf.domain.program.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;

/**
 * 方案详情引导页数据
 *
 * @author liyonggang
 * @create 2019-07-01 16:15
 */
@Data
@ApiModel("方案详情引导页")
@Builder
@Accessors(chain = true)
public class ProgramDetailsGuideResponse {

    @ApiModelProperty("方案详情封面数据")
    private PageData cover;

    @ApiModelProperty("方案概述")
    private PageData solutionIdea;

    @ApiModelProperty("人物定位")
    private PageData personLocation;

    @ApiModelProperty("设计说明")
    private PageData descOfDesign;

    @ApiModelProperty("材质分析")
    private PageData textureAnalyze;

    @ApiModelProperty("平面布局")
    private PageData planeLayout;

    @ApiModelProperty("方案效果图")
    private PageData solutionImages;

    @ApiModelProperty("品牌信息")
    private BrandDto  solutionBrandInfo;

    @ApiModelProperty("楼盘名称")
    private String houseProjectName;

    @ApiModelProperty("格局")
    private String houseTypeName;

    @Data
    @Builder
    @ApiModel("方案详情引导页通用类")
    @Accessors(chain = true)
    public static class PageData {

        @ApiModelProperty("图片")
        private List<String> image;

        @ApiModelProperty("设计耗时")
        private String elapsedTime;

        @ApiModelProperty("文案信息")
        private List<CopyWriterPackDto> desc;

        @ApiModelProperty("直接描述，没有标题的用此字段")
        private String simpleDesc;
    }

    @Data
    @Builder
    @ApiModel("文案封装")
    @Accessors(chain = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CopyWriterPackDto {

        private String title;

        private String description;

        private List<String> images;

        private Integer type;
    }

    @Data
    @Builder
    @ApiModel("品牌信息")
    @Accessors(chain = true)
    public static class BrandDto {

        private Set<String> softBrandList;

        private Set<String> hardBrandList;

        private String backgroundImage;
    }
}
