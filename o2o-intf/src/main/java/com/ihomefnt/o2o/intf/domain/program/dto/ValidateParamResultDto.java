package com.ihomefnt.o2o.intf.domain.program.dto;

import com.ihomefnt.cms.intf.house.dto.RoomInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-03-22 20:26
 */
@NoArgsConstructor
@Data
@ApiModel(value = "下单校验返回信息")
public class ValidateParamResultDto {


        @ApiModelProperty("空间错误信息集合")
        private List<RoomErrorListBean> roomErrorList;

        @ApiModelProperty("软装增配包错误信息集合")
        private List<AddErrorBean> softAddBagErrorList;

        @ApiModelProperty("硬装增配包错误信息集合")
        private List<AddErrorBean> hardAddBagErrorList;

        @ApiModelProperty("硬装升级项错误信息集合")
        private List<AddErrorBean> hardStandardErrorList;

        @ApiModelProperty("空间软装商品替换错误信息集合")
        private List<SoftReplaceErrorListBean> softReplaceErrorList;

        @ApiModelProperty("空间硬装商品新增错误信息集合")
        private List<HardAddErrorBean> hardAddErrorList;

        @ApiModelProperty("空间硬装商品替换错误信息集合")
        private List<HardAddErrorBean> hardReplaceErrorList;

        @ApiModelProperty("空间硬装商品删除错误信息集合")
        private List<HardAddErrorBean> hardDeleteErrorList;

        @ApiModelProperty("全屋空间替换错误信息集合")
        private List<HardAddErrorBean> wholeHardReplaceErrorList;

        @ApiModelProperty("空间错误信息集合")
        private List<SolutionError> solutionErrorList;

        List<SoftBomReplaceError> softBomReplaceErrorList;

        @ApiModelProperty("校验结果 1 校验通过 0 校验不通过")
        private Integer checkResult;


        @Data
        public static class AddErrorBean {

                private Integer errorCode;
                private String errorMsg;
                private Integer skuId;
        }

        @Data
        @Accessors(chain = true)
        @ApiModel(value = "软装bom替换错误信息")
        public static class SoftBomReplaceError extends RoomInfo {

                @ApiModelProperty("原组合id")
                private Long groupId;

                @ApiModelProperty("新组合id")
                private Long newGroupId;

                @ApiModelProperty("家具类型")
                private Integer furnitureType;
                private Integer errorCode;
                private String errorMsg;

        }

        @Data
        public static class HardAddErrorBean {

                private Integer craftId;
                private Integer errorCode;
                private String errorMsg;
                private Integer newCraftId;
                private Integer newSkuId;
                private Integer roomClassId;
                private Integer roomId;
                private String roomUsageName;
                private Integer skuId;
                private Integer solutionId;
                private String solutionName;
        }



        @Data
        public static class RoomErrorListBean {

                private Integer errorCode;
                private String errorMsg;
                private Integer roomId;
                private String roomUsageName;
                private Integer solutionId;
                private String solutionName;
        }


        @Data
        public static class SoftReplaceErrorListBean {

                private Integer errorCode;
                private String errorMsg;
                private Integer furnitureType;
                private Integer newSkuId;
                private Integer roomId;
                private String roomUsageName;
                private Integer skuId;
                private Integer solutionId;
                private String solutionName;
        }


        @Data
        public static class SolutionError {

                private Integer errorCode;

                @ApiModelProperty("方案id")
                private Long solutionId;

                @ApiModelProperty("方案id")
                private String solutionName;
        }


     
}
