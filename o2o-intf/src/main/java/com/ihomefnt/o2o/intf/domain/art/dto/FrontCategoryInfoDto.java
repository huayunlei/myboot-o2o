package com.ihomefnt.o2o.intf.domain.art.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-08-06 19:49
 */
@NoArgsConstructor
@Data
@ApiModel(value="新艾商城艺术品app端分类")
public class FrontCategoryInfoDto {

        @ApiModelProperty(value = "分类id")
        private Integer id;

        @ApiModelProperty(value = "app端分类名称")
        private String frontCategoryName;

        @ApiModelProperty(value = "app端显示小图标")
        private String frontCategoryIcon;

        @ApiModelProperty(value = "app端显示图片")
        private String frontCategoryImage;

        @ApiModelProperty(value = "商品中心oms商品分类信息")
        private List<ArtCategoryInfoBean> artCategoryInfo;

        @Data
        public static class ArtCategoryInfoBean {

            @ApiModelProperty(value = "艺术商品类目id")
            private String artCategoryId;

            @ApiModelProperty(value = "艺术商品类目名称")
            private String artCategoryName;
        }
}
