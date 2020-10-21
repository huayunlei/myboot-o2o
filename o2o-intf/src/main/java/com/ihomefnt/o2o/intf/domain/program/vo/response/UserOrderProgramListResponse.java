package com.ihomefnt.o2o.intf.domain.program.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-08-08 15:49
 */
@Data
@ApiModel("方案意见列表数据")
@Accessors(chain = true)
public class UserOrderProgramListResponse {

    @ApiModelProperty("订单列表")
    List<ProgramOrderInfo> orderList;

    @ApiModelProperty("房产数量")
    private Integer houseCount;

    @ApiModelProperty("方案数量")
    private Integer solutionCount;

    @Data
    public static class ProgramOrderInfo {
        private Integer orderNum;
        private List<SolutionInfo> solutionInfoList;
        private String buildingName = "";
        private String houseLayoutName = "";

        @Data
        public static class SolutionInfo {
            private Integer solutionId;
            private String solutionImage;
            private String solutionName;
            private String solutionStyleName;
            private String solutionTypeName;
            private Integer reviseOpinionNum = 0;
        }
    }
}
