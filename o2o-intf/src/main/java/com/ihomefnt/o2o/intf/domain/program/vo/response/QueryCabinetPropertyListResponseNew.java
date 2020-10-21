package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.google.common.collect.Lists;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-08-01 18:57
 */
@Data
public class QueryCabinetPropertyListResponseNew{

    @ApiModelProperty(value = "柜体标签总揽")
    private String cabinetTypeNameJoin;

    List<CabinetDataDto> cabinetDataDtoList;


    @Data
    @Accessors(chain = true)
    public static class CabinetDataDto {

        @ApiModelProperty("柜属性数据")
        private List<PropertyDataDto> dataList;

        @ApiModelProperty("柜组件数据")
        private List<PropertyDataDto> componentDataList = Lists.newArrayList();

        private String cabinetType;

        private String cabinetTypeName;

        private List<Integer> groupIdList;

        private List<Integer> defaultGroupIdList;

        private List<GroupRelationDto> groupRelationDtoList;
    }

    @Data
    @Accessors(chain = true)
    public static class GroupRelationDto {

        @ApiModelProperty(value = "组合id")
        private Integer groupId;

        @ApiModelProperty(value = "组合用量")
        private Integer defaultGroupNum;

        @ApiModelProperty(value = "默认组合id")
        private Integer defaultGroupId;

    }


    @Data
    public static class PropertyDataDto {

        private String propertyName;

        private Integer componentId;

        private Integer categoryId;

        private Integer propertyId;

        private Integer index;

        private Integer defaultPropertyValueCode;

        private Integer selectPropertyValueCode;

        private Integer type;//1:属性。2:组件

        List<PropertyValueList> optionList;

        private Integer materialId;

        private Integer defaultMaterialId;

        private Integer uniquenessId;
    }


    @Data
    @Accessors(chain = true)
    public static class PropertyValueList {
        private Integer propertyValueCode;
        private String propertyValue;
        private Integer isDefault = 2;//是否是标配 1：是，2，不是
        private Integer type;//1:属性。2:组件
        private String propertyUrl;
        private Integer index;
        private Integer uniquenessId;
        public String getPropertyUrl() {
            return AliImageUtil.imageCompress(propertyUrl, 1, 750, ImageConstant.SIZE_SMALL);
        }
    }

}
