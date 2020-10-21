package com.ihomefnt.o2o.intf.domain.program.vo.response;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-07-30 15:57
 */
@Data
public class QueryCabinetPropertyListResponseDolly {


    private List<GroupList> groupList;

    @Data
    public static class GroupList {
        private Integer id;
        private List<ComponentList> componentList;

        @Data
        public static class ComponentList {
            private Integer parentComponentId;
            private Integer categoryId;
            private String categoryName;
            private Integer componentId;
            private Integer materialId;
            private String name;
            private Integer purchasePrice;
            private Integer price;
            private Integer marketPrice;
            private Integer num;
            private Integer defaultLength;
            private Integer defaultWidth;
            private Integer defaultHeight;
            private Integer minLength;
            private Integer minWidth;
            private Integer minHeight;
            private Integer maxLength;
            private Integer maxWidth;
            private Integer maxHeight;
            private Integer chargeUnit;
            private List<PropertyList> propertyList = Lists.newArrayList();

            @Data
            public static class PropertyList {
                private Integer propertyId;
                private String propertyName;
                private Integer propertyValueCode;
                private Integer categoryId;
                private List<TemplatePropertyValueList> templatePropertyValueList;

                @Data
                public static class TemplatePropertyValueList {
                    private Integer addUserId;
                    private Integer updateUserId;
                    private Object id;
                    private Object templateId;
                    private Integer propertyId;
                    private Object parentId;
                    private String propertyValue;
                    private Object propertyExt;
                    private Object isChecked;
                    private String propertyValueUrl;
                    private Integer propertyValueCode;
                    private Object children;
                    private Integer componentId;
                    private Integer groupId;
                    private Integer categoryId;
                }
            }
        }
    }
}
