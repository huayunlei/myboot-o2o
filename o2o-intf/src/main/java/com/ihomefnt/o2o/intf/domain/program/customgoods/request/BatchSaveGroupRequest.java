package com.ihomefnt.o2o.intf.domain.program.customgoods.request;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-07-31 10:02
 */
@Data
public class BatchSaveGroupRequest {


    private List<AppGroupSaveParamList> appGroupSaveParamList = Lists.newArrayList();

    @Data
    public static class AppGroupSaveParamList {
        private Integer batchIndex;
        private Integer defaultGroupId;
        private Integer defaultGroupNum;
        private List<MaterialReplaceList> materialReplaceList = Lists.newArrayList();

        @Data
        public static class MaterialReplaceList {
            private Integer componentId;
            private Integer replaceMaterialId;
            private List<PropertyValueList> propertyValueList = Lists.newArrayList();

            @Data
            @Accessors(chain = true)
            public static class PropertyValueList {
                private Integer propertyId;
                private String propertyName;
                private Integer propertyValueCode;
            }
        }
    }
}
