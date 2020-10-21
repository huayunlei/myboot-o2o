package com.ihomefnt.o2o.intf.domain.visusal.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @author liyonggang
 * @create 2019-08-07 11:00
 */
@Data
@ApiModel("空间可视化图片查询")
public class QuerySpaceImgHardAndSoftRequest {

    @ApiModelProperty("方案id")
    private Long solutionId;

    @ApiModelProperty("空间id")
    private Long roomId;

    @ApiModelProperty("软装sku列表")
    @NotEmpty
    private List<Integer> softSkuIdList;

   private List<SpaceImgQueryHardItemDto> hardSkuIdList;

    @Data
    private static class SpaceImgQueryHardItemDto{

        private Integer hardSelectionId;

        private Integer processSelectId;

    }
}
