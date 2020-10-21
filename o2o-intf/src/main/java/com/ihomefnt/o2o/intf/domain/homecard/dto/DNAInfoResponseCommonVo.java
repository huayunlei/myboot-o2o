package com.ihomefnt.o2o.intf.domain.homecard.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2020-01-17 15:47
 */
@Data
public class DNAInfoResponseCommonVo {

    @ApiModelProperty("主题背景图")
    private String dnaBackgroundPic;

    @ApiModelProperty("主题背景对象")
    private PictureVO dnaBackgroundPicDto;

    @ApiModelProperty("短视频地址")
    private String dnaVideoUrl;

    @ApiModelProperty("视频背景图")
    private String dnaVideoPic;

    @ApiModelProperty("音频地址")
    private String audioFrequencyUrl;

    @ApiModelProperty("色彩示意图")
    private String colorDiagramUrl;

    @ApiModelProperty("颜色短文案")
    private String colorEssay;

    @ApiModelProperty("色彩示意图文字和色块")
    private List<ColorTextAndBlockListBean> colorTextAndBlockList;

    @ApiModelProperty("材质举例")
    private List<MaterialDiagramListBean> materialDiagramList;

    @Data
    public static class ColorTextAndBlockListBean {

        @ApiModelProperty("颜色文字描述")
        private String colorText;

        @ApiModelProperty("颜色色号")
        private String color;

        @ApiModelProperty("文字颜色")
        private String wordColor;


    }

    @Data
    public static class PictureVO {

        @ApiModelProperty("主题背景图url")
        private String dnaBackgroundUrl;

        @ApiModelProperty("字体颜色")
        private String characterRgb;

    }

    @Data
    public static class MaterialDiagramListBean {

        @ApiModelProperty("材质图片")
        private String materialUrl;

        @ApiModelProperty("材质名称")
        private String materialName;
    }
}
