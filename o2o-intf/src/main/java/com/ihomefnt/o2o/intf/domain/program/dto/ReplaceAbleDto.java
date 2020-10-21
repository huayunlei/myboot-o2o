package com.ihomefnt.o2o.intf.domain.program.dto;

import com.ihomefnt.o2o.intf.domain.common.http.HttpBaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-09-16 09:44
 */
@Data
@ApiModel("可替换项集合，用于一键替换")
public class ReplaceAbleDto extends HttpBaseRequest {

    @ApiModelProperty("方案id")
    private Long solutionId;

    @ApiModelProperty("空间数据明细")
    private List<SpaceDesignListBean> spaceDesignList;

    @Data
    public static class SpaceDesignListBean {

        @ApiModelProperty("空间id")
        private Integer spaceDesignId;

        @ApiModelProperty("空间用途名称")
        private String spaceUsageName;

        @ApiModelProperty("软装列表")
        private List<OptionalSoftResponseListBean> optionalSoftResponseList;

        @Data
        public static class OptionalSoftResponseListBean {

            @ApiModelProperty("空间id")
            private Integer roomId;

            @ApiModelProperty("防重主键")
            private String superKey;

            @ApiModelProperty("末级类目名称")
            private Integer lastCategoryId;

            @ApiModelProperty("软装默认项")
            private FurnitureDefaultBean furnitureDefault;

            @ApiModelProperty("bom组合信息")
            private List<BomGroupListBean> bomGroupList;

            @Data
            public static class FurnitureDefaultBean {

                @ApiModelProperty("skuId")
                private Integer skuId;
            }

            @Data
            public static class BomGroupListBean {

                @ApiModelProperty("组合id")
                private Integer groupId;
            }
        }
    }
}
