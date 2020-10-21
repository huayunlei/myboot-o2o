package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import com.ihomefnt.o2o.intf.manager.util.common.image.AliImageUtil;
import com.ihomefnt.o2o.intf.manager.util.common.image.ImageConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;


/**
 * @author liyonggang
 * @create 2019-07-24 18:38
 */
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class QueryCabinetPropertyListResponse extends HttpBaseRequest {

    @ApiModelProperty(value = "柜体标签总揽")
    private String cabinetTypeNameJoin;

    @ApiModelProperty("柜数据")
    private List<CabinetDataDto> dataList;


    @Data
    public static class CabinetDataDto {

        @ApiModelProperty("柜体标签名称")
        private String cabinetTypeName;

        @ApiModelProperty("柜体标签id")
        private String cabinetType;

        private List<Integer> groupIdList;

        private List<GroupPropertyListDto.ComponentList.PropertyList> propertyList;

        private List<GroupPropertyListDto.ComponentList.PropertyValueSelectList> propertySelectList;


        @Data
        public static class GroupPropertyListDto {

            private Integer id;
            private List<ComponentList> componentList;

            @Data
            public static class ComponentList {
                private Integer categoryId;
                private String categoryName;
                private Integer componentId;
                private List<PropertyList> propertyList;
                private List<PropertyValueSelectList> propertyValueList;

                @Data
                public static class PropertyList {
                    private Integer componentId;
                    private String componentName;
                    private Integer groupId;
                    private Integer propertyId;
                    private Integer defaultGroupId;
                    private String propertyName;
                    private List<PropertyValueList> propertyValueList = new ArrayList<>();

                    @Data
                    @Accessors(chain = true)
                    public static class PropertyValueList {
                        private Integer propertyValueCode;
                        private String propertyValue;
                        private Integer isDefault = 2;//是否是标配 1：是，2，不是
                        private Integer groupId;
                        private Integer componentId;
                        private String propertyUrl;

                        public String getPropertyUrl() {
                            return AliImageUtil.imageCompress(propertyUrl, 1, 750, ImageConstant.SIZE_SMALL);
                        }
                    }
                }

                @Data
                public static class PropertyValueSelectList {
                    private Integer propertyId;
                    private Integer componentId;
                    private Integer groupId;
                    private Integer propertyValueCode;
                    private Integer defaultGroupId;
                    private Integer defaultGroupNum;
                    private Integer defaultMaterialId;
                }
            }
        }
    }
}
