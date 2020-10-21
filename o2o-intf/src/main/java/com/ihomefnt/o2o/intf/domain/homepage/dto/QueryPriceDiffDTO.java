package com.ihomefnt.o2o.intf.domain.homepage.dto;

import com.ihomefnt.o2o.intf.domain.programorder.dto.SolutionEffectInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wanyunxin
 * @create 2019-04-12 17:36
 */
@ApiModel("差价查询")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryPriceDiffDTO {

    @ApiModelProperty("软硬装标配和替换项差价信息")
    private List<ComparePriceRequest> queryPriceDiffByRoomDTOList;

    @ApiModelProperty("方案和空间差价信息")
    private SolutionEffectInfo solutionEffectInfo;
}
