package com.ihomefnt.o2o.intf.domain.program.customgoods.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 定制柜商品详情信息
 *
 * @author liyonggang
 * @create 2019-07-27 14:37
 */
@Data
@ApiModel("定制柜商品详情信息")
public class CabinetBomGroupDetailResponse implements Serializable {


    @ApiModelProperty("组合二级分类id")
    private Integer secondCategoryId;

    @ApiModelProperty("组合二级分类名称")
    private String secondCategoryName;

    @Data
    @ApiModel("柜体信息")
    @Accessors(chain = true)
    public static class GroupDetailByCabinetType {

        @ApiModelProperty("柜体标签")
        private String cabinetType;

        @ApiModelProperty("柜体标签名称")
        private String cabinetTypeName;

        @ApiModelProperty("组合信息列表")
        List<GroupSimpleDetail> groupList;

    }

    @Data
    @ApiModel("组合简单信息")
    @Accessors(chain = true)
    public static class GroupSimpleDetail {

        @ApiModelProperty("组合图片")
        private String groupImage;

        @ApiModelProperty("组合id")
        private Integer groupId;

        @ApiModelProperty("组合名称")
        private String groupName;

        @ApiModelProperty("组合属性列表")
        List<GroupPropertyList> propertyList;
    }

    @Data
    @ApiModel("组合简单信息")
    @Accessors(chain = true)
    public static class GroupPropertyList {

        @ApiModelProperty("属性图片")
        private String imageUrl;

        @ApiModelProperty("属性名称")
        private String propertyName;

        @ApiModelProperty("属性选项值")
        private String propertyValue;

        @ApiModelProperty("备注")
        private String remark;
    }

}
