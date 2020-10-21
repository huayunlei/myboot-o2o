package com.ihomefnt.o2o.intf.domain.program.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * 定制柜属性过滤数据
 *
 * @author liyonggang
 * @create 2019-08-30 15:59
 */
@Data
@ApiModel("定制柜属性过滤数据")
public class CabinetItemCategoryDto {

    private Integer categoryId;//二级分类id
    private String categoryName;//二级分类名称
    private List<CabinetList> cabinetList;

    @Data
    public static class CabinetList {
        private String cabinetTypeName;//柜体标签名称
        private List<ComponentCategoryList> componentCategoryList;//组件集合

        @Data
        public static class ComponentCategoryList {
            private Integer componentCategory;//组件分类
            private String componentCategoryName;//组件分类名称
            private List<ComponentPropertyList> propertyList;

            @Data
            public static class ComponentPropertyList {
                private Integer type;//1:属性替换,2:组件替换
                private Integer propertyId;
                private String propertyName;
            }
        }
    }
}
